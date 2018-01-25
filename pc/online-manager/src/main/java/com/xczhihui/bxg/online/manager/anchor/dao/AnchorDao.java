package com.xczhihui.bxg.online.manager.anchor.dao;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.CourseAnchor;
import com.xczhihui.bxg.online.common.domain.CourseApplyInfo;
import com.xczhihui.bxg.online.common.domain.CourseApplyResource;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 云课堂课程管理DAO
 * 
 * @author yxd
 *
 */
@Repository
public class AnchorDao extends HibernateDao<CourseAnchor>{
	 public Page<CourseAnchor> findCourseAnchorPage(CourseAnchor courseAnchor, int pageNumber, int pageSize){
		 Map<String, Object> paramMap = new HashMap<String, Object>();
		 StringBuilder sql = new StringBuilder("SELECT ca.id,\n" +
				 "  ou.`name`,\n" +
				 "  ca.`type`,\n" +
				 "  ca.`vod_divide`,\n" +
				 "  ca.`live_divide`,\n" +
				 "  ca.`offline_divide`,\n" +
				 "  ca.`gift_divide`,\n" +
				 "  ca.`status`  \n" +
				 "FROM\n" +
				 "  `course_anchor` ca \n" +
				 "  JOIN `oe_user` ou \n" +
				 "    ON ca.`user_id` = ou.`id` \n" +
				 "WHERE ca.`deleted` = 0 \n" +
				 "  AND ou.`is_delete` = 0 \n" +
				 "    ");
		 if (courseAnchor.getName() != null) {
			 paramMap.put("name", "%" + courseAnchor.getName() + "%");
			 sql.append("and ou.name like :name ");
		 }
		 if (courseAnchor.getType()!= null) {
			 paramMap.put("type", courseAnchor.getType() );
			 sql.append("and ca.type like :type ");
		 }
		 sql.append(" ORDER BY ca.`create_time` DESC");

		 Page<CourseAnchor> courseAnchors = this.findPageBySQL(sql.toString(), paramMap, CourseAnchor.class, pageNumber, pageSize);

		 return courseAnchors;
	 }


	public CourseApplyInfo findCourseApplyById(Integer id) {
		DetachedCriteria dc = DetachedCriteria.forClass(CourseApplyInfo.class);
		dc.add(Restrictions.eq("id", id));
		CourseApplyInfo courseApplyInfo = this.findEntity(dc);

		return courseApplyInfo;
	}

	public CourseAnchor findCourseAnchorById(Integer id) {
	 	String sql = "SELECT \n" +
				"  ou.`name`,\n" +
				"  ca.`type`,\n" +
				"  ca.`vod_divide` vodDivide,\n" +
				"  ca.`live_divide` liveDivide,\n" +
				"  ca.`offline_divide` offlineDivide,\n" +
				"  ca.`gift_divide` giftDivide,\n" +
				"  ca.`status` \n" +
				"FROM\n" +
				"  `course_anchor` ca \n" +
				"  JOIN `oe_user` ou \n" +
				"    ON ca.`user_id` = ou.`id` \n" +
				"WHERE ca.`deleted` = 0 \n" +
				"  AND ou.`is_delete` = 0 \n" +
				"  AND ca.id=:id  \n" +
				"  ORDER BY ca.`create_time`\n" ;
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("id",id);
		return this.findEntityByJdbc(CourseAnchor.class,sql,m);
	}
}
