package com.xczhihui.gift.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

/** 
 * ClassName: RewardVo.java <br>
 * Description: 打赏实体<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
public class RewardVo extends OnlineBaseVo{ 

    /**
	 * Copyright © 2017 xinchengzhihui. All rights reserved.
	 */
	private static final long serialVersionUID = -5842036551442937318L;

	/**
     *礼物ID
     */
    private int id;
    
    private boolean status;
    
    private Double price ;
    
    private Boolean isFreeDom;
    
    private double brokerage;

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
		return isFreeDom;
	}

	public void setIsFreeDom(Boolean isFreeDom) {
		this.isFreeDom = isFreeDom;
	}

	public double getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(double brokerage) {
		this.brokerage = brokerage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
    
}

