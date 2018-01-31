package com.xczhihui.bxg.online.api.po;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 *  提现申请实体类
 * @author Rongcai Kang
 */
@Entity
@Table(name = "enchashment_apply_info")
public class EnchashmentApplyInfo implements Serializable {

	/**
	 * 唯一标识
	 */
	@Id
	private String id;

	@Column(name = "user_id")
	private String userId;
	@Column(name = "operator")
	private String operator;

	@Column(name = "bank_card_id")
	private Integer bankCardId;

	@Column(name = "enchashment_sum")
	private BigDecimal enchashmentSum;

	@Column(name = "order_from")
	private Integer orderFrom;

	private Integer dismissal;
	@Transient
	private String dismissalText;
	@Column(name = "dismissal_remark")
	private String dismissalRemark;
	@Column(name = "order_no")
	private String orderNo;

	@Column(name = "tickling_time")
	private Date ticklingTime;

	private Date time;

	private Integer status;

	/**
	 * 实体是否删除
	 */
	@Column(name = "is_deleted")
	private boolean isDeleted;
	@Transient
	private java.util.Date startTime;
	@Transient
	private java.util.Date stopTime;
	@Transient
	private String loginName;
	@Transient
	private String acctName;
	@Transient
	private String certId;
	@Transient
	private String acctPan;
	@Transient
	private String bankName;
	@Transient
	private Integer anthorType;

	public Integer getAnthorType() {
		return anthorType;
	}

	public void setAnthorType(Integer anthorType) {
		this.anthorType = anthorType;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

	public String getAcctPan() {
		return acctPan;
	}

	public void setAcctPan(String acctPan) {
		this.acctPan = acctPan;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getDismissalText() {
		return dismissalText;
	}

	public void setDismissalText(String dismissalText) {
		this.dismissalText = dismissalText;
	}

	public String getDismissalRemark() {
		return dismissalRemark;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setDismissalRemark(String dismissalRemark) {
		this.dismissalRemark = dismissalRemark;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(Integer bankCardId) {
		this.bankCardId = bankCardId;
	}

	public BigDecimal getEnchashmentSum() {
		return enchashmentSum;
	}

	public void setEnchashmentSum(BigDecimal enchashmentSum) {
		this.enchashmentSum = enchashmentSum;
	}

	public Integer getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(Integer orderFrom) {
		this.orderFrom = orderFrom;
	}

	public Integer getDismissal() {
		return dismissal;
	}

	public void setDismissal(Integer dismissal) {
		this.dismissal = dismissal;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean deleted) {
		isDeleted = deleted;
	}
}
