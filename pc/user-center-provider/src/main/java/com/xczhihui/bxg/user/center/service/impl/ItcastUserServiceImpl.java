package com.xczhihui.bxg.user.center.service.impl;

import java.util.*;

import com.xczhihui.bxg.user.center.dao.LoginLimitDao;
import com.xczhihui.user.center.bean.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xczhihui.bxg.user.center.dao.ItcastUserDao;
import com.xczhihui.bxg.user.center.service.CacheService;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.utils.CodeUtil;

@Service("userCenterAPI")
public class ItcastUserServiceImpl implements UserCenterAPI {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ItcastUserDao itcastUserDao;

	private TokenManager tokenManager;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private LoginLimitDao loginLimitDao;

	@Override
	public Token regist( String loginName,String password, 	String nikeName, 	UserSex sex, String email,
			String mobile, 	UserType type, 		UserOrigin origin, UserStatus status) {
		if (!StringUtils.hasText(loginName) || !StringUtils.hasText(password)) {
			throw new RuntimeException("用户名、密码不允许为空！");
		}
		ItcastUser u = this.getUser(loginName);
		if (u != null) {
			throw new RuntimeException("用户名'" + u.getLoginName() + "'已被注册");
		}
		ItcastUser itcastUser = new ItcastUser();
		itcastUser.setLoginName(loginName);
		itcastUser.setPassword(password);
		this.proccessPassword(itcastUser, true);
		itcastUser.setNikeName(nikeName);
		itcastUser.setSex(sex.getValue());
		itcastUser.setEmail(email);
		itcastUser.setMobile(mobile);
		itcastUser.setType(type.getValue());
		itcastUser.setOrigin(origin.toString().toLowerCase());
		itcastUser.setStatus(status.getValue());
		itcastUser.setRegistDate(new Date());
		this.itcastUserDao.addItcastUser(itcastUser);
		Token token = this.tokenManager.createToken(itcastUser);
		if(token!=null){

		}
		return token;
	}

	
	@Override
	public void registBatch(List<List<Map<String, Object>>> users) {
		for (int i = 0 ; i <   users.size() ; i++ ) {
			for ( Map<String, Object>  map : users.get(i) ) {
				if ( map.get("loginName") == null  ||   map.get("password") == null ) {
					throw new RuntimeException("用户名、密码不允许为空！");
				}
				String   opSign = map.get("opSign").toString();
				//2==新增操作,1==更新操作
				if("1".equals(opSign)){
					this.update(map.get("loginName")+"" , map.get("name")+"", Integer.valueOf(map.get("sex")+""), map.get("email")+"" ,map.get("mobile")+"" , UserType.STUDENT.getValue() ,0);
				}else{
					ItcastUser u = this.getUser(map.get("loginName").toString());
					//如果用户存在了，则判断是否是双元过去的用户
					if (u != null ){
						if("dual".equals(u.getOrigin())) {//online在线，dual双元，bxg院校，ask问答精灵
							throw new RuntimeException("第"+ (i+2) +"行  的手机号'" + u.getLoginName() + "'已在用户中心存在");
						}else{
							//非双元过来的用户，不作处理
						}
					}else{
						ItcastUser user = new ItcastUser();
						user.setLoginName(map.get("loginName").toString());
						user.setPassword(map.get("password").toString());
						this.proccessPassword(user, true);
						user.setNikeName(map.get("name")+"");
						user.setSex( Integer.valueOf(map.get("sex")+""));
						user.setEmail(map.get("email") == null ?null:map.get("email")+"");
						user.setMobile(map.get("mobile")+"");
						user.setType(((UserType)map.get("type")).getValue());
						user.setOrigin(((UserOrigin)map.get("origin")).toString().toLowerCase());
						user.setStatus( Integer.valueOf(map.get("status")+""));
						user.setRegistDate(new Date());
						this.itcastUserDao.addItcastUser(user);
						Token token = this.tokenManager.createToken(user);
						
					}
				}
 
			}
		}
	}
	
	@Override
	public ItcastUser deleteUser(String loginName) {
		ItcastUser user = this.getUser(loginName);
		if (user != null) {
			this.itcastUserDao.delItcastUser(loginName);
			logger.warn("delete user:{}", user);
		}
		return user;
	}
	
