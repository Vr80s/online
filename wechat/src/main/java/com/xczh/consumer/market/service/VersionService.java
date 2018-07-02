package com.xczh.consumer.market.service;

import com.xczh.consumer.market.vo.VersionInfoVo;

/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/7 21:00
 */
public interface VersionService {


    /**
     * 查看最新的app版本
     *
     * @param type app类型：1 IOS  2 安卓
     * @return
     */
    VersionInfoVo getNewVersion(Integer type);


}
