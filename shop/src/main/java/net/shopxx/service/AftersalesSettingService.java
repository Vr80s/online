/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: J3SlhagEID+noK14NqGpcG5REnwv4Al5
 */
package net.shopxx.service;

import net.shopxx.entity.AftersalesSetting;
import net.shopxx.entity.Store;

/**
 * Service - 售后设置
 * 
 * @author ixincheng
 * @version 6.1
 */
public interface AftersalesSettingService extends BaseService<AftersalesSetting, Long> {

	/**
	 * 通过店铺查找售后设置
	 * 
	 * @param store
	 *            店铺
	 * @return 售后设置
	 */
	AftersalesSetting findByStore(Store store);

}