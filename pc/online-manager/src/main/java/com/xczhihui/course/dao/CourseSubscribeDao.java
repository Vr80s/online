package com.xczhihui.course.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.course.vo.CourseSubscribeVo;

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


}