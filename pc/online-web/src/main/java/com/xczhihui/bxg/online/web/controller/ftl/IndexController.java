package com.xczhihui.bxg.online.web.controller.ftl;

import com.xczhihui.bxg.online.web.service.LiveService;
import com.xczhihui.bxg.online.web.service.ManagerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面路由控制器.
 * @author majian
 * @date 2016-8-1 14:38:06
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @Autowired
    private ManagerUserService  managerUserService;
    @Autowired
    private LiveService  liveService;

    @RequestMapping(value="testftl",method=RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("test-ftl");
        view.addObject("message", "Say hi for Freemarker.");
        return view;
    }
}
