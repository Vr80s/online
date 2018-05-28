package com.xczhihui.medical.anchor.enums;

/**
 * 主播类型枚举
 * @author zhuwenbao
 */
public enum AuchorTypeEnum {

    DOCTOR(1,"医师"),
    HOSPITAL(2,"医馆");

    private Integer code;

    private String msg;

    AuchorTypeEnum(Integer code, String msg) {
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
