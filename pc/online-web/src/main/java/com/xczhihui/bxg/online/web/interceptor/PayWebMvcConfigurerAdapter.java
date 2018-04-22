package com.xczhihui.bxg.online.web.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class PayWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
//    	registry.addInterceptor(new CharacterEncodInterceptor()).addPathPatterns("/unionpay/**");
        registry.addInterceptor(new AliPayInterceptor()).addPathPatterns("/web/alipay/**");
//        registry.addInterceptor(new WxPayInterceptor()).addPathPatterns("/wxpay/**","/wxsubpay/**");
        super.addInterceptors(registry);
    }
}

