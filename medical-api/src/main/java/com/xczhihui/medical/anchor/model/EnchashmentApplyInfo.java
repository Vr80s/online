package com.xczhihui.medical.anchor.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2018-01-29
 */
@TableName("enchashment_apply_info")
public class EnchashmentApplyInfo extends Model<EnchashmentApplyInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 提现申请id
     */
	@TableId(value="id", type= IdType.AUTO)
	private String id;
    /**
     * 提现用户id
     */
	@TableField("user_id")
	private String userId;
    /**
     * 提现银行卡
     */
	@TableField("bank_card_id")
	private Integer bankCardId;
    /**
     * 提现金额
     */
	@TableField("enchashment_sum")
	private BigDecimal enchashmentSum;
    /**
     * 1.pc 2.h5 3.android 4.ios
     */
	@TableField("order_from")
	private Integer orderFrom;
    /**
     * 驳回原因
     */
	private Integer dismissal;
    /**
     * 驳回备注
     */
	@TableField("dismissal_remark")
	private String dismissalRemark;
    /**
     * 申请发起时间
     */
	private Date time;
    /**
     * 申请处理时间
     */
	@TableField("tickling_time")
	private Date ticklingTime;
    /**
     * 0未审核 1 审核通过 2 审核未通过
     */
	private Integer status;
	@TableField("is_deleted")
	private Boolean isDeleted;


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

	public String getDismissalRemark() {
		return dismissalRemark;
	}

	public void setDismissalRemark(String dismissalRemark) {
		this.dismissalRemark = dismissalRemark;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getTicklingTime() {
		return ticklingTime;
	}

	public void setTicklingTime(Date ticklingTime) {
		this.ticklingTime = ticklingTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getDeleted() {
		return isDeleted;
	}

	public void setDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "EnchashmentApplyInfo{" +
			", id=" + id +
			", userId=" + userId +
			", bankCardId=" + bankCardId +
			", enchashmentSum=" + enchashmentSum +
			", orderFrom=" + orderFrom +
			", dismissal=" + dismissal +
			", dismissalRemark=" + dismissalRemark +
			", time=" + time +
			", ticklingTime=" + ticklingTime +
			", status=" + status +
			", isDeleted=" + isDeleted +
			"}";
	}
}
