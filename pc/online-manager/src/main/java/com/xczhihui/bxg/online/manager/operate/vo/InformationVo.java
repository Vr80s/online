package com.xczhihui.bxg.online.manager.operate.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class InformationVo extends OnlineBaseVo  {
	private String id;
	private String name;
    private String informationtype;
    private String informationtypeName;
    private String createPerson;
    private java.util.Date createTime;
    private String hrefAdress;
    private Integer sort;
    private Integer status;
    @JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
    private java.util.Date startTime;
    @JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
    private java.util.Date endTime;
    private Integer clickCount;
    private String last;
    private boolean isHot;
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
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
	public String getInformationtype() {
		return informationtype;
	}
	public void setInformationtype(String informationtype) {
		this.informationtype = informationtype;
	}
	@Override
    public String getCreatePerson() {
		return createPerson;
	}
	@Override
    public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	@Override
    public java.util.Date getCreateTime() {
		return createTime;
	}
	@Override
    public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
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
	public String getInformationtypeName() {
		return informationtypeName;
	}
	public void setInformationtypeName(String informationtypeName) {
		this.informationtypeName = informationtypeName;
	}
	public boolean getIsHot() {
		return isHot;
	}
	public void setIsHot(boolean isHot) {
		this.isHot = isHot;
	}
	
}
