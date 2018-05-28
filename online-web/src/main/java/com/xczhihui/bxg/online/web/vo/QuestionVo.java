package com.xczhihui.bxg.online.web.vo;/**
 * Created by admin on 2016/8/29.
 */

/**
 * 问题返回结果封装类
 *
 * @author 康荣彩
 * @create 2016-08-29 16:26
 */
public class QuestionVo {

    /**
     * 课程id号
     */
    private Integer courseId;

    /**
     * 问/答信息
     */
    private String questionName;

    /**
     * 问题及回答的id值
     */
    private String id;

    /**
     * 问题的回答信息
     */
    private String answers;


    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
}
