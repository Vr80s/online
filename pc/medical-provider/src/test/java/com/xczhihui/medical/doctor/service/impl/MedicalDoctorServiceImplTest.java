package com.xczhihui.medical.doctor.service.impl;

import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.medical.doctor.service.IMedicalDoctorApplyService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

/**
 * 医师入驻测试类
 */
public class MedicalDoctorServiceImplTest extends BaseJunit4Test {

    @Autowired
    private IMedicalDoctorBusinessService service;

    /**
     * 加入医馆
     */
    @Test
    public void testJoinHospital(){
        MedicalDoctor target = new MedicalDoctor();
        target.setUserId("2c9acf816166484a01616653235b0013"); // ==> doctorId = 3ae0b6eb01fd4a0e84f1b4811ce8a719
        target.setHospitalId("01e17edf2a0d47049709871200f6f8ed");
        target.setWorkTime("1,2,3,5");
        service.joinHospital(target);
    }

    /**
     * 获取医馆信息
     */
    @Test
    public void testGetHospital(){
        MedicalDoctor target = new MedicalDoctor();
        MedicalHospitalVo vo = service.getHospital("2c9acf816166484a01616653235b0013");
        System.out.println("----------------------" + vo.toString());


    }

}