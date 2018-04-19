package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.service.RewardService;
import com.xczh.consumer.market.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 打赏接口
 * @author liutao
 * @create 2017-08-26 15:26
 **/
@Controller
@RequestMapping("/bxg/reward")
public class RewardController {


    @Autowired
    private RewardService rewardService;


    @RequestMapping(value = "/list")
    @ResponseBody
    public ResponseObject list(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, String> params) throws Exception {
    	
    	
    	
        return ResponseObject.newSuccessResponseObject(rewardService.listAll());
    }

}
