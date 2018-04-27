package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.xczhihui.medical.exception.MedicalException;
import com.xczhihui.medical.department.mapper.MedicalDepartmentMapper;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAccountMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorDepartmentMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.model.MedicalDoctorDepartment;
import com.xczhihui.medical.doctor.service.IMedicalDoctorDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 医师对应科室服务实现类
 * @author zhuwenbao
 */
@Service
public class MedicalDoctorDepartmentServiceImpl extends ServiceImpl<MedicalDoctorDepartmentMapper, MedicalDoctorDepartment> implements IMedicalDoctorDepartmentService {

    @Autowired
    private MedicalDoctorDepartmentMapper doctorDepartmentMapper;
    @Autowired
    private MedicalDoctorAccountMapper doctorAccountMapper;
    @Autowired
    private MedicalDepartmentMapper departmentMapper;

    /**
     * 添加医师科室
     * @param departmentId 科室信息
     * @param doctorId 医师id
     * @param createTime 创建时间
     */
    @Override
    public void add(String departmentId, String doctorId, Date createTime) {
        MedicalDoctorDepartment doctorDepartment = new MedicalDoctorDepartment();
        doctorDepartment.setId(UUID.randomUUID().toString().replace("-",""));
        doctorDepartment.setDoctorId(doctorId);
        doctorDepartment.setDepartmentId(departmentId);
        doctorDepartment.setCreateTime(createTime);
        doctorDepartmentMapper.insert(doctorDepartment);
    }

    /**
     * 根据医师id获取科室列表
     * @param doctorId 医师id
     * @return 科室列表
     */
    @Override
    public List<MedicalDoctorDepartment> selectByDoctorId(String doctorId) {
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("doctor_id", doctorId);
        columnMap.put("deleted", 0);
        List<MedicalDoctorDepartment> doctorDepartments =  doctorDepartmentMapper.selectByMap(columnMap);
        return doctorDepartments;
    }

    /**
     * 根据用户id获取科室列表
     * @param userId 用户id
     * @return 科室列表
     */
    @Override
    public List<MedicalDepartment> selectByUserId(String userId) {

        // 根据用户id获取其医馆
        MedicalDoctorAccount doctorAccount = doctorAccountMapper.getByUserId(userId);
        if(doctorAccount == null){
            throw new MedicalException("您暂不是医师，请认证后再来");
        }

        // 根据医师id获取其科室
        List<MedicalDoctorDepartment> doctorDepartments = this.selectByDoctorId(doctorAccount.getDoctorId());

        if(CollectionUtils.isNotEmpty(doctorDepartments)){
            List<String> ids = doctorDepartments.stream().map(doctorDepartment -> doctorDepartment.getDepartmentId()).collect(Collectors.toList());

            List<MedicalDepartment> departments = departmentMapper.selectBatchIds(ids);

            return departments;

        }

        return null;
    }
}
