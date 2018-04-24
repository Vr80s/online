package com.xczhihui.bxg.common.web.util;

import com.xczhihui.common.support.domain.BxgUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理和登录相关的操作。
 * 
 * @author liyong
 *
 */
final public class UserLoginUtil {
	protected static Logger logger = LoggerFactory.getLogger(UserLoginUtil.class);

	private static final String SESSION_USER = "_user_";

	/**
	 * 获取当前登录用户。
	 * 
	 * @param request
	 * @return
	 */
	public static BxgUser getLoginUser(HttpServletRequest request) {
		BxgUser bxgUser = (BxgUser) (request.getSession().getAttribute(SESSION_USER));
		return bxgUser;
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

}
