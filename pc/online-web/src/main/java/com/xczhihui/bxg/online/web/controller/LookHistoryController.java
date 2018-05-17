package com.xczhihui.bxg.online.web.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.course.service.IWatchHistoryService;
import com.xczhihui.course.vo.WatchHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

} 