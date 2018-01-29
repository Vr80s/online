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

	@Column(name = "bank_card_id")
	private Integer bankCardId;

	@Column(name = "enchashment_sum")
	private BigDecimal enchashmentSum;

	@Column(name = "order_from")
	private Integer orderFrom;

	private Integer dismissal;
	private String dismissal_remark;

	@Column(name = "tickling_time")
	private Date ticklingTime;

	private Date time;

	private Integer status;

	/**
	 * 实体是否删除
	 */
	@Column(name = "is_deleted")
	private boolean isDeleted;

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

	public String getDismissal_remark() {
		return dismissal_remark;
	}

	public void setDismissal_remark(String dismissal_remark) {
		this.dismissal_remark = dismissal_remark;
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
