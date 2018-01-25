package com.xczhihui.medical.common.service.impl;

import com.xczhihui.medical.common.enums.CommonEnum;
import com.xczhihui.medical.common.service.ICommonService;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAccountMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorApplyMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalAccountMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalApplyMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalMapper;
import com.xczhihui.medical.hospital.model.MedicalHospitalAccount;
import com.xczhihui.medical.hospital.model.MedicalHospitalApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 医师医馆公共服务实现累
 */
@Service
public class CommonServiceImpl implements ICommonService {

    @Autowired
    private MedicalDoctorAccountMapper doctorAccountMapper;
    @Autowired
    private MedicalHospitalAccountMapper hospitalAccountMapper;
    @Autowired
    private MedicalHospitalMapper hospitalMapper;
    @Autowired
    private MedicalDoctorApplyMapper doctorApplyMapper;
    @Autowired
    private MedicalHospitalApplyMapper hospitalApplyMapper;

    /**
     * 根据用户id 判断用户是否是认证医师
     * @param userId 用户id
     */
    @Override
    public boolean isDoctor(String userId) {

        MedicalDoctorAccount medicalDoctorAccount = doctorAccountMapper.getByUserId(userId);

        if(medicalDoctorAccount != null){

            return true;

        }else{

            return false;

        }
    }

    /**
     * 根据用户id 判断用户拥有认证医馆
     * @param userId 用户id
     */
    @Override
    public boolean isHospital(String userId) {

        MedicalHospitalAccount hospitalAccount = hospitalAccountMapper.getByUserId(userId);

        if(hospitalAccount != null){

            if(hospitalMapper.getAuthenticationById(hospitalAccount.getDoctorId())){

                return true;
            }

        }

        return false;
    }

    /**
     * 根据用户id 判断用户是医师还是医馆
     * @param userId 用户id
     * @return 1：认证医师 2：认证医馆 3：医师认证中 4：医馆认证中 5：医师认证被拒 6：医馆认证被拒 7：即不是医师也不是医馆
     */
    @Override
    public Integer isDoctorOrHospital(String userId) {

        // 判断是否是认证医师
        if(this.isDoctor(userId)){
            return CommonEnum.AUTH_DOCTOR.getCode();
        }else {
            // 如果不是认证医师，判断是否正在认证医师
            MedicalDoctorApply doctorApply = doctorApplyMapper.getLastOne(userId);
            if(doctorApply != null){
                Integer status = doctorApply.getStatus();
                if(status == 0){
                    return CommonEnum.HOSPITAL_APPLY_REJECT.getCode();
                }else if(status == 2){
                    return CommonEnum.DOCTOR_APPLYING.getCode();
                }
            }
        }

        // 判断是否是已认证医馆
        if(this.isHospital(userId)){
            return CommonEnum.AUTH_HOSPITAL.getCode();
        }else {
            // 如果不是已认证医馆，判断是否正在认证医馆
            MedicalHospitalApply hospitalApply = hospitalApplyMapper.getLastOne(userId);
            if(hospitalApply != null){
                Integer status = hospitalApply.getStatus();
                if(status == 0){
                    return CommonEnum.DOCTOR_APPLY_REJECT.getCode();
                }else if(status == 2){
                    return CommonEnum.HOSPITAL_APPLYING.getCode();
                }
            }
        }

        return CommonEnum.NOT_DOCTOR_AND_HOSPITAL.getCode();
    }
}
