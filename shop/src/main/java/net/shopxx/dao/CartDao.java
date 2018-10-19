/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: S7HzSLdjifMPrBY5ERLSDNMUq9x2ErfR
 */
package net.shopxx.dao;

import net.shopxx.entity.Cart;

/**
 * Dao - 购物车
 * 
 * @author ixincheng
 * @version 6.1
 */
public interface CartDao extends BaseDao<Cart, Long> {

	/**
	 * 删除过期购物车
	 */
	void deleteExpired();
}