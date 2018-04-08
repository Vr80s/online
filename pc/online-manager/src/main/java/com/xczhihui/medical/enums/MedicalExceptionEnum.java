package com.xczhihui.medical.enums;

/**
 * 医师异常枚举
 */
public enum MedicalExceptionEnum {

    MUST_NOT_HANDLE(10000,"不能修改用户信息"),
    HOSPITAL_NOT_EXIT(10001,"医馆不存在"),
    DOCTOR_NOT_EXIT(10002,"医师不存在");

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
