package com.xczhihui.common.util;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/5/19 0019-下午 8:47<br>
 */
public class RedisCacheKey {

    public static final String REDIS_SPLIT_CHAR = ":";

    public static String GIFT_CACHE__PREFIX = "gift_";

    public static String FREE_GIFT_SEND__PREFIX = "f_g_";

    public static String GIFT_SHOW_PREFIX = "f_s_";

    public static String TICKET_PREFIX = "token";

    public static String VCODE_PREFIX = "vcode";

    public static String USER_DISABLE_PREFIX = "user" + REDIS_SPLIT_CHAR + "disable";
}
