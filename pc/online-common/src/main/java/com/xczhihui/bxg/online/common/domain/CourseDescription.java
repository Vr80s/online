package com.xczhihui.bxg.online.common.domain;
import com.xczhihui.bxg.common.support.domain.BasicEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "oe_course_description")
public class CourseDescription extends BasicEntity  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2018311364181300284L;

	@Column(name = "status")
    private Integer status;
	
	@Column(name = "sort")
    private Integer sort;
	
	@Column(name = "course_id")
    private Integer courseId;
	
	@Column(name = "course_title")
    private String courseTitle;
	
	@Column(name = "course_content")
	@Type(type="text")
	private String courseContent;
	
	@Column(name = "preview")
    private Boolean preview;
	
	@Column(name = "course_title_preview")
    private String courseTitlePreview;
	
	@Column(name = "course_content_preview")
	@Type(type="text")
    private String courseContentPreview;

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

	public String getCourseContent() {
		return courseContent;
	}

	public void setCourseContent(String courseContent) {
		this.courseContent = courseContent;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Boolean isPreview() {
		return preview;
	}

	public void setPreview(Boolean preview) {
		this.preview = preview;
	}

	public String getCourseTitlePreview() {
		return courseTitlePreview;
	}

	public void setCourseTitlePreview(String courseTitlePreview) {
		this.courseTitlePreview = courseTitlePreview;
	}

	public String getCourseContentPreview() {
		return courseContentPreview;
	}

	public void setCourseContentPreview(String courseContentPreview) {
		this.courseContentPreview = courseContentPreview;
	}

}