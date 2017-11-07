package com.xczh.consumer.market.service;

import java.math.BigDecimal;

/**
 * @author liutao
 * @create 2017-08-28 17:56
 **/
public interface GoldService {

    /**
     * 获取总金币数量
     * @param userId
     * @return
     */
    BigDecimal getReflectNumber(String userId);

    /**
     * 获取可体现总金币数量
     * @param userId
     * @return
     */
    BigDecimal getCanReflectNumber(String userId);

    /**
     * 获取不可体现总金币数量
     * @param userId
     * @return
     */
    BigDecimal getNotCanReflectNumber(String userId);

    /**
     * 充值可体现金币
     * @param userId
     * @param goldNumber
     * @return
     */
    boolean rechargeCanReflect(String userId, BigDecimal goldNumber);

    /**
     * 充值不可体现金币
     * @param userId
     * @param goldNumber
     * @return
     */
    boolean rechargeNotCanReflect(String userId, BigDecimal goldNumber);

    /**
     * 消费可体现金币
     * @param userId
     * @param goldNumber
     * @return
     */
    boolean consumptionCanReflect(String userId, BigDecimal goldNumber);

    /**
     * 消费不可体现金币
     * @param userId
     * @param goldNumber
     * @return
     */
    boolean consumptionNotCanReflect(String userId, BigDecimal goldNumber);




}
