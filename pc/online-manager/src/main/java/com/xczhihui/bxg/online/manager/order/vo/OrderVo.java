package com.xczhihui.bxg.online.manager.order.vo;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class OrderVo extends OnlineBaseVo  {

    private String id;
    private Integer isDelete;
    private Integer status;
    private Integer sort;
    private String orderNo;
    private String preferentyWay;
    private Double preferentyMoney;
    private Integer courseId;
    private String courseName;
    private Double actualPay;
    private String payAccount;
    private String purchaser;
    private Integer payType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date payTime;
    private Integer orderStatus;
    private String createPersonName;
    private java.util.Date startTime;
    private java.util.Date stopTime;
    private Integer orderFrom;//订单来源，0官网（本系统），1分销系统，2线下（刷数据）
    private String orderFromName;//订单来源名称
    private Double  subsidies;
    
    
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
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
	public String getPreferentyWay() {
		return preferentyWay;
	}
	public void setPreferentyWay(String preferentyWay) {
		this.preferentyWay = preferentyWay;
	}
	public Double getPreferentyMoney() {
		return preferentyMoney;
	}
	public void setPreferentyMoney(Double preferentyMoney) {
		this.preferentyMoney = preferentyMoney;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
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
	public String getCreatePersonName() {
		return createPersonName;
	}
	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getOrderFrom() {
		return orderFrom;
	}
	public void setOrderFrom(Integer orderFrom) {
		this.orderFrom = orderFrom;
	}
	public String getOrderFromName() {
		return orderFromName;
	}
	public void setOrderFromName(String orderFromName) {
		this.orderFromName = orderFromName;
	}
	public Double getSubsidies() {
		return subsidies;
	}
	public void setSubsidies(Double subsidies) {
		this.subsidies = subsidies;
	}
}
