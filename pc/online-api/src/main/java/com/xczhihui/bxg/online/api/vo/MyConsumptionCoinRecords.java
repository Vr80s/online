package com.xczhihui.bxg.online.api.vo;

import java.io.Serializable;
import java.util.Date;

/** 
 * ClassName: MyConsumptionCoinRecords.java <br>
 * Description: 我的消费记录<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月24日<br>
 */
public class MyConsumptionCoinRecords implements Serializable{

	private String orderNO;
	private String remark;
	private String total;
	private Date time;
	private String count;

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
}
