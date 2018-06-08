package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.OnlineResponse;
import com.xczhihui.bxg.online.web.service.CourseService;
import com.xczhihui.bxg.online.web.service.MenuService;
import com.xczhihui.bxg.online.web.vo.CourseVo;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.MyCourseType;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.vo.CourseLecturVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户课程控制器
 * @author majian
 * @date 2016-8-29 15:47:29
 */
@RestController
@RequestMapping(value = "/userCourse")
public class UserCourseController extends AbstractController{

    @Autowired
    private MenuService menuService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ICourseService courseServiceImpl;

    /**
     * 查询所有菜单
     * @return ResponseObject
     */
    @RequestMapping(value = "/menus",method= RequestMethod.GET)
    public ResponseObject menus(HttpServletRequest   request){
        OnlineUser loginUser = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(menuService.findUserCourseMenus(loginUser.getId()));
    }

    /**
     * 查询当前用户所有点播课程
     * @param courseStatus 课程状态:1:免费  0付费
     * @param pageNumber
     * @param pageSize
     * 返回数据格式：{"items":[{"id":"1","title":"java是一门什么样的语言","context":"讲师简介讲师简介","top":"1"}],"totalCount":1,"totalPageCount":1,"pageSize":5,"currentPage":1}
     * @return
     */
    @RequestMapping(value="courses/{courseStatus}/{pageNumber}/{pageSize}",method=RequestMethod.GET)
    @ResponseBody
    public ResponseObject courses(HttpServletRequest request,@PathVariable Integer courseStatus,@PathVariable Integer pageNumber, @PathVariable Integer pageSize){
        OnlineUser loginUser = getCurrentUser();
        if (loginUser == null) {
            return OnlineResponse.newErrorOnlineResponse("请登录！");
        }
        if(pageNumber==null) {
            pageNumber = 1;
        }
        if(pageSize==null) {
            pageSize = 15;
        }
        Page<CourseVo> courseVoPage=courseService.findUserCoursePage(loginUser.getId(),courseStatus, pageNumber, pageSize);
        return ResponseObject.newSuccessResponseObject(courseVoPage);
    }
    
    /**
     * 查询当前用户所有直播课程
     * @param courseStatus 课程状态:1:免费  0付费
     * @param pageNumber
     * @param pageSize
     * 返回数据格式：{"items":[{"id":"1","title":"java是一门什么样的语言","context":"讲师简介讲师简介","top":"1"}],"totalCount":1,"totalPageCount":1,"pageSize":5,"currentPage":1}
     * @return
     */
    @RequestMapping(value="publicCourses/{courseStatus}/{pageNumber}/{pageSize}",method=RequestMethod.GET)
    @ResponseBody
    public ResponseObject publicCourses(HttpServletRequest request,@PathVariable Integer courseStatus,@PathVariable Integer pageNumber, @PathVariable Integer pageSize){
    	OnlineUser loginUser = getCurrentUser();
    	if (loginUser == null) {
    		return OnlineResponse.newErrorOnlineResponse("请登录！");
    	}
    	if(pageNumber==null) {
            pageNumber = 1;
        }
    	if(pageSize==null) {
            pageSize = 15;
        }
    	Page<CourseVo> courseVoPage=courseService.findUserPublicCoursePage(loginUser.getId(),courseStatus, pageNumber, pageSize);
    	return ResponseObject.newSuccessResponseObject(courseVoPage);
    }
    
    /**
     * 查询当前用户所有线下课程
     * @param courseStatus 课程状态:1:免费  0付费
     * @param pageNumber
     * @param pageSize
     * 返回数据格式：{"items":[{"id":"1","title":"java是一门什么样的语言","context":"讲师简介讲师简介","top":"1"}],"totalCount":1,"totalPageCount":1,"pageSize":5,"currentPage":1}
     * @return
     */
    @RequestMapping(value="realCourses/{courseStatus}/{pageNumber}/{pageSize}",method=RequestMethod.GET)
    @ResponseBody
    public ResponseObject realCourses(HttpServletRequest request,@PathVariable Integer courseStatus,@PathVariable Integer pageNumber, @PathVariable Integer pageSize){
    	OnlineUser loginUser = getCurrentUser();
    	if (loginUser == null) {
    		return OnlineResponse.newErrorOnlineResponse("请登录！");
    	}
    	if(pageNumber==null) {
            pageNumber = 1;
        }
    	if(pageSize==null) {
            pageSize = 15;
        }
    	Page<CourseVo> courseVoPage=courseService.findUserRealCoursePage(loginUser.getId(),courseStatus, pageNumber, pageSize);
    	return ResponseObject.newSuccessResponseObject(courseVoPage);
    }

    /**
     * Description:我的课程和已结束课程
     * @return ResponseObject
     * @author wangyishuai
     **/
    @RequestMapping(value = "/myCourseType")
    @ResponseBody
    public ResponseObject getHostInfoById(HttpServletRequest request,Integer pageNumber, Integer pageSize,Integer type) {
        try {
            OnlineUser u =  getCurrentUser();
            String myCourseType = MyCourseType.getTypeText(type);
            if(myCourseType == null) {
                return ResponseObject.newErrorResponseObject("我的课程类型有误："+MyCourseType.getAllToString());
            }
            com.baomidou.mybatisplus.plugins.Page<CourseLecturVo> page = new com.baomidou.mybatisplus.plugins.Page<CourseLecturVo>();
            page.setCurrent(pageNumber);
            page.setSize(pageSize);
            com.baomidou.mybatisplus.plugins.Page<CourseLecturVo> list = courseServiceImpl.myCourseType(page, u.getId(),type);
            return ResponseObject.newSuccessResponseObject(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("获取数据失败");
        }
    }

    /**
     * Description:我已购买课程的不包含免费的
     * @return ResponseObject
     * @author wangyishuai
     **/
    @RequestMapping(value = "/purchasedCourseList")
    @ResponseBody
    public ResponseObject freeCourseList(HttpServletRequest request,Integer pageNumber, Integer pageSize) {
        try {
            com.baomidou.mybatisplus.plugins.Page<CourseLecturVo> page = new com.baomidou.mybatisplus.plugins.Page<>();
            page.setCurrent(pageNumber);
            page.setSize(pageSize);
            OnlineUser u =  getCurrentUser();
            com.baomidou.mybatisplus.plugins.Page<CourseLecturVo> list=courseServiceImpl.selectMyPurchasedCourseList(page, u.getId());
            return ResponseObject.newSuccessResponseObject(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("获取数据失败");
        }
    }
}
