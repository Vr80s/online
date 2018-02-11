package com.xczhihui.bxg.online.web.dao;/**
 * Created by admin on 2016/8/31.
 */

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.common.domain.ApplyGradeCourse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学员课程班级中间表底层类
 *
 * @author 康荣彩
 * @create 2016-08-31 15:07
 */
@Repository
public class ApplyGradeCourseDao extends SimpleHibernateDao {

    /**
     * 根据当前登录学员id，课程id，班级id，查找对应的学员信息
     * @return
     */
    public ApplyGradeCourse findInfoByID(String userId,Integer courseId,Integer gradeId){
        String  sql=" select jh.* from (select argc.*,a.user_id as userId from apply_r_grade_course argc left join oe_apply a  on argc.apply_id = a.id  ) as jh  where jh.userId =? and jh.course_id=?  and jh.grade_id=?";
        List<ApplyGradeCourse> applyGradeCourses= this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,new Object[]{userId,courseId,gradeId}, BeanPropertyRowMapper.newInstance(ApplyGradeCourse.class));
        return  applyGradeCourses.size() > 0 ? applyGradeCourses.get(0) : null;
    }

    /**
     * 获取当前班级中学号最大的用户
     * @return
     */
    public ApplyGradeCourse findUser(Integer courseId, Integer gradeId){
        String  sql=" select student_number as studentNumber from apply_r_grade_course where course_id =? and  grade_id=?   order by student_number desc  limit 1 ";
        List<ApplyGradeCourse> applyGradeCourses= this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,new Object[]{courseId,gradeId}, BeanPropertyRowMapper.newInstance(ApplyGradeCourse.class));
        return applyGradeCourses.size() > 0 ? applyGradeCourses.get(0) : null;
    }
    
    /**
     * 获取当前班级中学号最大的用户
     * @return
     */
    public ApplyGradeCourse findByCourseIdAndUserId(Integer courseId, String userId){
    	String  sql=" select is_payment as isPayment from apply_r_grade_course where course_id =? and  user_id=?   order by student_number desc  limit 1 ";
    	List<ApplyGradeCourse> applyGradeCourses= this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,new Object[]{courseId,userId}, BeanPropertyRowMapper.newInstance(ApplyGradeCourse.class));
    	return applyGradeCourses.size() > 0 ? applyGradeCourses.get(0) : null;
    }

    public ApplyGradeCourse findCollectionCourseByCourseIdAndUserId(Integer courseId, String userId) {
        String  sql=" SELECT \n" +
                "  is_payment AS isPayment \n" +
                "FROM\n" +
                "  apply_r_grade_course argc\n" +
                "  JOIN `oe_course` oc\n" +
                "  ON oc.id = argc.`course_id`\n" +
                "  JOIN  `collection_course` cc\n" +
                "  ON oc.id = cc.`collection_id`" +
                "WHERE cc.course_id = ? \n" +
                "  AND argc.user_id = ? \n" +
                "ORDER BY student_number DESC \n" +
                "LIMIT 1  ";
        List<ApplyGradeCourse> applyGradeCourses= this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,new Object[]{courseId,userId}, BeanPropertyRowMapper.newInstance(ApplyGradeCourse.class));
        return applyGradeCourses.size() > 0 ? applyGradeCourses.get(0) : null;
    }
}
