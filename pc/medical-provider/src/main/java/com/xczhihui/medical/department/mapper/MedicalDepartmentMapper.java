package com.xczhihui.medical.department.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.department.model.MedicalDepartment;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MedicalDepartmentMapper extends BaseMapper<MedicalDepartment> {

    /**
     * 获取全部的科室
     * @return 科室列表
     */
    List<MedicalDepartment> getAll();

}