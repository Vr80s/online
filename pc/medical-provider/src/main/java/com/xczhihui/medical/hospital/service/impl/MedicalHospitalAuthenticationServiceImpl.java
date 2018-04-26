package com.xczhihui.medical.hospital.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.exception.MedicalException;
import com.xczhihui.medical.hospital.mapper.*;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.model.MedicalHospitalAccount;
import com.xczhihui.medical.hospital.model.MedicalHospitalAuthentication;
import com.xczhihui.medical.hospital.service.IMedicalHospitalAuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 医馆认证信息服务实现类
 * @author zhuwenbao
 */
@Service
public class MedicalHospitalAuthenticationServiceImpl extends ServiceImpl<MedicalHospitalAuthenticationMapper, MedicalHospitalAuthentication> implements IMedicalHospitalAuthenticationService {


    @Autowired
    private MedicalHospitalMapper medicalHospitalMapper;
    @Autowired
    private MedicalHospitalAccountMapper hospitalAccountMapper;
    @Autowired
    private MedicalHospitalAuthenticationMapper hospitalAuthenticationMapper;

    /**
     * 根据用户id获取其医馆认证信息
     * @param userId 用户id
     */
    @Override
    public MedicalHospitalAuthentication selectHospitalAuthentication(String userId) {

        // 根据用户id获取其医馆id
        MedicalHospitalAccount hospitalAccount = hospitalAccountMapper.getByUserId(userId);

        if(hospitalAccount == null){
            throw new MedicalException("您尚未拥有医馆");
        }

        // 根据医馆id获取医馆的认证信息id
        MedicalHospital medicalHospital = medicalHospitalMapper.selectById(hospitalAccount.getDoctorId());

        if(medicalHospital != null && StringUtils.isNotBlank(medicalHospital.getAuthenticationId())){
            MedicalHospitalAuthentication authentication =
                    hospitalAuthenticationMapper.selectById(medicalHospital.getAuthenticationId());
            if(authentication != null){
                authentication.setName(medicalHospital.getName());
                return authentication;
            }
        }

        return null;

    }
}
