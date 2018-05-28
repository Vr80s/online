package com.xczhihui.bxg.online.common.domain;

import com.xczhihui.common.support.domain.BasicEntity;

import javax.persistence.Column;
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
public class ApplyGradeCourse   extends BasicEntity {


    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "grade_id")
    private Integer gradeId;

    @Column(name = "apply_id")
    private String applyId;

    @Column(name = "is_payment")
    private String isPayment;
    
    @Column(name = "user_id")
    private String userId;

    /**
     * 学号
     */
    @Column(name = "student_number")
    private String  studentNumber;

    /**
     * 课程费用
     */
    @Column(name = "cost")
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
    
}