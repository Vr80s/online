package com.xczhihui.course.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.ApplyGradeCourse;
import com.xczhihui.course.vo.CourseVo;
import com.xczhihui.course.vo.CriticizeVo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CriticizeDao extends SimpleHibernateDao {

	public Page<CriticizeVo> findCriticizePage(CriticizeVo criticizeVo, Integer pageNumber, Integer pageSize){
		   Map<String,Object> paramMap=new HashMap<String,Object>();
		   StringBuilder sql=new StringBuilder("SELECT " +
				   							   "	trim(oc.response) as response, " +
											   "	oc.id, " +
											   "	oc.content, " +
											   "	oc.star_level, " +
											   "	oc.praise_sum, " +
											   "	ous.`name` createPersonName, " +
											   "	(select DISTINCT og.`name` from oe_apply oa " +
											   "	join apply_r_grade_course argc on oa.id = argc.apply_id " +
											   "	join oe_grade og on argc.grade_id = og.id " +
											   "	where oa.user_id = oc.user_id and og.course_id = oc.course_id) gradeName, " +
											   "	oc.create_time " +
											   "FROM " +
											   "	oe_criticize oc " +
											   " LEFT JOIN oe_user ous on	oc.user_id = ous.id where oc.is_delete = 0 ");
		   
		   if(criticizeVo.getChapterId() != null){
		 		
			   sql.append(" and oc.chapter_id in( ");
		 		String [] ids=criticizeVo.getChapterId().split(",");
		 		 for(int i=0;i<ids.length;i++){
		                if(i!=0) {
                            sql.append(",");
                        }
		                sql.append("'"+ids[i]+"'");
		         }
		         sql.append(" ) ");
		         
		   }else if(criticizeVo.getCourseId() != null){
				   sql.append(" and oc.course_id = :courseId ");
				   paramMap.put("courseId", criticizeVo.getCourseId());
		   }

		   if(criticizeVo.getContent() != null){
			   sql.append(" and oc.content like :content");
			   paramMap.put("content", "%"+criticizeVo.getContent()+"%");
		   }
		   
		   if(criticizeVo.getResponse() != null && "1".equals(criticizeVo.getResponse())){
			   sql.append(" and oc.response is not null and oc.response != '' ");
		   }
		   if(criticizeVo.getResponse() != null && "0".equals(criticizeVo.getResponse())){
			   sql.append(" and (oc.response is null or oc.response = '') ");
		   }
		   
		   if(criticizeVo.getCreatePersonName() != null){
			   sql.append(" and ous.name like :name");
			   paramMap.put("name", "%"+criticizeVo.getCreatePersonName()+"%");
		   }
		   
		   if(criticizeVo.getVideoId() != null){
			   sql.append(" and oc.video_id = :videoId");
			   paramMap.put("videoId", criticizeVo.getVideoId());
		   }

		   if(criticizeVo.getGradeId() != null){
			   sql.append( " and (select 1 from oe_apply oa " +
						   "	join apply_r_grade_course argc on oa.id = argc.apply_id " +
						   "	join oe_grade og on argc.grade_id = og.id " +
						   "	where oa.user_id = oc.user_id " +
						   "    and og.id = :gradeId) ");
			   paramMap.put("gradeId", criticizeVo.getGradeId());
		   }

		   if(criticizeVo.getStartTime() !=null){
			  sql.append(" and oc.create_time >=:startTime");
			  paramMap.put("startTime", criticizeVo.getStartTime());
		   }

		   if(criticizeVo.getStopTime() !=null){
			  sql.append(" and DATE_FORMAT(oc.create_time,'%Y-%m-%d') <=:stopTime");
			  paramMap.put("stopTime", criticizeVo.getStopTime());
		   }
		   
		   if(criticizeVo.getSortType() !=null && criticizeVo.getSortType() == 1){
			   sql.append(" order by star_level desc,create_time desc");
		   }else if(criticizeVo.getSortType() !=null && criticizeVo.getSortType() == 2){
			   sql.append(" order by praise_sum desc,create_time desc");
		   }else{
			   sql.append(" order by create_time desc");
		   }
		   Page<CriticizeVo> ms = this.findPageBySQL(sql.toString(), paramMap, CriticizeVo.class, pageNumber, pageSize);
      	   return ms;
	}

	public Page<CourseVo> findCloudClassCoursePage(CourseVo courseVo, int pageNumber, int pageSize){
		 	Map<String,Object> paramMap=new HashMap<String,Object>();
		 	StringBuilder sql =new StringBuilder( "SELECT oc.id as id ,oc.grade_name as courseName, oc.class_template as classTemplate, om.name as xMenuName,st.name as scoreTypeName,"
					+ "tm.name as teachMethodName,oc.course_length as courseLength,oc.learnd_count as learndCount,"
					+ "oc.create_time as createTime,oc.status as status ,oc.is_free as isFree,oc.original_cost as originalCost,"
					+ "oc.current_price as currentPrice,oc.description as description,oc.menu_id as menuId,oc.course_type_id as courseTypeId, "
					+ "(select count(1) from oe_criticize ocr where ocr.course_id = oc.id and ocr.is_delete = 0) criticizeNum, "
					+ "(select count(1) from oe_criticize ocr where ocr.star_level >= 4 and ocr.course_id = oc.id and ocr.is_delete = 0) goodCriticizeNum, "
					+ "oc.courseType as courseType,count(og.id) as countGradeNum,oc.is_recommend,oc.qqno FROM oe_course oc "
					+ "LEFT JOIN oe_menu om ON om.id = oc.menu_id LEFT JOIN score_type st on st.id = oc.course_type_id "
					+ "LEFT JOIN teach_method tm ON tm.id = oc.courseType "
					+ "left join oe_grade og on og.course_id = oc.id where oc.is_delete = 0  and oc.type=2 ");

		 	if(courseVo.getCourseName() != null){
		 		paramMap.put("courseName", "%"+courseVo.getCourseName()+"%");
		 		sql.append("and oc.grade_name like :courseName ");
		 	}
		 	
		 	if(courseVo.getMenuId() != null){
		 		paramMap.put("menuId", courseVo.getMenuId());
		 		sql.append("and oc.menu_id = :menuId ");
		 	}
		 	
		 	if(courseVo.getCourseTypeId() != null){
		 		paramMap.put("courseTypeId", courseVo.getCourseTypeId());
		 		sql.append("and oc.course_type_id = :courseTypeId ");
		 	}
		 	
		 	if(courseVo.getCourseType() != null){
		 		paramMap.put("courseType", courseVo.getCourseType());
		 		sql.append("and oc.courseType = :courseType ");
		 	}
		 	
		 	if(courseVo.getIsRecommend() != null){
		 		paramMap.put("isRecommend", courseVo.getIsRecommend());
		 		sql.append("and oc.is_recommend = :isRecommend ");
		 	}
		 	
		 	if(courseVo.getStatus() != null){
		 		paramMap.put("status", courseVo.getStatus());
		 		sql.append("and oc.status = :status ");
		 	}
		 	
		 	if(courseVo.getId() > 0){
		 		paramMap.put("courseId", courseVo.getId());
		 		sql.append("and oc.id <> :courseId ");
		 	}
		 	
		 	if(courseVo.getSortType() != null && courseVo.getSortType() == 1 ){
		 		sql.append(" group by oc.id  order by (select count(1) from oe_criticize ocr WHERE ocr.course_id = oc.id and ocr.is_delete = 0) desc,oc.sort desc");
		 	}else if(courseVo.getSortType() != null && courseVo.getSortType() == 2 ){
		 		sql.append(" group by oc.id  order by (select sum(case when ocr.star_level >= 4 then 1 else 0 end)/count(1) from oe_criticize ocr where ocr.course_id = oc.id and ocr.is_delete = 0) desc,oc.sort desc");
		 	}else{
		 		sql.append(" group by oc.id  order by oc.sort desc");	
		 	}
	 		
	 		Page<CourseVo> courseVos=this.findPageBySQL(sql.toString(), paramMap, CourseVo.class, pageNumber, pageSize);
	 		for(CourseVo entityVo :courseVos.getItems()){
	 			List<ApplyGradeCourse> temps= this.findEntitiesByProperty(ApplyGradeCourse.class, "courseId", entityVo.getId());
	 			entityVo.setActCount(temps.size());
	 		}
			return courseVos;
	}
}

