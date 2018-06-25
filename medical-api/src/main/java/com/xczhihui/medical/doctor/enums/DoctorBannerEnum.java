package com.xczhihui.medical.doctor.enums;

public enum DoctorBannerEnum {

    COURSE_DETAIL(1, "COMMON_COURSE_DETAIL_PAGE"),
    LIVE_COURSE_DETAIL(2, "COMMON_COURSE_DETAIL_PAGE"),
    SPECIAL_COLUMN(3, "SPECIAL_COLUMN_DETAIL"),
    DOCTOR_CASE(4, "DOCTOR_CASE_DETAIL");

    private int type;

    private String routeType;

    DoctorBannerEnum(int type, String routeType) {
        this.type = type;
        this.routeType = routeType;
    }

    public static DoctorBannerEnum getByType(int type) {
        for (DoctorBannerEnum doctorBannerEnum : DoctorBannerEnum.values()) {
            if (doctorBannerEnum.type == type) {
                return doctorBannerEnum;
            }
        }
        throw new IllegalArgumentException("轮播图类型错误");
    }
}
