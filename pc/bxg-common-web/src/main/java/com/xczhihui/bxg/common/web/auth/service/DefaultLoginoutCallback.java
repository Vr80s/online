package com.xczhihui.bxg.common.web.auth.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 登入登出回调缺省回调
 * 
 * @author liyong
 *
 */
public class DefaultLoginoutCallback implements LoginoutCallback {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onLogin(HttpServletRequest request, HttpServletResponse response) {
		logger.info("logined");
	}

	@Override
	public void onLogout(HttpServletRequest request, HttpServletResponse response) {
		logger.info("logouted");
	}

}
