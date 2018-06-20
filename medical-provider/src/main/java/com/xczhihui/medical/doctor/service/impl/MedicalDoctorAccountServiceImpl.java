package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAccountMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.service.IMedicalDoctorAccountService;
import com.xczhihui.medical.hospital.service.IMedicalHospitalApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class MedicalDoctorAccountServiceImpl extends ServiceImpl<MedicalDoctorAccountMapper, MedicalDoctorAccount> implements IMedicalDoctorAccountService {

    @Autowired
    private MedicalDoctorAccountMapper medicalDoctorAccountMapper;

    @Override
    public MedicalDoctorAccount getByUserId(String userId) {
        return medicalDoctorAccountMapper.getByUserId(userId);
    }
}
