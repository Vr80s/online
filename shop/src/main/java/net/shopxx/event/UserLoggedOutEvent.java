/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: Xd08urhI/FdLzDGvd8I4+dOOWFQUrlEM
 */
package net.shopxx.event;

import net.shopxx.entity.User;

/**
 * Event - 用户注销
 * 
 * @author ixincheng
 * @version 6.1
 */
public class UserLoggedOutEvent extends UserEvent {

	private static final long serialVersionUID = 8560275705072178478L;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param user
	 *            用户
	 */
	public UserLoggedOutEvent(Object source, User user) {
		super(source, user);
	}

}