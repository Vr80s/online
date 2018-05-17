package com.xczhihui.course.consts;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.xczhihui.course.enums.RouteTypeEnum;

public class MultiUrlHelper {

    public static final String URL_TYPE_APP = "app";
    public static final String URL_TYPE_WEB = "web";
    public static final String URL_TYPE_MOBILE = "mobile";

    private static Map<String, Map<String, String>> urlMap = new HashMap<>();

    private static Map<String, String> courseDetailUrlMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/courseDetail?courseId={0}",
            URL_TYPE_WEB, "/web/html/courseDetail.html?courseId={0}",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> doctorApproveUrlMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/anchorDoctorApprove",
            URL_TYPE_WEB, "/web/html/anchors_resources.html",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> hospitalApproveUrlMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/anchorHospitalApprove",
            URL_TYPE_WEB, "/web/html/anchors_resources.html",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> workTableUrlMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/anchorWorkTable",
            URL_TYPE_WEB, "/web/html/anchor/curriculum.html#menu=2-1",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> anchorCNYMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/anchorCNY",
            URL_TYPE_WEB, "/web/html/anchor/curriculum.html#menu=2-1",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> courseListMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/courseList?tab=0",
            URL_TYPE_WEB, "/web/html/anchor/curriculum.html?tab=0#menu=1-1",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> liveCourseListMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/courseList?tab=1",
            URL_TYPE_WEB, "/web/html/anchor/curriculum.html?tab=1#menu=1-1",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> videoCourseListMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/courseList?tab=2",
            URL_TYPE_WEB, "/web/html/anchor/curriculum.html?tab=2#menu=1-1",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> offlineCourseListMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/courseList?tab=3",
            URL_TYPE_WEB, "/web/html/anchor/curriculum.html?tab=3#menu=1-1",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> audioCourseListMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/courseList?tab=4",
            URL_TYPE_WEB, "/web/html/anchor/curriculum.html?tab=4#menu=1-1",
            URL_TYPE_MOBILE, "");

    static {
        urlMap.put(RouteTypeEnum.COURSE_DETAIL_PAGE.name(), courseDetailUrlMap);
        urlMap.put(RouteTypeEnum.DOCTOR_APPROVE_PAGE.name(), doctorApproveUrlMap);
        urlMap.put(RouteTypeEnum.HOSPITAL_APPROVE_PAGE.name(), hospitalApproveUrlMap);
        urlMap.put(RouteTypeEnum.ANCHOR_WORK_TABLE_PAGE.name(), workTableUrlMap);
        urlMap.put(RouteTypeEnum.ANCHOR_PROPERTY_MONEY_PAGE.name(), anchorCNYMap);
        urlMap.put(RouteTypeEnum.COURSE_LIST_PAGE.name(), courseListMap);
        urlMap.put(RouteTypeEnum.LIVE_COURSE_LIST.name(), liveCourseListMap);
        urlMap.put(RouteTypeEnum.VIDEO_COURSE_LIST.name(), videoCourseListMap);
        urlMap.put(RouteTypeEnum.OFFLINE_COURSE_LIST.name(), offlineCourseListMap);
        urlMap.put(RouteTypeEnum.AUDIO_COURSE_LIST.name(), audioCourseListMap);
    }

    public static String getUrl(String routeType, String source) {
        Map<String, String> urlMap = MultiUrlHelper.urlMap.get(routeType);
        if (urlMap != null) {
            return urlMap.get(source);
        } else {
            return null;
        }
    }

    private static final String COLLECTION_COURSE_DETAIL = "/xcview/html/live_select_album.html?course_id={0}";
    private static final String LIVE_COURSE_DETAIL = "/xcview/html/live_play.html?my_study={0}";
    private static final String OFFLINE_COURSE_DETAIL = "/xcview/html/school_class.html?course_id={0}";
    private static final String AUDIO_VIDEO_COURSE_DETAIL = "/xcview/html/live_audio.html?my_study={0}";

    public static String getMobileCourseDetailUrl(boolean collection, int courseType) {
        //专辑
        if (collection) {
            return COLLECTION_COURSE_DETAIL;
        } else if (courseType == 1) {
            //直播
            return LIVE_COURSE_DETAIL;
        } else if (courseType == 2) {
            //视频音频
            return AUDIO_VIDEO_COURSE_DETAIL;
        } else if (courseType == 3) {
            //线下课
            return OFFLINE_COURSE_DETAIL;
        }
        return null;
    }
}
