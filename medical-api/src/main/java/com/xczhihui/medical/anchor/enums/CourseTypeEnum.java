package com.xczhihui.medical.anchor.enums;

/**
 * 课程类型枚举
 * @author zhuwenbao
 */
public enum CourseTypeEnum {

    LIVE(1,"直播"),
    VOD(2,"点播"),
    OFFLINE(3,"线下课");

    private Integer code;

    private String msg;

    CourseTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
