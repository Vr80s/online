package com.xczhihui.medical.dao;

import com.xczhihui.bxg.online.common.domain.MedicalDoctorApplyDepartment;
import com.xczhihui.common.dao.HibernateDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医师申请-科室对应关系DAO
 * @author zhuwenbao
 */
@Repository
public class DoctorApplyDepartmentDao extends HibernateDao<MedicalDoctorApplyDepartment> {

    /**
     * 根据doctor_apply_id查询department_id
     * @param applyId department_id
     */
    public List<MedicalDoctorApplyDepartment> findByApplyId(String applyId) {

        String hql = "select id,doctor_apply_id,department_id from " +
                "medical_doctor_apply_department where doctor_apply_id =:applyId";

        Map<String,Object> paramsMap = new HashMap();

        paramsMap.put("applyId",applyId);

        List<MedicalDoctorApplyDepartment> result = this.getNamedParameterJdbcTemplate().query(hql, paramsMap, BeanPropertyRowMapper.newInstance(MedicalDoctorApplyDepartment.class));

        return result;
    }
}
