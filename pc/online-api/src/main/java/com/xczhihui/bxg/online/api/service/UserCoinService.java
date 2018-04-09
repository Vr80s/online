package com.xczhihui.bxg.online.api.service;


import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.vo.OrderVo;
import com.xczhihui.bxg.online.api.vo.RechargeRecord;
import com.xczhihui.bxg.common.util.enums.OrderFrom;
import com.xczhihui.bxg.common.util.enums.Payment;

import java.math.BigDecimal;
import java.util.List;


/** 
 * ClassName: UserCoinService.java <br>
 * Description: <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月11日<br>
 */
public interface UserCoinService {

	/** 
	 * Description：获取用户熊猫币余额（平台赠送+充值）
	 * @param userId
	 * @return
	 * @return Map<String,String>
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public String getBalanceByUserId(String userId);

	/**
	 * Description：可结算熊猫币金额
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 5:50 2018/1/29 0029
	 **/
	public String getSettlementBalanceByUserId(String userId);

	/**
	 * Description：可提现人民币余额
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 5:50 2018/1/29 0029
	 **/
	public String getEnchashmentBalanceByUserId(String userId);

	/**
	 * Description：熊猫币充值方法
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 2:42 2018/1/29 0029
	 **/
	public void updateBalanceForRecharge(String userId, Payment payment, BigDecimal coin, OrderFrom orderFrom, String orderNo);

	/**
	 * Description：用户使用熊猫币买课
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 5:04 2018/1/29 0029
	 **/
	public String updateBalanceForBuyCourse(String userId, OrderFrom orderFrom, BigDecimal coin, String orderNo);

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
	 * @return void
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public void updateBalanceForGift(Object giftStatement);

	/**
	 * Description：多订单课程主播分成
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 8:19 2018/1/29 0029
	 **/
    public void updateBalanceForCourses(List<OrderVo> orderVos);

    /**
	 * Description：打赏主播余额增加
	 * @return void
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public void updateBalanceForReward(Object rs)throws Exception;


	public boolean checkRechargeOrder(String orderNo);

	/**
	 * Description：获取用户熊猫币消费记录
	 * @param userId
	 * @return void
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Object getUserCoinConsumptionRecord(String userId, Integer pageNumber, Integer pageSize);

	/**
	 * Description：结算熊猫币
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 2:04 2018/1/29 0029
	 **/
    void updateBalanceForSettlement4Lock(String lockKey, String userId, int amount, OrderFrom orderFrom);

    /**
     * Description：提现申请
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/3/5 0005 下午 3:32
     **/
	void saveEnchashmentApplyInfo4Lock(String lockKey, String userId, BigDecimal enchashmentSum, int bankCardId, OrderFrom orderFrom);
}
