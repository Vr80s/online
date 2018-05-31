package com.xczh.consumer.market.controller.course;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.model.WatchHistory;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IWatchHistoryService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.WatchHistoryVO;

@Controller
@RequestMapping("/xczh/history")
public class LookHistoryController {


    @Autowired
    public IWatchHistoryService watchHistoryServiceImpl;

    @Autowired
    private ICourseService courseServiceImpl;

    /**
     * Description：增加观看或者学习记录
     *
     * @param account
     * @param res
     * @param courseId
     * @param recordType
     * @param collectionId
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("add")
    @ResponseBody
    public ResponseObject add(@Account OnlineUser account,
                              HttpServletResponse res,
                              @RequestParam("courseId") Integer courseId,
                              @RequestParam("recordType") Integer recordType,
                              @RequestParam(required = false) Integer collectionId) {
        try {
            CourseLecturVo course = courseServiceImpl.selectCurrentCourseStatus(courseId);
            if (course == null) {
                throw new RuntimeException("课程信息有误");
            }

            String accountId = account.getId();
            //锁id
            String lockId = accountId + courseId;
            if (collectionId != null && collectionId != 0) {
                lockId = accountId + collectionId;
            }

            if (recordType != null) {
                if (recordType == 1) { //增加学习记录
                    if (course.getWatchState() == 1) {
                        watchHistoryServiceImpl.addLearnRecord(lockId, courseId, accountId, account.getLoginName());
                    }
                }
                if (recordType == 2) {
                    WatchHistory target = new WatchHistory();
                    target.setCourseId(courseId);
                    target.setUserId(account.getId());
                    target.setLecturerId(course.getUserLecturerId());
                    target.setCollectionId(collectionId);
                    watchHistoryServiceImpl.addOrUpdate(lockId, target);
                }

            } else {
                if (course.getType() == 4) {
                    WatchHistory target = new WatchHistory();
                    target.setCourseId(courseId);
                    target.setUserId(accountId);
                    target.setLecturerId(course.getUserLecturerId());
                    watchHistoryServiceImpl.addOrUpdate(lockId, target);
                }
                if (course.getWatchState() == 1) {
                    watchHistoryServiceImpl.addLearnRecord(lockId, courseId, accountId, account.getLoginName());
                }
            }
            return ResponseObject.newSuccessResponseObject("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("保存失败");
        }
    }

    /**
     * 观看记录列表
     *
     * @param req
     * @param res
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public ResponseObject list(@Account String accountId, HttpServletRequest req,
                               HttpServletResponse res) {
        try {
            Page<WatchHistoryVO> page = new Page<>();
            page.setCurrent(1);
            page.setSize(Integer.MAX_VALUE);
            return ResponseObject.newSuccessResponseObject(watchHistoryServiceImpl.selectWatchHistory(page, accountId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("数据有误");
        }
    }

    /**
     * 清空观看记录
     *
     * @param req
     * @param res
     * @return
     */
    @RequestMapping("empty")
    @ResponseBody
    public ResponseObject empty(@Account String accountId, HttpServletRequest req, HttpServletResponse res) {
        try {
            watchHistoryServiceImpl.deleteBatch(accountId);
            return ResponseObject.newSuccessResponseObject("清空成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("清空失败");
        }
    }
}
