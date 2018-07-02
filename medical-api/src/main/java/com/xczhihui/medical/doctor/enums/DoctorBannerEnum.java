package com.xczhihui.medical.doctor.enums;

public enum DoctorBannerEnum {

    COURSE_DETAIL(1, "COMMON_COURSE_DETAIL_PAGE", "课程"),
    LIVE_COURSE_DETAIL(2, "COMMON_COURSE_DETAIL_PAGE", "直播"),
    SPECIAL_COLUMN(3, "SPECIAL_COLUMN_DETAIL", "专栏文章"),
    DOCTOR_CASE(4, "DOCTOR_CASE_DETAIL", "医案"),
    APPRENTICE(5, "APPRENTICE_DETAIL", "招生简章"),
    NONE(6, "NONE", "");

    private int type;

    private String routeType;

    private String desc;

    DoctorBannerEnum(int type, String routeType, String desc) {
        this.type = type;
        this.routeType = routeType;
        this.desc = desc;
    }

    public static DoctorBannerEnum getByType(int type) {
        for (DoctorBannerEnum doctorBannerEnum : DoctorBannerEnum.values()) {
            if (doctorBannerEnum.type == type) {
                return doctorBannerEnum;
            }
        }
        throw new IllegalArgumentException("轮播图类型错误");
    }

    public int getType() {
        return type;
    }

    public String getRouteType() {
        return routeType;
    }

    public String getDesc() {
        return desc;
    }
}
