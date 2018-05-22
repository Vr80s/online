package com.xczhihui.course.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

/**
 * @author hejiwei
 */
@Configuration
public class WechatConfig {

    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.secret}")
    private String secret;

    @Bean
    public WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage() {
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(appId);
        wxMpInMemoryConfigStorage.setSecret(secret);
        return wxMpInMemoryConfigStorage;
    }

    @Bean
    public WxMpService wxMpService(WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage) {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpInMemoryConfigStorage);
        return wxMpService;
    }
}
