package com.xczhihui.bxg.common.web.annotation;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.support.service.OperationLogService;
/**
 * 记录日志注解的应用
 * @author Haicheng.J
 */
@Aspect
@Component
public class OperationLogApp {
	
	ThreadLocal<Long> time = new ThreadLocal<Long>();
	
	@Autowired
	private OperationLogService service;
	
	@Autowired
	private HttpServletRequest request;
	
	@Before("@annotation(com.xczhihui.bxg.common.web.annotation.OperationLogger)")
	public void beforeExec(JoinPoint joinPoint){
		time.set(System.currentTimeMillis());
	}
	
	@After("@annotation(com.xczhihui.bxg.common.web.annotation.OperationLogger)")
	public void afterExec(JoinPoint joinPoint) {
		String loginName = "";
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		OperationLogger o = method.getAnnotation(OperationLogger.class);
		
		Object obj = SecurityUtils.getSubject().getPrincipal();
		if (obj != null) {
			BxgUser user = (BxgUser)obj;
			if (user.getLoginName() != null) {
				loginName = user.getLoginName();
			}
		}
		
		String ids = "";
		if (StringUtils.hasText(o.idparameterName())) {
			String[] pvs = request.getParameterValues(o.idparameterName());
			if (pvs != null && pvs.length > 0) {
				for(String id : pvs){
					ids += (","+id);
				}
			}
		}
		if (ids.length() > 1) {
			ids = ids.substring(1);
		}
		
		service.add(loginName, joinPoint.getTarget().getClass().getSimpleName(), method.getName(), o.description(), System.currentTimeMillis() - time.get(),o.systemType(),o.operationType(),ids,o.tableName());
	}
	
}
