package com.xczhihui.bxg.online.manager.gift.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.gift.vo.RewardStatementVo;

public interface RewardStatementService {


	/**
	 * 根据条件分页获取打赏
	 * 
	 * @param groups
	 * @param pageVo
	 * @return
	 */
	public Page<RewardStatementVo> findRewardPage(RewardStatementVo rewardStatementVo, int pageNumber, int pageSize);


}
