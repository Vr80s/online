package com.xczhihui.medical.headline.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.headline.model.OeBxsAppraise;
import com.xczhihui.medical.headline.vo.AppraiseVO;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-04-02
 */
public interface OeBxsAppraiseMapper extends BaseMapper<OeBxsAppraise> {

    /**
     * 查询文章评论列表接口
     *
     * @param page
     * @param id
     * @param userId
     * @return
     */
    List<OeBxsAppraise> selectArticleAppraiseById(@Param("page") Page<OeBxsAppraise> page, @Param("id") Integer id, @Param("userId") String userId);

    /**
     * 点赞数加1
     *
     * @param id id
     * @return
     */
    @Update("update oe_bxs_appraise set praise_cnt = IFNULL(praise_cnt, 0) + 1 where id = #{id}")
    int incrAppraisePraiseCnt(@Param("id") String id);

    /**
     * 点赞数减一
     *
     * @param id id
     * @return
     */
    @Update("update oe_bxs_appraise set praise_cnt = praise_cnt - 1 where id = #{id} and praise_cnt > 0")
    int decrAppraisePraiseCnt(@Param("id") String id);

    /**
     * 查询文章的评论列表
     *
     * @param aid  文章id
     * @param page 分页参数
     * @return
     */
    @Select({"select oba.id, oba.content, oba.reply_comment_id as replyCommentId, oba.create_time as createTime, oba.user_id as userId," +
            " oba.praise_cnt as praiseCnt, oba.is_delete as deleted, ou.name, ou.small_head_photo as smallHeadPhoto" +
            " from oe_bxs_appraise oba left join oe_user ou on oba.user_id = ou.id" +
            " where oba.article_id = #{aid} and oba.is_delete = 0" +
            " order by oba.create_time desc"})
    List<AppraiseVO> listByArticleId(@Param("aid") Integer aid, Page<AppraiseVO> page);

    @Select({"<script>select oba.id, oba.content, oba.reply_comment_id as replyCommentId, oba.create_time as createTime, oba.user_id as userId," +
            " oba.praise_cnt as praiseCnt, oba.is_delete as deleted, ou.name, ou.small_head_photo as smallHeadPhoto" +
            " from oe_bxs_appraise oba left join oe_user ou on oba.user_id = ou.id" +
            " where oba.id in <foreach item='item' index='index' collection='aids' open='(' separator=',' close=')'>" +
            "             #{item} " +
            "           </foreach></script>"})
    List<AppraiseVO> listByIds(@Param("aids") List<String> aids);
}