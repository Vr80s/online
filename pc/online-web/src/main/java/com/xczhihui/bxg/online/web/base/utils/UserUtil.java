package com.xczhihui.bxg.online.web.base.utils;

import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.user.center.bean.Token;
import com.xczhihui.user.center.web.utils.UCCookieUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录退出相关
 * 
 * @author Haicheng Jiang
 *
 */
public class UserUtil {
	public static void setSessionCookie(HttpServletRequest request, HttpServletResponse response, BxgUser user,
			Token token) {
		UserLoginUtil.setLoginUser(request, user);
		UCCookieUtil.writeTokenCookie(response, token);
	}
	
	public static void cleanSessionCookie(HttpServletRequest request, HttpServletResponse response) {
		UserLoginUtil.setLoginUser(request, null);
		UCCookieUtil.clearTokenCookie(response);
	}
}
