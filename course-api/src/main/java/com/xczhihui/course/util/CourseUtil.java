package com.xczhihui.course.util;

import com.xczhihui.course.enums.RouteTypeEnum;
import com.xczhihui.course.model.Course;

public class CourseUtil {

    public static RouteTypeEnum getRouteType(boolean collection, int type) {
        return collection ? RouteTypeEnum.COLLECTION_COURSE_DETAIL_PAGE :
                (type == Course.COURSE_TYPE_LIVE ? RouteTypeEnum.LIVE_COURSE_DETAIL_PAGE :
                        (type == Course.COURSE_TYPE_VIDEO_AUDIO ? RouteTypeEnum.VIDEO_AUDIO_COURSE_DETAIL_PAGE
                                : RouteTypeEnum.OFFLINE_COURSE_DETAIL_PAGE));
    }
}
