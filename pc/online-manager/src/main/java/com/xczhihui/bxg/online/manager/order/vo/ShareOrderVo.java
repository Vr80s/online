package com.xczhihui.bxg.online.manager.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class ShareOrderVo extends OnlineBaseVo  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String buyUserId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date createTime;
    private Integer sort;
    private String orderNo;
    private String shareOrderNo;
    private String targetUserId;
    private Integer courseId;
    private String courseName;
    private Double actualPay;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date payTime;
    private Integer level;
    private Double subsidies;
    private Integer orderStatus;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date startTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date stopTime;
    
    
    //昵称
    private String createPersonName;
    
    //性别
    private Integer sex;
    
    //手机
    private String mobile;
    
    //邮箱
    private String email;
    
    //推荐人昵称
    private String shareUserName;
    
    //总计发展人数
    private Integer shareCount;
    
    //累计佣金
    private Double totalShareMoney;
    
    //折现
    private Double getShareMoney;

    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBuyUserId() {
		return buyUserId;
	}
	public void setBuyUserId(String buyUserId) {
		this.buyUserId = buyUserId;
	}
	@Override
    public java.util.Date getCreateTime() {
		return createTime;
	}
	@Override
    public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
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
	public String getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
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
	public java.util.Date getPayTime() {
		return payTime;
	}
	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Double getSubsidies() {
		return subsidies;
	}
	public void setSubsidies(Double subsidies) {
		this.subsidies = subsidies;
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
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getShareUserName() {
		return shareUserName;
	}
	public void setShareUserName(String shareUserName) {
		this.shareUserName = shareUserName;
	}
	public Integer getShareCount() {
		return shareCount;
	}
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
	public Double getTotalShareMoney() {
		return totalShareMoney;
	}
	public void setTotalShareMoney(Double totalShareMoney) {
		this.totalShareMoney = totalShareMoney;
	}
	public Double getGetShareMoney() {
		return getShareMoney;
	}
	public void setGetShareMoney(Double getShareMoney) {
		this.getShareMoney = getShareMoney;
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
	public String getShareOrderNo() {
		return shareOrderNo;
	}
	public void setShareOrderNo(String shareOrderNo) {
		this.shareOrderNo = shareOrderNo;
	}
}
