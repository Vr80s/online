package com.xczhihui.common.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.service.PermResourceService;
import com.xczhihui.user.service.ResourceTreeHelper;
import com.xczhihui.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.online.common.domain.Resource;
import com.xczhihui.bxg.online.common.domain.User;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private PermResourceService resourceService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        String type = "menu";//菜单
        //TODO cache menus
        List<Resource> resources = this.resourceService.findPermitResources(ManagerUserUtil.getId(), type);
        resources = ResourceTreeHelper.genHierarchyTree(resources);
        request.setAttribute("menus", resources);
        request.getSession().setAttribute("_user_", ManagerUserUtil.getPrincipal());
        return "home";
    }

    @RequestMapping(path = "/welcome")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public boolean check() {
        User user = userService.getUserById(ManagerUserUtil.getId());
        if (user == null || user.isDelete()) {
            SecurityUtils.getSubject().logout();
            return false;
        }
        return true;
    }
}
