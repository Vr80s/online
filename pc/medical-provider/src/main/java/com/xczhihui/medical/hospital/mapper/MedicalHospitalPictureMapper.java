package com.xczhihui.medical.hospital.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.hospital.model.MedicalHospitalPicture;
import com.xczhihui.medical.hospital.vo.MedicalHospitalPictureVo;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MedicalHospitalPictureMapper extends BaseMapper<MedicalHospitalPicture> {

    public List<MedicalHospitalPictureVo> getMedicalHospitalPictureByHospitalId(String hospitalId);
}