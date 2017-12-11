package com.xczhihui.bxg.online.web.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/")
public class HospitalController {

    @Autowired
    private IMedicalHospitalBusinessService medicalHospitalBusinessServiceImpl;


    @RequestMapping(value = "/getHospitals",method= RequestMethod.GET)
    public ResponseObject listBanner(Integer type,String name){
        Page<MedicalHospital> page = new Page<>();
        page.setCurrent(1);
        page.setSize(5);
        return ResponseObject.newSuccessResponseObject(medicalHospitalBusinessServiceImpl.selectHospitalPage(page,name));
    }

}
