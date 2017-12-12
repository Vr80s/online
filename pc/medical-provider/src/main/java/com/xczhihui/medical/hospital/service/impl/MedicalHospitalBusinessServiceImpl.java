package com.xczhihui.medical.hospital.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
        page.setRecords(medicalHospitalMapper.selectHospitalList(page,name,field));
        for (int i = 0; i < page.getRecords().size(); i++) {
            List<MedicalHospitalPicture> medicalHospitalPictures = medicalHospitalPictureMapper.getMedicalHospitalPictureByHospitalId(page.getRecords().get(i).getId());
            page.getRecords().get(i).setMedicalHospitalPictures(medicalHospitalPictures);
        }
        return page;
    }

    @Override
    public MedicalHospital selectHospitalById(String id) {
        return medicalHospitalMapper.selectHospitalById(id);
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
