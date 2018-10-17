/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: 6QKme46qtbLw0qkpQbXQ/DPghin0NLWe
 */
package net.shopxx.dao;

import java.util.List;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order.Status;
import net.shopxx.entity.Order.Type;
import net.shopxx.entity.OrderDelete;
import net.shopxx.entity.Product;
import net.shopxx.entity.Store;
import net.shopxx.merge.enums.OrderType;
import net.shopxx.merge.vo.OrderPageParams;

/**
 * Dao - 订单
 * 
 * @author SHOP++ Team
 * @version 6.1
 */
public interface OrderDeleteDao extends BaseDao<OrderDelete, Long> {

	/**  
	 * <p>Title: findPageXc</p>  
	 * <p>Description: </p>  
	 * @param orderPageParams
	 * @param general
	 * @param object
	 * @param stores
	 * @param member
	 * @param object2
	 * @param pageable
	 * @param orderType
	 * @return  
	 */ 
	Page<OrderDelete> findPageXc(OrderPageParams orderPageParams, OrderDelete.Type general,OrderDelete.Status status, List<Store> stores,
			Member member, Product product, Pageable pageable, OrderType orderType);



}