package com.xczhihui.bxg.online.manager.operate.web;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.manager.operate.service.Banner2Service;
import com.xczhihui.bxg.online.manager.operate.vo.Banner2Vo;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

@Controller
@RequestMapping(value = "/operate")
public class BannersController {

	/**
     * 
     * @return
     */
    @RequestMapping(value = "/banner3/index")
    public ModelAndView index3(){
         ModelAndView mav=new ModelAndView("/operate/banner3");
         return mav;
    }

	/**
     *
     * @return
     */
    @RequestMapping(value = "/banner4/index")
    public ModelAndView index4(){
         ModelAndView mav=new ModelAndView("/operate/banner4");
         return mav;
    }

	/**
     *
     * @return
     */
    @RequestMapping(value = "/banner5/index")
    public ModelAndView index5(){
         ModelAndView mav=new ModelAndView("/operate/banner5");
         return mav;
    }
}
