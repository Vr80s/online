/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: uX+G8SJSINSZ7VADfQwkZZMe+uYYODPk
 */
package net.shopxx.service;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Member;
import net.shopxx.entity.PointLog;

/**
 * Service - 积分记录
 * 
 * @author ixincheng
 * @version 6.1
 */
public interface PointLogService extends BaseService<PointLog, Long> {

	/**
	 * 查找积分记录分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 积分记录分页
	 */
	Page<PointLog> findPage(Member member, Pageable pageable);

}