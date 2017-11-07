package com.xczhihui.bxg.online.api.po;
// default package


import java.math.BigDecimal;
import java.util.Date;

public class LiveExamineInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String content;
	private String type;
	private String seeMode;
	private Date startTime;
	private String whenLong;
	private String userId;
	private String examineStatus;
	private String isFree;
	private String password;
	private BigDecimal price;
	private String logo;
	private Boolean isPushNotice;
	private Boolean isSendPhoneMessage;
	private Date endTime;

	// Constructors

	/** default constructor */
	public LiveExamineInfo() {
	}

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
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSeeMode() {
		return seeMode;
	}

	public void setSeeMode(String seeMode) {
		this.seeMode = seeMode;
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
		this.whenLong = whenLong;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getExamineStatus() {
		return examineStatus;
	}

	public void setExamineStatus(String examineStatus) {
		this.examineStatus = examineStatus;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		this.logo = logo;
	}

	public Boolean getPushNotice() {
		return isPushNotice;
	}

	public void setPushNotice(Boolean pushNotice) {
		isPushNotice = pushNotice;
	}

	public Boolean getSendPhoneMessage() {
		return isSendPhoneMessage;
	}

	public void setSendPhoneMessage(Boolean sendPhoneMessage) {
		isSendPhoneMessage = sendPhoneMessage;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}