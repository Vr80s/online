package com.xczhihui.bxg.online.manager.cloudClass.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StudentManagerVo extends OnlineBaseVo{

	 private String menuId;
	 private String menuName;
	 private String scoreTypeId;
	 private String scoreTypeName;
	 private String teachMethodId;
	 private String teachMethodName;
	 private String courseId;
	 private String courseName;
	 private String gradeId;
	 private String gradeName;
	 private String gradeCount;
	 private String LecturerId;
	 private String LecturerName;
	 private String assistantName;
	 private String HeadmasterName;
	 /**
	  *开课时间
	  */
    @DateTimeFormat(pattern = "yyyy-M-d HH:mm:ss")
    @JsonFormat(pattern = "yyyy-M-d HH:mm:ss", timezone = "GMT+8")
    private Date curriculumTime;

    @DateTimeFormat(pattern = "yyyy-M-d- HH:mm:ss")
    @JsonFormat(pattern = "yyyy-M-d- HH:mm:ss", timezone = "GMT+8")
    private Date stopTime;

    @JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
    private Date createTime;
    
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getScoreTypeId() {
		return scoreTypeId;
	}

	public void setScoreTypeId(String scoreTypeId) {
		this.scoreTypeId = scoreTypeId;
	}

	public String getScoreTypeName() {
		return scoreTypeName;
	}

	public void setScoreTypeName(String scoreTypeName) {
		this.scoreTypeName = scoreTypeName;
	}

	public String getTeachMethodId() {
		return teachMethodId;
	}

	public void setTeachMethodId(String teachMethodId) {
		this.teachMethodId = teachMethodId;
	}

	public String getTeachMethodName() {
		return teachMethodName;
	}

	public void setTeachMethodName(String teachMethodName) {
		this.teachMethodName = teachMethodName;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getGradeCount() {
		return gradeCount;
	}

	public void setGradeCount(String gradeCount) {
		this.gradeCount = gradeCount;
	}

	public String getLecturerId() {
		return LecturerId;
	}

	public void setLecturerId(String lecturerId) {
		LecturerId = lecturerId;
	}

	public String getLecturerName() {
		return LecturerName;
	}

	public void setLecturerName(String lecturerName) {
		LecturerName = lecturerName;
	}

	public String getAssistantName() {
		return assistantName;
	}

	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}

	public String getHeadmasterName() {
		return HeadmasterName;
	}

	public void setHeadmasterName(String headmasterName) {
		HeadmasterName = headmasterName;
	}

	public Date getCurriculumTime() {
		return curriculumTime;
	}

	public void setCurriculumTime(Date curriculumTime) {
		this.curriculumTime = curriculumTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	@Override
    public Date getCreateTime() {
		return createTime;
	}

	@Override
    public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	 
    
}
