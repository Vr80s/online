package com.xczhihui.bxg.online.manager.homework.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author Fudong.Sun【】
 * @Date 2017/3/2 9:04
 */
public class ClassPaperVo {

    /**
     * ID
     */
    private String id;

    /**
     *  班级ID
     */
    private String class_id;

    /**
     *  课程ID
     */
    private String course_id;

    /**
     *  试卷ID
     */
    private String paper_id;

    /**
     *  状态
     */
    private Integer status;

    /**
     *  开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date start_time;

    /**
     *  结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date end_time;

    /**
     * 试卷名称
     */
    private String paperName;

    /**
     * 交卷人数
     */
    private Integer sendCount;

    /**
     * 总人数
     */
    private Integer totalCount;

    /**
     * 难度
     */
    private String difficulty;

    /**
     * 平均分
     */
    private Double averageScore;

    /**
     * 未批阅人数
     */
    private Integer unReadOver;

    public Integer getUnReadOver() {
        return unReadOver;
    }

    public void setUnReadOver(Integer unReadOver) {
        this.unReadOver = unReadOver;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(String paper_id) {
        this.paper_id = paper_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
}
