package com.xczhihui.bxg.online.manager.medical.dao;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorApply;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 医师入驻申请服务dao
 * @author zhuwenbao
 */

@Repository("doctorApplyDao")
public class DoctorApplyDao extends HibernateDao<MedicalDoctorApply> {

    /**
     * 获取医师入驻申请列表
     * @param searchVo 查询条件
     * @param currentPage 当前页
     * @param pageSize 每页显示的列数
     * @return 医师入驻申请分页列表
     */
    public Page<MedicalDoctorApply> list(MedicalDoctorApply searchVo, int currentPage, int pageSize) {

        Map<String,Object> paramMap = new HashMap(3);
        StringBuilder hql = new StringBuilder("select id,name,title,description from medical_doctor_apply where deleted = 0 ");
        hql.append(" order by create_time desc ");

        return this.findPageByHQL(hql.toString(), paramMap , currentPage, pageSize);

    }
}
