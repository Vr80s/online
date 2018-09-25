/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: Br096S66HaqWb1BHRAXhzBTmzfBpaPK+
 */
package net.shopxx.merge.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Entity - 收货地址
 * 
 * @author SHOP++ Team
 * @version 6.1
 */
@Data
public class ReceiverVO implements Serializable {

	private Long id;
	/**
	 * 收货人
	 */
	private String consignee;

	/**
	 * 地区名称
	 */
	private String areaName;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 邮编
	 */
	private String zipCode;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 是否默认
	 */
	private Boolean isDefault;

	/**
	 * 地区
	 */
	private AreaVO area;

}