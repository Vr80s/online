package com.xczhihui.medical.hospital.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
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
}