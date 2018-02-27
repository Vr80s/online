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
		StringBuffer sql = new StringBuffer(" select  *  from ");
		sql.append(" (select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,"
				+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,DATE_FORMAT(oc.start_time,'%m.%d') as startDateStr,");
		sql.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
				+ "+IFNULL(oc.default_student_count, 0) learndCount, ");
		sql.append(" if(oc.is_free =0,0,1) as watchState, ");//是否免费
		sql.append(" oc.city as city, ");//城市
		sql.append(" if(oc.type =3,4,IF(oc.type =1,3,if(oc.multimedia_type=1,1,2))) as type, ");
		sql.append(" '2' as courseType, ");
		sql.append(" oc.is_recommend,oc.recommend_sort,oc.create_time ");
		sql.append(" from oe_course oc ");
		sql.append(" where   "
				+ "oc.is_delete=0 and oc.status = 1  ");
		sql.append(" and oc.multimedia_type = 2 and oc.is_recommend = 1 ");
		sql.append(" union ");
		sql.append(" select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,"
				+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,DATE_FORMAT(oc.start_time,'%m.%d') as startDateStr,");
		sql.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
				+ "+IFNULL(oc.default_student_count, 0) learndCount, ");
		sql.append(" if(oc.is_free =0,0,1) as watchState, ");//是否免费
		sql.append(" oc.city as city, ");//城市
		sql.append(" if(oc.type =3,4,IF(oc.type =1,3,if(oc.multimedia_type=1,1,2))) as type, ");
		sql.append(" '2' as courseType, ");
		sql.append(" oc.is_recommend,0 as recommend_sort,oc.create_time ");
		sql.append(" from oe_course oc ");
		sql.append(" where   "
				+ "oc.is_delete=0 and oc.status = 1   ");
		sql.append(" and oc.multimedia_type = 2 and oc.is_recommend = 0) o ");
		sql.append(" ORDER BY o.is_recommend DESC,o.recommend_sort DESC,o.create_time DESC LIMIT 0,12 ");

		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(CourseLecturVo.class) );
	}

	public List<CourseLecturVo>  listenCourseListBySort() throws SQLException{
		StringBuffer sql = new StringBuffer(" ");
		sql.append(" select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,"
				+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,DATE_FORMAT(oc.start_time,'%m.%d') as startDateStr,");
		sql.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
				+ "+IFNULL(oc.default_student_count, 0) learndCount, ");
		sql.append(" if(oc.is_free =0,0,1) as watchState, ");//是否免费
		sql.append(" oc.city as city, ");//城市
		sql.append(" if(oc.type =3,4,IF(oc.type =1,3,if(oc.multimedia_type=1,1,2))) as type, ");
		sql.append(" oc.smallimg_path as smallImgPath,");
		sql.append(" '2' as courseType ");
		sql.append(" from oe_course oc ");
		sql.append(" where   "
				+ "oc.is_delete=0 and oc.status = 1   ");
		sql.append(" and oc.multimedia_type = 2  ");
		sql.append(" order by oc.start_time desc limit 0,12 ");
		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(CourseLecturVo.class) );
	}
	

}
