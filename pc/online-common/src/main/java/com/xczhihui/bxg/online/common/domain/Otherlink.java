package com.xczhihui.bxg.online.common.domain;

import com.xczhihui.bxg.common.support.domain.BasicEntity2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 友情链接实体类
 * @author Rongcai Kang
 */
@Entity
@Table(name = "oe_otherlink")
public class Otherlink extends BasicEntity2 implements Serializable {

	private static final long serialVersionUID = -1547062789952388640L;
	/**
	 * 链接名
	 */
	@Column(name = "name")
	private String name;
	/**
	 *链接地址
	 */
	@Column(name = "url",nullable=false)
	private String url;
	/**
	 *链接logo地址
	 */
	@Column(name = "logo")
	private String logo;
	/**
	 * 链接排序
	 */
	@Column(name = "sort")
	private Integer sort;
	
	/**
	 * 是否禁用启用 1:启用 0：禁用
	 */
	@Column(name = "status")
	private String  status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
