package com.xczhihui.medical.doctor.service;


import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;

import java.util.List;

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

    /**
     * 获取科室列表
     * @return 科室列表
     */
    List<MedicalDepartment> listDepartment();

    /**
     * 上传图片
     * @param image 图片
     * @param userId 上传人id
     * @return 图片路径
     */
    String upload(byte[] image, String userId);
}
