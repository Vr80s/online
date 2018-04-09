package com.xczhihui.headline.dao;

import java.util.HashMap;
import java.util.Map;

import com.xczhihui.headline.vo.TagVo;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;

/**
 * 标签DAO层类
 *
 * @author 王高伟
 * @create 2016-10-13 18:03:39
 */
@Repository
public class BxsTagDao extends SimpleHibernateDao {

	public Page<TagVo> findTagPage(TagVo searchVo, int currentPage, int pageSize) {

		if (searchVo.getLatelyNum() == null || !(searchVo.getLatelyNum() > 0)) {// 默认取最近一个月的
			searchVo.setLatelyNum(1);
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		String sql = " select obt.id,obt.name,obt.status,obt.create_person,obt.create_time from oe_bxs_tag obt where 1 = 1 ";

		if (searchVo.getName() != null && !"".equals(searchVo.getName())) {
			sql += " and obt.name like :name ";
			paramMap.put("name", "%" + searchVo.getName() + "%");

		}

		if (searchVo.getSortType() != null && searchVo.getSortType() == 1) {
			sql += " order by (select count(1) from article_r_tag t where t.tag_id = obt.id  and exists(select 1 from oe_bxs_article t2 where t.article_id = t2.id and t2.is_delete = 0 ) ) desc,obt.create_time desc ";
		} else if (searchVo.getSortType() != null
				&& searchVo.getSortType() == 2) {
			sql += " order by "
					+ " (SELECT "
					+ "	count( "
					+ "		CASE "
					+ "		WHEN FORMAT(t.create_time, 'yyyy-mm-dd') >= FORMAT(DATE_SUB(now(), INTERVAL - "
					+ searchVo.getLatelyNum()
					+ " MONTH),'yyyy-mm-dd') THEN "
					+ "			1 "
					+ "		ELSE "
					+ "			NULL "
					+ "		END "
					+ "	) "
					+ " FROM "
					+ "	article_r_tag t "
					+ " WHERE "
					+ "	t.tag_id = obt.id "
					+ " and exists(select 1 from oe_bxs_article t2 where t.article_id = t2.id and t2.is_delete = 0 )) "
					+ " desc,obt.create_time desc ";
		} else {
			sql += " order by obt.create_time desc ";
		}

		Page<TagVo> tagVos = this.findPageBySQL(sql.toString(), paramMap,
				TagVo.class, currentPage, pageSize);

		for (int i = 0; i < tagVos.getItems().size(); i++) {
			sql = " select count(1) from article_r_tag t where t.tag_id = ?  and exists(select 1 from oe_bxs_article t2 where t.article_id = t2.id and t2.is_delete = 0 ) ";
			tagVos.getItems()
					.get(i)
					.setArticleCnt(
							this.queryForInt(sql, new Object[] { tagVos
									.getItems().get(i).getId() }));

			sql = " SELECT "
					+ "	count( "
					+ "		CASE "
					+ "		WHEN FORMAT(t.create_time, '%Y-%m-%d') >= FORMAT(DATE_ADD(now(), INTERVAL - "
					+ searchVo.getLatelyNum()
					+ " MONTH),'%Y-%m-%d') THEN "
					+ "			1 "
					+ "		ELSE "
					+ "			NULL "
					+ "		END "
					+ "	) "
					+ " FROM "
					+ "	article_r_tag t "
					+ " WHERE "
					+ "	t.tag_id = ? "
					+ "	 and exists(select 1 from oe_bxs_article t2 where t.article_id = t2.id and t2.is_delete = 0 ) ";
			tagVos.getItems()
					.get(i)
					.setArticleCntLately(
							this.queryForInt(sql, new Object[] { tagVos
									.getItems().get(i).getId() }));
		}
		return tagVos;
	}
}
