package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorDepartment;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorDepartmentMapper;
import com.xczhihui.medical.doctor.service.IMedicalDoctorDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * 医师对应科室服务实现类
 * @author zhuwenbao
 */
@Service
public class MedicalDoctorDepartmentServiceImpl extends ServiceImpl<MedicalDoctorDepartmentMapper, MedicalDoctorDepartment> implements IMedicalDoctorDepartmentService {

    @Autowired
    private MedicalDoctorDepartmentMapper doctorDepartmentMapper;

    /**
     * 添加医师科室
     * @param department 科室信息
     * @param doctorId 医师id
     * @param createTime 创建时间
     */
    @Override
    public void add(MedicalDepartment department, String doctorId, Date createTime) {
        MedicalDoctorDepartment doctorDepartment = new MedicalDoctorDepartment();
        doctorDepartment.setId(UUID.randomUUID().toString().replace("-",""));
        doctorDepartment.setDoctorId(doctorId);
        doctorDepartment.setDepartmentId(department.getId());
        doctorDepartment.setCreateTime(createTime);
        doctorDepartmentMapper.insert(doctorDepartment);
    }
}
