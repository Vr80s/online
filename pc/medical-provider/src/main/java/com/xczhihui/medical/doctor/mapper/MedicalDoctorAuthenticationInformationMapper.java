package com.xczhihui.medical.doctor.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorAuthenticationInformation;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MedicalDoctorAuthenticationInformationMapper extends BaseMapper<MedicalDoctorAuthenticationInformation> {

    MedicalDoctorAuthenticationInformation selectByDoctorId(String id);
}