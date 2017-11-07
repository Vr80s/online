package com.xczh.consumer.market.bean;

import java.util.Date;


/** 
 * ClassName: UserCoin.java <br>
 * Description:用户-代币余额表 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月11日<br>
 */
public class UserCoin implements java.io.Serializable {
	
	/**
	 * Copyright © 2017 xinchengzhihui. All rights reserved.
	 */
	private static final long serialVersionUID = -5991749956160574631L;
	// Fields
	
	private Integer id;
	private String userId;
	private Double balance;
	private Double balanceGive;
	private String version;
	private Date createTime;
	private Date updateTime;
	private Boolean status;
	private Boolean deleted;
	private String remark;

	// Constructors

	/** default constructor */
	public UserCoin() {
	}

	/** minimal constructor */
	public UserCoin(String userId, Double balance, Double balanceGive,
                    Date createTime, Boolean status, Boolean deleted) {
		this.userId = userId;
		this.balance = balance;
		this.balanceGive = balanceGive;
		this.createTime = createTime;
		this.status = status;
		this.deleted = deleted;
	}

	/** full constructor */
	public UserCoin(String userId, Double balance, Double balanceGive,
                    String version, Date createTime, Date updateTime,
                    Boolean status, Boolean deleted, String remark) {
		this.userId = userId;
		this.balance = balance;
		this.balanceGive = balanceGive;
		this.version = version;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
		this.deleted = deleted;
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getBalanceGive() {
		return balanceGive;
	}

	public void setBalanceGive(Double balanceGive) {
		this.balanceGive = balanceGive;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}