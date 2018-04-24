package com.xczh.consumer.market.service.impl;

import java.math.BigDecimal;

import com.xczhihui.bxg.common.util.enums.OrderFrom;
import com.xczhihui.bxg.common.util.enums.Payment;
import com.xczhihui.common.support.lock.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.dao.IphoneIpaMapper;
import com.xczh.consumer.market.service.iphoneIpaService;
import com.xczh.consumer.market.utils.RandomUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.TimeUtil;
import com.xczhihui.bxg.online.api.service.UserCoinService;

/**
 * @author liutao
 * @create 2017-10-11 10:18
 **/
@Service
public class iphoneIpaServiceImpl implements iphoneIpaService {

    @Autowired
   private  IphoneIpaMapper iphoneIpaMapper;


    @Autowired
    private UserCoinService userCoinService;


    @Override
    public void increase(String userId,int xmb,String json, String actualPrice) {
    	try {

			String orderNoRecharge = TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);

	        iphoneIpaMapper.save(json,
	        		actualPrice,
	        		JSONObject.parseObject(json).getJSONObject("receipt").getJSONArray("in_app").getJSONObject(0).get("transaction_id").toString(),
					orderNoRecharge,
	        		userId,
	        		"充值熊猫币："+xmb+"个",
	        		1);

	        userCoinService.updateBalanceForRecharge(userId,Payment.APPLYPAY,new BigDecimal(xmb), OrderFrom.IOS,orderNoRecharge);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    @Override
    public 	ResponseObject iapOrder(String userId,Double xmb,String orderNo,String actualPrice,String courserName) {
//    	try {
//    		//判断这个订单号中是否已经存在了，如果存在不存了
//    		Integer c = iphoneIpaMapper.findIap(orderNo);
//    		if(c<=0){
//    			 //保存这个购买的信息
//    			iphoneIpaMapper.save(null,actualPrice,TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12),
//    		        		orderNo,userId,"购买课程："+courserName+"花费"+xmb+"个熊猫币",0);
//		        /**
//		         * 减去熊猫币
//		         */
//		        UserCoinConsumption ucc = new UserCoinConsumption();
//		        ucc.setValue(new BigDecimal( -xmb));
//				//购买课程消耗的熊猫币
//		        ucc.setChangeType(ConsumptionChangeType.COURSE.getCode());
////				ucc.setOrderFrom(Payment);
//		        ucc.setUserId(userId);
//		        userCoinService.updateBalanceForConsumption(ucc);
//
//		        return ResponseObject.newSuccessResponseObject("支付成功");
//    		}else{
//    			return ResponseObject.newErrorResponseObject("此订单已支付过");
//    		}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			return ResponseObject.newErrorResponseObject("服务器异常");
//		}
		return null;
    }

	@Override
	public ResponseObject iapNewOrder(String userId, BigDecimal xmb,
			String orderNo, String actualPrice, String courderName,Integer orderForm) {
	  	try {
    		//判断这个订单号中是否已经存在了，如果存在不存了
    		Integer c = iphoneIpaMapper.findIap(orderNo);
    		if(c<=0){
    			
    			//扣减熊猫币
    			userCoinService.updateBalanceForBuyCourse(userId,OrderFrom.valueOf(orderForm),xmb, orderNo);
		        return ResponseObject.newSuccessResponseObject("支付成功");
    		}else{
    			return ResponseObject.newErrorResponseObject("此订单已支付过");
    		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("服务器异常");
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Lock(lockName = "iphoneIapLock",waitTime = 5,effectiveTime = 8)
	public void increaseNew(String OrderNo,String userId, BigDecimal xmb, String json,
			BigDecimal actualPrice,String transactionId) {
		
		iphoneIpaMapper.save(json,
        		actualPrice+"",
				transactionId,
				OrderNo,
        		userId,
        		"充值熊猫币："+xmb.doubleValue()+"个",
        		1);
		
		userCoinService.updateBalanceForRecharge(userId,Payment.APPLYPAY,xmb,
				OrderFrom.IOS,OrderNo);
	}
    
	
	
	public static void main(String[] args) {
	      // create 2 BigDecimal objects
	      BigDecimal bg1, bg2;
	      // assign value to bg1
	      bg1 = new BigDecimal("40");
	      // value before applying abs
	      System.out.println("Value is " + bg1);
	      // assign absolute value of bg1 to bg2
	      bg2 = bg1.abs();
	      // print bg2 value
	      System.out.println("Absolute value is " + bg2);
	      BigDecimal  decimal = bg1.multiply(new BigDecimal(-1));
	      System.out.println( decimal);
	}
}
