/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: Oo/MNbe+/PN4jfqYI2V8/IamWxjm95id
 */
package net.shopxx.dao;

import net.shopxx.entity.Seo;

/**
 * Dao - SEO设置
 * 
 * @author ixincheng
 * @version 6.1
 */
public interface SeoDao extends BaseDao<Seo, Long> {

	/**
	 * 查找SEO设置
	 * 
	 * @param type
	 *            类型
	 * @return SEO设置
	 */
	Seo find(Seo.Type type);

}