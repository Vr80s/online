package com.xczhihui.bxg.online.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.bxg.online.web.service.CourseService;
import com.xczhihui.bxg.online.web.vo.CourseApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.api.vo.CriticizeVo;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.AskTagService;
import com.xczhihui.bxg.online.web.service.VideoService;

/**
 * 视频相关页面控制层
 * @Author Fudong.Sun【】
 * @Date 2016/11/2 15:11
 */
@RestController
@RequestMapping(value = "/video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private AskTagService  askTagService;
    @Autowired
    private CourseService courseService;
    private Object lock = new Object();

    /**
     * 根据节Id查询节下的所有视频列表
     * v.新版本要求查询课程中所有章、节、知识点下的视频列表
     * @param sectionId 节Id
     * @return
     */
    @RequestMapping(value = "/getVideos")
    public ResponseObject getVideos(HttpServletRequest request,String sectionId,String courseId,Boolean isTryLearn) {
        //获取当前登录用户信息
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        return ResponseObject.newSuccessResponseObject(videoService.getVideos(sectionId, courseId,user,isTryLearn));
    }

    /**
     * v.新版本要求查询课程中所有章、节、知识点下的视频列表
     * @param request
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/getvideos")
    public ResponseObject getvideos(HttpServletRequest request,Integer courseId,Boolean isTryLearn) {
        //获取当前登录用户信息
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        String userId = "";
        if(user!=null){
            userId = user.getId();
        }
        return ResponseObject.newSuccessResponseObject(videoService.getvideos(courseId, userId, isTryLearn));
    }

    /**
     * 获取所有对该视频的评论
     * @param videoId 视频ID
     * @return
     */
    @RequestMapping(value = "/getVideoCriticize")
    public ResponseObject getVideoCriticize(HttpServletRequest request,Integer videoId,Integer pageNumber,Integer pageSize) {
        //获取当前登录用户信息
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
//        String userName = user==null? "" : user.getLoginName();
//        CourseApplyVo cv = courseService.getCourseApplyByCourseId(videoId);
        return ResponseObject.newSuccessResponseObject(videoService.getVideoCriticize(null, videoId, pageNumber, pageSize,user!= null ? user.getId() :null));
    }

    /**
     * 提交评论：
     * 参数：content、userId、chapterId、videoId、star_level
     * @param criticizeVo
     * @return
     */
    @RequestMapping(value = "/saveCriticize",method = RequestMethod.POST)
    public ResponseObject saveCriticize(HttpServletRequest request,CriticizeVo criticizeVo){
        try {
            //获取当前登录用户信息
            OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
            if(user!=null) {
                CourseApplyVo cv = courseService.getCourseApplyByCourseId(criticizeVo.getCourseId());
                criticizeVo.setCreatePerson(user.getId());
                criticizeVo.setUserId(cv.getUserLecturerId());
                criticizeVo.setCreateTime(new Date());
//                videoService.saveCriticize(criticizeVo);
                return ResponseObject.newSuccessResponseObject("提交评论成功！");
            }else {
                return ResponseObject.newErrorResponseObject("用户未登录！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("提交评论失败！");
        }
    }
    /**
     * 根据ID查询评论
     * @param id
     * @return
     */
    @RequestMapping(value = "/findCriticizeById")
    public ResponseObject findCriticizeById(String id){

//        return ResponseObject.newSuccessResponseObject(videoService.findCriticizeById(id));
        return ResponseObject.newSuccessResponseObject();
    }

    /**
     * 点赞、取消点赞
     * @param request
     * @param isPraise
     * @param criticizeId
     * @return
     */
    @RequestMapping(value = "/updatePraise",method = RequestMethod.POST)
    public ResponseObject updatePraise(HttpServletRequest request,Boolean isPraise, String criticizeId) {
        //获取当前登录用户信息
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if(user!=null) {
//            Map<String, Object> returnMap = videoService.updatePraise(isPraise, criticizeId, user.getLoginName());
//            return ResponseObject.newSuccessResponseObject(returnMap);
            return ResponseObject.newSuccessResponseObject(null);
        }else{
            return ResponseObject.newErrorResponseObject("用户未登录！");
        }
    }
    /**
     * 学员学习状态修改
     * @param request
     * @param studyStatus
     * @return
     */
    @RequestMapping(value = "/updateStudyStatus",method = RequestMethod.POST)
    public ResponseObject updateStudyStatus(HttpServletRequest request,String studyStatus,String videoId) {
        try {
            //获取当前登录用户信息
            OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
            if(user!=null) {
                videoService.updateStudyStatus(studyStatus, videoId, user.getId());
                return ResponseObject.newSuccessResponseObject("修改成功！");
            }else{
                return ResponseObject.newErrorResponseObject("用户未登录！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("修改失败！");
        }
    }
    /**
     * 获取视频Id获取学习该视频的8个学员
     * @param videoId 视频ID
     * @return
     */
    @RequestMapping(value = "/getLearnedUser")
    public ResponseObject getLearnedUser(String videoId) {
        return ResponseObject.newSuccessResponseObject(videoService.getLearnedUser(videoId));
    }
    /**
     * 根据课程id获取购买过该课程的学员
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/getPurchasedUser")
    public ResponseObject getPurchasedUser(String courseId) {
        return ResponseObject.newSuccessResponseObject(videoService.getPurchasedUser(courseId));
    }

    /**
     * 免费课程 用户报名时，将课程下所有视频插入用户视频中间表中
     * @param courseId 课程id
     * @return
     */
    @RequestMapping(value = "/saveEntryVideo",method = RequestMethod.POST)
    public  ResponseObject saveEntryVideo(Integer  courseId,@RequestParam(required=false)String password,HttpServletRequest request){
    	synchronized (lock) {
    		return ResponseObject.newSuccessResponseObject(videoService.saveEntryVideo(courseId,password, request));
		}
    }


    /**
     * 判断课程下是否有视频
     * @param courseId 课程id
     * @return
     */
    @RequestMapping(value = "/findVideosByCourseId",method = RequestMethod.GET)
    public  ResponseObject   findVideosByCourseId(Integer courseId){
        return ResponseObject.newSuccessResponseObject(videoService.findVideosByCourseId(courseId));
    }


    /**
     * 根据学科ID号查找对应的标签
     * @param menuId
     * @return
     */
    @RequestMapping(value = "/getTagsByMenuId")
    public ResponseObject getTagsByMenuId(Integer menuId) {
        return ResponseObject.newSuccessResponseObject(askTagService.getTagsByMenuId(menuId));
    }
}




