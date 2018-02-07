package com.xczhihui.medical.hospital.service.impl;

import java.util.Date;
import java.util.UUID;

import com.xczhihui.medical.common.enums.CommonEnum;
import com.xczhihui.medical.common.service.ICommonService;
import com.xczhihui.utils.RedisShardLockUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.hospital.enums.MedicalHospitalApplyEnum;
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
    @Autowired
    private ICommonService commonService;
    @Autowired
    private RedisShardLockUtils redisShardLockUtils;

    // 锁超时时间
    private final static int TIMEOUT = 3 * 1000;

    private final String LOCK_PREFIX = "hospital_apply_";

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
        if(medicalHospitalApply != null && !(medicalHospitalApply.getUserId().equals(target.getUserId()))){
            throw new RuntimeException("医馆名称已经在认证中");
        }

        // 判断用户是否是认证医师或者认证医馆
        Integer result = commonService.isDoctorOrHospital(target.getUserId());

        // 如果用户是认证医师或者医师认证中 不让其认证医馆
        if(result.equals(CommonEnum.AUTH_DOCTOR.getCode()) ||
                result.equals(CommonEnum.DOCTOR_APPLYING .getCode())){

            throw new RuntimeException("您已经认证了医师，不能再认证医馆");

        }

        // 如果用户已是认证医馆 表示其更新认证信息
        if(result.equals(CommonEnum.AUTH_HOSPITAL.getCode())){

            // 如果用户已是认证医馆 表示其更新认证信息
            this.applyAgain(target);
            return;

        }

        // 如果用户认证医馆中
        if(result.equals(CommonEnum.HOSPITAL_APPLYING.getCode())){

            // 获取用户最后一次申请认证信息
            MedicalHospitalApply hospitalApply = this.getLastOne(target.getUserId());

            Long currentTime = System.currentTimeMillis() + TIMEOUT;

            // 尝试获得锁
            if(redisShardLockUtils.lock(LOCK_PREFIX + hospitalApply.getId(), String.valueOf(currentTime))){

                // 如果用户之前提交了申请信息 表示其重新认证 删除之前的认证信息
                applyMapper.deleteByUserIdAndStatus(target.getUserId(), MedicalHospitalApplyEnum.WAIT.getCode());

                // 新增新的认证信息
                this.addMedicalHospitalApply(target);

                // 解锁
                redisShardLockUtils.unlock(LOCK_PREFIX + hospitalApply.getId(), String.valueOf(currentTime));

                return;
            }else {

                throw new RuntimeException("网络打了小差，请再试一次");

            }
        }

        // 如果用户医馆认证失败，医师认证失败，或者从没申请
        if(result.equals(CommonEnum.HOSPITAL_APPLY_REJECT.getCode()) ||
                result.equals(CommonEnum.NOT_DOCTOR_AND_HOSPITAL.getCode()) ||
                result.equals(CommonEnum.DOCTOR_APPLY_REJECT.getCode())){

            this.addMedicalHospitalApply(target);

        }
    }

    private void addMedicalHospitalApply(MedicalHospitalApply target) {

        // 生成主键
        String id = UUID.randomUUID().toString().replace("-","");
        target.setId(id);

        // 设置初始值 默认不删除 默认未处理
        target.setDeleted(false);
        target.setStatus(MedicalHospitalApplyEnum.WAIT.getCode());
        target.setCreateTime(new Date());

        applyMapper.insert(target);

    }

    private void applyAgain(MedicalHospitalApply target) {

        throw new RuntimeException("您已经认证了医馆，不能再重新认证");

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
