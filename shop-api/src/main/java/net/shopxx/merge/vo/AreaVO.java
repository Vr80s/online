/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: zv0q3PAbirqt3KE+X28BJXhvnmWK35RT
 */
package net.shopxx.merge.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity - 地区
 * 
 * @author SHOP++ Team
 * @version 6.1
 */
@Data
public class AreaVO implements Serializable{

	public Long id ;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 全称
	 */
	private String fullName;

	/**
	 * 树路径
	 */
	private String treePath;

	/**
	 * 层级
	 */
	private Integer grade;

	/**
	 * 上级地区
	 */
	private AreaVO parent;

	/**
	 * 下级地区
	 */
	private Set<AreaVO> children = new HashSet<AreaVO>();

	/**
	 * 收货地址
	 */
	private Set<ReceiverVO> receivers = new HashSet<ReceiverVO>();

	/**
	 * 订单
	 */
	private Set<OrderVO> orders = new HashSet<OrderVO>();


}