package com.xczhihui.bxg.online.manager.medical.dao;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalHospitalApply;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 医师入驻申请服务dao
 * @author zhuwenbao
 */

@Repository
public class HospitalApplyDao extends HibernateDao<MedicalHospitalApply> {

    /**
     * 获取医师入驻申请列表
     * @param searchVo 查询条件
     * @param currentPage 当前页
     * @param pageSize 每页显示的列数
     * @return 医师入驻申请分页列表
     */
    public Page<MedicalHospitalApply> list(MedicalHospitalApply searchVo, int currentPage, int pageSize) {

        Map<String,Object> paramMap = new HashMap(3);

        StringBuilder hql = new StringBuilder("select id,company,business_license_no," +
                "business_license_picture,status,name," +
                "license_for_pharmaceutical_trading,license_for_pharmaceutical_trading_picture," +
                "create_time from medical_hospital_apply where deleted = 0 ");

        if(searchVo.getStatus() != null){
            hql.append(" and status = " + searchVo.getStatus());
        }

        if(searchVo.getName() != null){
            hql.append(" and name like '%").append(searchVo.getName()).append("%'");
        }

        hql.append(" order by create_time desc ");

        Page<MedicalHospitalApply> page =  this.findPageBySQL(hql.toString(), paramMap, MedicalHospitalApply.class, currentPage, pageSize);

        return page;
    }
}
