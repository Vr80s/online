package com.xczhihui.bxg.online.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xczhihui.bxg.common.support.domain.BasicEntity2;

import java.io.Serializable;
import java.lang.Integer;
import java.util.Date;

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

	/**
	 * 连接类型
	 */
	@Column(name = "link_type")
	private Integer linkType;

	/**
	 * 链接地址
	 */
	@Column(name = "link_condition")
	private String linkCondition;

	/**
	 * 类型           1 推荐 2 分类
	 */
	@Column(name = "type")
	private Integer type;

	/**
	 * 启用时间
	 */
	@JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
	@Column(name = "start_time")
	private Date startTime;

	/**
	 * 关闭时间
	 */
	@JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
	@Column(name = "end_time")
	private Date endTime;

	/**
	 * 点击数
	 */
	@Column(name = "click_num")
	private Integer clickNum;

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

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLinkCondition() {
		return linkCondition;
	}

	public void setLinkCondition(String linkCondition) {
		this.linkCondition = linkCondition;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getClickNum() {
		return clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}
}
