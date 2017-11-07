package com.xczh.consumer.market.controller;/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/7 20:08
 */

import com.xczh.consumer.market.service.MessageService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author liutao
 * @create 2017-09-07 20:08
 **/
@Controller
@RequestMapping("/bxg/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserCenterAPI userCenterAPI;


    @RequestMapping("add")
    @ResponseBody
    public ResponseObject binnerList(HttpServletRequest req, HttpServletResponse res) throws Exception{
         messageService.add(req.getParameter("content"),req.getParameter("userId"));
        return ResponseObject.newSuccessResponseObject(null);
    }






}
