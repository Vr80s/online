package com.xczhihui.cloudClass.dao;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.common.dao.HibernateDao;
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
public class CourseApplyDao extends HibernateDao<CourseApplyInfo> {
	public Page<CourseApplyInfo> findCloudClassCoursePage(
			CourseApplyInfo courseApplyInfo, int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"SELECT cai.id,\n"
						+ "  cai.`title`,\n"
						+ "  cai.`img_path` imgPath,\n"
						+ "  cai.`course_form` courseForm,\n"
						+ "  cai.`price`*10 as price,\n"
						+ "  cai.lecturer,\n"
						+ "  cai.create_time,cai.status,cai.review_time,cai.collection,IF(cai.`status`=2,1,cai.`status`) ostatus,"
						+ "om.`name` menuName,ca.name userName FROM `course_apply_info` cai JOIN `oe_menu` om ON om.id=cai.`course_menu` "
						+ " LEFT JOIN `oe_user` ou ON cai.`user_id`=ou.`id` "
						+ " left join course_anchor as ca on cai.user_id = ca.user_id WHERE cai.`is_delete`=0 ");
		if (courseApplyInfo.getTitle() != null) {
			paramMap.put("title", "%" + courseApplyInfo.getTitle() + "%");
			sql.append("and cai.title like :title ");
		}
		if (courseApplyInfo.getLecturer() != null) {
			paramMap.put("lecturer", "%" + courseApplyInfo.getLecturer() + "%");
			sql.append("and ca.name like :lecturer ");
		}
		if (courseApplyInfo.getCourseMenu() != null) {
			paramMap.put("course_menu", courseApplyInfo.getCourseMenu());
			sql.append("and cai.course_menu = :course_menu ");
		}
		if (courseApplyInfo.getCourseForm() != null) {
			paramMap.put("course_form", courseApplyInfo.getCourseForm());
			sql.append("and cai.course_form = :course_form ");
		}
		if (courseApplyInfo.getMultimediaType() != null) {
			paramMap.put("multimedia_type", courseApplyInfo.getMultimediaType());
			sql.append("and cai.multimedia_type = :multimedia_type ");
		}

		if (courseApplyInfo.getFree() != null) {
			if (courseApplyInfo.getFree()) {
				sql.append("and cai.price = 0 ");
			} else {
				sql.append("and cai.price != 0 ");
			}
		}
		if (courseApplyInfo.getStatus() != null) {
			paramMap.put("status", courseApplyInfo.getStatus());
			sql.append("and cai.status = :status ");
		}

		if (courseApplyInfo.getStartTime() != null) {
			sql.append(" and cai.create_time >=:startTime");
			paramMap.put("startTime", courseApplyInfo.getStartTime());
		}

		if (courseApplyInfo.getEndTime() != null) {
			sql.append(" and cai.create_time <=:stopTime");
			paramMap.put("stopTime", courseApplyInfo.getEndTime());
		}
		sql.append(" ORDER BY ostatus ASC,cai.`create_time` DESC");

		Page<CourseApplyInfo> courseApplyInfos = this.findPageBySQL(
				sql.toString(), paramMap, CourseApplyInfo.class, pageNumber,
				pageSize);

