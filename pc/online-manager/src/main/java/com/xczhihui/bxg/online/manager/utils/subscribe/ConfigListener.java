package com.xczhihui.bxg.online.manager.utils.subscribe;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;

public class ConfigListener implements ServletContextListener {
	
    private CourseService courseService;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {   
//    	courseService = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()).getBean(CourseService.class);
//    	courseService.initOpenCourseToSend();
    }

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
}