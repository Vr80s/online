package com.xczhihui.medical.hospital.service;

import com.xczhihui.medical.hospital.model.MedicalHospitalAuthentication;

/**
 *  医馆认证信息服务类
 *  @author zhuwenbao
 */
public interface IMedicalHospitalAuthenticationService {

    /**
     * 根据用户id获取其医馆认证信息
     * @param userId 用户id
     */
    MedicalHospitalAuthentication selectHospitalAuthentication(String userId);

}
