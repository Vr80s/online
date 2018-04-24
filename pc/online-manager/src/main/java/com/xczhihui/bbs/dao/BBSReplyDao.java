package com.xczhihui.bbs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xczhihui.bbs.vo.BBSReplyVo;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;

@Repository("bbsReplyDao")
public class BBSReplyDao extends SimpleHibernateDao {

    public Page<BBSReplyVo> list(Map<String, Object> params, int page, int size) {
        String sql = "SELECT r.id as id, r.content as content, r.`init_time` as initTime, r.up as up, u.name as nickname,"
                + " u.id as userId, r.is_delete as deleted"
                + " FROM  quark_reply r,oe_user u"
                + " where r.user_id = u.id AND (:id is null OR r.id = :id)"
                + " AND (:userId is null OR u.id = :userId) AND (:isDelete is null OR r.is_delete = :isDelete)"
                + " AND (:content is null OR r.content LIKE :content) "
                + " order by r.`init_time` desc";
        return super.findPageBySQL(sql, params, BBSReplyVo.class, page, size);
    }

    public int changeDeleteStatus(List<Integer> ids, boolean deleted) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("ids", ids).addValue("deleted", deleted);
        return this
                .getNamedParameterJdbcTemplate()
                .update("UPDATE quark_reply SET is_delete = :deleted WHERE id IN (:ids)",
                        mapSqlParameterSource);
    }
}
