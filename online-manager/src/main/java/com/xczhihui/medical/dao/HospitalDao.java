package com.xczhihui.medical.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.online.common.domain.MedicalHospital;
import com.xczhihui.bxg.online.common.domain.MedicalHospitalPicture;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.common.util.bean.Page;

/**
 * 云课堂课程管理DAO
 *
 * @author yxd
 */
@Repository
public class HospitalDao extends HibernateDao<MedicalHospital> {

    public Page<MedicalHospital> findMedicalHospitalPage( MedicalHospital medicalHospital, int pageNumber, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder(
                "select mh.*,ou.`login_name` loginName FROM medical_hospital mh" +
                        " LEFT JOIN `medical_hospital_account` mha ON mh.id=mha.`doctor_id` AND mha.`deleted`=0" +
                        " LEFT JOIN `oe_user` ou ON ou.`id`=mha.`account_id` where mh.deleted = 0 ");
        if (medicalHospital.getName() != null) {
            paramMap.put("name", "%" + medicalHospital.getName() + "%");
            sql.append("and mh.name like :name ");
        }
        if (medicalHospital.getStatusnum() != null) {
            paramMap.put("status", medicalHospital.getStatus());
            sql.append("and mh.status = :status ");
        }
        if (medicalHospital.getAuthenticationNum() != null) {
            paramMap.put("authentication", medicalHospital.isAuthentication());
            sql.append("and mh.authentication = :authentication ");
        }
        if (medicalHospital.getScore() != null) {
            if (medicalHospital.getScore() == 0) {
                sql.append("and mh.score is null ");
            } else {
                paramMap.put("score", medicalHospital.getScore());
                sql.append("and mh.score = :score ");
            }
        }
        sql.append(" order by mh.authentication desc ,mh.recommend_sort desc,mh.score desc,mh.enable_time desc");

        Page<MedicalHospital> medicalHospitals = this.findPageBySQL(
                sql.toString(), paramMap, MedicalHospital.class, pageNumber,
                pageSize);
        for (int i = 0; i < medicalHospitals.getItems().size(); i++) {
            MedicalHospital mh = medicalHospitals.getItems().get(i);
            List<MedicalHospitalPicture> c = this.findEntitiesByProperty(
                    MedicalHospitalPicture.class, "hospitalId", mh.getId());
            if (c.size() > 0) {
                mh.setHasPicture(true);
            } else {
                mh.setHasPicture(false);
            }
        }
        return medicalHospitals;
    }

    public void deleteById(String id) {
        String sql = "update medical_hospital set is_delete = 1 where  id = :id";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        this.getNamedParameterJdbcTemplate().update(sql, params);
    }

    public MedicalHospital getMedicalHospitalById(Integer MedicalHospitalId) {
        DetachedCriteria dc = DetachedCriteria.forClass(MedicalHospital.class);
        dc.add(Restrictions.eq("id", MedicalHospitalId));
        MedicalHospital MedicalHospital = this.findEntity(dc);
        return MedicalHospital;
    }

    public Page<MedicalHospital> findRecMedicalHospitalPage(
            MedicalHospital medicalHospital, int pageNumber, int pageSize) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder(
                "select * from medical_hospital where deleted = 0 and recommend = 1");
        if (medicalHospital.getName() != null) {
            paramMap.put("name", "%" + medicalHospital.getName() + "%");
            sql.append("and name like :name ");
        }
        if (medicalHospital.getStatusnum() != null) {
            paramMap.put("status", medicalHospital.getStatus());
            sql.append("and status = :status ");
        }

        sql.append(" order by recommend_sort desc");

        Page<MedicalHospital> medicalHospitals = this.findPageBySQL(
                sql.toString(), paramMap, MedicalHospital.class, pageNumber,
                pageSize);
        for (int i = 0; i < medicalHospitals.getItems().size(); i++) {
            MedicalHospital mh = medicalHospitals.getItems().get(i);
            List<MedicalHospitalPicture> c = this.findEntitiesByProperty(
                    MedicalHospitalPicture.class, "hospitalId", mh.getId());
            if (c.size() <= 0) {
                mh.setHasPicture(false);
            } else {
                mh.setHasPicture(true);
            }
        }
        return medicalHospitals;
    }

}
