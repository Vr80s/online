package com.xczhihui.bxg.online.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.service.PlanService;

/**
 *   PlanService:学习计划控制层
 *   @author Rongcai Kang
 */
@RestController
@RequestMapping(value = "/bxs/plan")
public class PlanController {

    @Autowired
    private PlanService planService;
    /**
     * 获取用户此课程学习计划
     * @return
     */
    @RequestMapping(value = "/getUserLearPlan",method = RequestMethod.GET)
    public ResponseObject getUserLearPlan(Integer courseId, HttpServletRequest request){
       return  ResponseObject.newSuccessResponseObject(planService.getUserLearPlan(courseId,request));
    }
}
