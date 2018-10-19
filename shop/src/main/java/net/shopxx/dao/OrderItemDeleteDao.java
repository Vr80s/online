/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: qzyBSgrB+s+VBml3+UDgO/Sv1/6lGvTR
 */
package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.OrderItemDelete;
import net.shopxx.merge.vo.OrderItemVO;

/**
 * Dao - 订单项
 * 
 * @author ixincheng
 * @version 6.1
 */
public interface OrderItemDeleteDao extends BaseDao<OrderItemDelete, Long> {

	/**  
	 * <p>Title: findByOrders</p>  
	 * <p>Description: </p>  
	 * @return  
	 */ 
	List<OrderItemVO> findByOrders(Long orderId);

	/**  
	 * <p>Title: findByOrders</p>  
	 * <p>Description: </p>  
	 * @param productName
	 * @return  
	 */ 
	List<Long> findOrderIds(String productName);

}