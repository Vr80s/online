package com.xczh.consumer.market.bean;

import java.io.Serializable;
import java.util.Date;

public abstract class BasicEntity2 implements Serializable {

	/**
	 * 唯一标识
	 */
	private Integer id;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
}
