package com.xczhihui.bxg.online.web.controller;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.user.center.web.utils.CookieUtil;

/**
 * 分销 分享
 * 
 * @author Haicheng Jiang
 *
 */
@RestController
@RequestMapping
public class ShareController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${online.web.url:http://www.ixincheng.com}")
	private String weburl;

	/**
	 * 分享跳转
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "/share", method = RequestMethod.GET)
	public void course(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String url = "/";
		String usercode = req.getParameter("usercode");
		if (usercode != null) {
			usercode = usercode.replace("--", "?").replace("@@","&");
			String decodeurl = URLDecoder.decode(usercode, "utf-8");
			String[] split = decodeurl.split("__");
			if (split.length == 2) {
				url = split[1];
				if (split[0].length() > 0) {
					CookieUtil.setCookie(res, "_usercode_", split[0], "ixincheng.com");
				}
			}
		}
		res.sendRedirect(weburl+url);
	}
}
