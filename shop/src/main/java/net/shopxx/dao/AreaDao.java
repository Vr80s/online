/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: 50WsLWqRKzGnfjxFSerchzySWMHOKjAr
 */
package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.Area;

/**
 * Dao - 地区
 * 
 * @author ixincheng
 * @version 6.1
 */
public interface AreaDao extends BaseDao<Area, Long> {

	/**
	 * 查找顶级地区
	 * 
	 * @param count
	 *            数量
	 * @return 顶级地区
	 */
	List<Area> findRoots(Integer count);

	/**
	 * 查找上级地区
	 * 
	 * @param area
	 *            地区
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级地区
	 */
	List<Area> findParents(Area area, boolean recursive, Integer count);

	/**
	 * 查找下级地区
	 * 
	 * @param area
	 *            地区
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级地区
	 */
	List<Area> findChildren(Area area, boolean recursive, Integer count);

}