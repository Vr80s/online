package com.xczh.consumer.market.bean;
// default package

import java.sql.Timestamp;

public class Gift implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer giftId;
	private String name;
	private String createPerson;
	private Timestamp createTime;
	private Boolean isDelete;
	private String description;
	private String smallimgPath;
	private Integer sort;
	private String status;
	private Double price;
	private Boolean isFree;
	private Boolean isContinuous;
	private Integer continuousCount;
	private Double brokerage;

	// Constructors

	/** default constructor */
	public Gift() {
	}

	/** minimal constructor */
	public Gift(Timestamp createTime, Boolean isFree) {
		this.createTime = createTime;
		this.isFree = isFree;
	}

	/** full constructor */
	public Gift(String name, String createPerson, Timestamp createTime, Boolean isDelete, String description,
			String smallimgPath, Integer sort, String status, Double price, Boolean isFree, Boolean isContinuous,
			Integer continuousCount, Double brokerage) {
		this.name = name;
		this.createPerson = createPerson;
		this.createTime = createTime;
		this.isDelete = isDelete;
		this.description = description;
		this.smallimgPath = smallimgPath;
		this.sort = sort;
		this.status = status;
		this.price = price;
		this.isFree = isFree;
		this.isContinuous = isContinuous;
		this.continuousCount = continuousCount;
		this.brokerage = brokerage;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getCreatePerson() {
		return this.createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}


	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}


	public Boolean getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}


	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getSmallimgPath() {
		return this.smallimgPath;
	}

	public void setSmallimgPath(String smallimgPath) {
		this.smallimgPath = smallimgPath;
	}


	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


	public Boolean getIsFree() {
		return this.isFree;
	}

	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}


	public Boolean getIsContinuous() {
		return this.isContinuous;
	}

	public void setIsContinuous(Boolean isContinuous) {
		this.isContinuous = isContinuous;
	}


	public Integer getContinuousCount() {
		return this.continuousCount;
	}

	public void setContinuousCount(Integer continuousCount) {
		this.continuousCount = continuousCount;
	}


	public Double getBrokerage() {
		return this.brokerage;
	}

	public void setBrokerage(Double brokerage) {
		this.brokerage = brokerage;
	}

	public Integer getGiftId() {
		return giftId;
	}

	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}

	public Boolean getDelete() {
		return isDelete;
	}

	public void setDelete(Boolean delete) {
		isDelete = delete;
	}

	public Boolean getFree() {
		return isFree;
	}

	public void setFree(Boolean free) {
		isFree = free;
	}

	public Boolean getContinuous() {
		return isContinuous;
	}

	public void setContinuous(Boolean continuous) {
		isContinuous = continuous;
	}
}