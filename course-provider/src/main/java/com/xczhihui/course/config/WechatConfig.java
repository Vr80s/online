package com.xczhihui.course.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
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
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(appId);
        wxMpInMemoryConfigStorage.setSecret(secret);
        return wxMpInMemoryConfigStorage;
    }

    @Bean
    public WxMpService wxMpService(WxMpConfigStorage wxMpConfigStorage) {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
        return wxMpService;
    }

    /**
     * 注: 其他服务如果需要使用WxMpService 中的WxMpTemplateMsgService，MaterialService, UserService等时。
     * 请以dubbo服务注册订阅方式获取，切勿在外部服务直接调用相关的get方法来获取(无法序列化).
     *
     * @param wxMpService wxMpService
     * @return
     */
    @Bean
    public WxMpTemplateMsgService wxMpTemplateMsgService(WxMpService wxMpService) {
        return wxMpService.getTemplateMsgService();
    }
}
