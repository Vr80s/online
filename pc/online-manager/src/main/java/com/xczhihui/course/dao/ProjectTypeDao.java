package com.xczhihui.course.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Project;
import com.xczhihui.common.dao.HibernateDao;

@Repository("projectTypeDao")
public class ProjectTypeDao extends HibernateDao<Project> {

	public Page<Project> findProjectPage(Project project, int pageNumber,
			int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"select * from oe_project where is_delete=0 ");
		if (project.getType() != null && !"".equals(project.getType())) {
			sql.append(" and type = :type ");
			paramMap.put("type", project.getType());
		}
		sql.append(" order by  status desc,sort asc ");
		return this.findPageBySQL(sql.toString(), paramMap, Project.class,
				pageNumber, pageSize);
	}

	public int getMaxSort() {
		String sql = " select ifnull(max(sort),0) as maxSort from oe_project where  is_delete=0  ";
		Map<String, Object> result = this.getNamedParameterJdbcTemplate()
				.queryForMap(sql, new HashMap<String, Object>());
		return Integer.parseInt(result.get("maxSort") != null ? String
				.valueOf(result.get("maxSort")) : "0");
	}

	public Project findById(String parseInt) {
		StringBuilder sql = new StringBuilder(
				"select * from oe_project where id=:id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", parseInt);
		List<Project> menus = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(Project.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	public String deleteById(String id) {
		String s = "";
		// String sql =
		// "select * from menu_courseType where course_type_id = :id and is_delete = 0 ";
		String sql = " SELECT " + "	* " + " FROM " + "	oe_project op, "
				+ " WHERE " + " op.is_delete = 0 " + " AND op.id = :id";

		/*
		 * Map<String,Object> params=new HashMap<String,Object>();
		 * params.put("id", id); List<MenuCourseType> query =
		 * this.getNamedParameterJdbcTemplate
		 * ().query(sql,params,BeanPropertyRowMapper
		 * .newInstance(MenuCourseType.class));
		 * 
		 * String sql2 =
		 * "select * from oe_course where course_type_id = :id and is_delete = 0"
		 * ; Map<String,Object> params3=new HashMap<String,Object>();
		 * params3.put("id", id); List<Course> query2 =
		 * this.getNamedParameterJdbcTemplate
		 * ().query(sql2,params3,BeanPropertyRowMapper
		 * .newInstance(Course.class));
		 * 
		 * String deleteSql =
		 * " update score_type set is_delete=1 where  id = :id ";
		 * Map<String,Object> params2=new HashMap<String,Object>();
		 * params2.put("id", id); if(query.size() > 0 || query2.size() > 0){ s =
		 * "此课程类别已被使用，不能被删除!"; }else{
		 * this.getNamedParameterJdbcTemplate().update(deleteSql, params2); s=
		 * "删除成功"; }
		 */
		String deleteSql = " update oe_project set is_delete=1 where  id = :id ";
		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("id", id);
		this.getNamedParameterJdbcTemplate().update(deleteSql, params2);
		s = "删除成功";
		return s;
	}

}
