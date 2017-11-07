package com.xczhihui.bxg.online.manager.cloudClass.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LiveExamineInfoVo implements Serializable{
	
    private String id;
    
    private Integer appId;

    private String title;    //活动标题，那就是公开课名称

    private String content;  //内容

    private String type;     

    private String seeMode;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    private String whenLong;

    private String userId;

    private String examineStatus;

    private String isFree;

    private String password;

    private BigDecimal price;

    private String logo;
    
    //private String courseName;//课程名字
    
    private Integer menuId;//学科类型id
    
    private String menuName; //学科名字
    
    private String lecturerName; //讲师名字
    
    private int examineId;  //审查id
    
    
    private String appealReason; //申诉理由

    //private int examineId;  //审查id
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date appealTime;   //申诉时间
    
    private String reviewerPerson;//审核人
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reviewerTime;    //审核时间
	
    private String againstReason; //驳回理由
    
    
    
    private String auditPerson;  //审核人id
    
    private String auditPersonStr; //审核人名字
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date auditTime;     //审核时间
    
    
    @JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
    private Date s_startTime;
    @JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
    private Date s_endTime;
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSeeMode() {
        return seeMode;
    }

    public void setSeeMode(String seeMode) {
        this.seeMode = seeMode == null ? null : seeMode.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getWhenLong() {
        return whenLong;
    }

    public void setWhenLong(String whenLong) {
        this.whenLong = whenLong == null ? null : whenLong.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getExamineStatus() {
        return examineStatus;
    }

    public void setExamineStatus(String examineStatus) {
        this.examineStatus = examineStatus == null ? null : examineStatus.trim();
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree == null ? null : isFree.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getLecturerName() {
		return lecturerName;
	}

	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getAuditPerson() {
		return auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public int getExamineId() {
		return examineId;
	}

	public void setExamineId(int examineId) {
		this.examineId = examineId;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
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

	public Date getS_startTime() {
		return s_startTime;
	}

	public void setS_startTime(Date s_startTime) {
		this.s_startTime = s_startTime;
	}

	public Date getS_endTime() {
		return s_endTime;
	}

	public void setS_endTime(Date s_endTime) {
		this.s_endTime = s_endTime;
	}

	public String getAuditPersonStr() {
		return auditPersonStr;
	}

	public void setAuditPersonStr(String auditPersonStr) {
		this.auditPersonStr = auditPersonStr;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
	
}