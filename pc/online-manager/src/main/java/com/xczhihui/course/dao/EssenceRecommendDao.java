package com.xczhihui.course.dao;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.course.vo.CourseVo;
import com.xczhihui.common.dao.HibernateDao;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("essenceRecommendDao")
public class EssenceRecommendDao extends HibernateDao<Course> {

	public Page<CourseVo> findEssenceRecCoursePage(CourseVo courseVo,
			int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"SELECT \n"
						+ "  oc.id AS id,\n"
						+ "  oc.grade_name AS courseName,\n"
						+ "  oc.direct_id directId,\n"
						+ "  oc.class_template AS classTemplate,\n"
						+ "  om.name AS xMenuName,\n"
						+ "  oc.multimedia_type multimediaType,\n"
						+ "  oc.address,\n"
						+ "  oc.course_length AS courseLength,\n"
						+ "  oc.learnd_count AS learndCount,\n"
						+ "  oc.city AS realCitys,\n"
						+ "  oc.create_time AS createTime,\n"
						+ "  oc.status AS STATUS,\n"
						+ "  oc.is_free AS isFree, \n"
						+ "  oc.current_price AS currentPrice,\n"
						+ "  oc.description AS description,\n"
						+ "  oc.menu_id AS menuId,\n"
						+ "  oc.course_type_id AS courseTypeId,\n"
						+ "  oc.courseType AS courseType,\n"
						+ "  oc.is_recommend,\n"
						+ "  oc.recommend_sort,\n"
						+ "  oc.release_time,\n"
						+ "  oc.sort_update_time as sortUpdateTime,\n"
						+ "  oc.course_type AS serviceType,\n"
						+ "  oc.user_lecturer_id AS userLecturerId,\n"
						+ "  ou.`name` lecturerName,\n"
						+ "  oc.`lecturer`,\n"
						+
						" if(oc.live_status = 2,if(DATE_SUB(now(),INTERVAL 30 MINUTE)>=oc.start_time,6,if(  "
						+ "			    DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now() < oc.start_time,"
						+ "    4,if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now() < oc.start_time,5,oc.live_status))),oc.live_status) "
						+ "			     AS liveStatus, "
						+ "  oc.type as type \n"
						+ "FROM\n"
						+ "  oe_course oc \n"
						+ "  LEFT JOIN oe_menu om \n"
						+ "    ON om.id = oc.menu_id \n"
						+ "  LEFT JOIN oe_user ou\n"
						+ "    ON ou.id=oc.user_lecturer_id where oc.is_delete = 0 ");

		// 学科分类
		if (courseVo.getMenuId() != null && courseVo.getMenuId() != -1) {
			paramMap.put("menuId", courseVo.getMenuId());
			sql.append("and oc.menu_id = :menuId ");

		}
		// 课程名称
		if (courseVo.getCourseName() != null) {
			paramMap.put("courseName", "%" + courseVo.getCourseName() + "%");
			sql.append("and oc.grade_name like :courseName ");
		}
		// 直播类型
		if (courseVo.getType() != null) {
			paramMap.put("type", courseVo.getType());
			sql.append("and oc.type = :type ");
		}
		// 直播状态
		if (courseVo.getLiveStatus() != null) {
			paramMap.put("liveStatus", courseVo.getLiveStatus());
			sql.append("and oc.live_status = :liveStatus ");
		}
		// 媒体类型
		if (courseVo.getMultimediaType() != null) {
			paramMap.put("multimediaType", courseVo.getMultimediaType());
			sql.append("and oc.multimedia_type = :multimediaType ");
		}
		sql.append(" order by oc.status desc,oc.recommend_sort desc,oc.release_time desc ");

		System.out.println(sql);
		Page<CourseVo> courseVos = this.findPageBySQL(sql.toString(), paramMap,
				CourseVo.class, pageNumber, pageSize);
		return courseVos;
	}

}
