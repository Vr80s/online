package com.xczh.consumer.market.controller.pay;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineOrderService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.service.WxcpPayFlowService;
import com.xczh.consumer.market.service.WxcpWxRedpackService;
import com.xczh.consumer.market.service.WxcpWxTransService;
import com.xczh.consumer.market.utils.CookieUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.bxg.online.common.enums.OrderFrom;

@Controller
@RequestMapping("/xczh/order")
public class MyOrderController {
	@Autowired
	private OnlineOrderService onlineOrderService;
	@Autowired
	private OnlineUserService onlineUserService;
	@Autowired
	private WxcpClientUserWxMappingService wxService;
	@Autowired
	private WxcpPayFlowService wxcpPayFlowService;
	@Autowired
	private WxcpWxRedpackService wxcpWxRedpackService;
	@Autowired
	private WxcpWxTransService wxcpWxTransService;
	@Autowired
	private AppBrowserService appBrowserService;
	/**
	 * 生成订单  	订单来源，1：pc 2：h5 3:android 4 ios 5 线下 6 工作人员
	 * @param req
	 * @param res
	 * @param params  
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	@Transactional
	public ResponseObject saveOnlineOrder(HttpServletRequest req,
			@RequestParam("courseId")Integer courseId,@RequestParam("orderFrom")Integer orderFrom
			)throws Exception{
		
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
	    if(user==null){
	    	return ResponseObject.newErrorResponseObject("获取用户信息异常");
	    }
		return onlineOrderService.addOrder(courseId,user.getId(),orderFrom);
	}
	/**
	 * 根据订单号获取信息
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getByOrderNo")
	@ResponseBody
	public ResponseObject getOnlineOrderByOrderNo(HttpServletRequest req,
			@RequestParam("orderNo") String orderNo) throws Exception{
		
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
		if(user==null){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}
		return onlineOrderService.getOrderAndCourseInfoByOrderNo(orderNo);
	}
	
	/**
	 * 根据订单id获取信息
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getByOrderId")
	@ResponseBody
	public ResponseObject getOnlineOrderByOrderId(HttpServletRequest req,
			@RequestParam("orderId") String orderId)throws Exception{
		
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
		if(user==null){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}
		return onlineOrderService.getOnlineOrderByOrderId(orderId);
	}
	
	/**
	 * 消费记录  
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "consumptionList")
	@ResponseBody
	public ResponseObject consumptionList(HttpServletRequest req,
                                          HttpServletResponse res, Map<String, String> params) throws Exception{

		OnlineUser user =  appBrowserService.getOnlineUserByReq(req, params);
		if(user==null){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}
		int pageNumber = 0;
		if(null != req.getParameter("pageNumber")){
			pageNumber = Integer.valueOf(req.getParameter("pageNumber"));
		}
		int pageSize = 0;
		if(null != req.getParameter("pageSize")){
			pageSize = Integer.valueOf(req.getParameter("pageSize"));
		}
		/*
		 * 消费记录，目前分为两种： 一：购买课程、二、打赏
		 *   购买课程的在订单表里面有记录，如果是打赏的里面没有记录。
		 */
		Integer status = 1;
		/*
		 * 这个地方是不是应该查
		 *  购买课程的消费记录应该以课程为单位进行搞
		 * List<OnlineOrder> listOrder = onlineOrderService.consumptionList(status,userId, pageNumber,pageSize);
		 */
		return ResponseObject.newSuccessResponseObject(
				onlineOrderService.listPayRecord(user.getId(),pageNumber,pageSize));
	}
	/**
	 * 消费记录详情
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "consumptionItem")
	@ResponseBody
	public ResponseObject consumptionItem(HttpServletRequest req,
                                          HttpServletResponse res, Map<String, String> params) throws Exception{

		OnlineUser user =  appBrowserService.getOnlineUserByReq(req, params);
		if(user==null){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}
		return ResponseObject.newSuccessResponseObject(onlineOrderService.listPayRecordItem(req.getParameter("orderNo").toString()));
	}
}
