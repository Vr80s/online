package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.bxg.common.support.domain.BasicEntity2;

/**
 *  礼物实体类
 * @author Rongcai Kang
 */
@Entity
@Table(name = "oe_reward")
public class Reward extends BasicEntity2 implements Serializable {

    
	/**
	 * Copyright © 2017 xinchengzhihui. All rights reserved.
	 */
	private static final long serialVersionUID = 4893522843043397812L;

	@Column(name = "status")
    private boolean status;
    
	@Column(name = "price")
    private Double price ;
    
	@Column(name = "is_freedom")
    private Boolean isFreedom;
    
	@Column(name = "brokerage")
    private double brokerage;
	
	@Column(name = "sort")
	private int sort;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getIsFreeDom() {
		return isFreedom;
	}

	public void setIsFreeDom(Boolean isFreeDom) {
		this.isFreedom = isFreeDom;
	}

	public double getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(double brokerage) {
		this.brokerage = brokerage;
	}
	
	
}
