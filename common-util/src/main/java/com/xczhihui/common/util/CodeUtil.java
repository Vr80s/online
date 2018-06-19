package com.xczhihui.common.util;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * MD5等工具
 *
 * @author liyong
 */
public class CodeUtil {

    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static char hexDigitsLower[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

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
     * 生成随机的numBytes位字符串。
     *
     * @param numBytes 字符串长度。
     */
    public static String generateRandomString(int numBytes) {
        if (numBytes < 1) {
            throw new RuntimeException("字节数参数必需是正整数");
        }

        byte[] bytes = new byte[numBytes];
        secureRandom.nextBytes(bytes);
        return encodeHexString(bytes);
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
