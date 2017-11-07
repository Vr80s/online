package com.xczhihui.bxg.online.web.utils;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.support.service.impl.RedisCacheService;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.ItcastUser;
import com.xczhihui.user.center.bean.Token;
import com.xczhihui.user.center.web.utils.UCCookieUtil;

/**
 * 拦截器
 * 
 * @author Haicheng Jiang
 */
public class OnlineInterceptor implements HandlerInterceptor {

	private SimpleHibernateDao dao;
	
	@Autowired
	private UserCenterAPI api;
	
	@Autowired
	private RedisCacheService cacheService;
	
	@Autowired
	private UserService userService;
	
	private List<String> checkuris = new ArrayList<String>();
	
	public OnlineInterceptor(){
		try {
			URL controller = this.getClass().getClassLoader().getResource("auth.xml");
			Document doc = new SAXReader().read(controller.openStream());
			List<Element> uris = doc.selectNodes("//checkuris/uri");
			for (Element e : uris) {
				checkuris.add(e.getStringValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println(request.getRequestURL());
		BxgUser u = UserLoginUtil.getLoginUser(request);
		Token t = UCCookieUtil.readTokenCookie(request);
		
		//session里没有用户，cookie里有用户，以cookie为准设置session
		//这种情况是从其他系统跳过来的或者session失效了
		if (u == null && t != null) {
			String userRedisKey="tuk_"+t.getUserId();
			Serializable serializable = cacheService.get(userRedisKey);
			ItcastUser user = api.getUser(t.getLoginName());
			//验证票和用户中心，验证不通过清空cookie
			if (serializable != null && user != null) {
				UserLoginUtil.setLoginUser(request, this.addUserIfHasNo(user, t));
			} else {
				//UCCookieUtil.clearTokenCookie(response);1
			}
		}
		
		//session与cookie都有，但是不是同一个用户的，以cookie为准重新设置session
		//这种情况可能是先登陆了一个用户，然后用另一个用户从其他系统登陆再跳过来
		if (u != null && t != null && !u.getLoginName().equals(t.getLoginName())) {
			ItcastUser user = api.getUser(t.getLoginName());
			if(user != null){
				UserLoginUtil.setLoginUser(request, this.addUserIfHasNo(user, t));
			}
		}
		
		//没登陆，但是调用了必须登陆才可以调的接口
		if ((UserLoginUtil.getLoginUser(request) == null || UCCookieUtil.readTokenCookie(request) == null)
				&& checkuris.contains(request.getRequestURI())) {
			Gson gson = new GsonBuilder().create();
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
			response.getWriter().write(gson.toJson(ResponseObject.newErrorResponseObject("请登陆！")));
			return false;
		}
		
		return true;
	}
	
	private OnlineUser addUserIfHasNo(ItcastUser user,Token t){
		OnlineUser o = dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", t.getLoginName());
		if (o == null) {
			boolean ism = Pattern.matches("^((1[0-9]))\\d{9}$",t.getLoginName());
			boolean ise = Pattern.matches("^([a-z0-9A-Z]+[-_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",t.getLoginName());
			if (ism  || ise) {
				System.out.println("其他系统用户登陆写入用户！"+t.getLoginName()+";"+t.getTicket());
				o = new OnlineUser();
				o.setLoginName(user.getLoginName());
				o.setName(user.getNikeName());
				o.setEmail(user.getEmail());
				o.setMobile(user.getMobile());
				o.setCreateTime(new Date());
				o.setDelete(false);
				o.setStatus(0);
				o.setVisitSum(0);
				o.setStayTime(0);
				o.setSmallHeadPhoto("/web/images/defaultHeadImg.jpg");
				o.setUserType(0);
				o.setMenuId(-1);
				o.setOrigin(user.getOrigin());
    			o.setType(user.getType());
				userService.addUser(o);
			}
		}
		return o;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	@Resource(name = "simpleHibernateDao")
	public void setDao(SimpleHibernateDao dao) {
		this.dao = dao;
	}
}
