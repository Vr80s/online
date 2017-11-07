package com.xczhihui.bxg.online.web.vo;

import com.xczhihui.bxg.common.support.domain.BasicEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 学员信息与班级管理中间表的实体类
 *
 * @author 康荣彩
 * @create 2016-08-30 13:14
 */
@Entity
@Table(name = "apply_r_grade_course")
public class ApplyGradeCourseVo extends BasicEntity {


    private Integer courseId;

    private Integer gradeId;

    private String applyId;

    private String isPayment;

    private String  studentNumber;

    /**
     * 课程费用
     */
    private Double cost;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(String isPayment) {
        this.isPayment = isPayment == null ? null : isPayment.trim();
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}