package com.xczhihui.headline.dao;

import java.util.HashMap;
import java.util.Map;

import com.xczhihui.common.dao.HibernateDao;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.headline.vo.ArticleVo;

/**
 * 文章管理DAO
 * 
 * @author yxd
 *
 */
@Repository("articleDao")
public class ArticleDao extends HibernateDao<ArticleVo> {

	public Page<ArticleVo> findCloudClassCoursePage(ArticleVo articleVo,
			int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// StringBuilder sql =new
		// StringBuilder("SELECT article.* ,group_concat(tag.name) as tagName ,group_concat(tag.id) as tagId,arttype.`name` as typeName,u.name as author from oe_bxs_article article,article_r_tag art ,oe_bxs_tag tag , article_type arttype, user u where article.id = art.article_id and art.tag_id = tag.id and article.type_id =arttype.id and article.user_id = u.id ");
		// StringBuilder sql =new StringBuilder("SELECT \n" +
		// "  article.*,\n" +
		// "  GROUP_CONCAT(tag.name) AS tagName,\n" +
		// "  GROUP_CONCAT(tag.id) AS tagId,\n" +
		// "  arttype.`name` AS typeName,\n" +
		// "  article.user_id AS author ,\n" +
		// "  md.`name` doctorAuthor ,\n" +
		// "  GROUP_CONCAT(md1.name) AS reportDoctor " +
		// "FROM\n" +
		// "  article_r_tag art,\n" +
		// "  oe_bxs_tag tag,\n" +
		// "  article_type arttype,\n" +
		// "  USER u ,\n" +
		// "  oe_bxs_article article\n" +
		// "  LEFT JOIN `medical_doctor_author_article` mdaa\n" +
		// "  ON mdaa.`article_id` = article.`id`\n" +
		// "  LEFT JOIN `medical_doctor` md\n" +
		// "  ON mdaa.`doctor_id` = md.`id`\n" +
		// " LEFT JOIN `medical_doctor_report` mdr\n" +
		// "  ON mdr.`article_id` = article.`id`\n" +
		// "  LEFT JOIN `medical_doctor` md1\n" +
		// "  ON mdr.`doctor_id` = md1.`id`  "+
		// "  WHERE article.id = art.article_id \n" +
		// "  AND art.tag_id = tag.id \n" +
		// "  AND article.type_id = arttype.id \n");
		StringBuilder sql = new StringBuilder(
				"SELECT \n"
						+ "  article.*,\n"
						+ "  GROUP_CONCAT(tag.name) AS tagName,\n"
						+ "  GROUP_CONCAT(tag.id) AS tagId,\n"
						+ "  arttype.`name` AS typeName,\n"
						+ "  article.`user_id` author,\n"
						+ "  (SELECT md.name FROM `medical_doctor_author_article` mdaa JOIN medical_doctor md ON mdaa.`doctor_id` = md.`id` WHERE mdaa.`article_id` = article.`id`) doctorAuthor,\n"
						+ "  (SELECT GROUP_CONCAT(md.name) FROM `medical_doctor_report` mdr JOIN medical_doctor md ON mdr.`doctor_id` = md.`id` WHERE mdr.`article_id` = article.`id`) reportDoctor\n"
						+ "FROM\n" + "  oe_bxs_article article,\n"
						+ "  article_r_tag art,\n" + "  oe_bxs_tag tag,\n"
						+ "  article_type arttype\n"
						+ "WHERE article.id = art.article_id \n"
						+ "  AND art.tag_id = tag.id \n"
						+ "  AND article.type_id = arttype.id ");

		if (articleVo.getTitle() != null) {
			sql.append(" and article.title like :title");
			paramMap.put("title", "%" + articleVo.getTitle() + "%");
		}
		if (articleVo.getTypeId() != null) {
			sql.append(" and article.type_id =:typeId");
			paramMap.put("typeId", articleVo.getTypeId());
		}
		if (articleVo.getStatus() != null) {
			sql.append(" and article.status =:status ");
			paramMap.put("status", articleVo.getStatus());
		}
		if (articleVo.getIsRecommend() != null) {
			sql.append(" and article.is_recommend =:isRecommend ");
			paramMap.put("isRecommend", articleVo.getIsRecommend());
		}

		if (articleVo.getStartTime() != null) {
			sql.append(" and article.create_time >=:startTime");
			paramMap.put("startTime", articleVo.getStartTime());
		}

		if (articleVo.getStopTime() != null) {
			sql.append(" and article.create_time <=:stopTime");
			paramMap.put("stopTime", articleVo.getStopTime());
		}

		sql.append(" GROUP BY article.id order by create_time desc ");

		Page<ArticleVo> page = this.findPageBySQL(sql.toString(), paramMap,
				ArticleVo.class, currentPage, pageSize);
		return page;
	}

}
