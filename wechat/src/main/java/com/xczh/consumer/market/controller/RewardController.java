package com.xczh.consumer.market.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.utils.ResponseObject;

/**
 * 打赏接口
 * @author liutao
 * @create 2017-08-26 15:26
 **/
@Controller
@RequestMapping("/bxg/reward")
public class RewardController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RewardController.class);


    @RequestMapping(value = "/list")
    @ResponseBody
    public ResponseObject list(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, String> params) throws Exception {
    	
    	LOGGER.info("老版本方法----》》》》wxPay");
		return ResponseObject.newErrorResponseObject("请使用最新版本");
       // return ResponseObject.newSuccessResponseObject(rewardService.listAll());
    }

}
