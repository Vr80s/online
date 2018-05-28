package com.xczhihui.medical.bbs.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.bbs.model.Label;
import com.xczhihui.medical.bbs.model.Post;
import com.xczhihui.medical.bbs.model.Reply;
import com.xczhihui.medical.bbs.vo.PostVO;
import com.xczhihui.medical.bbs.vo.ReplyVO;

/**
 * @author hejiwei
 */
public interface IPostService {

    /**
     * 获取帖子列表
     *
     * @param page    分页参数
     * @param labelId 标签id
     * @param type    筛选类型
     * @return 帖子列表
     */
    Page<PostVO> list(int page, Integer labelId, String type);

    /**
     * 获取全部的帖子标签
     *
     * @return 标签列表
     */
    List<Label> getLabels();

    /**
     * 获取热门帖子
     *
     * @return 热门帖子列表
     */
    List<PostVO> listHot();

    /**
     * 发布帖子
     *
     * @param post 帖子数据封装对象
     * @return 是否保存成功
     */
    boolean savePost(Post post);

    /**
     * 回复帖子
     *
     * @param reply 回复的内容
     * @return 是否回复成功
     */
    boolean addReply(Reply reply);

    /**
     * 详情数据
     *
     * @param id id
     * @return 帖子
     */
    PostVO get(Integer id);

    /**
     * 帖子下的详情数据
     *
     * @param postId 帖子id
     * @param page   分页参数
     * @return 回复数据
     */
    Page<ReplyVO> listByPostId(int postId, int page);

    /**
     * 浏览记录
     *
     * @param userId 用户id
     * @param postId 帖子id
     */
    void addBrowseRecord(int postId, String userId);

    /**
     * 是否被加入黑名单
     *
     * @param userId 用户id
     * @return 是否是黑名单
     */
    boolean isBlacklist(String userId);

    /**
     * 是否被禁用
     *
     * @param userId 用户id
     * @return 是否被禁言
     */
    boolean isGags(String userId);


    /**
     * 我的帖子
     *
     * @param userId 用户id
     * @param page   分页参数
     * @return 帖子分页列表
     */
    Page<PostVO> listMyPosts(String userId, int page);

    /**
     * 我的回复
     *
     * @param userId 用户id
     * @param page   分页参数
     * @return 回复分页列表
     */
    Page<ReplyVO> listMyReplies(String userId, int page);
}
