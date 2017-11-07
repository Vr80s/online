package com.xczhihui.bxg.online.manager.common.util;

import org.springframework.util.StringUtils;

import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.user.center.utils.CodeUtil;

public class PasswordUtil {

	/**
	 * 将用的密码加密。
	 * 
	 * @param user
	 * @return
	 */
	public static String encodeUserPassword(User user) {
		String pwd = user.getPassword();
		if (!StringUtils.hasText(pwd)) {
			throw new IllegalArgumentException("密码不能为空");
		}
		String loginName = user.getLoginName();
		if (!StringUtils.hasText(loginName)) {
			throw new IllegalArgumentException("登录名不能为空");
		}
		return CodeUtil.encodePassword(pwd, loginName);
	}
}
