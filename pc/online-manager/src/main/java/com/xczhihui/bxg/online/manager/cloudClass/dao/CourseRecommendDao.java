package com.xczhihui.bxg.online.manager.cloudClass.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseRecommendVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CourseRecommendDao extends SimpleHibernateDao {

	public Page<CourseVo> findCourseRecommendPage(CourseRecommendVo courseRecommendVo, int pageNumber, int pageSize){
//		   Map<String,Object> paramMap=new HashMap<String,Object>();
//		   StringBuilder sql=new StringBuilder("");
//		   
//		   Page<CourseRecommendVo> ms = this.findPageBySQL(sql.toString(), paramMap, CourseRecommendVo.class, pageNumber, pageSize);
//      	   return ms;
      	   
      	   
      	 Map<String,Object> paramMap=new HashMap<String,Object>();
		 StringBuilder sql =new StringBuilder( "SELECT ocr.id ,oc.id showCourseId,oc.grade_name as courseName, oc.class_template as classTemplate, om.name as xMenuName,st.name as scoreTypeName,"
				 + "tm.name as teachMethodName,oc.course_length as courseLength,oc.learnd_count as learndCount,"
				 + "oc.create_time as createTime,oc.status as status ,oc.is_free as isFree,oc.original_cost as originalCost,"
				 + "oc.current_price as currentPrice,oc.description as description,oc.menu_id as menuId,oc.course_type_id as courseTypeId,"
				 + "oc.courseType as courseType,count(og.id) as countGradeNum,oc.is_recommend FROM oe_course oc "
				 + "LEFT JOIN oe_menu om ON om.id = oc.menu_id LEFT JOIN score_type st ON st.id = oc.course_type_id "
				 + "LEFT JOIN teach_method tm ON tm.id = oc.courseType "
				 + "left join oe_grade og on og.course_id = oc.id  "
				 + "join oe_course_recommend ocr on ocr.rec_course_id = oc.id and ocr.is_delete = 0 where oc.is_delete = 0  ");
		 
//		 paramMap.put("isRecommend","1");//只查询已推荐的课程
//		 sql.append(" and oc.is_recommend = :isRecommend ");
		 
		 paramMap.put("showCourseId",courseRecommendVo.getShowCourseId());//只查询已推荐的课程
		 sql.append(" and ocr.show_course_id = :showCourseId ");

		 sql.append(" group by oc.id  order by ocr.sort asc");
		 return this.findPageBySQL(sql.toString(), paramMap, CourseVo.class, pageNumber, pageSize);
	}
}

