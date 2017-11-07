package com.xczhihui.bxg.online.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 *  分销佣金表
 *  @author wgw
 */
@Entity
@Table(name = "oe_share_order")
public class ShareOrder {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "buy_user_id")
	private String buyUserId;
	
	@Column(name = "sort")
    private Integer sort;
	
	@Column(name = "order_no")
    private String orderNo;
	
	@Column(name = "target_user_id")
    private String targetUserId;
	
	@Column(name = "course_id")
    private Integer courseId;
	
	@Column(name = "course_name")
    private String courseName;
	
	@Column(name = "actual_pay")
    private Double actualPay;
	
	@Column(name = "pay_time")
    private java.util.Date payTime;
	
	@Column(name = "level")
    private Integer level;
	
	@Column(name = "subsidies")
    private Double subsidies;
	
	@Column(name = "order_status")
    private Integer orderStatus;
	
	@Column(name = "share_order_no")
	private String shareOrderNo;
	
	
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Id
	private String id;

	@Column(name = "is_delete")
	private boolean isDelete;

	@Column(name = "create_time")
	private Date createTime;

	public String getId() {
		return id;
	}

	public String getBuyUserId() {
		return buyUserId;
	}

	public void setBuyUserId(String buyUserId) {
		this.buyUserId = buyUserId;
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

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShareOrderNo() {
		return shareOrderNo;
	}

	public void setShareOrderNo(String shareOrderNo) {
		this.shareOrderNo = shareOrderNo;
	}
	
}
