package com.xczhihui.medical.headline.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 文章评论VO
 *
 * @author hejiwei
 */
public class AppraiseVO implements Serializable {

    private String id;

    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private Date createTime;

    private String content;

    private Integer praiseCnt = 0;

    private SimpleUserVO author;

    private Boolean self;

    private AppraiseVO reply;

    private Boolean praised;

    @JsonIgnore
    private String replyCommentId;

    @JsonIgnore
    private String userId;
    @JsonIgnore
    private String name;
    @JsonIgnore
    private String smallHeadPhoto;
    private Boolean deleted;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSmallHeadPhoto() {
        return smallHeadPhoto;
    }

    public void setSmallHeadPhoto(String smallHeadPhoto) {
        this.smallHeadPhoto = smallHeadPhoto;
    }

    public Integer getPraiseCnt() {
        return praiseCnt;
    }

    public void setPraiseCnt(Integer praiseCnt) {
        this.praiseCnt = praiseCnt;
    }

    public SimpleUserVO getAuthor() {
        return author;
    }

    public void setAuthor(SimpleUserVO author) {
        this.author = author;
    }

    public Boolean getSelf() {
        return self;
    }

    public void setSelf(Boolean self) {
        this.self = self;
    }

    public AppraiseVO getReply() {
        return reply;
    }

    public void setReply(AppraiseVO reply) {
        this.reply = reply;
    }

    public String getReplyCommentId() {
        return replyCommentId;
    }

    public void setReplyCommentId(String replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getPraised() {
        return praised;
    }

    public void setPraised(Boolean praised) {
        this.praised = praised;
    }

    @Override
    public String toString() {
        return "AppraiseVO{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                ", smallHeadPhoto='" + smallHeadPhoto + '\'' +
                ", praiseCnt=" + praiseCnt +
                ", author=" + author +
                '}';
    }


}
