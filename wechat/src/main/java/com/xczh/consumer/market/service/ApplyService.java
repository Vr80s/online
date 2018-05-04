package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.Apply;

/**
 * 线下培训班报名服务
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/20 15:42
 */
public interface ApplyService {

    /**
     * 保存或更新基本信息
     * @param realName
     * @param phone
     */
    void saveOrUpdateBaseInfo(String userId,String realName,String phone);

    void updateDetailsInfo(Apply apply);

    Apply get(String userId);



}
