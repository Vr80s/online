package com.xczhihui.headline.dao;

import java.util.HashMap;
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

    public Page<ArticleVo> findCloudClassCoursePage(ArticleVo articleVo,
                                                    int currentPage, int pageSize) {
        Map<String, Object> paramMap = new HashMap<>();
        StringBuilder sql = new StringBuilder(
                "SELECT \n"
                        + "  article.id,article.title,article.content,article.type_id,article.img_path,article.banner_path,article.browse_sum,\n"
                        + "  article.praise_sum,article.comment_sum,article.banner_status,article.status,article.is_delete,article.user_id,\n"
                        + "  article.create_time,article.praise_login_names,article.update_time,article.url,article.recommend_time, \n"
                        + "  GROUP_CONCAT(tag.name) AS tagName,\n"
                        + "  GROUP_CONCAT(tag.id) AS tagId,\n"
                        + "  arttype.`name` AS typeName,\n"
                        + "  article.`user_id` author,\n"
                        + " if(article.recommend_time< now(),0,article.sort) sort, "
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
        if (articleVo.getStartTime() != null) {
            sql.append(" and article.create_time >=:startTime");
            paramMap.put("startTime", articleVo.getStartTime());
        }
        if (articleVo.getStopTime() != null) {
            sql.append(" and article.create_time <=:stopTime");
            paramMap.put("stopTime", articleVo.getStopTime());
        }
        sql.append(" GROUP BY article.id order by article.status desc,sort desc, create_time desc ");

        return this.findPageBySQL(sql.toString(), paramMap,
                ArticleVo.class, currentPage, pageSize);
    }

}
