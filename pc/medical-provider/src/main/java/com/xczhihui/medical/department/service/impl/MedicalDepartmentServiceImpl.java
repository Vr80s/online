package com.xczhihui.medical.department.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.department.mapper.MedicalDepartmentMapper;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.department.service.IMedicalDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class MedicalDepartmentServiceImpl extends ServiceImpl<MedicalDepartmentMapper, MedicalDepartment> implements IMedicalDepartmentService {

    @Autowired
    private MedicalDepartmentMapper medicalDepartmentMapper;

    /**
     * 获取科室列表（分页）
     * @param page 翻页对象
     * @return 每页的科室内容
     */
    @Override
    public Page<MedicalDepartment> page(Page page) {

        return page.setRecords(medicalDepartmentMapper.page(page));
    }
}
