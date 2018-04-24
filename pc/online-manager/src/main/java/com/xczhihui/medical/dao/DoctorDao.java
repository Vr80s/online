package com.xczhihui.medical.dao;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalDoctor;
import com.xczhihui.common.dao.HibernateDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 医师管理DAO
 * 
 * @author yxd
 *
 */
@Repository
public class DoctorDao extends HibernateDao<MedicalDoctor> {
	public Page<MedicalDoctor> findMedicalDoctorPage(
			MedicalDoctor medicalDoctor, int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder("SELECT \n" + "  m.*,\n"
				+ "  mh.`name` hospital,\n"
				+ "  GROUP_CONCAT(md.`name`) AS department \n" + "FROM\n"
				+ "  medical_doctor m \n"
				+ "  LEFT JOIN `medical_hospital_doctor` mhd\n"
				+ "  ON mhd.`doctor_id`=m.`id`\n"
				+ "  LEFT JOIN `medical_hospital` mh\n"
				+ "  ON mhd.`hospital_id`=mh.`id`\n"
				+ "  LEFT JOIN `medical_doctor_department` mdd \n"
				+ "    ON m.id = mdd.`doctor_id` \n"
				+ "  LEFT JOIN `medical_department` md \n"
				+ "    ON mdd.`department_id` = md.id \n"
				+ "WHERE m.deleted = 0  ");
		if (medicalDoctor.getName() != null) {
			paramMap.put("name", "%" + medicalDoctor.getName() + "%");
			sql.append("and m.name like :name ");
		}
		if (medicalDoctor.getType() != null
				&& !"".equals(medicalDoctor.getType())) {
			paramMap.put("type", medicalDoctor.getType());
			sql.append("and m.type = :type ");
		}
		if (medicalDoctor.getStatusnum() != null) {
			paramMap.put("status", medicalDoctor.getStatus());
			sql.append("and m.status = :status ");
		}

		sql.append(" group by m.id \n" + "ORDER BY m.status DESC,\n"
				+ "  m.create_time DESC ");

		Page<MedicalDoctor> medicalDoctors = this.findPageBySQL(sql.toString(),
				paramMap, MedicalDoctor.class, pageNumber, pageSize);
		// for (int i = 0; i < medicalDoctors.getItems().size(); i++) {
		// MedicalDoctor mh = medicalDoctors.getItems().get(i);
		// List<MedicalDoctorPicture> c =
		// this.findEntitiesByProperty(MedicalDoctorPicture.class, "DoctorId",
		// mh.getId());
		// if(c.size()>=5){
		// mh.setHasPicture(true);
		// }else{
		// mh.setHasPicture(false);
		// }
		// }
		return medicalDoctors;
		// }
	}

	public void deleteById(String id) {
		String sql = "update medical_doctor set is_delete = 1 where  id = :id";
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
