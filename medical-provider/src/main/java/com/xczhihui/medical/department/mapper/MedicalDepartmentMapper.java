package com.xczhihui.medical.department.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
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
     * 获取科室列表（分页）
     * @param page 翻页对象
     * @return 每页的科室内容
     */
    List<MedicalDepartment> page(Page page);
}