package com.xczhihui.common.web.auth.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登入登出回调
 * 
 * @author liyong
 *
 */
public interface LoginoutCallback {

	/**
	 * 登录成功时回调。
	 * 
	 * @param request
	 * @param response
	 */
	public void onLogin(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 退出登录时回调
	 * 
	 * @param request
	 * @param response
	 */
	public void onLogout(HttpServletRequest request, HttpServletResponse response);
}
