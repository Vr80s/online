/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: eRkUNMuE29DQEPv/KJstwwRSWsQBN6cr
 */
package net.shopxx.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.shopxx.dao.SnDao;
import net.shopxx.entity.Sn;
import net.shopxx.service.SnService;

/**
 * Service - 序列号
 * 
 * @author ixincheng
 * @version 6.1
 */
@Service
public class SnServiceImpl implements SnService {

	@Inject
	private SnDao snDao;

	@Override
	@Transactional
	public String generate(Sn.Type type) {
		return snDao.generate(type);
	}

}