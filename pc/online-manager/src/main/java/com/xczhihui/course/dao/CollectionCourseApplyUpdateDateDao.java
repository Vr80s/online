package com.xczhihui.course.dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.support.dao.SimpleHibernateDao;

@Repository
public class CollectionCourseApplyUpdateDateDao extends SimpleHibernateDao {

    public List<Integer> getUpdateDatesByCollectionId(Integer collectionId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("collectionId", collectionId);
        String sql = "SELECT date FROM collection_course_apply_update_date WHERE collection_id = :collectionId";
        List<Integer> dates = this.getNamedParameterJdbcTemplate()
                .query(sql, mapSqlParameterSource, (rs, rowNum) -> rs.getInt(0));
        return dates;
    }
}
