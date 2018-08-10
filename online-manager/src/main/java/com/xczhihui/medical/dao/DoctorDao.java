package com.xczhihui.medical.dao;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.online.common.domain.MedicalDoctor;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.common.util.bean.Page;

/**
 * 医师管理DAO
 *
 * @author yxd
 */
@Repository
public class DoctorDao extends HibernateDao<MedicalDoctor> {
    public Page<MedicalDoctor> findMedicalDoctorPage(
            MedicalDoctor medicalDoctor, int pageNumber, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder("SELECT \n" +
                "  m.*,\n" +
                "  mh.`name` hospital,\n" +
                "  dpn.name AS department, \n" +
                "  ou.`login_name`\n" +
                "FROM\n" +
                "  medical_doctor m \n" +
                "  LEFT JOIN `medical_doctor_account` mda ON m.`id` = mda.`doctor_id`\n" +
                "  LEFT JOIN `oe_user` ou ON ou.id = mda.`account_id`\n" +
                "  LEFT JOIN (SELECT * FROM `medical_hospital_doctor` WHERE deleted = '0') mhd ON mhd.`doctor_id` = m.`id` \n" +
                "  LEFT JOIN `medical_hospital` mh ON mhd.`hospital_id` = mh.`id` \n" +
                "  LEFT JOIN (SELECT GROUP_CONCAT(md.`name`) AS NAME, mdd.`doctor_id` FROM\n" +
                "      `medical_doctor_department` mdd LEFT JOIN `medical_department` md ON mdd.`department_id` = md.id \n" +
                "    WHERE (mdd.deleted IS NULL OR mdd.deleted = FALSE) GROUP BY mdd.`doctor_id`) dpn \n" +
                "  ON m.`id` = dpn.doctor_id \n" +
                "WHERE m.deleted = 0 ");
        if (medicalDoctor.getName() != null) {
            paramMap.put("name", "%" + medicalDoctor.getName() + "%");
            sql.append(" and m.name like :name ");
        }
        if (medicalDoctor.getType() != null
                && !"".equals(medicalDoctor.getType())) {
            paramMap.put("type", medicalDoctor.getType());
            sql.append(" and m.type = :type ");
        }
        if (medicalDoctor.getStatusnum() != null) {
            paramMap.put("status", medicalDoctor.getStatus());
            sql.append(" and m.status = :status ");
        }

        sql.append(" ORDER BY m.status DESC,m.recommend_sort desc, \n"
                + "  m.create_time DESC ");

        Page<MedicalDoctor> medicalDoctors = this.findPageBySQL(sql.toString(),
                paramMap, MedicalDoctor.class, pageNumber, pageSize);
        return medicalDoctors;
    }

    public void deleteById(String id) {
        String sql = "UPDATE medical_doctor SET is_delete = 1 WHERE  id = :id";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        this.getNamedParameterJdbcTemplate().update(sql, params);
    }

    public MedicalDoctor getMedicalDoctorById(Integer MedicalDoctorId) {
        DetachedCriteria dc = DetachedCriteria.forClass(MedicalDoctor.class);
        dc.add(Restrictions.eq("id", MedicalDoctorId));
        MedicalDoctor MedicalDoctor = this.findEntity(dc);
        return MedicalDoctor;
    }

    public Page<MedicalDoctor> findRecMedicalDoctorPage(
            MedicalDoctor medicalDoctor, int currentPage, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder(
                "select * from medical_doctor where deleted = 0 and recommend = 1");
        if (medicalDoctor.getName() != null) {
            paramMap.put("name", "%" + medicalDoctor.getName() + "%");
            sql.append("and name like :name ");
        }
        if (medicalDoctor.getStatusnum() != null) {
            paramMap.put("status", medicalDoctor.getStatus());
            sql.append("and status = :status ");
        }

        sql.append(" order by recommend_sort desc");

        Page<MedicalDoctor> medicalHospitals = this.findPageBySQL(
                sql.toString(), paramMap, MedicalDoctor.class, currentPage,
                pageSize);
        return medicalHospitals;
    }
}
