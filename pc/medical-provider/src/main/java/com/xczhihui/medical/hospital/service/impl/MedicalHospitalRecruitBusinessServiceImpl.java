package com.xczhihui.medical.hospital.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalRecruitMapper;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.vo.MedicalHospitalRecruitVo;
import com.xczhihui.medical.hospital.service.IMedicalHospitalRecruitBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  医馆招聘业务接口类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class MedicalHospitalRecruitBusinessServiceImpl extends ServiceImpl<MedicalHospitalMapper, MedicalHospital> implements IMedicalHospitalRecruitBusinessService {

    @Autowired
    private MedicalHospitalRecruitMapper medicalHospitalRecruitMapper;


    @Override
    public List<MedicalHospitalRecruitVo> selectRecHospitalRecruit() {
        return medicalHospitalRecruitMapper.selectRecHospitalRecruit();
    }

    @Override
    public List<MedicalHospitalRecruitVo> selectHospitalRecruitByHospitalId(String hospitalId) {
        return medicalHospitalRecruitMapper.selectHospitalRecruitByHospitalId(hospitalId);
    }
}
