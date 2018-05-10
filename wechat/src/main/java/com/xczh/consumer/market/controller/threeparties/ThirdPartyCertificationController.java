package com.xczh.consumer.market.controller.threeparties;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xczhihui.user.center.bean.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.user.center.web.utils.UCCookieUtil;
import com.xczhihui.common.util.enums.CommonEnumsType;
import com.xczhihui.common.util.enums.SMSCode;
import com.xczhihui.common.util.enums.ThirdPartyType;
import com.xczhihui.common.util.enums.UserUnitedStateType;
import com.xczhihui.online.api.service.UserCoinService;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.course.model.QQClientUserMapping;
import com.xczhihui.course.model.WeiboClientUserMapping;
import com.xczhihui.course.service.IThreePartiesLoginService;
import com.xczhihui.course.util.XzStringUtils;

import weibo4j.http.HttpClient;

/**
 * 
 * ClassName: ThirdPartyCertificationController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
@Controller
@RequestMapping(value = "/xczh/third")
public class ThirdPartyCertificationController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(ThirdPartyCertificationController.class);

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


	// 手机端登录使用
	@Value("${mobile.authorizeURL}")
	public String weiboMobileAuthorizeURL;

	/**
	 * 
	 * Description：微信、微博、qq绑定 已经注册的手机号
	 * 
	 * @param req
	 * @param res
	 *            手机号
	 * @param unionId
	 *            第三方唯一标识（微信的、微博的、qq的）
	 * @param code
	 *            短信验证码
	 * @param type
	 *            绑定类型 1 微信 2qq 3微博
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 *
	 */
	@RequestMapping("thirdPartyBindIsNoMobile")
	@ResponseBody
	@Transactional
	public ResponseObject thirdPartyBindThereAreMobile(HttpServletRequest req,
			HttpServletResponse res, @RequestParam("userName") String userName,
			@RequestParam("unionId") String unionId,
			@RequestParam("code") String code,
			@RequestParam("type") Integer type) throws Exception {

		Integer vtype = SMSCode.FORGOT_PASSWORD.getCode();
		LOGGER.info("三方绑定已注册手机认证参数信息：" + "username:" + userName + ",unionId:"
				+ unionId + ",code:" + code + ",type:" + type);
		/*
		 * 验证短信验证码
		 */
		ResponseObject checkCode = onlineUserService.checkCode(userName, code,vtype);
		if (!checkCode.isSuccess()) { // 如果动态验证码不正确
			return checkCode;
		}

		LOGGER.info(">>>>>>>>>>>>>>>>>>验证码认证成功");

		OnlineUser ou = onlineUserService.findUserByLoginName(userName);
		if (ou == null) {
			return ResponseObject.newErrorResponseObject("该手机号暂未注册,请输入密码");
		}

		/*
		 * 一个人可能有多个qq号，获取多个微博号。 所以要改下了 更应该拿着这个id去查了
		 */
		switch (type) {

		case 1: // 微信

			LOGGER.info(">>>>>>>>>>>>>>>>>>数据来源：微信");

			WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserByUnionId(unionId);
		
			if(m==null) {
				return ResponseObject.newErrorResponseObject
						(CommonEnumsType.WEIBO_USERINFO_NOFOUND.getText(),
								CommonEnumsType.WEIBO_USERINFO_NOFOUND.getCode());
			}
			
			m.setClient_id(ou.getId());
			wxcpClientUserWxMappingService.update(m);

			// 清理cookie
			UCCookieUtil.clearThirdPartyCookie(res);

			break;
		case 2: // qq

			LOGGER.info(">>>>>>>>>>>>>>>>>>数据来源：qq");

			QQClientUserMapping qq = threePartiesLoginService.selectQQClientUserMappingByOpenId(unionId);
			
			if(qq==null) {
				return ResponseObject.newErrorResponseObject
						(CommonEnumsType.QQ_USERINFO_NOFOUND.getText(),
								CommonEnumsType.QQ_USERINFO_NOFOUND.getCode());
			}
			
			qq.setUserId(ou.getId());
			threePartiesLoginService.updateQQInfoAddUserId(qq);
			break;
		case 3: // 微博

			LOGGER.info(">>>>>>>>>>>>>>>>>>数据来源：微博");

			WeiboClientUserMapping weibo = threePartiesLoginService
					.selectWeiboClientUserMappingByUid(unionId);
			
			if(weibo==null) {
				return ResponseObject.newErrorResponseObject
						(CommonEnumsType.WEIBO_USERINFO_NOFOUND.getText(),
								CommonEnumsType.WEIBO_USERINFO_NOFOUND.getCode());
			}
			
			weibo.setUserId(ou.getId());
			threePartiesLoginService.updateWeiboInfoAddUserId(weibo);
			break;
		default:
			LOGGER.info(">>>>>>>>>>>>>>>>>>第三方登录类型有误");
			break;
		}

		LOGGER.info(">>>>>>>>>>>>>>>>>>第三方信息注入用户信息成功");

		ItcastUser iu = userCenterAPI.getUser(userName);
		Token t = userCenterAPI.loginThirdPart(userName, iu.getPassword(),
				TokenExpires.TenDay);
		ou.setTicket(t.getTicket());
		// 把用户中心的数据给他 --这些数据是IM的
		ou.setUserCenterId(iu.getId());
		ou.setPassword(iu.getPassword());
		this.onlogin(req, res, t, ou, t.getTicket());

		LOGGER.info(">>>>>>>>>>>>>>>>>>绑定信息成功，默认登录");

		return ResponseObject.newSuccessResponseObject(ou);
	}

	/**
	 * 微博、qq绑定 未被注册的 --》增加用户信息，并且用户信息存在用默认的第三方登录的名字和头像 Description：
	 * 
	 * @param req
	 * @param res
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("thirdPartyBindMobile")
	@ResponseBody
	@Transactional
	public ResponseObject thirdPartyBindMobile(HttpServletRequest req,
			HttpServletResponse res, @RequestParam("userName") String userName,
			@RequestParam("passWord") String passWord,
			@RequestParam("unionId") String unionId,
			@RequestParam("code") String code,
			@RequestParam("type") Integer type) throws Exception {

		LOGGER.info("三方绑定未注册手机认证参数信息：" + "username:" + userName + ",unionId:"
				+ unionId + ",code:" + code + ",type:" + type + "password:"
				+ passWord);

		if (!XzStringUtils.checkPhone(userName)) {
			return ResponseObject.newErrorResponseObject("请输入正确的手机号");
		}
		if (!XzStringUtils.checkPassword(passWord)) {
			return ResponseObject.newErrorResponseObject("密码为6-18为英文大小写字母或者阿拉伯数字");
		}

		/*
		 * 验证短信验证码
		 */
		Integer vtype = SMSCode.RETISTERED.getCode();
		ResponseObject checkCode = onlineUserService.checkCode(userName, code,
				vtype);
		if (!checkCode.isSuccess()) { // 如果动态验证码不正确
			return checkCode;
		}

		// 先假设验证码正确
		LOGGER.info(">>>>>>>>>>>>>>>>>>验证码认证成功");
		/**
		 * 在线用户数据
		 */
		OnlineUser ou = onlineUserService.findUserByLoginName(userName);
		if (ou == null) {
			ou = new OnlineUser();
			ou.setId(UUID.randomUUID().toString().replace("-", ""));
		} else {
			return ResponseObject.newErrorResponseObject("该手机号已经注册不用重新输入密码");
		}
		/**
		 * 判断
		 */
		String nickName = "";
		Integer sex = 0;

		switch (type) {
		case 1: // 微信
			LOGGER.info(">>>>>>>>>>>>>>>>>>数据来源：微信");
			WxcpClientUserWxMapping m = wxcpClientUserWxMappingService
					.getWxcpClientUserByUnionId(unionId);
			nickName = m.getNickname();
			if ("1".equals(m.getSex())) { // 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
				sex = 1;
			} else if ("2".equals(m.getSex())) {
				sex = 0;
			} else {
				sex = 2;
			}
			ou.setSex(sex);
			ou.setName(m.getNickname()); // 微信名字
			ou.setSmallHeadPhoto(m.getHeadimgurl());// 微信头像
			ou.setUserType(2); // 微信用户
			/*
			 * 保存userId 到第三方信息中
			 */
			m.setClient_id(ou.getId());
			wxcpClientUserWxMappingService.update(m);
			break;
		case 2: // qq
			LOGGER.info(">>>>>>>>>>>>>>>>>>数据来源：qq");

			// 那么这里是不是就不能用这个id了啊，需要用unionid

			QQClientUserMapping qq = threePartiesLoginService
					.selectQQClientUserMappingByOpenId(unionId);
			nickName = qq.getNickname();
			if ("男".equals(qq.getGender())) { // 性别。 如果获取不到则默认返回"男"
				sex = 1;
			} else {
				sex = 0;
			}
			ou.setSex(sex);
			ou.setName(qq.getNickname()); // qq名字
			ou.setSmallHeadPhoto(qq.getFigureurl1()); // qq头像
			ou.setUserType(1); // qq用户
			/*
			 * 保存userId 到第三方信息中
			 */
			qq.setUserId(ou.getId());
			threePartiesLoginService.updateQQInfoAddUserId(qq);
			break;
		case 3: // 微博

			LOGGER.info(">>>>>>>>>>>>>>>>>>数据来源：微博");

			WeiboClientUserMapping weibo = threePartiesLoginService
					.selectWeiboClientUserMappingByUid(unionId);
			nickName = weibo.getScreenName();
			if ("m".equals(weibo.getGender())) { // 性别，m：男、f：女、n：未知
				sex = 1;
			} else if ("f".equals(weibo.getGender())) {
				sex = 0;
			} else {
				sex = 2;
			}
			ou.setSex(sex);
			ou.setName(weibo.getName()); // 微博名字
			ou.setSmallHeadPhoto(weibo.getProfileImageUrl()); // 微博头像
			ou.setUserType(3); // 微博用户

			weibo.setUserId(ou.getId());
			threePartiesLoginService.updateWeiboInfoAddUserId(weibo);
			break;
		default:
			LOGGER.info("第三方登录类型有误");
			break;
		}
		/*
		 * 用户中心的
		 */
		ItcastUser iu = userCenterAPI.getUser(userName);
		if (iu == null) {
			userCenterAPI.regist(userName, passWord, nickName,
					UserSex.parse(sex), null, null, UserType.COMMON,
					UserOrigin.ONLINE, UserStatus.NORMAL);
			iu = userCenterAPI.getUser(userName);
		}
		ou.setLoginName(userName);
		ou.setMobile(userName);
		ou.setPassword(passWord);
		ou.setVisitSum(0);
		ou.setStayTime(0);

		onlineUserService.addOnlineUser(ou);
		/**
		 * 初始化一条代币记录
		 */
		userCoinService.saveUserCoin(ou.getId());
		Token t = userCenterAPI.loginThirdPart(userName, iu.getPassword(),
				TokenExpires.TenDay);
		ou.setTicket(t.getTicket());

		// 把用户中心的数据给他 --这些数据是IM的
		ou.setUserCenterId(iu.getId());
		ou.setPassword(iu.getPassword());
		/**
		 * 增加缓存信息
		 */
		onlogin(req, res, t, ou, t.getTicket());

		return ResponseObject.newSuccessResponseObject(ou);
	}

	/**
	 * Description：验证-->手机号 是否已经绑定了微信号、qq 或者 微博号
	 * 
	 * @param req
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping(value = "thirdCertificationMobile")
	@ResponseBody
	public ResponseObject thirdCertificationMobile(HttpServletRequest req,
			HttpServletResponse res, @RequestParam("userName") String userName,
			@RequestParam("unionId") String unionId,
			@RequestParam("type") Integer type) {

		try {

			ItcastUser user = userCenterAPI.getUser(userName);
			OnlineUser ou = onlineUserService.findUserByLoginName(userName);
			int code = UserUnitedStateType.PNHONE_NOT_THERE_ARE.getCode();
			if (null == user && null == ou) {
				code = UserUnitedStateType.PNHONE_NOT_THERE_ARE.getCode();
			} else {
				
				Object obj = null;
				/*
				 * 已注册手机号,判断手机号是否已经绑定了。
				 */

				LOGGER.info(">>>>>>>>>>>>>>>>>>数据来源：userName  " + userName
						+ "unionId" + unionId + ",type" + type);

				if (type == ThirdPartyType.WECHAT.getCode()) { // 微信

					obj = wxcpClientUserWxMappingService
							.getWxcpClientUserWxMappingByUserId(ou.getId());

				} else if (type == ThirdPartyType.QQ.getCode()) {// QQ
					obj = threePartiesLoginService
							.selectQQClientUserMappingByUserId(ou.getId());
				} else if (type == ThirdPartyType.WEIBO.getCode()) {// 微博

					obj = threePartiesLoginService
							.selectWeiboClientUserMappingByUserId(ou.getId());
				}
				LOGGER.info("obj    " + obj);
				if (obj == null) { // 已注册手机号,但是未绑定,可进行判断操作
					code = UserUnitedStateType.PNHONE_IS_WRONG.getCode();
				} else {
					code = UserUnitedStateType.PNHONE_BINDING.getCode();
				}
			}
			return ResponseObject.newSuccessResponseObject(UserUnitedStateType.valueOf(code), code);
		} catch (Exception e) {
			return ResponseObject.newErrorResponseObject("数据有误");
		}
	}

	/**
	 * Description：h5 绑定手机号，更换用户信息（手机号）
	 * 
	 * @param req
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 * @throws Exception 
	 */
	@RequestMapping(value = "h5WechatMobile")
	@ResponseBody
	public ResponseObject h5WechatMobile(HttpServletRequest req,
			HttpServletResponse res, @RequestParam("userName") String userName,
			@RequestParam("passWord") String passWord,
			@RequestParam("unionId") String unionId,
			@RequestParam("code") String code,
			@RequestParam("vtype") Integer vtype) throws Exception {

		ItcastUser user = userCenterAPI.getUser(unionId);
		OnlineUser ou = onlineUserService.findUserByLoginName(unionId);
		if (user == null || ou == null) {
			return ResponseObject.newErrorResponseObject("未找到用户信息");
		}
		LOGGER.info("三方绑定已注册手机认证参数信息：" + "username:" + userName + ",unionId:"
				+ unionId + ",code:" + code + ",type:" + vtype);
		/*
		 * 验证短信验证码
		 */
		ResponseObject checkCode = onlineUserService.checkCode(userName, code,
				vtype);
		if (!checkCode.isSuccess()) { // 如果动态验证码不正确
			return checkCode;
		}
		LOGGER.info(">>>>>>>>>>>>>>>>>>验证码认证成功");

		/**
		 * 更改微信的登录名
		 */
		userCenterAPI.updatePasswordAndLoginName(user.getId(), userName,
				passWord);

		ou.setLoginName(userName);
		onlineUserService.updateOnlineUserAddPwdAndUserName(ou);
		/**
		 * 微信连接这个用户
		 */
		WxcpClientUserWxMapping wxw = wxcpClientUserWxMappingService.getWxcpClientUserByUnionId(unionId);
		if(wxw==null) {
			return ResponseObject.newErrorResponseObject
					(CommonEnumsType.WECHAT_USERINFO_NOFOUND.getText(),
							CommonEnumsType.WECHAT_USERINFO_NOFOUND.getCode());
		}
		
		
		wxw.setClient_id(ou.getId());
		wxcpClientUserWxMappingService.update(wxw);
		/**
		 * 清除这个cookie
		 */
		UCCookieUtil.clearThirdPartyCookie(res);
		/**
		 * 修改缓存信息
		 */
		req.getSession().setAttribute("_user_", ou);

		// 返回用户信息
		return ResponseObject.newSuccessResponseObject(ou);

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
				Map<String, String> mapClientInfo = com.xczh.consumer.market.utils.HttpUtil
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
}
