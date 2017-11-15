package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.bean.OnlineCourse;
import com.xczh.consumer.market.bean.OnlineOrder;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.OnlineOrderService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.CourseLecturVo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

/**
 * 学习中心
 * @author liutao
 * @create 2017-09-28 11:15
 **/
@Controller
@RequestMapping("/bxg/learningCenter")
public class LearningCenterController {

    @Autowired
    private OnlineOrderService onlineOrderService;

    @Autowired
    private OnlineUserService onlineUserService;

    /**
     * 获取列表
     * @param req
     * @param res
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public ResponseObject getList(HttpServletRequest req,
                                             HttpServletResponse res, Map<String, String> params)throws Exception{
        int type = -1;   //支付状态 0:未支付 1:已支付 2:已关闭
        if(null != req.getParameter("type")){
            type = Integer.valueOf(req.getParameter("type"));
        }
        int pageNumber = 0;
        if(null != req.getParameter("pageNumber")){
            pageNumber = Integer.valueOf(req.getParameter("pageNumber"));
        }
        int pageSize = 10;
        if(null != req.getParameter("pageSize")){
            pageSize = Integer.valueOf(req.getParameter("pageSize"));
        }
        String userId =req.getParameter("userId");
        if(null == userId){
            return ResponseObject.newErrorResponseObject("参数异常");
        }
        List<OnlineCourse> lists = onlineOrderService.listLearningCenter(type, userId, pageNumber,pageSize);
		for (OnlineCourse onlineCourse : lists) {
			String city = onlineCourse.getAddress();
			if(city!=null){
				String [] citys = city.split("-");
				onlineCourse.setCity(citys[1]);
			}
		}
		System.out.println("list.size():"+lists.size());
        return ResponseObject.newSuccessResponseObject(lists);
    }








}
