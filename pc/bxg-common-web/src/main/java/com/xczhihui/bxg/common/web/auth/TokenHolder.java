package com.xczhihui.bxg.common.web.auth;

import com.xczhihui.user.center.bean.Token;

/**
 * 放置当前用户中心Token。
 * 
 * @author liyong
 *
 */
public class TokenHolder {
	private static final ThreadLocal<Token> currentToken = new ThreadLocal<Token>();

	/**
	 * 修改当前登录用户Token(一般在登录或退出时调用)
	 * 
	 * @param token
	 */
	public static void setCurrentToken(Token token) {
		currentToken.set(token);
	}

	/**
	 * 获取当前登录用户Token
	 * 
	 * @return 如果未登录返回null
	 */
	public static Token getCurrentToken() {
		return currentToken.get();
	}

	/**
	 * 获取当前登录用户Token
	 * 
	 * @exception 当前登录用户Token不存在抛出RuntimeException
	 * @return
	 */
	public static Token getRequireCurrentToken() {
		Token token = currentToken.get();
		if (token == null) {
			throw new RuntimeException("Current token is null.");
		}
		return token;
	}
}
