package com.xczhihui.medical.department.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorDepartmentService;
import com.xczhihui.medical.doctor.vo.DoctorQueryVo;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;

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
    
    
    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;

    /**
     * 获取医师所在的科室
     */
    @Test
    public void testGetDoctorDepartment(){
        List<MedicalDepartment> result = service.selectByUserId("402880e860c4ebe30160c51302660000");
        result.forEach(doctorDepartment -> System.out.println("-------------------" + doctorDepartment.toString()));
    }
    
    
    /**
     * 获取医师所在的科室
     */
    @Test
    public void testGetDoctorDepartment1(){

    	DoctorQueryVo dqv = new DoctorQueryVo();
    	dqv.setQueryKey("康复科");
    	dqv.setDepartmentId("170b3b46a2af42d1b99646c85807d62d");
    	dqv.bulid();
    	Page<MedicalDoctorVO> doctors = medicalDoctorBusinessService.
        		selectDoctorListByQueryKey(new Page<MedicalDoctorVO>(10, 1),dqv);
    	System.out.println(doctors.getTotal());
    }

}