package com.xczhihui.medical.headline.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;


/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2018-04-02
 */
@Data
@TableName("oe_bxs_appraise")
public class OeBxsAppraise extends Model<OeBxsAppraise> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 文章id
     */
    @TableField("article_id")
    private Integer articleId;
    /**
     * 评论内容
     */
    private String content;
    @TableField("create_time")
    private Date createTime;
    @TableField("is_delete")
    private Boolean isDelete;
    /**
     * 评论者用户id
     */
    @TableField("user_id")
    private String userId;
    /**
     * 被回复者用户id
     */
    @TableField("target_user_id")
    private String targetUserId;

    @TableField("praise_cnt")
    private Integer praiseCnt;
    /**
     * 回复的评论的id
     */
    @TableField("reply_comment_id")
    private String replyCommentId;

    @TableField(exist = false)
    private Boolean isMySelf;

    @TableField(exist = false)
    private String smallHeadPhoto;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String nickName;

    @TableField(exist = false)
    private String replySmallHeadPhoto;

    @TableField(exist = false)
    private String replyContent;

    @TableField(exist = false)
    private Date replyCreateTime;

    @TableField(exist = false)
    private boolean replyDeleted;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public Integer getPraiseCnt() {
        return praiseCnt;
    }

    public void setPraiseCnt(Integer praiseCnt) {
        this.praiseCnt = praiseCnt;
    }

    public String getReplyCommentId() {
        return replyCommentId;
    }

    public void setReplyCommentId(String replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public Boolean getMySelf() {
        return isMySelf;
    }

    public void setMySelf(Boolean mySelf) {
        isMySelf = mySelf;
    }

    public String getSmallHeadPhoto() {
        return smallHeadPhoto;
    }

    public void setSmallHeadPhoto(String smallHeadPhoto) {
        this.smallHeadPhoto = smallHeadPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getReplySmallHeadPhoto() {
        return replySmallHeadPhoto;
    }

    public void setReplySmallHeadPhoto(String replySmallHeadPhoto) {
        this.replySmallHeadPhoto = replySmallHeadPhoto;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Date getReplyCreateTime() {
        return replyCreateTime;
    }

    public void setReplyCreateTime(Date replyCreateTime) {
        this.replyCreateTime = replyCreateTime;
    }

    public boolean isReplyDeleted() {
        return replyDeleted;
    }

    public void setReplyDeleted(boolean replyDeleted) {
        this.replyDeleted = replyDeleted;
    }
}
