package com.xczhihui.bxg.online.web.controller;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.common.util.HttpUtil;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.vo.GiftInfo;

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
		System.out.println(strcont);
		response.setContentType("text/html;charset=UTF-8");
		if ("yrxk".equals(k)) {
			System.out.println("pass");
			response.getWriter().write("pass");
		} else {
			System.out.println("fail");
			response.getWriter().write("fail");
		}
	}

	public static void main(String[] args) {
		String timestr = new Date().getTime()+"";
		System.out.println(timestr);
	}
}
