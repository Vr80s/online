package com.xczhihui.bxg.online.manager.gift.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;


/** 
 * ClassName: GiftVo.java <br>
 * Description:礼物实体 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月15日<br>
 */
public class GiftVo extends OnlineBaseVo{


    /**
	 * Copyright © 2017 xinchengzhihui. All rights reserved.
	 */
	private static final long serialVersionUID = -5842036551442937318L;

	/**
     *礼物ID
     */
    private int id;
    
    private String name;
    
    private String createPerson;
    
    private String smallimgPath;
    
    private String status;
    
    private Double price ;
    
    private Boolean isFree;
    
    private Boolean isContinuous;
    
    private int continuousCount;

    private double brokerage;
    
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
    public String getCreatePerson() {
		return createPerson;
	}

	@Override
    public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
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
		return continuousCount;
	}

	public void setContinuousCount(int continuousCount) {
		this.continuousCount = continuousCount;
	}

	public String getSmallimgPath() {
		return smallimgPath;
	}

	public void setSmallimgPath(String smallimgPath) {
		this.smallimgPath = smallimgPath;
	}

	public int getContinuousCount() {
		return continuousCount;
	}
    
}

