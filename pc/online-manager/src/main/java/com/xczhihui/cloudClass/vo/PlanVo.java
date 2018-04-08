package com.xczhihui.cloudClass.vo;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class PlanVo extends OnlineBaseVo  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String templateId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planDate;
    private String week;
    private Integer day;
    private Integer chuanjiangHas;
    private String chuanjiangName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date chuanjiangStartTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date chuanjiangEndTime;
    private Double chuanjiangDuration;
    private Integer chuanjiangMode;
    private String chuanjiangRoomId;
    private String chuanjiangRoomLink;
    private Integer restHas;
    private Integer gradeId;
    private Integer courseId;
    private String gradeName;
    private String name;
    private String videoTime;
    private String chuanjiangLecturerId;
    private String microCourseIds;
    private Boolean chuanjiangisSend;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getChuanjiangHas() {
		return chuanjiangHas;
	}
	public void setChuanjiangHas(Integer chuanjiangHas) {
		this.chuanjiangHas = chuanjiangHas;
	}
	public String getChuanjiangName() {
		return chuanjiangName;
	}
	public void setChuanjiangName(String chuanjiangName) {
		this.chuanjiangName = chuanjiangName;
	}
	public java.util.Date getchuanjiangStartTime() {
		return chuanjiangStartTime;
	}
	public void setchuanjiangStartTime(java.util.Date chuanjiangStartTime) {
		this.chuanjiangStartTime = chuanjiangStartTime;
	}
	public java.util.Date getchuanjiangEndTime() {
		return chuanjiangEndTime;
	}
	public void setchuanjiangEndTime(java.util.Date chuanjiangEndTime) {
		this.chuanjiangEndTime = chuanjiangEndTime;
	}
	public Double getchuanjiangDuration() {
		return chuanjiangDuration;
	}
	public void setchuanjiangDuration(Double chuanjiangDuration) {
		this.chuanjiangDuration = chuanjiangDuration;
	}
	public Integer getChuanjiangMode() {
		return chuanjiangMode;
	}
	public void setChuanjiangMode(Integer chuanjiangMode) {
		this.chuanjiangMode = chuanjiangMode;
	}
	public String getChuanjiangRoomId() {
		return chuanjiangRoomId;
	}
	public void setChuanjiangRoomId(String chuanjiangRoomId) {
		this.chuanjiangRoomId = chuanjiangRoomId;
	}
	public String getChuanjiangRoomLink() {
		return chuanjiangRoomLink;
	}
	public void setChuanjiangRoomLink(String chuanjiangRoomLink) {
		this.chuanjiangRoomLink = chuanjiangRoomLink;
	}
	public Integer getRestHas() {
		return restHas;
	}
	public void setRestHas(Integer restHas) {
		this.restHas = restHas;
	}
	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVideoTime() {
		return videoTime;
	}
	public void setVideoTime(String videoTime) {
		this.videoTime = videoTime;
	}
	public String getChuanjiangLecturerId() {
		return chuanjiangLecturerId;
	}
	public void setChuanjiangLecturerId(String chuanjiangLecturerId) {
		this.chuanjiangLecturerId = chuanjiangLecturerId;
	}
	public String getMicroCourseIds() {
		return microCourseIds;
	}
	public void setMicroCourseIds(String microCourseIds) {
		this.microCourseIds = microCourseIds;
	}
	public Boolean getChuanjiangisSend() {
		return chuanjiangisSend;
	}
	public void setChuanjiangisSend(Boolean chuanjiangisSend) {
		this.chuanjiangisSend = chuanjiangisSend;
	}
}
