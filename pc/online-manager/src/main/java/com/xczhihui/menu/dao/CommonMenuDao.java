package com.xczhihui.menu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.ask.dao.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.MenuCourseType;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.ask.vo.TagVo;
import com.xczhihui.cloudClass.vo.MenuVo;
import com.xczhihui.common.dao.HibernateDao;

@Repository("commonMenuDao")
public class CommonMenuDao extends HibernateDao<Menu> {
	@Autowired
	TagDao tagDao;

	public Page<MenuVo> findCloudClassMenuPage(MenuVo menuVo, int pageNumber,
			int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"select om.id,om.name,om.remark,om.create_person,om.create_time,om.status,om.sort"
						+ " from oe_menu om  where om.is_delete = 0 and om.id !=0 ");
		if (menuVo.getName() != null && !"".equals(menuVo.getName())) {
			sql.append(" and om.name like :name ");
			paramMap.put("name", "%" + menuVo.getName() + "%");
		}
		if (menuVo.getCreatePerson() != null
				&& !"".equals(menuVo.getCreatePerson())) {
			sql.append(" and om.create_person like :create_person ");
			paramMap.put("create_person", "%" + menuVo.getCreatePerson() + "%");
		}
		if (menuVo.getTime_start() != null) {
			sql.append(" and om.create_time >=:create_time_start");
			paramMap.put("create_time_start", menuVo.getTime_start());
		}
		if (menuVo.getTime_end() != null) {
			sql.append(" and om.create_time <=:create_time_end");
			paramMap.put("create_time_end", menuVo.getTime_end());
		}
		sql.append(" order by om.sort desc ");
		Page<MenuVo> ms = this.findPageBySQL(sql.toString(), paramMap,
				MenuVo.class, pageNumber, pageSize);

		for (MenuVo m : ms.getItems()) {
			paramMap = new HashMap<String, Object>();
			paramMap.put("id", m.getId());
			String typesql = "select t3.name from oe_menu t1,menu_coursetype t2,score_type t3 where  t3.status=1 and t3.id<>0 and t1.id=t2.menu_id and t2.course_type_id=t3.id and t1.id=:id ";
			List<MenuVo> query = this.getNamedParameterJdbcTemplate().query(
					typesql, paramMap,
					BeanPropertyRowMapper.newInstance(MenuVo.class));
			String type = "";
			for (MenuVo string : query) {
				type += ("，" + string.getName());
			}
			if (type.length() > 0) {
				type = type.substring(1);
			}
			m.setChildMenuNames(type);

			// 放入包含的课程
			String courses = "select grade_name as courseName from oe_course where is_delete=0 and menu_id=:id and status=1 ";
			List<MenuVo> res = this.getNamedParameterJdbcTemplate().query(
					courses, paramMap,
					BeanPropertyRowMapper.newInstance(MenuVo.class));

			String courseName = "";

			for (MenuVo string : res) {
				courseName += ("，" + string.getCourseName());
			}
			if (courseName.length() > 0) {
				courseName = courseName.substring(1);
			}
			m.setCourseName(courseName);
			m.setCourseCount(res.size());

			// 查询标签数量
			TagVo tagVo = new TagVo();
			tagVo.setMenuId(m.getId());
			List<TagVo> tagVoList = tagDao.findTag(tagVo);
			m.setTagCount(tagVoList.size());

		}

