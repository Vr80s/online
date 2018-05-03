package com.xczhihui.bxg.online.web.interceptor;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
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

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.support.service.impl.RedisCacheService;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.ItcastUser;
import com.xczhihui.user.center.bean.Token;
import com.xczhihui.user.center.web.utils.UCCookieUtil;

/**
 * Description：权限拦截
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/4/26 0026 上午 10:56
 **/
public class OnlineInterceptor implements HandlerInterceptor {

	private SimpleHibernateDao dao;
	
	@Autowired
	private UserCenterAPI api;
	
	@Autowired
	private RedisCacheService cacheService;
	
	@Autowired
	private UserService userService;
	
	private List<String> checkuris = new ArrayList<String>();
	private List<String> checkanchoruris = new ArrayList<String>();

	public OnlineInterceptor(){
		try {
			URL controller = this.getClass().getClassLoader().getResource("auth.xml");
			Document doc = new SAXReader().read(controller.openStream());
			List<Element> uris = doc.selectNodes("//checkuris/uri");
			for (Element e : uris) {
				checkuris.add(e.getStringValue());
			}
			//新增主播校验2018-02-06
			List<Element> anchoruris = doc.selectNodes("//checkuris/anchoruri");
			for (Element e : anchoruris) {
				checkuris.add(e.getStringValue());
				checkanchoruris.add(e.getStringValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		BxgUser u = UserLoginUtil.getLoginUser(request);
		Token t = UCCookieUtil.readTokenCookie(request);
		
		//session里没有用户，cookie里有用户，以cookie为准设置session
		//这种情况是session失效了
		if (u == null && t != null) {
			String userRedisKey="tuk_"+t.getUserId();
			Serializable serializable = cacheService.get(userRedisKey);
//			ItcastUser user = api.getUser(t.getLoginName());
			//验证票和用户中心，验证不通过清空cookie
			if (serializable != null) {
				UserLoginUtil.setLoginUser(request, this.getUser(t));
			} else {
				//20171214若无票据 清理cookies
				UCCookieUtil.clearTokenCookie(response);
			}
		}
		
		//session与cookie都有，但是不是同一个用户的，以cookie为准重新设置session
		//这种情况可能是先登录了一个用户，然后用另一个用户从其他系统登录再跳过来
		if (u != null && t != null && !u.getLoginName().equals(t.getLoginName())) {
			ItcastUser user = api.getUser(t.getLoginName());
			if(user != null){
				UserLoginUtil.setLoginUser(request, this.getUser(t));
			}
		}
		
		//没登录，但是调用了必须登录才可以调的接口
		boolean b = (UserLoginUtil.getLoginUser(request) == null || UCCookieUtil.readTokenCookie(request) == null) && checkUris(request.getRequestURI());
		if (b) {
			Gson gson = new GsonBuilder().create();
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
			response.getWriter().write(gson.toJson(ResponseObject.newErrorResponseObject("请登录！")));
			return false;
		}else{
			OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
			//设置当前用户
			com.xczhihui.bxg.online.web.controller.AbstractController.setCurrentUser(user);
		}

		//已经登录，但是调用了主播角色才可以调的接口
		if (checkAnchoruris(request.getRequestURI())) {
			BxgUser user = UserLoginUtil.getLoginUser(request);
			Boolean anchor = userService.isAnchor(user.getLoginName());
			if(!anchor){
				response.sendRedirect("/");
				return false;
				/*Gson gson = new GsonBuilder().create();
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(ResponseObject.newErrorResponseObject("不具有主播权限或权限被禁用！")));
				return false;*/
			}
		}
		
		return true;
	}

	private boolean checkAnchoruris(String uri) {
		for (int i = 0; i < checkanchoruris.size(); i++) {
			String checkuri =  checkanchoruris.get(i);
			Pattern pattern = Pattern.compile(checkuri);
			Matcher matcher = pattern.matcher(uri);
			if(matcher.matches()) {
				System.out.println("exp:"+checkuri);
				System.out.println("uri:"+uri);
				return true;
			}
		}
		return false;
	}

	private boolean checkUris(String uri) {
		for (int i = 0; i < checkuris.size(); i++) {
			String checkuri =  checkuris.get(i);
			Pattern pattern = Pattern.compile(checkuri);
			Matcher matcher = pattern.matcher(uri);
			if(matcher.matches()) {
				System.out.println("anchor-exp:"+checkuri);
				System.out.println("anchor-uri:"+uri);
				return true;
			}
		}
		return false;
	}

	private OnlineUser getUser(Token t){
		OnlineUser o = dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", t.getLoginName());
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
