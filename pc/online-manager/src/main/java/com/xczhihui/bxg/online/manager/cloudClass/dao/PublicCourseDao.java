package com.xczhihui.bxg.online.manager.cloudClass.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;

@Repository("publicCourseDao")
public class PublicCourseDao extends HibernateDao<Course>{

	public Page<CourseVo> findCloudClassCoursePage(CourseVo courseVo,
			int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		Map<String,Object> paramMap=new HashMap<String,Object>();
	 	//StringBuilder sql =new StringBuilder("select c.*,c.grade_name as courseName ,l.name as lecturerName ,m.name as menuName from oe_course c,oe_menu m,oe_lecturer l where c.menu_id=m.id and c.lecturer_id = l.id and c.is_delete=0 and c.type=1");
		/**
		 * 2017-08-13 杨宣修改
		 * 讲师角色切换  当前  --用户角色
		 * 
		 * oe_course_mobile 从这个里面判断是否存在课程详情啊
		 */
		StringBuilder sql =new StringBuilder("select c.id,c.current_price*10 currentPrice,c.`lecturer`,c.`course_length`,c.`start_time`,c.smallimg_path smallimgPath,"


				+ " if(c.live_status = 2,if(DATE_SUB(now(),INTERVAL 30 MINUTE)>=c.start_time,6,if(  "
				+ "			    DATE_ADD(now(),INTERVAL 10 MINUTE)>=c.start_time and now() < c.start_time,"
				+ "    4,if(DATE_ADD(now(),INTERVAL 2 HOUR)>=c.start_time and now() < c.start_time,5,c.live_status))),c.live_status) "
				+ "			     AS liveStatus, "
				
				
				+ "c.grade_name as courseName ,c.sort_update_time as sortUpdateTime,ou.name as lecturerName ,m.name as menuName,c.`course_pwd` coursePwd "
				+ ",c.live_source as liveSource,c.release_time as releaseTime,c.recommend_sort as recommendSort,c.status as status,c.direct_id as directId,c.`essence_sort` as essenceSort \n" +
				" from oe_course c  LEFT JOIN\n" +
				"  oe_menu m ON c.menu_id = m.id \n" +
				"  LEFT JOIN \n" +
				"  oe_user ou ON c.user_lecturer_id = ou.id  "
				+ "where c.is_delete = 0 \n" +
				"  AND c.type = 1  ");
	 	
	 	if(courseVo.getCourseName() != null){
	 		paramMap.put("courseName", "%"+courseVo.getCourseName()+"%");
	 		sql.append(" and c.grade_name like :courseName ");
	 	}
		/*if(courseVo.getLecturerName() != null){
	 		paramMap.put("lecturerName", "%"+courseVo.getLecturerName()+"%");
	 		sql.append(" and l.name like :lecturerName ");
	 	}*/

	 	if(courseVo.getLecturerName() != null){
	 		paramMap.put("lecturerName", "%"+courseVo.getLecturerName()+"%");
	 		sql.append(" and ou.name like :lecturerName ");
	 	}
	 	
	 	if(courseVo.getMenuId() != null){
	 		paramMap.put("menuId", courseVo.getMenuId());
	 		sql.append(" and c.menu_id = :menuId ");
	 	}
		if(courseVo.getStatus() != null){
			paramMap.put("status", courseVo.getStatus());
			sql.append(" and c.status = :status ");
		}
		if(courseVo.getLiveStatus() != null){
			paramMap.put("liveStatus", courseVo.getLiveStatus());
			sql.append(" and c.live_status = :liveStatus ");
		}
	 	
	 	 if(courseVo.getStartTime()!=null){
	            sql.append(" and c.start_time >= :startTime ");
	            paramMap.put("startTime",courseVo.getStartTime());
	        }
	        if(courseVo.getEndTime()!=null){
	            sql.append(" and c.end_time <= :endTime ");
	            paramMap.put("endTime",courseVo.getEndTime());
	        }
	 	sql.append(" order by c.status desc,c.recommend_sort desc,c.start_time desc");
 		
		return this.findPageBySQL(sql.toString(), paramMap, CourseVo.class, pageNumber, pageSize);
	}

	public void updateCourseDirectId(Course course) {
		String sql="update oe_course set direct_id = :direct_id where  id = :id";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", course.getId());
		params.put("direct_id", course.getDirectId());
		this.getNamedParameterJdbcTemplate().update(sql, params);
	}

}
