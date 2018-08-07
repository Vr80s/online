package com.xczhihui.course.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.DoctorType;
import com.xczhihui.bxg.online.common.domain.MedicalDoctor;
import com.xczhihui.bxg.online.common.domain.Project;
import com.xczhihui.bxg.online.common.domain.Question;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.common.util.bean.Page;

@Repository("doctorTypeDao")
public class DoctorTypeDao extends HibernateDao<DoctorType> {

    public Page<DoctorType> findDoctorTypePage(DoctorType doctorType, int pageNumber,
                                         int pageSize) {
    	
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder(
                "select *,"
                + "( select count(*) from medical_doctor as md "
                + "   where md.deleted = 0 and md.type = dt.id ) as doctorNumber "
                + " from doctor_type as dt where dt.is_delete=0 ");
        sql.append(" order by  dt.status desc,dt.sort asc ");
        
        return this.findPageBySQL(sql.toString(), paramMap, DoctorType.class,
                pageNumber, pageSize);
    }

    public int getMaxSort() {
        String sql = " select ifnull(max(sort),0) as maxSort from doctor_type where  is_delete = 0  ";
        Map<String, Object> result = this.getNamedParameterJdbcTemplate()
                .queryForMap(sql, new HashMap<String, Object>());
        return Integer.parseInt(result.get("maxSort") != null ? String.valueOf(result.get("maxSort")) : "0");
    }

    public DoctorType findById(String parseInt) {
        StringBuilder sql = new StringBuilder(
                "select * from doctor_type where id=:id");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", parseInt);
        List<DoctorType> menus = this.getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(DoctorType.class));
        if (menus.size() > 0) {
            return menus.get(0);
        }
        return null;
    }

    public String deleteById(String id) {
    	String s = "";
        String sql2 = "select * from medical_doctor where deleted = 0 and type = :id";
        Map<String, Object> params3 = new HashMap<String, Object>();
        params3.put("id", id);
        List<MedicalDoctor> query2 = this.getNamedParameterJdbcTemplate().query(sql2,
                params3, BeanPropertyRowMapper.newInstance(MedicalDoctor.class));

        if (query2.size() > 0) {
            s = "此学科已被使用,不能被删除!";
        } else {
            String deleteSql = " update doctor_type set is_delete=1 where  id = :id ";
            Map<String, Object> params2 = new HashMap<String, Object>();
            params2.put("id", id);
            this.getNamedParameterJdbcTemplate().update(deleteSql, params2);
            s = "删除成功";
        }
        return s;
    }

}
