package com.xczhihui.bxg.online.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.course.service.IWatchHistoryService;
import com.xczhihui.course.vo.WatchHistoryVO;

/**
 * 观看记录和学习记录控制器
 *
 * @author yangxuan
 */
@Controller
@RequestMapping("/learnWatch")
public class LearnToWatchController extends AbstractController {

    @Autowired
    public IWatchHistoryService watchHistoryServiceImpl;


    /**
     * Description：增加观看记录
     *
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("add")
    @ResponseBody
    public ResponseObject add(
            @RequestParam("courseId") Integer courseId,
            @RequestParam("recordType") Integer recordType,
            @RequestParam(required = false) Integer collectionId) {
        BxgUser ou = UserLoginUtil.getLoginUser();
        if (ou == null) {
            return ResponseObject.newSuccessResponseObject("登录失效");
        }
        watchHistoryServiceImpl.addLookHistory(courseId, ou.getId(), recordType, collectionId);

        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    @RequestMapping("list")
    @ResponseBody
    public ResponseObject list() {
        try {
            //取五条记录
            Page<WatchHistoryVO> page = new Page<>();
            page.setCurrent(1);
            page.setSize(5);
            BxgUser ou = UserLoginUtil.getLoginUser();

            if (ou == null) {
                return ResponseObject.newSuccessResponseObject("登录失效");
            }
            return ResponseObject.newSuccessResponseObject(watchHistoryServiceImpl.selectWatchHistory(page, ou.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("数据有误");
        }
    }

    @RequestMapping("empty")
    @ResponseBody
    public ResponseObject empty() {
        try {
            BxgUser ou = UserLoginUtil.getLoginUser();
            if (ou == null) {
                return ResponseObject.newSuccessResponseObject("登录失效");
            }
            watchHistoryServiceImpl.deleteBatch(ou.getId());
            return ResponseObject.newSuccessResponseObject("清空成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("清空失败");
        }
    }

}
