package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.bean.Apply;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.ApplyService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.wxpay.typeutil.StringUtil;
import com.xczhihui.bxg.online.api.po.EnchashmentApplication;
import com.xczhihui.bxg.online.api.service.CityService;
import com.xczhihui.bxg.online.api.vo.UserAddressManagerVo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liutao
 * @create 2017-09-21 10:57
 **/
@Controller
@RequestMapping("bxg/apply")
public class ApplyController {

    @Autowired
    private ApplyService applyService;


    @Autowired
    private AppBrowserService appBrowserService;
    
    
    @Autowired
    private CityService cityService;

    
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ApplyController.class);
    
    @RequestMapping("updateBaseInfo")
    @ResponseBody
    public ResponseObject updateBaseInfo(HttpServletRequest request, HttpServletResponse res){

        OnlineUser user = appBrowserService.getOnlineUserByReq(request, null);
        applyService.saveOrUpdateBaseInfo(user.getId(),request.getParameter("realName"),request.getParameter("phone"));
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping("updateDetailsInfo")
    @ResponseBody
    public ResponseObject updateDetailsInfo(HttpServletRequest request, HttpServletResponse res, Apply apply){

        if(StringUtils.isBlank(apply.getQq())){
            apply.setQq(null);
        }
        if(StringUtils.isBlank(apply.getEmail())){
            apply.setEmail(null);
        }
        if(StringUtils.isBlank(apply.getOccupation())){
            apply.setOccupation(null);
        }
        if(StringUtils.isBlank(apply.getReferee())){
            apply.setReferee(null);
        }

log.info(apply.toString());
        OnlineUser user = appBrowserService.getOnlineUserByReq(request, null);
        applyService.updateDetailsInfo(apply);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping("get")
    @ResponseBody
    public ResponseObject get(HttpServletRequest request, HttpServletResponse res){
        String userId=null;
        if(!StringUtils.isNotBlank(userId=request.getParameter("userId"))){
            OnlineUser user = appBrowserService.getOnlineUserByReq(request, null);
            userId=user.getId();
        }
        Apply apply = applyService.get(userId);
        UserAddressManagerVo umv =  cityService.findAcquiescenceAddressById(userId);
        if(apply!=null && umv !=null){
        	apply.setUserAddressManagerVo(umv);
        }
        return ResponseObject.newSuccessResponseObject(apply);
    }

}
