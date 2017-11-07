package com.xczhihui.bxg.online.api.po;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;


/** 
 * ClassName: UserCoin.java <br>
 * Description:用户-代币余额表 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月11日<br>
 */
@Entity
@Table(name = "user_coin", catalog = "online")
public class UserCoin implements java.io.Serializable {
	
	/**
	 * Copyright © 2017 xinchengzhihui. All rights reserved.
	 */
	private static final long serialVersionUID = -5991749956160574631L;
	// Fields
	
	private Integer id;
	private String userId;
	private BigDecimal balance;
	private BigDecimal balanceGive;
	private BigDecimal balanceRewardGift;
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
	public UserCoin(String userId, BigDecimal balance, BigDecimal balanceGive,
			Date createTime, Boolean status, Boolean deleted) {
		this.userId = userId;
		this.balance = balance;
		this.balanceGive = balanceGive;
		this.createTime = createTime;
		this.status = status;
		this.deleted = deleted;
	}

	/** full constructor */
	public UserCoin(String userId, BigDecimal balance, BigDecimal balanceGive,
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

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false, length = 32)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "balance", nullable = false, precision = 20, scale = 6)
	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Column(name = "balance_give", nullable = false, precision = 20, scale = 6)
	public BigDecimal getBalanceGive() {
		return this.balanceGive;
	}

	public void setBalanceGive(BigDecimal balanceGive) {
		this.balanceGive = balanceGive;
	}

	@Column(name = "version", length = 32)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "status", nullable = false)
	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Column(name = "deleted", nullable = false)
	public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Column(name = "remark", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	@Column(name = "balance_reward_gift")
	public BigDecimal getBalanceRewardGift() {
		return balanceRewardGift;
	}

	public void setBalanceRewardGift(BigDecimal balanceRewardGift) {
		this.balanceRewardGift = balanceRewardGift;
	}

}