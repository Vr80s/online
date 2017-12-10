package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: MedicalDoctorBusinessServiceImpl.java <br>
 * Description:医师业务接口类 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 下午 11:24 2017/12/9 0009<br>
 */
@Service
public class MedicalDoctorBusinessServiceImpl implements IMedicalDoctorBusinessService{

    @Autowired
    private MedicalDoctorMapper medicalDoctorMapper;

    @Override
    public Page<MedicalDoctor> selectDoctorPage(Page<MedicalDoctor> page) {
        page.setRecords(medicalDoctorMapper.selectDoctorList(page));
        return page;
    }

    @Override
    public MedicalDoctor selectDoctorById(String id) {
        return medicalDoctorMapper.selectDoctorById(id);
    }
}