		return courseApplyInfos;
	}

	public CourseApplyInfo findCourseApplyAndMenuById(Integer id) {
		DetachedCriteria dc = DetachedCriteria.forClass(CourseApplyInfo.class);
		dc.add(Restrictions.eq("id", id));
		CourseApplyInfo courseApplyInfo = this.findEntity(dc);
		if (courseApplyInfo == null) {
			return null;
		}
		DetachedCriteria menudc = DetachedCriteria.forClass(Menu.class);
		menudc.add(Restrictions.eq("id",
				Integer.valueOf(courseApplyInfo.getCourseMenu())));
		Menu menu = this.findEntity(menudc);
		if (menu != null) {
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
		String sql = "select \n"
				+ "  cai.`id`,cai.`title`,cai.`lecturer`,cai.`price`*10 price,cai.`status` \n"
				+ "from\n" + "  `course_apply_info` cai \n"
				+ "  left join `collection_course_apply` cc\n"
				+ "  on cai.id = cc.`course_apply_id`\n"
				+ "where cai.`is_delete` = 0\n"
				+ "and cc.`collection_apply_id`=:cid \n"
				+ "order by cc.`collection_course_sort`";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cid", cid);
		return this.getNamedParameterJdbcTemplate().query(sql, params,
				BeanPropertyRowMapper.newInstance(CourseApplyInfo.class));
	}

	public List<CourseApplyInfo> getCourseDeatilsByCollectionId(Integer id) {
		String sql = "select \n"
				+ "  cai.*,cc.collection_course_sort collectionCourseSort \n"
				+ "from\n" + "  `course_apply_info` cai \n"
				+ "  left join `collection_course_apply` cc\n"
				+ "  on cai.id = cc.`course_apply_id`\n"
				+ "where cai.`is_delete` = 0\n"
				+ "and cc.`collection_apply_id`=:cid \n"
				+ "order by cc.`collection_course_sort`";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cid", id);
		return this.getNamedParameterJdbcTemplate().query(sql, params,
				BeanPropertyRowMapper.newInstance(CourseApplyInfo.class));
	}

	public Page<CourseApplyResource> findCourseApplyResourcePage(
			CourseApplyResource courseApplyResource, int currentPage,
			int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"SELECT \n"
						+ "  car.`id`,\n"
						+ "  car.`title`,\n"
						+ "  car.`resource`,\n"
						+ "  car.`multimedia_type` multimediaType,\n"
						+ "  car.`create_time` createTime, "
						+ "  ou.`login_name` loginName,\n"
						+ "  ou.`name` userName,\n"
						+ "  car.`is_delete` deleted \n"
						+ "FROM\n"
						+ "  `course_apply_resource` car LEFT JOIN `oe_user` ou ON car.`user_id`=ou.`id` \n"
						+ "WHERE 1=1 ");
		if (courseApplyResource.getTitle() != null) {
			paramMap.put("title", "%" + courseApplyResource.getTitle() + "%");
			sql.append("and car.title like :title ");
		}
		if (courseApplyResource.getUserName() != null) {
			paramMap.put("name", "%" + courseApplyResource.getUserName() + "%");
			sql.append("and ou.`name` like :name ");
		}
		if (courseApplyResource.getDeleted() != null) {
			paramMap.put("is_delete", courseApplyResource.getDeleted());
			sql.append("and car.is_delete = :is_delete ");
		}
		// car.`is_delete` = 0
		sql.append(" ORDER BY car.`create_time` DESC");

		Page<CourseApplyResource> courseApplyResources = this.findPageBySQL(
				sql.toString(), paramMap, CourseApplyResource.class,
				currentPage, pageSize);

		return courseApplyResources;
	}

	public Page<CourseApplyInfo> findCoursePageByUserId(
			CourseApplyInfo courseApplyInfo, int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"SELECT cai.id,\n"
						+ "  cai.`title`,\n"
						+ "  oc.`id` as courseId,\n"
						+ "  cai.`start_time` as startTime,\n"
						+ "  cai.`multimedia_type` as multimediaType,\n"
						+ "  cai.`sale` as sale,\n"
						+ "  cai.`status` as applyStatus,\n"
						+ "  oc.`status` as status,\n"
						+ "  oc.`release_time` as releaseTime,\n"
						+ "  cai.`img_path` imgPath,\n"
						+ "  cai.`course_form` courseForm,\n"
						+ "  cai.`price`*10 as price,\n"
						+ "  oc.lecturer as lecturer,\n"
						+ "  cai.create_time,cai.review_time,cai.collection,"
						+ "om.`name` as menuName FROM `course_apply_info` cai left JOIN `oe_menu` om ON om.id=cai.`course_menu` "
						+ " LEFT JOIN `oe_course` as oc ON cai.id = oc.apply_id WHERE cai.`is_delete`=0 ");

		String userId = courseApplyInfo.getUserId();
		String title = courseApplyInfo.getTitle();
		Integer applyStatus = courseApplyInfo.getApplyStatus();
		Integer courseForm = courseApplyInfo.getCourseForm();
		Integer status = courseApplyInfo.getStatus();
		if (userId != null && !userId.equals("")) {
			paramMap.put("userId", userId);
			sql.append("and cai.user_id = :userId ");
		}
		if (title != null) {
			paramMap.put("title", "%" + title + "%");
			sql.append("and cai.title like :title ");
		}
		if (applyStatus != null) {
			paramMap.put("applyStatus", applyStatus);
			sql.append("and cai.status = :applyStatus ");
		}
		if (courseForm != null) {
			paramMap.put("courseForm", courseForm);
			sql.append("and cai.course_form = :courseForm ");
		}
		if (status != null) {
			paramMap.put("status", status);
			sql.append("and oc.status = :status ");
		}

		sql.append(" ORDER BY oc.`release_time` DESC");
		Page<CourseApplyInfo> courseApplyInfos = this.findPageBySQL(
				sql.toString(), paramMap, CourseApplyInfo.class, pageNumber,
				pageSize);

		return courseApplyInfos;
	}
}
