/**
 *
 */
package com.xczhihui.common.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串相关的工具。
 *
 * @author liyong
 */
public final class IStringUtil {
    private static final Pattern A_LINK_PATTERN = Pattern.compile("(<a [^>]*?>)|(<\\/a>)");

    /**
     * 判断str是否包含可见的字符。
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        if (str != null && str.trim().length() > 0) {
            return true;
        }
        return false;
    }

    public static String getTop100Char(String text) {
        if (isNotBlank(text) && text.length() >= 100) {
            return text.substring(0, 100);
        }
        return text;
    }

    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Description：获取指定位数的随机字符串(包含小写字母、大写字母、数字,0<length)
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/19 0019 下午 6:41
     **/
    public static String getRandomString() {
        //随机字符串的随机字符库
        String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < 4; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }

    public static String filterLinkTag(String text) {
        if (text != null) {
            Matcher matcher = A_LINK_PATTERN.matcher(text);
            return matcher.replaceAll("");
        } else {
            return text;
        }
    }
}
