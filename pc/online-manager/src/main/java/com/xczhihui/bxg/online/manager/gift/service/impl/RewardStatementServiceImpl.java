package com.xczhihui.bxg.online.manager.gift.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.gift.dao.RewardStatementDao;
import com.xczhihui.bxg.online.manager.gift.service.RewardStatementService;
import com.xczhihui.bxg.online.manager.gift.vo.RewardStatementVo;


/** 
 * ClassName: RewardStatementServiceImpl.java <br>
 * Description: 打赏业务层实现类<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
@Service("rewardStatementService")
public class RewardStatementServiceImpl extends OnlineBaseServiceImpl implements RewardStatementService {

	@Autowired
	private RewardStatementDao rewardStatementDao;

	@Override
	public Page<RewardStatementVo> findRewardPage(RewardStatementVo rewardStatementVo, int pageNumber, int pageSize) {
		Page<RewardStatementVo> page = rewardStatementDao.findRewardStatementPage(rewardStatementVo, pageNumber, pageSize);
		return page;

	}

}
