package com.xczhihui.bxg.online.web.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.online.api.service.RechargesService;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Recharges;
import com.xczhihui.bxg.online.web.dao.RechargesServiceDao;

/**
 * 充值实现类
 *
 */
@Service("rechargesService")
public class RechargesServiceImpl  extends OnlineBaseServiceImpl implements RechargesService {

	@Autowired
	private RechargesServiceDao rechargesServiceDao;
	
	
	@Override
	public List<Recharges> getRecharges() {
		
		return rechargesServiceDao.getRecharges();
	}
	
}
