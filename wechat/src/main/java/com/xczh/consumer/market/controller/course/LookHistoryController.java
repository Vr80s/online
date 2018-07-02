package com.xczh.consumer.market.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.service.IWatchHistoryService;
import com.xczhihui.course.vo.WatchHistoryVO;

@Controller
@RequestMapping("/xczh/history")
public class LookHistoryController {


    @Autowired
    public IWatchHistoryService watchHistoryServiceImpl;

    /**
     * Description：增加观看或者学习记录
     *
     * @param account
     * @param courseId
     * @param recordType
     * @param collectionId
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("add")
    @ResponseBody
    public ResponseObject add(@Account OnlineUser account,
                              @RequestParam("courseId") Integer courseId,
                              @RequestParam("recordType") Integer recordType,
                              @RequestParam(required = false) Integer collectionId) {
        watchHistoryServiceImpl.addLookHistory(courseId, account.getId(), recordType, collectionId);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    /**
     * 观看记录列表
     *
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public ResponseObject list(@Account String accountId) {
        try {
            Page<WatchHistoryVO> page = new Page<>();
            page.setCurrent(1);
            page.setSize(5);
            return ResponseObject.newSuccessResponseObject(watchHistoryServiceImpl.selectWatchHistory(page, accountId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("数据有误");
        }
    }

    /**
     * 清空观看记录
     *
     * @return
     */
    @RequestMapping("empty")
    @ResponseBody
    public ResponseObject empty(@Account String accountId) {
        try {
            watchHistoryServiceImpl.deleteBatch(accountId);
            return ResponseObject.newSuccessResponseObject("清空成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("清空失败");
        }
    }
}
