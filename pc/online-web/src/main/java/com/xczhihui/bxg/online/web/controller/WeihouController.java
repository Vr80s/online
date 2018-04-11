package com.xczhihui.bxg.online.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author yuruixin
 * @Date 2017/7/17
 */
@RestController
@RequestMapping(value = "/weihou")
public class WeihouController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 微吼k值验证回调接口
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/pass")
	public void vPass(HttpServletRequest request, HttpServletResponse response,
			String email, String k) throws Exception {
		ServletInputStream ris = request.getInputStream();
		StringBuilder content = new StringBuilder();
		byte[] b = new byte[1024];
		int lens = -1;
		while ((lens = ris.read(b)) > 0) {
			content.append(new String(b, 0, lens));
		}
		String strcont = content.toString();// 内容
		response.setContentType("text/html;charset=UTF-8");
		if ("yrxk".equals(k)) {
			response.getWriter().write("pass");
		} else {
			response.getWriter().write("fail");
		}
	}

}
