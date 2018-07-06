package com.xczhihui.bxg.online.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.bxg.online.web.service.LiveService;
import com.xczhihui.bxg.online.web.service.ManagerUserService;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IWatchHistoryService;
import com.xczhihui.course.vo.CourseLecturVo;

/**
 * 页面路由控制器.
 *
 * @author majian
 * @date 2016-8-1 14:38:06
 */
@Controller
@RequestMapping
public class PageController {

    @Autowired
    public IWatchHistoryService watchHistoryServiceImpl;
    @Autowired
    private ManagerUserService managerUserService;
    @Autowired
    private LiveService liveService;
    @Autowired
    private ICourseService courseService;
    @Value("${env.flag}")
    private String env;
    @Value("${rate}")
    private int rate;
    @Value("${gift.im.room.postfix}")
    private String postfix;
    @Value("${gift.im.boshService}")
    private String boshService;//im服务地址
    @Value("${gift.im.host}")
    private String host;

    @RequestMapping(value = "/web/courseDetail/{courserId}", method = RequestMethod.GET)
    public ModelAndView courseDetail(@PathVariable String courserId, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("CourseDetail");
        mav.addObject("courserId", courserId);
        return mav;
    }

    @RequestMapping(value = "/web/storyDetail/{id}", method = RequestMethod.GET)
    public ModelAndView storyDetail(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("StoryDetail");
        mav.addObject("id", id);
        return mav;
    }

    @RequestMapping(value = "/web/jumpMethod/{qid}/{type}/{loginname}", method = RequestMethod.GET)
    public void jumpMethod(@PathVariable String qid, @PathVariable Integer type, @PathVariable String loginname, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = managerUserService.findUserByLoginName(loginname);
        //如果浏览器中获取有用户登录，接着在库里面查询此用户是管理员还是普通用户
        if (user != null) {
            //管理
            if (type == 1) {
                response.sendRedirect("/web/html/qusAndAnsDetailGuanLi.html?qid=" + qid + "&ln=" + loginname);
            } else {//投诉
                response.sendRedirect("/web/html/qusAndAnsDetailTouSu.html?qid=" + qid + "&ln=" + loginname);
            }
        } else {
            throw new RuntimeException("您不是管理员！");
        }
    }

    /**
     * 跳转到直播间
     *
     * @param courseId
     * @param roomId
     * @param planId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/web/livepage/{courseId}/{roomId}/{planId}", method = RequestMethod.GET)
    public ModelAndView livepage(@PathVariable String courseId, @PathVariable String roomId,
                                 @PathVariable String planId, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = liveService.livepage(courseId, request, response);
        return mv;
    }

    /**
     * 跳转到直播间
     *
     * @param courseId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/web/livepage/{courseId}", method = RequestMethod.GET)
    public ModelAndView livepage(@PathVariable String courseId, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = liveService.livepage(courseId, request, response);
        return mv;
    }


    /**
     * 判断是否跳到直播间
     *
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/web/liveCoursePage/{courseId}", method = RequestMethod.GET)
    public ModelAndView liveCoursePage(@PathVariable Integer courseId) {

        ModelAndView mv = null;
        BxgUser user = UserLoginUtil.getLoginUser();
        CourseLecturVo clv = null;
        if (user != null) {

            clv = courseService.selectUserCurrentCourseStatus(courseId, user.getId());

            //只有直播中的直接跳转  --》  直播间
            if ((clv.getWatchState() == 1 || clv.getWatchState() == 2)
                    && clv.getType() == 3 && clv.getLineState() == 1) {
                
                
                //因为跳到直播间了，增加学习记录
                if (clv.getWatchState() == 1) {
                    watchHistoryServiceImpl.addLearnRecord(courseId, user.getId());
                }
                
                
                mv = new ModelAndView("live_success_page");

                mv.addObject("lecturerId", clv.getUserLecturerId());
                mv.addObject("vhallName", clv.getVhallName());
                mv.addObject("userId", user.getId());
                mv.addObject("courseId", courseId);
                mv.addObject("liveStatus", clv.getLineState());
                mv.addObject("roomId", clv.getDirectId());
                mv.addObject("roomJId", courseId + postfix);
                mv.addObject("boshService", boshService);
                mv.addObject("now", System.currentTimeMillis());
                mv.addObject("description", clv);
                mv.addObject("email", user == null ? null : user.getId() + "@xczh.com");
                mv.addObject("name", user == null ? null : user.getName());
                mv.addObject("k", "yrxk");//TODO 此处暂时写死
                mv.addObject("guId", user.getId());
                mv.addObject("guPwd", user.getPassword());
                mv.addObject("env", env);
                mv.addObject("host", host);
                mv.addObject("rate", rate);


            } else {
                mv = new ModelAndView("redirect:/courses/" + courseId + "/info");
            }
        } else {
            //转发到展示页面
            mv = new ModelAndView("redirect:/courses/" + courseId + "/info");
        }

        return mv;
    }

    /**
     * 跳转至个人中心
     *
     * @return
     */
    @RequestMapping(value = "my", method = RequestMethod.GET)
    public void my(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/web/html/personal-center/personal-index.html").forward(request, response);
    }

    /**
     * 跳转至主播工作台
     *
     * @return
     */
    @RequestMapping(value = "anchor/my", method = RequestMethod.GET)
    public void anchor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/web/html/anchor/curriculum.html").forward(request, response);
    }

    /**
     * 跳转至医师工作台
     *
     * @return
     */
    @RequestMapping(value = "doctors/my", method = RequestMethod.GET)
    public void doctor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/web/html/anchors_resources.html").forward(request, response);
    }

    /**
     * 跳转至医馆工作台
     *
     * @return
     */
    @RequestMapping(value = "clinics/my", method = RequestMethod.GET)
    public void clinic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/web/html/ResidentHospital.html").forward(request, response);
    }

    /**
     * 跳转至App.html
     *
     * @return
     */
    @RequestMapping(value = "app", method = RequestMethod.GET)
    public void app(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/App.html").forward(request, response);
    }

    /**
     * 跳转至App.html
     *
     * @return
     */
    @RequestMapping(value = "/anchor/guide", method = RequestMethod.GET)
    public void guide(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/web/html/want-anchor.html").forward(request, response);
    }


}
