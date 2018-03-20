package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAccountMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAuthenticationInformationMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.model.MedicalDoctorAuthenticationInformation;
import com.xczhihui.medical.doctor.service.IMedicalDoctorAuthenticationInformationService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorDepartmentService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorAuthenticationInformationVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    @Autowired
    private IMedicalDoctorDepartmentService doctorDepartmentService;

    /**
     * 根据用户id获取其医师认证信息
     * @param userId 用户id
     */
    @Override
    public MedicalDoctorAuthenticationInformationVO selectDoctorAuthenticationVO(String userId) {

        // 根据用户id获取其医师id
        MedicalDoctorAccount doctorAccount = doctorAccountMapper.getByUserId(userId);

//        if(doctorAccount != null){
//            // 根据医师id获取其认证信息id
//            MedicalDoctor doctor = doctorMapper.selectById(doctorAccount.getDoctorId());
//            if(doctor != null && StringUtils.isNotBlank(doctor.getAuthenticationInformationId())){
//                // 根据认证信息id获取其认证信息
//                MedicalDoctorAuthenticationInformation authenticationInformation =
//                        doctorAuthenticationInformationMapper.selectById(doctor.getAuthenticationInformationId());
//                if(authenticationInformation != null){
//                    return this.processDoctorAuthenticationInformation(authenticationInformation, doctor);
//                }
//            }
//        }

        MedicalDoctor doctor = Optional.ofNullable(doctorAccount)
                .map(optional -> doctorMapper.selectById(optional.getDoctorId()))
                .orElse(null);

        return Optional.ofNullable(doctor)
                .map(optional -> optional.getAuthenticationInformationId())
                .map(optional -> doctorAuthenticationInformationMapper.selectById(optional))
                .map(optional -> this.processDoctorAuthenticationInformation(optional, doctor, userId))
                .orElse(null);

    }

    private MedicalDoctorAuthenticationInformationVO processDoctorAuthenticationInformation(MedicalDoctorAuthenticationInformation authenticationInformation, MedicalDoctor doctor, String userId) {

        MedicalDoctorAuthenticationInformationVO vo = new MedicalDoctorAuthenticationInformationVO();
        BeanUtils.copyProperties(authenticationInformation, vo);

        vo.setName(doctor.getName());
        vo.setCardNum(doctor.getCardNum());

        if(StringUtils.isNotBlank(doctor.getTitle())){
            vo.setTitle(doctor.getTitle());
        }
        if(StringUtils.isNotBlank(doctor.getFieldText())){
            vo.setFieldText(doctor.getFieldText());
        }
        if(StringUtils.isNotBlank(doctor.getDescription())){
            vo.setDescription(doctor.getDescription());
        }
        if(StringUtils.isNotBlank(doctor.getProvince())){
            vo.setProvince(doctor.getProvince());
        }
        if(StringUtils.isNotBlank(doctor.getCity())){
            vo.setCity(doctor.getCity());
        }
        if(StringUtils.isNotBlank(doctor.getDetailedAddress())){
            vo.setDetailedAddress(doctor.getDetailedAddress());
        }

        List<MedicalDepartment> departments = doctorDepartmentService.selectByUserId(userId);
        vo.setMedicalDepartments(departments);

        return vo;
    }

}
