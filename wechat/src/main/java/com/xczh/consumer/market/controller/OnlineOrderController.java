package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.bean.OnlineOrder;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.*;
import com.xczh.consumer.market.utils.CookieUtil;
import com.xczh.consumer.market.utils.RandomUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.TimeUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/bxg/order")
public class OnlineOrderController {
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
	/*
	 * 重新生成订单
	 */
	@RequestMapping(value = "saveRegenerate")
	@ResponseBody
	@Transactional
	public ResponseObject saveRegenerate(HttpServletRequest req,
			  HttpServletResponse res) throws Exception{

		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
	    if(user==null){
	    	return ResponseObject.newErrorResponseObject("登录失效");
	    }
		if(null == req.getParameter("orderFrom")
				|| null == req.getParameter("orderId")){
			return ResponseObject.newErrorResponseObject("获取参数有误");
		}
		/*
		 * 根据订单id查询下课程idlist
		 */
		String courseIds = onlineOrderService.getCourseIdByOrderId(req.getParameter("orderId"));
		/**
		 * 根据订单id得到这个订单中已经存在的课程。
		 *  如果这个课程已经存在，提示用户这个订单你已经购买过了。
		 */
		Map<String,Object> returnMap = new HashMap<String,Object>();
		ResponseObject ro =	onlineOrderService.orderIsExitCourseIsBuy(courseIds,user.getId());
		if(ro.isSuccess() && ro.getCode() == 200){//已经存在同样的未支付订单
			OnlineOrder onlineOrder = onlineOrderService.getOrderByOrderId(req.getParameter("orderId"));
			returnMap.put("orderNo", onlineOrder.getOrderNo());
		    returnMap.put("orderId",onlineOrder.getOrderId());
		    ro.setResultObject(returnMap);
			return ro;
		}
		/**
		 * 重新生成订单啦
		 * 然后保存。保存的时候，他们的订单号是一样了。但是订单id不一样
		 */
		String orderNo= TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
		String orderId =UUID.randomUUID().toString().replace("-", "");
		String [] courseIdArr = courseIds.split(",");
		for (String courseId : courseIdArr) {
			ResponseObject rb = onlineOrderService.regenerateOrder(Integer.parseInt(courseId),orderId,orderNo,user.getId(),
					Integer.parseInt(req.getParameter("orderFrom")));
			if(!rb.isSuccess()){
				return  ResponseObject.newErrorResponseObject("失败啦");
			}
		}

		returnMap.put("orderNo", orderNo);
	    returnMap.put("orderId",orderId);
		return  ResponseObject.newSuccessResponseObject(returnMap);
	}

	/**
	 * 生成订单
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
										  HttpServletResponse res, Map<String, String> params)throws Exception{
		
		//orderFrom订单来源  //订单来源，0直销（本系统），1分销系统，2线下（刷数据） 3:微信分销' 4:h5网页    5：手机app
		//分销，设置上下级管理
		String code = CookieUtil.getCookieValue(req, "_usercode_");
		if (code != null && !"".equals(code.trim())) {
			OnlineUser u = (OnlineUser)req.getSession().getAttribute("_user_");
			onlineOrderService.updateUserParentId(u.getId(), code);
		}
		
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
	    if(user==null){
	    	return ResponseObject.newErrorResponseObject("登录失效");
	    }
		if(null == req.getParameter("courseId") || null == req.getParameter("orderFrom")){
			return ResponseObject.newErrorResponseObject("参数异常");
		}
		Integer orderFrom = Integer.valueOf(req.getParameter("orderFrom"));
		Integer courseId = Integer.valueOf(req.getParameter("courseId"));
		return onlineOrderService.addOrder(courseId,user.getId(),orderFrom);
	}
	/**
	 * 获取订单列表
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public ResponseObject getOnlineOrderList(HttpServletRequest req,
                                             HttpServletResponse res, Map<String, String> params)throws Exception{
		int status = -1;   //支付状态 0:未支付 1:已支付 2:已关闭 
		if(null != req.getParameter("status")){
			status = Integer.valueOf(req.getParameter("status"));
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
		List<OnlineOrder> lists = onlineOrderService.getOnlineOrderList(status, userId, pageNumber,pageSize);
		OnlineUser user = onlineUserService.findUserById(userId);
		/**判断是否是合伙人，如果是合伙人将状态设置为10，可以去推荐，如果不是合伙人，默认为1，只能学习，不能推荐 **/
		if(null != user && StringUtils.isNotBlank(user.getShareCode())){
			for (OnlineOrder onlineOrder : lists) {
				if(onlineOrder.getOrderStatus() == 1){
					onlineOrder.setOrderStatus(10);
				}
			}
		}
		return ResponseObject.newSuccessResponseObject(lists);
	}
	/**
	 * 删除/取消订单
	 * @param req
	 * @param res
	 * @param params type : 0 删除订单 1 取消订单  | orderNo : 订单号  
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public ResponseObject updateOnlineOrderStatus(HttpServletRequest req,
                                                  HttpServletResponse res, Map<String, String> params) throws Exception{
		String orderNo = req.getParameter("orderNo");
		if(null == req.getParameter("type") || null == orderNo){
			return ResponseObject.newErrorResponseObject("参数异常");
		}
		Integer type = Integer.valueOf(req.getParameter("type"));
		onlineOrderService.updateOnlineOrderStatus(type, orderNo);
		return ResponseObject.newSuccessResponseObject("修改成功");
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
                                                  HttpServletResponse res, Map<String, String> params) throws Exception{
		String orderNo = req.getParameter("orderNo");
		if(null == orderNo){
			return ResponseObject.newErrorResponseObject("参数异常");
		}
		return onlineOrderService.getOrderAndCourseInfoByOrderNo(orderNo);
	}
	
	/**
	 * 根据订单号获取信息
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getByOrderId")
	@ResponseBody
	public ResponseObject getOnlineOrderByOrderId(HttpServletRequest req,
                                                  HttpServletResponse res, Map<String, String> params) throws Exception{
		String orderId = req.getParameter("orderId");
		if(null == orderId){
			return ResponseObject.newErrorResponseObject("参数异常");
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
                                          HttpServletResponse res) throws Exception{

		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
		if(user==null){
			return ResponseObject.newErrorResponseObject("登录失效");
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
                                          HttpServletResponse res) throws Exception{

		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
		if(user==null){
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		return ResponseObject.newSuccessResponseObject(onlineOrderService.listPayRecordItem(req.getParameter("orderNo").toString()));
	}
}
