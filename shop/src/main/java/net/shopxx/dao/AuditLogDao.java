/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: O+Rs3cQWuAu7Hqd0D2Yzt6jbCXAC4qUx
 */
package net.shopxx.dao;

import net.shopxx.entity.AuditLog;

/**
 * Dao - 审计日志
 * 
 * @author SHOP++ Team
 * @version 6.1
 */
public interface AuditLogDao extends BaseDao<AuditLog, Long> {

	/**
	 * 删除所有
	 */
	void removeAll();

}