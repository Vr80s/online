package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.Page;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.LecturVo;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class OnlineLecturerMapper extends BasicSimpleDao{

	public List<LecturVo> findLecturerById(Integer courseId) throws SQLException {
		Integer pageSize = 3;
		Page<LecturVo> page = null;
		if (courseId != null) {
			String sql = " select le.`name` from  course_r_lecturer grl join oe_lecturer le where grl.lecturer_id = le.id  and grl.is_delete = 0"
					+ " and  grl.course_id= ?  and le.role_type=1  and le.is_delete=0  order by  le.create_time ";
			
			sql += "limit " +pageSize;
			
			Object[] params = {courseId};
			return super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new BeanListHandler<>(LecturVo.class),params);
		}
		return page.getItems();
	}
	
}
