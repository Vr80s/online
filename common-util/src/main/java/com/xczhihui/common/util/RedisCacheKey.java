package com.xczhihui.common.util;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/5/19 0019-下午 8:47<br>
 */
public class RedisCacheKey {

    public static final String REDIS_SPLIT_CHAR = ":";

    public static String GIFT_CACHE__PREFIX = "gift:cache";

    public static String FREE_GIFT_SEND__PREFIX = "gift:send:free";

    public static String GIFT_SHOW_PREFIX = "gift:show";

    public static String TICKET_PREFIX = "token";

    public static String VCODE_PREFIX = "vcode";

    public static String USER_DISABLE_PREFIX = "user" + REDIS_SPLIT_CHAR + "disable";

    public static String VALIDATE_ANCHOR_PERMISSION_PREFIX = "anchor:permission:validate";
    public static String ANCHOR_CACHE_PREFIX = "anchor:cache";


    public static String getGiftCacheKey(String str) {
        return GIFT_CACHE__PREFIX + REDIS_SPLIT_CHAR + str;
    }

    public static String getFreeGiftSendCacheKey(String str) {
        return FREE_GIFT_SEND__PREFIX + REDIS_SPLIT_CHAR + str;
    }

    public static String getGiftShowCacheKey(String userId, Integer giftId, String liveId) {
        return GIFT_SHOW_PREFIX + REDIS_SPLIT_CHAR + userId + REDIS_SPLIT_CHAR + giftId + REDIS_SPLIT_CHAR + liveId;
    }

    public static String getAnchorPermissionValidateCacheKey(String userId) {
        return VALIDATE_ANCHOR_PERMISSION_PREFIX + REDIS_SPLIT_CHAR + userId;
    }

    public static String getAnchorCacheKey(String userId) {
        return ANCHOR_CACHE_PREFIX + REDIS_SPLIT_CHAR + userId;
    }
}
