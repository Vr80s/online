package com.xczhihui.course.consts;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.xczhihui.course.enums.RouteTypeEnum;

/**
 * 多端消息url处理类
 *
 * @author hejiwei
 */
public class MultiUrlHelper {

    public static final String URL_TYPE_APP = "app";
    public static final String URL_TYPE_WEB = "web";
    public static final String URL_TYPE_MOBILE = "mobile";

    private static Map<String, Map<String, String>> urlMap = new HashMap<>();
    private static final String APP_COURSE_DETAIL = "xczh://ipandatcm.com/courseDetail?id={0}";
    private static final String WEB_COURSE_DETAIL = "/web/html/courseDetail.html?courseId={0}";

    private static Map<String, String> collectionCourseDetailUrlMap = ImmutableMap.of(
            URL_TYPE_APP, APP_COURSE_DETAIL,
            URL_TYPE_WEB, WEB_COURSE_DETAIL,
            URL_TYPE_MOBILE, "/xcview/html/live_select_album.html?course_id={0}");
    private static Map<String, String> liveCourseDetailUrlMap = ImmutableMap.of(
            URL_TYPE_APP, APP_COURSE_DETAIL,
            URL_TYPE_WEB, WEB_COURSE_DETAIL,
            URL_TYPE_MOBILE, "/xcview/html/live_play.html?my_study={0}");
    private static Map<String, String> offlineCourseDetailUrlMap = ImmutableMap.of(
            URL_TYPE_APP, APP_COURSE_DETAIL,
            URL_TYPE_WEB, WEB_COURSE_DETAIL,
            URL_TYPE_MOBILE, "/xcview/html/school_class.html?course_id={0}");
    private static Map<String, String> videoAudioCourseDetailUrlMap = ImmutableMap.of(
            URL_TYPE_APP, APP_COURSE_DETAIL,
            URL_TYPE_WEB, WEB_COURSE_DETAIL,
            URL_TYPE_MOBILE, "/xcview/html/live_audio.html?my_study={0}");


    private static Map<String, String> doctorApproveUrlMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/anchorApprove",
            URL_TYPE_WEB, "/web/html/anchors_resources.html",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> hospitalApproveUrlMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/anchorApprove",
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
    private static Map<String, String> articleMap = ImmutableMap.of(
            URL_TYPE_WEB, "/headline/details/{0}"
    );
    private static Map<String, String> questionMap = ImmutableMap.of(
            URL_TYPE_WEB, "/web/qusDetail/{0}"
    );

    static {
        urlMap.put(RouteTypeEnum.COLLECTION_COURSE_DETAIL_PAGE.name(), collectionCourseDetailUrlMap);
        urlMap.put(RouteTypeEnum.LIVE_COURSE_DETAIL_PAGE.name(), liveCourseDetailUrlMap);
        urlMap.put(RouteTypeEnum.OFFLINE_COURSE_DETAIL_PAGE.name(), offlineCourseDetailUrlMap);
        urlMap.put(RouteTypeEnum.VIDEO_AUDIO_COURSE_DETAIL_PAGE.name(), videoAudioCourseDetailUrlMap);
        urlMap.put(RouteTypeEnum.DOCTOR_APPROVE_PAGE.name(), doctorApproveUrlMap);
        urlMap.put(RouteTypeEnum.HOSPITAL_APPROVE_PAGE.name(), hospitalApproveUrlMap);
        urlMap.put(RouteTypeEnum.ANCHOR_WORK_TABLE_PAGE.name(), workTableUrlMap);
        urlMap.put(RouteTypeEnum.ANCHOR_PROPERTY_MONEY_PAGE.name(), anchorCNYMap);
        urlMap.put(RouteTypeEnum.COURSE_LIST_PAGE.name(), courseListMap);
        urlMap.put(RouteTypeEnum.LIVE_COURSE_LIST.name(), liveCourseListMap);
        urlMap.put(RouteTypeEnum.VIDEO_COURSE_LIST.name(), videoCourseListMap);
        urlMap.put(RouteTypeEnum.OFFLINE_COURSE_LIST.name(), offlineCourseListMap);
        urlMap.put(RouteTypeEnum.AUDIO_COURSE_LIST.name(), audioCourseListMap);
        urlMap.put(RouteTypeEnum.ARTICLE_DETAIL.name(), articleMap);
        urlMap.put(RouteTypeEnum.QUESTION_DETAIL.name(), questionMap);
    }

    public static String getUrl(String routeType, String source) {
        Map<String, String> urlMap = MultiUrlHelper.urlMap.get(routeType);
        if (urlMap != null) {
            return urlMap.get(source);
        } else {
            return "";
        }
    }

    public static String getUrl(String routeType, String source, String detailId) {
        String url = null;
        if (routeType != null && !routeType.equals(RouteTypeEnum.NONE.name())) {
            url = MultiUrlHelper.getUrl(routeType, source);
            if (url != null && detailId != null) {
                url = MessageFormat.format(url, detailId);
            }
        }
        return url;
    }
}
