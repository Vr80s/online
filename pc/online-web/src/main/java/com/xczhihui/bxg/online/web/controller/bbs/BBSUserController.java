package com.xczhihui.bxg.online.web.controller.bbs;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.support.service.impl.RedisCacheService;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.base.controller.OnlineBaseController;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.utils.RandomUtil;
import com.xczhihui.bxg.online.web.base.common.Constant;
import com.xczhihui.bxg.online.web.base.common.OnlineResponse;
import com.xczhihui.bxg.online.web.base.utils.UserUtil;
import com.xczhihui.bxg.online.web.base.utils.VhallUtil;
import com.xczhihui.bxg.online.web.service.*;
import com.xczhihui.bxg.online.web.service.impl.OnlineLoginoutCallback;
import com.xczhihui.bxg.online.web.utils.HttpUtil;
import com.xczhihui.bxg.online.web.utils.MD5Util;
import com.xczhihui.bxg.online.web.utils.ThirdConnectionConfig;
import com.xczhihui.bxg.online.web.vo.ApplyVo;
import com.xczhihui.bxg.online.web.vo.UserDataVo;
import com.xczhihui.bxg.online.web.vo.UserVo;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.ItcastUser;
import com.xczhihui.user.center.bean.Token;
import com.xczhihui.user.center.bean.TokenExpires;
import com.xczhihui.user.center.utils.CodeUtil;
import com.xczhihui.user.center.web.utils.CookieUtil;
import com.xczhihui.user.center.web.utils.UCCookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 用户相关
 * @author Haicheng Jiang
 */
@Controller
@RequestMapping(value = "/online/bbs/user")
public class BBSUserController extends OnlineBaseController {

	@Autowired
	private UserService service;

	@Autowired
	private OnlineUserCenterService userCenterService;

	@Autowired
	private UserCenterAPI userCenterAPI;

	@Autowired
	private RedisCacheService cacheService;
	
	@Autowired
	private OnlineLoginoutCallback callback;
	
	@Autowired
	private ActivityTotalService totalService;

	@Autowired
	private UserCenterAPI api;

	@Autowired
	private MessageService messageService;

	@Autowired
	private ShoppingCartService shoppingCartService;


	@Value("${domain}")
	private String domain;
	
	@RequestMapping(value = "login")
	public ResponseObject login(String username, String password,HttpServletRequest request,HttpServletResponse response) {
//		Token t = userCenterAPI.loginForLimit(username, password,TokenExpires.Day,1,info);
		try {
			OnlineUser o = service.findUserByLoginName(username);
			Token t = userCenterAPI.login4BBS(username, password,o.getSmallHeadPhoto(),o.getId(),TokenExpires.Day);
			if (t != null) {
				if (o != null) {
					t.setHeadPhoto(o.getSmallHeadPhoto());
					t.setUuid(o.getId());
					if (o.isDelete() || o.getStatus() == -1){
						return ResponseObject.newErrorResponseObject("用户已禁用");
					}
					if(o.getVhallId()==null){
						String vhallPassword = RandomUtil.getCharAndNumr(6);
						String vhallId = VhallUtil.createUser(o,vhallPassword);
						o.setVhallId(vhallId);
						o.setVhallPass(vhallPassword);
						o.setVhallName(o.getId());
						service.updateVhallInfo(o);
					}
				} else {
					//本系统没有此用户，说明此用户来自熊猫中医其他系统，新增此用户
					boolean ism = Pattern.matches("^((1[0-9]))\\d{9}$", username);
					boolean ise = Pattern.matches("^([a-z0-9A-Z]+[-_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",username);
					if (!ism && !ise) {
						return ResponseObject.newErrorResponseObject("请用手机或邮箱登录");
					}

					ItcastUser u = this.userCenterAPI.getUser(username);
					o = new OnlineUser();
					o.setLoginName(u.getLoginName());
					o.setName(u.getNikeName());
					o.setEmail(u.getEmail());
					o.setMobile(u.getMobile());
					o.setCreateTime(new Date());
					o.setDelete(false);
					o.setStatus(0);
					o.setVisitSum(0);
					o.setStayTime(0);
					o.setSmallHeadPhoto("/web/images/defaultHead/" + (int) (Math.random() * 20 + 1)+".png");
					o.setUserType(0);
					o.setMenuId(-1);
					o.setOrigin(u.getOrigin());
					o.setType(u.getType());
					service.addUser(o);
				}

				UserUtil.setSessionCookie(request, response, o, t);
				callback.onLogin(request, response);

				return ResponseObject.newSuccessResponseObject(o);
			} else {
				return ResponseObject.newErrorResponseObject("用户名密码错误");
			}
		}catch(Exception e){
			return ResponseObject.newErrorResponseObject("用户名密码错误");
		}

	}
	
