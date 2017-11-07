package com.xczhihui.bxg.online.manager.gift.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.gift.vo.GiftStatementVo;

public interface GiftStatementService {


	/**
	 * 根据条件分页获取课程信息。
	 * 
	 * @param groups
	 * @param pageVo
	 * @return
	 */
	public Page<GiftStatementVo> findGiftPage(GiftStatementVo giftStatementVo, int pageNumber, int pageSize);


}
