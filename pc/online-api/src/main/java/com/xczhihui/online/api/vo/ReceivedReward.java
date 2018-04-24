package com.xczhihui.online.api.vo;

import java.io.Serializable;
import java.util.Date;

/** 
 * ClassName: ReceivedGift.java <br>
 * Description: 收到的打赏<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月16日<br>
 */
public class ReceivedReward implements Serializable{

	private String orderNo;
	private String price;
	private Date createTime;
	private String name;
	private String gain;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGain() {
		return gain;
	}
	public void setGain(String gain) {
		this.gain = gain;
	}

	
}
