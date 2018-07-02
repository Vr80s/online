package com.xczhihui.medical.doctor.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * Description：医师动态评论表
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/20 11:48
 **/
@TableName("medical_doctor_posts_comment")
public class MedicalDoctorPostsComment extends Model<MedicalDoctorPostsComment> {

    private static final long serialVersionUID = 1L;

    /**
     * 医师动态评论表
     */
    private Integer id;
    /**
     * 动态的ID
     */
    @TableField("posts_id")
    private Integer postsId;
    /**
     * 评论内容
     */
    @TableField("content")
    private String content;
    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;
    /**
     * 回复人id
     */
    @TableField("reply_user_id")
    private String replyUserId;
    /**
     * 评论id（回复的评论）
     */
    @TableField("comment_id")
    private Integer commentId;
    /**
     * 1已删除0未删除
     */
    @TableField("deleted")
    private Boolean deleted;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 评论用户
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 回复用户
     */
    @TableField(exist = false)
    private String replyUserName;
    /**
     * 回复用户
     */
    @TableField(exist = false)
    private Boolean isSelf = false;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostsId() {
        return postsId;
    }

    public void setPostsId(Integer postsId) {
        this.postsId = postsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public Boolean getSelf() {
        return isSelf;
    }

    public void setSelf(Boolean self) {
        isSelf = self;
    }
}
