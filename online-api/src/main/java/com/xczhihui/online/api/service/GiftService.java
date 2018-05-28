package com.xczhihui.online.api.service;


import java.util.Map;

import com.xczhihui.common.util.enums.OrderFrom;


/** 
 * ClassName: GiftService.java <br>
 * Description: 礼物打赏赠送逻辑层<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
public interface GiftService {
	
	/** 
	 * Description：送礼物
	 * @return
	 * @return GiftStatement
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Map<String,Object> addGiftStatement(String giverId, String receiverId, String giftId, OrderFrom orderFrom, int count, String liveId);

    /**
	 * Description：获取所有礼物
	 * @return
	 * @return List<Gift>
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Object getGift();

	/** 
	 * Description：通过主播查询礼物数量
	 * @param receiver
	 * @return
	 * @return int
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public int findByLiveId(Integer liveId);

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
	 * Description：分页获取该主播的直播课程
	 * @param userId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @return Object
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Object getLiveCourseByUserId (String userId, Integer pageNumber,	Integer pageSize);

	/**
	 * Description：获取直播课程对应的课程报名情况
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 8:20 2017/12/26 0026
	 **/
	Object getLiveCourseUsersById(String id, String userId, Integer pageNumber, Integer pageSize);
	
	
	/**
	 * 获取用户礼物榜单
	 * @param liveId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Object getRankingListByLiveId(String liveId, int pageNumber, int pageSize);
	
}
