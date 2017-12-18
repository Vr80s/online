package com.xczhihui.medical.hospital.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.field.model.MedicalField;
import com.xczhihui.medical.hospital.model.MedicalHospital;
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

    List<String> selectHospitalIdList(@Param("page") Page<MedicalHospital> page, @Param("name") String name,@Param("field") String field);

    List<MedicalHospital> selectHospitalAndPictureList(List<String> mhIds);

    MedicalHospital selectHospitalById(String id);

    List<MedicalHospital> selectRecHospital();

    List<MedicalField> getHotField();

    List<MedicalField> selectMedicalFieldsByHospitalId(String hospitalId);
}