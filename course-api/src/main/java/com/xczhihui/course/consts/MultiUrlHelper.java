package com.xczhihui.course.consts;

import static com.xczhihui.course.enums.RouteTypeEnum.HOSPITAL_APPROVE_PAGE;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableMap;
import com.xczhihui.course.enums.RouteTypeEnum;

/**
 * 多端消息url处理类
 *
 * @author hejiwei
 */
public class MultiUrlHelper {

    public static final int NONE_PARAM = 0;
    public static final int DETAIL = 1;
    public static final int LIST_FILTER = 2;
    public static final int OUTER_LINK = 3;

    public static final String URL_TYPE_APP = "app";
    public static final String URL_TYPE_WEB = "web";
    public static final String URL_TYPE_MOBILE = "mobile";
    private static final String APP_COURSE_DETAIL = "xczh://ipandatcm.com/courseDetail?id={0}";
    private static final String WEB_COURSE_DETAIL = "/courses/{0}/info";
    private static Map<String, Map<String, String>> urlMap = new HashMap<>();
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

    private static Map<String, String> courseDetailUrlMap =
            ImmutableMap.of(URL_TYPE_APP, APP_COURSE_DETAIL,
                    URL_TYPE_WEB, WEB_COURSE_DETAIL,
                    URL_TYPE_MOBILE, "/xczh/page/course/{0}");
    private static Map<String, String> learningCourseDetailUrlMap =
            ImmutableMap.of(URL_TYPE_APP, "xczh://ipandatcm.com/learningCourseDetail?id={0}",
                    URL_TYPE_WEB, WEB_COURSE_DETAIL,
                    URL_TYPE_MOBILE, "/xczh/page/course/{0}");

