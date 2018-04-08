package com.xczhihui.course.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class VideoResVo extends OnlineBaseVo{
	private String id;
	/**
     * 课程ID 
     */
    private Integer courseId;
    
    private Integer status;

    private Integer sort;

    private String chapterId;

    private String name;

    private String videoId;

    private String videoTime;

    private String videoSize;
    
    private String level;
    
    private Boolean isTryLearn;

    private String videoVersion;
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

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoTime() {
		return videoTime;
	}

	public void setVideoTime(String videoTime) {
		this.videoTime = videoTime;
	}

	public String getVideoSize() {
		return videoSize;
	}

	public void setVideoSize(String videoSize) {
		this.videoSize = videoSize;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsTryLearn() {
		return isTryLearn;
	}

	public void setIsTryLearn(Boolean isTryLearn) {
		this.isTryLearn = isTryLearn;
	}

	public String getVideoVersion() {
		return videoVersion;
	}

	public void setVideoVersion(String videoVersion) {
		this.videoVersion = videoVersion;
	}
    
    
	
}
