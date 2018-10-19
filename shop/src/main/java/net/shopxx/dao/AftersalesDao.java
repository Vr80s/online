/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: 54nIRaC3LMW0Mtg3zxSpI4vprECa9yAs
 */
package net.shopxx.dao;

import java.util.List;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Aftersales;
import net.shopxx.entity.Member;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.Store;

/**
 * Dao - 售后
 * 
 * @author ixincheng
 * @version 6.1
 */
public interface AftersalesDao extends BaseDao<Aftersales, Long> {

	/**
	 * 查找售后列表
	 * 
	 * @param orderItems
	 *            订单项
	 * @return 售后列表
	 */
	List<Aftersales> findList(List<OrderItem> orderItems);

	/**
	 * 查找售后分页
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param member
	 *            会员
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页信息
	 * @return 售后分页
	 */
	Page<Aftersales> findPage(Aftersales.Type type, Aftersales.Status status, Member member, Store store, Pageable pageable);

}