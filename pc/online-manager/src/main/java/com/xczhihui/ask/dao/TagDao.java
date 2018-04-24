package com.xczhihui.ask.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.AskTag;
import com.xczhihui.ask.vo.TagVo;

/**
 * 标签DAO层类
 *
 * @author 王高伟
 * @create 2016-10-13 18:03:39
 */
@Repository
public class TagDao extends SimpleHibernateDao {

	public List<TagVo> findTag(TagVo tagVo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"select oat.name,oat.menu_id from oe_ask_tag oat where is_delete = 0 and status = 1");

		if (tagVo.getMenuId() != null && !"".equals(tagVo.getMenuId())) {
			sql.append(" and oat.menu_id = :menuId ");
			paramMap.put("menuId", tagVo.getMenuId());
		}

		sql.append(" order by oat.seq desc");
		List<TagVo> list = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), paramMap,
				BeanPropertyRowMapper.newInstance(TagVo.class));

		return list;
	}

	public Page<TagVo> findTagPage(TagVo searchVo, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		paramMap.put("menuId", searchVo.getMenuId());
		params.put("menuId", searchVo.getMenuId());
		StringBuilder sql = new StringBuilder();
		sql.append(" select * from oe_ask_tag where is_delete =0 and menu_id = :menuId  ");

		if (searchVo.getName() != null && !"".equals(searchVo.getName())) {
			sql.append(" and name like :name ");
			paramMap.put("name", "%" + searchVo.getName() + "%");
		}
		sql.append(" order by status desc, seq desc");

		Page<TagVo> TagVos = this.findPageBySQL(sql.toString(), paramMap,
				TagVo.class, currentPage, pageSize);

		for (TagVo temp : TagVos.getItems()) {
			String quesCountSql = " SELECT count(1) as quesCount from (SELECT find_in_set('"
					+ temp.getName()
					+ "',tags) as a FROM oe_ask_question where ment_id = :menuId) q WHERE a >0";
			List<TagVo> templist = this.findEntitiesByJdbc(TagVo.class,
					quesCountSql, params);
			temp.setQuesCount(templist.get(0).getQuesCount());

			if (searchVo.getMonthSort() != null
					&& !"".equals(searchVo.getMonthSort())) {
				if ("30".equals(searchVo.getMonthSort())) {
					String quesCountSqlOneMonth = " SELECT count(1) as citesCount from (SELECT find_in_set('"
							+ temp.getName()
							+ "',tags) as a FROM oe_ask_question where ment_id = :menuId and DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= create_time) q WHERE a >0";
					List<TagVo> templistOneMonth = this.findEntitiesByJdbc(
							TagVo.class, quesCountSqlOneMonth, params);
					temp.setCitesCount(templistOneMonth.get(0).getCitesCount());
				} else if ("60".equals(searchVo.getMonthSort())) {
					String quesCountSqlOneMonth = " SELECT count(1) as citesCount from (SELECT find_in_set('"
							+ temp.getName()
							+ "',tags) as a FROM oe_ask_question where ment_id = :menuId and DATE_SUB(CURDATE(), INTERVAL 60 DAY) <= create_time) q WHERE a >0";
					List<TagVo> templistOneMonth = this.findEntitiesByJdbc(
							TagVo.class, quesCountSqlOneMonth, params);
					temp.setCitesCount(templistOneMonth.get(0).getCitesCount());
				} else if ("180".equals(searchVo.getMonthSort())) {
					String quesCountSqlOneMonth = " SELECT count(1) as citesCount from (SELECT find_in_set('"
							+ temp.getName()
							+ "',tags) as a FROM oe_ask_question where ment_id = :menuId and DATE_SUB(CURDATE(), INTERVAL 60 DAY) <= create_time) q WHERE a >0";
					List<TagVo> templistOneMonth = this.findEntitiesByJdbc(
							TagVo.class, quesCountSqlOneMonth, params);
					temp.setCitesCount(templistOneMonth.get(0).getCitesCount());
				}

			} else {
				String quesCountSqlOneMonth = " SELECT count(1) as citesCount from (SELECT find_in_set('"
						+ temp.getName()
						+ "',tags) as a FROM oe_ask_question where ment_id = :menuId and DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= create_time) q WHERE a >0";
				List<TagVo> templistOneMonth = this.findEntitiesByJdbc(
						TagVo.class, quesCountSqlOneMonth, params);
				temp.setCitesCount(templistOneMonth.get(0).getCitesCount());
			}

			if (searchVo.getQuesSort() != null
					&& !"".equals(searchVo.getQuesSort())) {
				temp.setSortType("question");
			} else if (searchVo.getCitesSort() != null
					&& !"".equals(searchVo.getCitesSort())) {
				temp.setSortType("use");
			}

		}

		if (searchVo.getQuesSort() != null
				&& !"".equals(searchVo.getQuesSort())) {

			Collections.sort(TagVos.getItems());
		} else if (searchVo.getCitesSort() != null
				&& !"".equals(searchVo.getCitesSort())) {
			Collections.sort(TagVos.getItems());
		}

		return TagVos;

	}

	public String deleteById(String id) {
		// TODO Auto-generated method stub
		String s = "";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String deleteSql = " update oe_ask_tag set is_delete=1 where  id = :id ";
		this.getNamedParameterJdbcTemplate().update(deleteSql, params);
		s = "删除成功";
		return s;
	}

	public AskTag updateDirectionUp(String id) {
		String sql = " select * from oe_ask_tag where seq > (select seq from oe_ask_tag where id=:id ) and  is_delete=0 and status=1 and menu_id=(select menu_id from oe_ask_tag where id=:id ) order by seq asc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<AskTag> askTags = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(AskTag.class));
		if (askTags.size() > 0) {
			return askTags.get(0);
		}
		return null;
	}

	public AskTag updateDirectionDown(String id) {
		String sql = " select * from oe_ask_tag where seq < (select seq from oe_ask_tag where id=:id )  and  is_delete=0 and status=1 and menu_id=(select menu_id from oe_ask_tag where id=:id ) order by seq desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<AskTag> askTags = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(AskTag.class));
		if (askTags.size() > 0) {
			return askTags.get(0);
		}
		return null;
	}
}
