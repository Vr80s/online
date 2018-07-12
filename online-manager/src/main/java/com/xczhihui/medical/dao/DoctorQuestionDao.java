package com.xczhihui.medical.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.online.common.domain.MedicalDoctor;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorQuestion;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.medical.vo.DoctorQuestionVO;

@Repository("doctorQuestionDao")
public class DoctorQuestionDao extends HibernateDao<MedicalDoctorQuestion> {

    public void deleteById(Integer id) {
        String sql = "UPDATE medical_doctor_question SET is_delete = 1 WHERE  id = :id";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        this.getNamedParameterJdbcTemplate().update(sql, params);
    }

    public Page<MedicalDoctorQuestion> selectQuestion(DoctorQuestionVO searchVo, int currentPage, int pageSize) {
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        
        StringBuilder sql = new StringBuilder("SELECT mdq.*  "+
                " FROM " +
                " medical_doctor_question mdq  " +
                " WHERE mdq.deleted = 0");
        
//        if (medicalDoctor.getName() != null) {
//            paramMap.put("name", "%" + medicalDoctor.getName() + "%");
//            sql.append(" and m.name like :name ");
//        }
//        if (medicalDoctor.getType() != null
//                && !"".equals(medicalDoctor.getType())) {
//            paramMap.put("type", medicalDoctor.getType());
//            sql.append(" and m.type = :type ");
//        }
        if (searchVo.getStatus() != null) {
            paramMap.put("status", searchVo.getStatus());
            sql.append(" and mdq.status = :status ");
        }
        
        sql.append(" ORDER BY mdq.status DESC,mdq.create_time DESC ");
        Page<MedicalDoctorQuestion> medicalDoctors = this.findPageBySQL(sql.toString(),
                paramMap, MedicalDoctorQuestion.class, currentPage, pageSize);
        return medicalDoctors;
    }



}
