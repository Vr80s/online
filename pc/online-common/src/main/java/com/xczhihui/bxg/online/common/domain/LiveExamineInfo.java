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
@Table(name = "live_examine_info")
public class LiveExamineInfo  implements Serializable{
	
	@Id
	private String id;

	@Column(name = "title")
    private String title;

	@Column(name = "content")
    private String content;

	@Column(name = "type")
    private String type;

	@Column(name = "see_mode")
    private String seeMode;

	@Column(name = "start_time")
    private Date startTime;

	@Column(name = "when_long")
    private String whenLong;

	@Column(name = "user_id")
    private String userId;

	@Column(name = "examine_status")
    private String examineStatus;

	@Column(name = "is_free")
    private String isFree;

	@Column(name = "password")
    private String password;

	@Column(name = "price")
    private BigDecimal price;

	@Column(name = "logo")
    private String logo;
	
	
	//is_push_notice  is_send_phone_message    reviewer_time  reviewer_person  

	@Column(name = "is_push_notice")
	private  Boolean isPushNotice;  
	
	@Column(name = "is_send_phone_message")
	private Boolean isSendPhoneMessage;
	
	@Column(name = "audit_person")
    private String auditPerson;
	
	@Column(name = "audit_time")
    private Date auditTime;
	/**
	 * 实体是否删除
	 */
	@Column(name = "is_delete")
	private 	Boolean isDelete;
	
	@Column(name = "end_time")
    private Date endTime;

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

	public Boolean getIsPushNotice() {
		return isPushNotice;
	}

	public void setIsPushNotice(Boolean isPushNotice) {
		this.isPushNotice = isPushNotice;
	}

	public Boolean getIsSendPhoneMessage() {
		return isSendPhoneMessage;
	}

	public void setIsSendPhoneMessage(Boolean isSendPhoneMessage) {
		this.isSendPhoneMessage = isSendPhoneMessage;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}