	@Override
	public ItcastUser deleteUserLogic(String loginName) {
		this.updateStatus(loginName, UserStatus.DISABLE.getValue());
		return this.getUser(loginName);
	}

	@Override
	public ItcastUser update(String loginName,String nikeName,int sex,String email,String mobile,int type,int status) {
		ItcastUser user = this.getUser(loginName);
		if (StringUtils.hasText(nikeName) && !nikeName.equals(user.getNikeName())) {
			user.setNikeName(nikeName);
		}
		if (UserSex.isValid(sex) && sex != user.getSex()) {
			user.setSex(sex);
		}
		if (StringUtils.hasText(email) && !email.equals(user.getEmail())) {
			user.setEmail(email);
		}
		if (StringUtils.hasText(mobile) && !mobile.equals(user.getMobile())) {
			user.setMobile(mobile);
		}
		if (UserType.isValid(type) && type != user.getType()) {
			user.setType(type);
		}
		if (UserStatus.isValid(status) && status != user.getStatus()) {
			user.setStatus(status);
		}
		this.itcastUserDao.updateItcastUser(user);
		return user;
	}
	
	@Override
	public void updateStatus(String loginName,int status) {
		if (!UserStatus.isValid(status)) {
			throw new IllegalArgumentException("status参数错误！");
		}
		itcastUserDao.updateStatus(loginName, status);
	}
	
	@Override
	public void updatePassword(String loginName,String oldPassword, String newPassword) {
		ItcastUser user = this.getUser(loginName);
		if (user == null) {
			logger.warn("没有找到登录名'{}'的用户.", loginName);
			return;
		}
		if (StringUtils.hasText(oldPassword)) {
			String expect = user.getPassword();
			String actual = CodeUtil.encodePassword(oldPassword, user.getSalt());
			if (!expect.equals(actual)) {
				logger.info("actual:{},expect:{}", actual, expect);
				throw new RuntimeException("原密码错误");
			}
		} else {
			logger.warn("重置密码");
		}
		user.setPassword(newPassword);
		this.proccessPassword(user, false);
		this.itcastUserDao.updatePassword(user.getId(), user.getPassword());
	}

	@Override
	public void updateLoginName(String oldLoginName, String newLoginName) {
		if (oldLoginName.equals(newLoginName)) {
			return;
		}
		ItcastUser user = this.getUser(newLoginName);
		if (user != null) {
			throw new RuntimeException("登录名'" + newLoginName + "'已存在.");
		}
		this.itcastUserDao.updateLoginName(oldLoginName, newLoginName);
	}

	@Override
	public Token login(String loginName, String password) {
		return this.login(loginName, password, TokenExpires.Hour);
	}
	
	@Override
	public Token loginMobile(String loginName, String password, TokenExpires tokenExpires) {
		if (StringUtils.hasText(loginName) && StringUtils.hasText(password)) {
			ItcastUser user = this.getUser(loginName);
			if (user == null) {
				throw new RuntimeException("手机号暂未注册");
			}
			if (user.getStatus() == UserStatus.DISABLE.getValue()) {
				throw new RuntimeException("账号被禁用");
			}
			String salt = user.getSalt();
			if (salt == null) {
				salt = "";
			}
			String expect = CodeUtil.encodePassword(password, salt);
			String actual = user.getPassword();
			if (!expect.equals(actual)) {
				logger.info("actual:{}", actual);
				logger.info("expect:{}", expect);
				throw new RuntimeException("用户名或密码错误");
			}
			this.updateLastLoginDate(user);
			Token token = this.tokenManager.createTokenMobile(user, tokenExpires.getExpires());
			return token;
		}
		return null;
	}
	
	
	@Override
	public Token login(String loginName, String password, TokenExpires tokenExpires) {
		if (StringUtils.hasText(loginName) && StringUtils.hasText(password)) {
			ItcastUser user = this.getUser(loginName);
			if (user == null) {
				throw new RuntimeException("手机号暂未注册");
			}
			if (user.getStatus() == UserStatus.DISABLE.getValue()) {
				throw new RuntimeException("帐号被禁用");
			}
			String salt = user.getSalt();
			if (salt == null) {
				salt = "";
			}
			String expect = CodeUtil.encodePassword(password, salt);
			String actual = user.getPassword();
			if (!expect.equals(actual)) {
				logger.info("actual:{}", actual);
				logger.info("expect:{}", expect);
				throw new RuntimeException("用户名或密码错误");
			}
			this.updateLastLoginDate(user);
			Token token = this.tokenManager.createToken(user, tokenExpires.getExpires());
			return token;
		}
		return null;
	}
	@Override
	public Token login4BBS(String loginName, String password, String smallHeadPhoto, String uuid, TokenExpires tokenExpires) {
		if (StringUtils.hasText(loginName) && StringUtils.hasText(password)) {
			ItcastUser user = this.getUser(loginName);
			if (user == null) {
				throw new RuntimeException("手机号暂未注册");
			}
			if (user.getStatus() == UserStatus.DISABLE.getValue()) {
				throw new RuntimeException("帐号被禁用");
			}
			String salt = user.getSalt();
			if (salt == null) {
				salt = "";
			}
			String expect = CodeUtil.encodePassword(password, salt);
			String actual = user.getPassword();
			if (!expect.equals(actual)) {
				logger.info("actual:{}", actual);
				logger.info("expect:{}", expect);
				throw new RuntimeException("用户名或密码错误");
			}
			this.updateLastLoginDate(user);
			user.setUuid(uuid);
			user.setHeadPhoto(smallHeadPhoto);
			Token token = this.tokenManager.createToken(user, tokenExpires.getExpires());
			return token;
		}
		return null;
	}

