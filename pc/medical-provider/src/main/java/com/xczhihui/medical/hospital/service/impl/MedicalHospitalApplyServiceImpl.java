package com.xczhihui.medical.hospital.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.doctor.enums.MedicalHospitalApplyEnum;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalAccountMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalApplyMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalMapper;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.model.MedicalHospitalAccount;
import com.xczhihui.medical.hospital.model.MedicalHospitalApply;
import com.xczhihui.medical.hospital.service.IMedicalHospitalApplyService;

/**
 * <p>
 *  医馆入驻申请认证服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2018-01-15
 */
@Service
public class MedicalHospitalApplyServiceImpl extends ServiceImpl<MedicalHospitalApplyMapper, MedicalHospitalApply> implements IMedicalHospitalApplyService {

    @Autowired
    private MedicalHospitalApplyMapper applyMapper;
    @Autowired
    private MedicalHospitalAccountMapper hospitalAccountMapper;
    @Autowired
    private MedicalHospitalMapper hospitalMapper;

    /**
     * 添加医馆入驻申请认证信息
     * @param target 医馆入驻申请认证的信息封装
     */
    @Override
    public void add(MedicalHospitalApply target) {

        // 参数校验
        this.validate(target);

        // 判断医馆名字是否已被占用
        MedicalHospital hospital = hospitalMapper.findByName(target.getName());
        if(hospital != null){
            throw new RuntimeException("医馆名称已经被占用");
        }

        MedicalHospitalApply medicalHospitalApply = applyMapper.findByName(target.getName());
        if(medicalHospitalApply != null){
            throw new RuntimeException("医馆名称已经在认证中");
        }

        // 判断用户是否已经拥有已认证的医馆
        MedicalHospitalAccount hospitalAccount = hospitalAccountMapper.getByUserId(target.getUserId());
        if(hospitalAccount != null && StringUtils.isNotBlank(hospitalAccount.getDoctorId())){
            if(hospitalMapper.getAuthenticationById(hospitalAccount.getDoctorId())){
                throw new RuntimeException("您已经拥有已认证的医馆，不能再申请认证");
            }
        }

        // 获取用户最后一次申请认证信息
        MedicalHospitalApply lastApply = this.getLastOne(target.getUserId());

        if(lastApply != null){
            // 如果该医师已申请 但状态为：未处理 则直接返回
            if(lastApply.getStatus().equals(MedicalHospitalApplyEnum.WAIT.getCode()) ){
                throw new RuntimeException("您已提交申请，请等待管理员审核");
            }else {
                // 如果为其他状态 则表示需要重新认证
                // 删除之前的申请
                applyMapper.delete(target.getUserId());
            }
        }

        // 生成主键
        String id = UUID.randomUUID().toString().replace("-","");
        target.setId(id);

        // 设置初始值 默认不删除 默认未处理
        target.setDeleted(false);
        target.setStatus(MedicalHospitalApplyEnum.WAIT.getCode());
        target.setCreateTime(new Date());

        applyMapper.insert(target);
    }

    /**
     * 获取用户最后一次申请认证信息
     * @param userId 用户id
     * @return 最后一次申请认证信息
     */
    @Override
    public MedicalHospitalApply getLastOne(String userId) {

        MedicalHospitalApply lastApply = applyMapper.getLastOne(userId);

        return lastApply;
    }


    /**
     * 参数校验
     * @param target 医馆入驻申请信息
     */
    private void validate(MedicalHospitalApply target) {

        if(StringUtils.isBlank(target.getName())){
            throw new RuntimeException("医馆名称不能为空");
        }else{
            if(target.getName().length() > 32){
                throw new RuntimeException("医馆名称应保持在32字以内");
            }
        }

        if(StringUtils.isBlank(target.getCompany())){
            throw new RuntimeException("医馆所属公司不能为空");
        }else{
            if(target.getCompany().length() > 32){
                throw new RuntimeException("医馆所属公司名应保持在32字以内");
            }
        }

        if(StringUtils.isBlank(target.getBusinessLicenseNo())){
            throw new RuntimeException("营业执照号码不能为空");
        }else{
            if(target.getBusinessLicenseNo().length() > 32){
                throw new RuntimeException("营业执照号码应保持在32字以内");
            }
        }

        if(StringUtils.isBlank(target.getBusinessLicensePicture())){
            throw new RuntimeException("请上传营业执照");
        }

        if(StringUtils.isBlank(target.getLicenseForPharmaceuticalTrading())){
            throw new RuntimeException("药品经营许可证号码不能为空");
        }else{
            if(target.getLicenseForPharmaceuticalTrading().length() > 32){
                throw new RuntimeException("药品经营许可证号码应保持在32字以内");
            }
        }

        if(StringUtils.isBlank(target.getLicenseForPharmaceuticalTradingPicture())){
            throw new RuntimeException("请上传药品经营许可证照片");
        }

    }

	@Override
	public MedicalHospital getMedicalHospitalByMiddleUserId(String userId) {
		// TODO Auto-generated method stub
		return hospitalAccountMapper.getMedicalHospitalByMiddleUserId(userId);
	}
	
	@Override
	public MedicalHospital getMedicalHospitalByUserId(String userId) {
		// TODO Auto-generated method stub
		return hospitalAccountMapper.getMedicalHospitalByUserId(userId);
	}

	@Override
	public MedicalHospitalAccount getByUserId(String userId) {
		// TODO Auto-generated method stub
		return hospitalAccountMapper.getByUserId(userId);
	}
}
