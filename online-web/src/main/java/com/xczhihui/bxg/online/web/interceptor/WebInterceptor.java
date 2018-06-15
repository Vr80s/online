package com.xczhihui.bxg.online.web.interceptor;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * web请求拦截器
 *
 * @author hejiwei
 */
public class WebInterceptor implements HandlerInterceptor {

    private static final String BASE_PATH_KEY = "webUrl";
    private static final String WEB_URL_PREFIX_TEMPLATE = "{0}://{1}{2}{3}";
    private static final String DEV_LOCAL = "dev-local";

    @Value("${env.flag}")
    private String envFlag;
    @Value(("${web.url}"))
    private String appWebUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && modelAndView.getViewName().indexOf("redirect") == -1) {
            String webUrl = appWebUrl;
            if (DEV_LOCAL.equals(envFlag)) {
                String contextPath = StringUtils.isNotBlank(request.getContextPath()) ? request.getContextPath() : "";
                String port = request.getServerPort() != 80 && request.getServerPort() != 0 ? ":" + request.getServerPort() : "";
                webUrl = MessageFormat.format(WEB_URL_PREFIX_TEMPLATE, request.getScheme(), request.getServerName(), port, contextPath);
            }
            modelAndView.addObject(BASE_PATH_KEY, webUrl);
            modelAndView.addObject("envFlag", envFlag);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
