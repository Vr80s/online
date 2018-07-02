package com.xczhihui.bxg.online.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.aliyuncs.exceptions.ClientException;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.CourseService;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.SearchType;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IMobileHotSearchService;
import com.xczhihui.course.vo.CourseLecturVo;

/**
 * 课程控制层实现类
 *
 * @author Rongcai Kang
 */
@RestController
@RequestMapping(value = "/course")
public class CourseController extends AbstractController {

    @Autowired
    private CourseService service;

    @Autowired
    private ICourseService courseServiceImpl;

    @Autowired
    private IMobileHotSearchService mobileHotSearchService;


    @RequestMapping(value = "/scoreList")
    public Object listAllScoreType() {
        return service.findAllScoreType();
    }


    /**
     * 中医课堂列表，需要分页
     *
     * @return
     */
    @RequestMapping(value = "/courseList")
    public ResponseObject courseList(Integer menuType, Integer menuId, String couseTypeId, String multimediaType, String isFree, String orderType, String orderBy, Integer pageNumber, Integer pageSize) {
        return ResponseObject.newSuccessResponseObject(service.courseList(menuType, menuId, couseTypeId, multimediaType, isFree, orderType, orderBy, pageNumber, pageSize));
    }

    /**
     * 查询课程信息，根据学科与课程类别筛选，需要分页
     *
     * @return
     */
    @RequestMapping(value = "/getPageCourseByMenuId")
    public ResponseObject getCourseByMenuNumber(Integer type, Integer menuId, String couseTypeId, Integer pageNumber, Integer pageSize) {
        return ResponseObject.newSuccessResponseObject(service.getCourseAndLecturerlist(type, menuId, couseTypeId, pageNumber, pageSize));
    }

    /**
     * 根据课程ID号，查找对应的课程对象
     *
     * @param courserId 课程id
     * @return Example 分页列表
     */
    @RequestMapping(value = "/getCourseById")
    public ResponseObject getCourseById(Integer courserId, HttpServletRequest request) throws IOException {
        OnlineUser loginUser = getCurrentUser();
        String path = request.getServletContext().getRealPath("/template");
        return ResponseObject.newSuccessResponseObject(service.getCourseById(courserId, path, request, loginUser));
    }

    /**
     * 根据合辑课程ID号，查找合辑下的所有课程
     *
     * @param collectionId 课程id
     * @return Example 分页列表
     */
    @RequestMapping(value = "/getCoursesByCollectionId")
    public ResponseObject getCoursesByCollectionId(Integer collectionId) {
        return ResponseObject.newSuccessResponseObject(service.getCoursesByCollectionId(collectionId));
    }

    /**
     * 根据课程ID号，查找对应的课程对象
     *
     * @param courserId 课程id
     * @return Example 分页列表
     */
    @RequestMapping(value = "/getOpenCourseById")
    public ResponseObject getOpenCourseById(Integer courserId, String ispreview, HttpServletRequest request) {
        return ResponseObject.newSuccessResponseObject(service.getOpenCourseById(courserId, ispreview, request));
    }

    /**
     * 根据课程ID号，查找对应的课程对象
     *
     * @param courseId 课程id
     * @return Example 分页列表
     */
    @RequestMapping(value = "/getCourseApplyByCourseId")
    public ResponseObject getCourseApplyByCourseId(Integer courseId) {
        return ResponseObject.newSuccessResponseObject(service.getCourseApplyByCourseId(courseId));
    }

    /**
     * 查找推荐课程信息
     *
     * @param courseId 课程id
     * @return
     */
    @RequestMapping(value = "/getRecommendCourseByCourseId")
    public ResponseObject getRecommendCourseByCourseId(Integer courseId) {
        return ResponseObject.newSuccessResponseObject(service.getCourseByCourseId(courseId, 5));
    }


    /**
     * 首页推荐课程信息
     *
     * @return
     */
    @RequestMapping(value = "/getRecommendCourse")
    public ResponseObject getRecommendCourse(Integer num) {
        return ResponseObject.newSuccessResponseObject(service.getRecommendCourse(4));
    }

