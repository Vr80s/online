package com.xczhihui.cloudClass.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LiveAppealInfoVo implements Serializable{
	
    private Integer id;

    private String appealReason; //申诉理由

    private String examineId;  //审查id
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date appealTime;   //申诉时间
    
    private String reviewerPerson;//审核人
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reviewerTime;    //审核时间
	
    private String againstReason; //驳回理由
    
    
    private String name; //审核人名字
    
    
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

	public String getReviewerPerson() {
		return reviewerPerson;
	}

	public void setReviewerPerson(String reviewerPerson) {
		this.reviewerPerson = reviewerPerson;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}