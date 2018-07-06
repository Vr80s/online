package com.xczhihui.common.util;

import com.xczhihui.common.util.enums.CourseForm;
import com.xczhihui.common.util.enums.Multimedia;
import com.xczhihui.common.util.enums.RouteTypeEnum;

public class CourseUtil {

    public static RouteTypeEnum getRouteType(Boolean collection, int type) {
        collection = collection == null ? false : collection;
        return collection ? RouteTypeEnum.COLLECTION_COURSE_DETAIL_PAGE :
                (type == CourseForm.LIVE.getCode() ? RouteTypeEnum.LIVE_COURSE_DETAIL_PAGE :
                        (type == CourseForm.VOD.getCode() ? RouteTypeEnum.VIDEO_AUDIO_COURSE_DETAIL_PAGE
                                : RouteTypeEnum.OFFLINE_COURSE_DETAIL_PAGE));
    }

    public static RouteTypeEnum getCourseLearningRouteType(Boolean collection, int type, Integer multimedia) {
        RouteTypeEnum routeTypeEnum = RouteTypeEnum.NONE;
        if (type == CourseForm.LIVE.getCode()) {
            routeTypeEnum = RouteTypeEnum.COMMON_LEARNING_LIVE_COURSE_DETAIL_PAGE;
        } else if (type == CourseForm.VOD.getCode()) {
            if (multimedia == Multimedia.AUDIO.getCode()) {
                routeTypeEnum = collection ? RouteTypeEnum.COMMON_LEARNING_AUDIO_COLLECTION_COURSE_DETAIL_PAGE : RouteTypeEnum.COMMON_LEARNING_AUDIO_COURSE_DETAIL_PAGE;
            } else {
                routeTypeEnum = collection ? RouteTypeEnum.COMMON_LEARNING_VIDEO_COLLECTION_COURSE_DETAIL_PAGE : RouteTypeEnum.COMMON_LEARNING_VIDEO_COURSE_DETAIL_PAGE;
            }
        } else if (type == CourseForm.OFFLINE.getCode()) {
            routeTypeEnum = RouteTypeEnum.COMMON_LEARNING_OFFLINE_COURSE_DETAIL_PAGE;
        }
        return routeTypeEnum;
    }

    /**
     * Description：计算评论星级
     *
     * @return Double
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    public static Double criticizeStartLevel(Double startLevel) {


        if (startLevel != null && startLevel != 0) { // 不等于0
            String b = startLevel.toString();
            if (b.length() > 1
                    && !b.substring(b.length() - 1, b.length()).equals("0")) { // 不等于整数
                String[] arr = b.split("\\.");
                Integer tmp = Integer.parseInt(arr[1]);
                if (tmp >= 5) {
                    return (double) (Integer.parseInt(arr[0]) + 1);
                } else {
                    return Double.valueOf(arr[0] + "." + 5);
                }
            } else {
                return startLevel;
            }
        } else {

            return 0d;
        }
    }
}
