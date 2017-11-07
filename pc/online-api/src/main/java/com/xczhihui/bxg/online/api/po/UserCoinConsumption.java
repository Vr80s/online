package com.xczhihui.bxg.online.api.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/** 
 * ClassName: UserCoinConsumption.java <br>
 * Description: 用户代币消费明细<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月11日<br>
 */
@Entity
@Table(name="user_coin_consumption")
@NamedQuery(name="UserCoinConsumption.findAll", query="SELECT u FROM UserCoinConsumption u")
public class UserCoinConsumption implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="balance_give_value")
	private BigDecimal balanceGiveValue;

	@Column(name="balance_reward_gift")
	private BigDecimal balanceRewardGift;

	@Column(name="balance_value")
	private BigDecimal balanceValue;

	@Column(name="change_type")
	private Integer changeType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private boolean deleted;

	@Column(name="order_no_consume")
	private String orderNoConsume;

	@Column(name="order_no_enchashment")
	private String orderNoEnchashment;

	@Column(name="order_no_gift")
	private String orderNoGift;

	@Column(name="order_no_overdue")
	private String orderNoOverdue;

	private String remark;

	private boolean status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="user_coin_id")
	private Integer userCoinId;

	@Column(name="user_id")
	private String userId;

	private BigDecimal value;

	private String version;

	public UserCoinConsumption() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getBalanceGiveValue() {
		return this.balanceGiveValue;
	}

	public void setBalanceGiveValue(BigDecimal balanceGiveValue) {
		this.balanceGiveValue = balanceGiveValue;
	}

	public BigDecimal getBalanceRewardGift() {
		return this.balanceRewardGift;
	}

	public void setBalanceRewardGift(BigDecimal balanceRewardGift) {
		this.balanceRewardGift = balanceRewardGift;
	}

	public BigDecimal getBalanceValue() {
		return this.balanceValue;
	}

	public void setBalanceValue(BigDecimal balanceValue) {
		this.balanceValue = balanceValue;
	}

	public Integer getChangeType() {
		return this.changeType;
	}

	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getOrderNoConsume() {
		return this.orderNoConsume;
	}

	public void setOrderNoConsume(String orderNoConsume) {
		this.orderNoConsume = orderNoConsume;
	}

	public String getOrderNoEnchashment() {
		return this.orderNoEnchashment;
	}

	public void setOrderNoEnchashment(String orderNoEnchashment) {
		this.orderNoEnchashment = orderNoEnchashment;
	}

	public String getOrderNoGift() {
		return this.orderNoGift;
	}

	public void setOrderNoGift(String orderNoGift) {
		this.orderNoGift = orderNoGift;
	}

	public String getOrderNoOverdue() {
		return this.orderNoOverdue;
	}

	public void setOrderNoOverdue(String orderNoOverdue) {
		this.orderNoOverdue = orderNoOverdue;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUserCoinId() {
		return this.userCoinId;
	}

	public void setUserCoinId(Integer userCoinId) {
		this.userCoinId = userCoinId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getValue() {
		return this.value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}