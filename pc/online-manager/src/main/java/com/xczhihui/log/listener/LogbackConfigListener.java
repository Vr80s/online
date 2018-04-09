package com.xczhihui.bxg.common.support.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.xczhihui.bxg.common.support.listener.LogbackWebConfigurer;

/**
 * logback初始化监听器。
 * 
 * @author liyong
 *
 */
public class LogbackConfigListener implements ServletContextListener {

	@Override
    public void contextInitialized(ServletContextEvent event) {
		LogbackWebConfigurer.initLogging(event.getServletContext());
	}

	@Override
    public void contextDestroyed(ServletContextEvent event) {
		LogbackWebConfigurer.shutdownLogging(event.getServletContext());
	}
}
