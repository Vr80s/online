package com.xczhihui.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis分布式锁
 * @author zhuwenbao
 */
@Component
public class RedisShardLockUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 加锁
     * @param key 唯一标示
     * @param value 当前时间+超时时间
     * @return
     */
    public boolean lock(String key, String value){

        /**
         * setnx:set if not exists
         * 将key设置值为value，如果key不存在，这种情况下等同SET命令, 表示客户端获得锁，返回1
         * 当key存在时，什么也不做 返回0
         * 如果设置成功 则表示成功上锁 则返回true
         */
        if(redisTemplate.opsForValue().setIfAbsent(key, value)){
            return true;
        }

        /**
         * 防止死锁
         * 如果获取不到锁，判断该锁是否过期，过期的话重新设置过期时间并获得锁
         * 如果客户端出现故障，崩溃或者其他情况无法释放该锁
         */

        // 获取锁的过期时间
        String currentValue = (String) redisTemplate.opsForValue().get(key);

        // 如果锁过期（ 即value值小于当前时间戳 超时）
        if (StringUtils.isNotBlank(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()){

            /**
             * getset:将key对应到value并且返回原来key对应的value
             *
             * 假设A线程已经超时 currentValue = A
             * 而此时B C两个线程同时进到这里 尝试获得锁 携带的value 都是newValue
             * 因为redis是单线程 所以只有一个线程会执行下面的getset方法
             * 假设B先执行了getset方法  返回oldValue = A 此时currentValue和返回oldValue是相等的
             * 所以加锁成功 并且设置value = newValue
             * 然后C再执行getset方法 但是拿到的oldValue 是b携带的 newValue 与currentValue不相等 加锁失败
             */
            String oldValue = (String) redisTemplate.opsForValue().getAndSet(key, value);
            if(StringUtils.isNotBlank(oldValue) && StringUtils.equals(oldValue,currentValue)){
                return true;
            }
        }

        return false;
    }

    /**
     * 解锁
     */
    public void unlock(String key, String value){
        try {
            String currentValue = (String) redisTemplate.opsForValue().get(key);
            if(StringUtils.isNotBlank(currentValue) && StringUtils.equals(currentValue,value)){
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            logger.error("解锁异常" , e);
        }
    }


}
