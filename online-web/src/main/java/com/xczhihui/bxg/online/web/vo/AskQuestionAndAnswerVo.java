package com.xczhihui.bxg.online.web.vo;

/**
 * 点赞最多回答及问题信息(为院校项目准备，不要随意改动)
 */
public class AskQuestionAndAnswerVo {

    /**
     *问题id
     */
    private String questionId;

    /**
     *问题标题
     */
    private String  title;


    /**
     *回答内容
     */
    private String  content;

    /**
     * 回答的点赞数
     */
    private Integer praiseSum;

    /**
     * 回答者昵称
     */
    private String createNickName;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPraiseSum() {
        return praiseSum;
    }

    public void setPraiseSum(Integer praiseSum) {
        this.praiseSum = praiseSum;
    }

    public String getCreateNickName() {
        return createNickName;
    }

    public void setCreateNickName(String createNickName) {
        this.createNickName = createNickName;
    }
}
