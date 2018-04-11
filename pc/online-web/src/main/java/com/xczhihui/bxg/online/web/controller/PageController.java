package com.xczhihui.bxg.online.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.web.service.LiveService;
import com.xczhihui.bxg.online.web.service.ManagerUserService;

/**
 * 页面路由控制器.
 * @author majian
 * @date 2016-8-1 14:38:06
 */
@Controller
@RequestMapping(value = "/web")
public class PageController {

    @Autowired
    private ManagerUserService  managerUserService;
    @Autowired
    private LiveService  liveService;

    @RequestMapping(value = "/courseDetail/{courserId}",method= RequestMethod.GET)
    public ModelAndView courseDetail(@PathVariable String courserId,HttpServletRequest request,HttpServletResponse response){
    	ModelAndView mav=new ModelAndView("CourseDetail");
    	mav.addObject("courserId",courserId);
    	return mav;
    }

    @RequestMapping(value = "/storyDetail/{id}",method= RequestMethod.GET)
    public ModelAndView storyDetail(@PathVariable String id,HttpServletRequest request,HttpServletResponse response){
        ModelAndView mav=new ModelAndView("StoryDetail");
        mav.addObject("id",id);
        return mav;
    }

    @RequestMapping(value = "/qusDetail/{qid}",method= RequestMethod.GET)
    public ModelAndView qusDetail(@PathVariable String qid,HttpServletRequest request,HttpServletResponse response){
        ModelAndView mav=new ModelAndView("QusDetail");
        mav.addObject("qid",qid);
        return mav;
    }


    @RequestMapping(value = "/jumpMethod/{qid}/{type}/{loginname}",method= RequestMethod.GET)
    public void  jumpMethod(@PathVariable String qid,@PathVariable Integer type,@PathVariable String loginname,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //Token token=  UCCookieUtil.readTokenCookie(request);
        //在manager端和web端再查看一下当前用户是否存在
        User user=managerUserService.findUserByLoginName(loginname);
        request.getSession().setAttribute("_adminUser_",user);
        //如果浏览器中获取有用户登录，接着在库里面查询此用户是管理员还是普通用户
        if(user !=null){  //是管理员
              if(type==1) //博问答管理员
              {
                  response.sendRedirect("/web/html/qusAndAnsDetailGuanLi.html?qid=" + qid);
              } else {
                  response.sendRedirect("/web/html/qusAndAnsDetailTouSu.html?qid=" + qid);
              }
        }else{
           throw new RuntimeException("您不是管理员！");
            // response.sendRedirect("/web/html/404.html");
        }


    }
    /**
     * 跳转到直播间
     * @param courseId
     * @param roomId
     * @param planId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/livepage/{courseId}/{roomId}/{planId}",method= RequestMethod.GET)
    public ModelAndView livepage(@PathVariable String courseId,@PathVariable String roomId,
    		@PathVariable String planId,HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = liveService.livepage(courseId, request,response);
        return mv;
    }

    /**
     * 跳转到直播间
     * @param courseId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/livepage/{courseId}",method= RequestMethod.GET)
    public ModelAndView livepage(@PathVariable String courseId,HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = liveService.livepage(courseId, request,response);
        return mv;
    }

}
