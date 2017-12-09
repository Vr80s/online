package com.xczhihui.bxg.online.manager.medical.dao;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.MedicalDoctor;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医师管理DAO
 * 
 * @author yxd
 *
 */
@Repository
public class DoctorDao extends HibernateDao<MedicalDoctor>{
	 public Page<MedicalDoctor> findMedicalDoctorPage(MedicalDoctor medicalDoctor, int pageNumber, int pageSize){
		 Map<String, Object> paramMap = new HashMap<String, Object>();
		 StringBuilder sql = new StringBuilder("select * from medical_doctor where deleted = 0 ");
		 if (medicalDoctor.getName() != null) {
			 paramMap.put("name", "%" + medicalDoctor.getName() + "%");
			 sql.append("and name like :name ");
		 }
		 if (medicalDoctor.getType() != null && !"".equals(medicalDoctor.getType())) {
			 paramMap.put("type",  medicalDoctor.getType());
			 sql.append("and type = :type ");
		 }
		 if (medicalDoctor.getStatusnum() != null) {
			 paramMap.put("status", medicalDoctor.getStatus());
			 sql.append("and status = :status ");
		 }

		 sql.append(" order by status desc");

		 Page<MedicalDoctor> medicalDoctors = this.findPageBySQL(sql.toString(), paramMap, MedicalDoctor.class, pageNumber, pageSize);
//		 for (int i = 0; i < medicalDoctors.getItems().size(); i++) {
//			 MedicalDoctor mh =  medicalDoctors.getItems().get(i);
//			 List<MedicalDoctorPicture> c = this.findEntitiesByProperty(MedicalDoctorPicture.class, "DoctorId", mh.getId());
//			 if(c.size()>=5){
//				 mh.setHasPicture(true);
//			 }else{
//				 mh.setHasPicture(false);
//			 }
//		 }
		 return medicalDoctors;
//		 }
	 }

	public void deleteById(String id) {
		String sql="update medical_doctor set is_delete = 1 where  id = :id";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", id);
		this.getNamedParameterJdbcTemplate().update(sql, params);
	}

	public MedicalDoctor getMedicalDoctorById(Integer MedicalDoctorId){
		DetachedCriteria dc = DetachedCriteria.forClass(MedicalDoctor.class);
		dc.add(Restrictions.eq("id", MedicalDoctorId));
		MedicalDoctor MedicalDoctor = this.findEntity(dc);
		return MedicalDoctor;
	}
}
