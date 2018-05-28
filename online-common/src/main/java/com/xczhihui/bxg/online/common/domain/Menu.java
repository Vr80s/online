package com.xczhihui.bxg.online.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.common.support.domain.BasicEntity2;

import java.io.Serializable;
import java.lang.Integer;

/**
 * 菜单实体类
 * @author Rongcai Kang
 */
@Entity
@Table(name = "oe_menu")
public class Menu extends BasicEntity2 implements Serializable {

	private static final long serialVersionUID = 6249862612548452415L;
	/**
	 * 菜单名
	 */
	@Column(name = "name")
	private String name;
	/**
	 * 菜单编号
	 */
	@Column(name = "number")
	private Integer number;
	/**
	 * 菜单级别
	 */
	@Column(name = "level")
	private String level;
	/**
	 * 菜单排序
	 */
	@Column(name = "sort")
	private Integer sort;
	
	/**
	 * 云课堂菜单排序
	 */
	@Column(name = "yun_sort")
	private Integer yunSort;
	
	/**
	 * 博问答菜单排序
	 */
	@Column(name = "bo_sort")
	private Integer boSort;

	/**
	 * 菜单类型
	 */
	@Column(name = "type")
	private Integer type;

	/**
	 * 禁用状态
	 */
	@Column(name = "status")
	private Integer status;


	/**
	 * 云课堂禁用状态
	 */
	@Column(name = "yun_status")
	private Integer yunStatus;

	/**
	 * 博问答禁用状态
	 */
	@Column(name = "bo_status")
	private Integer boStatus;
	
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 博问答学科权限（1全部公开，0只对付费课程公开）
	 */
	@Column(name = "ask_limit")
	private Integer askLimit;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Integer getYunSort() {
		return yunSort;
	}

	public void setYunSort(Integer yunSort) {
		this.yunSort = yunSort;
	}

	public Integer getBoSort() {
		return boSort;
	}

	public void setBoSort(Integer boSort) {
		this.boSort = boSort;
	}

	public Integer getYunStatus() {
		return yunStatus;
	}

	public void setYunStatus(Integer yunStatus) {
		this.yunStatus = yunStatus;
	}

	public Integer getBoStatus() {
		return boStatus;
	}

	public void setBoStatus(Integer boStatus) {
		this.boStatus = boStatus;
	}

	public Integer getAskLimit() {
		return askLimit;
	}

	public void setAskLimit(Integer askLimit) {
		this.askLimit = askLimit;
	}
}
