package com.xczh.consumer.market.service;

import java.math.BigDecimal;

import com.xczh.consumer.market.utils.ResponseObject;

/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/10/11 10:18
 */
public interface iphoneIpaService {


    /**
     * 苹果充值
     * @param json
     * @param actualPrice
     */
    void increase(String userId,int xmb,String json, String actualPrice);

    ResponseObject iapOrder(String userId, Double xmb, String json, String actualPrice,String courserName);

    /**
     * 
     * Description：熊猫币购买课程
     * @param userId
     * @param xmb
     * @param order_no
     * @param actualPrice
     * @param courderName
     * @param orderForm
     * @return
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
	ResponseObject iapNewOrder(String userId, BigDecimal xmb, String order_no,
			String actualPrice, String courderName,Integer orderForm);

	/**
	 * Description：苹果充值
	 * @param userId
	 * @param multiply
	 * @param resp
	 * @param actualPrice
	 * @param merchantOrderNo  商户订单号
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	void increaseNew(String merchantOrderNo,String userId, BigDecimal multiply, String resp,
			BigDecimal actualPrice,String transactionId);

}
