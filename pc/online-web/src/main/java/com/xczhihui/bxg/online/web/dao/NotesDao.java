package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.web.vo.NotesCommentVo;
import com.xczhihui.bxg.online.web.vo.NotesVo;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author Fudong.Sun【】
 * @Date 2016/12/13 11:00
 */
@Repository
public class NotesDao extends SimpleHibernateDao {

    /**
     * 保存笔记
     * @param notes
     */
    public void saveNotes(NotesVo notes){
        String sql = "insert into oe_notes (id,course_id,video_id,user_id,"
                + "grade_id,content,create_person,chapter_id,is_share) values "
                + "(:id,:course_id,:video_id,:user_id,:grade_id,:content,:create_person,:chapter_id,:is_share)";
        this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(notes));
    }

    /**
     * 修改笔记
     * @param id
     * @param content 内容
     */
    public void updateNotes(String id,String content,Boolean is_share){
        StringBuffer sql = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<>();
        sql.append(" update oe_notes set content =:content,create_time=now(),is_share =:is_share where id =:id");
        paramMap.put("id", id);
        paramMap.put("content", content);
        paramMap.put("is_share", is_share);
        this.getNamedParameterJdbcTemplate().update(sql.toString(), paramMap);
    }

    /**
     * 我的笔记列表、全部笔记列表
     * @param type 是否我的笔记(0：全部，1：我的笔记，2：我的收藏)
     * @param userId 当前用户id
     * @return
     */
    public Page<NotesVo> findNotes(Integer type, String videoId, String userId, Integer pageNumber, Integer pageSize){
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 15 : pageSize;
        StringBuffer sql = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<>();
        sql.append(" select nt.id,nt.create_time,nt.content, nt.praise_sum, nt.is_share, nt.comment_sum,");
        sql.append(" (select `name` from oe_user where id=nt.user_id) as user_name,");
        sql.append(" (select `small_head_photo` from oe_user where id=nt.user_id) as small_head_photo,");
        sql.append(" IF((select praise from oe_notes_praise where notes_id=nt.id and user_id =:id)=1,TRUE,FALSE) as praise,");
        sql.append(" IF((select collect from oe_notes_collection where notes_id=nt.id and user_id =:id)=1,TRUE,FALSE) as collect,");
        sql.append(" nt.user_id = '" + userId + "' as deleteButton");
        sql.append(" from oe_notes nt ");
        if(type==2){
            sql.append(" RIGHT JOIN oe_notes_collection nc on nt.id=nc.notes_id");
        }
        sql.append(" where nt.is_delete=0 and nt.`status`=1 and nt.video_id =:videoId");
        if(type==0) {
            //全部笔记
            if("".equalsIgnoreCase(userId)) {
                //未登录用户
                sql.append(" and nt.is_share =1");
            }else{
                //登录的用户
                sql.append(" and (nt.is_share =1 or nt.user_id =:id)");
            }
        }else if(type==1){
            //我的笔记
            sql.append(" and nt.user_id =:id");
        }else if(type==2){
            //我收藏的笔记
            sql.append(" and nc.user_id =:id and nc.collect=1");
        }
        sql.append(" order by nt.create_time desc");
        paramMap.put("id",userId);
        paramMap.put("videoId",videoId);
        return this.findPageBySQL(sql.toString(),paramMap,NotesVo.class,pageNumber,pageSize);
    }
    /**
     * 删除笔记
     * @param request
     * @param u
     * @param notes_id
     */
    public void deleteNotes(HttpServletRequest request, OnlineUser u, String notes_id){
        String sql = "select create_person,user_id from oe_notes where id = ? and is_delete =0";
        List<Map<String, Object>> check = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql, notes_id);
        if (check == null || check.size() <= 0) {
            throw new RuntimeException(String.format("不存在"));
        }
        User user= (User) request.getSession().getAttribute("_adminUser_");
        if(user == null){ //非管理员
            if(!check.get(0).get("user_id").toString().equals(u.getId())){
                throw new RuntimeException("您不是此笔记作者，无权删除！");
            }
            /** 1，删除笔记表数据 */
            sql = "delete from  oe_notes  where user_id=? and id = ? and is_delete=0";
            this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, u.getId(),notes_id);
            /** 2，删除相关点赞表数据 */
            sql = "delete from  oe_notes_praise  where notes_id = ?";
            this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,notes_id);
            /** 3，删除相关回答、评论表数据 */
            sql = "delete from  oe_notes_comment  where notes_id = ? and is_delete=0";
            this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,notes_id);
            /** 4，删除相关收藏表数据 */
            sql = "delete from  oe_notes_collection  where notes_id = ?";
            this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,notes_id);
        }else{
            sql = " UPDATE oe_notes set is_delete =1   where   id = ?  and is_delete=0";
            this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,notes_id);
        }
    }

    /**
     * 笔记点赞、取消点赞
     */
    public Map<String,Object> praise(String notes_id,OnlineUser u) {
        Map<String,Object> returnMap = new HashMap<>();
        boolean isPraise;
        String checkSql = "select * from oe_notes_praise where notes_id=? and user_id=?";
        List<Map<String, Object>> check = this.getNamedParameterJdbcTemplate().getJdbcOperations()
                .queryForList(checkSql, notes_id, u.getId());
        if (check == null || check.size()<1) {//查看当前用户是否有点赞笔记记录
            String id = UUID.randomUUID().toString().replaceAll("-", "");
            this.getNamedParameterJdbcTemplate().getJdbcOperations().update(
                    "insert into oe_notes_praise (id,notes_id,praise_name,user_id) values (?,?,?,?)", id, notes_id,
                    u.getName(),u.getId());
            //增加笔记评论数
            String sql = "update oe_notes set praise_sum=(praise_sum+1) where id = ?";
            this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, notes_id);
            isPraise = true;
        } else {
            boolean praise = Boolean.parseBoolean(check.get(0).get("praise").toString());
            if(praise){
                this.getNamedParameterJdbcTemplate().getJdbcOperations().update("UPDATE oe_notes_praise set praise=0,create_time=now() where id=? and user_id =?",
                        check.get(0).get("id").toString(),u.getId());
                //减少笔记评论数
                String sql = "update oe_notes set praise_sum=(praise_sum-1) where id = ?";
                this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, notes_id);
                isPraise = false;
            }else{
                this.getNamedParameterJdbcTemplate().getJdbcOperations().update("UPDATE oe_notes_praise set praise=1,create_time=now() where id=? and user_id =?",
                        check.get(0).get("id").toString(),u.getId());
                //增加笔记评论数
                String sql = "update oe_notes set praise_sum=(praise_sum+1) where id = ?";
                this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, notes_id);
                isPraise = true;
            }
        }
        returnMap.put("isPraise",isPraise);
        String Sql = "select praise_sum from oe_notes where id=?";
        int praise_sum = this.getNamedParameterJdbcTemplate().getJdbcOperations()
                .queryForObject(Sql, Integer.class,notes_id);
        returnMap.put("praise_sum",praise_sum);
        return returnMap;
    }
    /**
     * 笔记收藏、取消收藏
     */
    public boolean updateCollect(String notes_id,OnlineUser u) {
        String checkSql = "select * from oe_notes_collection where notes_id=? and user_id=?";
        List<Map<String, Object>> check = this.getNamedParameterJdbcTemplate().getJdbcOperations()
                .queryForList(checkSql, notes_id, u.getId());
        if (check == null || check.size()<1) {//查看当前用户是否有收藏笔记记录
            String id = UUID.randomUUID().toString().replaceAll("-", "");
            this.getNamedParameterJdbcTemplate().getJdbcOperations().update(
                    "insert into oe_notes_collection (id,notes_id,collect_name,user_id) values (?,?,?,?)", id, notes_id,
                    u.getName(),u.getId());
            return true;
        } else {
            boolean collect = Boolean.parseBoolean(check.get(0).get("collect").toString());
            if(collect){
                this.getNamedParameterJdbcTemplate().getJdbcOperations().update("UPDATE oe_notes_collection set collect=0,create_time=now() where id=? and user_id =?",
                        check.get(0).get("id").toString(),u.getId());
                return false;
            }else{
                this.getNamedParameterJdbcTemplate().getJdbcOperations().update("UPDATE oe_notes_collection set collect=1,create_time=now() where id=? and user_id =?",
                        check.get(0).get("id").toString(),u.getId());
                return true;
            }
        }
    }
    /**
     * 查询评论/回复列表
     * @param notes_id
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<NotesCommentVo> findComments(String userId,String notes_id, Integer pageNumber, Integer pageSize){
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 5 : pageSize;
        String sql = "select *,user_id = '" + userId + "' as deleteButton from oe_notes_comment where notes_id=:notes_id and is_delete=0 order by create_time ";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("notes_id", notes_id);
        return this.findPageBySQL(sql,paramMap, NotesCommentVo.class, pageNumber, pageSize);
    }
    /**
     * 新增评论/回复
     * @param nv
     */
    public void saveComment(NotesCommentVo nv){
        nv.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        String sql = "insert into oe_notes_comment (id,notes_id,content,create_nick_name,"
                + "create_head_img,create_person,target_person,target_nike_name,user_id,target_user_id) "
                + "values (:id,:notes_id,:content,:create_nick_name,:create_head_img,"
                + ":create_person,:target_person,:target_nike_name,:user_id,:target_user_id) ";
        this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(nv));

        //增加被评论问题的评论数
        sql = "update oe_notes set comment_sum=(comment_sum+1) where id = ?";
        this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, nv.getNotes_id());
    }
    /**
     * 删除评论/回复
     * @param comment_id
     */
    public void deleteComment(HttpServletRequest request, OnlineUser u, String comment_id){
        String sql = "select create_person,notes_id,user_id from oe_notes_comment where id = ? and is_delete =0";
        List<Map<String, Object>> check = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql, comment_id);
        if (check == null || check.size() <= 0) {
            throw new RuntimeException(String.format("不存在"));
        }
        User user= (User) request.getSession().getAttribute("_adminUser_");
        if(user == null){ //非管理员
            if(!check.get(0).get("user_id").toString().equals(u.getId())){
                throw new RuntimeException("您不是此评论的评论者，无权删除！");
            }
            sql = "delete from  oe_notes_comment  where user_id=? and id = ? and is_delete=0";
            this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, u.getId(),comment_id);
        }else{
            sql = " UPDATE oe_notes_comment set is_delete =1   where   id = ?  and is_delete=0";
            this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,comment_id);
        }

        //减少被评论问题的评论数
        sql = "update oe_notes  set comment_sum=(comment_sum-1)  where   id = ?";
        this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, check.get(0).get("notes_id"));

    }
}
