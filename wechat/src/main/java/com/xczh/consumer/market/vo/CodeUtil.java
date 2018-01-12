package com.xczh.consumer.market.vo;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * 加密
 * @author Alex Wang
 *
 */
public class CodeUtil {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
	        "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static SecureRandom secureRandom = new SecureRandom();

	/**
	 * 对明文密码加密。
	 * 
	 * @param plainPassword
	 *            明文密码
	 * @param salt
	 *            盐
	 * @return
	 */
	public static String encodePassword(String plainPassword,
	        String salt) {
		if (salt == null) {
			salt = "";
		}
		return CodeUtil.MD5Encode(CodeUtil.MD5Encode(plainPassword) + salt);
	}

	/**
	 * 生成随机的8位的盐.
	 * 
	 * @return
	 */
	public static String generateRandomSalt() {
		return generateRandomSalt(8);
	}

	/**
	 * 生成随机的String作为salt.
	 * 
	 * @param numBytes
	 *            byte数组的大小
	 */
	public static String generateRandomSalt(int numBytes) {
		if (numBytes < 1) {
			throw new RuntimeException("字节数参数必需是正整数");
		}

		byte[] bytes = new byte[numBytes];
		secureRandom.nextBytes(bytes);
		return byteArrayToHexString(bytes);
	}

	/**
	 * 生成MD5串
	 * 
	 * @param origin
	 * @return
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
			        .getBytes()));
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
		return resultString;
	}

	/**
	 * 产生一个不带'-'的UUID字符串
	 * 
	 * @return
	 */
	public static String getRandomUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
            n = 256 + n;
        }
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
}
