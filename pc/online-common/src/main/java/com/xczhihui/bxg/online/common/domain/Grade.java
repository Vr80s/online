package com.xczhihui.bxg.online.common.domain;

import com.xczhihui.bxg.common.support.domain.BasicEntity2;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 班级实体类
 * @author Rongcai Kang
 */
@Entity
@Table(name = "oe_grade")
public class Grade extends BasicEntity2 implements Serializable {

	private static final long serialVersionUID = -3378971760825032909L;

	/**
	 * 课程ID号
	 */
	@Column(name = "course_id")
	private String courseId;
	/**
	 * 班级名
	 */
	@Column(name = "name")
	private String name;
	/**
	 *开课时间
	 */

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "curriculum_time")
	private Date curriculumTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "stop_time")
	private Date stopTime;
	/**
	 *班级状态   0:开始报名  1:报名中  2:报名结束
	 */
	@Column(name = "status")
	private Integer status;

	/**
	 *班级状态 :0禁用 1启用
	 */
	@Column(name = "grade_status")
	private Integer gradeStatus;

	/**
	 * 剩余席位书
	 */
	@Column(name = "seat")
	private Integer seat;
	/**
	 * qq号
	 */
	@Column(name = "qqno")
	private String qqno;

	/**
	 * 排序字段
	 */
	@Column(name = "sort")
	private Integer sort;

	/**
	 * 班级人数
	 */
	@Column(name = "student_count")
	private Integer studentCount;
	/**
	 * 班级额定人数
	 */
	@Column(name = "student_amount")
	private Integer studentAmount;
	
	@Column(name = "work_day_sum")
	private Integer workDaySum;
	
	@Column(name = "rest_day_sum")
	private Integer restDaySum;
	
	@Column(name = "default_student_count")
	private Integer defaultStudentCount;
	
	public Integer getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(Integer studentCount) {
		this.studentCount = studentCount;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCurriculumTime() {
		return curriculumTime;
	}

	public void setCurriculumTime(Date curriculumTime) {
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

	public String getQqno() {
		return qqno;
	}

	public void setQqno(String qqno) {
		this.qqno = qqno;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getGradeStatus() {
		return gradeStatus;
	}

	public void setGradeStatus(Integer gradeStatus) {
		this.gradeStatus = gradeStatus;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public Integer getStudentAmount() {
		return studentAmount;
	}

	public void setStudentAmount(Integer studentAmount) {
		this.studentAmount = studentAmount;
	}

	public Integer getWorkDaySum() {
		return workDaySum;
	}

	public void setWorkDaySum(Integer workDaySum) {
		this.workDaySum = workDaySum;
	}

	public Integer getRestDaySum() {
		return restDaySum;
	}

	public void setRestDaySum(Integer restDaySum) {
		this.restDaySum = restDaySum;
	}

	public Integer getDefaultStudentCount() {
		return defaultStudentCount;
	}

	public void setDefaultStudentCount(Integer defaultStudentCount) {
		this.defaultStudentCount = defaultStudentCount;
	}
}
