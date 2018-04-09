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
 *  礼物实体类
 * @author Rongcai Kang
 */
@Entity
@Table(name = "oe_gift")
public class Gift implements Serializable {


	/**
	 * Copyright © 2017 xinchengzhihui. All rights reserved.
	 */
	private static final long serialVersionUID = 2911600425496674665L;

	@Column(name = "name")
    private String name;
    
	@Column(name = "smallimg_path")
    private String smallimgPath;
    
	@Column(name = "status")
    private String status;
    
	@Column(name = "price")
    private Double price ;
    
	@Column(name = "is_free")
    private Boolean isFree;
    
	@Column(name = "is_continuous")
    private Boolean isContinuous;
    
	@Column(name = "continuous_count")
    private int countinuousCount;

	@Column(name = "sort")
	private Integer sort;

	@Column(name = "brokerage")
	private double brokerage;

	/**
	 * 唯一标识
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 实体是否删除
	 */
	@Column(name = "is_delete")
	private boolean isDelete;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public double getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(double brokerage) {
		this.brokerage = brokerage;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSmallimgPath() {
		return smallimgPath;
	}

	public void setSmallimgPath(String smallimgPath) {
		this.smallimgPath = smallimgPath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getIsFree() {
		return isFree;
	}

	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}

	public Boolean getIsContinuous() {
		return isContinuous;
	}

	public void setIsContinuous(Boolean isContinuous) {
		this.isContinuous = isContinuous;
	}

	public int getCountinuousCount() {
		return countinuousCount;
	}

	public void setCountinuousCount(int countinuousCount) {
		this.countinuousCount = countinuousCount;
	}
	
	
}
