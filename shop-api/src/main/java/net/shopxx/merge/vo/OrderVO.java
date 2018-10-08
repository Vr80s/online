/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: Q4sIHeHcA4GSzrI/jfA8QgtN+KKCkT70
 */
package net.shopxx.merge.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entity - 订单
 * 
 * @author SHOP++ Team
 * @version 6.1
 */
@Data
public class OrderVO implements Serializable {

	/**
	 * 类型
	 */
	public enum Type {

		/**
		 * 普通订单
		 */
		GENERAL,

		/**
		 * 兑换订单
		 */
		EXCHANGE
	}

	/**
	 * 状态
	 */
	public enum Status {

		/**
		 * 等待付款
		 */
		PENDING_PAYMENT,

		/**
		 * 等待审核
		 */
		PENDING_REVIEW,

		/**
		 * 等待发货
		 */
		PENDING_SHIPMENT,

		/**
		 * 已发货
		 */
		SHIPPED,

		/**
		 * 已收货
		 */
		RECEIVED,

		/**
		 * 已完成
		 */
		COMPLETED,

		/**
		 * 已失败
		 */
		FAILED,

		/**
		 * 已取消
		 */
		CANCELED,

		/**
		 * 已拒绝
		 */
		DENIED
	}

	/**
	 * 佣金类型
	 */
	public enum CommissionType {

		/**
		 * 平台
		 */
		PLATFORM,

		/**
		 * 分销
		 */
		DISTRIBUTION
	}

	private Long id;

	private Date createdDate;

	private Date lastModifiedDate;

	private Long version;
	/**
	 * 编号
	 */
	private String sn;

	/**
	 * 类型
	 */
	private OrderVO.Type type;

	/**
	 * 状态
	 */
	private OrderVO.Status status;

	/**
	 * 价格
	 */
	private BigDecimal price;

	/**
	 * 支付手续费
	 */
	private BigDecimal fee;

	/**
	 * 运费
	 */
	private BigDecimal freight;

	/**
	 * 税金
	 */
	private BigDecimal tax;

	/**
	 * 促销折扣
	 */
	private BigDecimal promotionDiscount;

	/**
	 * 优惠券折扣
	 */
	private BigDecimal couponDiscount;

	/**
	 * 调整金额
	 */
	private BigDecimal offsetAmount;

	/**
	 * 订单金额
	 */
	private BigDecimal amount;

	/**
	 * 已付金额
	 */
	private BigDecimal amountPaid;

	/**
	 * 退款金额
	 */
	private BigDecimal refundAmount;

	/**
	 * 赠送积分
	 */
	private Long rewardPoint;

	/**
	 * 兑换积分
	 */
	private Long exchangePoint;

	/**
	 * 重量
	 */
	private Integer weight;

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
	 * 附言
	 */
	private String memo;

	/**
	 * 过期时间
	 */
	private Date expire;

	/**
	 * 是否已使用优惠码
	 */
	private Boolean isUseCouponCode;

	/**
	 * 是否已兑换积分
	 */
	private Boolean isExchangePoint;

	/**
	 * 是否已分配库存
	 */
	private Boolean isAllocatedStock;

	/**
	 * 支付方式名称
	 */
	private String paymentMethodName;

	/**
	 * 配送方式名称
	 */
	private String shippingMethodName;

	/**
	 * 完成日期
	 */
	private Date completeDate;

	/**
	 * 订单项
	 */
	private List<OrderItemVO> orderItems = new ArrayList<OrderItemVO>();

	/**
	 * 地区
	 */
	private AreaVO area;


	/**
	 * 是否已评论
	 */
	private Boolean isReviewed;

	/**
	 * 促销名称
	 */
	private List<String> promotionNames = new ArrayList<String>();

	private StoreVO store;
}