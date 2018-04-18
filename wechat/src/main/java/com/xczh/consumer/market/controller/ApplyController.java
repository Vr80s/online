package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.bean.Apply;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.ApplyService;
import com.xczh.consumer.market.utils.ResponseObject;
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

/**
 * @author liutao
 * @create 2017-09-21 10:57
 **/
@Controller
@RequestMapping("bxg/apply")
public class ApplyController {

    
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ApplyController.class);
    
    @RequestMapping("updateBaseInfo")
    @ResponseBody
    public ResponseObject updateBaseInfo(HttpServletRequest request, HttpServletResponse res){

    	 LOGGER.info("老版本方法----》》》》");
    	 return ResponseObject.newErrorResponseObject("请使用最新版本");
    	
    /*    OnlineUser user = appBrowserService.getOnlineUserByReq(request);
        applyService.saveOrUpdateBaseInfo(user.getId(),request.getParameter("realName"),request.getParameter("phone"));
        return ResponseObject.newSuccessResponseObject(null);*/
    }

    @RequestMapping("updateDetailsInfo")
    @ResponseBody
    public ResponseObject updateDetailsInfo(HttpServletRequest request, HttpServletResponse res, Apply apply){
    	 LOGGER.info("老版本方法----》》》》");
    	return ResponseObject.newErrorResponseObject("请使用最新版本");
/*        if(StringUtils.isBlank(apply.getQq())){
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

LOGGER.info(apply.toString());
        OnlineUser user = appBrowserService.getOnlineUserByReq(request);
        applyService.updateDetailsInfo(apply);
        return ResponseObject.newSuccessResponseObject(null);*/
    }

    @RequestMapping("get")
    @ResponseBody
    public ResponseObject get(HttpServletRequest request, HttpServletResponse res){
    	
    	 LOGGER.info("老版本方法----》》》》");
    	
    	return ResponseObject.newErrorResponseObject("请使用最新版本");
       /* String userId=null;
        if(!StringUtils.isNotBlank(userId=request.getParameter("userId"))){
            OnlineUser user = appBrowserService.getOnlineUserByReq(request);
            userId=user.getId();
        }
        Apply apply = applyService.get(userId);
        UserAddressManagerVo umv =  cityService.findAcquiescenceAddressById(userId);
        if(apply!=null && umv !=null){
        	apply.setUserAddressManagerVo(umv);
        }
        return ResponseObject.newSuccessResponseObject(apply);*/
    }

}
