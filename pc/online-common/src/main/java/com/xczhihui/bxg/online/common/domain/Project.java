package com.xczhihui.bxg.online.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.bxg.common.support.domain.BasicEntity2;

import java.io.Serializable;
import java.lang.Integer;

/**
 * 菜单实体类
 * @author Rongcai Kang
 */
@Entity
@Table(name = "oe_project")
public class Project extends BasicEntity2 implements Serializable {

	private static final long serialVersionUID = 6249862612548452415L;
	/**
	 * 专题名
	 */
	@Column(name = "name")
	private String name;
	/**
	 * 专题图标
	 */
	@Column(name = "icon")
	private String icon;
	
	/**
	 * 专题排序
	 */
	@Column(name = "sort")
	private Integer sort;
	/**
	 * 禁用状态
	 */
	@Column(name = "status")
	private Integer status;
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
