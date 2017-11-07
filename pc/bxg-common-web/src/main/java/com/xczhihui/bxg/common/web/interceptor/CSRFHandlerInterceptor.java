package com.xczhihui.bxg.common.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.xczhihui.bxg.common.web.annotation.CheckCSRF;
import com.xczhihui.bxg.common.web.tool.CSRFTokenTool;
import com.xczhihui.bxg.common.web.util.HttpRequestUtil;

/**
 * 拦截请求的方法上有CheckCSRF注解的请求，检查CSRF。
 * 
 * @author liyong
 *
 */
public class CSRFHandlerInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CSRFTokenTool csrfTokenTool;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (handler instanceof DefaultServletHttpRequestHandler) {
			return true;
		}
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;

			CheckCSRF c = hm.getMethodAnnotation(CheckCSRF.class);
			if (c != null) {
				boolean b = csrfTokenTool.checkCSRFToken(request, 123456);
				if (b) {
					return true;
				} else {
					String msg = String.format("Referer:%s client IP:%s", request.getHeader("Referer"),
							HttpRequestUtil.getClientIP(request));
					logger.warn("Bad or missing CSRF value:{}", msg);
					response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bad or missing CSRF value");
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}
}
