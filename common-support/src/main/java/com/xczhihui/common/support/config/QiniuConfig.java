package com.xczhihui.common.support.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * @author hejiwei
 */
@Configuration
public class QiniuConfig {

    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Bean
    public Auth auth() {
        return Auth.create(accessKey, secretKey);
    }

    @Bean
    public UploadManager qiniuUploadManager() {
        com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Zone.zone2());
        return new UploadManager(cfg);
    }
}
