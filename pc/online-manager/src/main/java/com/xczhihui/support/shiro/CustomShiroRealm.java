package com.xczhihui.support.shiro;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.user.service.UserService;
import com.xczhihui.user.center.utils.CodeUtil;

@Component
public class CustomShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		Principal principal = (Principal) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> roles = userService.findRoles(principal.getId());
		if (roles != null && roles.size() > 0) {
			info.addRoles(roles);
		}
		Set<String> permissions = userService
				.findPermissions(principal.getId());
		if (permissions != null && permissions.size() > 0) {
			info.addStringPermissions(permissions);
		}
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		String username = userToken.getUsername();
		if (StringUtils.isBlank(username)) {
			throw new UnknownAccountException("用户名不能为空");
		}
		User user = userService.getUserByLoginName(username);
		if (user == null) {
			throw new UnknownAccountException("用户不存在");
		}
		if (user.isDelete()) {
			throw new LockedAccountException("账号已被删除");
		}

		if (!user.getPassword().equals(
				CodeUtil.encodePassword(new String(userToken.getPassword()),
						user.getSalt()))) {
			throw new IncorrectCredentialsException("密码错误");
		}

		return new SimpleAuthenticationInfo(new Principal(user),
				userToken.getPassword(), getName());
	}
}
