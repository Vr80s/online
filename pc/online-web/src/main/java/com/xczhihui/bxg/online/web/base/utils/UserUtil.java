package com.xczhihui.bxg.online.web.base.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.web.auth.TokenHolder;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.user.center.bean.Token;
import com.xczhihui.user.center.web.utils.UCCookieUtil;

/**
 * 用户登陆退出相关
 * 
 * @author Haicheng Jiang
 *
 */
public class UserUtil {
	public static void setSessionCookie(HttpServletRequest request, HttpServletResponse response, BxgUser user,
			Token token) {
		UserLoginUtil.setLoginUser(request, user);
		UCCookieUtil.writeTokenCookie(response, token);
		TokenHolder.setCurrentToken(token);
	}
	
	public static void cleanSessionCookie(HttpServletRequest request, HttpServletResponse response) {
		UserLoginUtil.setLoginUser(request, null);
		UCCookieUtil.clearTokenCookie(response);
	}
}