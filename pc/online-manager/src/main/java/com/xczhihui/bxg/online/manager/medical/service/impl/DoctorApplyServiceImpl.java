package com.xczhihui.bxg.online.manager.medical.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.bxg.online.manager.medical.dao.*;
import com.xczhihui.bxg.online.manager.medical.service.DoctorApplyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 医师入驻申请服务实现层
 * @author zhuwenbao
 */
@Service
public class DoctorApplyServiceImpl implements DoctorApplyService {

    @Autowired
    private DoctorApplyDao doctorApplyDao;

    /**
     * 获取医师入驻申请列表
     * @param searchVo 查询条件
     * @param currentPage 当前页
     * @param pageSize 每页显示的列数
     * @return 医师入驻申请分页列表
     */
    @Override
    public Page<MedicalDoctorApply> list(MedicalDoctorApply searchVo, int currentPage, int pageSize) {

        return doctorApplyDao.list(searchVo, currentPage, pageSize);
    }

    /**
     * 更新医师入驻申请状态
     * @param doctorApply 更新的数据封装类
     */
    @Override
    public void updateStatus(MedicalDoctorApply doctorApply) {
        if(doctorApply == null){
            return;
        }
        if(StringUtils.isBlank(doctorApply.getId())){
            throw new RuntimeException("请选择要修改的目标");
        }
        if(doctorApply.getStatus() == null){
            throw new RuntimeException("缺少请求参数：status");
        }

        String id = doctorApply.getId();
        Integer status = doctorApply.getStatus();

        // 根据id获取被修改的认证信息详情
        MedicalDoctorApply apply = doctorApplyDao.find(id);
        if(apply == null){
            throw new RuntimeException("操作失败：该条信息不存在");
        }
        // 前台传来的状态和数据库的状态一致 不予处理
        if(apply.getStatus().equals(status)){
            return;
        }

        // 更新认证状态
        apply.setStatus(status);
        doctorApplyDao.update(apply);

        switch (status){
            // 当status = 1 即认证通过
            case 1:
                this.authenticationPassHandle(apply);
                break;
            // 当status = 0 即认证被拒
            case 0:
                this.authenticationRejectHandle();
                break;
            default:
                break;
        }
    }

    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private DoctorAccountDao doctorAccountDao;
    @Autowired
    private DoctorApplyDepartmentDao doctorApplyDepartmentDao;
    @Autowired
    private DoctorDepartmentDao doctorDepartmentDao;
    @Autowired
    private DoctorAuthenticationInformationDao doctorAuthenticationInformationDao;

    /**
     * 处理认证通过逻辑
     */
    private void authenticationPassHandle(MedicalDoctorApply apply) {

        Date now = new Date();

        String doctorId = UUID.randomUUID().toString().replace("-","");

        // 新增医师与用户的对应关系：medical_doctor_account
        // 判断用户是否已经是医师
        if(doctorAccountDao.findByAccountId(apply.getUserId()) != null){
            throw new RuntimeException("该用户已是医师，不能在进行认证");
        }

        MedicalDoctorAccount doctorAccount =
                new MedicalDoctorAccount();
        doctorAccount.setId(UUID.randomUUID().toString().replace("-",""));
        doctorAccount.setAccountId(apply.getUserId());
        doctorAccount.setDoctorId(doctorId);
        doctorAccount.setCreateTime(now);
        doctorAccountDao.save(doctorAccount);

        // 新增医师信息：medical_doctor
        MedicalDoctor doctor = new MedicalDoctor();
        doctor.setId(doctorId);
        doctor.setName(apply.getName());
        if(StringUtils.isNotBlank(apply.getTitle())){
            doctor.setTitle(apply.getTitle());
        }
        if(StringUtils.isNotBlank(apply.getDescription())){
            doctor.setDescription(apply.getDescription());
        }
        if(StringUtils.isNotBlank(apply.getProvince())){
            doctor.setProvince(apply.getProvince());
        }
        if(StringUtils.isNotBlank(apply.getCity())){
            doctor.setCity(apply.getCity());
        }
        if(StringUtils.isNotBlank(apply.getDetailedAddress())){
            doctor.setDetailedAddress(apply.getDetailedAddress());
        }
        doctor.setCreateTime(now);
        String authenticationInformationId = UUID.randomUUID().toString().replace("-","");
        doctor.setAuthenticationInformationId(authenticationInformationId);
        doctorDao.save(doctor);

        // 新增医师与科室的对应关系：medical_doctor_department
        List<MedicalDoctorApplyDepartment> medicalDoctorApplyDepartments =
                doctorApplyDepartmentDao.findByApplyId(apply.getId());

        // 将MedicalDoctorApplyDepartment数据格式转化成：MedicalDoctorDepartment
        if(medicalDoctorApplyDepartments != null && !medicalDoctorApplyDepartments.isEmpty()){
            medicalDoctorApplyDepartments
                    .stream()
                    .forEach(department -> this.saveMedicalDepartment(department, doctorId, now));
        }

        // 新增认证成功信息：medical_doctor_authentication_information
        MedicalDoctorAuthenticationInformation authenticationInformation =
                new MedicalDoctorAuthenticationInformation();
        authenticationInformation.setId(authenticationInformationId);
        authenticationInformation.setCardPositive(apply.getCardPositive());
        authenticationInformation.setCardNegative(apply.getCardNegative());
        authenticationInformation.setQualificationCertificate(apply.getQualificationCertificate());

        if(StringUtils.isNotBlank(apply.getTitleProve())){
            authenticationInformation.setTitleProve(apply.getTitleProve());
        }
        if(StringUtils.isNotBlank(apply.getProfessionalCertificate())){
            authenticationInformation.setProfessionalCertificate(apply.getProfessionalCertificate());
        }
        if(StringUtils.isNotBlank(apply.getHeadPortrait())){
            authenticationInformation.setHeadPortrait(apply.getHeadPortrait());
        }

        authenticationInformation.setDeleted(false);
        authenticationInformation.setStatus(true);
        authenticationInformation.setCreateTime(now);
        doctorAuthenticationInformationDao.save(authenticationInformation);
    }

    private void saveMedicalDepartment(MedicalDoctorApplyDepartment department, String doctorId, Date createTime){
        MedicalDoctorDepartment doctorDepartment = new MedicalDoctorDepartment();
        doctorDepartment.setId(UUID.randomUUID().toString().replace("-",""));
        doctorDepartment.setDoctorId(doctorId);
        doctorDepartment.setDepartmentId(department.getDepartmentId());
        doctorDepartment.setCreateTime(createTime);
        doctorDepartmentDao.save(doctorDepartment);
    }

    /**
     * 处理认证失败逻辑
     */
    private void authenticationRejectHandle(){

    }
}
