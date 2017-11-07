package com.xczhihui.bxg.online.manager.common.domain;

import com.xczhihui.bxg.common.support.domain.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * oe_common表实体类
 * 
 * @author Haicheng Jiang
 */
@Entity
@Table(name = "oe_common")
public class Common extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "[key]")
	private String key;
	@Column(name = "val")
	private String val;
	@Column(name = "[group]")
	private String group;

	/**
	 * 排序字段
	 */
	@Column(name = "sort")
	private Integer sort;
	/**
	 * 状态控制
	 */
	@Column(name = "status")
	private Integer status;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
