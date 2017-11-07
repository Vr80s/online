package com.xczhihui.bxg.online.api.service;


import java.math.BigDecimal;
import java.util.Map;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.Gift;
import com.xczhihui.bxg.online.api.po.GiftStatement;
import com.xczhihui.bxg.online.api.po.RewardStatement;
import com.xczhihui.bxg.online.api.po.UserCoinConsumption;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
import com.xczhihui.bxg.online.api.vo.RechargeRecord;


/** 
 * ClassName: UserCoinService.java <br>
 * Description: <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月11日<br>
 */
public interface UserCoinService {

	/** 
	 * Description：获取用户熊猫币余额
	 * @param userId
	 * @return
	 * @return Map<String,String>
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Map<String,String> getBalanceByUserId(String userId);
	
	/** 
	 * Description：用户熊猫币新增
	 * @param uci
	 * @return void
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public void updateBalanceForIncrease(UserCoinIncrease uci);
	
	/** 
	 * Description：用户熊猫币消费
	 * @param ucc
	 * @return UserCoinConsumption
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public UserCoinConsumption updateBalanceForConsumption(UserCoinConsumption ucc);

	/** 
	 * Description：添加用户熊猫
	 * @param userId
	 * @return void
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public void saveUserCoin(String userId);

	/**
	 * Description：用户充值记录
	 * @param userId
	 * @return void
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Page<RechargeRecord> getUserCoinIncreaseRecord(String userId, Integer pageNumber, Integer pageSize);

	/** 
	 * Description：送礼物相关余额扣减和增加
	 * @param giftStatement
	 * @param gift
	 * @return void
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public void updateBalanceForGift(GiftStatement giftStatement,Gift gift);
	
	/** 
	 * Description：打赏主播余额增加
	 * @param giftStatement
	 * @param gift
	 * @return void
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public void updateBalanceForReward(RewardStatement rs)throws Exception;

	/** 
	 * Description：获取可提现余额（熊猫币）
	 * @param userId
	 * @return void
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public BigDecimal getEnableEnchashmentBalance(String userId);

	public UserCoinConsumption updateBalanceForEnchashment(UserCoinConsumption ucc);

	public boolean checkRechargeOrder(String orderNo);

	/**
	 * Description：获取用户熊猫币消费记录
	 * @param userId
	 * @return void
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Object getUserCoinConsumptionRecord(String userId, Integer pageNumber, Integer pageSize);
}