	@RequestMapping(value = "logout")
	public ResponseObject logout(HttpServletRequest request,HttpServletResponse response) {
		callback.onLogout(request, response);
		UserUtil.cleanSessionCookie(request, response);
		return ResponseObject.newSuccessResponseObject(null);
	}

	/**
	 * 手机提交注册
	 * @param username
	 * @param password
	 * @param code
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "phoneRegist", method = RequestMethod.POST)
	public ResponseObject phoneRegist(String username, String password, String code, String nikeName, HttpServletRequest req) {
		String regmsg = service.addPhoneRegist(req,username, password, code,nikeName);
		//活动统计
		String cok = CookieUtil.getCookieValue(req, "act_code_from");
		if (cok != null && !"".equals(cok)) {
			totalService.addTotalDetail4Reg(cok,username,nikeName);
		}
		return ResponseObject.newSuccessResponseObject(regmsg);
	}
	/**
	 * 邮箱提交注册
	 * @param username
	 * @param password
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "emailRegist", method = RequestMethod.POST)
	public ResponseObject emailRegist(String username, String password,String nikeName,String vcode,
			HttpServletRequest req,HttpSession session) {
		Object sessionCode = req.getSession().getAttribute("randomCode");
		if (sessionCode == null || !sessionCode.toString().equals(vcode)) {
			logger.info("图形验证码错误！");
			return ResponseObject.newErrorResponseObject("验证码错误");
		}
		return ResponseObject.newSuccessResponseObject(service.addEmailRegist(req,username, password,nikeName));
	}
	/**
	 * 新用户注册邮箱激活
	 * @param vcode
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "registEmailValidate", method = RequestMethod.GET)
	public ModelAndView registEmailValidate(String vcode,HttpServletRequest req,HttpServletResponse res) {
		try {
			String msg = service.addRegistEmailValidate(vcode);
			/**
			 * 激活成功直接登录
			 */
			if ("ok".equals(msg)) {
				Token token = this.createToken(userCenterAPI.getUser(vcode.split("!@!")[0]), TokenExpires.Day.getExpires());
				OnlineUser user = this.userCenterService.getUserByLoginName(vcode.split("!@!")[0]);
				if(user.getVhallId()==null){
					String vhallPassword = RandomUtil.getCharAndNumr(6);
					String vhallId = VhallUtil.createUser(user,vhallPassword );
					user.setVhallId(vhallId);
					user.setVhallPass(vhallPassword);
					user.setVhallName(user.getId());
					service.updateVhallInfo(user);
				}
				req.getSession().setAttribute("_token_", token);
				req.getSession().setAttribute("_user_", user);
				UCCookieUtil.writeTokenCookie(res, token);
			CookieUtil.setCookie(res, "first_login", "1", domain, "/", 10);;
				//活动统计
				String cok = CookieUtil.getCookieValue(req, "act_code_from");
				if (cok != null && !"".equals(cok)) {
					totalService.addTotalDetail4Reg(cok,user.getLoginName(),user.getName());
				}
			}
			req.setAttribute("msg", msg);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "服务器异常，请稍后再试……");
		}
		return new ModelAndView("message/email_validate_message");
	}


	/**
	 * 手机重置用户密码
	 * @param username
	 * @param password
	 * @param
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "resetUserPassword", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject resetUserPassword(String username, String password,String code, HttpServletRequest req) {
		return ResponseObject.newSuccessResponseObject(service.updateUserPassword(username, password,code));
	}

	/**
	 * 去到邮箱重置密码页面
	 * @param vcode 验证信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "toResetEmail", method = RequestMethod.GET)
	public ModelAndView toResetEmail(String vcode, HttpServletRequest req) {
		try {
			String msg = service.addResetEmailValidate(vcode);
			if (!"ok".equals(msg)) {
				req.setAttribute("msg", msg);
				return new ModelAndView("message/email_resetpassword_err");
			} else {
				return new ModelAndView("message/email_resetpassword");
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "服务器错误，请稍后再试……");
			return new ModelAndView("message/email_resetpassword_err");
		}
	}

	/**
	 * 邮箱重置用户密码提交
	 * @param vcode 验证信息
	 * @param password 新密码
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "resetPasswordByEmail", method = RequestMethod.POST)
	public ModelAndView resetPasswordByEmail(String vcode, String password, HttpServletRequest req,HttpServletResponse res) {
		try {
			String msg = service.addResetPasswordByEmail(vcode, password);
			if (!"ok".equals(msg)) {
				req.setAttribute("msg", msg);
				return new ModelAndView("message/email_resetpassword_err");
			} else {

				/**
				 * 重置成功直接登录
				 */
				if ("ok".equals(msg)) {
					Token token = this.createToken(userCenterAPI.getUser(vcode.split("!@!")[0]), TokenExpires.Day.getExpires());
					OnlineUser user = this.userCenterService.getUserByLoginName(vcode.split("!@!")[0]);
					req.getSession().setAttribute("_token_", token);
					req.getSession().setAttribute("_user_", user);
					UCCookieUtil.writeTokenCookie(res, token);
				}

				return new ModelAndView("message/email_resetpassword_success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "服务器错误，请稍后再试……");
			return new ModelAndView("message/email_resetpassword_err");
		}
	}


	/**
	 * 是否登录
	 * @return
	 */
	@RequestMapping(value = "isAlive")
	@ResponseBody
	public OnlineResponse isAlive(HttpServletRequest request){
		BxgUser loginUser = UserLoginUtil.getLoginUser(request);
		return OnlineResponse.newSuccessOnlineResponse(service.isAlive(loginUser.getLoginName()));
	}

	/**
	 * 登录状态
	 * @return
	 */
	@RequestMapping(value = "loginStatus")
	@ResponseBody
	public OnlineResponse loginStatus(HttpServletRequest request,HttpServletResponse response){
		String code="0";
		BxgUser sessionLoginUser = UserLoginUtil.getLoginUser(request);
		Token cookieToken = UCCookieUtil.readTokenCookie(request);
		Token redisToken =null;
		if(cookieToken!=null){
			redisToken=cacheService.get("tuk_"+cookieToken.getUserId());

			if(!redisToken.getTicket().equals(cookieToken.getTicket())){
				code = "2";
				UCCookieUtil.clearTokenCookie(response);
				request.getSession().invalidate();
				return OnlineResponse.newSuccessOnlineResponse(code);
			}

		}

		//code=0 已登录状态
		//code=1 无需任何操作 未登录状态
		//code=2 异地登录过 跳转到被顶掉页面
		//code==3 异地修改密码


		//session没数据
		//从cookie里获取
		//cookie里没有就是没有登录过
		//cookie里有就检测是否有效 有效直接登录 无效就提示被顶掉（其他地方登录过）
		//session里有数据就直接是登录状态
		if(sessionLoginUser==null) {
			if (cookieToken == null) {
				code = "1";
			} else {
				if (redisToken != null) { //？ 不可能存在非空情况 因为被顶掉就清空redis了


					if (!redisToken.getTicket().equals(cookieToken.getTicket())) {
						code = "2";
					}else{
						//放到session 自动登录
						//ItcastUser user = api.getUser(redisToken.getLoginName());
						//UserLoginUtil.setLoginUser(request,user);
					}

				} else { //自动登录？
					code = "2";
					UCCookieUtil.clearTokenCookie(response);
				}

			}


		}


		return OnlineResponse.newSuccessOnlineResponse(code);
	}

	/**
	 * 检查当前用户昵称是否存在，存在返回：true   不存在返回：false
	 * @param nickName  昵称
	 * @return
	 */
	@RequestMapping(value = "checkNickName")
	@ResponseBody
	public ResponseObject  checkNickName(String  nickName,HttpSession s){
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
		return ResponseObject.newSuccessResponseObject(service.checkNickName(nickName, u));
	}

	/**
	 * 删除用户
	 * @param username
	 * @return
	 */
