package com.xczhihui.medical.doctor.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorDepartment;
import org.apache.ibatis.annotations.Update;

/**
 * 医师关联科室Mapper 接口
 * @author zhuwenbao
 */
public interface MedicalDoctorDepartmentMapper extends BaseMapper<MedicalDoctorDepartment> {


    /**
     * 根据医师id删除之前的科室信息
     * @param doctorId 医师id
     */
    @Update("update medical_doctor_department set deleted = 1 where doctor_id = #{doctorId} and delete = 0")
    void deleteByDoctorId(String doctorId);
}