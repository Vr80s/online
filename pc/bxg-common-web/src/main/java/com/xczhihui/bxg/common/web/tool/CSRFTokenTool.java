package com.xczhihui.bxg.common.web.tool;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xczhihui.bxg.common.web.annotation.PageTool;

/**
 * 处理跨站请求伪造。
 * 
 * @author liyong
 *
 */
@PageTool
@Component("csrfTokenTool")
public final class CSRFTokenTool {

	private static Logger logger = LoggerFactory.getLogger(CSRFTokenTool.class);

	private final static String CSRF_PARAM_NAME = "_csrft";

	/**
	 * 获取Server端的csrf token如果没创建一个。
	 * 
	 * @param userId
	 * @return
	 */
	public String getTokenFromServer(int userId) {
		// TODO Get token from redis by userId
		String token = "123456";
		token = "123456";
		// if (null == token) {
		// token = UUID.randomUUID().toString();
		// // TODO Set token to redis
		// }
		return token;
	}

	/**
	 * 获取客户端传来的csrf token
	 * 
	 * @param request
	 * @return
	 */
	public String getTokenFromClient(HttpServletRequest request) {
		String token = request.getParameter(CSRF_PARAM_NAME);
		if (token == null || "".equals(token)) {
			token = request.getHeader(CSRF_PARAM_NAME);
		}
		return token;
	}

	/**
	 * 检查csrf token
	 * 
	 * @param request
	 * @return
	 */
	public boolean checkCSRFToken(HttpServletRequest request, int userId) {
		String serverToken = getTokenFromServer(userId);
		String clientToken = getTokenFromClient(request);
		if (serverToken.equals(clientToken)) {
			return true;
		} else {
			logger.warn("expect '{}' actual '{}'", serverToken, clientToken);
		}
		return false;
	}

	/**
	 * 在页面上输出_csrft的hidden元素
	 * 
	 * @param userId
	 * @return
	 */
	public String genCSRFHiddenInput(int userId) {
		String input = "<input type='hidden' name='" + CSRF_PARAM_NAME + "' value='" + getTokenFromServer(userId)
				+ "'/>";
		return input;
	}

	/**
	 * 在页面上输出_csrft的hidden元素
	 * 
	 * @return
	 */
	public String getCSRFHiddenInput() {
		int userId = 1234;// 当前登录用户
		return this.genCSRFHiddenInput(userId);
	}
}
