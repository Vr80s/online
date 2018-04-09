package com.xczhihui.bxg.online.web.service.impl;


import com.xczhihui.bxg.online.common.domain.Recharges;
import com.xczhihui.bxg.online.api.service.RechargesService;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.web.dao.RechargesServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
