package com.xczhihui.medical.enums;

public enum MedicalExceptionEnum {

    USER_DATA_ERROR(20000,"数据异常，请联系管理员");

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
