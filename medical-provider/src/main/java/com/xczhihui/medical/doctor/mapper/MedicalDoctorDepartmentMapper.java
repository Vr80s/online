package com.xczhihui.medical.doctor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorDepartment;

/**
 * 医师关联科室Mapper 接口
 *
 * @author zhuwenbao
 */
public interface MedicalDoctorDepartmentMapper extends BaseMapper<MedicalDoctorDepartment> {


    /**
     * 根据医师id删除之前的科室信息
     *
     * @param doctorId 医师id
     */
    @Update("update medical_doctor_department set deleted = 1 where doctor_id = #{doctorId} and deleted = 0")
    void deleteByDoctorId(String doctorId);

    /**
     * 根据医师id查询与科室的关联关系
     *
     * @param doctorId 医师id
     * @return
     */
    @Select("select * from medical_doctor_department where doctor_id = #{doctorId} and (deleted = 0 OR deleted IS NULL)")
    List<MedicalDoctorDepartment> selectByDoctorId(@Param("doctorId") String doctorId);
}