package com.xczhihui.cloudClass.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.course.vo.StudentManagerVo;
import com.xczhihui.common.dao.HibernateDao;

@Repository("studentManagerDao")
public class StudentManagerDao extends HibernateDao<StudentManagerVo> {
	public Page<StudentManagerVo> findstudentsInfoPage(
			StudentManagerVo searchVo, int currentPage, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"select m.id,m.`name`,st.id,st.`name` "
						+ ",tm.id,tm.`name`,c.id ,c.grade_name,g.id "
						+ ",g.`name`,g.student_count,g.curriculum_time,g.stop_time "
						+ "from apply_r_grade_course ar,oe_grade g,oe_course c "
						+ ",oe_menu m,score_type st,teach_method tm,oe_apply a "
						+ ",oe_user u where ar.grade_id = g.id AND "
						+ "ar.course_id = c.id AND ar.apply_id = a.id "
						+ "AND c.menu_id = m.id AND c.course_type_id = st.id "
						+ "AND c.courseType = tm.id AND a.user_id = u.id ");
		return null;
	}
}
