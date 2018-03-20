package com.xczhihui.medical.doctor.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MedicalDoctorAccountMapper extends BaseMapper<MedicalDoctorAccount> {

    /**
     * 根据用户id获取doctorId
     * @param userId 用户id
     */
    MedicalDoctorAccount getByUserId(String userId);
}