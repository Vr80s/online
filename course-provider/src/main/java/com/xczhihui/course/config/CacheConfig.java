package com.xczhihui.course.config;

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
        return new RedisCacheService();
    }
}
