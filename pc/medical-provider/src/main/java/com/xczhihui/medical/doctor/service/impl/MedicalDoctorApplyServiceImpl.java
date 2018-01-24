package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.department.mapper.MedicalDepartmentMapper;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.doctor.enums.MedicalDoctorApplyEnum;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAccountMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorApplyDepartmentMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorApplyMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.medical.doctor.model.MedicalDoctorApplyDepartment;
import com.xczhihui.medical.doctor.service.IMedicalDoctorApplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2018-01-15
 */
@Service
public class MedicalDoctorApplyServiceImpl extends ServiceImpl<MedicalDoctorApplyMapper, MedicalDoctorApply> implements IMedicalDoctorApplyService {

    @Autowired
    private MedicalDoctorApplyMapper medicalDoctorApplyMapper;
    @Autowired
    private MedicalDoctorApplyDepartmentMapper applyDepartmentMapper;
    @Autowired
    private MedicalDepartmentMapper medicalDepartmentMapper;
    @Autowired
    private MedicalDoctorAccountMapper doctorAccountMapper;

    /**
     * 添加医师入驻申请信息
     * @param target 医师入驻申请信息
     */
    @Override
    public void add(MedicalDoctorApply target) {

        // 参数校验
        this.validate(target);

        // 如果该用户已经是医师
        if(doctorAccountMapper.getByUserId(target.getUserId()) != null){
            throw new RuntimeException("您已经是医师，不能再申请认证");
        }

        MedicalDoctorApply oldApply = this.getLastOne(target.getUserId());
        if(oldApply != null){
            // 如果该医师已申请 但状态为：未处理 则直接返回
            if(oldApply.getStatus().equals(MedicalDoctorApplyEnum.WAIT.getCode()) ){
                throw new RuntimeException("您已提交申请，请等待管理员审核");
            }else {
                // 如果为其他状态 则表示需要重新认证
                // 删除之前的申请
                medicalDoctorApplyMapper.delete(target.getUserId());
            }
        }

        // 生成主键
        String id = UUID.randomUUID().toString().replace("-","");
        target.setId(id);

        // 设置初始值 默认不删除 默认未处理
        target.setDeleted(false);
        target.setStatus(MedicalDoctorApplyEnum.WAIT.getCode());

        Date now = new Date();

        // 若科室不为空 则添加申请关联的科室信息
        if(target.getDepartments() != null && (!target.getDepartments().isEmpty())) {
            target.getDepartments()
                    .stream()
                    .forEach(department -> this.completeDepartmentField(department, id, now));
            applyDepartmentMapper.batchAdd(target.getDepartments());
        }

        medicalDoctorApplyMapper.insert(target);
    }

    /**
     * 根据userId获取用户最后一条医师入驻申请信息
     * @param userId 用户id
     * @return 医师入驻申请信息
     */
    @Override
    public MedicalDoctorApply getLastOne(String userId) {
        MedicalDoctorApply target = medicalDoctorApplyMapper.getLastOne(userId);
        if(target != null){
            // 获取申请入驻时关联的科室
            List<MedicalDoctorApplyDepartment> departmentList =
                    applyDepartmentMapper.getByDoctorApplyId(target.getId());
            // 获取科室名称
            if(departmentList != null){
                List<String> ids = new ArrayList<>();
                for(MedicalDoctorApplyDepartment department : departmentList){
                    ids.add(department.getDepartmentId());
                }
                List<MedicalDepartment> medicalDepartments =
                        medicalDepartmentMapper.selectBatchIds(ids);
                target.setMedicalDepartments(medicalDepartments);
            }
        }
        return target;
    }

    private void completeDepartmentField(MedicalDoctorApplyDepartment department, String id, Date now) {
        department.setId(UUID.randomUUID().toString().replace("-",""));
        department.setDoctorApplyId(id);
        department.setCreateTime(now);
    }

    /**
     * 参数校验
     * @param target 医师入驻申请信息
     */
    private void validate(MedicalDoctorApply target) {

        if(StringUtils.isBlank(target.getName())){
            throw new RuntimeException("请填写真实姓名");
        }else{
            if(target.getName().length() > 32){
                throw new RuntimeException("真实姓名应保持在32字以内");
            }
        }
        if(StringUtils.isBlank(target.getCardNum())){
            throw new RuntimeException("请填写身份证号");
        }else{
            if(target.getCardNum().length() > 32){
                throw new RuntimeException("身份证号应保持在32字以内");
            }
        }
        if(StringUtils.isBlank(target.getCardPositive())){
            throw new RuntimeException("请上传身份证正面");
        }
        if(StringUtils.isBlank(target.getCardNegative())){
            throw new RuntimeException("请上传身份证反面");
        }
        if(StringUtils.isBlank(target.getQualificationCertificate())){
            throw new RuntimeException("请上传医师资格证");
        }
        if(StringUtils.isNotBlank(target.getField()) && target.getField().length() >32){
            throw new RuntimeException("擅长领域内容应保持在32字以内");
        }
    }
}
