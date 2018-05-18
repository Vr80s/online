package com.xczhihui.bxg.online.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.MessageVo;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;

/**
 * Created by 1 on 2016/3/8.
 */
@Repository
public class MessageDao extends SimpleHibernateDao {
    /**
     * 获取用户的消息
     *
     * @param u
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<MessageVo> getMessageList(OnlineUser u, Integer type, Integer pageNumber, Integer pageSize) {
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", u.getId());
        String sql = "";
        if (type == null) {
            sql = " select id, context,create_time,readstatus," +
                    " route_type as routeType, detail_id as detailId from oe_message where type !=2 and user_id =:userId and is_delete = 0 order by create_time desc";
        } else {
            paramMap.put("type", type);
            sql = " select id, context,create_time,readstatus," +
                    " route_type as routeType, detail_id as detailId from oe_message where user_id =:userId and type=:type and is_delete = 0 order by create_time desc";
        }
        return this.findPageBySQL(sql, paramMap, MessageVo.class, pageNumber, pageSize);
    }


    /**
     * 删除用户的消息
     *
     * @param id
     * @param userId
     */
    public void deleteById(String id, String userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("userId", userId);
        String sql = "UPDATE oe_message SET is_delete = 1 WHERE id =:id AND user_id=:userId";
        this.getNamedParameterJdbcTemplate().update(sql, params);
    }

    /**
     * 将全部消息标志已读状态
     *
     * @param userId
     */
    public void updateReadStatus(String userId, Integer type) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        String sql;
        if (type == null) {
            sql = "UPDATE oe_message SET readstatus=1 WHERE readstatus =0 AND type !=2  AND user_id=:userId";
        } else {
            params.addValue("type", type);
            sql = "UPDATE oe_message SET readstatus=1 WHERE readstatus =0  AND type =:type  AND user_id=:userId ";
        }

        this.getNamedParameterJdbcTemplate().update(sql, params);
    }

    /**
     * 将某条消息标志已读状态
     *
     * @param id     消息id
     * @param userId
     */
    public void updateReadStatusById(String id, String userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("id", id);
        String sql = "UPDATE oe_message SET readstatus=1 WHERE id =:id  AND user_id=:userId ";
        this.getNamedParameterJdbcTemplate().update(sql, params);
    }

    /**
     * 获取未读消息总数
     *
     * @return
     */
    public Map<String, Object> findMessageCount(String userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        String sql = "SELECT (SELECT count(*) FROM  oe_message  WHERE readstatus =0 AND type !=2  AND user_id=:userId ) count," +
                "       (SELECT count(*) FROM  oe_message  WHERE readstatus =0 AND type =0  AND user_id=:userId ) systemCount," +
                "       (SELECT count(*) FROM  oe_message  WHERE readstatus =0 AND type =1  AND user_id=:userId ) courseMessageCount," +
                "       (SELECT count(*) FROM  oe_message  WHERE readstatus =0 AND type =3  AND user_id=:userId ) questionMessageCount," +
                "       (SELECT count(*) FROM  oe_message  WHERE readstatus =0 AND type =4  AND user_id=:userId ) comentMessageCount ";

        List<Map<String, Object>> obj = this.getNamedParameterJdbcTemplate().queryForList(sql, params);
        return obj.size() > 0 ? obj.get(0) : null;
    }


    /**
     * 获取最新公告
     */
    public Map<String, Object> findNewestNotice(OnlineUser user) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String sql = "";
        if (user != null) {
            paramMap.put("userId", user.getId());
            sql = "( SELECT m.id,m.context notice_content, 1 infoType FROM  oe_message m WHERE m.is_online=1 AND m.user_id=:userId  ORDER BY m.create_time ASC LIMIT 1 )" +
                    " UNION ALL ( SELECT n.id, n.notice_content,0 infoType FROM oe_notice n WHERE n.status=1)  ";
        } else {
            sql = "SELECT id, notice_content,0 infoType FROM oe_notice WHERE status=1  ORDER BY create_time DESC LIMIT 1";
        }
        List<Map<String, Object>> notices = this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
        return notices.size() > 0 ? notices.get(0) : null;
    }
}