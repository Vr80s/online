package com.xczhihui.medical.enums;

/**
 * medical异常枚举
 */
public enum MedicalExceptionEnum {

    USER_DATA_ERROR(20000,"不能修改用户信息"),
    NET_WRONG(20001,"网络异常"),
    PARAM_NOT_EMPTY(20002,"参数不能为空"),
    DATE_FORMAT_WRONG(20003,"时间格式错误"),
    DATE_START_IS_AFTER_END(20004,"起始时间不应大于终止时间"),

    // -----------------主播异常---------------------

    ANCHOR_NAME_EMPTY(30001, "主播昵称不能为空"),
    ANCHOR_PROFILEPHOTO_EMPTY(30002, "主播头像不能为空"),
    ANCHOR_DETAIL_EMPTY(30003, "主播个人介绍不能为空"),
    ANCHOR_HOSPITALID_EMPTY(30004, "主播任职医馆不能为空"),
    ANCHOR_PROVINCE_EMPTY(30005, "主播省份不能为空"),
    ANCHOR_CITY_EMPTY(30006, "主播城市不能为空"),

    // -----------------医馆异常---------------------
    HOSPITAL_IS_EMPTY(40001, "请选择医馆");

    private Integer code;

    private String msg;

    MedicalExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
