package com.xczhihui.bxg.online.web.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xczhihui.bxg.online.common.domain.Apply;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.VerificationCode;
import com.xczhihui.bxg.online.web.base.common.Constant;
import com.xczhihui.bxg.online.web.base.utils.VhallUtil;
import com.xczhihui.bxg.online.web.dao.UserCenterDao;
import com.xczhihui.bxg.online.web.service.ShareManageService;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.bxg.online.web.service.VerificationCodeService;
import com.xczhihui.bxg.online.web.utils.HttpUtil;
import com.xczhihui.bxg.online.web.utils.ThirdConnectionConfig;
import com.xczhihui.bxg.online.web.vo.RegionVo;
import com.xczhihui.bxg.online.web.vo.SchoolVo;
import com.xczhihui.bxg.online.web.vo.UserDataVo;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.support.domain.Attachment;
import com.xczhihui.common.support.domain.SystemVariate;
import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.common.support.service.AttachmentType;
import com.xczhihui.common.util.DateUtil;
import com.xczhihui.common.util.ImageUtil;
import com.xczhihui.common.util.RandomUtil;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.online.api.service.UserCoinService;
import com.xczhihui.user.center.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.xczhihui.bxg.online.web.utils.HttpUtil.sendGet;

/**
 * 用户相关
 * @author Haicheng Jiang
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserCenterAPI userCenterAPI;

	@Autowired
	public UserCenterDao userCenterDao;

    @Autowired
    ShareManageService shareManageService;

	private SimpleHibernateDao dao;

	@Autowired
	private VerificationCodeService verificationCodeService;
	
	@Autowired
	private AttachmentCenterService attachmentCenterService;
	
	@Autowired
	private UserCoinService userCoinService;
	
	@Value("${web.url}")
	private String weburl;
	
	//数据字典
	private Map<String, String> attrs = new HashMap<String, String>();


	@Override
	public String addPhoneRegist(HttpServletRequest req,String username, String password, String code,String nikeName) {
		
		
		//动态码验证
		List<VerificationCode>  codes = dao.findByHQL("from VerificationCode where phone=? and vtype='1' ", username);
		if (!StringUtils.hasText(code) || codes == null || codes.size() <= 0 
				|| !codes.get(0).getVcode().equals(code)) {
			throw new RuntimeException("动态码不正确！");
		}
		if (!codes.get(0).getPhone().equals(username)) {
			throw new RuntimeException ("手机号与动态码不符！");
		}
		
		if (nikeName == null || "".equals(nikeName)) {
			throw new RuntimeException ("请输入用户名");
		}
		
		if (dao.findOneEntitiyByProperty(OnlineUser.class, "name", nikeName) != null) {
			throw new RuntimeException ("用户名已存在");
		}
		
		//初始化字典
		initSystemVariate();
		
		if (new Date().getTime() - codes.get(0).getCreateTime().getTime() > 1000 * 60
				* Integer.valueOf(attrs.get("message_provider_valid_time"))) {
			throw new RuntimeException ("动态码超时，请重新发送！");
		}

		//保存本地库
		OnlineUser u = new OnlineUser();
		String uid= UUID.randomUUID().toString().replaceAll("-", "");
		u.setLoginName(username);
		u.setMobile(u.getLoginName());
		u.setStatus(0);
		u.setCreateTime(new Date());
		u.setDelete(false);
		u.setName(nikeName);
		u.setSmallHeadPhoto(weburl+"/web/images/defaultHead/" + (int) (Math.random() * 20 + 1)+".png");
		u.setVisitSum(0);
		u.setStayTime(0);
		u.setUserType(0);
		u.setOrigin("online");
		u.setMenuId(-1);
		u.setIsLecturer(0);
		u.setRoomNumber(0);
        u = shareManageService.saveShareRelation(req,u);
		dao.save(u);
		/*初始化用户账户--20170911--yuruixin*/
		userCoinService.saveUserCoin(u.getId());
		if(u.getVhallId()==null){
			updateVhallInfo(u);
		}

		/*初始化用户账户--20170911--yuruixin*/
