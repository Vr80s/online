package com.xczhihui.headline.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorWritings;
import com.xczhihui.bxg.online.common.domain.MedicalWritings;
import com.xczhihui.headline.dao.ArticleDao;
import com.xczhihui.headline.dao.WritingDao;
import com.xczhihui.headline.service.WritingService;
import com.xczhihui.headline.vo.ArticleTypeVo;
import com.xczhihui.headline.vo.ArticleVo;
import com.xczhihui.headline.vo.TagVo;
import com.xczhihui.headline.vo.WritingVo;

@Service("writingService")
public class WritingServiceImpl extends OnlineBaseServiceImpl implements
        WritingService {

    @Autowired
    ArticleDao articleDao;

    @Autowired
    WritingDao writingDao;

    @Override
    public Page<MedicalWritings> findWritingsPage(WritingVo searchVo,
                                                  int currentPage, int pageSize) {
        return writingDao.findCloudClassCoursePage(
                searchVo, currentPage, pageSize);
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
    public void addWriting(WritingVo writingVo) {
        /**
         * 新增文章
         */
        ArticleVo articleVo = new ArticleVo();
        articleVo.setTitle(writingVo.getTitle());
        articleVo.setImgPath(writingVo.getImgPath());
        articleVo.setContent(writingVo.getContent());
        articleVo.setUserId(writingVo.getUserId());

        List<ArticleVo> vos = articleDao.findEntitiesByJdbc(
                ArticleVo.class,
                "select * from oe_bxs_article where title = '"
                        + articleVo.getTitle() + "'",
                new HashMap<String, Object>());

        String sql = "INSERT INTO oe_bxs_article (title,content,type_id,img_path,user_id) VALUES "
                + "(:title,:content,:typeId,:imgPath,:userId) ";

        KeyHolder kh = new GeneratedKeyHolder();
        articleDao.getNamedParameterJdbcTemplate().update(sql,
                new BeanPropertySqlParameterSource(articleVo), kh);
        articleVo.setId(kh.getKey().intValue());

        String sqlupdate = "UPDATE oe_bxs_article SET sort =:sort WHERE id =:id";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", articleVo.getId());
        param.put("sort", articleVo.getId());
        articleDao.getNamedParameterJdbcTemplate().update(sqlupdate, param);
        /**
         * 新增著作
         */
        String id = UUID.randomUUID().toString().replace("-", "");
        writingVo.setId(id);
        writingVo.setArticleId(articleVo.getId() + "");
        String sqlWritingVo = "INSERT INTO medical_writings (id,author,title,buy_link,article_id, img_path, remark) VALUES "
                + "(:id,:author,:title,:buyLink,:articleId, :imgPath, :content) ";
        writingDao.getNamedParameterJdbcTemplate().update(sqlWritingVo,
                new BeanPropertySqlParameterSource(writingVo), kh);

    }

    @Override
    public void addArticleTag(ArticleVo articleVo) {
        String[] tagIds = articleVo.getTagId().split(",");
        for (String tagId : tagIds) {
            String sql = "INSERT INTO article_r_tag (id,tag_id,article_id) VALUES "
                    + "(:id,:tagId,:articleId);";
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", UUID.randomUUID().toString().replace("-", ""));
            param.put("tagId", tagId);
            param.put("articleId", articleVo.getId());
            articleDao.getNamedParameterJdbcTemplate().update(sql, param);
        }
    }

    @Override
    public WritingVo findWritingById(String id) {
        return writingDao.findEntitiesByJdbc(WritingVo.class,
                "select * from medical_writings where id = '" + id + "'",
                new HashMap<String, Object>()).get(0);
    }

    @Override
    public void updateWriting(WritingVo writingVo) {

        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(Integer.parseInt(writingVo.getArticleId()));
        articleVo.setTitle(writingVo.getTitle());
        articleVo.setImgPath(writingVo.getImgPath());
        articleVo.setContent(writingVo.getContent());
        articleVo.setUserId(writingVo.getUserId());

        List<ArticleVo> vos = articleDao.findEntitiesByJdbc(ArticleVo.class,
                "select * from oe_bxs_article where id!=" + articleVo.getId()
                        + " and title = '" + articleVo.getTitle() + "'",
                new HashMap<String, Object>());

        String sql = "UPDATE oe_bxs_article SET title =:title ,content =:content,img_path =:imgPath,user_id =:userId WHERE id =:id";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", articleVo.getId());
        param.put("title", articleVo.getTitle());
        param.put("content", articleVo.getContent());
        param.put("imgPath", articleVo.getImgPath());
        param.put("userId", articleVo.getUserId());
        articleDao.getNamedParameterJdbcTemplate().update(sql, param);

        /**
         * 修改著作信息
         */
        String sqlWriting = "UPDATE medical_writings SET author =:author ,title =:title,buy_link =:buyLink, remark = :remark WHERE id =:id";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", writingVo.getId());
        params.put("title", writingVo.getTitle());
        params.put("author", writingVo.getAuthor());
        params.put("buyLink", writingVo.getBuyLink());
        params.put("remark", writingVo.getContent());
        articleDao.getNamedParameterJdbcTemplate().update(sqlWriting, params);

    }

    @Override
    public void deletes(String[] ids) {
        for (String id : ids) {

            WritingVo wv = findWritingById(id);

            // 删除著作
            String deleteSqlwri = "DELETE FROM medical_writings WHERE id=:id";
            Map<String, Object> paramDelWri = new HashMap<>(16);
            paramDelWri.put("id", id);
            articleDao.getNamedParameterJdbcTemplate().update(deleteSqlwri,
                    paramDelWri);

            // 删除文章
            String deleteSqlArt = "DELETE FROM oe_bxs_article WHERE id=:id";
            Map<String, Object> paramDelArt = new HashMap<>(16);
            paramDelArt.put("id", wv.getArticleId());
            articleDao.getNamedParameterJdbcTemplate().update(deleteSqlArt,
                    paramDelArt);

            // oe_bxs_appraise删除评论
            String deleteSqlAppraise = "DELETE FROM oe_bxs_appraise WHERE article_id=:articleId";
            Map<String, Object> paramDelAppraise = new HashMap<>(16);
            paramDelAppraise.put("articleId", wv.getArticleId());
            articleDao.getNamedParameterJdbcTemplate().update(
                    deleteSqlAppraise, paramDelAppraise);

            // 删除关联的信息
            String deleteSqlDocWriting = "DELETE FROM medical_doctor_writings WHERE writings_id=:writingsId";
            Map<String, Object> paramDelDocWriting = new HashMap<>(16);
            paramDelDocWriting.put("writingsId", id);
            articleDao.getNamedParameterJdbcTemplate().update(
                    deleteSqlDocWriting, paramDelDocWriting);
        }
    }

    @Override
    public void updateStatus(String id) {

        WritingVo wv = findWritingById(id);
        if (wv.getStatus() != null && 1 == wv.getStatus()) {
            wv.setStatus(0);
        } else {
            wv.setStatus(1);
        }
        String sql = "UPDATE medical_writings SET status =:status  WHERE id =:id";
        Map<String, Object> param = new HashMap<>(16);
        param.put("id", wv.getId());
        param.put("status", wv.getStatus());
        writingDao.getNamedParameterJdbcTemplate().update(sql, param);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("status", wv.getStatus()).addValue("id", wv.getArticleId());
        articleDao.getNamedParameterJdbcTemplate().update("update oe_bxs_article set status = :status where id = :id", mapSqlParameterSource);
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
    public void updateMedicalDoctorWritings(String id, String[] doctorId) {
        List<MedicalDoctorWritings> mhfs = dao.findEntitiesByProperty(
                MedicalDoctorWritings.class, "writingsId", id);
        for (MedicalDoctorWritings mhf : mhfs) {
            dao.delete(mhf);
        }
        if (doctorId != null) {
            for (String aDoctorId : doctorId) {
                MedicalDoctorWritings medicalDoctorWritings = new MedicalDoctorWritings();

                String mid = UUID.randomUUID().toString().replace("-", "");
                medicalDoctorWritings.setId(mid);

                medicalDoctorWritings.setDoctorId(aDoctorId);
                medicalDoctorWritings.setWritingsId(id);
                medicalDoctorWritings.setCreateTime(new Date());
                dao.save(medicalDoctorWritings);
            }
        }
    }
}
