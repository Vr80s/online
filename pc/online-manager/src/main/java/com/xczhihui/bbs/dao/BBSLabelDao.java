package com.xczhihui.bbs.dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xczhihui.bbs.vo.BBSLabelVo;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.BBSLabel;

@Repository("bbsLabelDao")
public class BBSLabelDao extends SimpleHibernateDao {

    public int deleteByIds(List<Integer> ids) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("ids", ids);
        return this
                .getNamedParameterJdbcTemplate()
                .update("DELETE FROM quark_label WHERE id IN (:ids) AND posts_count <= 0",
                        mapSqlParameterSource);
    }

    public int updateStatusByIds(List<Integer> ids) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("ids", ids);
        return this
                .getNamedParameterJdbcTemplate()
                .update("UPDATE quark_label SET is_disable = !is_disable WHERE id IN (:ids)",
                        mapSqlParameterSource);
    }

    public void create(BBSLabel bbsLabel) {
        super.save(bbsLabel);
    }

    public int update(BBSLabel bbsLabel) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("details", bbsLabel.getDetails())
                .addValue("name", bbsLabel.getName())
                .addValue("sort", bbsLabel.getSort())
                .addValue("labelImgUrl", bbsLabel.getLabelImgUrl())
                .addValue("id", bbsLabel.getId());
        return this.getNamedParameterJdbcTemplate().update(
                "UPDATE quark_label SET details = :details, name = :name, sort = :sort,"
                        + " label_img_url = :labelImgUrl WHERE id = :id",
                mapSqlParameterSource);
    }

    public Page<BBSLabelVo> list(int page, int size) {
        return super
                .findPageBySQL(
                        "select id as id, details as details, name as name, posts_count as postsCount,"
                                + " is_disable as disable, sort as sort, label_img_url as labelImgUrl "
                                + " from quark_label order by sort desc", null,
                        BBSLabelVo.class, page, size);
    }

    public void updatePostCount(Integer labelId, Integer postCount) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", labelId).addValue("postCount",
                postCount);
        this.getNamedParameterJdbcTemplate()
                .update("UPDATE quark_label SET posts_count = :postCount WHERE id = :id",
                        mapSqlParameterSource);
    }
}
