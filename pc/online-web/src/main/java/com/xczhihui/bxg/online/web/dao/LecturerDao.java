package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.web.vo.LecturVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 讲师
 * @author majian
 * @date 2016-8-22 11:33:00
 */
@Repository
public class LecturerDao extends SimpleHibernateDao {


    /**
     * 根据课程查询讲师
     * @return
     */
    public List<LecturVo> listByCourseId(String courseId){
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("courseId",courseId);
        String sql="select ol.name,ol.sort,ol.description,ol.head_img as headImg from  course_r_lecturer grl join oe_lecturer ol on grl.lecturer_id=ol.id where grl.is_delete=0 and ol.is_delete=0 and  grl.course_id=:courseId  order by  ol.create_time ";
        return this.getNamedParameterJdbcTemplate().query(sql,params, new BeanPropertyRowMapper<LecturVo>(LecturVo.class));
    }

}
