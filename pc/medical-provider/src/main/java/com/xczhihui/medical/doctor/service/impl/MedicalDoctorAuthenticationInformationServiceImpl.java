package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAccountMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAuthenticationInformationMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.model.MedicalDoctorAuthenticationInformation;
import com.xczhihui.medical.doctor.service.IMedicalDoctorAuthenticationInformationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 医师认证信息服务实现类
 * @author zhuwenbao
 */
@Service
public class MedicalDoctorAuthenticationInformationServiceImpl extends ServiceImpl<MedicalDoctorAuthenticationInformationMapper, MedicalDoctorAuthenticationInformation> implements IMedicalDoctorAuthenticationInformationService {

    @Autowired
    private MedicalDoctorAccountMapper doctorAccountMapper;
    @Autowired
    private MedicalDoctorMapper doctorMapper;
    @Autowired
    private MedicalDoctorAuthenticationInformationMapper doctorAuthenticationInformationMapper;
    /**
     * 根据用户id获取其医师认证信息
     * @param userId 用户id
     */
    @Override
    public MedicalDoctorAuthenticationInformation selectDoctorAuthentication(String userId) {

        // 根据用户id获取其医师id
        MedicalDoctorAccount doctorAccount = doctorAccountMapper.getByUserId(userId);

        if(doctorAccount != null){

            // 根据医师id获取其认证信息id
            MedicalDoctor doctor = doctorMapper.selectById(doctorAccount.getDoctorId());
            if(doctor != null && StringUtils.isNotBlank(doctor.getAuthenticationInformationId())){

                // 根据认证信息id获取其认证信息
                MedicalDoctorAuthenticationInformation authenticationInformation =
                    doctorAuthenticationInformationMapper.selectById(doctor.getAuthenticationInformationId());

                return authenticationInformation;
            }

        }

        return null;
    }
}
