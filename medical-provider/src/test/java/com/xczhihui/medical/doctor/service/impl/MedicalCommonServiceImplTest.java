package com.xczhihui.medical.doctor.service.impl;

import com.xczhihui.medical.common.service.ICommonService;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.medical.doctor.service.IMedicalDoctorApplyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

/**
 * 医师入驻测试类
 */
public class MedicalCommonServiceImplTest extends BaseJunit4Test {

    @Autowired
    private ICommonService commonService;

    /**
     * 获取用户申请的最后一条信息
     */
    @Test
    public void testIsDoctorOrHospital(){
        Integer integer = commonService.isDoctorOrHospital("2c9acf816166484a01616653235b0013");

        System.out.println("====================================" + integer);

    }

}