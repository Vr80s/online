package com.xczhihui.bxg.common.web.tool;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.xczhihui.bxg.common.web.annotation.PageTool;

/**
 * 将带有PageTool注解的bean类，放入页面可以访问的context中。
 * 
 * @author liyong
 *
 */
public class PageToolsBeanProcessor implements BeanPostProcessor, ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LoggerFactory.getLogger(PageToolsBeanProcessor.class);

	private Map<String, Object> toolBeans = new HashMap<String, Object>();

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		PageTool pt = bean.getClass().getAnnotation(PageTool.class);
		if (pt != null) {
			toolBeans.put(beanName, bean);
			logger.info("Found tool bean name:'{}' class:'{}'", beanName, bean.getClass());
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		UrlBasedViewResolver urlBasedViewResolver = event.getApplicationContext().getBean(UrlBasedViewResolver.class);
		logger.warn("Found {} PageTool:{}", toolBeans.size(), toolBeans);
		urlBasedViewResolver.setAttributesMap(toolBeans);
	}

}
