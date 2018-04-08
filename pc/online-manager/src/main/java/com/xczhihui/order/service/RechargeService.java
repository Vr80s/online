package com.xczhihui.order.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;

public interface RechargeService {

	public Page<UserCoinIncrease> findUserCoinIncreasePage(UserCoinIncrease userCoinIncrease, Integer pageNumber, Integer pageSize);
	
	/**
	 * 逻辑批量删除
	 * 
	 *@return void
	 */
	public void deletes(String[] ids);

}
