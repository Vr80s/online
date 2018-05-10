package com.xczhihui.medical.hospital.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.hospital.model.MedicalHospitalDoctor;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MedicalHospitalDoctorMapper extends BaseMapper<MedicalHospitalDoctor> {


    @Select("select * from medical_hospital_doctor where doctor_id = #{doctorId} and deleted = '0'")
    List<MedicalHospitalDoctor> selectByDoctorId(@Param("doctorId") String doctorId);
}