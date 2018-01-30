package com.xczh.consumer.market.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import com.xczh.consumer.market.bean.OnlineUser;


/**
 * 监听ServletContext对象创建和销毁
 * @author 姜涛
 *
 */
public class MyServletContextListener implements ServletContextListener{
	// ServletContext对象创建 下面这个方法就会执行
	// ServletContextEvent事件对象. 监听器对象---》ServletContext对象.(事件源)
	@Override
    public void contextInitialized(ServletContextEvent sce) {
		Map<OnlineUser,HttpSession> userMap = new HashMap<OnlineUser,HttpSession>();
		sce.getServletContext().setAttribute("userMap", userMap);
	}
	@Override
    public void contextDestroyed(ServletContextEvent sce) {
		
	}



}
