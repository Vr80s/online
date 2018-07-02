package com.xczhihui.medical.doctor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAccountMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.service.IMedicalDoctorAccountService;

/**
 * <p>
 * 服务实现类
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
