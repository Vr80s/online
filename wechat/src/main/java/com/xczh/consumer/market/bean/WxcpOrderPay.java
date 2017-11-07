package com.xczh.consumer.market.bean;
import java.io.Serializable;


/**
 * 
 * 订单支付主表 (舍弃不用)
 * @author yanghui
 **/
@SuppressWarnings("serial")
public class WxcpOrderPay implements Serializable {

	/****/
	 private String pay_id;

	/**订单id;**/
	 private String order_id;

	/**支付类型，支持类型（区分大小写）：Alipay（支付宝）、WeChat（微信）**/
	 private String payment_type;

	/**支付的金额**/
	 private java.math.BigDecimal amount;

	/****/
	 private String pay_user_id;

	/****/
	 private java.util.Date create_datetime;

	/****/
	 private String pay_description;

	/**交易的流水号 支付方返回**/
	 private String flow_id;
	 
	public String getPay_id() {
		return pay_id;
	}

	public void setPay_id(String pay_id) {
		this.pay_id = pay_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public java.math.BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(java.math.BigDecimal amount) {
		this.amount = amount;
	}

	public String getPay_user_id() {
		return pay_user_id;
	}

	public void setPay_user_id(String pay_user_id) {
		this.pay_user_id = pay_user_id;
	}

	public java.util.Date getCreate_datetime() {
		return create_datetime;
	}

	public void setCreate_datetime(java.util.Date create_datetime) {
		this.create_datetime = create_datetime;
	}

	public String getPay_description() {
		return pay_description;
	}

	public void setPay_description(String pay_description) {
		this.pay_description = pay_description;
	}

	public String getFlow_id() {
		return flow_id;
	}

	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}

}
