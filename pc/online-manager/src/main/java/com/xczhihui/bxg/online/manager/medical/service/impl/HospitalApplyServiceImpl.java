package com.xczhihui.bxg.online.manager.medical.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.bxg.online.manager.medical.dao.*;
import com.xczhihui.bxg.online.manager.medical.service.HospitalApplyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * 医师入驻申请服务实现层
 * @author zhuwenbao
 */
@Service
public class HospitalApplyServiceImpl implements HospitalApplyService {

    @Autowired
    private HospitalApplyDao hospitalApplyDao;
    @Autowired
    private HospitalDao hospitalDao;
    @Autowired
    private HospitalAccountDao hospitalAccountDao;
    @Autowired
    private HospitalAuthenticationDao hospitalAuthenticationDao;
    @Autowired
    private DoctorAccountDao doctorAccountDao;

    /**
     * 获取医师入驻申请列表
     * @param searchVo 查询条件
     * @param currentPage 当前页
     * @param pageSize 每页显示的列数
     * @return 医师入驻申请分页列表
     */
    @Override
    public Page<MedicalHospitalApply> list(MedicalHospitalApply searchVo, int currentPage, int pageSize) {

        return hospitalApplyDao.list(searchVo, currentPage, pageSize);
    }

    /**
     * 更新医师入驻申请状态
     * @param hospitalApply 更新的数据封装类
     */
    @Override
    public void updateStatus(MedicalHospitalApply hospitalApply) {
        if(hospitalApply == null){
            return;
        }

        String id = hospitalApply.getId();
        Integer status = hospitalApply.getStatus();

        if(StringUtils.isBlank(id)){
            throw new RuntimeException("请选择要修改的目标");
        }
        if(status == null){
            throw new RuntimeException("缺少请求参数：status");
        }

        // 根据id获取被修改的认证信息详情
        MedicalHospitalApply apply = hospitalApplyDao.find(id);
        if(apply == null){
            throw new RuntimeException("操作失败：该条信息不存在");
        }
        // 前台传来的状态和数据库的状态一致 不予处理
        if(apply.getStatus().equals(status)){
            return;
        }

        switch (status){
            // 当status = 0 即认证被拒
            case 0:
                this.authenticationRejectHandle();
                break;
            // 当status = 1 即认证通过
            case 1:
                this.authenticationPassHandle(apply);
                break;
            default:
                break;
        }

        // 更新认证状态
        apply.setStatus(status);
        apply.setUpdateTime(new Date());
        hospitalApplyDao.update(apply);
    }

    /**
     * 根据id查询详情
     * @param applyId
     */
    @Override
    public MedicalHospitalApply findById(String applyId) {

        return hospitalApplyDao.find(applyId);

    }

    /**
     * 处理认证通过逻辑
     */
    private void authenticationPassHandle(MedicalHospitalApply apply) {

        Date now = new Date();

        String hospitalId = UUID.randomUUID().toString().replace("-","");

        // 判断用户是否已经是认证医师（医师 医馆只能认证一个）
        MedicalDoctorAccount doctorAccount = doctorAccountDao.findByAccountId(apply.getUserId());
        if(doctorAccount != null){
            throw new RuntimeException("该用户已是医师，不能再进行认证医馆");
        }

        // 判断用户是否已经已拥有验证医馆
        MedicalHospitalAccount hospitalAccount = hospitalAccountDao.findByAccountId(apply.getUserId());
        if(hospitalAccountDao.findByAccountId(apply.getUserId()) != null &&
                StringUtils.isNotBlank(hospitalAccount.getDoctorId())){
            MedicalHospital hospital = hospitalDao.find(hospitalAccount.getDoctorId());
            if(hospital != null && hospital.isAuthentication() == true){
                throw new RuntimeException("该用户已拥有已认证医馆，不能再进行认证");
            }
        }

        // 新增医师与用户的对应关系：medical_hospital_account
        MedicalHospitalAccount newHospitalAccount =
                new MedicalHospitalAccount();
        newHospitalAccount.setId(UUID.randomUUID().toString().replace("-",""));
        newHospitalAccount.setAccountId(apply.getUserId());
        newHospitalAccount.setDoctorId(hospitalId);
        newHospitalAccount.setCreateTime(now);
        hospitalAccountDao.save(newHospitalAccount);

        // 新增医馆信息：medical_hospital
        MedicalHospital hospital = new MedicalHospital();
        hospital.setId(hospitalId);
        hospital.setName(hospitalId);
        hospital.setCreateTime(now);
        hospital.setAuthentication(true);
        hospital.setDeleted(false);
        hospital.setStatus(false);
        String authenticationInformationId = UUID.randomUUID().toString().replace("-","");
        hospital.setAuthenticationId(authenticationInformationId);
        hospitalDao.save(hospital);

        // 新增认证成功信息：medical_hospital_authentication
        MedicalHospitalAuthentication authenticationInformation =
                new MedicalHospitalAuthentication();
        authenticationInformation.setCompany(apply.getCompany());
        authenticationInformation.setId(authenticationInformationId);
        authenticationInformation.setBusinessLicenseNo(apply.getBusinessLicenseNo());
        authenticationInformation.setBusinessLicensePicture(apply.getBusinessLicensePicture());
        authenticationInformation.setLicenseForPharmaceuticalTrading(apply.getLicenseForPharmaceuticalTrading());
        authenticationInformation.setLicenseForPharmaceuticalTradingPicture(apply.getLicenseForPharmaceuticalTradingPicture());

        authenticationInformation.setDeleted(false);
        authenticationInformation.setCreateTime(now);
        hospitalAuthenticationDao.save(authenticationInformation);
    }

    /**
     * 处理认证失败逻辑
     */
    private void authenticationRejectHandle(){

    }
}
