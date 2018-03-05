package com.xczhihui.bxg.online.api.service;


import com.xczhihui.bxg.online.common.enums.OrderFrom;

import java.util.Map;


/** 
 * ClassName: GiftService.java <br>
 * Description: 礼物打赏赠送逻辑层<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
public interface GiftSendService {

	/**
	 * Description：通过主播查询礼物数量
	 * @param receiver
	 * @return
	 * @return int
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public int findByUserId(String receiver);

    Map<String,Object> addGiftStatement4Lock(String lockKey, String giverId, String receiverId, String giftId, OrderFrom orderFrom, int count, String liveId);
}
