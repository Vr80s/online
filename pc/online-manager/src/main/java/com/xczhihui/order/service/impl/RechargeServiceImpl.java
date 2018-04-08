package com.xczhihui.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.support.service.SystemVariateService;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Order;
import com.xczhihui.order.dao.UserCoinIncreaseDao;
import com.xczhihui.order.service.RechargeService;

@Service
public class RechargeServiceImpl extends OnlineBaseServiceImpl implements RechargeService {

	@Autowired
	private UserCoinIncreaseDao userCoinIncreaseDao;
	@Autowired
	private SystemVariateService sv;
	
	@Override
	public Page<UserCoinIncrease> findUserCoinIncreasePage(UserCoinIncrease userCoinIncrease, Integer pageNumber, Integer pageSize) {
		Page<UserCoinIncrease> page = userCoinIncreaseDao.findUserCoinIncreasePage(userCoinIncrease, pageNumber, pageSize);
		return page;
	}

	@Override
	public void deletes(String[] ids) {
		// TODO Auto-generated method stub
		for(String id:ids){
			String hqlPre="from Order where isDelete=0 and id = ?";
			Order order= userCoinIncreaseDao.findByHQLOne(hqlPre,new Object[] {id});
            if(order !=null){
            	order.setDelete(true);
            	userCoinIncreaseDao.update(order);
            }
        }
	}
		
}
