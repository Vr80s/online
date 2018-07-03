package com.xczhihui.medical.service;

import com.xczhihui.bxg.online.common.domain.MedicalDoctorApply;
import com.xczhihui.common.util.bean.Page;

/**
 * 医师入驻申请服务层
 *
 * @author zhuwenbao
 */
public interface DoctorApplyService {

    /**
     * 获取医师入驻申请列表
     *
     * @param searchVo    查询条件
     * @param currentPage 当前页
     * @param pageSize    每页显示的列数
     * @return 医师入驻申请分页列表
     */
    Page<MedicalDoctorApply> list(MedicalDoctorApply searchVo, int currentPage,
                                  int pageSize);

    /**
     * 更新医师入驻申请状态
     *
     * @param doctorApply 更新的数据封装类
     */
    String updateStatus(MedicalDoctorApply doctorApply);

    /**
     * 根据id查询医师入驻申请状态
     *
     * @param id
     */
    MedicalDoctorApply findById(String id);

}