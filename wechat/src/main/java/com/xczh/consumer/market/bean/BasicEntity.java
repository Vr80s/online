package com.xczh.consumer.market.bean;

import java.io.Serializable;
import java.util.Date;

public abstract class BasicEntity implements Serializable {

	/**
	 * 唯一标识
	 */
	private String id;

	
	
	/**
	 * 实体是否删除
	 */
	private boolean isDelete;

	/**
	 * 创建人ID
	 */
	private String createPerson;

	/**
	 * 创建时间
	 */
	private Date createTime;
	
	private String userId;
	
	private String orderId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		setUserId(id);
		setOrderId(id);
		this.id = id;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
}
