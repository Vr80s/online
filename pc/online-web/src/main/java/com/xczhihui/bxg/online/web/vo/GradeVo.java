package com.xczhihui.bxg.online.web.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * Created by admin on 2016/7/27.
 */
public class GradeVo extends OnlineBaseVo {

    /**
     * 班级id
     */
    private Integer id;

    /**
     * 班级名
     */
    private String name;
    /**
     *开课时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp curriculumTime;

    /**
     *班级状态
     */
    private Integer status;
    /**
     * 剩余席位书
     */
    private Integer seat;

    /**
     * 已报名人数
     */
    private String studentCount;

    /**
     * 班级额定人数
     */
    private Integer student_amount;

    /**
     * 是否报名: true:已报名 false:未报名
     */
    private Boolean isApply = false;

    /**
     * 是否开班: true:开班(进行中) false:未开班(已完成)
     */
    private Boolean isOpenClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCurriculumTime() {
        return curriculumTime;
    }

    public void setCurriculumTime(Timestamp curriculumTime) {
        this.curriculumTime = curriculumTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean isApply() {
        return isApply;
    }

    public void setIsApply(Boolean isApply) {
        this.isApply = isApply;
    }

    public Integer getStudent_amount() {
        return student_amount;
    }

    public void setStudent_amount(Integer student_amount) {
        this.student_amount = student_amount;
    }


    public String getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(String studentCount) {
        this.studentCount = studentCount;
    }

    public Boolean isOpenClass() {
        return isOpenClass;
    }

    public void setIsOpenClass(Boolean isOpenClass) {
        this.isOpenClass = isOpenClass;
    }
}
