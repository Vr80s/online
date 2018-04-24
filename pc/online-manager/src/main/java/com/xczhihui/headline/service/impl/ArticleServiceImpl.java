package com.xczhihui.headline.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.enums.HeadlineType;
import com.xczhihui.headline.dao.ArticleDao;
import com.xczhihui.headline.service.ArticleService;
import com.xczhihui.headline.vo.ArticleTypeVo;
import com.xczhihui.headline.vo.ArticleVo;
import com.xczhihui.headline.vo.TagVo;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDao articleDao;

    @Override
    public Page<ArticleVo> findArticlePage(ArticleVo articleVo, int currentPage,
                                           int pageSize) {
        Page<ArticleVo> articlePage = articleDao.findArticlePage(articleVo,
                currentPage, pageSize);
        articlePage.getItems().forEach(article -> {
            String typeId = article.getTypeId();
            if (StringUtils.isNotBlank(typeId)) {
                Integer articleId = article.getId();
                if (HeadlineType.DJZL.getCode().equals(typeId)) {
                    article.setDoctorAuthor(articleDao.getDoctorAuthorByArticleId(articleId));
                } else if (HeadlineType.MYBD.getCode().equals(typeId)) {
                    article.setReportDoctor(articleDao.getReportAuthorByArticleId(articleId));
                }
            }
        });
        return articlePage;
    }

    @Override
    public List<ArticleTypeVo> getArticleTypes() {

        return articleDao.findEntitiesByJdbc(ArticleTypeVo.class,
                "select * from article_type where status = 1",
                new HashMap<String, Object>());
    }

    @Override
    public List<TagVo> getTags() {
        return articleDao.findEntitiesByJdbc(TagVo.class,
                "select * from oe_bxs_tag where status = 1",
                new HashMap<String, Object>());
    }

    @Override
    public void addArticle(ArticleVo articleVo) {
        List<ArticleVo> vos = articleDao.findEntitiesByJdbc(
                ArticleVo.class,
                "select * from oe_bxs_article where title = '"
                        + articleVo.getTitle() + "'",
                new HashMap<String, Object>(8));
        if (vos != null && vos.size() > 0) {
            throw new IllegalArgumentException("文章名称已存在！");
        }

        String sql = "INSERT INTO oe_bxs_article (title,content,type_id,img_path,user_id) VALUES "
                + "(:title,:content,:typeId,:imgPath,:userId) ";

        KeyHolder kh = new GeneratedKeyHolder();

        articleDao.getNamedParameterJdbcTemplate().update(sql,
                new BeanPropertySqlParameterSource(articleVo), kh);
        articleVo.setId(kh.getKey().intValue());

        String sqlupdate = "UPDATE oe_bxs_article SET sort =:sort WHERE id =:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", articleVo.getId());
        param.put("sort", articleVo.getId());
        articleDao.getNamedParameterJdbcTemplate().update(sqlupdate, param);

    }

    @Override
    public void addArticleTag(ArticleVo articleVo) {
        String[] tagIds = articleVo.getTagId().split(",");
        for (String tagId : tagIds) {
            String sql = "INSERT INTO article_r_tag (id,tag_id,article_id) VALUES "
                    + "(:id,:tagId,:articleId);";
            Map<String, Object> param = new HashMap<>();
            param.put("id", UUID.randomUUID().toString().replace("-", ""));
            param.put("tagId", tagId);
            param.put("articleId", articleVo.getId());
            articleDao.getNamedParameterJdbcTemplate().update(sql, param);
        }
    }

    @Override
    public ArticleVo findArticleById(Integer id) {
        return articleDao.findEntitiesByJdbc(ArticleVo.class,
                "select * from oe_bxs_article where id = " + id,
                new HashMap<String, Object>()).get(0);
    }

    @Override
    public void updateArticle(ArticleVo articleVo) {
        List<ArticleVo> vos = articleDao.findEntitiesByJdbc(ArticleVo.class,
                "select * from oe_bxs_article where id!=" + articleVo.getId()
                        + " and title = '" + articleVo.getTitle() + "'",
                new HashMap<String, Object>());
        if (vos != null && vos.size() > 0) {
            throw new IllegalArgumentException("文章名称已存在！");
        }

        String sql = "UPDATE oe_bxs_article SET title =:title ,content =:content,type_id =:typeId,img_path =:imgPath,user_id =:userId WHERE id =:id";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", articleVo.getId());
        param.put("title", articleVo.getTitle());
        param.put("content", articleVo.getContent());
        param.put("typeId", articleVo.getTypeId());
        param.put("imgPath", articleVo.getImgPath());
        param.put("userId", articleVo.getUserId());
        articleDao.getNamedParameterJdbcTemplate().update(sql, param);

        String deleteSql = "DELETE FROM article_r_tag WHERE article_id=:articleId";
        Map<String, Object> paramDel = new HashMap<String, Object>();
        paramDel.put("articleId", articleVo.getId());
        articleDao.getNamedParameterJdbcTemplate().update(deleteSql, paramDel);

        addArticleTag(articleVo);
    }

    @Override
    public void deletes(String[] ids) {
        for (String id : ids) {
            // 删除文章
            String deleteSqlArt = "UPDATE oe_bxs_article SET is_delete = TRUE WHERE id=:id";
            Map<String, Object> paramDelArt = new HashMap<>(1);
            paramDelArt.put("id", id);
            articleDao.getNamedParameterJdbcTemplate().update(deleteSqlArt,
                    paramDelArt);
            // oe_bxs_appraise删除评论
            String deleteSqlAppraise = "UPDATE oe_bxs_appraise SET is_delete = TRUE WHERE article_id=:articleId";
            Map<String, Object> paramDelAppraise = new HashMap<>(1);
            paramDelAppraise.put("articleId", id);
            articleDao.getNamedParameterJdbcTemplate().update(
                    deleteSqlAppraise, paramDelAppraise);
        }
    }

    @Override
    public void updateStatus(Integer id) {
        ArticleVo vo = findArticleById(id);
        if (vo.getStatus() != null && 1 == vo.getStatus()) {
            vo.setStatus(0);
        } else {
            vo.setStatus(1);
        }

        String sql = "UPDATE oe_bxs_article SET status =:status  WHERE id =:id";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", vo.getId());
        param.put("status", vo.getStatus());
        articleDao.getNamedParameterJdbcTemplate().update(sql, param);

    }

    @Override
    public void addPreArticle(ArticleVo articleVo) {
        String sql = "INSERT INTO oe_bxs_preview_article (title,content,type_id,img_path,user_id) VALUES "
                + "(:title,:content,:typeId,:imgPath,:userId) ";

        KeyHolder kh = new GeneratedKeyHolder();
        articleDao.getNamedParameterJdbcTemplate().update(sql,
                new BeanPropertySqlParameterSource(articleVo), kh);
        articleVo.setId(kh.getKey().intValue());
    }


    @Override
    public void updateRecommendSort(Integer id, Integer recommendSort, String recommendTime) {
        String sql = "UPDATE oe_bxs_article SET sort =:recommendSort,recommend_time =:recommendTime WHERE  id =:id";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        param.put("recommendSort", recommendSort);
        param.put("recommendTime", recommendTime);
        articleDao.getNamedParameterJdbcTemplate().update(sql, param);
    }
}
