package com.xczhihui.course.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by admin on 2016/8/2.
 */
public class GradeVo {

	/**
	 * 课程ID号
	 */
	private Integer id;
	/**
	 * 班级名
	 */
	private String name;

	/**
	 * 课程名
	 */
	private String courseName;
	/**
	 * 课程编号
	 */
	private String courseId;
	/**
	 * 模板
	 */
	private String classTemplate;
	/**
	 * 讲师
	 */
	private String role_type1;

	/**
	 * 班主任
	 */
	private String role_type2;

	/*
	 * 助教
	 */
	private String role_type3;

	/**
	 * 班级号
	 */
	private String nameNumber;

	/**
	 * 授课方式
	 */
	private String teachMethodId;

	/**
	 * 授课方式
	 */
	private String teachMethodName;

	private String scoreTypeName;
	private String courseNameTmp;

	private Integer workDaySum;

	private Integer restDaySum;

	/**
	 * 开课时间
	 */

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-d HH:mm:ss", timezone = "GMT+8")
	private Date curriculumTime;

	@DateTimeFormat(pattern = "yyyy-M-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date stopTime;
	/**
	 * 班级状态
	 */
	private Integer status;
	/**
	 * 剩余席位书
	 */
	private Integer seat;
	/**
	 * 课程名称
	 */
	private String couseName;

	/**
	 * 班级状态 :0禁用 1启用
	 */
	private Integer gradeStatus;

	/**
	 * 课程类别
	 */
	private String scoreTypeId;

	/**
	 * 联系QQ
	 */
	private String qqno;

	/**
	 * 联系QQ
	 */
	private Integer sort;

	/**
	 * 课程id号
	 */
	private Integer course_id;

	/**
	 * 班级人数
	 */
	private Integer studentCount;

	/**
	 * 学科名
	 */
	private String menuName;

	/**
	 * 学科编号
	 */
	private String menuId;

	/**
	 * 开班状态,根据当前时间进行判断,如果开课日期大于当前时间为已开班,否则未开班
	 */
	private Integer otcStatus;
	/**
	 * 班级额定人数
	 */
	private Integer studentAmount;

	/**
	 * 是否有计划
	 */
	private Integer hasPlan;

	private Integer defaultStudentCount;

	private Integer paperCount;// 试卷数量

	public Integer getPaperCount() {
		return paperCount;
	}

	public void setPaperCount(Integer paperCount) {
		this.paperCount = paperCount;
	}

	// 模板配置的天数
	private Integer defaultTemplateCount;

	@JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
	private Date createTime;

	public Integer getOtcStatus() {
		return otcStatus;
	}

	public void setOtcStatus(Integer otcStatus) {
		this.otcStatus = otcStatus;
	}

	public Integer getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(Integer studentCount) {
		this.studentCount = studentCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getCouseName() {
		return couseName;
	}

	public void setCouseName(String couseName) {
		this.couseName = couseName;
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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Integer getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Integer course_id) {
		this.course_id = course_id;
	}

	public String getRole_type1() {
		return role_type1;
	}

	public void setRole_type1(String role_type1) {
		this.role_type1 = role_type1;
	}

	public String getRole_type2() {
		return role_type2;
	}

	public void setRole_type2(String role_type2) {
		this.role_type2 = role_type2;
	}

	public String getRole_type3() {
		return role_type3;
	}

	public void setRole_type3(String role_type3) {
		this.role_type3 = role_type3;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getNameNumber() {
		return nameNumber;
	}

	public String getClassTemplate() {
		return classTemplate;
	}

	public void setClassTemplate(String classTemplate) {
		this.classTemplate = classTemplate;
	}

	public void setNameNumber(String nameNumber) {
		this.nameNumber = nameNumber;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getScoreTypeId() {
		return scoreTypeId;
	}

	public void setScoreTypeId(String scoreTypeId) {
		this.scoreTypeId = scoreTypeId;
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

	public String getScoreTypeName() {
		return scoreTypeName;
	}

	public void setScoreTypeName(String scoreTypeName) {
		this.scoreTypeName = scoreTypeName;
	}

	public String getCourseNameTmp() {
		return courseNameTmp;
	}

	public void setCourseNameTmp(String courseNameTmp) {
		this.courseNameTmp = courseNameTmp;
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

	public Integer getHasPlan() {
		return hasPlan;
	}

	public void setHasPlan(Integer hasPlan) {
		this.hasPlan = hasPlan;
	}

	public Integer getDefaultStudentCount() {
		return defaultStudentCount;
	}

	public void setDefaultStudentCount(Integer defaultStudentCount) {
		this.defaultStudentCount = defaultStudentCount;
	}

	public Integer getDefaultTemplateCount() {
		return defaultTemplateCount;
	}

	public void setDefaultTemplateCount(Integer defaultTemplateCount) {
		this.defaultTemplateCount = defaultTemplateCount;
	}
}
