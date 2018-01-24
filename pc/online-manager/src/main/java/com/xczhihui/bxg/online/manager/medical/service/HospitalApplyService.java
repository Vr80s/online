package com.xczhihui.bxg.online.manager.medical.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalHospitalApply;

/**
 * 医馆入驻申请服务层
 * @author zhuwenbao
 */
public interface HospitalApplyService {

    /**
     * 获取医师入驻申请列表
     * @param searchVo 查询条件
     * @param currentPage 当前页
     * @param pageSize 每页显示的列数
     * @return 医师入驻申请分页列表
     */
    Page<MedicalHospitalApply> list(MedicalHospitalApply searchVo, int currentPage, int pageSize);

    /**
     * 更新医师入驻申请状态
     * @param doctorApply 更新的数据封装类
     */
    void updateStatus(MedicalHospitalApply doctorApply);

    /**
     * 根据id查询详情
     * @param applyId
     */
    MedicalHospitalApply findById(String applyId);
}
