package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.common.domain.MenuTag;
import com.xczhihui.bxg.online.web.vo.AskAccuseVo;
import com.xczhihui.bxg.online.web.vo.AskAnswerVo;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class MenuTagDao extends SimpleHibernateDao {
    public List<MenuTag> getChildrenMenuTag(String MenuTagId){
        DetachedCriteria dc = DetachedCriteria.forClass(MenuTag.class);
        dc.add(Restrictions.eq("isDelete", false));
        dc.add(Restrictions.eq("pid", MenuTagId));
        return this.findEntities(dc);
    };

    public List<MenuTag> getAllFirstMenuTag(){
    	StringBuilder sql = new StringBuilder();
    	sql.append(" select * from menuTag m where m.pid = -1 and m.is_delete = 0 and m.`status` = 1 ORDER BY m.sort limit 11 ");
    	return this.getNamedParameterJdbcTemplate().getJdbcOperations().query
                (sql.toString(), BeanPropertyRowMapper.newInstance(MenuTag.class));
    }


    public List<MenuTag> getSecondChildrenMenuTagByPid(String pid){
        StringBuilder sql = new StringBuilder();
        sql.append(" select * from menuTag m where m.pid =? and m.is_delete = 0 and m.`status` = 1 ORDER BY m.sort limit 11 ");
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().query
                (sql.toString(),new Object[]{pid}, BeanPropertyRowMapper.newInstance(MenuTag.class));
    }


    public List<MenuTag> getSecondChildrenMenuTagByPidByIndex(String pid){
        StringBuilder sql = new StringBuilder();
        sql.append(" select * from menuTag m where m.pid =? and m.is_delete = 0 and m.`status` = 1 ORDER BY m.sort");
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().query
                (sql.toString(),new Object[]{pid}, BeanPropertyRowMapper.newInstance(MenuTag.class));
    }


    public List<MenuTag> getThirdChildrenMenuTagByPid(String pid){
        StringBuilder sql = new StringBuilder();
        sql.append(" select * from menuTag m where m.pid =? and m.is_delete = 0 and m.`status` = 1 ORDER BY m.sort ");
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().query
                (sql.toString(),new Object[]{pid}, BeanPropertyRowMapper.newInstance(MenuTag.class));
    }


    public MenuTag findMenuTag(String MenuTagId){
        DetachedCriteria dc = DetachedCriteria.forClass(MenuTag.class);
        dc.add(Restrictions.eq("isDelete", false));
        dc.add(Restrictions.eq("id", MenuTagId));
        return this.findEntity(dc);
    }

    public List<MenuTag> finds(){
        DetachedCriteria dc = DetachedCriteria.forClass(MenuTag.class);
        dc.add(Restrictions.eq("isDelete", false));
        return this.findEntities(dc);
    }


    /**
     * 投诉
     * @param ac 参数封装对象
     */
    public  void   saveAccuse(AskAccuseVo ac){
        ac.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        ac.setStatus(0);
        String sql = "insert into oe_ask_accuse (id,target_type,target_id,accuse_type,content,create_person,user_id)   "
                + " values (:id,:target_type,:target_id,:accuse_type,:content,:create_person,:user_id) ";
        this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(ac));
    }

    public void updateAccuseStatus(String targetId,String targetType){
            //改变问题以及回答信息的投诉状态
            String tableName = "0".equals(targetType) ? "oe_ask_question" :"oe_ask_answer";
            String seSql="select id  from "+tableName + " where id = ?";
            List<AskAnswerVo> check = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(seSql,
                BeanPropertyRowMapper.newInstance(AskAnswerVo.class), targetId);
            if(check.size() < 1){
                throw new RuntimeException("不存在");
            }
            String  updateSql="update "+tableName+" set accused=0  where id=?";
            this.getNamedParameterJdbcTemplate().getJdbcOperations().update(updateSql, targetId);

            String  sql="update oe_ask_accuse set status=2  where  target_id=? and status = 0";
           this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,targetId);

    }
}
