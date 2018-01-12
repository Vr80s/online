package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.OnlineResponse;
import com.xczhihui.bxg.online.web.service.CourseService;
import com.xczhihui.bxg.online.web.service.LecturerService;
import com.xczhihui.bxg.online.web.service.MenuService;
import com.xczhihui.bxg.online.web.vo.CourseVo;
import com.xczhihui.bxg.online.web.vo.LecturVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
public class UserCourseController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private CourseService courseService;
    @Autowired
    private LecturerService lecturerService;

    /**
     * 查询所有菜单
     * @return ResponseObject
     */
    @RequestMapping(value = "/menus",method= RequestMethod.GET)
    public ResponseObject menus(HttpServletRequest   request){
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if (loginUser == null) {
            return OnlineResponse.newErrorOnlineResponse("请登录！");
        }
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
        OnlineUser loginUser = (OnlineUser)UserLoginUtil.getLoginUser(request);
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
    	OnlineUser loginUser = (OnlineUser)UserLoginUtil.getLoginUser(request);
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
    	OnlineUser loginUser = (OnlineUser)UserLoginUtil.getLoginUser(request);
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
}
