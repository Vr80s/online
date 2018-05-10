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
@Table(name = "app_version_info")
public class AppVersionInfo extends BasicEntity2 implements Serializable {
	/**
	 * Copyright © 2017 xinchengzhihui. All rights reserved.
	 */
	private static final long serialVersionUID = 3225774571214950998L;

	/**
	 * 版本号
	 */
	@Column(name = "version")
	private String version;

	/**
	 * 排序
	 */
	@Column(name = "sort")
	private Integer sort;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "[describe]")
	private String describe;

	@Column(name = "is_must_update")
	private Boolean isMustUpdate;
	
	@Column(name = "down_url")
	private String downUrl;
	
	@Column(name = "filename")
	private String filename;
	
	@Column(name = "type")
	private Integer type;
	

	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public static long getSerialVersionUID() {return serialVersionUID;}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public boolean isMustUpdate() {
		return isMustUpdate;
	}
	public void setMustUpdate(boolean isMustUpdate) {
		this.isMustUpdate = isMustUpdate;
	}
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
