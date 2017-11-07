package com.xczhihui.bxg.online.common.domain;


import com.xczhihui.bxg.online.common.utils.JsonUtil;

/**
 * 日志实体
 * @author majian
 * @date 2016-4-18
 */
public interface ILogEntity {

    /**
     * 打印日志
     * @return
     */
    public default String log(){
        return JsonUtil.toJson(this);
    }
}
