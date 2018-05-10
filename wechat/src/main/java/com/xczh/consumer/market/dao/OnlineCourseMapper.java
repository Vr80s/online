package com.xczh.consumer.market.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.stereotype.Repository;

import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.LecturVo;

@Repository
public class OnlineCourseMapper extends BasicSimpleDao{


	public CourseLecturVo courseShare(Integer courseId) throws SQLException {
		StringBuffer sql = new StringBuffer("");
		sql.append("select c.id,c.direct_Id as directId,c.grade_name as gradeName,c.lecturer as name,");
		sql.append("c.smallimg_path as smallImgPath,c.start_time as startTime,c.end_time as endTime,");
		sql.append("c.course_detail as description");  //课程简介
		sql.append(" from oe_course c");
		sql.append(" where c.id = ?  and c.is_delete=0  ");
		Object[] params = {courseId};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(), new BeanHandler<>(CourseLecturVo.class),params);
	}

	public LecturVo lectureShare(String lecturerId) throws SQLException {
		StringBuffer sql = new StringBuffer("");
		sql.append("select ca.name as name,ou.small_head_photo as headImg ,");
		sql.append(" ca.detail as description ");  //课程简介
		sql.append(" from oe_user ou,course_anchor ca ");
		sql.append(" where  ou.id = ca.user_id and ou.id = ?  and ou.is_delete=0 and ou.status = 0   ");
		Object[] params = {lecturerId};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(), new BeanHandler<>(LecturVo.class),params);
	}
}	
