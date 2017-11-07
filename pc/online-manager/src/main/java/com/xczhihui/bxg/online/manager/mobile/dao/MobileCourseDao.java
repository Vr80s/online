package com.xczhihui.bxg.online.manager.mobile.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.ApplyGradeCourse;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;

@Repository("mobileCourseDao")
public class MobileCourseDao extends HibernateDao<Course>{

	public Page<CourseVo> findMobileCoursePage(CourseVo courseVo, int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		 StringBuilder sql = new StringBuilder(" select w.* from ( SELECT oc.id as id ,oc.grade_name as courseName, oc.class_template as classTemplate, om.name as xMenuName,st.name as scoreTypeName,"
				 + "tm.name as teachMethodName,oc.course_length as courseLength,oc.learnd_count as learndCount,"
				 + "oc.create_time as createTime,oc.status as status ,oc.is_free as isFree,oc.original_cost as originalCost,"
				 + "oc.current_price as currentPrice,oc.description as description,oc.menu_id as menuId,oc.course_type_id as courseTypeId,"
				 + "oc.courseType as courseType,count(og.id) as countGradeNum,oc.is_recommend,oc.qqno,oc.course_type as serviceType,oc.user_lecturer_id as userLecturerId, "//TODO 杨宣增加userLecturerId
				 + "(select group_concat(ol.name) from oe_lecturer ol where role_type=1 and is_delete=0 and ol.id in (select lecturer_id from course_r_lecturer where course_id=og.course_id and is_delete=0 )) AS role_type1, "
				 + "(select group_concat(ol.name) from oe_lecturer ol where role_type=2 and is_delete=0 and ol.id in (select lecturer_id from course_r_lecturer where course_id=og.course_id and is_delete=0 ))role_type2, "
				 + "(select group_concat(ol.name) from oe_lecturer ol where role_type=3 and is_delete=0 and ol.id in (select lecturer_id from course_r_lecturer where course_id=og.course_id and is_delete=0 ))role_type3, "
				 + "(select count(*) from oe_course_mobile as ocmb where ocmb.course_id = oc.id ) as isCourseDetails " //杨宣新增
				 + "FROM oe_course oc "
				 + "LEFT JOIN oe_menu om ON om.id = oc.menu_id LEFT JOIN score_type st ON st.id = oc.course_type_id "
				 + "LEFT JOIN teach_method tm ON tm.id = oc.courseType "
				 + "left join oe_grade og on og.course_id = oc.id where oc.is_delete = 0  and oc.type is null AND oc.`online_course`=0");
		 if (courseVo.getCourseName() != null) {
			 paramMap.put("courseName", "%" + courseVo.getCourseName() + "%");
			 sql.append("and oc.grade_name like :courseName ");
		 }
		 if (courseVo.getMenuId() != null) {
			 paramMap.put("menuId", courseVo.getMenuId());
			 sql.append("and oc.menu_id = :menuId ");
		 }
		 if (courseVo.getCourseTypeId() != null) {
			 paramMap.put("courseTypeId", courseVo.getCourseTypeId());
			 sql.append("and oc.course_type_id = :courseTypeId ");
		 }
		 if (courseVo.getCourseType() != null) {
			 paramMap.put("courseType", courseVo.getCourseType());
			 sql.append("and oc.courseType = :courseType ");
		 }
//		 paramMap.put("serviceType",1); // 20170724 于瑞鑫
//		 sql.append("and oc.course_type = :serviceType ");
		 if (courseVo.getIsRecommend() != null) {
			 paramMap.put("isRecommend", courseVo.getIsRecommend());
			 sql.append("and oc.is_recommend = :isRecommend ");
		 }
		 if (courseVo.getStatus() != null) {
			 paramMap.put("status", courseVo.getStatus());
			 sql.append("and oc.status = :status ");
		 }

		 if (courseVo.getId() > 0) {
			 paramMap.put("courseId", courseVo.getId());
			 sql.append("and oc.id <> :courseId ");
		 }


		 sql.append(" group by oc.id  order by oc.sort desc");
		 sql.append(" ) w ");
		if(courseVo.getIsCourseDetails()!=null){
			sql.append("where w.isCourseDetails=:isCourseDetails ");
			paramMap.put("isCourseDetails", courseVo.getIsCourseDetails());
		}
		 
		 Page<CourseVo> courseVos = this.findPageBySQL(sql.toString(), paramMap, CourseVo.class, pageNumber, pageSize);
		 for (CourseVo entityVo : courseVos.getItems()) {
			// 学生报名到班级信息表
			//实际学习人数 
			List<ApplyGradeCourse> temps = this.findEntitiesByProperty(ApplyGradeCourse.class, "courseId", entityVo.getId());
		    entityVo.setActCount(temps.size());
			
		    //讲课老师
			/*Map<String,Object> params=new HashMap<String,Object>();
	        params.put("courseId", entityVo.getId());
	        StringBuilder teacherSql=new StringBuilder();
	        teacherSql.append("SELECT group_concat(ol. name) as lecturerName from oe_lecturer ol ,course_r_lecturer grl where ol.id = grl.lecturer_id AND grl.course_id =:courseId AND ol.is_delete = 0 GROUP BY grl.course_id");
	        List<CourseVo> teacherNames = this.getNamedParameterJdbcTemplate().query(teacherSql.toString(),params,BeanPropertyRowMapper.newInstance(CourseVo.class));
	        entityVo.setLecturerName(teacherNames==null||teacherNames.size()==0?"":teacherNames.get(0).getLecturerName());*/
		    /**
		     * 杨宣修改   把班级信息去掉
		     */
		    Map<String,Object> params=new HashMap<String,Object>();
	        params.put("user_lecturer_id", entityVo.getUserLecturerId());
	        StringBuilder teacherSql=new StringBuilder();  //// + "LEFT JOIN oe_user ou on oc.user_lecturer_id = ou.id and oe.is_lecturer = 1 and oe.status = 0");
	        teacherSql.append("SELECT ou.name as lecturerName from oe_user as ou where ou.id =:user_lecturer_id and ou.is_lecturer = 1 and ou.status = 0 ");
	        List<CourseVo> teacherNames = this.getNamedParameterJdbcTemplate().query(teacherSql.toString(),params,BeanPropertyRowMapper.newInstance(CourseVo.class));
	        entityVo.setLecturerName(teacherNames==null||teacherNames.size()==0?"":teacherNames.get(0).getLecturerName());
		 }
		 return courseVos;
//		 }
	 }

	public List<ScoreType> getScoreType() {
		// TODO Auto-generated method stub
		String sql = "select * from score_type where is_delete = 0  and status=1 and id<>0";
		Map<String,Object> params=new HashMap<String,Object>();
		return this.getNamedParameterJdbcTemplate().query(sql,params,BeanPropertyRowMapper.newInstance(ScoreType.class));
	}

}
