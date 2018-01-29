package com.xczh.consumer.market.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.bxg.online.api.po.EnchashmentApplication;
import com.xczhihui.bxg.online.api.service.EnchashmentService;
import com.xczhihui.bxg.online.api.service.UserCoinService;

/**
 * 提现
 * @author liutao
 * @create 2017-09-18 11:52
 **/
@Controller
@RequestMapping("/bxg/enchash")
public class EnchashMentController {

    @Autowired
    private EnchashmentService enchashmentService;

    @Autowired
    private  UserCoinService userCoinService;

    @Autowired
    private AppBrowserService appBrowserService;

    @RequestMapping("add")
    @ResponseBody
    public ResponseObject list(HttpServletRequest request, HttpServletResponse res,EnchashmentApplication enchashmentApplication) throws Exception{

    	
       throw new RuntimeException("请更新最新版本！"); 	
//        Map<String, String> params2=new HashMap<>();
//        params2.put("token",request.getParameter("token"));
//        OnlineUser user = appBrowserService.getOnlineUserByReq(request, params2); // onlineUserMapper.findUserById("2c9aec345d59c9f6015d59caa6440000");
//        if (user == null) {
//            throw new RuntimeException("登录超时！");
//        }
//      enchashmentApplication.setUserId(user.getId());
//      enchashmentService.saveEnchashmentApplyInfo(userId, enchashmentSum, bankCardId, orderFrom);
//       return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping("getEnchashmentBalance")
    @ResponseBody
    public ResponseObject getEnchashmentBalance(HttpServletRequest request, HttpServletResponse res) throws Exception{
        Map<String, String> params2=new HashMap<>();
        params2.put("token",request.getParameter("token"));
        OnlineUser user = appBrowserService.getOnlineUserByReq(request, params2);
        if (user == null) {
        	return ResponseObject.newSuccessResponseObject(0);	
        }else{
        	return ResponseObject.newSuccessResponseObject(userCoinService.getBalanceByUserId(user.getId()));	
        }
    }
    @RequestMapping("getEnchashmentRmbBalance")
    @ResponseBody
    public ResponseObject getEnchashmentRmbBalance(HttpServletRequest request, HttpServletResponse res) throws Exception{

        Map<String, String> params2=new HashMap<>();
        params2.put("token",request.getParameter("token"));
        OnlineUser user = appBrowserService.getOnlineUserByReq(request, params2);
        if (user == null) {
            throw new RuntimeException("登录超时！");
        }
        return ResponseObject.newSuccessResponseObject(userCoinService.getEnchashmentBalanceByUserId(user.getId()));
    }

}
