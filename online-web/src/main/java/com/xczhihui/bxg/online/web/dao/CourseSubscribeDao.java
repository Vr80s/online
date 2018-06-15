package com.xczhihui.bxg.online.web.dao;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.web.vo.CourseSubscribeVo;

/**
 * 课程预约底层实现类
 *
 * @author rongcai Kang
 */
@Repository
public class CourseSubscribeDao extends SimpleHibernateDao {

    /**
     * 查找相关课程预约信息
     *
     * @param courseId 课程id
     * @return
     */
    public List<CourseSubscribeVo> getCourseSubscribeByCourseId(Integer courseId) {
        List<CourseSubscribeVo> courseSubscribeVoList = null;
        if (courseId != null ) {
            String sql="SELECT id,course_id AS courseId,user_id AS userId,phone FROM oe_course_subscribe WHERE course_id = ?";
            courseSubscribeVoList = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, BeanPropertyRowMapper.newInstance(CourseSubscribeVo.class),courseId);
        }
        return courseSubscribeVoList;
    }


    /**
     * 查询用户是否预约过
     * @param userId
     * @param courseId
     * @return
     */
    public boolean isSubscribe(String userId,Integer courseId){
        String sql=" select count(*) as allCount from oe_course_subscribe where course_id =? and user_id = ?";
        int ct=this.queryForInt(sql.toString(),courseId,userId);
        return ct>=1?true:false;
    }

    public Integer insert(String userId,String mobile,Integer courseId){
        String sql= " insert into oe_course_subscribe(course_id,user_id,phone,create_time ) values (?,?,?,?) ";
        int ct=this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,courseId,userId,mobile,new Date());
        return ct;
    }





}