package com.xczhihui.course.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.MenuCourseType;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.course.vo.ScoreTypeVo;
import com.xczhihui.common.dao.HibernateDao;

//@Repository("scoreTypeDao")
public class CopyOfScoreTypeDao extends HibernateDao<ScoreType> {

	public Page<ScoreTypeVo> findScoreTypePage(ScoreTypeVo menuVo,
			int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"select ds.*,(select count(1) from score_type where  is_delete=0 and status=1 ) as courseCount,(select group_concat(name) from score_type where  is_delete=0 and status=1 and name <> '全部') as childMenuNames from (select * from score_type where  is_delete=0 and name<>'全部')ds where 1=1 ");
		if (menuVo.getName() != null && !"".equals(menuVo.getName())) {
			sql.append(" and ds.name like :name ");
			paramMap.put("name", "%" + menuVo.getName() + "%");
		}
		if (menuVo.getCreatePerson() != null
				&& !"".equals(menuVo.getCreatePerson())) {
			sql.append(" and ds.create_person like :create_person ");
			paramMap.put("create_person", "%" + menuVo.getCreatePerson() + "%");
		}
		if (menuVo.getTime_start() != null) {
			sql.append(" and ds.create_time >=:create_time_start");
			paramMap.put("create_time_start", menuVo.getTime_start());
		}
		if (menuVo.getTime_end() != null) {
			sql.append(" and ds.create_time <=:create_time_end");
			paramMap.put("create_time_end", menuVo.getTime_end());
		}
		// 课程类别所属课程类型：1中医学习管理2身心修养管理3经方要略管理 ---20170720---yuruixin
		if (menuVo.getStatus() != null) {
			sql.append(" and ds.type =:type");
			paramMap.put("type", menuVo.getStatus());
		} else {
			sql.append(" and ds.type =:type");// 若无该参数，则取1
			paramMap.put("type", "1");
		}
		sql.append(" order by sort desc ");
		Page<ScoreTypeVo> pages = this.findPageBySQL(sql.toString(), paramMap,
				ScoreTypeVo.class, pageNumber, pageSize);

		String sqlIsUsed = "select count(1) as menuCount from menu_coursetype where course_type_id =:id ";
		for (ScoreTypeVo temp : pages.getItems()) {
			paramMap.put("id", temp.getId());
			List<ScoreTypeVo> res = this.findEntitiesByJdbc(ScoreTypeVo.class,
					sqlIsUsed, paramMap);
			if (res.get(0).getMenuCount() > 0) {
				temp.setMenuCount(res.get(0).getMenuCount());
			} else {
				temp.setMenuCount(0);
			}
		}

		return pages;
	}

	/**
	 * 获取最大的
	 * 
	 * @return
	 */
	public int getMaxSort() {
		String sql = " select ifnull(max(sort),0) as maxSort from score_type where  is_delete=0  ";
		Map<String, Object> result = this.getNamedParameterJdbcTemplate()
				.queryForMap(sql, new HashMap<String, Object>());
		return Integer.parseInt(result.get("maxSort") != null ? String
				.valueOf(result.get("maxSort")) : "0");
	}

	/**
	 * 通过id进行查找
	 * 
	 * @param parseInt
	 * @return
	 */
	public ScoreType findById(String parseInt) {
		StringBuilder sql = new StringBuilder(
				"select * from score_type where id=:id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", parseInt);
		List<ScoreType> menus = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(ScoreType.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	/**
	 * 查找实体
	 * 
	 * @param searchEntity
	 * @return
	 */
	public ScoreType findByNotEqId(ScoreType searchEntity) {
		StringBuilder sql = new StringBuilder(
				"select * from score_type where id <> :id and name=:name and  is_delete =0");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", searchEntity.getId());
		params.put("name", searchEntity.getName());
		List<ScoreType> menus = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(ScoreType.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	/**
	 * 删除数据
	 * 
	 * @param _ids
	 */
	public void deletes(String[] _ids) {
		if (_ids.length > 0) {
			StringBuilder sql = new StringBuilder(
					" update score_type set is_delete=1 where  id IN (");
			for (int i = 0; i < _ids.length; i++) {
				if (i != 0) {
					sql.append(",");
				}
				sql.append("'" + _ids[i] + "'");
			}
			sql.append(")");
			this.getNamedParameterJdbcTemplate().update(sql.toString(),
					new HashMap<String, Object>());
		}

	}

	/**
	 * 课程类别管理上移
	 * 
	 * @param parseInt
	 * @return
	 */
	public ScoreType updateScoreTypeUp(String parseInt) {
		String sql = " select * from score_type where sort > (select sort from score_type where id=:id ) and  is_delete=0 order by sort asc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", parseInt);
		List<ScoreType> menus = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(ScoreType.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	public ScoreType updateScoreTypeDown(String parseInt) {
		String sql = " select * from score_type where sort < (select sort from score_type where id=:id )  and is_delete=0  order by sort desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", parseInt);
		List<ScoreType> menus = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(ScoreType.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	/**
	 * 通过id进行查询
	 * 
	 * @param parseInt
	 * @return
	 */
	public ScoreType searchById(String parseInt) {
		StringBuilder sql = new StringBuilder(
				"select * from score_type where id=:id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", parseInt);
		List<ScoreType> menus = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(ScoreType.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	/**
	 * 批量删除数据
	 * 
	 * @param id
	 * @return
	 */
	public String deleteById(String id) {
		String s = "";
		// String sql =
		// "select * from menu_courseType where course_type_id = :id and is_delete = 0 ";
		String sql = " SELECT " + "	* " + " FROM " + "	oe_menu t, "
				+ "	menu_courseType t2 " + " WHERE " + "	t2.menu_id = t.id "
				+ " AND t.is_delete = 0 " + " AND t2.is_delete = 0 "
				+ " AND t2.course_type_id = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<MenuCourseType> query = this
				.getNamedParameterJdbcTemplate()
				.query(sql, params,
						BeanPropertyRowMapper.newInstance(MenuCourseType.class));

		String sql2 = "select * from oe_course where course_type_id = :id and is_delete = 0";
		Map<String, Object> params3 = new HashMap<String, Object>();
		params3.put("id", id);
		List<Course> query2 = this.getNamedParameterJdbcTemplate().query(sql2,
				params3, BeanPropertyRowMapper.newInstance(Course.class));

		String deleteSql = " update score_type set is_delete=1 where  id = :id ";
		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("id", id);
		if (query.size() > 0 || query2.size() > 0) {
			s = "此课程类别已被使用，不能被删除!";
		} else {
			this.getNamedParameterJdbcTemplate().update(deleteSql, params2);
			s = "删除成功";
		}
		return s;
	}

}
