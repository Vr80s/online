package com.xczhihui.common.util.redis.key;

public class CourseRedisCacheKey {

    public static final String REDIS_SPLIT_CHAR = ":";

    public static final String COURSE_LIVE_AUDIO_BAN_PREFIX = "course:live:audio:ban";
    public static final String COURSE_LIVE_AUDIO_TOKEN_PREFIX = "course:live:audio:token";

    public static final String COURSE_LIVE_VIDEO_BAN_PREFIX = "course:live:video:ban";
    public static final String COURSE_LIVE_VIDEO_TOKEN_PREFIX = "course:live:video:token";

    /**
     * 有效期5小时
     */
    public static final  int COURSE_LIVE_TOKEN_SECONDS =  60*60*5;

    public static String getLiveAudioBanCacheKey(Integer courseId,String userId) {
        return COURSE_LIVE_AUDIO_BAN_PREFIX + REDIS_SPLIT_CHAR + courseId.toString() + REDIS_SPLIT_CHAR + userId;
    }

    public static String getLiveAudioTokenCacheKey(Integer courseId,String userId) {
        return COURSE_LIVE_AUDIO_TOKEN_PREFIX + REDIS_SPLIT_CHAR + courseId.toString() + REDIS_SPLIT_CHAR + userId;
    }

    public static String getLiveVideoBanCacheKey(Integer courseId,String userId) {
        return COURSE_LIVE_VIDEO_BAN_PREFIX + REDIS_SPLIT_CHAR + courseId.toString() + REDIS_SPLIT_CHAR + userId;
    }

    public static String getLiveVideoTokenCacheKey(Integer courseId,String userId) {
        return COURSE_LIVE_VIDEO_TOKEN_PREFIX + REDIS_SPLIT_CHAR + courseId.toString() + REDIS_SPLIT_CHAR + userId;
    }

}
