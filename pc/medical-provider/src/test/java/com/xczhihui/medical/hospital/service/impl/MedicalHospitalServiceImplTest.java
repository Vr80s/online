package com.xczhihui.medical.hospital.service.impl;

import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

public class MedicalHospitalServiceImplTest extends BaseJunit4Test {

    @Autowired
    private IMedicalHospitalBusinessService service;

    /**
     * 删除医馆的医师
     */
    @Test
    public void testDelete(){

        service.deleteDoctor("402880e860c4ebe30160c51302660000", "ae5492665cb5412cac65e553626c7694");
    }

    /**
     * 更新医馆信息
     */
    @Test
    public void testUpdate(){

        MedicalHospital medicalHospital = new MedicalHospital();

        medicalHospital.setUpdatePerson("402880e860c4ebe30160c51302660000");
        medicalHospital.setDetailedAddress("0-12930197210283109");
        service.update(medicalHospital);

    }

}