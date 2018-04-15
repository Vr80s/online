package com.xczhihui.wechat.course.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

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
@TableName("oe_order")
@Data
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("create_person")
	private String createPerson;
	@TableField("create_time")
	private Date createTime;
	@TableField("is_delete")
	private Boolean isDelete;
    /**
     * 排序字段
     */
	private Integer sort;
    /**
     * 订单号
     */
	@TableField("order_no")
	private String orderNo;
    /**
     * 优惠总金额
     */
	@TableField("preferenty_money")
	private Double preferentyMoney;
    /**
     * 实际支付总金额
     */
	@TableField("actual_pay")
	private Double actualPay;
    /**
     * 支付账号
     */
	@TableField("pay_account")
	private String payAccount;
    /**
     * 购买者
     */
	private String purchaser;
    /**
     * 支付类型 0.支付宝1.微信支付2.苹果支付3.熊猫币支付4.线下支付-1其他支付-2未支付
     */
	@TableField("pay_type")
	private Integer payType;
    /**
     * 支付时间
     */
	@TableField("pay_time")
	private Date payTime;
    /**
     * 支付状态 0:未支付 1:已支付 2:已关闭 
     */
	@TableField("order_status")
	private Integer orderStatus;
    /**
     * 用户ID
     */
	@TableField("user_id")
	private String userId;
    /**
     * 订单来源 0.赠送1.pc 2.h5 3.android 4.ios 5.线下 6.工作人员
     */
	@TableField("order_from")
	private Integer orderFrom;
    /**
     * 是否计算用户佣金 0:计算 1不计算
     */
	@TableField("is_count_brokerage")
	private Integer isCountBrokerage;

	@TableField(exist = false)
	private Double price;

	public Boolean getDelete() {
		return isDelete;
	}

	public void setDelete(Boolean delete) {
		isDelete = delete;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
