package com.xczhihui.medical.common.service.impl;

import com.xczhihui.medical.common.service.ICommonService;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

public class MedicalCommonServiceImplTest extends BaseJunit4Test {

    @Autowired
    private ICommonService service;

    /**
     * 判断用户医师医馆认证状态
     */
    @Test
    public void testDelete(){

        service.isDoctorOrHospital("7852cbfecf5b475aad23fed1988b13ef");
    }

}