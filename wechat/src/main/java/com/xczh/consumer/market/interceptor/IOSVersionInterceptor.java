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

/**
 * @author hejiwei
 */
@Component
public class IOSVersionInterceptor implements HandlerInterceptor {

    public static ThreadLocal<Boolean> onlyThread = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    };
    private static String IVERSION = "iversion";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${ios.check.version}")
    private String version;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String iversion = request.getHeader(IVERSION);
        if (StringUtils.isBlank(iversion)) {
            iversion = request.getParameter(IVERSION);
        }
        if (version.equals(iversion)) {
            onlyThread.set(Boolean.TRUE);
        } else {
            onlyThread.set(Boolean.FALSE);
        }
        logger.info(version + ":" + iversion + version.equals(iversion));
        logger.info("tl:" + onlyThread.get());
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

}
