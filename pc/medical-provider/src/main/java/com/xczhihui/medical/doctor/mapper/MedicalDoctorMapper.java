package com.xczhihui.medical.doctor.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVo;
import com.xczhihui.medical.doctor.vo.MedicalWritingsVo;
import com.xczhihui.medical.field.vo.MedicalFieldVo;
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

    List<MedicalDoctorVo> selectDoctorList(@Param("page") Page<MedicalDoctorVo> page, @Param("type") Integer type, @Param("hospitalId") String hospitalId, @Param("name") String name, @Param("field") String field);

    MedicalDoctorVo selectDoctorById(String id);

    List<MedicalFieldVo> getHotField();

    List<MedicalDoctorVo> selectRecDoctor();

    List<MedicalFieldVo> selectMedicalFieldsByDoctorId(String doctorId);

    List<MedicalWritingsVo> getWritingsByDoctorId(String doctorId);

    MedicalWritingsVo getWritingsDetailsById(String writingsId);

    List<MedicalWritingsVo> getRecentlyWritings();

    List<MedicalDoctorVo> getHotSpecialColumnAuthor(String specialColumn);

    List<MedicalWritingsVo> getWritingsByPage(Page<MedicalWritingsVo> page);
}