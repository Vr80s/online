package com.xczhihui.common.util.redis.key;

public class CourseRedisCacheKey {

    public static final String REDIS_SPLIT_CHAR = ":";

    public static final String COURSE_LIVE_AUDIO_BAN__PREFIX = "course:live:audio:ban";

    public static String getLiveAudioBanCacheKey(Integer courseId,String userId) {
        return COURSE_LIVE_AUDIO_BAN__PREFIX + REDIS_SPLIT_CHAR + courseId.toString() + REDIS_SPLIT_CHAR + userId;
    }


}