//		u.getId();
		//向用户中心注册
		if (userCenterAPI.getUser(u.getLoginName()) == null) {
			userCenterAPI.regist(u.getLoginName(), password, u.getName(), UserSex.UNKNOWN, null,
					u.getLoginName(), UserType.STUDENT, UserOrigin.ONLINE, UserStatus.NORMAL);
		}
		//删除动态码
		dao.delete(codes.get(0));
		
		return "注册成功！";
	}

	@Override
	public String addEmailRegist(HttpServletRequest req,String username, String password,String nikeName) {
		
		if (nikeName == null || "".equals(nikeName)) {
			throw new RuntimeException ("请输入用户名");
		}
		
		if (dao.findOneEntitiyByProperty(OnlineUser.class, "name", nikeName) != null) {
			throw new RuntimeException ("用户名已存在");
		}
		
		ItcastUser iu = userCenterAPI.getUser(username);
		OnlineUser ou = dao.findByHQLOne("from OnlineUser where loginName=?", username);
		/**
		 * 用户存在且状态正常
		 */
		if (iu != null && ou != null && ou.getStatus() != -1) {
			throw new RuntimeException ("该邮箱已注册，请直接登录！");
		}
		/**
		 * 用户存在且状态为禁用
		 */
		if (iu != null && ou != null && ou.getStatus() == -1) {
			throw new RuntimeException ("用户已禁用！");
		}
		
		if (ou == null) {
			//保存本地库，状态设置为禁用（未验证）
			OnlineUser u = new OnlineUser();
			u.setLoginName(username);
			u.setEmail(u.getLoginName());
			u.setStatus(-1);
			u.setPassword(password);
			u.setCreateTime(new Date());
			u.setDelete(false);
			u.setName(nikeName);
			u.setSmallHeadPhoto(weburl+"/web/images/defaultHead/" + (int) (Math.random() * 20 + 1)+".png");
			u.setVisitSum(0);
			u.setStayTime(0);
			u.setUserType(0);
			u.setMenuId(-1);
			u.setOrigin("online");
			u.setIsLecturer(0);
			u.setRoomNumber(0);
            u = shareManageService.saveShareRelation(req,u);
			dao.save(u);
			/*初始化用户账户--20170911--yuruixin*/
			userCoinService.saveUserCoin(u.getId());
			/*初始化用户账户--20170911--yuruixin*/
		}
		//发送验证邮件
		verificationCodeService.addMessage(username, "1");
		
		return "注册成功，请前往邮箱激活！";
	}

	@Override
	public String addRegistEmailValidate(String vcode) {
		
		if (!StringUtils.hasText(vcode)) {
			return ("链接不正确！");
		}

		String[] codestring = vcode.split("!@!");
		if (codestring.length < 2) {
			return ("链接不正确！");
		}
		
		//获取邮箱名、动态码
		String email = codestring[0];
		vcode = codestring[1];
		
		//动态码验证
		List<VerificationCode>  codes = dao.findByHQL("from VerificationCode where phone=? and vcode=? and vtype='1' ", email, vcode);
		if (!StringUtils.hasText(vcode) || codes == null || codes.size() <= 0 ) {
			return ("此链接已失效！");
		}
		
		//初始化字典
		initSystemVariate();
		
		//验证超时
		if (System.currentTimeMillis() - codes.get(0).getCreateTime().getTime() > 1000 * 60
				* Integer.valueOf(attrs.get("message_provider_valid_time"))) {
			return "此链接已超时，请重新注册！";
		}
		
		OnlineUser u = dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", codes.get(0).getPhone());
		//更新用户状态为正常
		u.setStatus(0);
		
		//向用户中心注册
		if (userCenterAPI.getUser(u.getLoginName()) == null) {
			userCenterAPI.regist(u.getLoginName(), u.getPassword(), u.getName(), UserSex.UNKNOWN, u.getLoginName(),
					u.getLoginName(), UserType.STUDENT, UserOrigin.ONLINE, UserStatus.NORMAL);
		}
		
		//清除用户名密码
		u.setPassword(null);
		dao.update(u);
		
		if (codes.get(0) != null) {
			dao.delete(codes.get(0));
		}
		
		return "ok";
	}

	/**
	 * 重置用户密码
	 * @param username
	 * @param password
	 * @param code
	 * @return
	 */
    @Override
    public String updateUserPassword(String username,String password, String code) {
    	
    	ItcastUser iu = userCenterAPI.getUser(username);
    	OnlineUser user = dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", username);
    	if (user == null || iu == null) {
			throw new RuntimeException ("用户不存在！");
		} else if (user.getStatus() == -1 || user.isDelete() || iu.getStatus() == UserStatus.DISABLE.getValue()) {
			throw new RuntimeException ("用户已经禁用！");
		}
    	
    	//动态码验证
		List<VerificationCode>  codes = dao.findByHQL("from VerificationCode where phone=? and vcode=? and vtype='2' ", username, code);
		if (codes == null || codes.size() <= 0 ) {
			throw new RuntimeException ("动态码不正确！");
		}
		
		//初始化字典
		initSystemVariate();
		
		//动态码超时
		if (System.currentTimeMillis() - codes.get(0).getCreateTime().getTime() > 1000 * 60
				* Integer.valueOf(attrs.get("message_provider_valid_time"))) {
			throw new RuntimeException ("动态码超时，请重新发送！");
		}
		
		//更新用户密码
		userCenterAPI.updatePassword(username, null, password);
		
		if (codes.get(0) != null) {
			dao.delete(codes.get(0));
		}
		return "修改成功！";
    }
    
    public void initSystemVariate(){
		//查数据字典
		List<SystemVariate> lst = dao.findByHQL("select t1 from SystemVariate t1,SystemVariate t2 "
				+ "where t1.parentId=t2.id and t2.name=?","message_provider");
		for (SystemVariate systemVariate : lst) {
			attrs.put(systemVariate.getName(), systemVariate.getValue());
		}
	}
    
	@Override
	public String update(String username,OnlineUser u) {
		OnlineUser user = dao.findOneEntitiyByProperty(OnlineUser.class, "id", u.getId());
		user.setLoginName(username);
		if (user != null) {
			dao.update(user);
		}
		ItcastUser iu = userCenterAPI.getUser(u.getLoginName());
		userCenterAPI.updateLoginName(u.getLoginName(),username);

		return "修改成功！";
	}

	@Override
	public String addResetEmailValidate(String vcode) {
		
		String[] codestring = vcode.split("!@!");
		if (codestring.length < 2) {
			return ("动态码不正确！");
		}
		
		//获取邮箱名、动态码
		String email = codestring[0];
		vcode = codestring[1];
		
		//动态码验证
		List<VerificationCode>  codes = dao.findByHQL("from VerificationCode where phone=? and vcode=? and vtype='2' ", email,vcode);
		if (!StringUtils.hasText(vcode) || codes == null || codes.size() <= 0 ) {
			return ("连接已失效！");
		}
		
		//初始化字典
		initSystemVariate();
		
		//验证超时，如果超时删除验证码
		if (System.currentTimeMillis() - codes.get(0).getCreateTime().getTime() > 1000 * 60
				* Integer.valueOf(attrs.get("message_provider_valid_time"))) {
			dao.delete(codes.get(0));
			return "验证超时，请重新发送！";
		}
		
		//验证用户
		OnlineUser user = dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", email);
		if (user == null) {
			return "用户不存在！";
		} else if (user.getStatus() == -1 || user.isDelete()) {
			return "用户已禁用！";
		}
		
		return "ok";
	}
	
	
	@Override
	public String addResetPasswordByEmail(String vcode, String password) {
		//验证
		String msg = this.addResetEmailValidate(vcode);
		if (!"ok".equals(msg)) {
			return msg;
		}
		userCenterAPI.updatePassword(vcode.split("!@!")[0], null, password);
		
		//成功之后，删除验证码
		List<VerificationCode>  codes = dao.findByHQL("from VerificationCode where phone=? and vcode=? and vtype='2' ",
				vcode.split("!@!")[0], vcode.split("!@!")[1]);
		for (VerificationCode v : codes) {
			dao.delete(v);
		}
		
		return "ok";
	}
	
	@Resource(name="simpleHibernateDao")
	public void setDao(SimpleHibernateDao dao) {
		this.dao = dao;
	}

	/**
	 * 获取用户资料
	 * @return
	 */
	@Override
    public UserDataVo getUserData(OnlineUser loginUser) {
		UserDataVo vo = new UserDataVo();
		if (StringUtils.hasText(loginUser.getId())) {
			vo = userCenterDao.getUserData(loginUser.getId());
	 	if (!StringUtils.hasText(vo.getNickName())) {
				vo.setNickName(Constant._NICKNAME);
			}
		    vo.setBirthdayStr(DateUtil.formatDate(vo.getBirthday(), DateUtil.FORMAT_DAY));
			//设置职位
			vo.setJob(userCenterDao.getJob("occupation"));

			// 获取用户报名信息
			Apply  app=  dao.findOneEntitiyByProperty(Apply.class, "userId", loginUser.getId());
			if(app != null) {
				vo.setUid(app.getUserId());
				vo.setQq(app.getQq());
				vo.setEmail(app.getEmail());
//				vo.setSex(app.getSex());
				vo.setMobile(app.getMobile());
				vo.setBirthdayStr(app.getBirthday() != null ? DateUtil.formatDate(app.getBirthday(), DateUtil.FORMAT_DAY) : "");
				vo.setApplyProvince(app.getProvince());
				vo.setAppCity(app.getCity());
				vo.setRealName(app.getRealName());
				vo.setSchoolId(app.getSchoolId());
				vo.setEducationId(app.getEducationId());
				vo.setMajorId(app.getMajorId());
				vo.setApplyId(app.getId());
				vo.setIdCardNo(app.getIdCardNo());

			}
		}
		return vo;
	}


	/**
	 * 检查当前用户昵称是否存在，存在返回：true   不存在返回：false
	 * @param nickName  昵称
	 * @return
	 */
    @Override
    public Boolean  checkNickName(String  nickName, OnlineUser u) {
		String sql = "select name from oe_user where name = ? ";
		List<UserDataVo> ou = dao.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,
				BeanPropertyRowMapper.newInstance(UserDataVo.class), nickName);
		if (u == null && ou.size() >0) {
			return true;
		}
		if ( ou.size() >0 && u != null && !nickName.equals(u.getName())) {
			return true;
		}
		return false;

	}

	@Override
	public void updateHeadPhoto(String userId, byte[] image) throws IOException {

		OnlineUser ou = dao.findOneEntitiyByProperty(OnlineUser.class, "id", userId);
		//旧的头像
		String oldHeadImg=ou.getSmallHeadPhoto();
		Attachment bigattr = this.attachmentCenterService.addAttachment(userId, AttachmentType.ONLINE, userId+"_big.png", 
				image,StringUtils.getFilenameExtension(userId+"_big.png"));
		
		ou.setBigHeadPhoto(bigattr.getUrl());
		
		InputStream imageInputStream = new ByteArrayInputStream(image);
        ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream();
        
        ImageUtil.scaleImage(imageInputStream, imageOutputStream,"png", 80, 80);
		image = imageOutputStream.toByteArray();
		
		Attachment smallattr  = this.attachmentCenterService.addAttachment(
				userId, AttachmentType.ONLINE, userId+"_small.png", image,
				StringUtils.getFilenameExtension(userId+"_small.png"));
		ou.setSmallHeadPhoto(smallattr.getUrl());
		
		dao.update(ou);

		/**
		 * 更新博问答相关用户信息
		 */
		if (smallattr.getUrl() != null && (!StringUtils.hasText(oldHeadImg) || !oldHeadImg.equals(smallattr.getUrl()))) {
			userCenterDao.updateAskUserInfo(ou.getId(),ou.getName(),ou.getSmallHeadPhoto());
		}
		
		/*更新微吼账户信息*/
		VhallUtil.updateUser(ou, null);
	}
	

	@Override
	public OnlineUser isAlive(String loginName) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		OnlineUser u=dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", loginName);
		u.setIsPerfectInformation(true);
        u.setIsOldUser(0);
		if(u != null){ //查看用户真实资料是否填写完成
			paramMap.put("userId",u.getId());
			String  sql1=" select is_old_user from  oe_apply  where user_id=:userId ";
			String  sql2=" select is_old_user from  oe_apply  where user_id=:userId  and (real_name is NULL  or real_name='' or mobile is null or mobile='' or id_card_no is null or id_card_no = '' ) ";
			List<Map<String,Object>> applys1 =  dao.getNamedParameterJdbcTemplate().queryForList(sql1, paramMap);
			List<Map<String,Object>> applys2 =  dao.getNamedParameterJdbcTemplate().queryForList(sql2, paramMap);
			if(applys1.size() < 1 || applys2.size() > 0){
				u.setIsPerfectInformation(false);
			}
		}
		return u;
	}

	@Override
	public List<RegionVo> listProvinces() {
		return dao.getNamedParameterJdbcTemplate().query("select * from oe_region where parent_id='0' ",
				new BeanPropertyRowMapper<RegionVo>(RegionVo.class));
	}

	@Override
	public List<RegionVo> listCities(String provinceId) {
		Map<String, Object> ps = new HashMap<String, Object>();
		ps.put("provinceId", provinceId);
		return dao.getNamedParameterJdbcTemplate().query("select * from oe_region where parent_id= :provinceId ",ps,
				new BeanPropertyRowMapper<RegionVo>(RegionVo.class));
	}

	@Override
	public List<SchoolVo> listSchools(String cityId) {
		Map<String, Object> ps = new HashMap<String, Object>();
		ps.put("cityId", cityId);
		return dao.getNamedParameterJdbcTemplate().query("select id,name from school where city_id= :cityId ",ps,
				new BeanPropertyRowMapper<SchoolVo>(SchoolVo.class));
	}

	@Override
	public void addUser(OnlineUser user) {
		dao.save(user);
	}

	@Override
	public OnlineUser findUserByLoginName(String loginName) {
		return dao.findByHQLOne("from OnlineUser where loginName=?", loginName);
	}
	
	@Override
	public OnlineUser findUserById(String userId) {
		return dao.findByHQLOne("from OnlineUser where id=?", userId);
	}

	@Override
	public String getQQAccessToken(String authorization_code) {
		Map<String,String> param = new HashMap<>();
		param.put("grant_type","authorization_code");
		param.put("client_id", ThirdConnectionConfig.QQ_APP_ID);
		param.put("client_secret",ThirdConnectionConfig.QQ_APP_KEY);
		param.put("code",authorization_code);
		param.put("redirect_uri",ThirdConnectionConfig.QQ_REDIRECT_URI);
		//发送GET请求
		String returnString = sendGet("https://graph.qq.com/oauth2.0/token",param);
		Map<String, Object> m = HttpUtil.getUrlParamMap(returnString);
		String code = (String) m.get("code");
		String msg = (String) m.get("msg");
		String access_token = (String) m.get("access_token");
		if(access_token!=null){
			System.out.println("成功信息："+returnString);
			return access_token;
		}else if("100014".equalsIgnoreCase(code)){//access token过期
			System.out.println("access token自动续期："+msg);
			String refresh_token = (String) m.get("refresh_token");
			param = new HashMap<>();
			param.put("grant_type","refresh_token");
			param.put("client_id", ThirdConnectionConfig.QQ_APP_ID);
			param.put("client_secret",ThirdConnectionConfig.QQ_APP_KEY);
			param.put("refresh_token",refresh_token);
			//发送GET请求
			returnString = sendGet("https://graph.qq.com/oauth2.0/token",param);
			m = HttpUtil.getUrlParamMap(returnString);
			access_token = (String) m.get("access_token");
			if(access_token!=null){
				System.out.println("成功信息："+ returnString);
				return access_token;
			}
		}else{
			System.out.println("失败信息："+ returnString);
		}
		return null;
	}

	@Override
	public String getQQOpenId(String access_token) {
		Map<String,String> param = new HashMap<>();
		param.put("access_token",access_token);
		//发送GET请求
		String returnString = sendGet("https://graph.qq.com/oauth2.0/me", param);
		returnString = returnString.substring(returnString.indexOf("(")+1,returnString.indexOf(")"));
		Gson g = new GsonBuilder().create();
		Map<String, Object> fromJson = g.fromJson(returnString, Map.class);
		String openId = (String) fromJson.get("openid");
		if(openId!=null){
			System.out.println("成功信息："+ returnString);
			return openId;
		}else{
			System.out.println("失败信息："+ returnString);
		}
		return null;
	}

	@Override
	public Map<String,String> saveQQUserInfo(String access_token, String openId) {
		Map<String,String> returnParam = new HashMap<>();
		Map<String,String> param = new HashMap<>();
		param.put("access_token",access_token);
		param.put("oauth_consumer_key", ThirdConnectionConfig.QQ_APP_ID);
		param.put("openid",openId);
		//发送GET请求
		String returnString = sendGet("https://graph.qq.com/user/get_user_info",param);
		Gson g = new GsonBuilder().create();
		Map<String, Object> fromJson = g.fromJson(returnString, Map.class);
		int ret = (int)(double) fromJson.get("ret");
		if(ret==0){
			String nickname = (String) fromJson.get("nickname");//qq昵称
			String figureurl_qq_1 = (String) fromJson.get("figureurl_qq_1");//qq头像

			/** 查询用户中心是否注册了qq用户，如果没有向用户中心注册qq用户 */
			ItcastUser usercenter = userCenterAPI.getUser(openId);
			if (usercenter == null) {
				userCenterAPI.regist(openId, UUID.randomUUID().toString().replaceAll("-", ""), nickname, UserSex.UNKNOWN, null,
						null, UserType.STUDENT, UserOrigin.ONLINE, UserStatus.NORMAL);
			}

			/** 查询数据库中是否注册了qq用户，如果没有向数据库中插入qq用户 */
			OnlineUser user = dao.findOneEntitiyByProperty(OnlineUser.class, "unionId", openId);
			if (user == null) {
				OnlineUser u = new OnlineUser();
				u.setUnionId(openId);
				u.setUserType(1);
				u.setLoginName(openId);
				u.setStatus(0);
				u.setCreateTime(new Date());
				u.setDelete(false);
				u.setName(nickname);
				u.setSmallHeadPhoto(figureurl_qq_1);
				u.setVisitSum(0);
				u.setStayTime(0);
				dao.save(u);
			}
			returnParam.put("username",openId);
			return returnParam;
		}else{
			System.out.println("失败信息："+ returnString);
		}
		return null;
	}

	@Override
	public Map<String,String> saveWechatUserInfo(String code) {
		Map<String,String> returnParam = new HashMap<>();
		/** 1，根据code调用微信接口‘https://api.weixin.qq.com/sns/oauth2/access_token’查询accessToken和openid */
		Map<String, String> param = new HashMap<>();
		param.put("grant_type", "authorization_code");
		param.put("code", "CODE");
		param.put("appid", ThirdConnectionConfig.WX_APP_ID);
		param.put("secret", ThirdConnectionConfig.WX_APP_KEY);
		param.put("code", "ED12F9682E734EEE91540227B3D0EE1E");
		//发送GET请求
		String returnString = sendGet("https://api.weixin.qq.com/sns/oauth2/access_token", param);
		Gson g = new GsonBuilder().create();
		Map<String, Object> fromJson = g.fromJson(returnString, Map.class);
		String access_token = String.valueOf(fromJson.get("access_token"));
		String openid = String.valueOf(fromJson.get("openid"));
		/** 2，调用微信接口‘https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID’获取微信用户信息 */
		if (access_token != null && openid != null) {
			param = new HashMap<>();
			param.put("access_token", access_token);
			param.put("openid", openid);
			param.put("lang", "zh-CN");
			//发送GET请求
			returnString = sendGet("https://api.weixin.qq.com/sns/userinfo", param);
			g = new GsonBuilder().create();
			fromJson = g.fromJson(returnString, Map.class);
			String errcode = String.valueOf(fromJson.get("errcode"));
			/** 3，根据openid判断本地和用户中心是否存在此微信用户，否则保存用户 */
			if(errcode==null) {
				String nickname = String.valueOf(fromJson.get("nickname"));
				String unionid = String.valueOf(fromJson.get("unionid"));
				String headimgurl = (String) fromJson.get("headimgurl");//头像

				/** 查询用户中心是否注册了微信用户，如果没有向用户中心注册微信用户 */
				ItcastUser usercenter = userCenterAPI.getUser(unionid);
				if (usercenter == null) {
					userCenterAPI.regist(unionid, UUID.randomUUID().toString().replaceAll("-", ""), nickname, UserSex.UNKNOWN, null,
							null, UserType.STUDENT, UserOrigin.ONLINE, UserStatus.NORMAL);
				}

				/** 查询数据库中是否注册了微信用户，如果没有向数据库中插入微信用户 */
				OnlineUser user = dao.findOneEntitiyByProperty(OnlineUser.class, "unionId", unionid);
				if (user == null) {
					OnlineUser u = new OnlineUser();
					u.setUnionId(unionid);
					u.setUserType(2);
					u.setLoginName(unionid);
					u.setStatus(0);
					u.setCreateTime(new Date());
					u.setDelete(false);
					u.setName(nickname);
					u.setSmallHeadPhoto(headimgurl);
					u.setVisitSum(0);
					u.setStayTime(0);
					dao.save(u);
				}
				returnParam.put("username",unionid);
				return returnParam;
			}
		}
		return null;
	}

	@Override
	public ResponseObject saveBindCount(String username, String unionid) {
		ItcastUser usercenter = userCenterAPI.getUser(username);
		OnlineUser ou = dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", username);
		if(usercenter!=null && ou!=null) {
			OnlineUser tou = dao.findByHQLOne("from OnlineUser where unionId=?", unionid);
			if(tou!=null) {
				tou.setRefId(ou.getId());
				dao.update(tou);
				return ResponseObject.newSuccessResponseObject("绑定成功！");
			}else{
				return ResponseObject.newErrorResponseObject("绑定失败！");
			}
		}else{
			return ResponseObject.newErrorResponseObject("要绑定的帐号未注册！");
		}
	}

	@Override
	public void updateVhallInfo(OnlineUser o) {
		String vhallPassword = RandomUtil.getCharAndNumr(6);
		String vhallId = VhallUtil.createUser(o,vhallPassword);
		o.setVhallId(vhallId);
		o.setVhallPass(vhallPassword);
		o.setVhallName(o.getId());
		dao.update(o);
	}

	@Override
	public Boolean isAnchor(String loginName) {
		StringBuffer sql = new StringBuffer();
		List<OnlineUser> users;
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName",loginName);
		sql.append( "SELECT \n" +
				"  ou.`name`," +
				" ca.status caStatus "+
				"FROM\n" +
				"  `course_anchor` ca \n" +
				"  JOIN `oe_user` ou \n" +
				"    ON ca.`user_id` = ou.id \n" +
				"WHERE ou.`login_name` = :loginName ");
		users= dao.findEntitiesByJdbc(OnlineUser.class, sql.toString(), paramMap);
		if(users.size()!=1){
			return false;
		}else{
			return users.get(0).getCaStatus();
		}
	}
}

