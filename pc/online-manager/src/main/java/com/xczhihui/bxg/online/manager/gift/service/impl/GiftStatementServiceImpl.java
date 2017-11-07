package com.xczhihui.bxg.online.manager.gift.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.gift.dao.GiftStatementDao;
import com.xczhihui.bxg.online.manager.gift.service.GiftStatementService;
import com.xczhihui.bxg.online.manager.gift.vo.GiftStatementVo;

/**
 * GiftServiceImpl:礼物业务层接口实现类
 * 
 * @author Rongcai Kang
 */
@Service("giftStatementService")
public class GiftStatementServiceImpl extends OnlineBaseServiceImpl implements GiftStatementService {

	@Autowired
	private GiftStatementDao giftStatementDao;

	@Override
	public Page<GiftStatementVo> findGiftPage(GiftStatementVo giftStatementVo, int pageNumber, int pageSize) {
		Page<GiftStatementVo> page = giftStatementDao.findGiftStatementPage(giftStatementVo, pageNumber, pageSize);
		return page;

	}

}
