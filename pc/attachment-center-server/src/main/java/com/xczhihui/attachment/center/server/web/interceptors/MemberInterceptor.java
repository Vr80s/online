package com.xczhihui.attachment.center.server.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MemberInterceptor implements HandlerInterceptor{

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//请求的路径
		String contextPath = request.getContextPath();
		String userName = request.getHeader("userName");
		String password = request.getHeader("password");
		String url = request.getServletPath().toString().toLowerCase();
		if(url.contains(".")){//允许访问附件
			return true;
		}
		if("boxuegu".equals(userName) && "BoxueGU&Pics123*".equals(password)){
			return true;
		}else{
			response.sendRedirect(contextPath+"/errors.jsp");
			return false;
		}
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
