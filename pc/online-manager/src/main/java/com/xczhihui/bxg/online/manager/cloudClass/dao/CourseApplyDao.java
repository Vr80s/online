package com.xczhihui.bxg.online.manager.cloudClass.dao;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.*;
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
public class CourseApplyDao extends HibernateDao<CourseApplyInfo>{
	 public Page<CourseApplyInfo> findCloudClassCoursePage(CourseApplyInfo courseApplyInfo, int pageNumber, int pageSize){
		 Map<String, Object> paramMap = new HashMap<String, Object>();
		 StringBuilder sql = new StringBuilder("SELECT cai.*,IF(cai.`status`=2,1,cai.`status`) ostatus,ou.name userName FROM `course_apply_info` cai LEFT JOIN `oe_user` ou ON cai.`user_id`=ou.`id` WHERE cai.`is_delete`=0 ");
		 if (courseApplyInfo.getTitle() != null) {
			 paramMap.put("title", "%" + courseApplyInfo.getTitle() + "%");
			 sql.append("and cai.title like :title ");
		 }

		 if (courseApplyInfo.getStatus() != null) {
			 paramMap.put("status", courseApplyInfo.getStatus());
			 sql.append("and cai.status = :status ");
		 }

		 sql.append(" ORDER BY ostatus ASC,cai.`create_time` DESC");

		 Page<CourseApplyInfo> courseApplyInfos = this.findPageBySQL(sql.toString(), paramMap, CourseApplyInfo.class, pageNumber, pageSize);

		 return courseApplyInfos;
//		 }
	 }

    public CourseApplyInfo findCourseApplyAndMenuById(Integer id) {
		DetachedCriteria dc = DetachedCriteria.forClass(CourseApplyInfo.class);
		dc.add(Restrictions.eq("id", id));
		CourseApplyInfo courseApplyInfo = this.findEntity(dc);
		if(courseApplyInfo == null){
			return null;
		}
		DetachedCriteria menudc = DetachedCriteria.forClass(Menu.class);
		menudc.add(Restrictions.eq("id", Integer.valueOf(courseApplyInfo.getCourseMenu())));
		Menu menu = this.findEntity(menudc);
		if(menu!=null){
			courseApplyInfo.setCourseMenu(menu.getName());
		}
		return courseApplyInfo;
    }

	public CourseApplyInfo findCourseApplyById(Integer id) {
		DetachedCriteria dc = DetachedCriteria.forClass(CourseApplyInfo.class);
		dc.add(Restrictions.eq("id", id));
		CourseApplyInfo courseApplyInfo = this.findEntity(dc);

		return courseApplyInfo;
	}

	public List<CourseApplyInfo> getCourseByCollectionId(Integer cid) {
		String sql = "select \n" +
				"  cai.`id`,cai.`title`,cai.`lecturer`,cai.`price`,cai.`status` \n" +
				"from\n" +
				"  `course_apply_info` cai \n" +
				"  left join `collection_course_apply` cc\n" +
				"  on cai.id = cc.`course_apply_id`\n" +
				"where cai.`is_delete` = 0\n" +
				"and cc.`collection_apply_id`=:cid \n" +
				"order by cai.`collection_course_sort`";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("cid",cid);
		return this.getNamedParameterJdbcTemplate().query(sql,params, BeanPropertyRowMapper.newInstance(CourseApplyInfo.class));
	}

	public List<CourseApplyInfo> getCourseDeatilsByCollectionId(Integer id) {
		String sql = "select \n" +
				"  cai.* \n" +
				"from\n" +
				"  `course_apply_info` cai \n" +
				"  left join `collection_course_apply` cc\n" +
				"  on cai.id = cc.`course_apply_id`\n" +
				"where cai.`is_delete` = 0\n" +
				"and cc.`collection_apply_id`=:cid \n" +
				"order by cai.`collection_course_sort`";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("cid",id);
		return this.getNamedParameterJdbcTemplate().query(sql,params, BeanPropertyRowMapper.newInstance(CourseApplyInfo.class));
	}
}
