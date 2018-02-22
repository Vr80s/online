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
                service.selectDoctorAuthenticationVO("23908ae85dad4541ba7ecf53fc52aab2");

        Assert.assertNotNull(information);

    }


}