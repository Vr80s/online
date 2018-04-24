package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.ShareManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;

import static com.xczhihui.common.util.bean.ResponseObject.newSuccessResponseObject;

/**
 * 分销管理模块控制层代码
 * @Author Fudong.Sun【】
 * @Date 2016/12/8 16:06
 */
@RestController
@RequestMapping(value = "/share")
public class ShareManageController {
    @Autowired
    ShareManageService shareManageService;


    /**
     * 查询用户各等级学费补贴
     * @param request
     * @return
     */
    @RequestMapping(value = "/order_subsidies")
    public ResponseObject findSubsidies(HttpServletRequest request){
        //获取当前登录用户信息
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if(user!=null) {
            return newSuccessResponseObject(shareManageService.findSubsidies(user.getId()));
        }else{
            return null;
        }
    }

    /**
     * 根据条件查询分享订单
     * @param request
     * @param searchCase  搜索条件（0：按课程名称搜索，1：按购买者用户名搜索）
     * @param level  补贴级别
     * @param startTime  查询的起始时间
     * @param endTime 查询的结束时间
     * @return
     */
    @RequestMapping(value = "/order_shareOrders")
    public ResponseObject findShareOrders(HttpServletRequest request,Integer searchCase,String searchContent,Integer level,String startTime,String endTime,Integer pageNumber, Integer pageSize){
        //获取当前登录用户信息
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if(user!=null) {
            return newSuccessResponseObject(shareManageService.findShareOrders(user.getId(),searchCase,searchContent,level,startTime,endTime,pageNumber,pageSize));
        }else{
            return null;
        }
    }


    /**
     * 查询用户各级别用户人数
     * @return
     */
    @RequestMapping(value = "/findUserCount")
    public ResponseObject findUserCount(HttpServletRequest request){
       return newSuccessResponseObject(shareManageService.findUserCount(request));
    }



    /**
     * 获取当前登录用户的所有一级分享用户
     * @param searchCase 0:按照昵称搜索  1:按照用户名搜索
     * @param searchContent 搜索内容
     * @param startTime  搜索开始时间
     * @param endTime  结束时间
     * @param pageNumber 当前页面
     * @param pageSize  每页条数
     * @param request  当前登录用户
     * @return
     */
    @RequestMapping(value = "/findOneLevelUser")
    public ResponseObject findOneLevelUser(Integer searchCase,String searchContent,String startTime,String endTime,Integer pageNumber, Integer pageSize,HttpServletRequest request) throws ParseException {
        return newSuccessResponseObject(shareManageService.findOneLevelUser(searchCase,searchContent,startTime,endTime,pageNumber,pageSize,request));
    }

    /**
     * 保存登录用户分享关系
     * @param req
     * @return
     */
    @RequestMapping(value = "/saveShareRelation")
    public ResponseObject saveShareRelation(HttpServletRequest req){
        //获取当前登录用户信息
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(req);
        return ResponseObject.newSuccessResponseObject(shareManageService.saveShareRelation(req,user));
    }
    /**
     * 检查是否修改登录用户的分享关系
     * @param req
     * @return
     */
    @RequestMapping(value = "/checkShareRelation")
    public ResponseObject checkShareRelation(HttpServletRequest req){
      return ResponseObject.newSuccessResponseObject(shareManageService.updateCheckShareRelation(req));
    }
}

