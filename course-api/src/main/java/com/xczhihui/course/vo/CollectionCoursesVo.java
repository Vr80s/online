package com.xczhihui.course.vo;


import java.io.Serializable;


/**
 * Created by admin on 2016/7/27.
 */
public class CollectionCoursesVo implements Serializable {
   
	
	private Integer id;
	private String subtitle;
	private String gradeName;
	private String courseLength; // 00:00:00
	private String directId;
	private String name;
	
	private String duration; //分钟

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getCourseLength() {
		return courseLength;
	}

	public void setCourseLength(String courseLength) {
		this.courseLength = courseLength;
	}

	public String getDirectId() {
		return directId;
	}

	public void setDirectId(String directId) {
		this.directId = directId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
}
