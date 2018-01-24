package com.xczhihui.medical.hospital.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.model.MedicalHospitalAccount;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MedicalHospitalAccountMapper extends BaseMapper<MedicalHospitalAccount> {

    /**
     * 通过用户id获取对应的医馆id
     * @param userId
     */
    MedicalHospitalAccount getByUserId(String userId);
    
    /**
     * 通过用户id查找  -- >得到医师id,通过医师得到医馆信息 -->查找医馆信息
     * Description：
     * @param userId
     * @return
     * @return MedicalHospitalAccount
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    MedicalHospital getMedicalHospitalByMiddleUserId(String userId);
    
    /**
     * 通过用户id查找医馆信息
     * Description：
     * @param userId
     * @return
     * @return MedicalHospitalAccount
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    MedicalHospital getMedicalHospitalByUserId(String userId);
    
}