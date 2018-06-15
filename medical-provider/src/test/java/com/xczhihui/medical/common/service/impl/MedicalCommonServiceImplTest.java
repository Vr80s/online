package com.xczhihui.medical.common.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xczhihui.medical.common.service.ICommonService;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAccountMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalAccountMapper;
import com.xczhihui.medical.hospital.model.MedicalHospitalAccount;

import test.BaseJunit4Test;

public class MedicalCommonServiceImplTest extends BaseJunit4Test {

    @Autowired
    private ICommonService service;
    
    @Autowired
    private MedicalDoctorAccountMapper doctorAccountMapper;
    
    @Autowired
    private MedicalHospitalAccountMapper hospitalAccountMapper;
    

    /**
     * 判断用户医师医馆认证状态
     */
    @Test
    public void testDelete(){

        service.isDoctorOrHospital("7852cbfecf5b475aad23fed1988b13ef");
    }
    
    @Test
    public void testDelete1(){
    	/*
    	 * MedicalDoctorAccount [id=0d88b7dc26b3422db298748c21d41d6b, 
    	 *    doctorId=14c2192523c5438f9a10d17994a1c6a3, 
    	 *    accountId=ef894375d67146478869ed0b3d7ccd66, 
    	 *    createTime=null, status=true, deleted=false 
    	 */
//    	MedicalDoctorAccount mda = doctorAccountMapper.getMedicalDoctorStatusByUserId("ef894375d67146478869ed0b3d7ccd66");
//        System.out.println(mda.toString());
//        
    	/**
    	 * MedicalHospitalAccount [id=0dd1eeb4afb24ac89024302cb9432ad6,
    	 *  doctorId=16ee1223dbef474d8149a60602d8a058, accountId=ff80808162717632016274d7c4280001, 
    	 * createTime=null, status=true, deleted=false]
    	 */
        MedicalHospitalAccount mha =  hospitalAccountMapper.getMedicalHospitalStatusByUserId("ff80808162717632016274d7c4280001");
        System.out.println(mha.toString());
        
    }

}