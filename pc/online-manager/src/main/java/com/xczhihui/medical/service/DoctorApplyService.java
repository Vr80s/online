package com.xczhihui.medical.service;

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

    /**
     * 更新医师入驻申请状态
     * @param doctorApply 更新的数据封装类
     */
    void updateStatus(MedicalDoctorApply doctorApply);

    /**
     * 根据id查询医师入驻申请状态
     * @param id
     */
    MedicalDoctorApply findById(String id);

    /**
     * 兼容之前主播没有进行医师认证所缺少的数据
     */
    void afterApply(String userId);

    void afterApplyAll();
}
