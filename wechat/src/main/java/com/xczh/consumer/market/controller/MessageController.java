package com.xczh.consumer.market.controller;/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/7 20:08
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.utils.ResponseObject;

/**
 * @author liutao
 * @create 2017-09-07 20:08
 **/
@Controller
@RequestMapping("/bxg/message")
public class MessageController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LearningCenterController.class);
	
    @RequestMapping("add")
    @ResponseBody
    public ResponseObject binnerList(HttpServletRequest req, HttpServletResponse res) throws Exception{
    	
    	 LOGGER.info("老版本方法----》》》》learningCenter");
         return ResponseObject.newErrorResponseObject("请使用最新版本");
//    	 OnlineUser user = appBrowserService.getOnlineUserByReq(req);
//    	 if(user!=null){
//    		 messageService.add(req.getParameter("content"),user.getId());
//    	 }else{
//    		 messageService.add(req.getParameter("content"),null);
//    	 }
//         return ResponseObject.newSuccessResponseObject(null);
    }


}
