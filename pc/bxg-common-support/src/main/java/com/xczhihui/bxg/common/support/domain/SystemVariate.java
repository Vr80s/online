package com.xczhihui.bxg.common.support.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.util.StringUtils;

/**
 * 系统变量，处理系统的数据字典、配置等。
 * 
 * @author liyong
 */
@Entity
@Table(name = "system_variate", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "name", "value" }) }, indexes = { @Index(columnList = "parent_id"),
				@Index(columnList = "name") })
public class SystemVariate extends BasicEntity {

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
	 //columnDefinition="CLOB"
    @Column(name = "description",columnDefinition="CLOB")
	private String description;

	/**
	 * 显示顺序
	 */
	@Column(name = "display_order")
	private int displayOrder;

	/**
	 * 父变量
	 */
	@Column(name = "parent_id")
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
