package com.xczhihui.medical.doctor.service;

import com.xczhihui.medical.department.model.MedicalDepartment;

import java.util.Date;

/**
 *  医师对应科室服务接口
 *  @author zhuwenbao
 */
public interface IMedicalDoctorDepartmentService {

    /**
     * 添加医师科室
     * @param department 科室信息
     * @param doctorId 医师id
     * @param createTime 创建时间
     */
    void add(MedicalDepartment department, String doctorId, Date createTime);

}
