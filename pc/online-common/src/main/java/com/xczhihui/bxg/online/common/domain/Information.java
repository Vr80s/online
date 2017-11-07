package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.xczhihui.bxg.common.support.domain.BasicEntity2;
import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

@Entity
@Table(name = "oe_information")
public class Information extends BasicEntity2 implements Serializable  {
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "informationtype")
    private String informationtype;
	
	@Column(name = "href_adress")
    private String hrefAdress;
	@Column(name = "sort")
    private Integer sort;
    @Column(name = "status")
    private Integer status;
    
    @JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
    @Column(name = "start_Time")
    private java.util.Date startTime;
    
    @JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
    @Column(name = "end_Time")
    private java.util.Date endTime;
    @Column(name = "click_count")
    private Integer clickCount;
    
    @Column(name = "is_Hot")
    private boolean isHot;
    
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInformationtype() {
		return informationtype;
	}
	public void setInformationtype(String informationtype) {
		this.informationtype = informationtype;
	}
	public String getHrefAdress() {
		return hrefAdress;
	}
	public void setHrefAdress(String hrefAdress) {
		this.hrefAdress = hrefAdress;
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
	public java.util.Date getStartTime() {
		return startTime;
	}
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	public java.util.Date getEndTime() {
		return endTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	public Integer getClickCount() {
		return clickCount;
	}
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	public boolean getIsHot() {
		return isHot;
	}
	public void setIsHot(boolean isHot) {
		this.isHot = isHot;
	}
	
}
