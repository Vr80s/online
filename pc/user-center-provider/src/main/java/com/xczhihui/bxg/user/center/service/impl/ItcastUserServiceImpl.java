package com.xczhihui.bxg.user.center.service.impl;

import java.util.*;

import com.xczhihui.bxg.user.center.dao.LoginLimitDao;
import com.xczhihui.bxg.user.center.exception.LoginRegException;
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
			throw new LoginRegException("用户名、密码不允许为空！");
		}
		ItcastUser u = this.getUser(loginName);
		if (u != null) {
			throw new LoginRegException("用户名'" + u.getLoginName() + "'已被注册");
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
		if(oldPassword!=null && newPassword!=null && oldPassword.equals(newPassword)){
			logger.info("newPassword:{},oldPassword:{}", newPassword, oldPassword);
			throw new LoginRegException("新密码不能与旧密码相同");
		}
		
		if (StringUtils.hasText(oldPassword)) {
			String expect = user.getPassword();
			String actual = CodeUtil.encodePassword(oldPassword, user.getSalt());
			if (!expect.equals(actual)) {
				logger.info("actual:{},expect:{}", actual, expect);
				throw new LoginRegException("原密码错误");
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
			throw new LoginRegException("登录名'" + newLoginName + "'已存在.");
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
				throw new LoginRegException("手机号暂未注册");
			}
			if (user.getStatus() == UserStatus.DISABLE.getValue()) {
				throw new LoginRegException("账号被禁用");
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
				throw new LoginRegException("用户名或密码错误");
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
				throw new LoginRegException("手机号暂未注册");
			}
			if (user.getStatus() == UserStatus.DISABLE.getValue()) {
				throw new LoginRegException("帐号被禁用");
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
				throw new LoginRegException("用户名或密码错误");
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
				throw new LoginRegException("手机号暂未注册");
			}
			if (user.getStatus() == UserStatus.DISABLE.getValue()) {
				throw new LoginRegException("帐号被禁用");
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
				throw new LoginRegException("用户名或密码错误");
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
	public Token reflushTicket(String ticket, TokenExpires tokenExpires) {
		return this.tokenManager.reflushTicket(ticket, tokenExpires.getExpires());
	}

	@Override
	public ItcastUser getUser(int id) {
		return this.itcastUserDao.getItcastUser(id);
	}

	@Override
	public ItcastUser getUser(String loginName) {
		return this.itcastUserDao.getItcastUser(loginName);
	}

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
	public Token loginThirdPart(String loginName, String password, TokenExpires tokenExpires) throws Exception {
		if (!StringUtils.hasText(loginName) || !StringUtils.hasText(password)) {
			throw new LoginRegException("参数错误！");
		}
		ItcastUser user = this.getUser(loginName);
		if (user.getStatus() == UserStatus.DISABLE.getValue()) {
			throw new LoginRegException("帐号被禁用");
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
			throw new LoginRegException("用户名或密码错误");
		}
		this.updateLastLoginDate(user);
		return this.tokenManager.createTokenMobile(user, tokenExpires.getExpires());
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
