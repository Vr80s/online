package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.doctor.enums.MedicalDoctorApplyEnum;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorApplyDepartmentMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorApplyFieldMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorApplyMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.medical.doctor.model.MedicalDoctorApplyDepartment;
import com.xczhihui.medical.doctor.model.MedicalDoctorApplyField;
import com.xczhihui.medical.doctor.service.IMedicalDoctorApplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    private MedicalDoctorApplyFieldMapper applyFieldMapper;

    /**
     * 添加医师入驻申请信息
     * @param target 医师入驻申请信息
     */
    @Override
    public void add(MedicalDoctorApply target) {

        // 参数校验
        this.validate(target);

        // 生成主键
        String id = UUID.randomUUID().toString().replace("-","");
        target.setId(id);

        // 设置初始值 默认不删除 默认未处理（2）
        target.setDeleted(false);
        target.setStatus(MedicalDoctorApplyEnum.WAIT.getCode());

        Date now = new Date();

        // 若科室不为空 则添加申请关联的科室信息
        if(target.getDepartments() != null) {
            target.getDepartments()
                    .stream()
                    .forEach(department -> this.completeDepartmentField(department, id, now));
            applyDepartmentMapper.batchAdd(target.getDepartments());
        }

        // 若领域不为空 则添加申请关联的领域信息
        if(target.getFields() != null){
            target.getFields().stream()
                    .forEach(field -> this.completeFieldField(field, id, now));
            applyFieldMapper.batchAdd(target.getFields());
        }

        medicalDoctorApplyMapper.insert(target);
    }

    private void completeDepartmentField(MedicalDoctorApplyDepartment department, String id, Date now) {
        department.setId(UUID.randomUUID().toString().replace("-",""));
        department.setDoctorApplyId(id);
        department.setCreateTime(now);
    }

    private void completeFieldField(MedicalDoctorApplyField field, String id, Date now) {
        field.setId(UUID.randomUUID().toString().replace("-",""));
        field.setDoctorApplyId(id);
        field.setCreateTime(now);
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
                throw new RuntimeException("真实姓名应保存在32字以内");
            }
        }
        if(StringUtils.isBlank(target.getCardNum())){
            throw new RuntimeException("请填写身份证号");
        }else{
            if(target.getCardNum().length() > 40){
                throw new RuntimeException("身份证号应保存在40字以内");
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
        if(StringUtils.isBlank(target.getProfessionalCertificate())){
            throw new RuntimeException("请上传职业医师证");
        }
    }
}
