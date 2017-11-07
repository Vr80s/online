package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.web.vo.GradeVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2016/11/22.
 */
@Repository
public class GradeDao extends SimpleHibernateDao {

    /**
     * 查询课程下报名中的班级信息
     * @param courseId 课程id
     * @return
     */
    public List<GradeVo> findGradeByCourseId( Integer courseId) {
        //班级信息集合
        List<GradeVo>  newGradeVos =null;
        String sql=" select g.* from ( select id, name,1 as isOpenClass, SUBSTRING(curriculum_time,1,10) as curriculumTime,SUBSTRING(stop_time,1,10) as stop_time,ifnull(qqno,'暂无') as qqno, "+
                   " IFNULL(student_amount,0) as student_amount, (student_amount -  IFNULL(student_count,0) ) as seat from oe_grade  where is_delete=0  and grade_status = 1 and   course_id = ? " +
                   " and ifnull(student_count,0) != student_amount  and  unix_timestamp(NOW()) between  unix_timestamp(curriculum_time)  and  unix_timestamp(stop_time)    order by  curriculum_time  limit 1 ) as g ";
        //查找某课程下的班级，最多显示三条
        List<GradeVo> gradeVos = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, new Object[]{courseId}, BeanPropertyRowMapper.newInstance(GradeVo.class));
        return  gradeVos;
    }
}
