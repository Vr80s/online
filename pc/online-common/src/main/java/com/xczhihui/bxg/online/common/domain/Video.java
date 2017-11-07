package com.xczhihui.bxg.online.common.domain;

import com.xczhihui.bxg.common.support.domain.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
/**
 *  视屏实体类
 * @author yxd
 */
@Entity
@Table(name = "oe_video")
public class Video extends BasicEntity implements Serializable{
	    @Column(name = "status")//1已启用  0已禁用
	    private Integer status;
	    @Column(name = "sort")//排序字段
	    private Integer sort;
	    @Column(name = "chapter_id")//章节表ID
	    private String chapterId;
	    @Column(name = "name")//视频名
	    private String name;
	    @Column(name = "video_id")//视频ID
	    private String videoId;
	    @Column(name = "video_time")//视频时长
	    private String videoTime;
	    @Column(name = "video_size")//视频大小
	    private String videoSize;
	    @Column(name = "course_id")
	    private Integer courseId;
	    @Column(name = "is_try_learn")
	    private Boolean isTryLearn;
	    @Column(name = "video_version")//版本号
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
