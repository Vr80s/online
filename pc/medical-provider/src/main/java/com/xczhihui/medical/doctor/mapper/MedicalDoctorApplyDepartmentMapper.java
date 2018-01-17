package com.xczhihui.medical.doctor.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorApplyDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  医师入驻申请关联科室Mapper 接口
 * </p>
 *
 * @author zhuwenbao
 * @since 2018-01-15
 */
public interface MedicalDoctorApplyDepartmentMapper extends BaseMapper<MedicalDoctorApplyDepartment> {

    /**
     * 批量添加医师入驻申请时关联的科室
     * @param departments 医师入驻申请关联科室列表
     */
    void batchAdd(@Param("departments") List<MedicalDoctorApplyDepartment> departments);

    /**
     * 根据id获取医师入驻申请关联科室列表
     * @param id medical_doctor_apply_department表主键
     * @return 医师入驻申请关联科室列表
     */
    List<MedicalDoctorApplyDepartment> getByDoctorApplyId(String id);
}