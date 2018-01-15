package com.xczh.consumer.market.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户中心相关的cookie操作。
 * 
 * @author Alex Wang
 */
public class CookieUtil {

	static Logger logger = LoggerFactory.getLogger(CookieUtil.class);

	/**
	 * 根据Cookie名称得到Cookie的值，没有返回Null
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		if (cookie != null) {
			return cookie.getValue();
		} else {
			return null;
		}
	}

	/**
	 * 根据Cookie名称得到Cookie对象，不存在该对象则返回Null
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie cookies[] = request.getCookies();
		if (cookies == null || name == null || name.length() == 0) {
            return null;
        }
		Cookie cookie = null;
		for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
			if (cookie.getName().equals(name)) {
				break;
			}
			cookie = null;
			/*
			 * if (request.getServerName().equals(cookie.getDomain())) break;
			 */
		}

		return cookie;
	}

	/**
	 * 构造一个当前domain，path=/的不存储的cookie
	 * 
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void setCookie(HttpServletResponse response, String name,
	        String value) {
		setCookie(response, name, value, "");
	}

	/**
	 * 构造一个path=/的不存储的cookie
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param domain
	 */
	public static void setCookie(HttpServletResponse response, String name,
	        String value, String domain) {
		setCookie(response, name, value, domain, "/");
	}

	/**
	 * 构造一个不存储的cookie
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param domain
	 * @param path
	 */
	public static void setCookie(HttpServletResponse response, String name,
	        String value, String domain, String path) {
		setCookie(response, name, value, domain, path, -1);
	}

	/**
	 * 构造一个cookie
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param domain
	 * @param path
	 * @param maxAge
	 *            seconds
	 */
	public static void setCookie(HttpServletResponse response, String name,
	        String value, String domain, String path, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		//cookie.setDomain(DOMAIN);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
}
