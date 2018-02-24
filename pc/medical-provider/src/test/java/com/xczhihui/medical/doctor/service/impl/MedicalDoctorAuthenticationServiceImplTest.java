package com.xczhihui.medical.doctor.service.impl;

import com.xczhihui.medical.doctor.service.IMedicalDoctorAuthenticationInformationService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorAuthenticationInformationVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

/**
 * 医师认证信息测试类
 */
public class MedicalDoctorAuthenticationServiceImplTest extends BaseJunit4Test {

    @Autowired
    private IMedicalDoctorAuthenticationInformationService service;

    /**
     * 获取医师的认证信息
     */
    @Test
    public void testGet(){

        MedicalDoctorAuthenticationInformationVO information =
                service.selectDoctorAuthenticationVO("ff80808161c0dd000161c17b63490000");

        Assert.assertNotNull(information);

    }


}