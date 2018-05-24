package com.xczh.consumer.market.controller.threeparties;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xczhihui.common.util.enums.TokenExpires;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.utils.UCCookieUtil;
import com.xczhihui.user.center.vo.OeUserVO;
import com.xczhihui.user.center.vo.ThridFalg;
import com.xczhihui.user.center.vo.Token;
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
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ClientUserUtil;
import com.xczh.consumer.market.utils.ConfigUtil;
import com.xczh.consumer.market.utils.HttpUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.wxpay.TokenThread;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.util.CommonUtil;
import com.xczhihui.common.util.SLEmojiFilter;
import com.xczhihui.common.util.enums.ThirdPartyType;
import com.xczhihui.common.util.enums.UserUnitedStateType;

/**
 * 用户controller
 *
 * @author zhangshixiong
 * @date 2017-02-22
 */
@Controller
@RequestMapping(value = "/xczh/wxlogin")
public class WeChatThirdPartyController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(WeChatThirdPartyController.class);

	@Autowired
	private OnlineUserService onlineUserService;

	@Autowired
	private WxcpClientUserWxMappingService wxcpClientUserWxMappingService;

	@Autowired
	private UserCenterService userCenterService;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private AppBrowserService appBrowserService;

	@Value("${returnOpenidUri}")
	private String returnOpenidUri;
	
	@Value("${webdomain}")
	private String webdomain;

	/**
	 * 公众号和手机号在h5中的关系 Description： 1、h5
	 * 微信第三方登录（如果没有绑定手机号，在进行非游客操作时需要进行晚上信息，如果绑定了手机号就直接登录）--》也就是微信号绑定手机账户
	 * 2、h5绑定微信号 -->也就是通过手机号去绑定微信号
	 *
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("publicWechatAndMobile")
	public void publicWechatAndMobile(HttpServletRequest req,
									  HttpServletResponse res) throws Exception {

		String redirect_uri = "/xczh/wxlogin/publicWechatAndMobileCallback";
		String userId = req.getParameter("userId");
		if (StringUtils.isNotBlank(userId)) {
			OnlineUser ou = onlineUserService.findUserById(userId);
			if (ou == null) { // 用户信息异常---》直接去登录页面
				res.sendRedirect(returnOpenidUri + "/xcview/html/enter.html");
				return;
			}
			redirect_uri += "?userId=" + userId;
		}
		String strLinkHome = WxPayConst.CODE_URL_3
				+ "?appid="
				+ WxPayConst.gzh_appid
				+ "&redirect_uri="
				+ returnOpenidUri
				+ redirect_uri
				+ "&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect"
				.replace("appid=APPID", "appid=" + WxPayConst.gzh_appid);
		LOGGER.info("strLinkHome:" + strLinkHome);
		res.sendRedirect(strLinkHome);
	}

	/**
	 * 公众号和手机号在h5中的关系 --》微信回调
	 *
	 * Description： 1、h5
	 * 微信第三方登录（如果没有绑定手机号，在进行非游客操作时需要进行晚上信息，如果绑定了手机号就直接登录）--》也就是微信号绑定手机账户
	 * 2、h5绑定微信号 -->也就是通过手机号去绑定微信号
	 *
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("publicWechatAndMobileCallback")
	public void publicWechatAndMobileCallback(HttpServletRequest req,
											  HttpServletResponse res, Map<String, String> params)
			throws Exception {

		LOGGER.info("WX return code:" + req.getParameter("code"));
		LOGGER.info("WX return userId:" + req.getParameter("userId"));
		try {

			OnlineUser currentOnlineUser = appBrowserService
					.getOnlineUserByReq(req);
			String userId = req.getParameter("userId");

			if (currentOnlineUser != null && !StringUtils.isNotBlank(userId)) {
				/**
				 * 先清理下课程存在的账户信息，以当前第三方信息为准
				 */
				UCCookieUtil.clearTokenCookie(res);
				req.getSession().setAttribute("_user_", null);
			}
			LOGGER.info("{}{}{}{}{}{}{}{}{}{}{}{}======userId:"+userId);
			/**
			 * 通过code获取微信信息
			 */
			String code = req.getParameter("code");
			WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,
					wxcpClientUserWxMappingService);
			if (wxw == null) {
				LOGGER.info("微信第三方认证失败 ");
			}
			LOGGER.info("wxxx" + wxw.toString());
			LOGGER.info("openId " + wxw.getOpenid());
			String openId = wxw.getOpenid();
			/**
			 * 如果这个用户信息已经保存进去了，那么就直接登录就ok
			 */
			if (StringUtils.isNotBlank(wxw.getClient_id())) {

				LOGGER.info(" 已经绑定过了:" + wxw.getClient_id());

				if (StringUtils.isNotBlank(userId)) { // type=1 说明这个已经绑定了

					res.sendRedirect(returnOpenidUri
							+ "/xcview/html/lickacc_mobile.html?type=1");
					return;

				} else { // 直接登录

					OnlineUser ou = onlineUserService.findUserById(wxw.getClient_id());
					Token t = userCenterService.loginThirdPart(ou.getLoginName(), TokenExpires.TenDay);

					// 把用户中心的数据给他 这里im都要用到
					ou.setUserCenterId(ou.getId());
					ou.setTicket(t.getTicket());
					onlogin(req, res, t, ou, t.getTicket());
					/**
					 * 清除这个cookie
					 */
					UCCookieUtil.clearThirdPartyCookie(res);

					res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html?openId=" + openId);
					return;
				}

			} else {
				LOGGER.info(" 没有绑定了:");

				if (StringUtils.isNotBlank(userId)) { // 手机号绑定微信号
					/**
					 * 更改微信信息 --》增加用户id
					 */
					wxw.setClient_id(userId);
					wxcpClientUserWxMappingService.update(wxw);
					// type=2 绑定成功
					res.sendRedirect(returnOpenidUri
							+ "/xcview/html/lickacc_mobile.html?type=2");
					return;

				} else { // 此微信号没有绑定，在进入系统后，进行相关操作需要完善信息
					/**
					 * 写入这个cookie
					 */
					ThridFalg tf = new ThridFalg();
					tf.setOpenId(wxw.getOpenid());
					tf.setUnionId(wxw.getUnionid());
					tf.setNickName( StringUtils.isNotBlank(wxw.getNickname()) ? wxw.getNickname() : "熊猫中医" );
					String defaultHeadImg = webdomain+"/web/images/defaultHead/18.png";
					tf.setHeadImg(StringUtils.isNotBlank(wxw.getHeadimgurl()) ? wxw.getHeadimgurl() : defaultHeadImg );
					UCCookieUtil.writeThirdPartyCookie(res, tf);

					LOGGER.info("readThirdPartyCookie{}{}{}{}{}{}"
							+ UCCookieUtil.readThirdPartyCookie(req));

					res.sendRedirect(returnOpenidUri
							+ "/xcview/html/home_page.html?openId=" + openId
							+ "&unionId=" + wxw.getUnionid() + "&jump_type=1");
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//抛异常后，让去登录页面。或者去app看看
			res.sendRedirect(returnOpenidUri + "/xcview/html/enter.html");
		}
	}

	/**
	 * Description：app端第三方登录 ---绑定手机号使用
	 *
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping(value = "appThirdPartyLogin")
	@ResponseBody
	@Transactional
	public ResponseObject addAppThirdparty(HttpServletRequest req,
										   HttpServletResponse res,
										   @RequestParam("accessToken") String accessToken,
										   @RequestParam("openId") String openId,
										   @RequestParam("model") String model) throws Exception {

		LOGGER.info("WX get access_token	:" + accessToken);
		LOGGER.info("WX get openid	:" + openId);

		String userId = req.getParameter("userId");
		if (StringUtils.isNotBlank(userId)) {
			OnlineUser ou = onlineUserService.findUserById(userId);
			if (ou == null) {
				return ResponseObject.newErrorResponseObject("获取用户信息有误");
			}
		}
		LOGGER.info("绑定呢还是解除绑定呢： " + userId);

		Map<String, String> mapRequest = new HashMap<String, String>();
		mapRequest.put("type", ThirdPartyType.WECHAT.getCode() + "");
		try {
			/**
			 * 通过票据和openId获取用户微信信息
			 */
			String user_buffer = CommonUtil.getUserInfo(accessToken, openId);

			JSONObject jsonObject = JSONObject.parseObject(user_buffer);// Map<String,
			// Object>
			// user_info
			// =GsonUtils.fromJson(user_buffer,
			// Map.class);
			String openid_ = (String) jsonObject.get("openid");
			String nickname_ = (String) jsonObject.get("nickname");
			String sex_ = String.valueOf(jsonObject.get("sex"));
			String language_ = (String) jsonObject.get("language");
			String city_ = (String) jsonObject.get("city");
			String province_ = (String) jsonObject.get("province");
			String country_ = (String) jsonObject.get("country");
			String headimgurl_ = (String) jsonObject.get("headimgurl");
			String unionid_ = (String) jsonObject.get("unionid");

			// 防止表情名字
			nickname_ = SLEmojiFilter.filterEmoji(nickname_);
			/**
			 * 用户 unionid_ 表名唯一用户
			 */
			WxcpClientUserWxMapping m = wxcpClientUserWxMappingService
					.getWxcpClientUserByUnionId(unionid_);
			/**
			 * 如果存在，那么就不添加了。
			 */
			if (null == m) {
				WxcpClientUserWxMapping wxcpClientUserWxMapping = new WxcpClientUserWxMapping();
				wxcpClientUserWxMapping.setWx_id(UUID.randomUUID().toString()
						.replace("-", ""));
				wxcpClientUserWxMapping.setWx_public_id(WxPayConst.app_appid);
				wxcpClientUserWxMapping
						.setWx_public_name(WxPayConst.appid4name);
				wxcpClientUserWxMapping.setOpenid(openid_);
				
				wxcpClientUserWxMapping.setNickname(nickname_);
				wxcpClientUserWxMapping.setSex(sex_);
				wxcpClientUserWxMapping.setLanguage(language_);
				wxcpClientUserWxMapping.setCity(city_);
				wxcpClientUserWxMapping.setProvince(province_);
				wxcpClientUserWxMapping.setCountry(country_);
				
				if(!StringUtils.isNotBlank(headimgurl_)) {
					headimgurl_ = webdomain+"/web/images/defaultHead/18.png";
				}
				
				wxcpClientUserWxMapping.setHeadimgurl(headimgurl_);
				wxcpClientUserWxMapping.setProvince(province_);
				wxcpClientUserWxMapping.setUnionid(unionid_);

				if (StringUtils.isNotBlank(userId)) { // 绑定成功
					wxcpClientUserWxMapping.setClient_id(userId);
					mapRequest.put("code",
							UserUnitedStateType.MOBILE_BINDING.getCode() + "");
				} else {
					mapRequest.put("code",
							UserUnitedStateType.UNBOUNDED.getCode() + "");
				}
				wxcpClientUserWxMappingService.insert(wxcpClientUserWxMapping);
				mapRequest.put("unionId", unionid_ + "");

				return ResponseObject.newSuccessResponseObject(mapRequest,
						Integer.parseInt(mapRequest.get("code").toString()));
			} else if (StringUtils.isNotBlank(m.getClient_id())) { // 绑定了用户信息

				if (StringUtils.isNotBlank(userId)) { // 这里说明人家这个已经绑定过其他信息了。我的天
					mapRequest
							.put("code",
									UserUnitedStateType.MOBILE_UNBOUNDED
											.getCode() + "");
					return ResponseObject.newSuccessResponseObject(mapRequest,
							UserUnitedStateType.MOBILE_UNBOUNDED.getCode());
				}

				OnlineUser ou = onlineUserService
						.findUserById(m.getClient_id());
				Token t = userCenterService.loginThirdPart(ou.getLoginName(), TokenExpires.TenDay);
				// 把用户中心的数据给他 --这些数据是IM的
				ou.setUserCenterId(ou.getId());
				ou.setTicket(t.getTicket());
				/**
				 * 直接让登录了 返回状态值：200
				 */
				this.onlogin(req, res, t, ou, t.getTicket());
				return ResponseObject.newSuccessResponseObject(ou,
						UserUnitedStateType.BINDING.getCode());

			} else if (!StringUtils.isNotBlank(m.getClient_id())) {

				if (StringUtils.isNotBlank(userId)) { // 绑定成功
					mapRequest.put("code",
							UserUnitedStateType.MOBILE_BINDING.getCode() + "");
					/**
					 * 更改qq信息 --》增加用户id
					 */
					m.setClient_id(userId);
					wxcpClientUserWxMappingService.update(m);
				} else {
					mapRequest.put("code",
							UserUnitedStateType.UNBOUNDED.getCode() + "");
				}
				mapRequest.put("unionId", unionid_ + "");
				return ResponseObject.newSuccessResponseObject(mapRequest,
						Integer.parseInt(mapRequest.get("code").toString()));
			}
			return ResponseObject.newSuccessResponseObject("");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Description：普通用户微信登录----》得到当前微信号的的openId(为了让微信支付成功)
	 *
	 * @param req
	 * @param res
	 * @param params  entryType 微信公众号入口，entryType 1 表示从登录页面进入首页   2 表示从注册页面进入完善头像页面
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("getCurrentWechatOpenId")
	public void getCurrentWechatOpenId(HttpServletRequest req,
									   HttpServletResponse res,
									   @RequestParam("entryType")Integer entryType) throws Exception {

		if(entryType == null || entryType == 0 ) {
			entryType = 1;
		}
		String strLinkHome = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
				+ WxPayConst.gzh_appid
				+ "&redirect_uri="
				+ returnOpenidUri
				+ "/xczh/wxlogin/getCurrentWechatOpenIdCallback?entryType="+entryType
				+ "&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect";

		LOGGER.info("strLinkHome:" + strLinkHome);
		res.sendRedirect(strLinkHome);

	}

	/**
	 *
	 * Description：普通用户微信登录----》微信回调获取当前手机号的openId
	 *
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 *
	 */
	@RequestMapping("getCurrentWechatOpenIdCallback")
	public void getCurrentWechatOpenIdCallback(HttpServletRequest req,
											   HttpServletResponse res, Map<String, String> params)
			throws Exception {


		ConfigUtil cfg = new ConfigUtil(req.getSession());
		String returnOpenidUri = cfg.getConfig("returnOpenidUri");
		/**
		 * 微信回调后，获取微信信息。
		 */
		String code = req.getParameter("code");
		String entryType = req.getParameter("entryType");
		LOGGER.info("WX return code:" + code);
		LOGGER.info("WX return zidingyi entryType:" + entryType);
		/*
		 * 获取当前微信信息
		 */
		WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,
				wxcpClientUserWxMappingService);

		if(entryType!=null && entryType.equals("2")) {
			res.sendRedirect(returnOpenidUri+ "/xcview/html/heads_nicknames.html?openId="+ wxw.getOpenid());
		}else {
			res.sendRedirect(returnOpenidUri+ "/xcview/html/home_page.html?openId="
					+ wxw.getOpenid());
		}


	}

	/**
	 * 登录成功处理
	 *
	 * @param req
	 * @param res
	 * @param token
	 * @param user
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public void onlogin(HttpServletRequest req, HttpServletResponse res,
						Token token, OnlineUser user, String ticket) throws SQLException {

		LOGGER.info("用户普通登录----》ticket" + ticket);
		/**
		 * 存在两个票，两个票都可以得到用户信息。 然后根据用户信息得到新的票和这个旧的票进行比较就ok了
		 */
		String appUniqueId = req.getParameter("appUniqueId");

		/*
		 * 这个地方是可以了。目前都支持吧
		 */

		if (StringUtils.isNotBlank(appUniqueId)) { // 表示是app登录
			cacheService.set(ticket, user, TokenExpires.TenDay.getExpires());
			cacheService.set(user.getId(), ticket,
					TokenExpires.TenDay.getExpires());
			String model = req.getParameter("model");
			if (StringUtils.isNotBlank(model) && user.getLoginName() != null) {
				cacheService.set(user.getLoginName(), model,
						TokenExpires.TenDay.getExpires());
			} else if (user.getLoginName() != null) {
				cacheService.set(user.getLoginName(), "其他设备",
						TokenExpires.TenDay.getExpires());
			}
		} else {
			// 用户登录成功
			// 第一个BUG的解决:第二个用户登录后将之前的session销毁!
			req.getSession().invalidate();
			// 第二个BUG的解决:判断用户是否已经在Map集合中,存在：已经在列表中.销毁其session.
			// 获得到ServletCOntext中存的Map集合.
			Map<OnlineUser, HttpSession> userMap = (Map<OnlineUser, HttpSession>) req
					.getServletContext().getAttribute("userMap");
			// 判断用户是否已经在map集合中'
			HttpSession session = userMap.get(user);
			if (session != null && userMap.containsKey(user)) {
				/**
				 * * 如果存在那么就注销原来的。或者把原来的session搞成一个表示，不是取用户信息的。 得到客户端信息
				 */
				Map<String, String> mapClientInfo = HttpUtil
						.getClientInformation(req);
				// session.invalidate();
				session.setAttribute("topOff", mapClientInfo);
				session.setAttribute("_user_", null);
			} else if (session != null) {
				session.setAttribute("topOff", null);
			}
			// 使用监听器:HttpSessionBandingListener作用在JavaBean上的监听器.
			req.getSession().setMaxInactiveInterval(86400);// 设置session失效时间
			req.getSession().setAttribute("_user_", user);
			/**
			 * 这是cookie
			 */
			UCCookieUtil.writeTokenCookie(res, token);
		}
	}

	/**
	 * 初始化获取微信token
	 */
	@PostConstruct
	public void initTokenFiter(){

		LOGGER.info("初始化token:");

		new Thread(new TokenThread()).start();
	}
}
