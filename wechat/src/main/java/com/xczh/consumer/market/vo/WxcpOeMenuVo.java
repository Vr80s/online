package com.xczh.consumer.market.vo;

import java.io.Serializable;

public class WxcpOeMenuVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer menuId; //ios使用了
	
	private String name;
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		setMenuId(id);
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	
}
