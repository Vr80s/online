package com.xczh.consumer.market.body.article;

import java.util.Date;

import com.xczhihui.medical.headline.model.OeBxsAppraise;

/**
 * @author hejiwei
 */
public class AppraiseBody {

    private int articleId;

    private String content;

    private String replyId;

    public OeBxsAppraise build(String userId) {
        OeBxsAppraise oeBxsAppraise = new OeBxsAppraise();
        oeBxsAppraise.setArticleId(articleId);
        oeBxsAppraise.setContent(content);
        oeBxsAppraise.setReplyCommentId(replyId);
        oeBxsAppraise.setUserId(userId);
        oeBxsAppraise.setCreateTime(new Date());
        oeBxsAppraise.setIsDelete(false);
        return oeBxsAppraise;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }
}
