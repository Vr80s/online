package com.xczhihui.bxg.common.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.web.auth.TokenHolder;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.user.center.bean.Token;
import com.xczhihui.user.center.web.utils.UCCookieUtil;

/**
 * 处理和登录相关的操作。
 * 
 * @author liyong
 *
 */
final public class UserLoginUtil {
	protected static Logger logger = LoggerFactory.getLogger(UserLoginUtil.class);

	private static final String SESSION_TOKEN = "_token_";
	private static final String SESSION_USER = "_user_";

	/**
	 * 获取当前登录用户。
	 * 
	 * @param request
	 * @return
	 */
	public static BxgUser getLoginUser(HttpServletRequest request) {
		return (BxgUser) (request.getSession().getAttribute(SESSION_USER));
	}

	/**
	 * 修改登录用户
	 * 
	 * @param request
	 * @param user
	 */
	public static void setLoginUser(HttpServletRequest request, BxgUser user) {
		request.getSession().setAttribute(SESSION_USER, user);
	}

	/**
	 * 成功登录后调用，设置session属性(token, user)和SSO cookie。
	 * 
	 * @param request
	 * @param response
	 */
	public static void onLogin(HttpServletRequest request, HttpServletResponse response) {
		Token token = TokenHolder.getRequireCurrentToken();
		BxgUser user = UserHolder.getRequireCurrentUser();
		request.getSession().setAttribute(SESSION_TOKEN, token);
		request.getSession().setAttribute(SESSION_USER, user);
		UCCookieUtil.writeTokenCookie(response, token);
	}

	/**
	 * 退出登录后调用，使session无效并清除SSO cookie。
	 * 
	 * @param request
	 * @param response
	 */
	public static void onLogout(HttpServletRequest request, HttpServletResponse response) {
		Token token = UCCookieUtil.readTokenCookie(request);
		if (token != null) {
			request.getSession().removeAttribute(SESSION_TOKEN);
			request.getSession().removeAttribute(SESSION_USER);
			UCCookieUtil.clearTokenCookie(response);
		}
	}
}
