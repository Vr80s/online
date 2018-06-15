package com.xczhihui.bxg.online.web.body.bbs;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.xczhihui.medical.bbs.model.Reply;

/**
 * @author hejiwei
 */
public class ReplyBody {

    private int postId;

    @NotBlank(message = "回复内容不能为空")
    private String content;

    /**
     * 如果是对回复进行的回复，这就是被回复的id
     */
    private Integer targetReplyId;

    public Reply build(String userId) {
        Reply reply = new Reply();
        reply.setContent(this.content);
        reply.setInitTime(new Date());
        reply.setPostId(postId);
        reply.setUserId(userId);
        reply.setToReplyId(targetReplyId);
        reply.setUp(0);
        return reply;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTargetReplyId() {
        return targetReplyId;
    }

    public void setTargetReplyId(Integer targetReplyId) {
        this.targetReplyId = targetReplyId;
    }
}
