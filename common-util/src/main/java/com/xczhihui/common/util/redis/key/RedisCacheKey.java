package com.xczhihui.common.util.redis.key;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/5/19 0019-下午 8:47<br>
 */
public class RedisCacheKey {

    public static final String REDIS_SPLIT_CHAR = ":";

    public static final String GIFT_CACHE__PREFIX = "gift:cache";

    public static final String FREE_GIFT_SEND__PREFIX = "gift:send:free";

    public static final String GIFT_SHOW_PREFIX = "gift:show";

    public static final String TICKET_PREFIX = "token";

    public static final String VCODE_PREFIX = "vcode";

    public static final String USER_DISABLE_PREFIX = "user" + REDIS_SPLIT_CHAR + "disable";

    public static final String VALIDATE_ANCHOR_PERMISSION_PREFIX = "anchor:permission:validate";
    public static final String ANCHOR_CACHE_PREFIX = "anchor:cache";

    public static final String LIVE_COURSE_REMIND_KEY = "course:remind:live";
    public static final String OFFLINE_COURSE_REMIND_KEY = "course:remind:offline";
    public static final String COLLECTION_COURSE_REMIND_KEY = "course:remind:collection";
    
    public static final String COMMON_MINUTE_REMIND_KEY = "common:minute";
    
    public static final String TREATMENT_MINUTE_TYPE = "treatmentlive";
    
    public static final String LIVE_COURSE_REMIND_LAST_TIME_KEY = "course:live:lastTime";


    public static final String MESSAGE_PUSH_KEY = "message:push:";

    public static final String APPRAISE_PRAISE_KEY = "appraise:praise:";

    public static final String XG_ACCOUNT_KEY = "xg:account:key:";

    public static final String VHALLYUN_BAN_KEY = "vhallyun:ban:key:";

    public static final String CHANNEL_ONLINE_KEY = "channel:online:key:";

    public static final String USER_TREATMENT_STATUS_CNT_KEY = "user:treatment:cnt:";

    public static final String DOCTOR_TREATMENT_STATUS_CNT_KEY = "doctor:treatment:cnt:";
    
    public static final String PROVINCE_CITY_COUNTY  = "province:city:county";

    
    public static final String STORE_DOCTOR_RELEVANCE  = "STORE:DOCTOR:RELEVANCE";
    
    public static final String SHOP_USERS_RELATION = "SHOP:USERS:RELATION";
    
    public static final String OE_USER_INFO = "OE:USER:INFO";
    
    
    

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
