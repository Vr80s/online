package com.xczh.consumer.market.service.impl;

import java.math.BigDecimal;

import com.xczhihui.common.util.enums.OrderFrom;
import com.xczhihui.common.util.enums.Payment;
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
import com.xczhihui.online.api.service.UserCoinService;

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
}
