package com.xczhihui.bxg.online.web.interceptor;

import com.google.gson.Gson;
import com.xczhihui.common.exception.IpandaTcmException;
import com.xczhihui.common.util.EmailUtil;
import com.xczhihui.common.util.bean.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常处理，并记录异常日志
 * @author Haicheng Jiang
 */
public class ExceptionResolver extends SimpleMappingExceptionResolver {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,Exception ex) {
		HandlerMethod method = (HandlerMethod) handler;
		//异常通知告警
		if((ex instanceof IpandaTcmException)&&((IpandaTcmException) ex).isAlarm()){
			try {
				String subject = "业务异常";
				System.out.println(ex.getMessage());
				System.out.println(printStackTraceToString(ex));
				EmailUtil.sendExceptionMailBySSL("pc端",subject,printStackTraceToString(ex));
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		if (method != null && method.getBeanType() != null && method.getMethod() != null) {
			log.error(ex.getMessage()+";"+method.getBeanType().toString()+";"+method.getMethod().getName(), ex);
		} else {
			log.error(ex.getMessage(), ex);
		}
		ResponseBody body = method.getMethodAnnotation(ResponseBody.class);
		RestController rest = AnnotationUtils.findAnnotation(method.getBeanType(), RestController.class);
		if (body == null && rest == null) {
			return super.doResolveException(request, response, handler, ex);
		}
		ModelAndView mv = new ModelAndView();
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

	public static String printStackTraceToString(Throwable t) {
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw, true));
		return sw.getBuffer().toString();
	}
}
