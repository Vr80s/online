package com.xczhihui.user.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 版本管理
 * 
 * @author yxd
 */
@Controller
@RequestMapping("version")
public class VersionManagerController {
	protected final static String VERSION_PATH_PREFIX = "/version/";

	/**
	 * 转到页面
	 * 
	 * @param request
	 * @return
	 */
	// @RequiresPermissions("version:manager")
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		return VERSION_PATH_PREFIX + "index";
	}

}
