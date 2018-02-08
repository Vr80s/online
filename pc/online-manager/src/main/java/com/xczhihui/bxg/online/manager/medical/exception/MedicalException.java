package com.xczhihui.bxg.online.manager.medical.exception;

import com.xczhihui.bxg.online.manager.medical.enums.MedicalExceptionEnum;

public class MedicalException extends RuntimeException{

    private Integer code;

    public MedicalException(MedicalExceptionEnum businessExceptionEnum){
        super(businessExceptionEnum.getMsg());
        this.code = businessExceptionEnum.getCode();
    }

}
