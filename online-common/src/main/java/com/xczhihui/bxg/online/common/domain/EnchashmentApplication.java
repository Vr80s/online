package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the enchashment_application database table.
 * 
 */
@Entity
@Table(name="enchashment_application")
public class EnchashmentApplication implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="client_type")
	private Integer clientType;
	@Column(name="cause_type")
	private Integer causeType;

	@Column(name="enchashment_account")
	private String enchashmentAccount;
	
	@Column(name="real_name")
	private String realName;
	
	@Column(name="phone")
	private String phone;

	@Column(name="enchashment_account_type")
	private Integer enchashmentAccountType;

	@Column(name="enchashment_status")
	private Integer enchashmentStatus;

	@Column(name="enchashment_sum")
	private BigDecimal enchashmentSum;

	private String operator;

	@Column(name="tickling_time")
	private Date ticklingTime;

	private Date time;

	@Column(name="user_id")
	private String userId;
	
	@Column(name="enchashment_remark")
	private String enchashmentRemark;
	
	@Column(name="operate_remark")
	private String operateRemark;
	@Transient
	private java.util.Date startTime;
	@Transient
    private java.util.Date stopTime;
	@Transient
	private String loginName;
	@Column(name="enable_enchashment_balance")
	private BigDecimal enableEnchashmentBalance;
    
	/**
	 * 杨宣新加。存放的是  提现到xxx账户
	 */
	@Transient
	public String newRemark; 
    

	public java.util.Date getStartTime() {
		return startTime;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	public java.util.Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(java.util.Date stopTime) {
		this.stopTime = stopTime;
	}

	public EnchashmentApplication() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientType() {
		return this.clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getEnchashmentAccount() {
		return this.enchashmentAccount;
	}

	public void setEnchashmentAccount(String enchashmentAccount) {
		this.enchashmentAccount = enchashmentAccount;
	}

	public Integer getEnchashmentAccountType() {
		return this.enchashmentAccountType;
	}

	public void setEnchashmentAccountType(Integer enchashmentAccountType) {
		this.enchashmentAccountType = enchashmentAccountType;
	}

	public Integer getEnchashmentStatus() {
		return this.enchashmentStatus;
	}

	public void setEnchashmentStatus(Integer enchashmentStatus) {
		this.enchashmentStatus = enchashmentStatus;
	}

	public BigDecimal getEnchashmentSum() {
		return this.enchashmentSum;
	}

	public void setEnchashmentSum(BigDecimal enchashmentSum) {
		this.enchashmentSum = enchashmentSum;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getTicklingTime() {
		return this.ticklingTime;
	}

	public void setTicklingTime(Date ticklingTime) {
		this.ticklingTime = ticklingTime;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEnchashmentRemark() {
		return enchashmentRemark;
	}

	public void setEnchashmentRemark(String enchashmentRemark) {
		this.enchashmentRemark = enchashmentRemark;
	}

	public String getOperateRemark() {
		return operateRemark;
	}

	public void setOperateRemark(String operateRemark) {
		this.operateRemark = operateRemark;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public BigDecimal getEnableEnchashmentBalance() {
		return enableEnchashmentBalance;
	}

	public void setEnableEnchashmentBalance(BigDecimal enableEnchashmentBalance) {
		this.enableEnchashmentBalance = enableEnchashmentBalance;
	}

	public Integer getCauseType() {
		return causeType;
	}

	public void setCauseType(Integer causeType) {
		this.causeType = causeType;
	}

	public String getNewRemark() {
		return newRemark;
	}

	public void setNewRemark(String newRemark) {
		this.newRemark = newRemark;
	}

	
	
}