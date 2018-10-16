/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: o7u5GLnjUaE24MigiEYSZ7cZD5zmeKXl
 */
package net.shopxx.dao;

import net.shopxx.entity.CartItem;

import java.util.List;

/**
 * Dao - 购物车项
 *
 * @author SHOP++ Team
 * @version 6.1
 */
public interface CartItemDao extends BaseDao<CartItem, Long> {

    List<CartItem> getCartItemListByIds(Long[] ids);
    /**
     * 查询购物车项，饥饿加载sku
     *
     * @param id id
     * @return CartItem
     */
    CartItem findFetchSku(Long id);
	/**  
	 * <p>Title: updateCartItemChecked</p>  
	 * <p>Description: </p>  
	 * @param ids
	 * @param isChecked
	 * @return  
	 */ 
	Integer updateCartItemChecked(List<Long> ids, Boolean isChecked,Long cartId);
}