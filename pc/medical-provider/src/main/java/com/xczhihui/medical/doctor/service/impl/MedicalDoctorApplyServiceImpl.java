package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.bxg.common.util.IDCard;
import com.xczhihui.bxg.common.support.lock.Lock;
import com.xczhihui.medical.common.enums.CommonEnum;
import com.xczhihui.medical.common.service.ICommonService;
import com.xczhihui.medical.department.mapper.MedicalDepartmentMapper;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.doctor.enums.MedicalDoctorApplyEnum;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorApplyDepartmentMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorApplyMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.medical.doctor.model.MedicalDoctorApplyDepartment;
import com.xczhihui.medical.doctor.service.IMedicalDoctorApplyService;
import com.xczhihui.utils.RedisShardLockUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    private ICommonService commonService;
    @Autowired
    private IMedicalDoctorApplyService medicalDoctorApplyService;

    /**
     * 添加医师入驻申请信息
     *
     * @param target 医师入驻申请信息
     */
    @Override
    public void add(MedicalDoctorApply target) {
        medicalDoctorApplyService.addDetail(target);
    }

    @Override
    public void addDetail(MedicalDoctorApply target){
        medicalDoctorApplyService.addDetail4Lock(target.getUserId(),target);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Lock(lockName = "addDoctorApply")
    public void addDetail4Lock(String lockKey,MedicalDoctorApply target) {
        // 参数校验
        this.validate(target);
        // 判断用户是否是认证医师或者认证医馆
        Integer result = commonService.isDoctorOrHospital(target.getUserId());
        // 如果用户是认证医馆或者医馆认证中 不让其认证医师
        if(result.equals(CommonEnum.AUTH_HOSPITAL.getCode()) ||
                result.equals(CommonEnum.HOSPITAL_APPLYING.getCode())){

            // 如果用户是认证医馆 表示用户走错路了
            throw new RuntimeException("您已经认证了医馆，不能再认证医师");
        }

        // 如果用户认证医师中 表示其重新提交认证信息
        if(result.equals(CommonEnum.DOCTOR_APPLYING.getCode()) ||
                result.equals(CommonEnum.AUTH_DOCTOR.getCode())){

            // 如果用户之前提交了申请信息 表示其重新认证 删除之前的认证信息
            medicalDoctorApplyMapper.deleteByUserIdAndStatus(target.getUserId(), MedicalDoctorApplyEnum.WAIT.getCode());

            // 新增新的认证信息
            this.addMedicalDoctorApply(target);

        }

        // 如果用户医师认证成功，医师认证失败，医馆认证失败，或者从没申请
        if(result.equals(CommonEnum.DOCTOR_APPLY_REJECT.getCode()) ||
                result.equals(CommonEnum.NOT_DOCTOR_AND_HOSPITAL.getCode()) ||
                result.equals(CommonEnum.HOSPITAL_APPLY_REJECT.getCode())){
            System.out.println("插入新数据");
            this.addMedicalDoctorApply(target);
        }
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
            if(departmentList != null && departmentList.size()>0){
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

    private void addMedicalDoctorApply(MedicalDoctorApply target) {

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
                    .forEach(department -> this.completeDepartmentField(department, id, now));
            applyDepartmentMapper.batchAdd(target.getDepartments());
        }

        medicalDoctorApplyMapper.insert(target);
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
            if(!IDCard.validator(target.getCardNum())){
                throw new RuntimeException("身份证号不正确");
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
            throw new RuntimeException("请上传执业资格证");
        }
        if(StringUtils.isNotBlank(target.getField()) && target.getField().length() >32){
            throw new RuntimeException("擅长领域内容应保持在32字以内");
        }
    }
}
