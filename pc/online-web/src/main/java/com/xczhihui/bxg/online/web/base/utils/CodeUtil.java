package com.xczhihui.bxg.online.web.base.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5等工具
 * 
 * @author liyong
 *
 */
public class CodeUtil {

	static Logger logger = LoggerFactory.getLogger(CodeUtil.class);

	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	private static char hexDigitsLower[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	private static SecureRandom secureRandom = new SecureRandom();

	/**
	 * 产生一个不带'-'的UUID字符串
	 * 
	 * @return
	 */
	public static String getRandomUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 对字符串进行MD5摘要，字符编码为UTF-8，md5后的字符串是大写。
	 * 
	 * @param text
	 *            要摘要的字符串
	 * @return
	 */
	public static String md5Upper(String text) {
		return md5(text, false);
	}

	/**
	 * 对字符串进行MD5摘要，字符编码为UTF-8，md5后的字符串是小写。
	 * 
	 * @param text
	 *            要摘要的字符串
	 * @return
	 */
	public static String md5Lower(String text) {
		return md5(text, true);
	}

	/**
	 * 对byte[] 进行md5摘要，md5后的字符串是大写。
	 * 
	 * @param data
	 * @return
	 */
	public static String md5Upper(byte[] data) {
		return md5(data, false);
	}

	/**
	 * 对byte[] 进行md5摘要，md5后的字符串是小写。
	 * 
	 * @param data
	 * @return
	 */
	public static String md5Lower(byte[] data) {
		return md5(data, true);
	}

	/**
	 * 生成随机的numBytes位字符串。
	 * 
	 * @param numBytes
	 *            字符串长度，必须是偶数。
	 */
	public static String generateRandomString(int numBytes) {
		if (numBytes < 1) {
			throw new RuntimeException("字节数参数必需是正整数");
		}

		byte[] bytes = new byte[numBytes / 2];
		secureRandom.nextBytes(bytes);
		return encodeHexString(bytes);
	}

	/**
	 * 
	 * @param salt
	 *            盐值
	 * @param plainText
	 *            明文
	 * @return md5(md5(plainText)+salt)
	 */
	public static String entryptText(String plainText, String salt) {
		String ret = md5Lower(plainText);
		if (salt != null) {
			ret += salt.trim();
		}
		return md5Lower(ret);
	}

	/**
	 * 校验签名参数值。
	 * 
	 * @param params
	 *            客户端请求的参数，必须包括签名参数sig。
	 * @param privateKey
	 *            由服务端提供给客户端的私钥。
	 */
	public static void checkSignature(Map<String, String> params, String privateKey) {
		String actualSignature = params.get("sig");
		String expectSignature = calSignature(params, privateKey);
		if (actualSignature == null || expectSignature == null || !expectSignature.equals(actualSignature)) {
			logger.warn("sig not match,actual:{}. expect:{}", actualSignature, expectSignature);
			throw new IllegalArgumentException("Signature not match");
		}
	}

	/**
	 * 计算签名参数的值；计算规则md5Lower(name1=val1&name2=val2...&privateKey)；跳过参数sig。
	 * 
	 * @param params
	 *            发送请求的所有参数。
	 * @param privateKey
	 *            由服务端提供给客户端的私钥。
	 * @return
	 */
	public static String calSignature(Map<String, String> params, String privateKey) {
		return calSignature(params, privateKey, "sig");
	}

	/**
	 * 计算签名参数的值。计算规则md5Lower(name1=val1&name2=val2...&privateKey)。
	 * 
	 * @param params
	 *            发送请求的所有参数。
	 * @param privateKey
	 *            由服务端提供给客户端的私钥。
	 * @param skipParamsName
	 *            不参与签名计算的参数，如：客户端传的签名参数。
	 * @return
	 */
	public static String calSignature(Map<String, String> params, String privateKey, String skipParamsName) {
		TreeSet<String> names = new TreeSet<String>(params.keySet());
		StringBuilder sb = new StringBuilder();
		for (String name : names) {
			if (name.equals(skipParamsName)) {
				continue;
			}
			String value = params.get(name);
			sb.append(name);
			sb.append("=");
			sb.append(value);
			sb.append("&");
		}
		sb.append(privateKey);
		logger.debug("param:{}", sb);
		return md5Lower(sb.toString());
	}

	/**
	 * 
	 * @param text
	 * @param toLowerCase
	 *            是否用小写字母。
	 * @return
	 */
	private static String md5(String text, final boolean toLowerCase) {
		if (text == null || text.trim().length() < 1) {
			return "";
		}
		try {
			return md5(text.getBytes("UTF-8"), toLowerCase);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static String md5(byte[] source, final boolean toLowerCase) {
		if (source == null || source.length == 0) {
			return null;
		}
		return md5(source, toLowerCase ? hexDigitsLower : hexDigits);
	}

	private static String md5(byte[] source, final char[] hexDigits) {
		String s = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = encodeHex(tmp, hexDigits);
			s = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	private static String encodeHexString(final byte[] data) {
		return new String(encodeHex(data, true));
	}

	private static char[] encodeHex(final byte[] data, final boolean toLowerCase) {
		return encodeHex(data, toLowerCase ? hexDigitsLower : hexDigits);
	}

	private static char[] encodeHex(final byte[] data, final char[] toDigits) {
		final int l = data.length;
		final char[] out = new char[l << 1];
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
			out[j++] = toDigits[0x0F & data[i]];
		}
		return out;
	}
}
