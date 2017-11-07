package com.xczhihui.bxg.common.web.auth.service;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.web.auth.TokenHolder;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.common.web.auth.bean.UCenterNamePasswordToken;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.Token;
import com.xczhihui.user.center.bean.TokenExpires;

/**
 * 基于shiro认证和授权接口
 * 
 * @author liyong
 */
public class ShiroRealmAuthService extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(ShiroRealmAuthService.class);

	private AuthFinder authFinder;

	@Autowired
	private UserCenterAPI userCenterAPI;

	private TokenExpires tokenExpires = TokenExpires.Day;

	public ShiroRealmAuthService() {
		this.setAuthenticationTokenClass(UCenterNamePasswordToken.class);
		this.setCredentialsMatcher(new AllowAllCredentialsMatcher());
	}

	/**
	 * 获取用户的权限信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		BxgUser user = (BxgUser) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> roles = this.authFinder.findRoles(user.getId());
		System.out.println("roles:" + (roles == null));
		if (roles != null && roles.size() > 0) {
			info.addRoles(roles);
		}
		Set<String> permissions = this.authFinder.findPermissions(user.getId());
		System.out.println("roles permissions:" + (permissions == null));
		if (permissions != null && permissions.size() > 0) {
			info.addStringPermissions(permissions);
		}
		logger.info("{} roles:{} \npermissions:{}", user.getLoginName(), roles, permissions);
		return info;
	}

	/**
	 * 获取用户的认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UCenterNamePasswordToken userToken = (UCenterNamePasswordToken) token;
		Token ut = null;
		String loginName = userToken.getUsername();
		String password = userToken.getPassword() != null ? new String(userToken.getPassword()) : null;
		String ticket = userToken.getTicket();
		if (StringUtils.hasText(loginName) && StringUtils.hasText(password)) {
			ut = this.userCenterAPI.login(loginName, password, tokenExpires);
		} else if (StringUtils.hasText(ticket)) {
			ut = this.userCenterAPI.validateTicket(ticket);
		}
		if (ut != null) {
			BxgUser user = this.authFinder.findUserByLoginName(ut.getLoginName());
			if (user != null) {
				UserHolder.setCurrentUser(user);
				TokenHolder.setCurrentToken(ut);
				return new SimpleAuthenticationInfo(user, password, null, super.getName());
			}
		}
		return null;
	}

	@Override
	public void onLogout(PrincipalCollection principals) {
		Token token = TokenHolder.getCurrentToken();
		if (token != null) {
			this.userCenterAPI.destoryTicket(token.getTicket());
		}
		UserHolder.setCurrentUser(null);
		TokenHolder.setCurrentToken(null);
		super.onLogout(principals);
	}

	public void setAuthFinder(AuthFinder authFinder) {
		this.authFinder = authFinder;
	}

	public void setTokenExpiresType(int type) {
		this.tokenExpires = TokenExpires.Day;
	}
}