    /**
     * 博文答详情页面的推荐课程
     *
     * @param menuId     学科ID号
     * @param showAnswer 控制是否显示推荐课程 true: 不查询推荐课程  false:显示推荐课程
     * @return 推荐课程集合 courseLecturVos
     */
    @RequestMapping(value = "/getRecommendedCourse")
    public ResponseObject getRecommendedCourse(Integer menuId, boolean showAnswer) {
        return ResponseObject.newSuccessResponseObject(showAnswer ? null : service.getRecommendedCourse(menuId));

    }

    /**
     * 根据课程ID查看课程介绍
     *
     * @param isPreview
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/getCourseDescriptions")
    public ResponseObject getCourseDescriptions(@RequestParam(required = false, defaultValue = "0") Boolean isPreview, String courseId) {
        return ResponseObject.newSuccessResponseObject(service.getCourseDescriptionsByCourseId(isPreview, courseId));
    }

    /**
     * 根据课程ID查看课程介绍
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getCourseDescriptionById")
    public ResponseObject getCourseDescriptionById(String id, String courseId) {
        return ResponseObject.newSuccessResponseObject(service.getCourseDescriptionById(id, courseId));
    }

    /**
     * 课程订单 根据课程id产生订单
     *
     * @param courseId 课程id号
     * @return 返回对应的课程对象
     */
    @RequestMapping(value = "/findCourseOrderById")
    public ResponseObject findCourseOrderById(Integer courseId, HttpServletRequest request) {
        return ResponseObject.newSuccessResponseObject(service.findCourseOrderById(courseId));
    }


    /**
     * 购买课程时，进行检测此订单关联的课程是否下架以及是否购买
     */
    @RequestMapping(value = "/checkCouseInfo")
    public ResponseObject checkCouseInfo(String orderId) {
        return ResponseObject.newSuccessResponseObject(service.checkCouseInfo(orderId));

    }


    /**
     * 预约
     *
     * @param courseId
     * @return
     * @throws ClientException
     */
    @RequestMapping(value = "/subscribe")
    public ResponseObject subscribe(String mobile, Integer courseId, HttpSession session) throws ClientException {
        OnlineUser u = getCurrentUser();
        if (u == null) {
            return ResponseObject.newErrorResponseObject("用户未登录");
        }
        return service.insertSubscribe(u.getId(), mobile, courseId);
    }

    @RequestMapping(value = "/courses")
    public ModelAndView courses(Integer courseId) throws ClientException {
        ModelAndView m = new ModelAndView("redirect:/courses/" + courseId + "/info");
        return m;
    }


    @RequestMapping(value = "/courses/{courseId}")
    public ModelAndView coursesJump(@PathVariable Integer courseId) throws ClientException {
        ModelAndView m = new ModelAndView("redirect:/courses/" + courseId + "/info");
        return m;
    }

    @RequestMapping(value = "/courses/recommend/{type}")
    public ResponseObject coursesRecommend(@PathVariable Integer type) throws ClientException {
        return ResponseObject.newSuccessResponseObject(service.getCoursesRecommendByType(type));
    }

    /**
     * Description:课程详情（展示页面）页面
     *
     * @return ResponseObject
     * @author wangyishuai
     **/
    @RequestMapping(value = "/courseDetails")
    @ResponseBody
    public ResponseObject courseDetails(HttpServletRequest request, @RequestParam("courseId") Integer courseId) {
        try {
            CourseLecturVo cv = courseServiceImpl.selectCourseMiddleDetailsById(courseId);
            if (cv == null) {
                return ResponseObject.newErrorResponseObject("课程信息有误");
            }
            return ResponseObject.newSuccessResponseObject(cv);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("获取课程信息有误");
        }
    }

    /**
     * Description:通过合辑id获取合辑中的课程
     *
     * @return ResponseObject
     * @author wangyishuai
     **/
    @RequestMapping(value = "/newGetCoursesByCollectionId")
    @ResponseBody
    public ResponseObject newGetCoursesByCollectionId(HttpServletRequest request, @RequestParam("collectionId") Integer collectionId) {
        try {
            List<CourseLecturVo> courses = courseServiceImpl.selectCoursesByCollectionId(collectionId);
            return ResponseObject.newSuccessResponseObject(courses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("获取合辑列表有误");
        }
    }

    /**
     * @return
     */
    @RequestMapping(value = "/hotSearch")
    @ResponseBody
    public ResponseObject hotSearch() {
        return ResponseObject.newSuccessResponseObject(mobileHotSearchService.HotSearchList(SearchType.DEFAULT_SEARCH.getCode()));
    }
}
