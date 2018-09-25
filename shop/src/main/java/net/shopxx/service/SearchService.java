/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: Jo/hqAUkBZWnYIFpuH5uW958XHeWn7SM
 */
package net.shopxx.service;

/**
 * Service - 搜索
 * 
 * @author SHOP++ Team
 * @version 6.1
 */
public interface SearchService {

	/**
	 * 创建索引
	 * 
	 * @param type
	 *            索引类型
	 */
	void index(Class<?> type);

	/**
	 * 创建索引
	 * 
	 * @param type
	 *            索引类型
	 * @param purgeAll
	 *            是否清空已存在索引
	 */
	void index(Class<?> type, boolean purgeAll);

}