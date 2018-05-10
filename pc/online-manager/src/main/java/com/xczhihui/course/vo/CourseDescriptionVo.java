package com.xczhihui.course.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class CourseDescriptionVo extends OnlineBaseVo {

	private String id;
	private java.util.Date createTime;
	private Integer status;
	private Integer sort;
	private Integer courseId;
	private Integer desCourseId;
	private String courseTitle;
	private String courseContent;
	private boolean preview;
	private String courseTitlePreview;
	private String courseContentPreview;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public java.util.Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
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

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public boolean getPreview() {
		return preview;
	}

	public void setPreview(boolean preview) {
		this.preview = preview;
	}

	public String getCourseTitlePreview() {
		return courseTitlePreview;
	}

	public void setCourseTitlePreview(String courseTitlePreview) {
		if (courseTitlePreview == null) {
			courseTitlePreview = "";
		}
		this.courseTitlePreview = courseTitlePreview;
	}

	public Integer getDesCourseId() {
		return desCourseId;
	}

	public void setDesCourseId(Integer desCourseId) {
		this.desCourseId = desCourseId;
	}

	public String getCourseContent() {
		return courseContent;
	}

	public void setCourseContent(String courseContent) {
		if (courseContent == null) {
			courseContent = "";
		}
		this.courseContent = courseContent;
	}

	public String getCourseContentPreview() {
		return courseContentPreview;
	}

	public void setCourseContentPreview(String courseContentPreview) {
		if (courseContentPreview == null) {
			courseContentPreview = "";
		}
		this.courseContentPreview = courseContentPreview;
	}
}
