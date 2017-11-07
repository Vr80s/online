package com.xczhihui.bxg.online.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.web.service.FcodeService;

/**
 * F码
 * @author Haicheng Jiang
 */
@RestController
@RequestMapping(value = "/fcode")
public class FcodeController {
	
	@Autowired
	private FcodeService service;
	
	@RequestMapping(value = "/findMyFcode")
    public ResponseObject getBarrierBasicInfo(HttpServletRequest req, 
    		Integer status, Integer pageNumber, Integer pageSize){
		String userId = UserLoginUtil.getLoginUser(req).getId();
		status = status == null ? 0 : status;
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 20 : pageSize;
        return ResponseObject.newSuccessResponseObject(service.findMyFcode(userId,status, pageNumber, pageSize));
    }
	
	@RequestMapping(value = "/addUserFcode",method= RequestMethod.POST)
    public ResponseObject addUserFcode(HttpServletRequest req,String fcode){
		String userId = UserLoginUtil.getLoginUser(req).getId();
		service.addUserFcode(userId,fcode);
        return ResponseObject.newSuccessResponseObject(null);
    }
}
