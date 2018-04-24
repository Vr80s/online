package com.xczhihui.headline.dao;

import java.util.HashMap;
import java.util.Map;

import com.xczhihui.headline.vo.ArticleVo;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;

@Repository
public class BannerDao extends SimpleHibernateDao {

	public Page<ArticleVo> findBannerPage(ArticleVo articleVo, int currentPage,
			int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder("SELECT" + " article.*, "
				+ " group_concat(tag. NAME) AS tagName,"
				+ " group_concat(tag.id) AS tagId,"
				+ " arttype.`name` AS typeName," + " u. NAME AS author"
				+ " FROM" + " oe_bxs_article article," + " article_r_tag art,"
				+ " oe_bxs_tag tag," + " article_type arttype," + " USER u"
				+ " where article.id = art.article_id"
				+ " AND art.tag_id = tag.id"
				+ " AND article.type_id = arttype.id"
				+ " AND article.user_id = u.id" + " AND article.is_recommend=1"
				+ " GROUP BY" + " article.id");
		sql.append(" order by article.sort desc");
		Page<ArticleVo> page = this.findPageBySQL(sql.toString(), paramMap,
				ArticleVo.class, currentPage, pageSize);
		return page;
	}

}
