/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: UvbLH44JVcH0gk+tjkuDNQpXHjuw+eD9
 */
package net.shopxx.service;

import net.shopxx.entity.AuditLog;

/**
 * Service - 审计日志
 * 
 * @author ixincheng
 * @version 6.1
 */
public interface AuditLogService extends BaseService<AuditLog, Long> {

	/**
	 * 创建审计日志(异步)
	 * 
	 * @param auditLog
	 *            审计日志
	 */
	void create(AuditLog auditLog);

	/**
	 * 清空审计日志
	 */
	void clear();

}