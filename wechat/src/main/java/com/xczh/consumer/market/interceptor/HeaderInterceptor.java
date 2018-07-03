package com.xczh.consumer.market.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.common.util.enums.ClientType;

/**
 * @author hejiwei
 */
@Component
public class HeaderInterceptor implements HandlerInterceptor {

    public static ThreadLocal<Boolean> ONLY_THREAD = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    };
    public static ThreadLocal<String> CLIENT = new ThreadLocal<String>();
    private static String IVERSION = "iversion";
    private static String CLIENT_TYPE = "clientType";

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${ios.check.version}")
    private String version;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String iversion = request.getHeader(IVERSION);
        String clientType = request.getHeader(CLIENT_TYPE);
        if (StringUtils.isBlank(iversion)) {
            iversion = request.getParameter(IVERSION);
        }
        if (version.equals(iversion)) {
            ONLY_THREAD.set(Boolean.TRUE);
        } else {
            ONLY_THREAD.set(Boolean.FALSE);
        }
        CLIENT.set(clientType);
        logger.warn("clientType:{}",clientType);

        logger.info(version + ":" + iversion + version.equals(iversion));
        logger.info("tl:" + ONLY_THREAD.get());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) throws Exception {
    }

    public static ClientType getClientType(){
        ClientType clientType = null;
        if(StringUtils.isNotBlank(HeaderInterceptor.CLIENT.get())){
            clientType = ClientType.getClientType(Integer.valueOf(HeaderInterceptor.CLIENT.get()));
        }
        return clientType;
    }

    public static Integer getClientTypeCode(){
        ClientType clientType = null;
        if(StringUtils.isNotBlank(HeaderInterceptor.CLIENT.get())){
            clientType = ClientType.getClientType(Integer.valueOf(HeaderInterceptor.CLIENT.get()));
        }
        if(clientType == null){
            return null;
        }
        return clientType.getCode();
    }

}
