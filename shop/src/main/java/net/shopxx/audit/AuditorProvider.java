/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: zLs/G9OItI8NWFdE2GVjwuZW5FMQgvFf
 */
package net.shopxx.audit;

/**
 * Audit - 审计者Provider
 * 
 * @author SHOP++ Team
 * @version 6.1
 */
public interface AuditorProvider<T> {

	/**
	 * 获取当前审计者
	 * 
	 * @return 当前审计者
	 */
	T getCurrentAuditor();

}