package com.xczh.consumer.market.wxpay.entity;

/**
 * 业务实体
 */
public class BizOrder {
	
	// 数据ID
	private String id;
	
	// 订单号、单据号
	private String orderCode;
	
	// 金额(分)
	private int feeAmount;
	
	// 支付title描述
	private String paytitle;
	
	//js api支付使用
	private String openId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public int getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(int feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getPaytitle() {
		return paytitle;
	}

	public void setPaytitle(String paytitle) {
		this.paytitle = paytitle;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
