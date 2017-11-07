package com.xczhihui.bxg.common.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.xczhihui.bxg.common.web.listener.LogbackWebConfigurer;

/**
 * logback初始化监听器。
 * 
 * @author liyong
 *
 */
public class LogbackConfigListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		LogbackWebConfigurer.initLogging(event.getServletContext());
	}

	public void contextDestroyed(ServletContextEvent event) {
		LogbackWebConfigurer.shutdownLogging(event.getServletContext());
	}
}
