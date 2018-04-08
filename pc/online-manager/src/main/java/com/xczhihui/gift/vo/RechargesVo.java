package com.xczhihui.gift.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

/**
 * ClassName: GiftVo.java <br>
 * Description:礼物实体 <br>
 * Create by: name：yuxin <br>
 * email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月15日<br>
 */
public class RechargesVo extends OnlineBaseVo {

	/**
	 * Copyright © 2017 xinchengzhihui. All rights reserved.
	 */
	private static final long serialVersionUID = -5842036551442937318L;

	private Integer id;

	private Integer sort;

	private String status;

	private Double price;
	
	private Double xmbPrice;

	private String couponsType;

	private String couponsIcon;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getXmbPrice() {
		return xmbPrice;
	}

	public void setXmbPrice(Double xmbPrice) {
		this.xmbPrice = xmbPrice;
	}
	
}
