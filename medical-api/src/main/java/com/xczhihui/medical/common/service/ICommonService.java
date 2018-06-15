package com.xczhihui.medical.common.service;

import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.hospital.model.MedicalHospitalAccount;

/**
 * 医师医馆公共服务接口
 */
public interface ICommonService {

    /**
     * 根据用户id 判断用户是否是认证医师
     * @param userId 用户id
     */
    boolean isDoctor(String userId);

    /**
     * 根据用户id 判断用户拥有认证医馆
     * @param userId 用户id
     */
    boolean isHospital(String userId);

    /**
     * 根据用户id 判断用户是医师还是医馆
     * @param userId 用户id
     * @return 1：认证医师 2：认证医馆 3：医师认证中 4：医馆认证中 5：医师认证被拒 6：医馆认证被拒 7：即没有认证医师也没有认证医馆
     */
    Integer isDoctorOrHospital(String userId);

	MedicalDoctorAccount isDoctorStatus(String userId);

	MedicalHospitalAccount isHospitalStatus(String userId);
}
