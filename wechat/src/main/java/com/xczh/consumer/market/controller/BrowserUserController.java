package com.xczh.consumer.market.controller;

import java.sql.SQLException;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.Token;
import com.xczh.consumer.market.utils.UCCookieUtil;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.TokenExpires;

/**
 * 用户调用这个接口，进入h5页面模式 ClassName: BrowserUserController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月2日<br>
 */
@Controller
@RequestMapping(value = "/bxg/bs")
public class BrowserUserController {
	@Autowired
	private UserCenterAPI userCenterAPI;
	@Autowired
	private OnlineUserService onlineUserService;
	@Autowired
	private CacheService cacheService;

	@Autowired
	private AppBrowserService appBrowserService;

	@Value("${returnOpenidUri}")
	private String returnOpenidUri;

	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(BrowserUserController.class);

	/**
	 * h5、APP提交注册。微信公众号是没有注册的，直接就登录了
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "phoneRegist")
	@ResponseBody
	@Transactional
	public ResponseObject phoneRegist(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		
		LOGGER.info("老版本方法----》》》》phoneRegist");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
//		String password = req.getParameter("password");
//		String username = req.getParameter("username");
//		String code = req.getParameter("code");
//		if (null == password || null == username || null == code) {
//			return ResponseObject.newErrorResponseObject("参数异常");
//		}
//		String vtype = "1";
//		// 短信验证码
//		ResponseObject checkCode = onlineUserService.checkCode(username, code,
//				Integer.parseInt(vtype));
//		if (!checkCode.isSuccess()) {
//			return checkCode;
//		}
//		OnlineUser ou = onlineUserService.addPhoneRegistByAppH5(req, password,
//				username, Integer.parseInt(vtype));
//		
//		return ResponseObject.newSuccessResponseObject(ou);
	}

	/**
	 * 忘记密码
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "forgotPassword")
	@ResponseBody
	public ResponseObject forgotPassword(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		LOGGER.info("老版本方法----》》》》forgotPassword");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");		
//		String password = req.getParameter("password");
//		String username = req.getParameter("username");
//		String code = req.getParameter("code");
//		String vtype = "2";
//		if (null == password || null == username || null == code) {
//			return ResponseObject.newErrorResponseObject("参数异常");
//		}
//		// 短信验证码
//		ResponseObject checkCode = onlineUserService.checkCode(username, code,
//				Integer.parseInt(vtype));
//		if (!checkCode.isSuccess()) {
//			return checkCode;
//		}
//		// 更新用户密码
//		try {
//			userCenterAPI.updatePassword(username, null, password);
//			return ResponseObject.newSuccessResponseObject("修改密码成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseObject.newErrorResponseObject("修改密码失败");
//		}
	}

	/**
	 * 修改密码
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "editPassword")
	@ResponseBody
	public ResponseObject editPassword(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		LOGGER.info("老版本方法----》》》》editPassword");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
//		String password = req.getParameter("password");
//		String username = req.getParameter("username");
//		String code = req.getParameter("code");
//		String vtype = "2";
//		if (null == password || null == username || null == code) {
//			return ResponseObject.newErrorResponseObject("参数异常");
//		}
//		// 短信验证码
//		ResponseObject checkCode = onlineUserService.checkCode(username, code,
//				Integer.parseInt(vtype));
//		if (!checkCode.isSuccess()) {
//			return checkCode;
//		}
//		// 更新用户密码
//		try {
//			userCenterAPI.updatePassword(username, null, password);
//			return ResponseObject.newSuccessResponseObject("修改密码成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseObject.newErrorResponseObject("修改密码失败");
//		}
	}

	/**
	 * 修改密码
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "editNewPassword")
	@ResponseBody
	public ResponseObject editNewPassword(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
//		String oldPassword = req.getParameter("oldPassword");
//		String newPassword = req.getParameter("newPassword");
//		String username = req.getParameter("username");
//		if (null == oldPassword || null == newPassword) {
//			return ResponseObject.newErrorResponseObject("参数异常");
//		}
//		// 更新用户密码
//		try {
//			userCenterAPI.updatePassword(username, null, newPassword);
//			return ResponseObject.newSuccessResponseObject("修改密码成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseObject.newErrorResponseObject("修改密码失败");
//		}
	}

	/**
	 * 验证密码是否一致
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "checkPassword")
	@ResponseBody
	public ResponseObject checkPassword(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
		
		//		String oldPassword = req.getParameter("oldPassword");
//		String username = req.getParameter("username");
//		if (null == oldPassword) {
//			return ResponseObject.newErrorResponseObject("参数异常");
//		}
//		// 更新用户密码
//		try {
//			userCenterAPI.checkPassword(username, oldPassword);
//			return ResponseObject.newSuccessResponseObject("密码一致");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseObject.newErrorResponseObject("密码一致");
//		}
	}

	/**
	 * Description：判断此用户有没有注册过
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
	@RequestMapping("isReg")
	@ResponseBody
	public ResponseObject isReg(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
//		ResponseObject rob = new ResponseObject();
//		String username = req.getParameter("username");
//		Map<String, String> mapReq = new HashMap<String, String>();
//		try {
//			ItcastUser user = userCenterAPI.getUser(username);
//			int code = 202;
//			if (null == user) {
//				code = 200;
//			} else {
//				/**
//				 * 需要查询下这个onlineuser表中是否存在unionid 如果存在那么就说明这个你这个用户已经绑定过了。
//				 */
//				OnlineUser ou = onlineUserService.findUserByLoginName(username);
//				if (null != ou) {
//					String unionId = ou.getUnionId();
//					if (null != unionId && !"".equals(unionId)) {
//						code = 201;
//					} else {
//						code = 202;
//					}
//				} else {
//					/**
//					 * 当用户中心有用户信息时，但是用户表中没有信息，那么就有问题啦。 这里做下容错处理。
//					 */
//					boolean ise = Pattern.matches(
//							"^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$",
//							username);
//					if (ise) {
//						// 如果是正常的手机号
//						// 这个地方会返回这个用户的微吼id和名字
//						OnlineUser newUser = onlineUserService.addUser(
//								username, user.getNike_name(), null,
//								user.getPassword());
//						code = 202;
//					}
//				}
//			}
//			if (code == 200) {
//				rob.setCode(200);
//				rob.setSuccess(true);
//				mapReq.put("code", 200 + "");
//				mapReq.put("errorMessage", "此用户不存在");
//				rob.setResultObject(mapReq);
//				return rob;
//			} else if (code == 201) {
//				rob.setCode(201);
//				rob.setSuccess(true);
//				mapReq.put("code", 201 + "");
//				mapReq.put("errorMessage", "该手机号已经绑定其他微信帐号");
//				rob.setResultObject(mapReq);
//				return rob;
//			} else {
//				rob.setCode(202);
//				rob.setSuccess(true);
//				mapReq.put("code", 202 + "");
//				mapReq.put("errorMessage", "此手机号存在，可绑定微信号");
//				rob.setResultObject(mapReq);
//				return rob;
//			}
//		} catch (Exception e) {
//			rob.setCode(200);
//			rob.setSuccess(true);
//			mapReq.put("code", 202 + "");
//			mapReq.put("errorMessage", "服务器异常");
//			rob.setResultObject(mapReq);
//			return rob;
//		}
	}

	/**
	 * Description：浏览器端登录
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
	@RequestMapping("login")
	@ResponseBody
	@Transactional
	public ResponseObject login(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
		//		String username = req.getParameter("username");
//		String password = req.getParameter("password");
//		/**
//		 * 如果是微信浏览器普通登录的话，那么就需要自动获取下他的openId了
//		 */
//		if (null == username || null == password) {
//			return ResponseObject.newErrorResponseObject("参数异常");
//		}
//		Token t = null;
//		try {
//			// 存储在redis中了，有效期为10天。
//			t = userCenterAPI.loginMobile(username, password,
//					TokenExpires.TenDay);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseObject.newErrorResponseObject("用户名密码有误");
//		}
//		if (t != null) {
//			OnlineUser o = onlineUserService.findUserByLoginName(username);
//			if (o != null) {
//				// 判断是否存在微吼信息
//				if (o.getVhallId() == null || "".equals(o.getVhallId())) {
//					String weihouId = WeihouInterfacesListUtil.createUser(
//							o.getId(), WeihouInterfacesListUtil.MOREN,
//							o.getName(), o.getSmallHeadPhoto());
//					onlineUserService.updateVhallIdOnlineUser(weihouId,
//							WeihouInterfacesListUtil.MOREN, o.getName(),
//							o.getId());
//					o.setVhallId(weihouId);
//					o.setVhallPass(WeihouInterfacesListUtil.MOREN);
//					o.setVhallName(o.getName());
//				}
//				ItcastUser user = userCenterAPI.getUser(username);
//				if (o.isDelete() || o.getStatus() == -1) {
//					return ResponseObject.newErrorResponseObject("用户已禁用");
//				}
//				// 把用户中心的数据给他 这里im都要用到
//				o.setUserCenterId(user.getId());
//				o.setPassword(user.getPassword());
//				// 把这个票给前端
//				o.setTicket(t.getTicket());
//
//				this.onlogin(req, res, t, o, t.getTicket());
//				// 这个也存放在redis中吧
//				return ResponseObject.newSuccessResponseObject(o);
//			} else {
//				boolean ise = Pattern
//						.matches(
//								"^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$",
//								username);
//				if (ise) {
//					ItcastUser user = userCenterAPI.getUser(username);
//					// 这个地方会返回这个用户的微吼id和名字
//					OnlineUser newUser = onlineUserService.addUser(username,
//							user.getNike_name(), null, password);
//					newUser.setPassword(user.getPassword());
//					// 把这个票给前端
//					newUser.setTicket(t.getTicket());
//					// 把用户中心的数据给他
//					newUser.setUserCenterId(user.getId());
//					newUser.setPassword(user.getPassword());
//					this.onlogin(req, res, t, newUser, t.getTicket());
//					return ResponseObject.newSuccessResponseObject(newUser);
//				}
//			}
//			return ResponseObject.newErrorResponseObject("在线系统不存在此用户");
//		} else {
//			return ResponseObject.newErrorResponseObject("用户名密码错误");
//		}
	}

	@RequestMapping(value = "/index")
	public void index(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

//		Cookie[] c = req.getCookies();
//		for (Cookie cookie : c) {
//			LOGGER.info("cookieName+" + cookie.getName());
//		}
//		Cookie cookie = new Cookie("nihao", "nihao");
//		cookie.setDomain("localhost");
//		cookie.setPath("/");
//		cookie.setMaxAge(60 * 60);
//		// 应该改变来修改浏览器的cookie
//		res.addCookie(cookie);
//
//		cookie = CookieUtil.getCookie(req, "nihao");
//		if (cookie != null) {
//			LOGGER.info("cookie:" + cookie.getValue());
//		}

		// req.setAttribute("access", "brower");
		// //存储redis
		// Token token = new Token();
		// // 票的生成策略用UUID
		// String ticket = CodeUtil.getRandomUUID();
		// token.setTicket(ticket);
		// token.setEmail("453099975");
		// long time = System.currentTimeMillis() + 10 * 1000;
		// token.setExpires(time);
		// cacheService.set(ticket, token, 1*1000);
		// LOGGER.info("存储成功");
		// token = cacheService.get(token.getTicket());
		// LOGGER.info(token.getEmail());
		// String str = cacheService.get("123");
		// LOGGER.info("str:"+str);
		// LOGGER.info("取数据成功");
		// req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, res);
	}

	/**
	 * Description：app端第三方登录
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
	@RequestMapping(value = "addGetUserInfoByCode")
	@ResponseBody
	@Transactional
	public ResponseObject addGetUserInfoByCode(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		
		LOGGER.info("WX get openid:" + req.getParameter("openid"));
		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
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
			HttpServletResponse res) throws Exception {

		LOGGER.info("WX get access_token	:" + req.getParameter("access_token"));
		LOGGER.info("WX get openid	:" + req.getParameter("openid"));

		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
	}

	@RequestMapping(value = "getBalanceTotal")
	@ResponseBody
	public ResponseObject getBalanceTotal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
	}

	/**
	 * Description：给 vtype 包含 3 和 4 发送短信。 如果是3的话，需要判断此手机号是否绑定，在发短信。
	 * 如果是4的话，需要判断此要更改的手机号是否绑定，如果绑定就不发短信了。
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping(value = "phoneCheck")
	@ResponseBody
	public ResponseObject phoneCheck(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
//		String username = req.getParameter("username");
//		// username = "13723160793";
//		String vtype = req.getParameter("vtype");
//		if (null == vtype || null == username) {
//			return ResponseObject.newErrorResponseObject("参数异常");
//		}
//		// 短信验证码
//		String str = onlineUserService.changeMobileSendCode(username,
//				Integer.parseInt(vtype));
//		try {
//			if ("发送成功".equals(str)) {
//				return ResponseObject.newSuccessResponseObject(str);
//			} else {
//				return ResponseObject.newErrorResponseObject(str);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			LOGGER.info("获取错误信息啦" + e.getMessage());
//			return ResponseObject.newErrorResponseObject("发送失败");
//		}
	}

	/**
	 * Description：验证 3 的手机号是否相同
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping(value = "phoneCheckAndCode")
	@ResponseBody
	public ResponseObject phoneCheckAndCode(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
//		String username = req.getParameter("username");
//		String code = req.getParameter("code");
//		String vtype = req.getParameter("vtype");
//		if (null == vtype || null == username || null == code) {
//			return ResponseObject.newErrorResponseObject("参数异常");
//		}
//		// 短信验证码
//		ResponseObject checkCode = onlineUserService.changeMobileCheckCode(
//				username, code, Integer.parseInt(vtype));
//
//		return checkCode;
	}
	/**
	 * Description：更换手机号 --
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping(value = "updatePhone")
	@ResponseBody
	public ResponseObject updatePhone(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		String oldUsername = req.getParameter("oldUsername");
		String newUsername = req.getParameter("newUsername");
		String code = req.getParameter("code");
		String vtype = req.getParameter("vtype");
		if (null == vtype || null == oldUsername || null == newUsername) {
			return ResponseObject.newErrorResponseObject("参数异常");
		}
		// 短信验证码
		ResponseObject checkCode = onlineUserService.changeMobileCheckCode(
				newUsername, code, Integer.parseInt(vtype));
		if (!checkCode.isSuccess()) {
			return checkCode;
		}
		/*
		 * 如果正确的话
		 */
		// 更新用户信息 --
		userCenterAPI.updateLoginName(oldUsername, newUsername);
		// 更新用户表中的密码
		OnlineUser o = onlineUserService.findUserByLoginName(oldUsername);
		o.setLoginName(newUsername);
		onlineUserService.updateUserLoginName(o);

		return ResponseObject.newSuccessResponseObject("更改手机号成功");
	}

	// 是否主播
	@RequestMapping(value = "isLecturer")
	@ResponseBody
	public ResponseObject isLecturer(HttpServletRequest request) {
		OnlineUser user = appBrowserService.getOnlineUserByReq(request); // onlineUserMapper.findUserById("2c9aec345d59c9f6015d59caa6440000");
		if (user == null) {
			throw new RuntimeException("登录失效");
		}
		try {
			String userId = request.getParameter("userId");
			if (!StringUtils.isNotBlank(userId)) {
				userId = user.getId();
			}
			OnlineUser onlineUser = onlineUserService.findUserById(userId);
			return ResponseObject.newSuccessResponseObject(onlineUser
					.getIsLecturer());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * Description：通过用户id得到用户信息，返回给前台
	 * 
	 * @param request
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 *
	 */
	@RequestMapping(value = "getUserInfoById")
	@ResponseBody
	public ResponseObject getUserInfoById(HttpServletRequest request) {

		String userId = request.getParameter("userId");
		try {
			OnlineUser onlineUser = onlineUserService.findUserById(userId);
			return ResponseObject.newSuccessResponseObject(onlineUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Description：
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("appLogin")
	@ResponseBody
	public ResponseObject appOnlyOneId(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

//		String appUniqueId = req.getParameter("appUniqueId");
//		String token = req.getParameter("token");
//		String type = req.getParameter("type");
		
		return ResponseObject.newErrorResponseObject("请使用最新版本");
		
//		/**
//		 * 如果存在这个token。那么说明这个token可以的啦。就不用用appUniqueId来判断了
//		 * 直接中缓存中得到这个token。如果这个token没有或者失效了。那么就中数据库中取下。 在从数据库中去的时候
//		 */
//		// cacheService.delete("ba525056ebfa48dfb33974e7ab92d7a5");
//		OnlineUser ou = new OnlineUser();
//		if (token != null && cacheService.get(token) != null) {
//			ou = cacheService.get(token);
//			return ResponseObject.newSuccessResponseObject(ou);
//		}
//		/*
//		 * 1、判断是游客登录呢，还是普通用户登录
//		 */
//		Map<String, Object> map = onlineUserService
//				.getAppTouristRecord(appUniqueId);
//		if (map == null) { // 第一次进来
//			ou = onlineUserService.addYkUser(appUniqueId);
//			LOGGER.info("用户中心id:" + ou.getUserCenterId());
//			// 也需要保存这个信息啦
//			onlineUserService.saveAppTouristRecord(ou, appUniqueId);
//		} else {
//			/**
//			 * 1、点击退出的时候，在数据库中变化一个值 如果这个值是1，说明它是退出登录了。在进入的时候我返回他一些基本的信息。
//			 * 如果这个是是0,。进入的时候我返回他更过的信息。 2、当登录的时候，在增加这个标识符为0:
//			 */
//			Boolean regis = (Boolean) map.get("isRegis");
//			LOGGER.info("userCenterId;" + map.get("userCenterId"));
//			ItcastUser iu = userCenterAPI.getUser(appUniqueId);
//			if (!regis || "1".equals(type)) { // 返回用户基本信息 --主要是不返回loginName
//				ou = onlineUserService.findUserByIdAndVhallNameInfo(map.get(
//						"userId").toString());
//			} else { // 返回用户信息 -- 包括loginName
//				ou = onlineUserService.findUserById(map.get("userId")
//						.toString());
//			}
//			if (iu != null) {
//				ou.setUserCenterId(iu.getId());
//				ou.setPassword(iu.getPassword());
//			}
//		}
//
//		/**
//		 * 游客身份进入后，存缓存到redis中。也就是这个票 通过这个票进行各种身份通过，对出登录删除这个票就行了。
//		 * 在以游客的身份过来的话，就判断这个票有效无效。如果无效就在生成这个票。如果有效，就还用这个票吧。
//		 */
//		String ticket = CodeUtil.getRandomUUID();
//		ou.setTicket(ticket);
//		cacheService.set(ticket, ou, TokenExpires.Day.getExpires());
//		return ResponseObject.newSuccessResponseObject(ou);
	}

	public void appleOnlogin(HttpServletRequest req, HttpServletResponse res,
			Token token, OnlineUser user, String ticket) {

		cacheService.set(ticket, user, TokenExpires.Day.getExpires());
		cacheService.set(user.getId(), ticket, TokenExpires.Day.getExpires());
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

		// if(StringUtils.isNotBlank(appUniqueId)){ //表示是app登录
		// 设置登录标识
		//onlineUserService.updateAppleTourisrecord(appUniqueId, 1);
		cacheService.set(ticket, user, TokenExpires.TenDay.getExpires());
		cacheService
				.set(user.getId(), ticket, TokenExpires.TenDay.getExpires());
		// Map<String,String> mapClientInfo =
		// com.xczh.consumer.market.utils.HttpUtil.getClientInformation(req);
		String model = req.getParameter("model");
		if (StringUtils.isNotBlank(model) && user.getLoginName() != null) {
			cacheService.set(user.getLoginName(), model,
					TokenExpires.TenDay.getExpires());
		} else if (user.getLoginName() != null) {
			cacheService.set(user.getLoginName(), "其他设备",
					TokenExpires.TenDay.getExpires());
		}
		// }else{
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
		// }
	}

	/**
	 * apple用户提交注册
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "applePhoneRegist")
	@ResponseBody
	public ResponseObject applePhoneRegist(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		
		return ResponseObject.newErrorResponseObject("请使用最新版本");
//		String appUniqueId = req.getParameter("appUniqueId");
//		String password = req.getParameter("password");
//		String username = req.getParameter("username");
//		String code = req.getParameter("code");
//		if (null == password || null == username || null == code) {
//			return ResponseObject.newErrorResponseObject("参数异常");
//		}
//		String vtype = "1";
//		// 短信验证码
//		ResponseObject checkCode = onlineUserService.checkCode(username, code,
//				Integer.parseInt(vtype));
//		if (!checkCode.isSuccess()) {
//			return checkCode;
//		}
//		return onlineUserService.updateIPhoneRegist(req, password, username,
//				Integer.parseInt(vtype), appUniqueId);
	}

	public static void main(String[] args) {
		int[] arr = { 1, 2, 3, 4 };
		// 产生0-(arr.length-1)的整数值,也是数组的索引
		LOGGER.info(Math.random() + "===" + Math.random() * arr.length);
		int index = (int) (Math.random() * arr.length);
		LOGGER.info(index + "");
		int rand = arr[index];
		LOGGER.info(rand + "");
	}
}
