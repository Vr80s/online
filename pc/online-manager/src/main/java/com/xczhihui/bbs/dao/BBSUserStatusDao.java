package com.xczhihui.bbs.dao;

import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xczhihui.bbs.vo.BBSOnlineUserVo;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.utils.TableVo;

@Repository
public class BBSUserStatusDao extends SimpleHibernateDao {

    public boolean gags(String userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        return this
                .getNamedParameterJdbcTemplate()
                .update("UPDATE bbs_user_status SET is_gag = '1' WHERE user_id = :userId AND is_gag != '1'",
                        mapSqlParameterSource) > 0;
    }

    public boolean unGags(String userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        return this
                .getNamedParameterJdbcTemplate()
                .update("UPDATE bbs_user_status SET is_gag = '0' WHERE user_id = :userId AND is_gag != '0'",
                        mapSqlParameterSource) > 0;
    }

    public boolean addBlacklist(String userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        return this
                .getNamedParameterJdbcTemplate()
                .update("UPDATE bbs_user_status SET is_blacklist = '1' WHERE user_id = :userId AND is_blacklist != '1'",
                        mapSqlParameterSource) > 0;
    }

    public boolean cancelBlacklist(String userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        return this
                .getNamedParameterJdbcTemplate()
                .update("UPDATE bbs_user_status SET is_blacklist = '0' WHERE user_id = :userId AND is_blacklist != '0'",
                        mapSqlParameterSource) > 0;
    }

    public Page<BBSOnlineUserVo> list(Map<String, Object> params,
                                      TableVo tableVo) {
        String sql = "select u.id as id, u.name as nickname, u.mobile as mobile, s.is_gag as gags, s.is_blacklist as blacklist"
                + " from oe_user u, bbs_user_status s"
                + " where u.id = s.user_id and (:mobile is null OR u.mobile = :mobile)";
        return this.findPageBySQL(sql, params, BBSOnlineUserVo.class,
                tableVo.getCurrentPage(), tableVo.getiDisplayLength());
    }
}
