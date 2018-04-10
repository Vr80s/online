package com.xczhihui.course.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CriticizeVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 实体是否删除
	 */
	private boolean isDelete;
	/**
	 * 创建人ID
	 */
	private String createPerson;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date createTime;

	private String id;
	private Integer status;
	private Integer sort;
	private String content;
	private String userId;
	private String chapterId;
	private String videoId;
	private Float starLevel;
	private Integer praiseSum;
	private String praiseLoginNames;
	private String courseId;
	private String createPersonName;
	private Integer gradeId;
	private Date startTime;
	private Date stopTime;
	private Integer sortType;
	private String gradeName;
	private String response;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	private Integer chapterLevel;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public Float getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(Float starLevel) {
		this.starLevel = starLevel;
	}

	public Integer getPraiseSum() {
		return praiseSum;
	}

	public void setPraiseSum(Integer praiseSum) {
		this.praiseSum = praiseSum;
	}

	public String getPraiseLoginNames() {
		return praiseLoginNames;
	}

	public void setPraiseLoginNames(String praiseLoginNames) {
		this.praiseLoginNames = praiseLoginNames;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCreatePersonName() {
		return createPersonName;
	}

	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
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

	public Integer getChapterLevel() {
		return chapterLevel;
	}

	public void setChapterLevel(Integer chapterLevel) {
		this.chapterLevel = chapterLevel;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
}
