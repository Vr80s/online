package com.xczh.consumer.market.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 在线订单表
 * @author zhangshixiong
 * @date 2017-02-22 
 */
public class OnlineOrder extends BasicEntity implements Serializable{
	private static final long serialVersionUID = 1L;
    private int sort;//排序字段
    private String orderNo;//订单号
    private Double preferentyMoney;//优惠金额
    private Double actualPay;//实际支付
    private String payAccount;//支付账号
    private String purchaser;//购买者
    private int payType;//支付类型 0:微信  1:支付宝  2:网银
    private java.util.Date payTime;//支付时间
    private int orderStatus;//支付状态 0:未支付 1:已支付 2:已关闭
    private int orderFrom;//订单来源，0官网（本系统），1分销系统，2线下（刷数据）
    private int isCountBrokerage;//是否计算用户佣金 0:计算 1不计算
    private String validity; //课程有效期
    private String orderValidity;//订单有效期
    
    
    private List<OnlineCourse> allCourse;
    
	public List<OnlineCourse> getAllCourse() {
		return allCourse;
	}
	public void setAllCourse(List<OnlineCourse> allCourse) {
		this.allCourse = allCourse;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
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
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public java.util.Date getPayTime() {
		return payTime;
	}
	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public int getOrderFrom() {
		return orderFrom;
	}
	public void setOrderFrom(int orderFrom) {
		this.orderFrom = orderFrom;
	}
	public int getIsCountBrokerage() {
		return isCountBrokerage;
	}
	public void setIsCountBrokerage(int isCountBrokerage) {
		this.isCountBrokerage = isCountBrokerage;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getOrderValidity() {
		return orderValidity;
	}
	public void setOrderValidity(String orderValidity) {
		this.orderValidity = orderValidity;
	}
}
