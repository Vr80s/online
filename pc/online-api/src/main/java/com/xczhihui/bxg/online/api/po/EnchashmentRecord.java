package com.xczhihui.bxg.online.api.po;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the enchashment_record database table.
 * 
 */
@Entity
@Table(name="enchashment_record")
@NamedQuery(name="EnchashmentRecord.findAll", query="SELECT e FROM EnchashmentRecord e")
public class EnchashmentRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="client_type")
	private Integer clientType;
	
	@Column(name="cause_type")
	private Integer causeType;

	@Column(name="real_name")
	private String realName;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="enchashment_account")
	private String enchashmentAccount;

	@Column(name="enchashment_account_type")
	private Integer enchashmentAccountType;

	@Column(name="enchashment_application_id")
	private Integer enchashmentApplicationId;

	@Column(name="enchashment_sum")
	private BigDecimal enchashmentSum;

	private String operator;

	@Column(name="enchashment_remark")
	private String enchashmentRemark;
	
	@Column(name="operate_remark")
	private String operateRemark;

	@Column(name="tickling_time")
	private Date ticklingTime;

	private Date time;

	@Column(name="user_id")
	private String userId;
	@Column(name="enable_enchashment_balance")
	private BigDecimal enableEnchashmentBalance;

	public EnchashmentRecord() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
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

	public String getEnchashmentAccount() {
		return enchashmentAccount;
	}

	public void setEnchashmentAccount(String enchashmentAccount) {
		this.enchashmentAccount = enchashmentAccount;
	}

	public Integer getEnchashmentAccountType() {
		return enchashmentAccountType;
	}

	public void setEnchashmentAccountType(Integer enchashmentAccountType) {
		this.enchashmentAccountType = enchashmentAccountType;
	}

	public Integer getEnchashmentApplicationId() {
		return enchashmentApplicationId;
	}

	public void setEnchashmentApplicationId(Integer enchashmentApplicationId) {
		this.enchashmentApplicationId = enchashmentApplicationId;
	}

	public BigDecimal getEnchashmentSum() {
		return enchashmentSum;
	}

	public void setEnchashmentSum(BigDecimal enchashmentSum) {
		this.enchashmentSum = enchashmentSum;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public Date getTicklingTime() {
		return ticklingTime;
	}

	public void setTicklingTime(Date ticklingTime) {
		this.ticklingTime = ticklingTime;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

}