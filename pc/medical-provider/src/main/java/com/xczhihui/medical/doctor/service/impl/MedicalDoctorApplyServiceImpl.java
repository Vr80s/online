package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.medical.department.mapper.MedicalDepartmentMapper;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.doctor.enums.MedicalDoctorApplyEnum;
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
    private AttachmentCenterService attachmentCenterService;

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
     * 根据userId获取医师入驻申请信息
     * @param userId 用户id
     * @return 医师入驻申请信息
     */
    @Override
    public MedicalDoctorApply get(String userId) {
        MedicalDoctorApply target = medicalDoctorApplyMapper.getLastOne(userId);
        if(target != null){
            // 获取申请入驻时关联的科室
            List<MedicalDoctorApplyDepartment> departmentList =
                    applyDepartmentMapper.getByDoctorApplyId(target.getId());
            // 获取科室名称
            if(departmentList != null && (!departmentList.isEmpty())){
                List<String> ids = new ArrayList<>();
                for(MedicalDoctorApplyDepartment department : departmentList){
                    ids.add(department.getDepartmentId());
                }
                List<MedicalDepartment> medicalDepartments =
                        medicalDepartmentMapper.selectBatchIds(ids);
                target.setMedicalDepartments(medicalDepartments);
                return target;
            }
        }
        return null;
    }

    /**
     * 获取科室列表
     * @return 科室列表
     */
    @Override
    public List<MedicalDepartment> listDepartment() {
        Wrapper<MedicalDepartment> wrapper = new EntityWrapper(MedicalDepartment.class);
        return medicalDepartmentMapper.selectList(wrapper);
    }

    /**
     * 上传图片
     * @param image 图片
     * @return 图片路径
     */
    @Override
    public String upload(byte[] image, String userId) {

        Attachment attachment = attachmentCenterService.addAttachment(userId,
                AttachmentType.ONLINE,
                userId + "_medicalDoctorApply.png", image,
                org.springframework.util.StringUtils.getFilenameExtension(userId+"_medicalDoctorApply.png"),
                null);

        return attachment.getUrl();
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
