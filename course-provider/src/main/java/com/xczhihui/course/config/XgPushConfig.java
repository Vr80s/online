package com.xczhihui.course.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xczhihui.course.service.IXgPushService;
import com.xczhihui.course.service.impl.XgPushServiceImpl;

/**
 * @author hejiwei
 */
@Configuration
public class XgPushConfig {

    @Value("${push.android.accessId}")
    private Long androidAccessId;
    @Value("${push.android.secretKey}")
    private String androidSecretKey;

    @Value("${push.ios.accessId}")
    private Long iosAccessId;
    @Value("${push.ios.secretKey}")
    private String iosSecretKey;

    @Bean("iosXgPushService")
    public IXgPushService iosXgPushService() {
        XgPushServiceImpl xgPushService = new XgPushServiceImpl();
        xgPushService.setAccessId(iosAccessId);
        xgPushService.setSecretKey(iosSecretKey);
        return xgPushService;
    }

    @Bean("androidXgPushService")
    public IXgPushService androidXgPushService() {
        XgPushServiceImpl xgPushService = new XgPushServiceImpl();
        xgPushService.setSecretKey(androidSecretKey);
        xgPushService.setAccessId(androidAccessId);
        return xgPushService;
    }
}
