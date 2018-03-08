package com.xczhihui.medical.common.service.impl;

import com.xczhihui.medical.common.enums.CommonEnum;
import com.xczhihui.medical.common.service.ICommonService;
import com.xczhihui.medical.doctor.enums.MedicalDoctorApplyEnum;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAccountMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorApplyMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.medical.enums.MedicalExceptionEnum;
import com.xczhihui.medical.exception.MedicalException;
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
                countDownLatch.countDown();
                return result;
            });

            countDownLatch.await(5, TimeUnit.SECONDS);
            Integer authDoctorResult = authDoctorFuture.get();
            Integer authHospitalResult = authHospitalFuture.get();

            if(authDoctorResult != null && authHospitalResult == null){
                return authDoctorResult;
            }
            if(authDoctorResult == null && authHospitalResult != null){
                return authHospitalResult;
            }
            if(authDoctorResult == null && authHospitalResult == null){
                return CommonEnum.NOT_DOCTOR_AND_HOSPITAL.getCode();
            }

            if(authDoctorResult != null && authHospitalResult != null){

                // 如果用户是认证医师 或者认证医师中
                if(authDoctorResult.equals(CommonEnum.AUTH_DOCTOR.getCode()) ||
                        authDoctorResult.equals(CommonEnum.DOCTOR_APPLYING.getCode())){

                    // 同时又认证医馆或者已是医馆
                    if(authHospitalResult.equals(CommonEnum.HOSPITAL_APPLYING.getCode()) ||
                            authHospitalResult.equals(CommonEnum.AUTH_HOSPITAL.getCode())){
                        log.error("---------------userId = {} auth doctor , also auth hospital" , userId);
                        throw new MedicalException(MedicalExceptionEnum.USER_DATA_ERROR);
                    }else{
                        return authDoctorResult;
                    }

                }

                // 如果用户认证医师被拒
                if(authDoctorResult.equals(CommonEnum.DOCTOR_APPLY_REJECT.getCode())){

                    // 同时用户认证医馆也被拒
                    if(authHospitalResult.equals(CommonEnum.HOSPITAL_APPLY_REJECT.getCode())){
                        return CommonEnum.NOT_DOCTOR_AND_HOSPITAL.getCode();
                    }else{
                        return authHospitalResult;
                    }
                }

                // 如果用户是认证医馆 或者认证医馆中
                if(authHospitalResult.equals(CommonEnum.AUTH_HOSPITAL.getCode()) ||
                        authHospitalResult.equals(CommonEnum.HOSPITAL_APPLYING.getCode())){

                    // 同时又认证医师或者已是医师
                    if(authDoctorResult.equals(CommonEnum.DOCTOR_APPLYING.getCode()) ||
                            authDoctorResult.equals(CommonEnum.AUTH_DOCTOR.getCode())){
                        log.warn("-------------userId = {} auth doctor , also auth hospital" , userId);
                        throw new MedicalException(MedicalExceptionEnum.USER_DATA_ERROR);
                    }else{
                        return authHospitalResult;
                    }

                }

                // 如果用户认证医馆被拒
                if(authHospitalResult.equals(CommonEnum.HOSPITAL_APPLY_REJECT.getCode())){

                    // 同时用户认证医师也被拒
                    if(authDoctorResult.equals(CommonEnum.DOCTOR_APPLY_REJECT.getCode())){
                        return CommonEnum.NOT_DOCTOR_AND_HOSPITAL.getCode();
                    }else{
                        return authDoctorResult;
                    }
                }

            }

            return null;

        }catch (MedicalException e) {
            log.error("---------------throw exception in user : {} auth doctor or hospital ,exception is {}", userId, e.getMessage());
            throw new RuntimeException(MedicalExceptionEnum.USER_DATA_ERROR.getMsg());
        } catch (Exception e) {
            throw new RuntimeException("哎呦喂，网络不给力啊，请再试一次");
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
