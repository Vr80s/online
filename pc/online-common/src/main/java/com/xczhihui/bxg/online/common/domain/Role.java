package com.xczhihui.bxg.online.common.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.xczhihui.common.support.domain.BasicEntity;

/**
 * 用户角色
 * 
 * @author Haicheng Jiang
 *
 */
@Entity
@Table(name = "role", uniqueConstraints = { @UniqueConstraint(columnNames = { "code" }) })
public class Role extends BasicEntity {

	/**
	 * 角色代码，如:admin,manager
	 */
	private String code;
	
	/**
	 * 角色名字，如:管理员,经理
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
