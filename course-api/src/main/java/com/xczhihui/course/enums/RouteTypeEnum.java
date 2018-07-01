package com.xczhihui.course.enums;

/**
 * @author hejiwei
 */

public enum RouteTypeEnum {

    //专辑课程详情页
    COLLECTION_COURSE_DETAIL_PAGE(1),
    //直播课程详情页
    LIVE_COURSE_DETAIL_PAGE(1),
    //线下课课程详情页
    OFFLINE_COURSE_DETAIL_PAGE(1),
    //音视频课程详情页
    VIDEO_AUDIO_COURSE_DETAIL_PAGE(1),
    //课程详情页，不区分课程类型
    COMMON_COURSE_DETAIL_PAGE(1),
    //学习中心的课程详情
    COMMON_LEARNING_COURSE_DETAIL_PAGE(1),
    SPECIAL_COLUMN_DETAIL(1),
    DOCTOR_CASE_DETAIL(1),
    //医师认证页
    DOCTOR_APPROVE_PAGE(0),
    //医馆认证页
    HOSPITAL_APPROVE_PAGE(0),
    ANCHOR_WORK_TABLE_PAGE(0),
    ANCHOR_PROPERTY_MONEY_PAGE(0),
    COURSE_LIST_PAGE(0),
    LIVE_COURSE_LIST(0),
    VIDEO_COURSE_LIST(0),
    OFFLINE_COURSE_LIST(0),
    AUDIO_COURSE_LIST(0),

    COLLECTION_COURSE_LIST_ONLY_WEB(0),
    LIVE_COURSE_LIST_ONLY_WEB(0),
    VIDEO_COURSE_LIST_ONLY_WEB(0),
    OFFLINE_COURSE_LIST_ONLY_WEB(0),
    AUDIO_COURSE_LIST_ONLY_WEB(0),
    //不跳转
    NONE(0),
    ARTICLE_DETAIL(1),
    QUESTION_DETAIL(1),
    ANCHOR_INDEX(1),
    H5(3),
    PUBLIC_COURSE_LIST_PAGE(2);

    //0 => 不带参数 1=>详情页跳转，参数为id 2 => 列表筛选条件 3=>h5
    private int paramType;

    RouteTypeEnum(int paramType) {
        this.paramType = paramType;
    }

    public int getParamType() {
        return paramType;
    }
}