/*	@RequestMapping(value = "deleteUser")
		 @ResponseBody
		 public ResponseObject deleteUser(String username) {
		return ResponseObject.newSuccessResponseObject(service.deleteUser(username));
	}*/

	/**
	 * 删除用户
	 * @param username
	 * @return
	 */
//	@RequestMapping(value = "updateUser")
//	@ResponseBody
//	public ResponseObject updateUser(String username,HttpServletRequest request,HttpServletResponse response) {
//		OnlineUser loginUser = (OnlineUser)UserLoginUtil.getLoginUser(request);
//		service.update(username, loginUser);
//		UserLoginUtil.onLogout(request,response);
//		return ResponseObject.newSuccessResponseObject("成功");
//	}

	/***
	 * 获取用户资料
	 * @return
	 */
	@RequestMapping(value = "getUserData")
	@ResponseBody
	public ResponseObject getUserData(HttpServletRequest request){
		OnlineUser loginUser = (OnlineUser)UserLoginUtil.getLoginUser(request);
		if (loginUser == null) {
			return OnlineResponse.newErrorOnlineResponse("请登录！");
		}
		return ResponseObject.newSuccessResponseObject(service.getUserData(loginUser));
	}

	/**
	 * 获取省份
	 * @param
	 */
	@RequestMapping(value="getAllProvince",method=RequestMethod.GET)
	@ResponseBody
	public OnlineResponse getAllProvince(){
		return OnlineResponse.newSuccessOnlineResponse(userCenterService.getAllProvince());
	}


	/**
	 * 根据省份id获取城市
	 * @param
	 *
	 */
	@RequestMapping(value="getCityByProId",method=RequestMethod.GET)
	@ResponseBody
	public OnlineResponse getCityByProId(Integer proId){
		return OnlineResponse.newSuccessOnlineResponse(userCenterService.getCityByProId(proId));
	}

	/**
	 * 修改用户资料
	 * @param request
	 * @param
	 * @return
	 * @throws ServletRequestBindingException
	 */
	@RequestMapping(value="updateUser",method=RequestMethod.POST)
	@ResponseBody
	public OnlineResponse updateUser(HttpServletRequest request,UserVo userVo) throws ServletRequestBindingException{
		OnlineResponse response=new OnlineResponse();
		if (request.getSession().getAttribute(Constant.LOGINUSER) == null) {
			response.setSuccess(false);
			response.setErrorMessage("请登录!");
			return response;
		}
		BxgUser user=(BxgUser)request.getSession().getAttribute(Constant.LOGINUSER);
		String userId = ServletRequestUtils.getStringParameter(request,
				"userId");

		String nickName = ServletRequestUtils.getStringParameter(request,
				"nickName");
		String autograph = ServletRequestUtils.getStringParameter(request,
				"autograph");//个性签名
		String loginName = ServletRequestUtils.getStringParameter(request,
				"loginName");//用户名
		Integer occupation = ServletRequestUtils.getIntParameter(request,
				"occupation");
		String occupationOther = ServletRequestUtils.getStringParameter(request,
				"occupationOther");
//		Integer jobyearId = ServletRequestUtils.getIntParameter(request,
//				"jobyearId");
//		String company = ServletRequestUtils.getStringParameter(request,
//				"company");
//		String posts = ServletRequestUtils.getStringParameter(request,
//				"posts");
		String province = ServletRequestUtils.getStringParameter(request,
				"province");
		String city = ServletRequestUtils.getStringParameter(request,
				"city");
//		String district = ServletRequestUtils.getStringParameter(request,
//				"district");
		String target = ServletRequestUtils.getStringParameter(request,
				"target");
		String sex = ServletRequestUtils.getStringParameter(request,
				"sex");
//		String fullAddress = ServletRequestUtils.getStringParameter(request,
//				"fullAddress");
		UserDataVo vo = new UserDataVo();
		vo.setUid(userId);
		if(nickName==null||StringUtils.isEmpty(nickName.trim())){
			vo.setNickName(loginName); //如果什么都不填的话 昵称默认为帐号
		}else {
			vo.setNickName(nickName);
		}
//		vo.setFullAddress(fullAddress);
		vo.setAutograph(autograph);
		vo.setOccupation(occupation);
//		vo.setCompany(company);
//		vo.setPosts(posts);
//		vo.setJobyearId(jobyearId);
		vo.setProvince(province);
		vo.setCity(city);
//		vo.setDistrict(district);
		vo.setTarget(target);
		vo.setLoginName(loginName);
//		if(occupation != null && occupation == 24) {
			vo.setOccupationOther(occupationOther);
//		}
		vo.setSex(Integer.valueOf(sex));
		boolean a = userCenterService.updateUser(vo);
		if(a){
			user.setName(nickName);
			return OnlineResponse.newSuccessOnlineResponse("修改成功");
		}else{
			return OnlineResponse.newSuccessOnlineResponse("修改失败");
		}
	}

	/**
	 * 修改用户课程信息
	 * @param applyVo
	 * @return
	 */
	@RequestMapping(value="updateApply",method=RequestMethod.POST)
	@ResponseBody
	public OnlineResponse updateApply(ApplyVo applyVo,HttpServletRequest request) {
		return OnlineResponse.newSuccessOnlineResponse(userCenterService.updateApply(applyVo,request));
	}

	/**
	 * 获取所有省份
	 * @param
	 * @return
	 */
	@RequestMapping(value="getAllProvinceCity")
	@ResponseBody
	public OnlineResponse getAllProvinceCity(HttpServletRequest request) throws SQLException {
		return OnlineResponse.newSuccessOnlineResponse(userCenterService.getAllProvinceCity());
	}

	/**
	 * 修改用户头像
	 * @param request
	 * @param
	 * @return
	 * @throws ServletRequestBindingException
	 */
	@RequestMapping(value="updateUserHeadImg",method=RequestMethod.POST)
	@ResponseBody
	public OnlineResponse updateUserHeadImg(HttpServletRequest request) throws ServletRequestBindingException{
		OnlineResponse response=new OnlineResponse();
		if (request.getSession().getAttribute(Constant.LOGINUSER) == null) {
			response.setSuccess(false);
			response.setErrorMessage("请登录!");
			return response;
		}
		BxgUser user=(BxgUser)request.getSession().getAttribute(Constant.LOGINUSER);
		String userId = ServletRequestUtils.getStringParameter(request,
				"userId");
		if(!StringUtils.isEmpty(userId)&&!user.getId().equals(userId)){
			response.setSuccess(false);
			response.setErrorMessage("不是本人操作!");
			return response;
		}
		String img = ServletRequestUtils.getStringParameter(request,
				"img");
		if(!StringUtils.isEmpty(img)) {
			userCenterService.updateUserHeadImg(user, img);
			((OnlineUser)user).setSmallHeadPhoto(img);
		}
		return OnlineResponse.newSuccessOnlineResponse("修改成功");
	}

	/**
	 * 修改密码
	 * @param userId
	 * @param pwd
	 * @return
	 */
