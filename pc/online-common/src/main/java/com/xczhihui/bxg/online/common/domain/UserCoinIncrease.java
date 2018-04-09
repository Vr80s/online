package com.xczhihui.bxg.online.common.domain;

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
import javax.persistence.Transient;



/** 
 * ClassName: UserCoinIncrease.java <br>
 * Description:用户代币增加明细 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月11日<br>
 */
@Entity
@Table(name="user_coin_increase")
@NamedQuery(name="UserCoinIncrease.findAll", query="SELECT u FROM UserCoinIncrease u")
public class UserCoinIncrease implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private BigDecimal balance;

	@Column(name="balance_give")
	private BigDecimal balanceGive;

	@Column(name="balance_reward_gift")
	private BigDecimal balanceRewardGift;
	private BigDecimal rmb;

	@Column(name="brokerage_value")
	private BigDecimal brokerageValue;
	@Column(name="ios_brokerage_value")
	private BigDecimal iosBrokerageValue;
	@Column(name="ratio")
	private BigDecimal ratio;

	@Column(name="change_type")
	private Integer changeType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private boolean deleted;

	@Column(name="order_from")
	private Integer orderFrom;

	@Column(name="order_no_gift")
	private String orderNoGift;

	@Column(name="order_no_largess")
	private String orderNoLargess;

	@Column(name="order_no_recharge")
	private String orderNoRecharge;

	@Column(name="order_no_reject")
	private String orderNoReject;

	@Column(name="order_no_reward")
	private String orderNoReward;

	@Column(name="order_no_settlement")
	private String orderNoSettlement;

	@Column(name="order_no_course")
	private String orderNoCourse;

	@Column(name="pay_type")
	private Integer payType;

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
	
	@Transient
	private String subject;
	@Column(name="balance_type")
	private Integer balanceType;

	public Integer getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(Integer balanceType) {
		this.balanceType = balanceType;
	}

	@Transient
    private java.util.Date startTime;
	@Transient
    private java.util.Date stopTime;

	public UserCoinIncrease() {
	}

	public BigDecimal getIosBrokerageValue() {
		return iosBrokerageValue;
	}

	public void setIosBrokerageValue(BigDecimal iosBrokerageValue) {
		this.iosBrokerageValue = iosBrokerageValue;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	public BigDecimal getRmb() {
		return rmb;
	}

	public void setRmb(BigDecimal rmb) {
		this.rmb = rmb;
	}

	public String getOrderNoSettlement() {
		return orderNoSettlement;
	}

	public void setOrderNoSettlement(String orderNoSettlement) {
		this.orderNoSettlement = orderNoSettlement;
	}

	public String getOrderNoCourse() {
		return orderNoCourse;
	}

	public void setOrderNoCourse(String orderNoCourse) {
		this.orderNoCourse = orderNoCourse;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalanceGive() {
		return this.balanceGive;
	}

	public void setBalanceGive(BigDecimal balanceGive) {
		this.balanceGive = balanceGive;
	}

	public BigDecimal getBalanceRewardGift() {
		return this.balanceRewardGift;
	}

	public void setBalanceRewardGift(BigDecimal balanceRewardGift) {
		this.balanceRewardGift = balanceRewardGift;
	}

	public BigDecimal getBrokerageValue() {
		return this.brokerageValue;
	}

	public void setBrokerageValue(BigDecimal brokerageValue) {
		this.brokerageValue = brokerageValue;
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

	public Integer getOrderFrom() {
		return this.orderFrom;
	}

	public void setOrderFrom(Integer orderFrom) {
		this.orderFrom = orderFrom;
	}

	public String getOrderNoGift() {
		return this.orderNoGift;
	}

	public void setOrderNoGift(String orderNoGift) {
		this.orderNoGift = orderNoGift;
	}

	public String getOrderNoLargess() {
		return this.orderNoLargess;
	}

	public void setOrderNoLargess(String orderNoLargess) {
		this.orderNoLargess = orderNoLargess;
	}

	public String getOrderNoRecharge() {
		return this.orderNoRecharge;
	}

	public void setOrderNoRecharge(String orderNoRecharge) {
		this.orderNoRecharge = orderNoRecharge;
	}

	public String getOrderNoReject() {
		return this.orderNoReject;
	}

	public void setOrderNoReject(String orderNoReject) {
		this.orderNoReject = orderNoReject;
	}

	public String getOrderNoReward() {
		return this.orderNoReward;
	}

	public void setOrderNoReward(String orderNoReward) {
		this.orderNoReward = orderNoReward;
	}

	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

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

}