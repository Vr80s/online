package com.xczh.consumer.market.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;

@Repository
public class OLCourseMapper extends BasicSimpleDao {

	public List<CourseLecturVo> offLineClass(int number, int pageSize, 
			String queryParam) throws SQLException {
		StringBuffer all = new StringBuffer("");
		all.append("select  oc.smallimg_path as smallImgPath,oc.id ");
		all.append(" from oe_course as oc INNER JOIN oe_user ou on(ou.id=oc.user_lecturer_id)  ");
		all.append(" where oc.is_delete=0 and oc.status=1 and oc.type = 3  ");//and oc.is_free=0 oc.course_type=1 and
		return super.queryPage(JdbcUtil.getCurrentConnection(),all.toString(),number,pageSize,CourseLecturVo.class);
	}
}