		return ms;
	}

	public Integer getMaxSort() {
		String sql = " select ifnull(max(sort),0) as maxSort from oe_menu where level=1 and is_delete=0  ";
		Map<String, Object> result = this.getNamedParameterJdbcTemplate()
				.queryForMap(sql, new HashMap<String, Object>());
		return Integer.parseInt(result.get("maxSort") != null ? String
				.valueOf(result.get("maxSort")) : "1");
	}

	public Integer getMaxYunSort() {
		String sql = " select ifnull(max(yun_sort),0) as maxYunSort from oe_menu where level=1 and is_delete=0  ";
		Map<String, Object> result = this.getNamedParameterJdbcTemplate()
				.queryForMap(sql, new HashMap<String, Object>());
		return Integer.parseInt(result.get("maxYunSort") != null ? String
				.valueOf(result.get("maxYunSort")) : "1");
	}

	public Integer getMaxBoSort() {
		String sql = " select ifnull(max(bo_sort),0) as maxBoSort from oe_menu where level=1 and is_delete=0  ";
		Map<String, Object> result = this.getNamedParameterJdbcTemplate()
				.queryForMap(sql, new HashMap<String, Object>());
		return Integer.parseInt(result.get("maxBoSort") != null ? String
				.valueOf(result.get("maxBoSort")) : "1");
	}

	public Integer getMinSort() {
		String sql = " select ifnull(min(sort),0) as minSort from oe_menu where level=1 and is_delete=0  ";
		Map<String, Object> result = this.getNamedParameterJdbcTemplate()
				.queryForMap(sql, new HashMap<String, Object>());
		return Integer.parseInt(result.get("minSort") != null ? String
				.valueOf(result.get("minSort")) : "1");
	}

	public Integer getMinYunSort() {
		String sql = " select ifnull(min(yun_sort),0) as minYunSort from oe_menu where level=1 and is_delete=0  ";
		Map<String, Object> result = this.getNamedParameterJdbcTemplate()
				.queryForMap(sql, new HashMap<String, Object>());
		return Integer.parseInt(result.get("minYunSort") != null ? String
				.valueOf(result.get("minYunSort")) : "1");
	}

	public Integer getMinBoSort() {
		String sql = " select ifnull(min(bo_sort),0) as minBoSort from oe_menu where level=1 and is_delete=0  ";
		Map<String, Object> result = this.getNamedParameterJdbcTemplate()
				.queryForMap(sql, new HashMap<String, Object>());
		return Integer.parseInt(result.get("maxBoSort") != null ? String
				.valueOf(result.get("maxBoSort")) : "1");
	}

	public Menu updateDirectionUp(Integer id) {
		String sql = " select * from oe_menu where sort > (select sort from oe_menu where id=:id ) and  is_delete=0 order by sort asc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<Menu> menus = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(Menu.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	public Menu updateDirectionUp(Integer parentNumber, Integer id,
			Integer level) {
		String sql = " select * from oe_menu where sort<(select sort from oe_menu where id=:id ) and level=:level and is_delete=0 and number like :number order by sort desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("level", level);
		params.put("number", parentNumber);
		List<Menu> menus = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(Menu.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	public Menu updateDirectionDown(Integer id) {
		String sql = " select * from oe_menu where sort < (select sort from oe_menu where id=:id )  and is_delete=0  order by sort desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<Menu> menus = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(Menu.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	public Menu updateDirectionDown(Integer parentNumber, Integer id,
			Integer level) {
		String sql = " select * from oe_menu where sort>(select sort from oe_menu where id=:id ) and level=:level and is_delete=0  and number like :number order by sort asc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("level", level);
		params.put("number", parentNumber + "%");
		List<Menu> menus = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(Menu.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	public Integer getMaxNumber(Integer level) {
		String sql = " select ifnull(max(number),0) as maxNumber from oe_menu where level=:level ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("level", level);
		Map<String, Object> result = this.getNamedParameterJdbcTemplate()
				.queryForMap(sql, params);
		return Integer.parseInt(result.get("maxNumber") != null ? String
				.valueOf(result.get("maxNumber")) : "0");
	}

	public Integer getMaxNumber(Integer number, Integer level) {
		String sql = " select ifnull(max(number),0) as maxNumber from oe_menu where level=:level and number like :number  ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("level", level);
		params.put("number", number + "%");
		Map<String, Object> result = this.getNamedParameterJdbcTemplate()
				.queryForMap(sql, params);
		return Integer.parseInt(result.get("maxNumber") != null ? String
				.valueOf(result.get("maxNumber")) : "0");
	}

	public Integer getMaxSort(Integer number, Integer level) {
		String sql = " select ifnull(max(sort),0) as maxSort from oe_menu where level=:level  and number like :number  ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("level", level);
		params.put("number", number + "%");
		Map<String, Object> result = this.getNamedParameterJdbcTemplate()
				.queryForMap(sql, params);
		return Integer.parseInt(result.get("maxSort") != null ? String
				.valueOf(result.get("maxSort")) : "0");
	}

	public void deletes(String[] ids) {
		if (ids.length > 0) {
			StringBuilder sql = new StringBuilder(
					" update oe_menu set is_delete=1 where level=1 and  id in(");
			for (int i = 0; i < ids.length; i++) {
				if (i != 0) {
					sql.append(",");
				}
				sql.append("'" + ids[i] + "'");
			}
			sql.append(" ) ");
			this.getNamedParameterJdbcTemplate().update(sql.toString(),
					new HashMap<String, Object>());
		}
	}

	public Menu find(Menu menu) {
		StringBuilder sql = new StringBuilder(
				"select * from oe_menu where id=:id and name=:name");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", menu.getId());
		params.put("name", menu.getName());
		List<Menu> menus = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(Menu.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	public Menu findById(Integer id) {
		StringBuilder sql = new StringBuilder(
				"select * from oe_menu where id=:id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<Menu> menus = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(Menu.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	public Menu findByNotEqId(Menu menu) {
		StringBuilder sql = new StringBuilder(
				"select * from oe_menu where id <> :id and name=:name and level=:level and is_delete =0");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", menu.getId());
		params.put("name", menu.getName());
		params.put("level", menu.getLevel());
		List<Menu> menus = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(Menu.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	public void deletesByNumber(Integer parentNumber) {
		StringBuilder sql = new StringBuilder(
				" update oe_menu set is_delete=1 where  number like :parentNumber");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentNumber", "%" + parentNumber + "%");
		this.getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	public List<Menu> findChildrenByNumber(Integer number, Integer level) {
		String sql = "select * from oe_menu where number like :number and level=:level and is_delete=0 and name<>'全部' order by sort asc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("number", number + "%");
		params.put("level", level);
		return this.getNamedParameterJdbcTemplate().query(sql.toString(),
				params, BeanPropertyRowMapper.newInstance(Menu.class));
	}

	/**
	 * 查询课程类别表
	 * 
	 * @return
	 */
	public List<ScoreType> findScoreType(String menuId) {
		String sql = "select * from score_type where is_delete = 0 and status = 1 and name <> '全部' order by sort desc ";
		List<ScoreType> sts = this.getNamedParameterJdbcTemplate().query(
				sql.toString(),
				BeanPropertyRowMapper.newInstance(ScoreType.class));

		String courseTypeSql = "select * from menu_coursetype where menu_id = :menu_id and is_delete = 0 ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("menu_id", menuId);
		List<MenuCourseType> cts = this.getNamedParameterJdbcTemplate().query(
				courseTypeSql, params,
				BeanPropertyRowMapper.newInstance(MenuCourseType.class));

		for (ScoreType st : sts) {
			for (MenuCourseType mt : cts) {
				if (st.getId().equals(mt.getCourseTypeId())) {
					st.setChecked("true");
					break;
				}
			}
		}
		return sts;
	}

	/**
	 * 将取消选中的数据从中间表中删除
	 * 
	 * @param id
	 * @param menuId
	 * @return
	 */
	public void removeMenuCourseType(Integer menuId) {
		String delSql = "delete from menu_coursetype  where  menu_id = :menu_id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("menu_id", menuId);

		// return
		// this.getNamedParameterJdbcTemplate().query(delSql,params,BeanPropertyRowMapper.newInstance(MenuCourseType.class));
		this.getNamedParameterJdbcTemplate().update(delSql, params);

	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 */
	public String deleteById(String id) {
		String s = "";
		/*
		 * String sql = "select * from menu_courseType where menu_id = :id";
		 * Map<String,Object> params=new HashMap<String,Object>();
		 * params.put("id", id); List<MenuCourseType> query =
		 * this.getNamedParameterJdbcTemplate
		 * ().query(sql,params,BeanPropertyRowMapper
		 * .newInstance(MenuCourseType.class));
		 */

		TagVo tagVo = new TagVo();
		tagVo.setMenuId(id);
		List<TagVo> tagVoList = tagDao.findTag(tagVo);

		String sql2 = "select * from oe_course where is_delete = 0 and menu_id = :id";
		Map<String, Object> params3 = new HashMap<String, Object>();
		params3.put("id", id);
		List<Course> query2 = this.getNamedParameterJdbcTemplate().query(sql2,
				params3, BeanPropertyRowMapper.newInstance(Course.class));

		String deleteSql = " update oe_menu set is_delete=1 where  id = :id ";
		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("id", id);
		if (query2.size() > 0 || tagVoList.size() > 0) {
			s = "此学科已被使用,不能被删除!";
		} else {
			removeMenuCourseType(Integer.valueOf(id));
			this.getNamedParameterJdbcTemplate().update(deleteSql, params2);
			s = "删除成功";
		}
		return s;
	}
}
