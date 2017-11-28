package com.xczhihui.bxg.online.api.vo;

import java.io.Serializable;
import java.util.Date;

/** 
 * ClassName: RechargeRecord.java <br>
 * Description: 充值记录<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月16日<br>
 */
public class RechargeRecord implements Serializable{

	private String orderNo;
	private Date createTime; //创建时间
	private String value;	 //充值转换的熊猫币
	private String balance;  //充值后的余额（熊猫币）
	private String total;	 //充值转换的人民币
	private String paytype;  //支付方式
	
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	
	
}
