package com.xczhihui.headline.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.headline.vo.ArticleVo;

/**
 * 文章管理DAO
 *
 * @author yxd
 */
@Repository("articleDao")
public class ArticleDao extends HibernateDao<ArticleVo> {

    public Page<ArticleVo> findArticlePage(ArticleVo articleVo,
                                           int currentPage, int pageSize) {
        Map<String, Object> paramMap = new HashMap<>(8);
        StringBuilder sql = new StringBuilder(" SELECT " +
                "    article.id,article.title,article.content,article.type_id,article.img_path,article.banner_path,article.browse_sum," +
                "    article.praise_sum,article.comment_sum,article.banner_status,article.status,article.is_delete,article.user_id," +
                "    article.create_time,article.praise_login_names,article.update_time,article.url,article.recommend_time," +
                "    arttype.`name` AS typeName, article.`user_id` author, if(article.recommend_time< now(),0,article.sort) sort," +
                "    art_tag.tagName as tagName, art_tag.tagId as tagId, article.user_created as userCreated" +
                "    FROM oe_bxs_article article" +
                "       left join (" +
                "           select GROUP_CONCAT(t.name) as tagName, rt.article_id, group_concat(t.id) as tagId " +
                "               from article_r_tag rt , oe_bxs_tag t" +
                "           where rt.tag_id = t.id group by rt.article_id" +
                "          ) art_tag on article.id = art_tag.article_id" +
                "      left join article_type arttype on article.type_id = arttype.id " +
                " where is_delete = false");
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
        if (articleVo.getStartTime() != null) {
            sql.append(" and article.create_time >=:startTime");
            paramMap.put("startTime", articleVo.getStartTime());
        }
        if (articleVo.getStopTime() != null) {
            sql.append(" and article.create_time <=:stopTime");
            paramMap.put("stopTime", articleVo.getStopTime());
        }
        sql.append(" order by article.status desc,sort desc, create_time desc ");

        return this.findPageBySQL(sql.toString(), paramMap,
                ArticleVo.class, currentPage, pageSize);
    }

    public String getDoctorAuthorByArticleId(Integer articleId) {
        String sql = "SELECT group_concat(md.name) " +
                " FROM `medical_doctor_special_column` mdsc " +
                " JOIN medical_doctor md ON mdsc.`doctor_id` = md.`id`" +
                " WHERE mdsc.`doctor_id` = md.`id` and mdsc.article_id = ?";
        List<Object> params = new ArrayList<>(1);
        params.add(articleId);
        Object name = this.findUniqueBySql(sql, params);
        return name != null ? name.toString() : null;
    }

    public String getReportAuthorByArticleId(Integer articleId) {
        String sql = "SELECT group_concat(md.name) " +
                " FROM `medical_doctor_report` mdr " +
                " JOIN medical_doctor md ON mdr.`doctor_id` = md.`id`" +
                " WHERE mdr.`doctor_id` = md.`id` and mdr.article_id = ?";
        List<Object> params = new ArrayList<>(1);
        params.add(articleId);
        Object name = this.findUniqueBySql(sql, params);
        return name != null ? name.toString() : null;
    }
}
