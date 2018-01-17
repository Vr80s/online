package com.xczhihui.medical.doctor.service;


import com.xczhihui.medical.doctor.model.MedicalDoctorApply;

/**
 *  医师入驻申请服务类
 *  @author zhuwenbao
 */
public interface IMedicalDoctorApplyService {

    /**
     * 添加医师入驻申请信息
     * @param target 医师申请入驻申请信息
     */
    void add(MedicalDoctorApply target);

    /**
     * 根据userId获取医师入驻申请信息
     * @param userId 用户id
     * @return 医师入驻申请信息
     */
    MedicalDoctorApply get(String userId);

}
