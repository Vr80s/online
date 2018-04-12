package com.xczhihui.support.shiro;

import org.apache.shiro.SecurityUtils;

/**
 * 管理后台用户封装类
 *
 * @author hejiwei
 */
public class ManagerUserUtil {

	public static Principal getPrincipal() {
		return (Principal) SecurityUtils.getSubject().getPrincipal();
	}

	public static String getId() {
		return getPrincipal().getId();
	}

	public static String getUsername() {
		return getPrincipal().getUsername();
	}

	public static String getName() {
		return getPrincipal().getName();
	}
}
