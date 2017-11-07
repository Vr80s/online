package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.MessageShortVo;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Page<MessageShortVo> getMessageList(OnlineUser u, Integer type, Integer pageNumber, Integer pageSize) {
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", u.getId());
        String sql = "";
        if (type == null) {
            sql = " select id, context,create_time,readstatus from oe_message where  type !=2 and user_id =:userId  order by create_time desc";
        } else {
            paramMap.put("type", type);
            sql = " select id, context,create_time,readstatus from oe_message where user_id =:userId and type=:type order by create_time desc";
        }
        return this.findPageBySQL(sql, paramMap, MessageShortVo.class, pageNumber, pageSize);
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
        String sql = "delete from  oe_message  where id =:id  and  user_id=:userId";
        this.getNamedParameterJdbcTemplate().update(sql, params);
    }

    /**
     * 将全部消息标志已读状态
     *
     * @param userId
     */
    public void updateReadStatus(String userId,Integer type) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        String sql="";
        if(type==null){
            sql = "update oe_message set readstatus=1 where readstatus =0 and type !=2  and user_id=:userId   ";
        }else {
            params.addValue("type", type);
            sql = "update oe_message set readstatus=1 where readstatus =0  and type =:type  and user_id=:userId ";
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
        String sql = "update oe_message set readstatus=1 where id =:id  and user_id=:userId ";
        this.getNamedParameterJdbcTemplate().update(sql, params);
    }

    /**
     * 获取未读消息总数
     * @return
     */
    public  Map<String, Object> findMessageCount(String userId){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        String sql="select (select count(*) from  oe_message  where readstatus =0 and type !=2  and user_id=:userId ) count," +
                   "       (select count(*) from  oe_message  where readstatus =0 and type =0  and user_id=:userId ) systemCount," +
                   "       (select count(*) from  oe_message  where readstatus =0 and type =1  and user_id=:userId ) courseMessageCount," +
                   "       (select count(*) from  oe_message  where readstatus =0 and type =3  and user_id=:userId ) questionMessageCount," +
                   "       (select count(*) from  oe_message  where readstatus =0 and type =4  and user_id=:userId ) comentMessageCount ";

         List< Map<String, Object>> obj=this.getNamedParameterJdbcTemplate().queryForList(sql, params);
         return  obj.size() > 0 ? obj.get(0) : null;
    }


    /**
     * 获取最新公告
     */
    public  Map<String, Object>  findNewestNotice(OnlineUser user){
        Map<String, Object> paramMap = new HashMap<String, Object>();
         String sql="";
         if(user != null){
             paramMap.put("userId",user.getId());
             sql= "( SELECT m.id,m.context notice_content, 1 infoType from  oe_message m where m.is_online=1 and m.user_id=:userId  order by m.create_time asc limit 1 )" +
                  " UNION all ( select n.id, n.notice_content,0 infoType from oe_notice n where n.status=1)  ";
         }else{
             sql ="select id, notice_content,0 infoType from oe_notice where status=1  order by create_time desc limit 1";
         }
         List<Map<String, Object>> notices= this.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
         return  notices.size() > 0 ? notices.get(0): null;
    }

    /**
     * 发送消息
     * @param messageShortVo
     */
    public void saveMessage(MessageShortVo messageShortVo){
        String sql = "insert into oe_message (id,user_id,context,type,status,create_person ) values "
                   + "(:id,:user_id,:context,:type,1,:create_person)";
        this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(messageShortVo));
    }
}