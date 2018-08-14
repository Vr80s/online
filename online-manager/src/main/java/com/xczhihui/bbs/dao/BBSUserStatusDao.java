package com.xczhihui.bbs.dao;

import com.xczhihui.bbs.vo.BBSOnlineUserVo;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.utils.TableVo;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Map;

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
        String sql = "select u.id as id, u.name as nickname, u.login_name as mobile, IFNULL(s.is_gag,0) AS gags,"
                + " IFNULL(s.is_blacklist,0) AS blacklist "
                + " from oe_user u left join bbs_user_status s on u.id = s.user_id"
                + " where (:mobile is null OR u.login_name = :mobile)";
        return this.findPageBySQL(sql, params, BBSOnlineUserVo.class,
                tableVo.getCurrentPage(), tableVo.getiDisplayLength());
    }
}
