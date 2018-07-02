/**
 *
 */
package com.xczhihui.course.util;

/**
 * 字符串相关的工具。
 *
 * @author Alex Wang
 */
public final class IStringUtil {

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
}
