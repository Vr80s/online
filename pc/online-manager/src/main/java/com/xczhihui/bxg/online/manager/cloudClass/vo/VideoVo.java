package com.xczhihui.bxg.online.manager.cloudClass.vo;

import java.util.Date;

public class VideoVo {
	
	 	private Integer courseId;
	   private Integer status;

	    private Integer sort;

	    private String chapterId;

	    private String name;

	    private String videoId;

	    private Date videoTime;

	    private String videoSize;
	    
	    private Boolean isTryLearn;

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

		public Date getVideoTime() {
			return videoTime;
		}

		public void setVideoTime(Date videoTime) {
			this.videoTime = videoTime;
		}

		public String getVideoSize() {
			return videoSize;
		}

		public void setVideoSize(String videoSize) {
			this.videoSize = videoSize;
		}
	    
		public Boolean getIsTryLearn() {
			return isTryLearn;
		}

		public void setIsTryLearn(Boolean isTryLearn) {
			this.isTryLearn = isTryLearn;
		}

		public Integer getCourseId() {
			return courseId;
		}

		public void setCourseId(Integer courseId) {
			this.courseId = courseId;
		}
		
		
}
