package com.xczhihui.medical.hospital.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.field.mapper.MedicalFieldMapper;
import com.xczhihui.medical.field.model.MedicalField;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalPictureMapper;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.model.MedicalHospitalPicture;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  医馆业务接口类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class MedicalHospitalBusinessServiceImpl extends ServiceImpl<MedicalHospitalMapper, MedicalHospital> implements IMedicalHospitalBusinessService {

    @Autowired
    private MedicalHospitalMapper medicalHospitalMapper;
    @Autowired
    private MedicalHospitalPictureMapper medicalHospitalPictureMapper;


    public Page<MedicalHospital> selectHospitalPage(Page<MedicalHospital> page, String name, String field) {
        List<String> mhIds = medicalHospitalMapper.selectHospitalIdList(page, name, field);
        List<MedicalHospital> medicalHospitals = medicalHospitalMapper.selectHospitalAndPictureList(mhIds);
        page.setRecords(medicalHospitals);
        return page;
    }

    @Override
    public MedicalHospital selectHospitalById(String id) {
        MedicalHospital medicalHospital = medicalHospitalMapper.selectHospitalById(id);
        return medicalHospital;
    }

    @Override
    public List<MedicalHospital> selectRecHospital() {
        return medicalHospitalMapper.selectRecHospital();
    }

    @Override
    public List<MedicalField> getHotField() {
        return medicalHospitalMapper.getHotField();
    }


}
