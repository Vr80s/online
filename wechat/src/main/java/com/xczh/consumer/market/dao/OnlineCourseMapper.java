package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class OnlineCourseMapper extends BasicSimpleDao{
    
	public List<CourseLecturVo> findLiveListInfo(int pageNumber, int pageSize, String queryParam) throws SQLException {
		// TODO Auto-generated method stub  
		StringBuffer sql = new StringBuffer("");
		sql.append("select c.id,c.direct_Id as directId,c.course_length as courseLength,c.grade_name as gradeName,ou.small_head_photo as headImg,ou.name as name,ou.id as userId,");
		sql.append("c.smallimg_path as smallImgPath,c.start_time as startTime,c.end_time as endTime, ");
		sql.append("c.original_cost as originalCost,c.current_price as currentPrice,");
		//sql.append(" if(c.course_pwd is not null,2,if(c.is_free =0,1,0)) as watchState, ");  // 观看状态  
		sql.append(" if(c.course_pwd is not null,2,if(c.is_free = 0,1,0)) as watchState, ");  // 观看状态  
		sql.append(" IF(c.type is not null,1,if(c.multimedia_type=1,2,3)) as type, "); //类型 
		//观看人数
		sql.append(" (SELECT IFNULL((SELECT  COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id),0) ");
		sql.append(" + IFNULL(c.default_student_count, 0) + IFNULL(c.pv, 0)) as  learndCount, ");
		
		sql.append(" live_status as  lineState ");
		sql.append(" from oe_course c,oe_user ou ");
		sql.append(" where  c.user_lecturer_id = ou.id and  c.is_delete=0 and c.status = 1 and ou.status =0 and c.type=1  ");
		//房间编号/主播/课程
		if(queryParam!=null && !"".equals(queryParam) && !"null".equals(queryParam)){
			sql.append(" and ("); 
			sql.append(" ou.room_number like '%"+ queryParam + "%'"); 
			sql.append(" or "); 
			sql.append(" ou.name like '%"+ queryParam + "%'"); 
			sql.append(" or "); 
			sql.append(" c.grade_name like '%"+ queryParam + "%')"); 
		}
		sql.append(" order by  c.live_status,c.start_time desc ");
		return super.queryPage(JdbcUtil.getCurrentConnection(), sql.toString(),pageNumber,pageSize,CourseLecturVo.class,null);
	}
    
	public CourseLecturVo liveDetailsByCourseId(int course_id) throws SQLException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("");
		sql.append("select c.id,c.direct_Id as directId,c.grade_name as gradeName,ou.small_head_photo as headImg,ou.name as name,");
		sql.append("c.smallimg_path as smallImgPath,ou.room_number as roomNumber,c.start_time as startTime,c.end_time as endTime,");
		sql.append("c.description as description,ou.id as userId,"
				+ " c.original_cost as originalCost,c.current_price as currentPrice,");  //课程简介
		sql.append("if(c.course_pwd is not null,2,if(c.is_free =0,1,0)) as watchState, ");  //课程简介
		
		sql.append(" (SELECT IFNULL((SELECT  COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id),0) ");
		sql.append(" + IFNULL(c.default_student_count, 0) + IFNULL(c.pv, 0)) as  learndCount,c.live_status as  lineState ");
		
		sql.append(" from oe_course c,oe_user ou ");
		sql.append(" where  c.user_lecturer_id = ou.id and c.id = ?  and c.is_delete=0 and c.status = 1 "); /*and  c.type=1 */
		Object[] params = {course_id};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(), new BeanHandler<>(CourseLecturVo.class),params);
	}

	public CourseLecturVo bunchDetailsByCourseId(int course_id) throws SQLException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("");
		sql.append("select c.id,c.direct_Id as directId,c.grade_name as gradeName,ou.small_head_photo as headImg,ou.name as name,");
		sql.append("c.smallimg_path as smallImgPath,c.start_time as startTime,c.end_time as endTime,");
		sql.append("c.description as description,ou.id as userId");  //课程简介
		sql.append(" from oe_course c,oe_user ou ");
		sql.append(" where  c.user_lecturer_id = ou.id and c.id = ?  and c.is_delete=0 and c.status = 1 and c.type =1  ");
		Object[] params = {course_id};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(), new BeanHandler<>(CourseLecturVo.class),params);
	}
	/**
	 * Description：保存用户密码
	 * @param id
	 * @param course_id
	 * @param course_pwd
	 * @throws SQLException
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public void saveCoursePwdUser(String id, int course_id, String course_pwd) throws SQLException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("");
		sql.append("insert into course_pwd_user ");
		sql.append("(course_id,user_id,course_pwd,insert_date) ");  //课程简介
		sql.append("values                              ");
		sql.append(" (?,?,?,?) ");
		this.update(JdbcUtil.getCurrentConnection(),sql.toString(), course_id,id,course_pwd,new Date());
	}
	/**
	 * Description：得到课程类型：音频、视频、直播
	 * @param courseId
	 * @return
	 * @throws SQLException
	 * @return Integer
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public Integer getIsCouseType(Integer courseId) throws SQLException{
		String sql = "select IF(c.type is not null,1,if(c.multimedia_type=1,2,3)) as type from oe_course as c where id = ?";
		//this.query(JdbcUtil.getCurrentConnection(), sql,MapHandler new Object()[courseId]);
		Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), sql,
				new MapHandler(),courseId);
		Integer isCourseType = 0;
		if(map!=null && map.size()>0){
			Object type = map.get("type");
			if(type!=null){
				isCourseType = Integer.parseInt(type.toString());
			}
		}
		return isCourseType;
	}

	
	/**
	 * Description：分享跳转使用
	 * @param courseId
	 * @return
	 * @throws SQLException
	 * @return Map<String,Object>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public Map<String, Object> shareJump(Integer courseId) throws SQLException{
		
		String sql = "select c.direct_Id as directId,IF(c.type is not null,1,if(c.multimedia_type=1,2,3)) as type, "
				+ "c.live_status as state,c.grade_name as gradeName,smallimg_path as smallImgPath   from oe_course as c where id = ?";
		Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), sql,
				new MapHandler(),courseId);
		return map;
	}
	
	/**
	 * Description：获取当前直播在线人数   ---》假的
	 * @param courseId
	 * @return
	 * @throws SQLException
	 * @return Integer
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public Integer getCurrntLiveByCourseId(Integer courseId) throws SQLException{
		String sql = "SELECT IFNULL((SELECT  COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id),0)"
				+ " + IFNULL(default_student_count, 0) + IFNULL(pv, 0) learnd_count FROM oe_course c where c.id=";
		Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), sql,
				new MapHandler(),courseId);
		Integer isCurrntCount = 0;
		if(map!=null && map.size()>0){
			Object type = map.get("learnd_count");
			if(type!=null){
				isCurrntCount = Integer.parseInt(type.toString());
			}
		}
		return isCurrntCount;
	}
}	
