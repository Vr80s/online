package com.xczhihui.medical.hospital.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.BaseJunit4Test;

import com.xczhihui.medical.hospital.model.MedicalHospitalApply;
import com.xczhihui.medical.hospital.service.IMedicalHospitalApplyService;

/**
 * 医馆入驻测试类
 */
public class MedicalHospitalApplyServiceImplTest extends BaseJunit4Test {

    @Autowired
    private IMedicalHospitalApplyService service;

    /**
     * 获取用户申请的最后一条信息
     */
    @Test
    public void testGetLastOne(){
        MedicalHospitalApply lastOne =
                service.getLastOne("4af72e6511a94a7ba9f7ed2aaf41ac5d");
        System.out.println("----------------------------" + lastOne.toString());
    }

    /**
     * 添加医师入驻申请信息
     */
    @Test
    public void testAdd(){
        MedicalHospitalApply target = new MedicalHospitalApply();

        target.setUserId("402880e860c4ebe30160c51302660000");
        target.setName("麦芽多米医馆");
        target.setCompany("麦芽多米医馆");
        target.setBusinessLicenseNo("7709123819273****0912831");
        target.setBusinessLicensePicture("xxxxxxxxxxxxxxx");
        target.setLicenseForPharmaceuticalTradingPicture("xxxxx");
        target.setLicenseForPharmaceuticalTrading("qwieuo1239123");

        service.add(target);
    }

}