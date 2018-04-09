package com.xczhihui.cloudClass.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.cloudClass.vo.CourseDescriptionVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CourseDescriptionDao extends SimpleHibernateDao {

	public List<Map<String, Object>> getDesList(
			CourseDescriptionVo courseDescriptionVo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(" SELECT " + "	t.id, "
				+ "	t.create_person createPerson, "
				+ "	t.create_time createTime, " + "	t.is_delete isDelete, "
				+ "	t.status, " + "	t.sort, " + "	t.course_id courseId, "
				+ "	ifnull(t.course_title,'') courseTitle, "
				+ "	ifnull(t.course_content,'') courseContent, "
				+ "	t.preview, "
				+ "	ifnull(t.course_title_preview,'') courseTitlePreview, "
				+ "	ifnull(t.course_content_preview,'') courseContentPreview "
				+ " FROM " + "	oe_course_description t where t.is_delete = 0 ");
		sql.append(" and t.course_id = :courseId");
		paramMap.put("courseId", courseDescriptionVo.getCourseId());

		sql.append(" order by t.sort asc");
		List<Map<String, Object>> ms = this.getNamedParameterJdbcTemplate()
				.queryForList(sql.toString(), paramMap);
		return ms;
	}
}
