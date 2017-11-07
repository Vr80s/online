package com.xczh.consumer.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.dao.IphoneIpaMapper;
import com.xczh.consumer.market.service.iphoneIpaService;
import com.xczh.consumer.market.utils.RandomUtil;
import com.xczh.consumer.market.utils.TimeUtil;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

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

        UserCoinIncrease userCoinIncrease=new UserCoinIncrease();
        userCoinIncrease.setUserId(userId);
        userCoinIncrease.setChangeType(1);
        userCoinIncrease.setPayType(3);
        userCoinIncrease.setValue(new BigDecimal(xmb));
        userCoinIncrease.setCreateTime(new Date());
        userCoinIncrease.setOrderFrom(3);
        userCoinIncrease.setOrderNoRecharge(TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12));

        iphoneIpaMapper.save(json,actualPrice,JSONObject.parseObject(json).getJSONObject("receipt").getJSONArray("in_app").getJSONObject(0).get("transaction_id").toString(),userCoinIncrease.getOrderNoRecharge(),userId,"充值熊猫币："+xmb+"个");
        userCoinService.updateBalanceForIncrease(userCoinIncrease);
    }
}
