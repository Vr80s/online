/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: P7DaweetUeeL7luntfBZLAFMXIx5q7P+
 */
package net.shopxx.service;

import net.shopxx.entity.MessageConfig;

/**
 * Service - 消息配置
 * 
 * @author ixincheng
 * @version 6.1
 */
public interface MessageConfigService extends BaseService<MessageConfig, Long> {

	/**
	 * 查找消息配置
	 * 
	 * @param type
	 *            类型
	 * @return 消息配置
	 */
	MessageConfig find(MessageConfig.Type type);

}