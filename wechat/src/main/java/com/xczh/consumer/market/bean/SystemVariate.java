package com.xczh.consumer.market.bean;


import org.springframework.util.StringUtils;

/**
 * 系统变量，处理系统的数据字典、配置等。
 * 
 * @author zhangshixiong
 */
public class SystemVariate extends BasicEntity2 {
	/**
	 * 变量名，多个同名变量组成list
	 */
	private String name;

	/**
	 * 变量的值
	 */
	private String value;

	/**
	 * 变量值
	 */
	private String description;

	/**
	 * 显示顺序
	 */
	private int displayOrder;

	/**
	 * 父变量
	 */
	private String parentId;

	public int getValueAsInt() {
		int val = 0;
		if (StringUtils.hasText(this.value)) {
			val = Integer.parseInt(value.trim());
		}
		return val;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
