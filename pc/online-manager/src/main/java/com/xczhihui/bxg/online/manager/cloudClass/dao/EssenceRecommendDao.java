package com.xczhihui.bxg.online.manager.cloudClass.dao;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;
import com.xczhihui.bxg.online.manager.utils.Group;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("essenceRecommendDao")
public class EssenceRecommendDao extends HibernateDao<Course>{

	public Page<CourseVo> findEssenceRecCoursePage(CourseVo courseVo,
			int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		Map<String,Object> paramMap=new HashMap<String,Object>();
		 StringBuilder sql = new StringBuilder("SELECT \n" +
				 "  oc.id AS id,\n" +
				 "  oc.grade_name AS courseName,\n" +
				 "  oc.direct_id directId,\n" +
				 "  oc.class_template AS classTemplate,\n" +
				 "  om.name AS xMenuName,\n" +
				 "  oc.multimedia_type multimediaType,\n" +
				 "  oc.address,\n" +
				 "  oc.course_length AS courseLength,\n" +
				 "  oc.learnd_count AS learndCount,\n" +
				 "  oc.city AS realCitys,\n" +
				 "  oc.create_time AS createTime,\n" +
				 "  oc.status AS STATUS,\n" +
				 "  oc.is_free AS isFree, \n" +
				 "  oc.current_price AS currentPrice,\n" +
				 "  oc.description AS description,\n" +
				 "  oc.menu_id AS menuId,\n" +
				 "  oc.course_type_id AS courseTypeId,\n" +
				 "  oc.courseType AS courseType,\n" +
				 "  oc.is_recommend,\n" +
				 "  oc.course_type AS serviceType,\n" +
				 "  oc.user_lecturer_id AS userLecturerId,\n" +
				 "  ou.`name` lecturerName,\n" +
				 "  oc.`lecturer`,\n" +
				 "  oc.live_status as liveStatus, \n" +
				 
				 "  oc.`is_essence` as isEssence,\n" +
				 "  oc.is_type_recommend as isTypeRecommend, \n" +
				 
				 "  oc.type as type \n" +
				 "FROM\n" +
				 "  oe_course oc \n" +
				 "  LEFT JOIN oe_menu om \n" +
				 "    ON om.id = oc.menu_id \n" +
				 "  LEFT JOIN oe_user ou\n" +
				 "    ON ou.id=oc.user_lecturer_id where oc.is_delete = 0 ");
	
		 if(courseVo.getIsEssence()!=null){ //精品推荐排序
			 
			 paramMap.put("isEssence", 1);
			 sql.append("and oc.is_essence = :isEssence ");
			 
			 sql.append(" order by oc.status desc,oc.essence_sort desc");
			 
		 }else if(courseVo.getIsTypeRecommend()!=null){ //分类推荐排序
			 //学科分类
			 if (courseVo.getMenuId() != null && courseVo.getMenuId()!=-1) {
				 paramMap.put("menuId", courseVo.getMenuId());
				 sql.append("and oc.menu_id = :menuId ");
				 
			 }
			 paramMap.put("isTypeRecommend",1);
			 sql.append("and oc.is_type_recommend = :isTypeRecommend ");
			 
			 sql.append(" order by oc.status desc,oc.type_sort desc");
		 }else{
			 
			 if (courseVo.getCourseName() != null) {
				 paramMap.put("courseName", "%" + courseVo.getCourseName() + "%");
				 sql.append("and oc.grade_name like :courseName ");
			 }
			 if (courseVo.getType() != null) {
				 paramMap.put("type", courseVo.getType());
				 sql.append("and oc.type = :type ");
			 }
			 if (courseVo.getLiveStatus() != null) {
				 paramMap.put("liveStatus", courseVo.getLiveStatus());
				 sql.append("and oc.live_status = :liveStatus ");
			 }
			 if (courseVo.getMultimediaType() != 0) {
				 paramMap.put("multimediaType", courseVo.getMultimediaType());
				 sql.append("and oc.multimedia_type = :multimediaType ");
			 }
			 sql.append(" order by oc.status desc,oc.sort desc");
		 }
		 System.out.println(sql);
		 Page<CourseVo> courseVos = this.findPageBySQL(sql.toString(), paramMap, CourseVo.class, pageNumber, pageSize);
		 return courseVos;
	}

	public void updateCourseDirectId(Course course) {
		String sql="update oe_course set direct_id = :direct_id where  id = :id";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", course.getId());
		params.put("direct_id", course.getDirectId());
		this.getNamedParameterJdbcTemplate().update(sql, params);
	}

}