	@Override
	public boolean destoryTicket(String ticket) {
		Token token = this.tokenManager.deleteTicket(ticket);
		return token != null ? true : false;
	}

	@Override
	public Token reflushTicket(String ticket) {
		return this.reflushTicket(ticket, TokenExpires.Hour);
	}
	
	@Override
	public Token reflushTicket(String ticket, TokenExpires tokenExpires) {
		return this.tokenManager.reflushTicket(ticket, tokenExpires.getExpires());
	}

	@Override
	public Token validateTicket(String ticket) {
		return this.tokenManager.getTicket(ticket);
	}

	@Override
	public ItcastUser getUser(int id) {
		return this.itcastUserDao.getItcastUser(id);
	}

	@Override
	public List<ItcastUser> getUsersByIds(Set<Integer> ids) {
		return this.itcastUserDao.getUsersByIds(ids);
	}

	@Override
	public ItcastUser getUser(String loginName) {
		return this.itcastUserDao.getItcastUser(loginName);
	}

	@Override
	public List<ItcastUser> getUsersByLoginNames(Set<String> loginNames) {
		return this.itcastUserDao.getUsersByLoginNames(loginNames);
	}

//	@Autowired
//	public void setItcastUserDao(ItcastUserDao itcastUserDao) {
//		this.itcastUserDao = itcastUserDao;
//	}
//
//	@Autowired
//	public void setLoginLimitDao(LoginLimitDao loginLimitDao) {
//		this.loginLimitDao = loginLimitDao;
//	}
//
	@Autowired
	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
		this.tokenManager = new TokenManager(this.cacheService);
	}
	
	private void updateLastLoginDate(ItcastUser user) {
		user.setLastLoginDate(new Date());
		this.itcastUserDao.updateLastLoginDate(user.getId(), user.getLastLoginDate());
	}
	
	private void proccessPassword(ItcastUser itcastUser, boolean genSalt) {
		String salt = itcastUser.getSalt();
		if (!StringUtils.hasText(salt) && genSalt) {
			salt = CodeUtil.generateRandomSalt();
			itcastUser.setSalt(salt);
		}
		logger.info("password:'{}', salt:'{}'", itcastUser.getPassword(), salt);
		String encPassord = CodeUtil.encodePassword(itcastUser.getPassword(), salt);
		itcastUser.setPassword(encPassord);
	}

	@Override
    public TableVo getUsers(TableVo vo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(vo.getsSearch())){
			Gson gson = new Gson();
			List<Map<String, Object>> paramList = gson.fromJson(vo.getsSearch(),new TypeToken<List<Map<String, Object>>>() {}.getType());
			if (paramList.size() > 0) {
				for (Map<String, Object> item : paramList) {
					paramMap.put((String) item.get("propertyName"),item.get("propertyValue1"));
				}
			}
		}
		
		String search = paramMap.get("search") == null ? null : paramMap.get("search").toString();
		String origin = paramMap.get("origin") == null ? null : paramMap.get("origin").toString();
		Object statusobj = paramMap.get("status");
		int status = statusobj == null ? 100 : Integer.valueOf(statusobj.toString());
		return itcastUserDao.getUsers(search,origin,status,
				vo.getiDisplayStart() / vo.getiDisplayLength() + 1,vo.getiDisplayLength());
	}

	@Override
	public Token loginThirdPart(String loginName, String password, TokenExpires tokenExpires) throws Exception {
		if (!StringUtils.hasText(loginName) || !StringUtils.hasText(password)) {
			throw new RuntimeException("参数错误！");
		}
		ItcastUser user = this.getUser(loginName);
		if (user == null) {
			throw new RuntimeException("用户名或密码错误");
		}
		if (user.getStatus() == UserStatus.DISABLE.getValue()) {
			throw new RuntimeException("帐号被禁用");
		}
		String salt = user.getSalt();
		if (salt == null) {
			salt = "";
		}
/*		String expect = CodeUtil.encodePassword(password, salt); */
		String actual = user.getPassword();
		if (!password.equals(actual)) {
			logger.info("actual:{}", actual);
			logger.info("expect:{}", password);
			throw new RuntimeException("用户名或密码错误");
		}
		this.updateLastLoginDate(user);
		return this.tokenManager.createTokenMobile(user, tokenExpires.getExpires());
	}

	@Override
	public Token wechatLogin(String loginName) throws Exception {
		ItcastUser user = this.getUser(loginName);
		this.updateLastLoginDate(user);
		return this.tokenManager.createToken(user, TokenExpires.TenDay.getExpires());
	}
	
	@Override
	public boolean checkPassword(String loginName,String password) {
		if (StringUtils.hasText(loginName) && StringUtils.hasText(password)) {
			
			ItcastUser user = this.getUser(loginName);
			if (user == null) {
				throw new RuntimeException("用户名或密码错误");
			}
			if (user.getStatus() == UserStatus.DISABLE.getValue()) {
				throw new RuntimeException("帐号被禁用");
			}
			String salt = user.getSalt();
			if (salt == null) {
				salt = "";
			}
			String expect = CodeUtil.encodePassword(password, salt);
			String actual = user.getPassword();
			if (!expect.equals(actual)) {
				logger.info("actual:{}", actual);
				logger.info("expect:{}", expect);
				throw new RuntimeException("用户名或密码错误");
			}
			return true;
		}
		return false;
	}



	@Override
	public Token loginForLimit(String loginName, String password, TokenExpires tokenExpires, int clientType, String info) {
		Token t = login(loginName,password,tokenExpires);
		if(t!=null){
			updateLoginLimit(loginName,clientType,info);
			return t;
		}
		return null;
	}

	@Override
	public void updateLoginLimit(String loginName, int clientType, String info) {
		LoginLimit ll = loginLimitDao.getLoginLimitByLoginName(loginName);
		if(ll==null){
			ll = new LoginLimit();
			ll.setLoginName(loginName);
		}
		String sign = UUID.randomUUID().toString().replaceAll("-", "");;
		Date lastTime = new Date();
		switch (clientType){
			case 1:
				ll.setPcSign(sign);
				ll.setPcInfo(info);
				ll.setPcLastTime(lastTime);
				break;
			case 2:
				ll.setH5Sign(sign);
				ll.setH5Info(info);
				ll.setH5LastTime(lastTime);
				break;
			case 3:
				ll.setAppSign(sign);
				ll.setAppInfo(info);
				ll.setAppLastTime(lastTime);
				break;
			default:
				break;
		}
		if(ll.getId()==null){
			loginLimitDao.addLoginList(ll);
		}else{
			loginLimitDao.updateLoginLimit(ll);
		}
	}
	@Override
	public void updatePasswordAndLoginName(int id, String userName,
			String passWord) {
		// TODO Auto-generated method stub
		ItcastUser user = this.getUser(id);
		if (user == null) {
			logger.warn("没有找到登录名'{}'的用户.");
			return;
		}
		/**
		 * 更新用户和密码
		 */
		if(passWord !=null){
			user.setPassword(passWord);
			this.proccessPassword(user, false);
			itcastUserDao.updatePasswordAndLoginName(id, user.getPassword(), userName);
		}
		this.itcastUserDao.updateLoginName(user.getLoginName(), userName);
	}
}
