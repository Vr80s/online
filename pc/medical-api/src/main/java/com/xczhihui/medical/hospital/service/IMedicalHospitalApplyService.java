package com.xczhihui.medical.hospital.service;

import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.model.MedicalHospitalAccount;
import com.xczhihui.medical.hospital.model.MedicalHospitalApply;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  医馆入驻申请认证服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-01-15
 */
public interface IMedicalHospitalApplyService {

    /**
     * 添加医馆入驻申请认证信息
     * @param target 医馆入驻申请认证的信息封装
     */
    void add(MedicalHospitalApply target);

    void addDetail(MedicalHospitalApply target);

    void addDetail4Lock(String lockKey, MedicalHospitalApply target);

    /**
     * 根据userId获取医师入驻最后一条申请信息
     * @param userId 用户id
     * @return 医师入驻申请信息
     */
    MedicalHospitalApply getLastOne(String userId);
    
    /**
     * Description：根据用户信息得到医师信息在得到医馆信息
     * @param userId
     * @return
     * @return MedicalHospitalAccount
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    MedicalHospital getMedicalHospitalByMiddleUserId(String userId);
    
    /**
     * Description：根据用户信息直接得到医馆信息
     * @param userId
     * @return
     * @return MedicalHospitalAccount
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    MedicalHospital getMedicalHospitalByUserId(String userId);
    
    /**
     * Description：通过用户信息直接得到医馆信息
     * @param userId
     * @return
     * @return MedicalHospitalAccount
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    MedicalHospitalAccount getByUserId(String userId);
}
