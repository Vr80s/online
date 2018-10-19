/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: NXtQVGPKz6byDUSVxgAsfWsUgTmAAUNg
 */
package net.shopxx.merge.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity - 订单项
 * 
 * @author ixincheng
 * @version 6.1
 */
@Data
public class OrderItemVO implements Serializable {
	
	
	private Long id; 
	
	/**
	 * 编号
	 */
	private String sn;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 价格
	 */
	private BigDecimal price;

	/**
	 * 重量
	 */
	private Integer weight;

	/**
	 * 是否需要物流
	 */
	private Boolean isDelivery;

	/**
	 * 缩略图
	 */
	private String thumbnail;

	/**
	 * 数量
	 */
	private Integer quantity;

	/**
	 * 已发货数量
	 */
	private Integer shippedQuantity;

	/**
	 * 已退货数量
	 */
	private Integer returnedQuantity;

	/**
	 * 平台佣金总计
	 */
	private BigDecimal platformCommissionTotals;

	/**
	 * 分销佣金小计
	 */
	private BigDecimal distributionCommissionTotals;

	/**
	 * 规格
	 */
	private List<String> specifications = new ArrayList<String>();

	/**
	 * SKU
	 */
	private SkuVO sku;


}