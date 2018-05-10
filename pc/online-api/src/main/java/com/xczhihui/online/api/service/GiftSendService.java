package com.xczhihui.online.api.service;

import com.xczhihui.common.util.enums.OrderFrom;

import java.util.Map;

/** 
 * ClassName: GiftService.java <br>
 * Description: 礼物打赏赠送逻辑层<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
public interface GiftSendService {

    Map<String,Object> addGiftStatement4Lock(String lockKey, String giverId, String receiverId, String giftId, OrderFrom orderFrom, int count, String liveId);
}
