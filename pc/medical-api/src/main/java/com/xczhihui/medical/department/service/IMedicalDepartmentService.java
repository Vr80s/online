package com.xczhihui.medical.department.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.department.model.MedicalDepartment;


/**
 * 科室业务层
 * @author zhuwenbao
 * @since 2017-12-09
 */
public interface IMedicalDepartmentService {

    /**
     * 获取科室列表（分页）
     * @param page 翻页对象
     * @return 每页的科室内容
     */
    Page<MedicalDepartment> page(Page page);


}
