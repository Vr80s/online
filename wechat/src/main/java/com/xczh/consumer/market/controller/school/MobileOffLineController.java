package com.xczh.consumer.market.controller.school;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.interceptor.HeaderInterceptor;
import com.xczh.consumer.market.utils.APPUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.service.IOfflineCityService;

/**
 * 线下课控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/bunch")
public class MobileOffLineController {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileOffLineController.class);


    @Autowired
    private IMobileBannerService mobileBannerService;

    @Autowired
    private IOfflineCityService offlineCityService;


    /*****************************************
     *
     *
     * 	新版app关于学堂的接口   -- 线下培训班接口
     *
     *
     * **************************************
     */

    @RequestMapping("offLine")
    @ResponseBody
    public ResponseObject offLine(HttpServletRequest request) throws Exception {
    	
        int clientType = HeaderInterceptor.getClientType().getCode();
        Boolean onlyThread = HeaderInterceptor.ONLY_THREAD.get(); 
        String mobileSource =  APPUtil.getMobileSource(request);
        
        return ResponseObject.newSuccessResponseObject(offlineCityService.getOffLine(clientType,onlyThread,mobileSource));
    }
}
