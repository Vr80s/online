package com.xczhihui.course.consts;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableMap;
import com.xczhihui.common.util.enums.CourseType;
import com.xczhihui.common.util.enums.RouteTypeEnum;

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
    public static final String APP_COURSE_DETAIL_TYPE = "courseDetail";
    public static final String APP_SPECIAL_COLUMN_DETAIL_TYPE = "specialColumn";
    public static final String APP_DOCTOR_CASE_DETAIL_TYPE = "specialColumn";
    private static final String APP_COURSE_DETAIL = "xczh://ipandatcm.com/" + APP_COURSE_DETAIL_TYPE + "?id={0}";
    private static final String WEB_COURSE_DETAIL = "/courses/{0}/info";
    private static final String MOBILE_COURSE_DETAIL = "/page/course/{0}";
    private static final String APPRENTICE_URL = "/xcview/html/apprentice/inherited_introduction.html?merId={0}&needLogin=true";
    private static final String NEED_LOGIN_TRUE = "needLogin=true";
    private static final String NEED_LOGIN_FALSE = "needLogin=false";

    private static Map<String, Map<String, String>> urlMap = new HashMap<>();

    private static Map<String, String> courseDetailUrlMap =
            ImmutableMap.of(URL_TYPE_APP, APP_COURSE_DETAIL,
                    URL_TYPE_WEB, WEB_COURSE_DETAIL,
                    URL_TYPE_MOBILE, MOBILE_COURSE_DETAIL);
    private static Map<String, String> learningCourseDetailUrlMap =
            ImmutableMap.of(URL_TYPE_APP, "xczh://ipandatcm.com/learningCourseDetail?id={0}",
                    URL_TYPE_WEB, WEB_COURSE_DETAIL,
                    URL_TYPE_MOBILE, MOBILE_COURSE_DETAIL);

    //===== 学习里的课程详情 courseType区分课程类型 collection 区分是否是专辑 ======
    private static Map<String, String> learningLiveCourseDetailUrlMap =
            ImmutableMap.of(URL_TYPE_APP, "xczh://ipandatcm.com/learningCourseDetail?id={0}&collection=false&courseType=" + CourseType.LIVE.getId(),
                    URL_TYPE_WEB, WEB_COURSE_DETAIL,
                    URL_TYPE_MOBILE, MOBILE_COURSE_DETAIL);

    private static Map<String, String> learningAudioLiveCourseDetailUrlMap =
            ImmutableMap.of(URL_TYPE_APP, "xczh://ipandatcm.com/learningCourseDetail?id={0}&collection=false&courseType=" + CourseType.AUDIO_LIVE.getId(),
                    URL_TYPE_WEB, WEB_COURSE_DETAIL,
                    URL_TYPE_MOBILE, MOBILE_COURSE_DETAIL);

    private static Map<String, String> learningAudioCourseDetailUrlMap =
            ImmutableMap.of(URL_TYPE_APP, "xczh://ipandatcm.com/learningCourseDetail?id={0}&collection=false&courseType=" + CourseType.AUDIO.getId(),
                    URL_TYPE_WEB, WEB_COURSE_DETAIL,
                    URL_TYPE_MOBILE, MOBILE_COURSE_DETAIL);

    private static Map<String, String> learningVideoCourseDetailUrlMap =
            ImmutableMap.of(URL_TYPE_APP, "xczh://ipandatcm.com/learningCourseDetail?id={0}&collection=false&courseType=" + CourseType.VIDEO.getId(),
                    URL_TYPE_WEB, WEB_COURSE_DETAIL,
                    URL_TYPE_MOBILE, MOBILE_COURSE_DETAIL);
    private static Map<String, String> learningOfflineCourseDetailUrlMap =
            ImmutableMap.of(URL_TYPE_APP, "xczh://ipandatcm.com/learningCourseDetail?id={0}&collection=false&courseType=" + CourseType.OFFLINE.getId(),
                    URL_TYPE_WEB, WEB_COURSE_DETAIL,
                    URL_TYPE_MOBILE, MOBILE_COURSE_DETAIL);

    private static Map<String, String> learningAudioCourseCollectionDetailUrlMap =
            ImmutableMap.of(URL_TYPE_APP, "xczh://ipandatcm.com/learningCourseDetail?id={0}&collection=true&courseType=" + CourseType.AUDIO.getId(),
                    URL_TYPE_WEB, WEB_COURSE_DETAIL,
                    URL_TYPE_MOBILE, MOBILE_COURSE_DETAIL);

    private static Map<String, String> learningVideoCourseCollectionDetailUrlMap =
            ImmutableMap.of(URL_TYPE_APP, "xczh://ipandatcm.com/learningCourseDetail?id={0}&collection=true&courseType=" + +CourseType.VIDEO.getId(),
                    URL_TYPE_WEB, WEB_COURSE_DETAIL,
                    URL_TYPE_MOBILE, MOBILE_COURSE_DETAIL);
    //====================================================================

    private static Map<String, String> doctorApproveUrlMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/anchorApprove?type=1",
            URL_TYPE_WEB, "/doctors/authentication",
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
    private static Map<String, String> messageListMap = ImmutableMap.of(
            URL_TYPE_WEB, "/my#menu6",
            URL_TYPE_APP, "xczh://ipandatcm.com/message",
            URL_TYPE_MOBILE, ""
    );
    private static Map<String, String> doctorPostMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/doctorPost?id={0}",
            URL_TYPE_WEB, "/anchors/doctorPost?id={0}",
            URL_TYPE_MOBILE, "/xcview/html/physician/physicians_page.html?doctor={0}"
    );
    private static Map<String, String> anchorIndexMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/anchorIndex?id={0}",
            URL_TYPE_WEB, "/anchors/{0}/courses",
            URL_TYPE_MOBILE, "/xcview/html/live_personal.html?userLecturerId={0}");
    private static Map<String, String> h5Map = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/h5?url={0}",
            URL_TYPE_MOBILE, "{0}",
            URL_TYPE_WEB, "{0}");
    private static Map<String, String> apprenticeMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/h5?url={0}",
            URL_TYPE_MOBILE, "{0}",
            URL_TYPE_WEB, "javascript:void(0)");
    private static Map<String, String> publicCourseListMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/publicCourseList?",
            URL_TYPE_WEB, "/courses/list?",
            URL_TYPE_MOBILE, "/xcview/html/curriculum_table.html?");
    private static Map<String, String> specialColumnMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/" + APP_SPECIAL_COLUMN_DETAIL_TYPE + "?id={0}",
            URL_TYPE_WEB, "/headline/details/{0}",
            URL_TYPE_MOBILE, "/xcview/html/physician/consilia.html?consiliaId={0}");
    
    private static Map<String, String> doctorCaseMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/" + APP_DOCTOR_CASE_DETAIL_TYPE + "?id={0}",
            URL_TYPE_WEB, "/headline/details/{0}",
            URL_TYPE_MOBILE, "/xcview/html/physician/consilia.html?consiliaId={0}");

    
    private static Map<String, String> appointmentTreatmentInfoMap = ImmutableMap.of(
            URL_TYPE_APP, "xczh://ipandatcm.com/appointmentTreatmentInfo?id={0}",
            URL_TYPE_WEB, "/headline/details/{0}",
            URL_TYPE_MOBILE, "/xcview/html/physician/consilia.html?consiliaId={0}");
    
    private static Map<String, String> productInfoMap = ImmutableMap.of(
    		URL_TYPE_MOBILE, "/xcview/html/shop/commodity_details.html?productId={0}");

    
    
    static {
        urlMap.put(RouteTypeEnum.DOCTOR_APPROVE_PAGE.name(), doctorApproveUrlMap);
        urlMap.put(RouteTypeEnum.HOSPITAL_APPROVE_PAGE.name(), hospitalApproveUrlMap);
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
        urlMap.put(RouteTypeEnum.DOCTOR_POST.name(), doctorPostMap);
        urlMap.put(RouteTypeEnum.H5.name(), h5Map);
        urlMap.put(RouteTypeEnum.APPRENTICE_DETAIL.name(), apprenticeMap);
        urlMap.put(RouteTypeEnum.COMMON_COURSE_DETAIL_PAGE.name(), courseDetailUrlMap);
        urlMap.put(RouteTypeEnum.COMMON_LEARNING_COURSE_DETAIL_PAGE.name(), learningCourseDetailUrlMap);
        urlMap.put(RouteTypeEnum.PUBLIC_COURSE_LIST_PAGE.name(), publicCourseListMap);
        urlMap.put(RouteTypeEnum.SPECIAL_COLUMN_DETAIL.name(), specialColumnMap);
        urlMap.put(RouteTypeEnum.DOCTOR_CASE_DETAIL.name(), doctorCaseMap);
        urlMap.put(RouteTypeEnum.MESSAGE_LIST.name(), messageListMap);

        //===== 学习里的课程详情 courseType区分课程类型 collection 区分是否是专辑 ======
        urlMap.put(RouteTypeEnum.COMMON_LEARNING_AUDIO_COLLECTION_COURSE_DETAIL_PAGE.name(), learningAudioCourseCollectionDetailUrlMap);
        urlMap.put(RouteTypeEnum.COMMON_LEARNING_VIDEO_COLLECTION_COURSE_DETAIL_PAGE.name(), learningVideoCourseCollectionDetailUrlMap);
        urlMap.put(RouteTypeEnum.COMMON_LEARNING_VIDEO_COURSE_DETAIL_PAGE.name(), learningVideoCourseDetailUrlMap);
        urlMap.put(RouteTypeEnum.COMMON_LEARNING_LIVE_COURSE_DETAIL_PAGE.name(), learningLiveCourseDetailUrlMap);
        urlMap.put(RouteTypeEnum.COMMON_LEARNING_LIVE_AUDIO_COURSE_DETAIL_PAGE.name(), learningAudioLiveCourseDetailUrlMap);
        urlMap.put(RouteTypeEnum.COMMON_LEARNING_AUDIO_COURSE_DETAIL_PAGE.name(), learningAudioCourseDetailUrlMap);
        urlMap.put(RouteTypeEnum.COMMON_LEARNING_OFFLINE_COURSE_DETAIL_PAGE.name(), learningOfflineCourseDetailUrlMap);
        //====================================================================
        
        urlMap.put(RouteTypeEnum.APPOINTMENT_TREATMENT_INFO_PAGE.name(), appointmentTreatmentInfoMap);
        urlMap.put(RouteTypeEnum.PRODUCT_DETAIL.name(), productInfoMap);
        
    }

    public static String getUrl(String routeType, String source, String detailId, String link) {
        if (StringUtils.isNotBlank(detailId)) {
            return getUrl(routeType, source, detailId);
        } else {
            return getUrl(routeType, source, link);
        }
    }

    public static void main(String[] args) {
		
    	RouteTypeEnum valueOf = RouteTypeEnum.valueOf("PRODUCT_DETAIL");
    	
    	System.out.println(valueOf.toString());
    	
	}
    
    public static String getUrl(String routeType, String source, String params) {
        String url = "";
        try {
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
                            boolean containQuestion = false;
                            boolean needLogin = false;
                            if (params.contains("?")) {
                                containQuestion = true;
                            }
                            if (params.contains(NEED_LOGIN_TRUE)) {
                                needLogin = true;
                            }
                            url = MessageFormat.format(format, URLEncoder.encode(params, "UTF-8"));
                            url = url + (containQuestion ? "&" : "?") + (needLogin ? NEED_LOGIN_TRUE : NEED_LOGIN_FALSE);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 额外处理师承招生简章的参数
     *
     * @param prefix    域名前缀
     * @param linkParam 参数
     * @return
     */
    public static String handleParam(String prefix, String linkParam, String routeType) {
        if (routeType.equals(RouteTypeEnum.APPRENTICE_DETAIL.name())) {
            if (StringUtils.isNotBlank(linkParam)) {
                return MessageFormat.format(prefix + APPRENTICE_URL, linkParam);
            }
        }
        return linkParam;
    }
}
