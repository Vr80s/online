/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: GNDilnktfQAeHc8XrcoH3ERaAa5p+eTK
 */
package net.shopxx.service;

import java.util.Collection;

import net.shopxx.entity.PaymentItem;
import net.shopxx.entity.PaymentTransaction;
import net.shopxx.plugin.PaymentPlugin;

/**
 * Service - 支付事务
 * 
 * @author SHOP++ Team
 * @version 6.1
 */
public interface PaymentTransactionService extends BaseService<PaymentTransaction, Long> {

	/**
	 * 根据编号查找支付事务
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 支付事务，若不存在则返回null
	 */
	PaymentTransaction findBySn(String sn);

	/**
	 * 生成支付事务
	 * 
	 * @param lineItem
	 *            支付明细
	 * @param paymentPlugin
	 *            支付插件
	 * @param rePayUrl
	 *            重新支付URL
	 * @return 支付事务
	 */
	PaymentTransaction generate(PaymentTransaction.LineItem lineItem, PaymentPlugin paymentPlugin, String rePayUrl);

	/**
	 * 生成父支付事务
	 * 
	 * @param lineItems
	 *            支付明细
	 * @param paymentPlugin
	 *            支付插件
	 * @param rePayUrl
	 *            重新支付URL
	 * @return 父支付事务
	 */
	PaymentTransaction generateParent(Collection<PaymentTransaction.LineItem> lineItems, PaymentPlugin paymentPlugin, String rePayUrl);

	/**
	 * 支付处理
	 * 
	 * @param paymentTransaction
	 *            支付事务
	 */
	void handle(PaymentTransaction paymentTransaction);

	/**
	 * 生成支付明细
	 * 
	 * @param paymentItem
	 *            支付项
	 * @param ipandatcmUserId
     * @return 支付明细
	 */
	PaymentTransaction.LineItem generate(PaymentItem paymentItem, String ipandatcmUserId);

}