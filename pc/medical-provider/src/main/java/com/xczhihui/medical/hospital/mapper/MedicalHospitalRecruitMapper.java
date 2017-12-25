package com.xczhihui.medical.hospital.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.hospital.vo.MedicalHospitalRecruitVO;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-20
 */
public interface MedicalHospitalRecruitMapper extends BaseMapper<MedicalHospitalRecruitVO> {

    List<MedicalHospitalRecruitVO> selectRecHospitalRecruit();

    List<MedicalHospitalRecruitVO> selectHospitalRecruitByHospitalId(String hospitalId);
}