package com.xczhihui.gift.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.gift.vo.RewardStatementVo;

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
