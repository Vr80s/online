package com.xczhihui.medical.hospital.service;

import com.xczhihui.medical.hospital.model.MedicalHospitalApply;

/**
 * <p>
 *  医馆入驻申请认证服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-01-15
 */
public interface IMedicalHospitalApplyService {

    /**
     * 添加医馆入驻申请认证信息
     * @param target 医馆入驻申请认证的信息封装
     */
    void add(MedicalHospitalApply target);

    /**
     * 根据userId获取医师入驻最后一条申请信息
     * @param userId 用户id
     * @return 医师入驻申请信息
     */
    MedicalHospitalApply getLastOne(String userId);
}
