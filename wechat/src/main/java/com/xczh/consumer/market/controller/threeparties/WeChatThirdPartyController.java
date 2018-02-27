package com.xczh.consumer.market.controller.threeparties;

import java.sql.SQLException;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ClientUserUtil;
import com.xczh.consumer.market.utils.ConfigUtil;
import com.xczh.consumer.market.utils.HttpUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.SLEmojiFilter;
import com.xczh.consumer.market.utils.ThridFalg;
import com.xczh.consumer.market.utils.Token;
import com.xczh.consumer.market.utils.UCCookieUtil;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.util.CommonUtil;
import com.xczhihui.bxg.online.common.enums.ThirdPartyType;
import com.xczhihui.bxg.online.common.enums.UserUnitedStateType;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.TokenExpires;

/**
 * 用户controller
 * @author zhangshixiong
 * @date 2017-02-22
 */
@Controller
@RequestMapping(value = "/xczh/wxlogin")
public class WeChatThirdPartyController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WeChatThirdPartyController.class);
	
	
	@Autowired
	private OnlineUserService onlineUserService;
	
	@Autowired
	private	WxcpClientUserWxMappingService wxcpClientUserWxMappingService;
	
	@Autowired
	private UserCenterAPI userCenterAPI;
	
	@Autowired
	private CacheService cacheService;
	
	@Value("${returnOpenidUri}")
	private String returnOpenidUri;
	
	/**
	 * Description：h5 --》 得到微信微信 oauth2 获取code
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("wxGetCodeUrl")
	public void getOpenId(HttpServletRequest req,
			HttpServletResponse res) throws Exception{
		
		String redirect_uri ="/xczh/wxlogin/wxThirdGetAccessToken";
		String userId = req.getParameter("userId");
	    if(StringUtils.isNotBlank(userId)){
	    	OnlineUser ou =  onlineUserService.findUserById(userId);
	    	if(ou == null){ //用户信息异常---》直接去登录页面
	    		res.sendRedirect(returnOpenidUri + "/xcview/html/enter.html");
	    		return;
	    	}
	    	redirect_uri +="?userId="+userId;
	    }
		String strLinkHome 	= WxPayConst.CODE_URL_3+"?appid="+WxPayConst.gzh_appid+"&redirect_uri="+returnOpenidUri+redirect_uri+"&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect".replace("appid=APPID", "appid="+ WxPayConst.gzh_appid);
		LOGGER.info("strLinkHome:"+strLinkHome);
		//存到session中，如果用户回调成功
		res.sendRedirect(strLinkHome);
	}
	

	/**
	 * 点击在线课堂    --》微信授权获取用户信息后的回调
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("wxThirdGetAccessToken")
	public void h5GetOpenid1(HttpServletRequest req, HttpServletResponse res,
			Map<String, String> params) throws Exception {
		
		LOGGER.info("WX return code:" + req.getParameter("code"));
		LOGGER.info("WX return userId:" + req.getParameter("userId"));
		
		try {
			/**
			 * 通过code获取微信信息
			 */
			String code = req.getParameter("code");
			String userId = req.getParameter("userId");
			
			WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
			
			if(wxw==null){
				LOGGER.info("微信第三方认证失败 ");
			}
			LOGGER.info("wxxx"+wxw.toString());
			LOGGER.info("openId "+wxw.getOpenid());
			String openId = wxw.getOpenid();
			/**
			 * 如果这个用户信息已经保存进去了，那么就直接登录就ok
			 */
			if(StringUtils.isNotBlank(wxw.getClient_id())){
				
				LOGGER.info(" 已经绑定过了:" +wxw.getClient_id());
				
				if(userId!=null){
					//type=1 说明这个已经绑定了
					res.sendRedirect(returnOpenidUri + "/xcview/html/lickacc_mobile.html?type=1");	
					return;
				}
				OnlineUser  ou = onlineUserService.findUserById(wxw.getClient_id());
			    ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
				Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
				
				//把用户中心的数据给他   这里im都要用到
				ou.setUserCenterId(iu.getId());
				ou.setPassword(iu.getPassword());
				ou.setTicket(t.getTicket());
				onlogin(req,res,t,ou,t.getTicket());
				
				/**
				 * 清除这个cookie
				 */
				UCCookieUtil.clearThirdPartyCookie(res);
				
				if (openId != null && !openId.isEmpty()) {
					res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html?openId="+ openId);
				} else{
					res.sendRedirect(returnOpenidUri + "/xcview/html/enter.html");
				}	
			}else{
				LOGGER.info(" 没有绑定了:");
				if(userId!=null){
            	   /**
            	    * 更改微信信息	--》增加用户id
            	    */
					wxw.setClient_id(userId);
	            	wxcpClientUserWxMappingService.update(wxw);
					//type=2   绑定成功
					res.sendRedirect(returnOpenidUri + "/xcview/html/lickacc_mobile.html?type=2");	
					return;
				}
				/*
				 * 跳转到绑定手机号页面。也就是完善信息页面。  --》绑定类型微信
				 */
				//res.sendRedirect(returnOpenidUri + "/xcview/html/evpi.html?openId="+openId+"&unionId="+wxw.getUnionid()+"&jump_type=1");
				
				/**
				 * 写入这个cookie
				 */
				ThridFalg tf = new ThridFalg(); 
				tf.setOpenId(wxw.getUnionid());
				tf.setUnionId(wxw.getOpenid());
				
				UCCookieUtil.writeThirdPartyCookie(res,tf);
				
				
				LOGGER.info("readThirdPartyCookie{}{}{}{}{}{}"+UCCookieUtil.readThirdPartyCookie(req));
				
				res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html?openId="+openId+"&unionId="+wxw.getUnionid()+"&jump_type=1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Description：app端第三方登录  ---绑定手机号使用
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="appThirdPartyLogin")
	@ResponseBody
	@Transactional
	public ResponseObject addAppThirdparty(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("accessToken")String accessToken,
			@RequestParam("openId")String openId,
			@RequestParam("model")String model) throws Exception {
		
		LOGGER.info("WX get access_token	:" + accessToken);
		LOGGER.info("WX get openid	:" + openId);
		
		String userId = req.getParameter("userId");
	    if(StringUtils.isNotBlank(userId)){
	    	OnlineUser ou =  onlineUserService.findUserById(userId);
	    	if(ou == null){
	    		return	ResponseObject.newErrorResponseObject("获取用户信息有误");
	    	}
	    }
	    LOGGER.info("绑定呢还是解除绑定呢： "+ userId);
		
	    
		Map<String,String> mapRequest = new HashMap<String,String>();
		mapRequest.put("type", ThirdPartyType.WECHAT.getCode()+"");
		try {
			/**
			 * 通过票据和openId获取用户微信信息
			 */
			String user_buffer =  CommonUtil.getUserInfo(accessToken,openId);
			
			JSONObject jsonObject = JSONObject.parseObject(user_buffer);//Map<String, Object> user_info =GsonUtils.fromJson(user_buffer, Map.class);
			String openid_ = (String)jsonObject.get("openid");
			String nickname_ = (String)jsonObject.get("nickname");
			String sex_ = String.valueOf(jsonObject.get("sex"));
			String language_ = (String)jsonObject.get("language");
			String city_ = (String)jsonObject.get("city");
			String province_ = (String)jsonObject.get("province");
			String country_ = (String)jsonObject.get("country");
			String headimgurl_ = (String)jsonObject.get("headimgurl");
			String unionid_ = (String)jsonObject.get("unionid");
			
			//防止表情名字
			nickname_= SLEmojiFilter.filterEmoji(nickname_);
			/**
			 * 用户  unionid_ 表名唯一用户
			 */
			WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserByUnionId(unionid_);
			/**
			 * 如果存在，那么就不添加了。
			 */
			if(null == m){
				WxcpClientUserWxMapping wxcpClientUserWxMapping = new WxcpClientUserWxMapping();
				wxcpClientUserWxMapping.setWx_id(UUID.randomUUID().toString().replace("-", ""));
				wxcpClientUserWxMapping.setWx_public_id(WxPayConst.app_appid);
				wxcpClientUserWxMapping.setWx_public_name(WxPayConst.appid4name);
				wxcpClientUserWxMapping.setOpenid(openid_);
				wxcpClientUserWxMapping.setNickname(nickname_);
				wxcpClientUserWxMapping.setSex(sex_);
				wxcpClientUserWxMapping.setLanguage(language_);
				wxcpClientUserWxMapping.setCity(city_);
				wxcpClientUserWxMapping.setProvince(province_);
				wxcpClientUserWxMapping.setCountry(country_);
				wxcpClientUserWxMapping.setHeadimgurl(headimgurl_);
				wxcpClientUserWxMapping.setProvince(province_);
				wxcpClientUserWxMapping.setUnionid(unionid_);	
				
			    if(userId!=null){  // 绑定成功
			    	 wxcpClientUserWxMapping.setClient_id(userId);	
	            	 mapRequest.put("code",UserUnitedStateType.MOBILE_BINDING.getCode()+"");
	 			}else{
	 				 mapRequest.put("code",UserUnitedStateType.UNBOUNDED.getCode()+"");
	 			}
				wxcpClientUserWxMappingService.insert(wxcpClientUserWxMapping);
				mapRequest.put("unionId",unionid_+"");
				
				return ResponseObject.newSuccessResponseObject(mapRequest,Integer.parseInt(mapRequest.get("code").toString()));
			}else if(StringUtils.isNotBlank(m.getClient_id())){ //绑定了用户信息
				
				
				
				if(userId!=null){  // 这里说明人家这个已经绑定过其他信息了。我的天
					mapRequest.put("code",UserUnitedStateType.MOBILE_UNBOUNDED.getCode()+"");	
					return ResponseObject.newSuccessResponseObject(mapRequest,UserUnitedStateType.MOBILE_UNBOUNDED.getCode());
	 			}
				
				OnlineUser ou =  onlineUserService.findUserById(m.getClient_id());
				ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
				Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
				//把用户中心的数据给他  --这些数据是IM的
				ou.setUserCenterId(iu.getId());
				ou.setPassword(iu.getPassword());
				ou.setTicket(t.getTicket());
				/**
				 *	 直接让登录了  返回状态值：200
				 */
				this.onlogin(req,res,t,ou,t.getTicket());
				return ResponseObject.newSuccessResponseObject(ou,UserUnitedStateType.BINDING.getCode());
			
			}else if(!StringUtils.isNotBlank(m.getClient_id())){
				
				
				if(userId!=null){  // 绑定成功
	            	 mapRequest.put("code",UserUnitedStateType.MOBILE_BINDING.getCode()+"");
	            	 /**
	            	  * 更改qq信息	--》增加用户id
	            	  */
	            	 m.setClient_id(userId);
	            	 wxcpClientUserWxMappingService.update(m);
		 		}else{
		 			 mapRequest.put("code",UserUnitedStateType.UNBOUNDED.getCode()+"");
		 		}
				mapRequest.put("unionId",unionid_+"");
				return ResponseObject.newSuccessResponseObject(mapRequest,Integer.parseInt(mapRequest.get("code").toString()));
			}
			return ResponseObject.newSuccessResponseObject("");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
	
	/**
	 * Description：在微信端 ---> 普通登录需要获取到用户的openid
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("h5BsGetCodeUrlReqParams")
	public void getOpenIdReqParams(HttpServletRequest req, HttpServletResponse res) throws Exception{

		
		String userName = req.getParameter("username");
		OnlineUser ou = onlineUserService.findUserByLoginName(userName);
		
		WxcpClientUserWxMapping wxw = wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByUserId(ou.getId());
		if(wxw!=null){ //说明已经绑定过了  --》直接去首页
			
			LOGGER.info("已经绑定了:"+wxw.toString());
			
			res.sendRedirect(returnOpenidUri+"/xcview/html/home_page.html?openId="+wxw.getOpenid());
		}else{ 		   //没有授权
			String strLinkHome 	=
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WxPayConst.gzh_appid+
				"&redirect_uri="+returnOpenidUri+"/xczh/wxlogin/h5GetCodeAndUserMobile?userName="+userName+
			    "&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect";
				
			LOGGER.info("还没有绑定 要获取openid了:");
			
			LOGGER.info("strLinkHome:"+strLinkHome);
			res.sendRedirect(strLinkHome);
		}
	}
	
	/**
	 * 1、需要在写一个来判断这个微信信息是否包含了手机号。
	 * 2、或者这个手机号是否和已经存在的一样
	 */
	@RequestMapping("h5GetCodeAndUserMobile")
	public void h5GetCodeAndUserMobile(HttpServletRequest req, HttpServletResponse res,
			Map<String, String> params) throws Exception {

		ConfigUtil cfg = new ConfigUtil(req.getSession());
		String returnOpenidUri = cfg.getConfig("returnOpenidUri");
		/**
		 * 微信回调后，获取微信信息。
		 */
		String userName = req.getParameter("userName");
		String code = req.getParameter("code");
		
		LOGGER.info("WX return code:" +code);
		
		/*
		 * 获取当前登录用户信息
		 */
		OnlineUser ou = onlineUserService.findUserByLoginName(userName);
		/*
		 * 获取当前微信信息
		 */
		WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
		
		if(!StringUtils.isNotBlank(wxw.getClient_id())){ // 当前微信号没有绑定手机号，就直接绑定这个手机号就行了
			
			LOGGER.info("当前微信号没有绑定手机号，就直接绑定这个手机号就行了:");
			/*
			 * 如果用户信息中的一些基本信息为null的话，可以把微信的中基本信息填充过去
			 */
			OnlineUser ouNew = new OnlineUser();
			//性别
			if(ou.getSex()==2){
				ouNew.setSex(Integer.parseInt(wxw.getSex()));
			}
			//名称
			if(StringUtils.isBlank(ou.getName())){
				ouNew.setName(wxw.getNickname());
			}
			//头像
			if(StringUtils.isBlank(ou.getSmallHeadPhoto())){
				ouNew.setSmallHeadPhoto(wxw.getHeadimgurl());
			}
			ouNew.setUnionId(wxw.getUnionid());
			/*
			 * 更新用户信息
			 */
			onlineUserService.updateOnlineUserByWeixinInfo(ou,ouNew);
			/*
			 * 更新微信信息
			 */
			wxw.setClient_id(ou.getId());
			wxcpClientUserWxMappingService.update(wxw);
			/*
			 * 去首页，首页是jsp吗，jsp哈哈就可以得到数据啦
			 */
			res.sendRedirect(returnOpenidUri+"/xcview/html/home_page.html?openId="+wxw.getOpenid());
			
		}else{	//这个微信号，已经绑定了其他的手机号，并不是现在的手机号，所以绑定不了了啊
			
			
			LOGGER.info("这个微信号，已经绑定了其他的手机号，并不是现在的手机号，所以绑定不了了啊");
			
			res.sendRedirect(returnOpenidUri+"/xcview/html/home_page.html?openId="+wxw.getOpenid());
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
				Map<String,String> mapClientInfo =  HttpUtil.getClientInformation(req);
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
