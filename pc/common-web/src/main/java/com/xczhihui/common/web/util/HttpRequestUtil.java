package com.xczhihui.common.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 和http请求相关的工具类。
 * 
 * @author liyong
 *
 */
public final class HttpRequestUtil {

	/**
	 * 判断请求是否由移动端发出。
	 * 
	 * @param request
	 * @return
	 */
	public boolean isMobileRequest(HttpServletRequest request) {
		String ua = getRequestUserAgent(request);
		if (ua != null && ua.trim().length() > 0) {
			ua = ua.toLowerCase();
			if (ua.indexOf("phone") > -1 || ua.indexOf("ipad") > -1 || ua.indexOf("android") > -1
					|| ua.indexOf("ios") > -1 || ua.indexOf("mobile") > -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取请求的UserAgent
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestUserAgent(HttpServletRequest request) {
		return request.getHeader("user-agent");
	}

	/**
	 * 获取请求的Referer
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestReferer(HttpServletRequest request) {
		return request.getHeader("Referer");
	}

	/**
	 * 
	 * 获取IP地址
	 * 
	 * @param request
	 * @return String
	 */
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip != null && ip.trim().length() > 0) {
			return ip;
		} else {
			return "0.0.0.0";
		}
	}
}
