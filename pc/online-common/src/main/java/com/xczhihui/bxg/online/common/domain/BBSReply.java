package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 回复
 *
 * @author hejiwei
 */
@Table(name = "quark_reply")
@Entity
public class BBSReply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //回复的内容
    @Column(columnDefinition = "text", nullable = false)
    private String content;

    //回复时间
    @Column(name = "init_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date initTime;

    //点赞个数
    private Integer up = 0;

    //与话题的关联关系
    @ManyToOne
    @JoinColumn(nullable = false, name = "posts_id")
    private BBSPost post;

    //与用户的关联关系
    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private OnlineUser ou;

    //是否删除这个回复
    @Column(name = "is_delete")
    private boolean isDelete;

    @Column(name = "to_reply_id")
    private Integer toReplyId;

    @Transient
    private BBSReply toReply;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getUp() {
        return up;
    }

    public void setUp(Integer up) {
        this.up = up;
    }

    public BBSPost getPost() {
        return post;
    }

    public void setPost(BBSPost post) {
        this.post = post;
    }

    public OnlineUser getOu() {
        return ou;
    }

    public void setOu(OnlineUser ou) {
        this.ou = ou;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public Integer getToReplyId() {
        return toReplyId;
    }

    public void setToReplyId(Integer toReplyId) {
        this.toReplyId = toReplyId;
    }

    public BBSReply getToReply() {
        return toReply;
    }

    public void setToReply(BBSReply toReply) {
        this.toReply = toReply;
    }
}
