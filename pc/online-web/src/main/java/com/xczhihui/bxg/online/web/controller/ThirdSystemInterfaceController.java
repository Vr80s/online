package com.xczhihui.bxg.online.web.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.xczhihui.common.util.HttpUtil;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.ThirdSystemService;

/**
 * 第三方系统接口提供，所有方法均返回json， 设计隐私的要校验cookie，去用户中心验票
 * 
 * @author Haicheng Jiang
 *
 */
@RestController
@RequestMapping(value = "/api")
public class ThirdSystemInterfaceController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ThirdSystemService service;
	
	@RequestMapping(value = "callThirdGet",method= RequestMethod.GET)
	public ResponseObject callThirdGet(HttpServletRequest req, HttpServletResponse res) {
		
		Map<String, String> params = new TreeMap<String, String>();
		Enumeration<String> paraNames = req.getParameterNames();
		for (Enumeration<String> e = paraNames; e.hasMoreElements();) {
			String thisName = e.nextElement().toString();
			String thisValue = req.getParameter(thisName);
			params.put(thisName, thisValue);
		}
		
		String url = params.get("thirdUrl");
		String param = "";
		for (Map.Entry<String, String> e : params.entrySet()) {
			if (e.getKey() != null && !"thirdUrl".equals(e.getKey())) {
				param += ("&"+e.getKey()+"="+e.getValue());
			}
		}
		if (param.length() > 0) {
			param = "?"+param.substring(1);
		}
		url += param;
		
		Map<String, String> cookiesmap = new HashMap<String, String>();
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			cookiesmap.put(cookie.getName(), cookie.getValue());
		}
		
		String msg = HttpUtil.sendGetRequestWithCookies(url, "utf-8", cookiesmap);
		if (msg != null && msg.contains("success")) {
			Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			ResponseObject r = g.fromJson(msg, ResponseObject.class);
			return r;
		}
		
		return ResponseObject.newSuccessResponseObject(msg);
	}
	
	@RequestMapping(value = "callThirdPost",method= RequestMethod.POST)
	public ResponseObject callThirdPost(HttpServletRequest req, HttpServletResponse res) {
		
		Map<String, String> params = new TreeMap<String, String>();
		Enumeration<String> paraNames = req.getParameterNames();
		for (Enumeration<String> e = paraNames; e.hasMoreElements();) {
			String thisName = e.nextElement().toString();
			String thisValue = req.getParameter(thisName);
			if (!"thirdUrl".equals(thisName)) {
				params.put(thisName, thisValue);
			}
		}
		
		Map<String, String> cookiesmap = new HashMap<String, String>();
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			cookiesmap.put(cookie.getName(), cookie.getValue());
		}
		
//		String msg = HttpUtil.sendPostRequestWithCookies(req.getParameter("thirdUrl"), params, cookiesmap);
		String msg = null;//20170724----yuruixin
		/*if (msg != null && msg.contains("success")) {
			Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			ResponseObject r = g.fromJson(msg, ResponseObject.class);
			return r;
		}*/
		
		return ResponseObject.newSuccessResponseObject(msg);
	}
	
	@RequestMapping(value = "sendMobileMessage",method= RequestMethod.POST)
	public ResponseObject sendMobileMessage(HttpServletRequest req,String systemName,String mobile,String content,long timestamp,String sign){
		String msg = service.sendMobileMessage(systemName,mobile,content,timestamp,sign);
		if (!"ok".equals(msg)) {
			return ResponseObject.newErrorResponseObject(msg);
		}
		return ResponseObject.newSuccessResponseObject("OK");
	}

	@RequestMapping(value = "getUserInfo")
	public ResponseObject getUserInfo(HttpServletRequest req,String systemName,String loginName,long timestamp,String sign) {
		OnlineUser user = service.getUserInfo(systemName,loginName,timestamp,sign);
		return ResponseObject.newSuccessResponseObject(user);
	}
	
}
