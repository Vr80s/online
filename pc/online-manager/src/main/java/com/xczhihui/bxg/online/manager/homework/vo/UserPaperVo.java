package com.xczhihui.bxg.online.manager.homework.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author Fudong.Sun【】
 * @Date 2017/3/2 16:07
 */
public class UserPaperVo {
    private String id;
    /**
     * 学员id
     */
    private String user_id;
    /**
     * 班级ID
     */
    private String classId;
    /**
     * 班级试卷id
     */
    private String class_paper_id;
    /**
     * 得分
     */
    private Double score;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 交卷时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date submit_time;
    /**
     * 学员名
     */
    private String studentName;
    /**
     * 学号
     */
    private String student_number;

    /**
     * 耗时
     */
    private String expendTime;

    /**
     * 排序类型
     */
    private String sortType;

    /**
     * 排序字段
     */
    private String sortName;

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getExpendTime() {
        return expendTime;
    }

    public void setExpendTime(String expendTime) {
        this.expendTime = expendTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getClass_paper_id() {
        return class_paper_id;
    }

    public void setClass_paper_id(String class_paper_id) {
        this.class_paper_id = class_paper_id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(Date submit_time) {
        this.submit_time = submit_time;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
