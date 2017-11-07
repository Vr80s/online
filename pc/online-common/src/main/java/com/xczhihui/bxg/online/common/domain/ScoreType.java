package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.xczhihui.bxg.common.support.domain.BasicEntity;

/**
 * 授课方式表
 *  @author yxd
 */
@Entity
@Table(name = "score_type")
public class ScoreType extends BasicEntity implements Serializable{

	// Fields
	@Column(name = "name")
	private String name;
	
	@Column(name = "status")
	private Integer status;
	
	//排序
	@Column(name = "sort")
	private Integer sort;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "type")
	private Integer type;
	
	//是否选中
	@Transient
	private String checked;
	
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}