package com.xczhihui.cloudClass.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;
import com.xczhihui.bxg.online.common.domain.Menu;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * 菜单web端调用的结果封装类
 * 
 * @author Rongcai Kang
 */
public class TeachMethodVo extends OnlineBaseVo {

	private String id;
	/**
	 * 授课方式名称
	 */
	private String name;
	/**
	 * 授课方式排序字段
	 */
	private Integer sort;

	private String remark;

	private boolean status;

	private Integer courseCount;

	@JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCourseCount() {
		return courseCount;
	}

	public void setCourseCount(Integer courseCount) {
		this.courseCount = courseCount;
	}

}
