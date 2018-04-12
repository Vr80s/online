package com.xczhihui.gift.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.gift.dao.GiftStatementDao;
import com.xczhihui.gift.service.GiftStatementService;
import com.xczhihui.gift.vo.GiftStatementVo;

/**
 * GiftStatementServiceImpl:礼物业务层接口实现类
 *
 * @author Rongcai Kang
 */
@Service("giftStatementService")
public class GiftStatementServiceImpl extends OnlineBaseServiceImpl implements
		GiftStatementService {

	@Autowired
	private GiftStatementDao giftStatementDao;

	@Override
	public Page<GiftStatementVo> findGiftPage(GiftStatementVo giftStatementVo,
			int pageNumber, int pageSize) {
		return giftStatementDao.findGiftStatementPage(giftStatementVo,
				pageNumber, pageSize);
	}
}
