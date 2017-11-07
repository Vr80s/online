package com.xczhihui.bxg.online.manager.common.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.Resource;
import com.xczhihui.bxg.online.manager.user.service.PermResourceService;
import com.xczhihui.bxg.online.manager.user.service.ResourceTreeHelper;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private PermResourceService resourceService;
	
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		String type = "menu";//菜单，system_variate表中定义
		String userId = UserHolder.getCurrentUser().getId();
		List<Resource> resources = this.resourceService.findPermitResources(userId , type);
		resources = ResourceTreeHelper.genHierarchyTree(resources);
		request.setAttribute("menus", resources);
		return "home";
	}
	
	@RequestMapping(path = "/welcome")
	public String welcome()
	{
		return "welcome";
	}
	
	@RequestMapping(value = "logined")
	@ResponseBody
	public String logined(HttpServletRequest request) throws ServletRequestBindingException {
		BxgUser user = UserLoginUtil.getLoginUser(request);
		if (user != null) {
			return "1";
		} else {
			return "0";
		}
	}
	

}
