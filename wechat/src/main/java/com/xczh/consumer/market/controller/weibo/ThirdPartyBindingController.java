package com.xczh.consumer.market.controller.weibo;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import weibo4j.http.HttpClient;
import weibo4j.util.WeiboConfig;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.Token;
import com.xczh.consumer.market.utils.UCCookieUtil;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.common.enums.SMSCode;
import com.xczhihui.bxg.online.common.enums.ThirdPartyType;
import com.xczhihui.bxg.online.common.enums.UserUnitedStateType;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.TokenExpires;
import com.xczhihui.user.center.bean.UserOrigin;
import com.xczhihui.user.center.bean.UserSex;
import com.xczhihui.user.center.bean.UserStatus;
import com.xczhihui.user.center.bean.UserType;
import com.xczhihui.wechat.course.model.QQClientUserMapping;
import com.xczhihui.wechat.course.model.WeiboClientUserMapping;
import com.xczhihui.wechat.course.service.IThreePartiesLoginService;

/**
 * 
 * ClassName: ThirdPartyCertificationController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
@Controller
@RequestMapping(value = "/xczh/bind")
public class ThirdPartyBindingController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ThirdPartyBindingController.class);
	
	
	public HttpClient client = new HttpClient();
	
	@Autowired
	private OnlineUserService onlineUserService;
	
	@Autowired
	private IThreePartiesLoginService threePartiesLoginService;
	
	@Autowired
	private WxcpClientUserWxMappingService wxcpClientUserWxMappingService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private UserCenterAPI userCenterAPI;
	
	@Autowired
	private UserCoinService userCoinService;
	
	@Autowired
	private AppBrowserService appBrowserService;
	
	//手机端登录使用
	@Value("${mobile.authorizeURL}")
	public  String weiboMobileAuthorizeURL;

	/**
	 * Description：获取当前用户的绑定信息
	 * @param req
	 * @param userId
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="userBindingInfo")
	@ResponseBody
	public ResponseObject userBindingInfo(HttpServletRequest req,
			HttpServletResponse res ){
		/**
		 * 获取用户信息
		 */
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		if(ou ==null){
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		try {
			Map<String,Object> map = threePartiesLoginService.selectUserBindingInfo(ou.getId());
			return ResponseObject.newSuccessResponseObject(map);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseObject.newSuccessResponseObject("获取用户绑定信息有误");
		}
	}
	/**
	 * Description：解除绑定
	 * @param req
	 * @param userId
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="removeBinding")
	@ResponseBody
	public ResponseObject removeBinding(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("unionId")String unionId,
			@RequestParam("type")Integer type ){
		/**
		 * 获取用户信息
		 */
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		if(ou ==null){
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		try {
			if(type==ThirdPartyType.WECHAT.getCode()){  //微信
				WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByUserIdAndUnionId(ou.getId(), unionId);
				m.setClient_id("");
				wxcpClientUserWxMappingService.update(m);
			}else if(type==ThirdPartyType.WEIBO.getCode()){
				QQClientUserMapping qq = threePartiesLoginService.selectQQClientUserMappingByUserId(ou.getId(), unionId);
		    	qq.setUserId("");
		    	threePartiesLoginService.updateQQInfoAddUserId(qq);
			}else if(type==ThirdPartyType.QQ.getCode()){
				WeiboClientUserMapping weibo = threePartiesLoginService.selectWeiboClientUserMappingByUserId(ou.getId(), unionId);
		    	weibo.setUserId("");
		    	threePartiesLoginService.updateWeiboInfoAddUserId(weibo);
			}
			return ResponseObject.newSuccessResponseObject("解除绑定成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseObject.newSuccessResponseObject("解除绑定失败");
		}
	}
	
	/**
	 * Description：用现有的手机号绑定微信、微博或者qq
	 * @param req
	 * @param userId
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="mobileBindingThirdParty")
	public ResponseObject addBinding(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("unionId")String unionId,
			@RequestParam("type")Integer type ){
		/**
		 * 获取用户信息
		 */
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		if(ou ==null){
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		try {
			if(type==ThirdPartyType.WECHAT.getCode()){  //微信
				WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByUserIdAndUnionId(ou.getId(), unionId);
				m.setClient_id("");
				wxcpClientUserWxMappingService.update(m);
			}else if(type==ThirdPartyType.WEIBO.getCode()){
				QQClientUserMapping qq = threePartiesLoginService.selectQQClientUserMappingByUserId(ou.getId(), unionId);
		    	qq.setUserId("");
		    	threePartiesLoginService.updateQQInfoAddUserId(qq);
			}else if(type==ThirdPartyType.QQ.getCode()){
				WeiboClientUserMapping weibo = threePartiesLoginService.selectWeiboClientUserMappingByUserId(ou.getId(), unionId);
		    	weibo.setUserId("");
		    	threePartiesLoginService.updateWeiboInfoAddUserId(weibo);
			}
			return ResponseObject.newSuccessResponseObject("解除绑定成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseObject.newSuccessResponseObject("解除绑定失败");
		}
	}
	
	
	
	
	/**
	 * 登陆成功处理
	 * @param req
	 * @param res
	 * @param token
	 * @param user
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public void onlogin(HttpServletRequest req, HttpServletResponse res,
                        Token token, OnlineUser user, String ticket) throws SQLException{
		
		LOGGER.info("用户普通登录----》ticket"+ticket);
		/**
		 * 存在两个票，两个票都可以得到用户信息。
		 * 然后根据用户信息得到新的票和这个旧的票进行比较就ok了
		 */
		String appUniqueId = req.getParameter("appUniqueId");
		
		/*
		 * 这个地方是可以了。目前都支持吧
		 */
		
		if(StringUtils.isNotBlank(appUniqueId)){   //表示是app登录
			//设置登录标识
			onlineUserService.updateAppleTourisrecord(appUniqueId,1);
			cacheService.set(ticket, user,TokenExpires.TenDay.getExpires());
			cacheService.set(user.getId(),ticket,TokenExpires.TenDay.getExpires());
			//Map<String,String> mapClientInfo =  com.xczh.consumer.market.utils.HttpUtil.getClientInformation(req);
			String model = req.getParameter("model");
			if(StringUtils.isNotBlank(model) && user.getLoginName()!=null){
				cacheService.set(user.getLoginName(),model,TokenExpires.TenDay.getExpires());
			}else if(user.getLoginName()!=null){
				cacheService.set(user.getLoginName(),"其他设备",TokenExpires.TenDay.getExpires());
			}
		}else{
			// 用户登录成功
			// 第一个BUG的解决:第二个用户登录后将之前的session销毁!
			req.getSession().invalidate();
			// 第二个BUG的解决:判断用户是否已经在Map集合中,存在：已经在列表中.销毁其session.
			// 获得到ServletCOntext中存的Map集合.
			Map<OnlineUser, HttpSession> userMap = (Map<OnlineUser, HttpSession>) req.getServletContext()
					.getAttribute("userMap");
			// 判断用户是否已经在map集合中'
			HttpSession session = userMap.get(user);
			if(session!=null && userMap.containsKey(user)){
				/**
				 *  * 如果存在那么就注销原来的。或者把原来的session搞成一个表示，不是取用户信息的。
				 * 得到客户端信息
				 */
				Map<String,String> mapClientInfo =  com.xczh.consumer.market.utils.HttpUtil.getClientInformation(req);
				//session.invalidate();
				session.setAttribute("topOff", mapClientInfo);
				session.setAttribute("_user_",null);
			}else if(session!=null){
				session.setAttribute("topOff",null);
			}
			// 使用监听器:HttpSessionBandingListener作用在JavaBean上的监听器.
			req.getSession().setMaxInactiveInterval(86400);//设置session失效时间
			req.getSession().setAttribute("_user_", user);
			/**
			 * 这是cookie 
			 */
			UCCookieUtil.writeTokenCookie(res, token);
		}
	}
}
