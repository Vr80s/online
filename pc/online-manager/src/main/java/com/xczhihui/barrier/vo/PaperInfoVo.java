package com.xczhihui.barrier.vo;

import java.util.List;

/**
 * @Author Fudong.Sun【】
 * @Date 2016/12/22 14:36
 */
public class PaperInfoVo {
    /**
     * 题型
     */
    private Integer question_type;
    /**
     * 总题数
     */
    private Integer total_count;
    /**
     * 正确题数
     */
    private Integer right_count;
    /**
     * 错误题数
     */
    private Integer wrong_count;
    /**
     * 总分数
     */
    private Integer total_score;
    /**
     * 正确分数
     */
    private Integer right_score;
    /**
     * 试题
     */
    private List<ExamPaperVo> examPaper;

    public Integer getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(Integer question_type) {
        this.question_type = question_type;
    }

    public Integer getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }

    public Integer getRight_count() {
        return right_count;
    }

    public void setRight_count(Integer right_count) {
        this.right_count = right_count;
    }

    public Integer getTotal_score() {
        return total_score;
    }

    public void setTotal_score(Integer total_score) {
        this.total_score = total_score;
    }

    public Integer getRight_score() {
        return right_score;
    }

    public void setRight_score(Integer right_score) {
        this.right_score = right_score;
    }

    public List<ExamPaperVo> getExamPaper() {
        return examPaper;
    }

    public void setExamPaper(List<ExamPaperVo> examPaper) {
        this.examPaper = examPaper;
    }

    public Integer getWrong_count() {
        return wrong_count;
    }

    public void setWrong_count(Integer wrong_count) {
        this.wrong_count = wrong_count;
    }
}
