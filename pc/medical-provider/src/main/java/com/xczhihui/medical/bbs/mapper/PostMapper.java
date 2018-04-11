package com.xczhihui.medical.bbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.bbs.model.Post;
import com.xczhihui.medical.bbs.vo.PostVO;

/**
 * @author hejiwei
 */
public interface PostMapper extends BaseMapper<Post> {

    /**
     * 标签id筛选获取帖子列表
     *
     * @param page    分页参数
     * @param labelId 标签id
     * @return 帖子列表
     */
    @Select("select p.id as id, p.content as content, p.title as title, p.label_id as labelId, p.good as good, p.init_time as initTime," +
            " p.`browse_count` as browseCount, p.`reply_count` as replyCount, p.`report_order` as reportOrder," +
            " p.`top` as top, p.`hot` as hot, ou.name as name, ou.id as userId, ou.small_head_photo as smallHeadPhoto, l.name as labelName" +
            " from quark_posts p" +
            " left join oe_user ou on p.`user_id` = ou.`id` left join quark_label l on p.label_id = l.id" +
            " where p.`is_delete` = false and (#{labelId} is null OR p.label_id = #{labelId}) order by p.init_time desc")
    List<PostVO> listByLabelId(Page<PostVO> page, @Param("labelId") Integer labelId);

    /**
     * 类型为热的帖子列表
     *
     * @param page 分页参数
     * @return 帖子列表
     */
    @Select("select p.id as id, p.content as content, p.title as title, p.label_id as labelId, p.good as good, p.init_time as initTime," +
            " p.`browse_count` as browseCount, p.`reply_count` as replyCount, p.`report_order` as reportOrder," +
            " p.`top` as top, p.`hot` as hot, ou.name as name, ou.id as userId, ou.small_head_photo as smallHeadPhoto, l.name as labelName" +
            " from quark_posts p" +
            " left join oe_user ou on p.`user_id` = ou.`id` left join quark_label l on p.label_id = l.id " +
            " where p.`is_delete` = false and hot = true order by p.init_time desc")
    List<PostVO> listHot(Page<PostVO> page);

    /**
     * 类型为精品的帖子列表
     *
     * @param page 分页参数
     * @return 帖子列表
     */
    @Select("select p.id as id, p.content as content, p.title as title, p.label_id as labelId, p.good as good, p.init_time as initTime," +
            " p.`browse_count` as browseCount, p.`reply_count` as replyCount, p.`report_order` as reportOrder," +
            " p.`top` as top, p.`hot` as hot, ou.name as name, ou.id as userId, ou.small_head_photo as smallHeadPhoto, l.name as labelName" +
            " from quark_posts p" +
            " left join oe_user ou on p.`user_id` = ou.`id` left join quark_label l on p.label_id = l.id " +
            " where p.`is_delete` = false and good = true order by p.init_time desc")
    List<PostVO> listGood(Page<PostVO> page);

    /**
     * 更新帖子回复数
     *
     * @param id id
     * @return 更新的行数
     */
    @Update("update quark_posts set reply_count = reply_count + 1 where id = #{id}")
    int updateReplyCount(@Param("id") int id);

    /**
     * 更新帖子浏览数
     *
     * @param id id
     * @return 更新的行数
     */
    @Update("update quark_posts set browse_count = browse_count + 1 where id = #{id}")
    int updateBrowseCount(@Param("id") int id);

    /**
     * 根据id查找帖子
     *
     * @param id id
     * @return 帖子
     */
    @Select("select p.id as id, p.content as content, p.title as title, p.label_id as labelId, p.good as good, p.init_time as initTime," +
            " p.`browse_count` as browseCount, p.`reply_count` as replyCount, p.`report_order` as reportOrder," +
            " p.`top` as top, p.`hot` as hot, ou.name as name, ou.id as userId, ou.small_head_photo as smallHeadPhoto, l.name as labelName" +
            " from quark_posts p" +
            " left join oe_user ou on p.`user_id` = ou.`id` left join quark_label l on p.label_id = l.id" +
            " where p.`is_delete` = false and p.id = #{id}")
    PostVO get(Integer id);

    /**
     * 查询我的帖子
     *
     * @param page   分页参数
     * @param userId 用户id
     * @return 我的帖子列表
     */
    @Select("select p.id as id, p.content as content, p.title as title, p.label_id as labelId, p.good as good, p.init_time as initTime," +
            " p.`browse_count` as browseCount, p.`reply_count` as replyCount, p.`report_order` as reportOrder," +
            " p.`top` as top, p.`hot` as hot, l.name as labelName" +
            " from quark_posts p left join quark_label l on p.label_id = l.id" +
            " where p.`is_delete` = false and p.user_id = #{userId}")
    List<PostVO> listMyPosts(Page<PostVO> page, @Param("userId") String userId);
}
