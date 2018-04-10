package com.xczhihui.bbs.dao;

import java.util.List;
import java.util.Map;

import com.xczhihui.bbs.vo.BBSPostVo;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;

@Repository("bbsPostDao")
public class BBSPostDao extends SimpleHibernateDao {

	public Page<BBSPostVo> list(Map<String, Object> params, int page, int size) {
		String sql = "select p.id as id, p.title as title, p.init_time as initTime, p.content as content, u.name as nickname,"
				+ " p.top as top, p.good as good, p.hot as hot, p.is_delete as deleted"
				+ " from quark_posts p, oe_user u"
				+ " where p.`user_id` = u.`id` and (:isDelete is null OR p.is_delete = :isDelete) "
				+ " and (:id is null OR p.id = :id)  and (:nickname is null OR u.name like :nickname)"
				+ " and (:title is null OR p.title like :title)";
		if (params.get("type") != null) {
			sql = sql + "and p." + params.get("type") + " is true";
		}
		sql = sql + " order by p.init_time desc";
		return super.findPageBySQL(sql, params, BBSPostVo.class, page, size);
	}

	public int changeGoodStatus(List<Integer> ids) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("ids", ids);
		return this.getNamedParameterJdbcTemplate().update(
				"UPDATE quark_posts SET good = !good WHERE id IN (:ids)",
				mapSqlParameterSource);
	}

	public int changeTopStatus(List<Integer> ids) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("ids", ids);
		return this.getNamedParameterJdbcTemplate().update(
				"UPDATE quark_posts SET top = !top WHERE id IN (:ids)",
				mapSqlParameterSource);
	}

	public int changeDeleteStatus(List<Integer> ids, boolean deleted) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("ids", ids).addValue("deleted", deleted);
		return this
				.getNamedParameterJdbcTemplate()
				.update("UPDATE quark_posts SET is_delete = :deleted WHERE is_delete != :deleted AND id IN (:ids)",
						mapSqlParameterSource);
	}

	public int changeHotStatus(List<Integer> ids) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("ids", ids);
		return this.getNamedParameterJdbcTemplate().update(
				"UPDATE quark_posts SET hot = !hot WHERE id IN (:ids)",
				mapSqlParameterSource);
	}

	public void reduceReplyCount(List<Integer> ids) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("ids", ids);
		String sql = "UPDATE quark_posts p SET p.reply_count = p.reply_count - 1"
				+ " WHERE p.reply_count > 1 AND p.id IN (SELECT r.posts_id FROM quark_reply r WHERE r.id IN (:ids))";
		this.getNamedParameterJdbcTemplate().update(sql, mapSqlParameterSource);
	}

	public void addReplyCount(List<Integer> ids) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("ids", ids);
		String sql = "UPDATE quark_posts p SET p.reply_count = p.reply_count + 1"
				+ " WHERE p.id IN (SELECT r.posts_id FROM quark_reply r WHERE r.id IN (:ids))";
		this.getNamedParameterJdbcTemplate().update(sql, mapSqlParameterSource);
	}

	public int countByLabelId(int labelId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("labelId", labelId);
		return this
				.getNamedParameterJdbcTemplate()
				.queryForObject(
						"SELECT count(*) FROM quark_posts WHERE label_id = :labelId AND is_delete = FALSE",
						mapSqlParameterSource, Integer.class);
	}
}
