package com.xczh.consumer.market.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xczh.consumer.market.dao.IphoneIpaMapper;
import com.xczh.consumer.market.service.iphoneIpaService;
import com.xczhihui.common.support.lock.Lock;
import com.xczhihui.common.util.enums.OrderFrom;
import com.xczhihui.common.util.enums.Payment;
import com.xczhihui.online.api.service.UserCoinService;

/**
 * @author liutao
 * @create 2017-10-11 10:18
 **/
@Service
public class iphoneIpaServiceImpl implements iphoneIpaService {

    @Autowired
    private IphoneIpaMapper iphoneIpaMapper;

    @Autowired
    private UserCoinService userCoinService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Lock(lockName = "iphoneIapLock", waitTime = 5, effectiveTime = 8)
    public void increaseNew(String OrderNo, String userId, BigDecimal xmb, String json,
                            BigDecimal actualPrice, String transactionId) {

        iphoneIpaMapper.save(json,
                actualPrice + "",
                transactionId,
                OrderNo,
                userId,
                "充值熊猫币：" + xmb.doubleValue() + "个",
                1);

        userCoinService.updateBalanceForRecharge(userId, Payment.APPLYPAY, xmb,
                OrderFrom.IOS, OrderNo);
    }
}
