package com.xczhihui.medical.bbs.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hejiwei
 */
public class ReplyVO implements Serializable {
    private Integer id;

    private String content;

    private Date initTime;

    private String name;

    private Integer toReplyId;

    private ReplyVO toReply;

    private String postTitle;

    private String labelName;

    private Integer postId;

    private String smallHeadPhoto;

    @Override
    public String toString() {
        return "ReplyVO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", initTime=" + initTime +
                ", name='" + name + '\'' +
                ", toReplyId=" + toReplyId +
                ", toReply=" + toReply +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getToReplyId() {
        return toReplyId;
    }

    public void setToReplyId(Integer toReplyId) {
        this.toReplyId = toReplyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getInitTime() {
        return initTime;
    }

    public void setInitTime(Date initTime) {
        this.initTime = initTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReplyVO getToReply() {
        return toReply;
    }

    public void setToReply(ReplyVO toReply) {
        this.toReply = toReply;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getSmallHeadPhoto() {
        return smallHeadPhoto;
    }

    public void setSmallHeadPhoto(String smallHeadPhoto) {
        this.smallHeadPhoto = smallHeadPhoto;
    }
}
