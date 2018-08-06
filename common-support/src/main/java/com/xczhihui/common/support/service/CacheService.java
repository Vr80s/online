package com.xczhihui.common.support.service;

import java.io.Serializable;
import java.util.Set;

/**
 * 缓存接口
 *
 * @author liyong
 */
public interface CacheService {

    /**
     * 一分钟的秒数
     */
    int ONE_MINUTE = 60;

    /**
     * 一小时的秒数
     */
    int ONE_HOUR = ONE_MINUTE * 60;
    int FIVE_HOUR = ONE_HOUR * 5;

    /**
     * 一天的秒数
     */
    int ONE_DAY = ONE_HOUR * 24;

    int ONE_MONTH = ONE_DAY * 30;

    /**
     * 一年的秒数
     */
    int ONE_YEAR = ONE_DAY * 365;

    /**
     * 存入缓存
     *
     * @param key
     * @param obj
     * @param seconds
     */
    void set(final String key, final Serializable obj, final int seconds);

    /**
     * 从缓存中获取对象
     *
     * @param key
     * @return
     */
    <T extends Serializable> T get(final String key);

    /**
     * 删除缓存
     *
     * @param key
     */
    void delete(String key);

    void set(String key, Serializable obj);

    /**
     * 增加缓存中数值。
     *
     * @param key
     * @param number
     * @return
     */
    int inc(final String key, final int number);

    /**
     * 获取缓存中的数值
     *
     * @param key
     * @return
     */
    int getInt(final String key);

    Set<String> getKeys(String cMRKey);

    /**
     * 加入集合
     *
     * @param key
     * @param value
     * @return
     */
    Long sadd(String key, String value);

    /**
     * 判断集合中是否含有该元素
     *
     * @param key   key
     * @param value value
     * @return
     */
    Boolean sismenber(String key, String value);

    /**
     * 移除集合元素
     *
     * @param key   key
     * @param value value
     * @return
     */
    Long srem(String key, String value);

    Set<String> smembers(String key);
}
