package com.xczhihui.bxg.online.manager.operate.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class Banner2Vo extends OnlineBaseVo  {
	private String id;
    private String description;
    private String imgHref;
    private String imgPath;
    private Integer sort;
    private Integer status;
    @JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
    private java.util.Date startTime;
    @JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
    private java.util.Date endTime;
    private Integer clickCount;
    private String createPersonName;
    private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgHref() {
		return imgHref;
	}
	public void setImgHref(String imgHref) {
		this.imgHref = imgHref;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
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
	public String getCreatePersonName() {
		return createPersonName;
	}
	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
	}
}