/*	@RequestMapping(value="updatePassword",method=RequestMethod.POST)
	@ResponseBody
	public OnlineResponse updatePassword(String userId,String pwd){
		boolean isOk = userCenterService.updatePassword(userId, pwd);
		if(isOk){
			return OnlineResponse.newSuccessOnlineResponse(true);
		}else{
			return OnlineResponse.newErrorOnlineResponse("密码修改失败");
		}
	}*/


	/**
	 * 获取顶部用户资料
	 * @param userId
	 * @return
	 */
/*	@RequestMapping(value="getUserInfoTop",method=RequestMethod.GET)
	@ResponseBody
	public OnlineResponse getUserInfoTop(String userId){
			UserCenterVo userCenterVo=userCenterService.getUserInfo(userId);
			if(userCenterVo!=null&&(userCenterVo.getName()==null||StringUtils.isEmpty(userCenterVo.getName()))){
				userCenterVo.setName(Constant._NICKNAME);
			}
			return OnlineResponse.newSuccessOnlineResponse(userCenterVo);

	}*/

	/**
	 * 修改头像
	 * @param request
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws IOException
	 */
	@RequestMapping(value = "updateHeadPhoto")
	@ResponseBody
	public ResponseObject updateHeadPhoto(HttpServletRequest request) throws ServletRequestBindingException, IOException {
		//获取当前登录用户信息
		OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);

		String content = ServletRequestUtils.getRequiredStringParameter(request, "image");
		int i = content.indexOf(',');
		if (i > 0) {
			content = content.substring(i + 1);
		}
		byte[] image = Base64.getDecoder().decode(content);
		service.updateHeadPhoto(user.getId(), image);


		return this.isAlive(request);
	}
	/**
	 * 联动显示省
	 * @param
	 * @return
	 */
	@RequestMapping(value = "listProvinces")
	@ResponseBody
	public ResponseObject listProvinces() {
		return ResponseObject.newSuccessResponseObject(service.listProvinces());
	}
	/**
	 * 联动显示市
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value = "listCities")
	@ResponseBody
	public ResponseObject listCities(String provinceId) {
		return ResponseObject.newSuccessResponseObject(service.listCities(provinceId));
	}
	/**
	 * 联动显示院校
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value = "listSchools")
	@ResponseBody
	public ResponseObject listSchools(String cityId) {
		return ResponseObject.newSuccessResponseObject(service.listSchools(cityId));
	}

	/**
	 * 联动显示院校
	 * @param schoolNname
	 * @return
	 */
	//@RequestMapping(value = "listSchools")
	//@ResponseBody
	//public ResponseObject listSchools(String schoolNname) {
	//	return ResponseObject.newSuccessResponseObject(service.listSchools(schoolNname));
	//}
	/**
	 * 联动显示专业
	 * @param schoolId
	 * @return
	 */
	@RequestMapping(value = "listSpecialities")
	@ResponseBody
	public ResponseObject listSpecialities(String schoolId) {
		return ResponseObject.newSuccessResponseObject(service.listSpecialities(schoolId));
	}

	Token createToken(ItcastUser user, int expires) {
		if (user == null) {
			return null;
		}
		Token token = new Token();
		// 票的生成策略用UUID
		String ticket = CodeUtil.getRandomUUID();
		token.setTicket(ticket);
		token.setLoginName(user.getLoginName());
		token.setUserId(user.getId());
		token.setOrigin(user.getOrigin());
		long time = System.currentTimeMillis() + expires * 1000;
		token.setExpires(time);

		String userRedisKey="tuk_"+user.getId();
		this.cacheService.set(userRedisKey, token, expires);
		return token;
	}



	/**
	 * 三方QQ登录请求
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "qq_login")
	public void qqlogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//生成签名
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		request.getSession().setAttribute("qq_connect_state",uuid);
		String state = MD5Util.MD5Encode("uuid="+uuid+"&KEY="+ ThirdConnectionConfig.QQ_APP_KEY, "utf-8").toUpperCase();
		Map<String,String> param = new HashMap<>();
		param.put("response_type", "code");
		param.put("client_id", ThirdConnectionConfig.QQ_APP_ID);
		param.put("redirect_uri", ThirdConnectionConfig.QQ_REDIRECT_URI);
		param.put("state", state);
		response.sendRedirect("https://graph.qq.com/oauth2.0/authorize" + "?" + HttpUtil.createQueryString(param));
	}
	/**
	 * qq登录授权成功后回调处理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "qq_afterlogin")
	public void qquserInfo(HttpServletRequest request, HttpServletResponse response,String code,String state) throws Exception {
		//生成签名
		String uuid = (String) request.getSession().getAttribute("qq_connect_state");
		String stateSign = MD5Util.MD5Encode("uuid="+uuid+"&KEY="+ ThirdConnectionConfig.QQ_APP_KEY, "utf-8").toUpperCase();
		if(!StringUtils.isEmpty(state) && !StringUtils.isEmpty(stateSign) && state.equalsIgnoreCase(stateSign)) {
			String accessToken = service.getQQAccessToken(code);
			if (accessToken != null) {
				String openId = service.getQQOpenId(accessToken);
				if (openId != null) {
					Map<String, String> returnMap = service.saveQQUserInfo(accessToken, openId);
					if (returnMap != null) {
						Token token = this.createToken(userCenterAPI.getUser(returnMap.get("username")), TokenExpires.Day.getExpires());
						OnlineUser user = this.userCenterService.getUserByLoginName(returnMap.get("username"));
						request.getSession().setAttribute("_token_", token);
						request.getSession().setAttribute("_user_", user);
						UCCookieUtil.writeTokenCookie(response, token);
						response.sendRedirect("/web/html/bindAccount.html");
					}
				}
			}
		}else{
//			System.out.print("没有获取到响应参数");
		}
	}

	/**
	 * 三方微信登录请求
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "wechat_login")
	public void wechatLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//生成签名
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		request.getSession().setAttribute("wx_connect_state",uuid);
		String state = MD5Util.MD5Encode("uuid="+uuid+"&KEY="+ ThirdConnectionConfig.WX_APP_KEY, "utf-8").toUpperCase();
		Map<String,String> param = new HashMap<>();
		param.put("response_type", "code");
		param.put("client_id", ThirdConnectionConfig.WX_APP_ID);
		param.put("redirect_uri", ThirdConnectionConfig.WX_REDIRECT_URI);
		param.put("state", state);
		response.sendRedirect("https://open.weixin.qq.com/connect/qrconnect" + "?" + HttpUtil.createQueryString(param));
	}
	/**
	 * 微信登录授权成功后回调
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "wechat_afterlogin")
	public void wechatUserInfo(HttpServletRequest request, HttpServletResponse response,String code,String state) throws Exception {
		//生成签名
		String uuid = (String) request.getSession().getAttribute("wx_connect_state");
		String stateSign = MD5Util.MD5Encode("uuid="+uuid+"&KEY="+ ThirdConnectionConfig.WX_APP_KEY, "utf-8").toUpperCase();
		if(!StringUtils.isEmpty(state) && !StringUtils.isEmpty(stateSign) && state.equalsIgnoreCase(stateSign)) {
			Map<String, String> returnMap = service.saveWechatUserInfo(code);
			if (returnMap != null) {
				Token token = this.createToken(userCenterAPI.getUser(returnMap.get("username")), TokenExpires.Day.getExpires());
				OnlineUser user = this.userCenterService.getUserByLoginName(returnMap.get("username"));
				request.getSession().setAttribute("_token_", token);
				request.getSession().setAttribute("_user_", user);
				UCCookieUtil.writeTokenCookie(response, token);
				response.sendRedirect("/web/html/bindAccount.html");
			}
		}else{
//			System.out.print("没有获取到响应参数");
		}
	}
	/**
	 * 三方登录帐号绑定手机或邮箱帐号
	 * @param request
	 * @param username    绑定帐号
	 * @return
	 */
	@RequestMapping(value = "bindcount",method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject bindCount(HttpServletRequest request,HttpServletResponse response,String username){
		//获取当前登录用户信息(三方登录用户)
		OnlineUser onlineUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
		if(onlineUser!=null) {
			ResponseObject obj = service.saveBindCount(username, onlineUser.getUnionId());
			if(obj.isSuccess()) {
				refreshToken(request, response, username);
			}
			return obj;
		}else{
			return ResponseObject.newErrorResponseObject("当前未登录！");
		}
	}

	/**
	 * 刷新session和cookie信息
	 * @param request
	 * @param response
	 * @param username
	 */
	private void refreshToken(HttpServletRequest request, HttpServletResponse response, String username) {
		/** 更新session和cookie信息 */
		Token token = this.createToken(userCenterAPI.getUser(username), TokenExpires.Day.getExpires());
		OnlineUser user = this.userCenterService.getUserByLoginName(username);
		request.getSession().setAttribute("_token_", token);
		request.getSession().setAttribute("_user_", user);
		UCCookieUtil.writeTokenCookie(response, token);
	}


	/**
	 * 获取未读消息总数
	 * @return
	 */
	@RequestMapping(path="findMessageCount",method= RequestMethod.GET)
	@ResponseBody
	public ResponseObject findMessageCount(HttpSession s){
		OnlineUser user =  (OnlineUser)s.getAttribute("_user_");
		if(user == null) {
			return OnlineResponse.newErrorOnlineResponse("请登录!");
		}
		return OnlineResponse.newSuccessOnlineResponse(messageService.findMessageCount(user.getId()));
	}

	/**
	 * 查询我的购物车中课程数量
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findCourseNum" )
	@ResponseBody
	public ResponseObject findCourseNum(HttpServletRequest req) {
		BxgUser user = UserLoginUtil.getLoginUser(req);
		if (user == null) {
			return ResponseObject.newErrorResponseObject("请登录！");
		}
		return ResponseObject.newSuccessResponseObject(shoppingCartService.findCourseNum(user.getId()));
	}
}
