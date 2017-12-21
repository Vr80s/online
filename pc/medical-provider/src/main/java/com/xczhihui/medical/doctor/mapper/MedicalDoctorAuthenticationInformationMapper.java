package com.xczhihui.medical.doctor.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorAuthenticationInformation;
import com.xczhihui.medical.doctor.vo.MedicalDoctorAuthenticationInformationVo;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MedicalDoctorAuthenticationInformationMapper extends BaseMapper<MedicalDoctorAuthenticationInformation> {

    MedicalDoctorAuthenticationInformationVo selectByDoctorId(String id);
}