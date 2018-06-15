package com.xczhihui.bxg.online.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.common.support.domain.BasicEntity2;

import java.io.Serializable;

/**
 * 首页banner图实体类
 * @author Rongcai Kang
 */
@Entity
@Table(name = "oe_banner2")
public class Banner extends BasicEntity2 implements Serializable {

	private static final long serialVersionUID = -2834185652843675982L;
	/**
	 * 图片路径
	 */
	@Column(name = "img_path")
	private String imgPath;
	/**
	 * 图片描述
	 */
	@Column(name = "description")
	private String description;
	/**
	 *
	 * 图片跳转路径
	 */
	@Column(name = "img_href")
	private String imgHref;

	/**
	 *
	 * 排序
	 */
	@Column(name = "sort")
	private Integer sort;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "start_time")
	private java.util.Date startTime;
	@Column(name = "end_time")
	private java.util.Date endTime;
	@Column(name = "click_count")
	private Integer clickCount;
	@Column(name = "type")
	private Integer type;


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public static long getSerialVersionUID() {return serialVersionUID;}

	public String getImgHref() {
		return imgHref;
	}
	public void setImgHref(String imgHref) {
		this.imgHref = imgHref;
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
}
