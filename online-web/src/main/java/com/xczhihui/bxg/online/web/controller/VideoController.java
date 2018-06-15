package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.AskTagService;
import com.xczhihui.bxg.online.web.service.CourseService;
import com.xczhihui.bxg.online.web.service.VideoService;
import com.xczhihui.bxg.online.web.vo.CourseApplyVo;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.online.api.vo.CriticizeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 视频相关页面控制层
 * @Author Fudong.Sun【】
 * @Date 2016/11/2 15:11
 */
@RestController
@RequestMapping(value = "/video")
public class VideoController extends AbstractController{
    @Autowired
    private VideoService videoService;
    @Autowired
    private AskTagService  askTagService;
    @Autowired
    private CourseService courseService;
    private Object lock = new Object();


    /**
     * 获取所有对该视频的评论
     * @param videoId 视频ID
     * @return
     */
    @RequestMapping(value = "/getVideoCriticize")
    public ResponseObject getVideoCriticize(HttpServletRequest request,Integer videoId,Integer pageNumber,Integer pageSize) {
        //获取当前登录用户信息
        OnlineUser user = getCurrentUser();
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
            OnlineUser user = getCurrentUser();
            if(user!=null) {
                CourseApplyVo cv = courseService.getCourseApplyByCourseId(criticizeVo.getCourseId());
                criticizeVo.setCreatePerson(user.getId());
                criticizeVo.setUserId(cv.getUserLecturerId());
                criticizeVo.setCreateTime(new Date());
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
        return ResponseObject.newSuccessResponseObject();
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
            OnlineUser user = getCurrentUser();
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




