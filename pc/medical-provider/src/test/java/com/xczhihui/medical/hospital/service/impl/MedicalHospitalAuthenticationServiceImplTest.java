package com.xczhihui.medical.hospital.service.impl;

import com.xczhihui.medical.hospital.model.MedicalHospitalAuthentication;
import com.xczhihui.medical.hospital.service.IMedicalHospitalAuthenticationService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

/**
 * 医师认证信息测试类
 */
public class MedicalHospitalAuthenticationServiceImplTest extends BaseJunit4Test {

    @Autowired
    private IMedicalHospitalAuthenticationService service;

    /**
     * 获取医师的认证信息
     */
    @Test
    public void testGet(){

        MedicalHospitalAuthentication information =
                service.selectHospitalAuthentication("402880e860c4ebe30160c51302660000");

        Assert.assertNotNull(information);

    }


}