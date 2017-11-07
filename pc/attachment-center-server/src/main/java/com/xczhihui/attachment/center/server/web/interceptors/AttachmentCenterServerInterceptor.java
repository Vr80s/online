package com.xczhihui.attachment.center.server.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.GsonBuilder;

import com.xczhihui.bxg.common.support.domain.Attachment;

/**
 * 附件中心拦截器
 * 
 * @author Haicheng Jiang
 */
public class AttachmentCenterServerInterceptor implements HandlerInterceptor {

	@Value("${white.ip.list.on:off}")
	private String whiteIpListOn = "off";
	
	@Value("${white.ip.list:127.0.0.1}")
	private String whiteIpList = "127.0.0.1";
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
	        Exception exception) throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
	        ModelAndView modelAndView) throws Exception {

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		
		//上传判断白名单
		if ("on".equals(whiteIpListOn) && "/attachment/upload".equals(request.getRequestURI()) 
				&& !whiteIpList.contains(request.getRemoteAddr())) {
			
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");
			response.getWriter().write(new GsonBuilder().create().toJson(new Attachment(1,"无权限！")));
			
			System.out.println("无权限ip:"+request.getRemoteAddr());
			
			return false;
		}
		
		return true;
	}

	public String getWhiteIpList() {
		return whiteIpList;
	}

	public void setWhiteIpList(String whiteIpList) {
		this.whiteIpList = whiteIpList;
	}

	public String getWhiteIpListOn() {
		return whiteIpListOn;
	}

	public void setWhiteIpListOn(String whiteIpListOn) {
		this.whiteIpListOn = whiteIpListOn;
	}

}
