package com.xczhihui.bxg.online.api.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.Gift;
import com.xczhihui.bxg.online.api.po.GiftStatement;
import com.xczhihui.bxg.online.api.po.RewardStatement;
import com.xczhihui.bxg.online.api.po.UserCoinConsumption;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
import com.xczhihui.bxg.online.api.vo.OrderVo;
import com.xczhihui.bxg.online.api.vo.RechargeRecord;
import com.xczhihui.bxg.online.common.enums.OrderFrom;
import com.xczhihui.bxg.online.common.enums.Payment;


/** 
 * ClassName: UserCoinService.java <br>
 * Description: <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月11日<br>
 */
public interface UserCoinService {

	/** 
	 * Description：获取用户熊猫币余额 -- 充值和平台赠送了
	 * @param userId
	 * @return
	 * @return Map<String,String>
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public String getBalanceByUserId(String userId);

	/**
	 * Description：可结算熊猫币金额 -- 分成的，可结算为人民币的余额
	 * 
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
	 * Description：用户使用熊猫币买课
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 5:04 2018/1/29 0029
	 **/
	public void updateBalanceForBuyCourse(String userId, OrderFrom orderFrom, BigDecimal coin, String orderNo);

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
	public void updateBalanceForGift(GiftStatement giftStatement, Gift gift);

	/**
	 * Description：多订单课程主播分成
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 8:19 2018/1/29 0029
	 **/
    void updateBalanceForCourses(List<OrderVo> orderVos);

    /**
	 * Description：打赏主播余额增加
	 * @return void
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public void updateBalanceForReward(RewardStatement rs)throws Exception;

	/**
	 * Description：提现-更改用户的人民币余额
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 4:34 2018/1/29 0029
	 **/
	public UserCoinConsumption updateBalanceForEnchashment(String userId, BigDecimal enchashmentSum, OrderFrom orderFrom,String enchashmentApplyId);

	public boolean checkRechargeOrder(String orderNo);

	/**
	 * Description：获取用户熊猫币消费记录
	 * @param userId
	 * @return void
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Object getUserCoinConsumptionRecord(String userId, Integer pageNumber, Integer pageSize);

	/**
	 * Description：买课后，给主播分成
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 8:19 2018/1/29 0029
	 **/
	public void updateBalanceForCourse(OrderVo orderVo);

	/**
	 * Description：结算熊猫币
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 2:04 2018/1/29 0029
	 **/
    void updateBalanceForSettlement(String userId, int amount, OrderFrom orderFrom);

}
