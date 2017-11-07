package com.xczhihui.bxg.online.manager.barrier.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author Fudong.Sun【】
 * @Date 2016/12/22 14:36
 */
public class MarkRecordVo {
    /**
     * 用户ID
     */
    private String id;
    /**
     * 学号
     */
    private String student_number;
    /**
     * 用户名
     */
    private String user_name;
    /**
     * 闯关分数记录
     */
    private String score_record;
    /**
     * 最近闯关时间
     */
    @JsonFormat(pattern = "yyyy-M-d HH:mm", timezone = "GMT+8")
    private Date create_time;
    /**
     * 试卷提交时间
     */
    @JsonFormat(pattern = "yyyy-M-d HH:mm", timezone = "GMT+8")
    private Date submit_time;
    /**
     * 耗时
     */
    private Integer expend_time;
    /**
     * 闯关次数
     */
    private Integer recore_num;
    /**
     * 总分
     */
    private Integer total_score;
    /**
     * 通关分数
     */
    private Integer pass_score_percent;
    /**
     * 通关状态
     */
    private Boolean barrier_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getScore_record() {
        return score_record;
    }

    public void setScore_record(String score_record) {
        this.score_record = score_record;
    }

    public Date getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(Date submit_time) {
        this.submit_time = submit_time;
    }

    public Integer getExpend_time() {
        return expend_time;
    }

    public void setExpend_time(Integer expend_time) {
        this.expend_time = expend_time;
    }

    public Integer getRecore_num() {
        return recore_num;
    }

    public void setRecore_num(Integer recore_num) {
        this.recore_num = recore_num;
    }

    public Integer getTotal_score() {
        return total_score;
    }

    public void setTotal_score(Integer total_score) {
        this.total_score = total_score;
    }

    public Integer getPass_score_percent() {
        return pass_score_percent;
    }

    public void setPass_score_percent(Integer pass_score_percent) {
        this.pass_score_percent = pass_score_percent;
    }

    public Boolean getBarrier_status() {
        return barrier_status;
    }

    public void setBarrier_status(Boolean barrier_status) {
        this.barrier_status = barrier_status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
