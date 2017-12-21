package com.xczhihui.medical.hospital.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.field.vo.MedicalFieldVo;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MedicalHospitalMapper extends BaseMapper<MedicalHospital> {

    List<String> selectHospitalIdList(@Param("page") Page<MedicalHospitalVo> page, @Param("name") String name, @Param("field") String field);

    List<MedicalHospitalVo> selectHospitalAndPictureList(List<String> mhIds);

    MedicalHospitalVo selectHospitalById(String id);

    List<MedicalHospitalVo> selectRecHospital();

    List<MedicalFieldVo> getHotField();

    List<MedicalFieldVo> selectMedicalFieldsByHospitalId(String hospitalId);
}