    private static Map<String, String> doctorApproveUrlMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/anchorApprove?type=1",
            URL_TYPE_WEB, "/web/html/ResidentDoctor.html",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> hospitalApproveUrlMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/anchorApprove?type=2",
            URL_TYPE_WEB, "/web/html/ResidentHospital.html",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> workTableUrlMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/anchorWorkTable",
            URL_TYPE_WEB, "/anchor/my#menu=1-1",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> anchorCNYMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/anchorCNY?type=1",
            URL_TYPE_WEB, "/anchor/my?isCNY=1#menu=2-1",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> courseListMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/courseList?tab=0",
            URL_TYPE_WEB, "/anchor/my#menu=1-1",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> liveCourseListMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/courseList?tab=1",
            URL_TYPE_WEB, "/anchor/my#menu=1-3",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> videoCourseListMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/courseList?tab=2",
            URL_TYPE_WEB, "/anchor/my?courseType=2#menu=1-1",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> offlineCourseListMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/courseList?tab=3",
            URL_TYPE_WEB, "/anchor/my?courseType=3#menu=1-1",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> audioCourseListMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/courseList?tab=4",
            URL_TYPE_WEB, "/anchor/my?courseType=4#menu=1-1",
            URL_TYPE_MOBILE, "");

    private static Map<String, String> liveCourseListOnlyWebMap = ImmutableMap.of(
            URL_TYPE_WEB, "/anchor/my?courseType=1#menu=1-1");
    private static Map<String, String> videoCourseListOnlyWebMap = ImmutableMap.of(
            URL_TYPE_WEB, "/anchor/my?courseType=2#menu=1-1");
    private static Map<String, String> offlineCourseListOnlyWebMap = ImmutableMap.of(
            URL_TYPE_WEB, "/anchor/my?courseType=3#menu=1-1");
    private static Map<String, String> audioCourseListOnlyWebMap = ImmutableMap.of(
            URL_TYPE_WEB, "/anchor/my?courseType=4#menu=1-1");
    private static Map<String, String> collectionCourseListOnlyWebMap = ImmutableMap.of(
            URL_TYPE_WEB, "/anchor/my#menu=1-2");

    private static Map<String, String> articleMap = ImmutableMap.of(
            URL_TYPE_WEB, "/headline/details/{0}"
    );
    private static Map<String, String> questionMap = ImmutableMap.of(
            URL_TYPE_WEB, "/questions/{0}"
    );
    private static Map<String, String> anchorIndexMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/anchorIndex?id={0}",
            URL_TYPE_WEB, "/anchors/{0}/courses",
            URL_TYPE_MOBILE, "/xcview/html/live_personal.html?userLecturerId={0}");
    private static Map<String, String> h5Map = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/h5?url={0}");
    private static Map<String, String> publicCourseListMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/publicCourseList?",
            URL_TYPE_WEB, "/courses/list?",
            URL_TYPE_MOBILE, "/xcview/html/curriculum_table.html?");
    private static Map<String, String> specialColumnMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/specialColumn?id={0}",
            URL_TYPE_WEB, "/headline/details/{0}",
            URL_TYPE_MOBILE, "");
    private static Map<String, String> doctorCaseMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/doctorCase?id={0}",
            URL_TYPE_WEB, "/headline/details/{0}",
            URL_TYPE_MOBILE, "");

    static {
        urlMap.put(RouteTypeEnum.COLLECTION_COURSE_DETAIL_PAGE.name(), collectionCourseDetailUrlMap);
        urlMap.put(RouteTypeEnum.LIVE_COURSE_DETAIL_PAGE.name(), liveCourseDetailUrlMap);
        urlMap.put(RouteTypeEnum.OFFLINE_COURSE_DETAIL_PAGE.name(), offlineCourseDetailUrlMap);
        urlMap.put(RouteTypeEnum.VIDEO_AUDIO_COURSE_DETAIL_PAGE.name(), videoAudioCourseDetailUrlMap);
        urlMap.put(RouteTypeEnum.DOCTOR_APPROVE_PAGE.name(), doctorApproveUrlMap);
        urlMap.put(HOSPITAL_APPROVE_PAGE.name(), hospitalApproveUrlMap);
        urlMap.put(RouteTypeEnum.ANCHOR_WORK_TABLE_PAGE.name(), workTableUrlMap);
        urlMap.put(RouteTypeEnum.ANCHOR_PROPERTY_MONEY_PAGE.name(), anchorCNYMap);
        urlMap.put(RouteTypeEnum.COURSE_LIST_PAGE.name(), courseListMap);
        urlMap.put(RouteTypeEnum.LIVE_COURSE_LIST.name(), liveCourseListMap);
        urlMap.put(RouteTypeEnum.VIDEO_COURSE_LIST.name(), videoCourseListMap);
        urlMap.put(RouteTypeEnum.OFFLINE_COURSE_LIST.name(), offlineCourseListMap);
        urlMap.put(RouteTypeEnum.AUDIO_COURSE_LIST.name(), audioCourseListMap);

        //带有only_web结尾的是为了兼容，有些跳转APP无落地页,只跳web端页面做的处理
        urlMap.put(RouteTypeEnum.LIVE_COURSE_LIST_ONLY_WEB.name(), liveCourseListOnlyWebMap);
        urlMap.put(RouteTypeEnum.VIDEO_COURSE_LIST_ONLY_WEB.name(), videoCourseListOnlyWebMap);
        urlMap.put(RouteTypeEnum.OFFLINE_COURSE_LIST_ONLY_WEB.name(), offlineCourseListOnlyWebMap);
        urlMap.put(RouteTypeEnum.AUDIO_COURSE_LIST_ONLY_WEB.name(), audioCourseListOnlyWebMap);
        urlMap.put(RouteTypeEnum.COLLECTION_COURSE_LIST_ONLY_WEB.name(), collectionCourseListOnlyWebMap);

        urlMap.put(RouteTypeEnum.ARTICLE_DETAIL.name(), articleMap);
        urlMap.put(RouteTypeEnum.QUESTION_DETAIL.name(), questionMap);
        urlMap.put(RouteTypeEnum.ANCHOR_INDEX.name(), anchorIndexMap);
        urlMap.put(RouteTypeEnum.H5.name(), h5Map);
        urlMap.put(RouteTypeEnum.COMMON_COURSE_DETAIL_PAGE.name(), courseDetailUrlMap);
        urlMap.put(RouteTypeEnum.COMMON_LEARNING_COURSE_DETAIL_PAGE.name(), learningCourseDetailUrlMap);
        urlMap.put(RouteTypeEnum.PUBLIC_COURSE_LIST_PAGE.name(), publicCourseListMap);
        urlMap.put(RouteTypeEnum.SPECIAL_COLUMN_DETAIL.name(), specialColumnMap);
        urlMap.put(RouteTypeEnum.DOCTOR_CASE_DETAIL.name(), doctorCaseMap);
    }

    public static String getUrl(String routeType, String source, String detailId, String link) {
        if (StringUtils.isNotBlank(detailId)) {
            return getUrl(routeType, source, detailId);
        } else {
            return getUrl(routeType, source, link);
        }
    }

    public static String getUrl(String routeType, String source, String params) {
        String url = "";
        if (routeType != null && !routeType.equals(RouteTypeEnum.NONE.name())) {
            RouteTypeEnum routeTypeEnum = RouteTypeEnum.valueOf(routeType);
            Map<String, String> urlMap = MultiUrlHelper.urlMap.get(routeType);
            if (urlMap == null) {
                return url;
            }
            String format = urlMap.get(source);
            if (StringUtils.isBlank(format)) {
                return url;
            }
            if (routeTypeEnum.getParamType() == OUTER_LINK) {
                if (StringUtils.isBlank(params)) {
                    return url;
                }
                if (URL_TYPE_WEB.equals(source) || URL_TYPE_MOBILE.equals(source)) {
                    return params;
                } else {
                    try {
                        url = MessageFormat.format(format, URLEncoder.encode(params, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return url;
                    }
                }
            } else if (routeTypeEnum.getParamType() == DETAIL || routeTypeEnum.getParamType() == NONE_PARAM) {
                if (params != null) {
                    url = MessageFormat.format(format, params);
                } else {
                    url = format;
                }
            } else if (routeTypeEnum.getParamType() == LIST_FILTER) {
                if (StringUtils.isNotBlank(params)) {
                    url = format + params;
                } else {
                    url = format;
                }
            }
        }
        return url;
    }
}
