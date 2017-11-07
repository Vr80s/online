package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.bxg.common.support.domain.BasicEntity;

/**
 * 班级-讲师中间表
 * @author yxd
 */
@Entity
@Table(name = "grade_r_lecturer")
public class GradeRLecturer extends BasicEntity implements Serializable {

	// Fields

	@Column(name = "grade_id")
	private String gradeId;
	@Column(name = "lecturer_id")
	private String lecturerId;
	@Column(name = "course_id")
	private String courseId;
	public String getGradeId() {
		return gradeId;
	}
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	public String getLecturerId() {
		return lecturerId;
	}
	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}




}