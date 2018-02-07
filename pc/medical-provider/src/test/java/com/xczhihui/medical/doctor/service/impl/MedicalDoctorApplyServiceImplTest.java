package com.xczhihui.medical.doctor.service.impl;

import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.medical.doctor.service.IMedicalDoctorApplyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

/**
 * 医师入驻测试类
 */
public class MedicalDoctorApplyServiceImplTest extends BaseJunit4Test {

    @Autowired
    private IMedicalDoctorApplyService doctorApplyService;

    /**
     * 获取用户申请的最后一条信息
     */
    @Test
    public void testGetLastOne(){
        MedicalDoctorApply lastOne =
                doctorApplyService.getLastOne("4af72e6511a94a7ba9f7ed2aaf41ac5d");
        System.out.println("----------------------------" + lastOne.toString());
    }

    /**
     * 添加医师入驻申请信息
     */
    @Test
    public void testAdd(){
        MedicalDoctorApply target = new MedicalDoctorApply();

        target.setName("朱辣椒");
        // java.lang.RuntimeException: 您已经有认证医馆, 不能再申请认证
        // target.setUserId("ff8080816142af54016149e069080000");
        target.setCardNum("7709123819273****0912831");
        target.setCardPositive("xxxxxxxxxxxxxxx");
        target.setCardNegative("xxxxxxxxxxxxxxx");
        target.setQualificationCertificate("xxxxxxxxxxxxxxx");
        target.setField("xxxxxxxxxxxx");

        doctorApplyService.add(target);
    }

}