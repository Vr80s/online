package com.xczhihui.medical.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalHospitalRecruit;
import com.xczhihui.common.dao.HibernateDao;

@Repository
public class HospitalRecruitDao extends HibernateDao<MedicalHospitalRecruit> {

    public Page<MedicalHospitalRecruit> findMedicalHospitalRecruitPage(
            MedicalHospitalRecruit medicalHospitalRecruit, int pageNumber,
            int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder(
                "select * from medical_hospital_recruit where deleted = 0 and hospital_id = '"
                        + medicalHospitalRecruit.getHospitalId() + "'");
        if (medicalHospitalRecruit.getStatusnum() != null) {
            paramMap.put("status", medicalHospitalRecruit.getStatus());
            sql.append(" and status = :status ");
        }
        if (medicalHospitalRecruit.getPosition() != null) {
            paramMap.put("position", "%" + medicalHospitalRecruit.getPosition()
                    + "%");
            sql.append(" and position like :position ");
        }
        sql.append(" order by status desc,sort desc");
        return this.findPageBySQL(sql.toString(), paramMap,
                MedicalHospitalRecruit.class, pageNumber, pageSize);
    }
}
