/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: LTd8Dm/RUNI6Uk0z989S0TxJksIEv6E4
 */
package net.shopxx.dao;

import net.shopxx.entity.Order;
import net.shopxx.entity.OrderShipping;

/**
 * Dao - 订单发货
 * 
 * @author ixincheng
 * @version 6.1
 */
public interface OrderShippingDao extends BaseDao<OrderShipping, Long> {

	/**
	 * 查找最后一条订单发货
	 * 
	 * @param order
	 *            订单
	 * @return 订单发货，若不存在则返回null
	 */
	OrderShipping findLast(Order order);

}