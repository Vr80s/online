package com.xczh.consumer.market.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xczhihui.user.center.bean.TokenExpires;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 用户中心相关的cookie操作。
 * 
 * @author Alex Wang
 */
public class UCCookieUtil {

	static Logger logger = LoggerFactory.getLogger(UCCookieUtil.class);

	private final static String DEFAULT_DOMAIN = "ixincheng.com";

	/**
	 * 用户中心token cookie名
	 */
	private final static String COOKIE_TOKEN_NAME = "_uc_t_";
	
	/**
	 * cookie名,用户判断第三方账户是否绑定了用户信息
	 */
	private final static String  THIRD_PARTY_COOKIE_TOKEN_NAME = "third_party_uc_t_";

	/**
	 * 从cookie构造token
	 * 
	 * @param request
	 * @return cookie没有信息时返回null
	 */
	public static Token readTokenCookie(HttpServletRequest request) {
		String str = CookieUtil.getCookieValue(request, COOKIE_TOKEN_NAME);
		if (str == null || str.length() < 1) {// 没有token信息
			return null;
		}
		Token token = decodeToken(str);
		return token;
	}
	/**
	 * 将token中的信息写入cookie。
	 * 
	 * @param response
	 * @param token
	 */
	public static void writeTokenCookie(HttpServletResponse response, Token token) {
		String str = encodeToken(token);
		writeBXGCookie(response, COOKIE_TOKEN_NAME, str, token.getExpires());
	}
	/**
	 * 清除cookie中的token信息。
	 */
	public static void clearTokenCookie(HttpServletResponse response) {
		clearBXGCookie(response, COOKIE_TOKEN_NAME);
	}
	
	
	/**
	 * 将token中的信息写入cookie。  -- 第三方cookie
	 * 
	 * @param response
	 * @param token
	 */
	public static void writeThirdPartyCookie(HttpServletResponse response, ThridFalg tf) {
		String str;
		try {
			//加码
			String openId = tf.getOpenId();
			String unionId = tf.getUnionId();
			String v = String.format("%s;%s", openId, unionId);
			str = URLEncoder.encode(v, "UTF-8");
			writeBXGCookie(response, THIRD_PARTY_COOKIE_TOKEN_NAME, str, TokenExpires.TenDay.getExpires());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 清除cookie中的token信息。 -- 第三方cookie
	 */
	public static void clearThirdPartyCookie(HttpServletResponse response) {
		clearBXGCookie(response, THIRD_PARTY_COOKIE_TOKEN_NAME);
	}
	
	/**
	 * 从cookie构造token   -- 第三方cookie
	 * 
	 * @param request
	 * @return cookie没有信息时返回null
	 */
	public static ThridFalg readThirdPartyCookie(HttpServletRequest request) {
		String str = CookieUtil.getCookieValue(request, THIRD_PARTY_COOKIE_TOKEN_NAME);
		if (str == null || str.length() < 1) {// 没有token信息
			return null;
		}
		try {
			//解码
			str = URLDecoder.decode(str, "UTF-8");
			String[] strs = str.split(";");
			String openId =strs[0].trim();
			String unionId = strs[1].trim();
			
			ThridFalg tf = new ThridFalg();
			tf.setOpenId(openId);
			tf.setUnionId(unionId);
			
			return tf;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * 将token组成字符串，并用URLEncoder编码。
	 * 
	 * @param token
	 * @return
	 */
	static String encodeToken(Token token) {
		int id = token.getUserId();
		String loginName = token.getLoginName();
		String ticket = token.getTicket();
		String origin = token.getOrigin();
		long expires = token.getExpires();
		String v = String.format("%d;%s;%s;%s;%d", id, loginName, ticket, origin, expires);
		try {
			v = URLEncoder.encode(v, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return v;
	}

	/**
	 * 从字符串中编码出token。
	 * 
	 * @param str
	 * @return
	 */
	static Token decodeToken(String str) {
		Token t = null;
		try {
			str = URLDecoder.decode(str, "UTF-8");
			String[] strs = str.split(";");
			int id = Integer.parseInt(strs[0].trim());
			String loginName = strs[1].trim();
			String ticket = strs[2].trim();
			String origin = strs[3].trim();
			long expires = Long.parseLong(strs[4].trim());
			t = new Token();
			t.setUserId(id);
			t.setLoginName(loginName);
			t.setTicket(ticket);
			t.setOrigin(origin);
			t.setExpires(expires);
		} catch (Exception e) {
			logger.warn("parse user center cookie " + str + " error:", e.getMessage());
		}
		return t;
	}

	private static void clearBXGCookie(HttpServletResponse response, String name) {
		CookieUtil.setCookie(response, name, "", DEFAULT_DOMAIN, "/", 0);
	}

	private static void writeBXGCookie(HttpServletResponse response, String name, String value, long maxAge) {
		long age = maxAge - System.currentTimeMillis();
		age = age / 1000;
		CookieUtil.setCookie(response, name, value, DEFAULT_DOMAIN, "/", (int) age);
	}

}
