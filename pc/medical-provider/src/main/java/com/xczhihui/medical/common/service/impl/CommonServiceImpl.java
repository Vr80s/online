package com.xczhihui.medical.common.service.impl;

import com.xczhihui.medical.common.enums.CommonEnum;
import com.xczhihui.medical.common.service.ICommonService;
import com.xczhihui.medical.doctor.enums.MedicalDoctorApplyEnum;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAccountMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorApplyMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.medical.hospital.enums.MedicalHospitalApplyEnum;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalAccountMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalApplyMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalMapper;
import com.xczhihui.medical.hospital.model.MedicalHospitalAccount;
import com.xczhihui.medical.hospital.model.MedicalHospitalApply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * 医师医馆公共服务实现累
 */
@Service
public class CommonServiceImpl implements ICommonService {

    @Autowired
    private MedicalDoctorAccountMapper doctorAccountMapper;
    @Autowired
    private MedicalHospitalAccountMapper hospitalAccountMapper;
    @Autowired
    private MedicalHospitalMapper hospitalMapper;
    @Autowired
    private MedicalDoctorApplyMapper doctorApplyMapper;
    @Autowired
    private MedicalHospitalApplyMapper hospitalApplyMapper;
    @Autowired
    private ThreadPoolTaskExecutor commonThreadPoolTaskExecutor;

    private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);

    /**
     * 根据用户id 判断用户是否是认证医师
     * @param userId 用户id
     */
    @Override
    public boolean isDoctor(String userId) {

        MedicalDoctorAccount medicalDoctorAccount = doctorAccountMapper.getByUserId(userId);

        if(medicalDoctorAccount != null){

            return true;

        }else{

            return false;

        }
    }

    /**
     * 根据用户id 判断用户拥有认证医馆
     * @param userId 用户id
     */
    @Override
    public boolean isHospital(String userId) {

        MedicalHospitalAccount hospitalAccount = hospitalAccountMapper.getByUserId(userId);

        if(hospitalAccount != null){

            if(hospitalMapper.getAuthenticationById(hospitalAccount.getDoctorId())){

                return true;
            }

        }

        return false;
    }

    /**
     * 根据用户id 判断用户是医师还是医馆
     * @param userId 用户id
     * @return 1：认证医师 2：认证医馆 3：医师认证中 4：医馆认证中 5：医师认证被拒 6：医馆认证被拒 7：即没有认证医师也没有认证医馆
     */
    @Override
    public Integer isDoctorOrHospital(String userId) {

        CountDownLatch countDownLatch = new CountDownLatch(2);

        try {

            // 判断是否是认证医师
            Future<Integer> authDoctorFuture = commonThreadPoolTaskExecutor.submit(() -> {
                log.info("-----------------auth doctor start");
                Integer result = null;
                if(this.isDoctor(userId)){
                    result = CommonEnum.AUTH_DOCTOR.getCode();
                }else {
                    // 如果不是认证医师，判断是否正在认证医师
                    MedicalDoctorApply doctorApply = doctorApplyMapper.getLastOne(userId);
                    if(doctorApply != null){
                        Integer status = doctorApply.getStatus();
                        if(status.equals(MedicalDoctorApplyEnum.REJECT.getCode())){
                            result = CommonEnum.DOCTOR_APPLY_REJECT.getCode();
                        }else if(status.equals(MedicalDoctorApplyEnum.WAIT.getCode())){
                            result = CommonEnum.DOCTOR_APPLYING.getCode();
                        }
                    }
                }
                log.info("-----------------auth doctor stop");
                countDownLatch.countDown();
                return result;
            });

            // 判断是否是已认证医馆
            Future<Integer> authHospitalFuture = commonThreadPoolTaskExecutor.submit(() -> {
                log.info("-----------------auth hospital start");
                Integer result = null;
                if(this.isHospital(userId)){
                    result = CommonEnum.AUTH_HOSPITAL.getCode();
                }else {
                    // 如果不是已认证医馆，判断是否正在认证医馆
                    MedicalHospitalApply hospitalApply = hospitalApplyMapper.getLastOne(userId);
                    if(hospitalApply != null){
                        Integer status = hospitalApply.getStatus();
                        if(status.equals(MedicalHospitalApplyEnum.REJECT.getCode())){
                            result = CommonEnum.HOSPITAL_APPLY_REJECT.getCode();
                        }else if(status.equals(MedicalHospitalApplyEnum.WAIT.getCode())){
                            result = CommonEnum.HOSPITAL_APPLYING.getCode();
                        }
                    }
                }
                log.info("-----------------auth hospital stop");
                countDownLatch.countDown();
                return result;
            });

            countDownLatch.await(5, TimeUnit.SECONDS);
            Integer authDoctorResult = authDoctorFuture.get();
            Integer authHospitalResult = authHospitalFuture.get();

            log.info("-----------------auth doctor result = " + authDoctorResult);
            log.info("-----------------auth hospital result = " + authHospitalResult);

            if(authDoctorResult != null && authHospitalResult == null){
                return authDoctorResult;
            }

            if(authDoctorResult == null && authHospitalResult != null){
                return authHospitalResult;
            }

            // 当两者都为空时
            if(authDoctorResult == null && authHospitalResult == null){
                return CommonEnum.NOT_DOCTOR_AND_HOSPITAL.getCode();
            }

            // 当查询到该用户即是认证医师，又认证医馆
            if(authDoctorResult != null && authHospitalResult != null){
                log.warn("userId = {} is auth doctor , also auth hospital" , userId);
                throw new RuntimeException("账号异常，请联系管理员");
            }

            return null;

        } catch (Exception e) {
            log.error("---------------throw exception in auth user is doctor or hospital ,exception is " + e.getMessage());
            throw new RuntimeException("网络开了个小差，请再试一次");
        }finally {
            // 防止死锁
            Long count = countDownLatch.getCount();
            if(count != 0){
                if(count == 1){
                    countDownLatch.countDown();
                }
                if(count == 2){
                    countDownLatch.countDown();
                    countDownLatch.countDown();
                }
            }
        }
    }
}
