package com.xczhihui.bxg.online.manager.homework.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author Fudong.Sun【】
 * @Date 2017/3/2 11:49
 */
public class PaperVo {
    private String id;

    /**
     * 类型，0阶段作业，1阶段考试
     */
    private Integer type;

    /**
     * 课程id
     */
    private String course_id;
    /**
     * 作业卷名称
     */
    private String paperName;
    /**
     * 难度级别
     */
    private String difficult;
    /**
     * 试卷总分
     */
    private Double score;

    /**
     * 时长
     */
    private Integer duration;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     *  创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 使用次数
     */
    private Integer useSum;

    public Integer getUseSum() {
        return useSum;
    }

    public void setUseSum(Integer useSum) {
        this.useSum = useSum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
