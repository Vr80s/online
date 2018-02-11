package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/***
 * 听课
 * @author yjy
 *
 */
@Repository
public class ListenCourseMapper extends BasicSimpleDao {

	/****
	 * 听课列表查询
	 * @return
	 * @throws SQLException
	 */
	public List<CourseLecturVo>  listenCourseList() throws SQLException{
		StringBuffer sql = new StringBuffer(" ");
		sql.append(" select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,"
				+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,DATE_FORMAT(oc.start_time,'%m.%d') as startDateStr,");
		sql.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
				+ "+IFNULL(oc.default_student_count, 0) learndCount, ");
		sql.append(" if(oc.is_free =0,0,1) as watchState, ");//是否免费
		sql.append(" oc.city as city, ");//城市
		sql.append(" oc.smallimg_path as smallImgPath,");
		sql.append(" '2' as courseType ");
		sql.append(" from oe_course oc ");
		sql.append(" where   "
				+ "oc.is_delete=0 and oc.status = 1   ");
		sql.append(" and oc.multimedia_type = 2 ");
		sql.append(" order by oc.recommend_sort desc,oc.create_time desc limit 0,12 ");
		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(CourseLecturVo.class) );
	}
	

}
