package com.xczh.consumer.market.controller.course;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.model.LineApply;
import com.xczhihui.course.service.ILineApplyService;

@Controller
@RequestMapping("/xczh/apply")
public class LineApplyController {



    @Autowired
    private ILineApplyService lineApplyService;
    
    @Autowired
    private OnlineUserService onlineUserService;

    /**
     * 增加线下课报名记录
     * @param account
     * @param res
     * @param lineApply
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public ResponseObject add(@Account OnlineUser account,
                              HttpServletResponse res,
                              LineApply lineApply) {
        try {
    	  String accountId = account.getId();
    	  OnlineUser ou = onlineUserService.findUserById(accountId);
    	  if(ou == null ) {
    		  return ResponseObject.newErrorResponseObject("用户信息有误");
    	  }
    	  lineApply.setUserId(accountId);
    	  lineApplyService.saveOrUpdate(accountId,lineApply);
          return ResponseObject.newSuccessResponseObject("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("保存失败");
        }
    }
    
    /**
     * 通过用户id获取
     * @param account
     * @param res
     * @return
     */
    @RequestMapping("applyInfo")
    @ResponseBody
    public ResponseObject applyInfo(@Account OnlineUser account,
                              HttpServletResponse res) {
        try {
    	  String accountId = account.getId();
    	  OnlineUser ou = onlineUserService.findUserById(accountId);
    	  if(ou == null ) {
    		  return ResponseObject.newErrorResponseObject("用户信息有误");
    	  }
    	  LineApply lineApply =  lineApplyService.findLineApplyByUserId(accountId);
          return ResponseObject.newSuccessResponseObject(lineApply);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("获取报名信息有误");
        }
    }
}
