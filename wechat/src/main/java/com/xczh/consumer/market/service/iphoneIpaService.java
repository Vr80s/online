package com.xczh.consumer.market.service;

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

}
