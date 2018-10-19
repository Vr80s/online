/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: QoBExZRZyTcS8Di6TEFdKxw4mV+shyRR
 */
package net.shopxx.service;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.DistributionCommission;
import net.shopxx.entity.Distributor;

/**
 * Service - 分销佣金
 * 
 * @author ixincheng
 * @version 6.1
 */
public interface DistributionCommissionService extends BaseService<DistributionCommission, Long> {

	/**
	 * 查找分销佣金记录分页
	 * 
	 * @param distributor
	 *            分销员
	 * @param pageable
	 *            分页信息
	 * @return 分销佣金记录分页
	 */
	Page<DistributionCommission> findPage(Distributor distributor, Pageable pageable);

}