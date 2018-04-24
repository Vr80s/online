package com.xczhihui.wechat.course.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
@TableName("oe_order_detail")
public class OrderDetail extends Model<OrderDetail> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 订单主表id
     */
	@TableField("order_id")
	private String orderId;
    /**
     * 商品id
     */
	@TableField("course_id")
	private String courseId;
    /**
     * 实际应付
     */
	@TableField("actual_pay")
	private Double actualPay;
    /**
     * 活动规则明细id，不参加默认为NULL
     */
	@TableField("activity_rule_detal_id")
	private String activityRuleDetalId;
    /**
     * 课程单价
     */
	private Double price;
    /**
     * 班级id，买课时可选班级
     */
	@TableField("class_id")
	private String classId;
	@TableField("create_time")
	private Date createTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Double getActualPay() {
		return actualPay;
	}

	public void setActualPay(Double actualPay) {
		this.actualPay = actualPay;
	}

	public String getActivityRuleDetalId() {
		return activityRuleDetalId;
	}

	public void setActivityRuleDetalId(String activityRuleDetalId) {
		this.activityRuleDetalId = activityRuleDetalId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "OeOrderDetail{" +
			", id=" + id +
			", orderId=" + orderId +
			", courseId=" + courseId +
			", actualPay=" + actualPay +
			", activityRuleDetalId=" + activityRuleDetalId +
			", price=" + price +
			", classId=" + classId +
			", createTime=" + createTime +
			"}";
	}
}
