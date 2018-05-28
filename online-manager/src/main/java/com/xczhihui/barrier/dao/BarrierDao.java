package com.xczhihui.barrier.dao;

import com.xczhihui.barrier.vo.BarrierVo;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.ApplyGradeCourse;
import com.xczhihui.course.vo.CourseVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class BarrierDao extends SimpleHibernateDao {

	public Page<BarrierVo> findBarrierPage(BarrierVo barrierVo,
			Integer pageNumber, Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(" select " + "	id, "
				+ "	course_id, " + "	name, " + "	total_score, "
				+ "	limit_time, " + "	pass_score_percent, " + "	parent_id, "
				+ "	kpoint_id, " + "	is_delete, " + "	create_time " + " from "
				+ "	oe_barrier ob where is_delete = 0");

		if (barrierVo.getChapterId() != null) {

			sql.append(" and ob.kpoint_id in( ");
			String[] ids = barrierVo.getChapterId().split(",");
			for (int i = 0; i < ids.length; i++) {
				if (i != 0) {
					sql.append(",");
				}
				sql.append("'" + ids[i] + "'");
			}
			sql.append(" ) ");

		}

		if (barrierVo.getCourseId() != null) {
			sql.append(" and ob.course_id = :courseId ");
			paramMap.put("courseId", barrierVo.getCourseId());
		}

		sql.append(" order by ob.create_time asc ");

		Page<BarrierVo> ms = this.findPageBySQL(sql.toString(), paramMap,
				BarrierVo.class, pageNumber, pageSize);
		return ms;
	}

	public Page<CourseVo> findCloudClassCoursePage(CourseVo courseVo,
			int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"SELECT oc.id as id ,oc.grade_name as courseName, oc.class_template as classTemplate, om.name as xMenuName,st.name as scoreTypeName,"
						+ "tm.name as teachMethodName,oc.course_length as courseLength,oc.learnd_count as learndCount,"
						+ "oc.create_time as createTime,oc.status as status ,oc.is_free as isFree,oc.original_cost as originalCost,"
						+ "oc.current_price as currentPrice,oc.description as description,oc.menu_id as menuId,oc.course_type_id as courseTypeId, "
						+ "(select count(1) from oe_barrier ob where ob.course_id = oc.id and ob.is_delete = 0) barrierNum, "
						+ "oc.courseType as courseType,count(og.id) as countGradeNum,oc.is_recommend,oc.qqno FROM oe_course oc "
						+ "LEFT JOIN oe_menu om ON om.id = oc.menu_id LEFT JOIN score_type st on st.id = oc.course_type_id "
						+ "LEFT JOIN teach_method tm ON tm.id = oc.courseType "
						+ "left join oe_grade og on og.course_id = oc.id where oc.is_delete = 0  and oc.type=2 ");

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

		if (courseVo.getSortType() != null && courseVo.getSortType() == 1) {
			sql.append(" group by oc.id  order by (select count(1) from oe_criticize ocr WHERE ocr.course_id = oc.id and ocr.is_delete = 0) desc,oc.sort desc");
		} else if (courseVo.getSortType() != null
				&& courseVo.getSortType() == 2) {
			sql.append(" group by oc.id  order by (select sum(case when ocr.star_level >= 4 then 1 else 0 end)/count(1) from oe_criticize ocr where ocr.course_id = oc.id and ocr.is_delete = 0) desc,oc.sort desc");
		} else {
			sql.append(" group by oc.id  order by oc.sort desc");
		}

		Page<CourseVo> courseVos = this.findPageBySQL(sql.toString(), paramMap,
				CourseVo.class, pageNumber, pageSize);
		for (CourseVo entityVo : courseVos.getItems()) {
			List<ApplyGradeCourse> temps = this.findEntitiesByProperty(
					ApplyGradeCourse.class, "courseId", entityVo.getId());
			entityVo.setActCount(temps.size());
			if (this.getNamedParameterJdbcTemplate().queryForObject(
					"select count(id) from oe_barrier where `status`=1 and course_id='"
							+ entityVo.getId() + "' ", paramMap, Integer.class) > 0) {
				entityVo.setBarrierStatus(1);
			} else {
				entityVo.setBarrierStatus(0);
			}
		}
		return courseVos;
	}
}
