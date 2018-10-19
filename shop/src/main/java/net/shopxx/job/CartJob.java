/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: 6UxJSUX2I1QjE3CF/PrJXxa31JmKjNgD
 */
package net.shopxx.job;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.shopxx.service.CartService;

/**
 * Job - 购物车
 * 
 * @author ixincheng
 * @version 6.1
 */
@Lazy(false)
@Component
public class CartJob {

	@Inject
	private CartService cartService;

	/**
	 * 删除过期购物车
	 */
	@Scheduled(cron = "${job.cart_delete_expired.cron}")
	public void deleteExpired() {
		cartService.deleteExpired();
	}

}