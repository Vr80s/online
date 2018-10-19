/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: Nx95uwWsaq0CBZqhnLzlelvLd8WrIVn9
 */
package net.shopxx.dao;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.MessageGroup;
import net.shopxx.entity.User;

/**
 * Dao - 消息组
 * 
 * @author ixincheng
 * @version 6.1
 */
public interface MessageGroupDao extends BaseDao<MessageGroup, Long> {

	/**
	 * 查找
	 * 
	 * @param user
	 *            用户
	 * @param pageable
	 *            分页信息
	 * @return 消息组分页
	 */
	Page<MessageGroup> findPage(User user, Pageable pageable);

	/**
	 * 查找
	 * 
	 * @param user1
	 *            用户1
	 * @param user2
	 *            用户2
	 * @return 消息组
	 */
	MessageGroup find(User user1, User user2);

}