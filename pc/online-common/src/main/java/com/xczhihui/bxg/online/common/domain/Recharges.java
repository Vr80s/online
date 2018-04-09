package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 礼物实体类
 * 
 * @author Rongcai Kang
 */
@Entity
@Table(name = "oe_recharges")
public class Recharges implements Serializable {

	/**
	 * Copyright © 2017 xinchengzhihui. All rights reserved.
	 */
	private static final long serialVersionUID = 2911600425396674665L;

	/**
	 * 唯一标识
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 创建人ID
	 */
	@Column(name = "create_person")
	private String createPerson;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 实体是否删除
	 */
	@Column(name = "is_delete")
	private Boolean isDelete;

	@Column(name = "sort")
	private Integer sort;

	@Column(name = "status")
	private String status;

	@Column(name = "price")
	private Double price;

	@Column(name = "coupons_type")
	private String couponsType;

	@Column(name = "coupons_icon")
	private String couponsIcon;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson == null ? null : createPerson.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
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
		this.status = status == null ? null : status.trim();
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCouponsType() {
		return couponsType;
	}

	public void setCouponsType(String couponsType) {
		this.couponsType = couponsType == null ? null : couponsType.trim();
	}

	public String getCouponsIcon() {
		return couponsIcon;
	}

	public void setCouponsIcon(String couponsIcon) {
		this.couponsIcon = couponsIcon == null ? null : couponsIcon.trim();
	}

}
