package com.xczhihui.medical.bbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.bbs.model.Reply;
import com.xczhihui.medical.bbs.vo.ReplyVO;

/**
 * 回复
 *
 * @author hejiwei
 */
public interface ReplyMapper extends BaseMapper<Reply> {

    /**
     * 分页获取帖子的回复数据
     *
     * @param page   分页参数
     * @param postId 帖子id
     * @return 回复列表
     */
    @Select("select r.id as id, r.content as content, r.init_time as initTime," +
            " ou.name as name, r.to_reply_id as toReplyId" +
            " from quark_reply r" +
            " left join oe_user ou on r.`user_id` = ou.`id`" +
            " where r.`is_delete` = false and r.posts_id = #{postId} order by r.init_time desc")
    List<ReplyVO> listByPostId(Page<ReplyVO> page, @Param("postId") Integer postId);

    /**
     * 通过id获取回复的数据
     *
     * @param id id
     * @return 回复的数据
     */
    @Select("select r.id as id, r.content as content, r.init_time as initTime," +
            " ou.name as name, r.to_reply_id as toReplyId" +
            " from quark_reply r" +
            " left join oe_user ou on r.`user_id` = ou.`id`" +
            " where r.`is_delete` = false and r.id = #{id}")
    ReplyVO get(@Param("id") int id);

    /**
     * 用户的回复数据
     *
     * @param page   分页参数
     * @param userId 用户id
     * @return 回复列表
     */
    @Select("select r.id as id, r.content as content, r.init_time as initTime," +
            " r.to_reply_id as toReplyId, p.title as postTitle, l.name as labelName, p.id as postId" +
            " from quark_reply r, quark_posts p, quark_label l" +
            " where r.posts_id = p.id and p.label_id = l.id and r.`is_delete` = false and r.user_id = #{userId}")
    List<ReplyVO> listMyReplies(Page<ReplyVO> page, @Param("userId") String userId);
}
