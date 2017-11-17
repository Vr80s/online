package com.xczhihui.bxg.online.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.online.web.service.ActivityTotalService;
import com.xczhihui.user.center.web.utils.CookieUtil;

/**
 * 活动统计
 * @author Haicheng Jiang
 *
 */
@RestController
@RequestMapping(value = "/act/total")
public class ActivityTotalController {
	@Value("${online.web.url}")
	private String weburl;
	
	@Autowired
	private ActivityTotalService service;
	
	@RequestMapping(value = "/url", method = RequestMethod.GET)
	public void course(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String code = req.getParameter("code");
		if (code != null) {
			code = code.replace("--", "?").replace("@@","&");
		}
		String[] ss = code.split("&fromCode=");
		if (ss.length == 2) {
			service.addTotal(ss[1]);
			CookieUtil.setCookie(res, "act_code_from", ss[1], "ixincheng.com", "/");
			code = ss[0];
			res.sendRedirect(weburl+code);
		}
	}
}
