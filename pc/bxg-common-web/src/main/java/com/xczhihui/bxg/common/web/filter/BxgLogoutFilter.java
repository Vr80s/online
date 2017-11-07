package com.xczhihui.bxg.common.web.filter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.web.auth.service.DefaultLoginoutCallback;
import com.xczhihui.bxg.common.web.auth.service.LoginoutCallback;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;

/**
 * 退出登录shiro拦截器，包含退出后的清理工作。
 * 
 * @author liyong
 *
 */
public class BxgLogoutFilter extends LogoutFilter {

	static final Logger logger = LoggerFactory.getLogger(BxgLogoutFilter.class);

	private LoginoutCallback loginoutCallback = new DefaultLoginoutCallback();
	
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		/*
		BxgUser user = UserLoginUtil.getLoginUser((HttpServletRequest)request);
		if (user != null) {
			String msg = "此次退出的学生是" + user.getLoginName() + ",时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			logger.error(msg);
			System.out.println(msg);
		}
		*/
		UserLoginUtil.onLogout((HttpServletRequest) request, (HttpServletResponse) response);
		this.loginoutCallback.onLogout((HttpServletRequest) request, (HttpServletResponse) response);
		return super.preHandle(request, response);
	}

	public void setLoginoutCallback(LoginoutCallback callback) {
		this.loginoutCallback = callback;
	}
}