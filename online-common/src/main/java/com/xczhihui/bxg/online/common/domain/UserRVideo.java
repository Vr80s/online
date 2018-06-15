package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.common.support.domain.BasicEntity;
/**
 *  用户视频中间表实体类
 * @author yxd
 */
@Entity
@Table(name = "user_r_video")
public class UserRVideo extends BasicEntity implements Serializable{
	 	@Column(name = "status")
	    private Integer status;
	 	@Column(name = "sort")
	    private Integer sort;
	 	@Column(name = "video_id")
	    private String videoId;
	 	@Column(name = "user_id")
	    private String userId;
	 	@Column(name = "apply_id")
	    private String applyId;
	 	@Column(name = "study_status")
	    private Integer studyStatus;
	 	@Column(name = "collection")
	    private Boolean collection;
	 	@Column(name = "course_id")
	    private Integer courseId;
	 	@Column(name = "last_learn_time")
	    private Date lastLearnTime;
	 	
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
		public String getVideoId() {
			return videoId;
		}
		public void setVideoId(String videoId) {
			this.videoId = videoId;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getApplyId() {
			return applyId;
		}
		public void setApplyId(String applyId) {
			this.applyId = applyId;
		}
		public Integer getStudyStatus() {
			return studyStatus;
		}
		public void setStudyStatus(Integer studyStatus) {
			this.studyStatus = studyStatus;
		}
		public Boolean getCollection() {
			return collection;
		}
		public void setCollection(Boolean collection) {
			this.collection = collection;
		}
		public Integer getCourseId() {
			return courseId;
		}
		public void setCourseId(Integer courseId) {
			this.courseId = courseId;
		}
		public Date getLastLearnTime() {
			return lastLearnTime;
		}
		public void setLastLearnTime(Date lastLearnTime) {
			this.lastLearnTime = lastLearnTime;
		}
	 	
	 	
}
