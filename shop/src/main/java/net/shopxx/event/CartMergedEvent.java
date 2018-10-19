/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: e4AdikV7QcL5x2vOga9KPqDB+PLp4fJg
 */
package net.shopxx.event;

import net.shopxx.entity.Cart;

/**
 * Event - 合并购物车
 * 
 * @author ixincheng
 * @version 6.1
 */
public class CartMergedEvent extends CartEvent {

	private static final long serialVersionUID = -320699877093325080L;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param cart
	 *            购物车
	 */
	public CartMergedEvent(Object source, Cart cart) {
		super(source, cart);
	}

}