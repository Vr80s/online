package com.xczhihui.bxg.online.web.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.course.model.WatchHistory;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IWatchHistoryService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.WatchHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: LookHistoryController
 * @Description: 观看历史记录
 * @Author: wangyishuai
 * @email: 15210815880@163.com
 * @CreateDate: 2018/5/16 16:16
 **/
@Controller
@RequestMapping(value = "/history")
public class LookHistoryController extends AbstractController {

    @Autowired
    public IWatchHistoryService watchHistoryService;
    @Autowired
    private ICourseService courseServiceImpl;
    @Autowired
    public IWatchHistoryService watchHistoryServiceImpl;

    /**
     * Description:观看历史记录列表
     * @return ResponseObject
     * @author wangyishuai
     **/
    @RequestMapping(value = "/list")
    @ResponseBody
    public ResponseObject getLookHistoryList(HttpServletRequest request,Integer pageNumber, Integer pageSize) {
        try {
            Page<WatchHistoryVO> page = new Page<>();
            page.setCurrent(pageNumber);
            page.setSize(pageSize);
            OnlineUser u =  getCurrentUser();
            if(u==null) {
                return ResponseObject.newErrorResponseObject("用户未登录");
            }
            Page<WatchHistoryVO> list=watchHistoryService.selectWatchHistory(page,u.getId());
            return ResponseObject.newSuccessResponseObject(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("数据有误");
        }
    }

    /**
     * Description:清空观看记录
     * @return ResponseObject
     * @author wangyishuai
     **/
    @RequestMapping(value = "/empty")
    @ResponseBody
    public ResponseObject empty(HttpServletRequest request) {
        try {
            OnlineUser u =  getCurrentUser();
            if(u==null) {
                return ResponseObject.newErrorResponseObject("用户未登录");
            }
            watchHistoryService.deleteBatch(u.getId());
            return ResponseObject.newSuccessResponseObject("清空成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("清空失败");
        }
    }

    /**
     * Description:增加观看或者学习记录
     * @return ResponseObject
     * @author wangyishuai
     **/
    @RequestMapping("add")
    @ResponseBody
    public ResponseObject add(HttpServletRequest req,
                              HttpServletResponse res,
                              @RequestParam("courseId") Integer courseId,
                              @RequestParam("recordType")Integer recordType,
                              @RequestParam(required=false)Integer collectionId) {
        try {
            OnlineUser u =  getCurrentUser();
            if(u==null) {
                return ResponseObject.newErrorResponseObject("用户未登录");
            }
            CourseLecturVo course =  courseServiceImpl.selectCurrentCourseStatus(courseId);
            if(course == null){
                throw new RuntimeException("课程信息有误");
            }

            //锁id
            String lockId = u.getId()+courseId;
            if(collectionId!=null && collectionId!=0){
                lockId  = u.getId()+collectionId;
            }

            if(recordType!=null){
                if(recordType == 1){ //增加学习记录
                    if(course.getWatchState() == 1){
                        watchHistoryServiceImpl.addLearnRecord(lockId, courseId, u.getId(), u.getLoginName());
                    }
                }
                if(recordType == 2){
                    WatchHistory target = new WatchHistory();
                    target.setCourseId(courseId);
                    target.setUserId(u.getId());
                    target.setLecturerId(course.getUserLecturerId());
                    target.setCollectionId(collectionId);
                    watchHistoryServiceImpl.addOrUpdate(lockId,target);
                }
            }else{
                if(course.getType() == 4){
                    WatchHistory target = new WatchHistory();
                    target.setCourseId(courseId);
                    target.setUserId(u.getId());
                    target.setLecturerId(course.getUserLecturerId());
                    watchHistoryServiceImpl.addOrUpdate(lockId,target);
                }
                if(course.getWatchState() == 1){
                    watchHistoryServiceImpl.addLearnRecord(lockId, courseId, u.getId(), u.getLoginName());
                }
            }
            return ResponseObject.newSuccessResponseObject("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("保存失败");
        }
    }

} 