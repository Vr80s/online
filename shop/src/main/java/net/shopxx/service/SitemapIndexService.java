/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: afpufon8xnvY0ZNtobVrUcZErN+KPAK/
 */
package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.SitemapIndex;
import net.shopxx.entity.SitemapUrl;

/**
 * Service - Sitemap索引
 * 
 * @author ixincheng
 * @version 6.1
 */
public interface SitemapIndexService {

	/**
	 * 生成Sitemap索引
	 * 
	 * @param type
	 *            类型
	 * @param maxSitemapUrlSize
	 *            最大Sitemap URL数量
	 * @return Sitemap索引
	 */
	List<SitemapIndex> generate(SitemapUrl.Type type, int maxSitemapUrlSize);

}