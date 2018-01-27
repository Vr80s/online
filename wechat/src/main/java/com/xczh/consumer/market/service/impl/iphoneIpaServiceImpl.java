package com.xczh.consumer.market.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import com.xczhihui.bxg.online.common.enums.IncreaseChangeType;
import com.xczhihui.bxg.online.common.enums.OrderForm;
import com.xczhihui.bxg.online.common.enums.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.dao.IphoneIpaMapper;
import com.xczh.consumer.market.service.iphoneIpaService;
import com.xczh.consumer.market.utils.RandomUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.TimeUtil;
import com.xczhihui.bxg.online.api.po.UserCoinConsumption;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
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
    		UserCoinIncrease userCoinIncrease=new UserCoinIncrease();
	        userCoinIncrease.setUserId(userId);
	        //充值
	        userCoinIncrease.setChangeType(IncreaseChangeType.RECHARGE.getCode());
	        //苹果支付
	        userCoinIncrease.setPayType(Payment.APPLYPAY.getCode());
	        userCoinIncrease.setValue(new BigDecimal(xmb));
	        userCoinIncrease.setCreateTime(new Date());
	        userCoinIncrease.setOrderFrom(OrderForm.IOS.getCode());
	        userCoinIncrease.setOrderNoRecharge(TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12));
	        
	        iphoneIpaMapper.save(json,
	        		actualPrice,
	        		JSONObject.parseObject(json).getJSONObject("receipt").getJSONArray("in_app").getJSONObject(0).get("transaction_id").toString(),
	        		userCoinIncrease.getOrderNoRecharge(),
	        		userId,
	        		"充值熊猫币："+xmb+"个",
	        		1);

	        userCoinService.updateBalanceForIncrease(userCoinIncrease);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    @Override
    public 	ResponseObject iapOrder(String userId,Double xmb,String orderNo,String actualPrice,String courserName) {
    	try {
    		//判断这个订单号中是否已经存在了，如果存在不存了
    		Integer c = iphoneIpaMapper.findIap(orderNo);
    		if(c<=0){
    			 
    			 //保存这个购买的信息
    			iphoneIpaMapper.save(null,actualPrice,TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12),
    		        		orderNo,userId,"购买课程："+courserName+"花费"+xmb+"个熊猫币",0);
		        /**
		         * 减去熊猫币
		         */
		        UserCoinConsumption ucc = new UserCoinConsumption();
		        ucc.setValue(new BigDecimal( -xmb));
		        ucc.setChangeType(10);//购买课程消耗的熊猫币
		        ucc.setUserId(userId);
		        userCoinService.updateBalanceForConsumption(ucc);
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
    
}
