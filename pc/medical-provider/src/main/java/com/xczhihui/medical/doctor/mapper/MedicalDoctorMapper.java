package com.xczhihui.medical.doctor.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.field.model.MedicalField;
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
public interface MedicalDoctorMapper extends BaseMapper<MedicalDoctor> {

    List<MedicalDoctor> selectDoctorList(@Param("page") Page<MedicalDoctor> page, @Param("type") Integer type, @Param("hospitalId") String hospitalId, @Param("name") String name, @Param("field") String field);

    MedicalDoctor selectDoctorById(String id);

    List<MedicalField> getHotField();

    List<MedicalDoctor> selectRecDoctor();

    List<MedicalField> selectMedicalFieldsByDoctorId(String doctorId);
}