package com.xczhihui.medical.doctor.service.impl;

import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.medical.doctor.model.MedicalDoctorApplyDepartment;
import com.xczhihui.medical.doctor.service.IMedicalDoctorApplyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

import java.util.ArrayList;
import java.util.List;

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
                doctorApplyService.getLastOne("402880e860c4ebe30160c51302660000");
        System.out.println("----------------------------" + lastOne.toString());
    }

    /**
     * 添加医师入驻申请信息
     */
    @Test
    public void testAdd(){
        MedicalDoctorApply target = new MedicalDoctorApply();

        target.setUserId("402880e860c4ebe30160c51302660000");

        target.setName("朱疤瘌");
        target.setCardNum("7709123819273****0912831");

        target.setCardPositive("1111111111");
        target.setCardNegative("11111111");
        target.setQualificationCertificate("11111111");
        target.setProfessionalCertificate("1111111");
        target.setHeadPortrait("23123912");
        target.setTitle("2312312");
        target.setField("11111111");
        target.setTitleProve("12391298");
        target.setDescription("qeqweqweqwe");
        target.setProvince("阿富汗");
        target.setCity("阿富汗");

        MedicalDoctorApplyDepartment department_1 = new MedicalDoctorApplyDepartment();
        MedicalDoctorApplyDepartment department_2 = new MedicalDoctorApplyDepartment();
        department_1.setDepartmentId("0f4df242c3294902a87b8bc0a0ffe4d8");
        department_2.setDepartmentId("3dde4a0a3c6b4d4cbef7a2c501eee373");
        List<MedicalDoctorApplyDepartment> departments = new ArrayList<>();
        departments.add(department_1);
        departments.add(department_2);

        target.setDepartments(departments);

        doctorApplyService.add(target);
    }

}