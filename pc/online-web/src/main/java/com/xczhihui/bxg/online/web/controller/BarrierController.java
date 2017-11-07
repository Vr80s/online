package com.xczhihui.bxg.online.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.bxg.online.web.vo.BarrierQuestionVo;
import com.xczhihui.bxg.online.web.vo.BarrierVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.web.service.BarrierService;

import java.util.List;

/**
 *关卡试卷控制层实现类
 * @author Rongcai Kang
 */
@RestController
@RequestMapping(value = "/barrier")
public class BarrierController {

    @Autowired
    private BarrierService service;
    /**
     * 获取关卡基本信息
     * @param id 关卡id
     * @return
     */
    @RequestMapping(value = "/getBarrierBasicInfo",method= RequestMethod.GET)
    public ResponseObject getBarrierBasicInfo(String id,Integer examStatu,HttpServletRequest request){
        return   ResponseObject.newSuccessResponseObject(service.getBarrierBasicInfo(id,examStatu,request));
    }


    /**
     * 获取当前用户考试卷，如果没有就创建
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/createUserTestPaper",method= RequestMethod.POST)
    public ResponseObject createUserTestPaper(HttpServletRequest request,String barrierId){
        return ResponseObject.newSuccessResponseObject
        		(service.createUserTestPaper(UserLoginUtil.getLoginUser(request).getId(),barrierId));
    }

    /**
     * 保存我的回答
     * @param qid
     */
    @RequestMapping(value = "/updateQuestionById",method= RequestMethod.POST)
    public ResponseObject updateQuestionById(String questionId,String answer,HttpServletRequest request){
        service.updateQuestionById(questionId,answer,UserLoginUtil.getLoginUser(request).getId());
        return  ResponseObject.newSuccessResponseObject("保存成功");
    }
    
    /**
     * 提交试卷
     * @param request
     * @param barrierId
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/submitPaper",method= RequestMethod.POST)
    public ResponseObject submitPaper(HttpServletRequest request,String recordId) throws Exception{
    	String userId = UserLoginUtil.getLoginUser(request).getId();
        return  ResponseObject.newSuccessResponseObject(service.addSubmitPaper(userId,recordId));
    }

    /**
     * 获得试卷
     * @param request
     * @param barrierId
     * @return
     */
    @RequestMapping(value = "/getCurrentPaper",method= RequestMethod.GET)
    private ResponseObject getCurrentPaper(HttpServletRequest request,String barrierId,Integer examStatu){
        return ResponseObject.newSuccessResponseObject(service.getCurrentPaper(UserLoginUtil.getLoginUser(request).getId(),barrierId,examStatu));
    }

    /**
     * 获取最新一次闯关关卡基本信息
     * @param id 关卡id
     * @return
     */
    @RequestMapping(value = "/getNewBarrierBasicInfo",method= RequestMethod.GET)
    public ResponseObject getNewBarrierBasicInfo(String id,HttpServletRequest request){
        return  ResponseObject.newSuccessResponseObject(service.getNewBarrierBasicInfo(id,request));
    }
}
