package com.xczhihui.bxg.online.manager.medical.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorApply;

/**
 * 医师入驻申请服务层
 * @author zhuwenbao
 */
public interface DoctorApplyService {

    /**
     * 获取医师入驻申请列表
     * @param searchVo 查询条件
     * @param currentPage 当前页
     * @param pageSize 每页显示的列数
     * @return 医师入驻申请分页列表
     */
    Page<MedicalDoctorApply> list(MedicalDoctorApply searchVo, int currentPage, int pageSize);
}
