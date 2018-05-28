package com.xczhihui.medical.department.service.impl;

import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.doctor.service.IMedicalDoctorDepartmentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

import java.util.List;

/**
 * 科室测试类
 */
public class MedicalDepartmentServiceImplTest extends BaseJunit4Test {

    @Autowired
    private IMedicalDoctorDepartmentService service;

    /**
     * 获取医师所在的科室
     */
    @Test
    public void testGetDoctorDepartment(){
        List<MedicalDepartment> result = service.selectByUserId("402880e860c4ebe30160c51302660000");
        result.forEach(doctorDepartment -> System.out.println("-------------------" + doctorDepartment.toString()));
    }

}