package com.xczhihui.medical.common.enums;

/**
 * 医师医馆公共枚举
 * @author zhuwenbao
 */
public enum CommonEnum {

    AUTH_DOCTOR(1),
    AUTH_HOSPITAL(2),
    DOCTOR_APPLYING(3),
    HOSPITAL_APPLYING(4),
    DOCTOR_APPLY_REJECT(5),
    HOSPITAL_APPLY_REJECT(6),
    NOT_DOCTOR_AND_HOSPITAL(7);


    CommonEnum(Integer code){
        this.code = code;
    }

    private Integer code;

    public Integer getCode() {
        return code;
    }
}
