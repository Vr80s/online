package com.xczhihui.user.center.web.utils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xczhihui.user.center.bean.ThridFalg;
import com.xczhihui.user.center.bean.TokenExpires;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xczhihui.user.center.bean.Token;

/**
 * 用户中心相关的cookie操作。
 * 
 * @author liyong
 */
public class UCCookieUtil {

	static Logger logger = LoggerFactory.getLogger(UCCookieUtil.class);

	/**
	 * cookie名,用户判断第三方账户是否绑定了用户信息
	 */
	private final static String  THIRD_PARTY_COOKIE_TOKEN_NAME = "third_party_uc_t_";

	private  static String DEFAULT_DOMAIN ;
	/**
	 * 用户中心token cookie名
	 */
	private static String COOKIE_TOKEN_NAME;

	

	static{
		InputStream in = null;
		try{
			Properties properties = new Properties();
			in =UCCookieUtil.class.getClassLoader().getResourceAsStream("config.properties");
			properties.load(in);

			//微信公众号和h5
			DEFAULT_DOMAIN = properties.getProperty("domain");
			COOKIE_TOKEN_NAME = properties.getProperty("cookieTokenName");

			logger.info("读取配置信息成功！");
		}catch(Exception e){
			e.printStackTrace();
			logger.info("读取配置信息失败！");
		}finally{
			if(in != null){
				try{
					in.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}




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
		CookieUtil.setCookie(response, name, "", null, "/", 0);//20171215 yuxin
	}

	private static void writeBXGCookie(HttpServletResponse response, String name, String value, long maxAge) {
		long age = maxAge - System.currentTimeMillis();
		age = age / 1000;
		CookieUtil.setCookie(response, name, value, DEFAULT_DOMAIN, "/", (int) age);
	}

	/**
	 * 清除cookie中的token信息。 -- 第三方cookie
	 */
	public static void clearThirdPartyCookie(HttpServletResponse response) {
		clearBXGCookie(response, THIRD_PARTY_COOKIE_TOKEN_NAME);
	}

	/**
	 * 将token中的信息写入cookie。  -- 第三方cookie
	 *
	 * @param response
	 */
	public static void writeThirdPartyCookie(HttpServletResponse response, ThridFalg tf) {
		String str;
		try {
			//加码
			String openId = tf.getOpenId();
			String unionId = tf.getUnionId();
			String nickName = tf.getNickName();
			String headImg = tf.getHeadImg();

			String v = String.format("%s;%s;%s;%s", openId, unionId,nickName,headImg);
			str = URLEncoder.encode(v, "UTF-8");

			writeBXGCookie(response, THIRD_PARTY_COOKIE_TOKEN_NAME, str, TokenExpires.TenDay.getExpires());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			String nickName = strs[2].trim();
			String headImg = strs[3].trim();

			ThridFalg tf = new ThridFalg();
			tf.setOpenId(openId);
			tf.setUnionId(unionId);
			tf.setNickName(nickName);
			tf.setHeadImg(headImg);

			return tf;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

	}

}
