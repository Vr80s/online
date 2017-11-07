package com.xczhihui.bxg.online.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.bxg.common.support.domain.BasicEntity;

@Entity
@Table(name = "oe_barrier")
public class Barrier  extends BasicEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "course_id")
	private String courseId;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "total_score")
    private Integer totalScore;
	
	@Column(name = "limit_time")
    private Integer limitTime;
	
	@Column(name = "pass_score_percent")
    private Integer passScorePercent;
	
	@Column(name = "parent_id")
    private String parentId;
	
	@Column(name = "kpoint_id")
    private String kpointId;
	
	@Column(name = "status")
    private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Integer limitTime) {
		this.limitTime = limitTime;
	}

	public Integer getPassScorePercent() {
		return passScorePercent;
	}

	public void setPassScorePercent(Integer passScorePercent) {
		this.passScorePercent = passScorePercent;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getKpointId() {
		return kpointId;
	}

	public void setKpointId(String kpointId) {
		this.kpointId = kpointId;
	}
}
