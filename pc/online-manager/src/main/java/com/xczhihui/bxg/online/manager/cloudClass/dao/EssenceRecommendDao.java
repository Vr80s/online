package com.xczhihui.bxg.online.manager.cloudClass.dao;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("essenceRecommendDao")
public class EssenceRecommendDao extends HibernateDao<Course>{

	public Page<CourseVo> findEssenceRecCoursePage(CourseVo courseVo,
			int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		Map<String,Object> paramMap=new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("");
		sql.append(" select oc.id as id,oc.grade_name as courseName,oc.current_price*10 as currentPrice,oc.status as status,"
				+ "oc.essence_sort as essenceSort,oc.multimedia_type as multimediaType,ou.name as lecturerName,");
		sql.append(" IF(oc.type is not null,1,if(oc.multimedia_type=1,2,3)) as type, ");
		sql.append(" oc.live_status as  lineState ");

		sql.append(" from oe_course oc, oe_user ou ");
		sql.append(" where oc.user_lecturer_id = ou.id  and oc.is_delete=0  order by oc.status desc, oc.essence_sort desc ");

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
