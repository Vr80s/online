package com.xczh.consumer.market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.support.service.impl.RedisCacheService;

/**
 * @author hejiwei
 */
@Configuration
public class CacheConfig {

    @Bean
    public CacheService cacheService() {
        //TODO 当前redis配置使用默认的配置
        return new RedisCacheService();
    }
}
