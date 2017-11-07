package com.xczhihui.bxg.online.api.service;


import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.online.api.po.Gift;
import com.xczhihui.bxg.online.api.po.GiftStatement;


/** 
 * ClassName: GiftService.java <br>
 * Description: 礼物打赏赠送逻辑层<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
public interface GiftService {
	
	/** 
	 * Description：送礼物
	 * @param giftStatement
	 * @return
	 * @return GiftStatement
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Map<String,Object> addGiftStatement(GiftStatement giftStatement);

	/** 
	 * Description：获取所有礼物
	 * @return
	 * @return List<Gift>
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public List<Gift> getGift();

	/** 
	 * Description：通过主播查询礼物数量
	 * @param receiver
	 * @return
	 * @return int
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public int findByUserId(String receiver);

	/** 
	 * Description：获取收到的礼物
	 * @param id
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @return Object
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Object getReceivedGift(String id, Integer pageNumber,
			Integer pageSize);

	/** 
	 * Description：获取收到的打赏
	 * @param id
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @return Object
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Object getReceivedReward(String id, Integer pageNumber,
			Integer pageSize);


	/**
	 * Description：送礼物(加锁)
	 * @param giftStatement
	 * @return
	 * @return GiftStatement
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Map<String,Object> sendGiftStatement(GiftStatement giftStatement);
}
