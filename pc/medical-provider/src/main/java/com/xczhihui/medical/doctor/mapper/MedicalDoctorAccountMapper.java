package com.xczhihui.medical.doctor.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MedicalDoctorAccountMapper extends BaseMapper<MedicalDoctorAccount> {

    /**
     * 根据用户id获取doctorId
     *
     * @param userId 用户id
     */
    MedicalDoctorAccount getByUserId(String userId);

    /**
     * 根据doctorId获取 医师账户信息
     *
     * @param doctorId 医师id
     * @return 医师账号信息
     */
    @Select("select * from medical_doctor_account where doctor_id = #{doctorId}")
    MedicalDoctorAccount getByDoctorId(@Param("doctorId") String doctorId);
}