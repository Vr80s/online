/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: 4JGqDBHw9N6pVpI9q2JL7Ke4MMIqJ11g
 */
package net.shopxx.service;

import java.util.List;
import java.util.Set;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Coupon;
import net.shopxx.entity.Store;

/**
 * Service - 优惠券
 * 
 * @author ixincheng
 * @version 6.1
 */
public interface CouponService extends BaseService<Coupon, Long> {

	/**
	 * 验证价格运算表达式
	 * 
	 * @param priceExpression
	 *            价格运算表达式
	 * @return 验证结果
	 */
	boolean isValidPriceExpression(String priceExpression);

	/**
	 * 查找优惠券
	 * 
	 * @param store
	 *            店铺
	 * @return 优惠券
	 */
	List<Coupon> findList(Store store);

	/**
	 * 查找优惠券
	 * 
	 * @param store
	 *            店铺
	 * @param isEnabled
	 *            是否启用
	 * @param isExchange
	 *            是否允许积分兑换
	 * @param hasExpired
	 *            是否已过期
	 * @return 优惠券
	 */
	List<Coupon> findList(Store store, Boolean isEnabled, Boolean isExchange, Boolean hasExpired);

	/**
	 * 查找优惠券
	 * 
	 * @param store
	 *            店铺
	 * @param matchs
	 *            匹配优惠券
	 * @return 优惠券
	 */
	List<Coupon> findList(Store store, Set<Coupon> matchs);

	/**
	 * 查找优惠券分页
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @param isExchange
	 *            是否允许积分兑换
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 优惠券分页
	 */
	Page<Coupon> findPage(Boolean isEnabled, Boolean isExchange, Boolean hasExpired, Pageable pageable);

	/**
	 * 查找优惠券分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页信息
	 * @return 优惠券分页
	 */
	Page<Coupon> findPage(Store store, Pageable pageable);

}