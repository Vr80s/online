package com.xczhihui.attachment.center.server.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
