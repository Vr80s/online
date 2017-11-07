package com.xczhihui.bxg.common.web.exception;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.google.gson.Gson;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.util.HttpUtil;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.auth.UserHolder;

/**
 * 异常处理，如果方法有注解ResponseBody，将错误信息转成json，否则跳转到错误页面。
 * 
 * @author liyong
 *
 */
public class JsonSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		log.info(ex.getMessage(), ex);
		HandlerMethod method = (HandlerMethod) handler;
		ResponseBody body = method.getMethodAnnotation(ResponseBody.class);
		RestController rest = AnnotationUtils.findAnnotation(method.getBeanType(), RestController.class);
		if (body == null && rest == null) {
			return super.doResolveException(request, response, handler, ex);
		}
		ModelAndView mv = new ModelAndView();
		//response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		try {
			ResponseObject ro = ResponseObject.newErrorResponseObject(ex.getMessage());
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(ro));
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
		}
		return mv;
	}
}
