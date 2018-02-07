package com.xczh.consumer.market.controller.weibo;

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
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.SLEmojiFilter;
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
	 * Description：h5 得到微信微信 oauth2 获取code
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("wxGetCodeUrl")
	public void getOpenId(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{
		
		String strLinkHome 	= WxPayConst.CODE_URL_3+"?appid="+WxPayConst.gzh_appid+"&redirect_uri="+returnOpenidUri+"/bxg/wxpay/h5GetOpenid&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect".replace("appid=APPID", "appid="+ WxPayConst.gzh_appid);
		LOGGER.info("strLinkHome:"+strLinkHome);
		//存到session中，如果用户回调成功
		res.sendRedirect(strLinkHome);
	}

	/**
	 * 点击在线课堂
	 * 微信授权获取用户信息后的回调
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
		try {
			/**
			 * 通过code获取微信信息
			 */
			String code = req.getParameter("code");
			
			WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
			if(wxw==null){
				LOGGER.info("微信第三方认证失败 ");
			}
			String openId = wxw.getOpenid();
			/**
			 * 如果这个用户信息已经保存进去了，那么就直接登录就ok
			 */
			if(wxw.getClient_id() != null){
				OnlineUser  ou = onlineUserService.findUserById(wxw.getClient_id());
			    ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
				Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
				ou.setTicket(t.getTicket());
				onlogin(req,res,t,ou,t.getTicket());
				
				if (openId != null && !openId.isEmpty()) {
					res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html?openId"+ openId);
				} else{
					res.sendRedirect(returnOpenidUri + "/xcview/html/enter.html");
				}	
			}else{
				/*
				 * 跳转到绑定手机号页面。也就是完善信息页面。  --》绑定类型微信
				 */
				res.sendRedirect(returnOpenidUri + "/xcview/html/evpi.html?openId="+openId+"&type="+ThirdPartyType.WECHAT.getCode());
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
		String public_id = WxPayConst.app_appid ;
		String public_name = WxPayConst.appid4name ;
		/**
		 * code 200 表示一致，可以登录。
		 * code 201 表示第一次登录，需要绑定手机号
		 * code 202   
		 */
		Map<String,String> mapRequest = new HashMap<String,String>();
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
			
			nickname_= SLEmojiFilter.filterEmoji(nickname_);
			
			WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByOpenId(openid_);
			/**
			 * 如果存在，那么就不添加了。
			 */
			if(null == m){
				WxcpClientUserWxMapping wxcpClientUserWxMapping = new WxcpClientUserWxMapping();
				wxcpClientUserWxMapping.setWx_id(UUID.randomUUID().toString().replace("-", ""));
				wxcpClientUserWxMapping.setWx_public_id(public_id);
				wxcpClientUserWxMapping.setWx_public_name(public_name);
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
				wxcpClientUserWxMappingService.insert(wxcpClientUserWxMapping);
				
				mapRequest.put("code",UserUnitedStateType.UNBOUNDED.getCode()+"");
				mapRequest.put("openId",wxcpClientUserWxMapping.getOpenid()+"");
				mapRequest.put("type",ThirdPartyType.WECHAT.getCode()+"");

				return ResponseObject.newSuccessResponseObject(mapRequest,UserUnitedStateType.UNBOUNDED.getCode());
			}else if(m.getClient_id()!=null){ //绑定了用户信息
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
			
			}else if(m.getClient_id()==null){
				mapRequest.put("code",UserUnitedStateType.UNBOUNDED.getCode()+"");
				mapRequest.put("openId",openid_+"");
				mapRequest.put("type",ThirdPartyType.WECHAT.getCode()+"");
				
				return ResponseObject.newSuccessResponseObject(mapRequest,UserUnitedStateType.UNBOUNDED.getCode());
			}
			return ResponseObject.newSuccessResponseObject("");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
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
