package com.xczhihui.bxg.common.web.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.auth.TokenHolder;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.common.web.auth.bean.UCenterNamePasswordToken;
import com.xczhihui.bxg.common.web.auth.service.DefaultLoginoutCallback;
import com.xczhihui.bxg.common.web.auth.service.LoginoutCallback;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.Token;
import com.xczhihui.user.center.web.utils.UCCookieUtil;

/**
 * 处理登录(包括用户中心的统一登录功能)的shiro拦截器。
 * 
 * @author liyong
 *
 */
public class BxgAuthcFilter extends FormAuthenticationFilter {

	static final Logger logger = LoggerFactory.getLogger(BxgAuthcFilter.class);

	@Autowired
	private UserCenterAPI userCenterAPI;

	private LoginoutCallback loginoutCallback = new DefaultLoginoutCallback();

	@Override
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		BxgUser user = UserLoginUtil.getLoginUser(req);
		Token token = UCCookieUtil.readTokenCookie(req);
		if (this.isNeedLogout(user, token, req)) {
			UserLoginUtil.onLogout(req, (HttpServletResponse) response);
			this.loginoutCallback.onLogout(req, (HttpServletResponse) response);
			Subject subject = getSubject(request, response);
			if (subject.isAuthenticated()) {
				subject.logout();
			}
			return super.onPreHandle(request, response, mappedValue);
		}
		// cookie中有票，但当前登录用户不存在，或当前登录用户与cookie中的不一致，以cookie为准，重新登录。
		if (token != null && (user == null || !user.getLoginName().equals(token.getLoginName()))) {
			Token t = this.userCenterAPI.validateTicket(token.getTicket());
			logger.info("cookie ticket:{} validateTicket:{} sessionUser loginName:{} ", token.getTicket(), t,
					user == null ? "null" : user.getLoginName());
			if (t != null) {
				Subject subject = SecurityUtils.getSubject();
				UCenterNamePasswordToken npt = new UCenterNamePasswordToken(t.getTicket());
				try {
					subject.login(npt);
					UserLoginUtil.onLogin(req, (HttpServletResponse) response);
					this.loginoutCallback.onLogin(req, (HttpServletResponse) response);
				} catch (Exception e) {// 用在其他站点登录，但不是该站点的用户。
					logger.warn(e.getMessage());
				}
			}
		}
		if (user != null) {
			UserHolder.setCurrentUser(user);
		}
		if (token != null) {
			TokenHolder.setCurrentToken(token);
		}
		if (isLoginRequest(request, response)) {
			Subject subject = getSubject(request, response);
			if (subject.isAuthenticated()) {
				try {
					logger.info("Authenticated redirect to success!");
					issueSuccessRedirect(request, response);
					return false;
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
		return super.onPreHandle(request, response, mappedValue);
	}

	private boolean isNeedLogout(BxgUser user, Token token, HttpServletRequest req) {
		// cookie中没有票，但当前登录用户存在，需要注销；(用户在其他系统注销)
		String domain = req.getServerName();
		// 本机测试服务
		boolean localhost = domain.indexOf("localhost") > -1 || domain.indexOf("127.0.0.1") > -1 || domain.indexOf("ixincheng.com") > -1;
		if (token == null && user != null && !localhost) {
//		if (token == null && user != null ) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		
		UserLoginUtil.onLogin(httpServletRequest,httpServletResponse);
		this.loginoutCallback.onLogin(httpServletRequest,httpServletResponse);
		
		//ajax请求
		if ("XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {
			httpServletResponse.setCharacterEncoding("utf-8");
			httpServletResponse.setContentType("application/json; charset=utf-8");
			httpServletResponse.getWriter().write(new Gson().toJson(ResponseObject.newSuccessResponseObject(null)));
			return false;
        } else {//非ajax请求
        	return super.onLoginSuccess(token, subject, request, response);
        }
		
	}

	@Override
	protected AuthenticationToken createToken(String username, String password, boolean rememberMe, String host) {
		UCenterNamePasswordToken token = new UCenterNamePasswordToken(username, password);
		token.setRememberMe(rememberMe);
		token.setHost(host);
		return token;
	}

	@Override
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
		logger.info(ae.getMessage(), ae);
		request.setAttribute("shiroLoginException", ae);
		super.setFailureAttribute(request, ae);
	}

	public void setLoginoutCallback(LoginoutCallback callback) {
		this.loginoutCallback = callback;
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,ServletResponse response) {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		//ajax请求
		if ("XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {
			try {
				httpServletResponse.setContentType("application/json; charset=utf-8");
				httpServletResponse.setCharacterEncoding("utf-8");
				httpServletResponse.getWriter().write(new Gson().toJson(ResponseObject.newErrorResponseObject("用户名密码错误！")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
        } else {//非ajax请求
        	return super.onLoginFailure(token, e, request, response);
        }
	}
}
