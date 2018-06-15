package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "live_appeal")
public class LiveAppealInfo  implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "appeal_reason")
    private String appealReason;  //申诉理由

	@Column(name = "appeal_time") 
    private Date appealTime;      //申诉时间

	@Column(name = "examine_id")  //申请id
    private String examineId;
	
	@Column(name = "reviewer_time")   
    private Date reviewerTime;    //审核时间
	
	@Column(name = "against_reason")   
    private String againstReason;    //驳回理由
	
	@Column(name = "reviewer_person")   
    private String reviewerPerson;   //审核人
	
	/**
	 * 实体是否删除
	 */
	@Column(name = "is_delete")
	private boolean isDelete;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppealReason() {
		return appealReason;
	}

	public void setAppealReason(String appealReason) {
		this.appealReason = appealReason;
	}

	public Date getAppealTime() {
		return appealTime;
	}

	public void setAppealTime(Date appealTime) {
		this.appealTime = appealTime;
	}


	public String getExamineId() {
		return examineId;
	}

	public void setExamineId(String examineId) {
		this.examineId = examineId;
	}

	public Date getReviewerTime() {
		return reviewerTime;
	}

	public void setReviewerTime(Date reviewerTime) {
		this.reviewerTime = reviewerTime;
	}

	public String getAgainstReason() {
		return againstReason;
	}

	public void setAgainstReason(String againstReason) {
		this.againstReason = againstReason;
	}

	public String getReviewerPerson() {
		return reviewerPerson;
	}

	public void setReviewerPerson(String reviewerPerson) {
		this.reviewerPerson = reviewerPerson;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	
	
}