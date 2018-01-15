package com.xczhihui.medical.hospital.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.field.vo.MedicalFieldVO;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalMapper;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
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


    @Override
    public Page<MedicalHospitalVo> selectHospitalPage(Page<MedicalHospitalVo> page, String name, String field) {
        List<String> mhIds = medicalHospitalMapper.selectHospitalIdList(page, name, field);
        if(mhIds.size()>0){
            List<MedicalHospitalVo> medicalHospitals = medicalHospitalMapper.selectHospitalAndPictureList(mhIds);
            page.setRecords(medicalHospitals);
        }
        return page;
    }

    @Override
    public MedicalHospitalVo selectHospitalById(String id) {
        MedicalHospitalVo medicalHospitalVo = medicalHospitalMapper.selectHospitalById(id);
        return medicalHospitalVo;
    }

    @Override
    public List<MedicalHospitalVo> selectRecHospital() {
        return medicalHospitalMapper.selectRecHospital();
    }

    @Override
    public List<MedicalFieldVO> getHotField() {
        return medicalHospitalMapper.getHotField();
    }


}
