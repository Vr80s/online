package com.xczhihui.bxg.online.common.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.bxg.common.support.domain.BasicEntity;

@Entity
@Table(name = "oe_order")
public class Order extends BasicEntity  {
	
	
	
	@Column(name = "sort")
    private Integer sort;
	
	@Column(name = "order_no")
    private String orderNo;
	
	@Column(name = "preferenty_money")
    private Double preferentyMoney;
	
	@Column(name = "actual_pay")
    private Double actualPay;
	
	@Column(name = "pay_account")
    private String payAccount;
	
	@Column(name = "purchaser")
    private String purchaser;
	
	@Column(name = "pay_type")
    private Integer payType;
	
	@Column(name = "pay_time")
    private java.util.Date payTime;
	
	@Column(name = "order_status")
	private Integer orderStatus;
	/**
	 * 订单来源，0官网（本系统），1分销系统，2线下（刷数据）
	 */
	@Column(name = "order_from")
    private Integer orderFrom;

	@Column(name = "is_count_brokerage")
	private Integer isCountBrokerage;

	

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	

	public Double getPreferentyMoney() {
		return preferentyMoney;
	}

	public void setPreferentyMoney(Double preferentyMoney) {
		this.preferentyMoney = preferentyMoney;
	}

	public Double getActualPay() {
		return actualPay;
	}

	public void setActualPay(Double actualPay) {
		this.actualPay = actualPay;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public String getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public java.util.Date getPayTime() {
		return payTime;
	}

	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(Integer orderFrom) {
		this.orderFrom = orderFrom;
	}

	public Integer getIsCountBrokerage() {
		return isCountBrokerage;
	}

	public void setIsCountBrokerage(Integer isCountBrokerage) {
		this.isCountBrokerage = isCountBrokerage;
	}
}
