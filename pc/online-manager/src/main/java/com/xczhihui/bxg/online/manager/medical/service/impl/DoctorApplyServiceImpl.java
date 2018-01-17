package com.xczhihui.bxg.online.manager.medical.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorApply;
import com.xczhihui.bxg.online.manager.medical.dao.DoctorApplyDao;
import com.xczhihui.bxg.online.manager.medical.service.DoctorApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 医师入驻申请服务实现层
 * @author zhuwenbao
 */
@Service
public class DoctorApplyServiceImpl implements DoctorApplyService {

    @Autowired
    private DoctorApplyDao doctorApplyDao;

    /**
     * 获取医师入驻申请列表
     * @param searchVo 查询条件
     * @param currentPage 当前页
     * @param pageSize 每页显示的列数
     * @return 医师入驻申请分页列表
     */
    @Override
    public Page<MedicalDoctorApply> list(MedicalDoctorApply searchVo, int currentPage, int pageSize) {

        return doctorApplyDao.list(searchVo, currentPage, pageSize);
    }
}
