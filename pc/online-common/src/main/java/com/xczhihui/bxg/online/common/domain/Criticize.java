package com.xczhihui.bxg.online.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.bxg.common.support.domain.BasicEntity;

@Entity
@Table(name = "oe_criticize")
public class Criticize extends BasicEntity  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6921285561593657883L;
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "sort")
	private Integer sort;
    
	@Column(name = "content")
	private String content;
	
	@Column(name = "user_id")
    private String userId;
	
	@Column(name = "chapter_id")
    private String chapterId;
	
	@Column(name = "video_id")
    private String videoId;
	
	@Column(name = "star_level")
    private Float starLevel;
	
	@Column(name = "praise_sum")
    private Integer praiseSum;
    
	@Column(name = "praise_login_names")
	private String praiseLoginNames;
	
	@Column(name = "response")
	private String response;
	
	@Column(name = "response_time")
	private Date responseTime;

	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
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
}
