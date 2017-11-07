package com.xczhihui.bxg.online.manager.order.vo;

import java.util.Date;

public class MessageShortVo {
	/**
	 * 编号
	 */
	private String id;

	/**
	 * 消息内容
	 */
	private String context;

	/**
	 * 读取状态：0.未读取  1.已读取;
	 */
	private Short readstatus;

	/**
	 * 创建时间
	 */
	private Date createTime;

	private String  create_person;

	private Integer type;

	private String  user_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Short getReadstatus() {
		return readstatus;
	}

	public void setReadstatus(Short readstatus) {
		this.readstatus = readstatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreate_person() {
		return create_person;
	}

	public void setCreate_person(String create_person) {
		this.create_person = create_person;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	

}
