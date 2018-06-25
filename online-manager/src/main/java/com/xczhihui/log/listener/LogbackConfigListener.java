package com.xczhihui.log.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * logback初始化监听器。
 *
 * @author liyong
 */
public class LogbackConfigListener implements ServletContextListener {

    static {
        //设置dubbo使用slf4j来记录日志
        System.setProperty("dubbo.application.logger", "slf4j");
        System.setProperty("org.jboss.logging.provider", "slf4j");
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        LogbackWebConfigurer.initLogging(event.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        LogbackWebConfigurer.shutdownLogging(event.getServletContext());
    }
}
