/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: gfFiexGDc/EvHS+hwsJoauC9u/eu20sV
 */
package net.shopxx.dao;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.ShippingMethod;

/**
 * Dao - 配送方式
 * 
 * @author ixincheng
 * @version 6.1
 */
public interface ShippingMethodDao extends BaseDao<ShippingMethod, Long> {

	/**
	 * 查找配送方式分页
	 * 
	 * @param pageable
	 *            分页
	 * @return 配送方式分页
	 */
	Page<ShippingMethod> findPage(Pageable pageable);
}