package com.xczh.consumer.market.controller.pay;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xczhihui.wechat.course.model.Order;
import com.xczhihui.wechat.course.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineOrder;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineOrderService;
import com.xczh.consumer.market.utils.ResponseObject;

@Controller
@RequestMapping("/xczh/order")
public class MyOrderController {
	@Autowired
	private OnlineOrderService onlineOrderService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private AppBrowserService appBrowserService;
	
	@Value("${rate}")
	private int rate;

	/**
	 * 生成订单  	订单来源，1：pc 2：h5 3:android 4 ios 5 线下 6 工作人员
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	@Transactional
	public ResponseObject saveOnlineOrder(HttpServletRequest req,
			@RequestParam("courseId")Integer courseId,
			@RequestParam("orderFrom")Integer orderFrom
			)throws Exception{
		
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
	    if(user==null){
	    	return ResponseObject.newErrorResponseObject("登录失效");
	    }
		Order order = orderService.createOrder(user.getUserId(), courseId, orderFrom);
		Map returnMap = new HashMap();
		returnMap.put("orderNo", order.getOrderNo());
		returnMap.put("orderId",order.getId());
		returnMap.put("currentPrice", order.getPrice());
		return ResponseObject.newSuccessResponseObject(returnMap);
	}
	/**
	 * 根据订单id获取信息
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getByOrderId")
	@ResponseBody
	public ResponseObject getOnlineOrderByOrderId(HttpServletRequest req,
			@RequestParam("orderId") String orderId)throws Exception{
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
		if(user==null){
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		/**
		 * 返回给前台熊猫币
		 */
		OnlineOrder order = (OnlineOrder) onlineOrderService.getNewOrderAndCourseInfoByOrderId(orderId).getResultObject();
		if(order!=null) {
			order.setActualPay(order.getActualPay()*rate);
		}
		return ResponseObject.newSuccessResponseObject(order);
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
	
	/**
	 * 判断此用户在下单的时候是否已经购买过这个订单中的课程
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "orderIsExitCourseIsBuy")
	@ResponseBody
	public ResponseObject orderIsExitCourseIsBuy(HttpServletRequest req,
                                          HttpServletResponse res, Map<String, String> params) throws Exception{
		String orderId = req.getParameter("orderId");
		OnlineOrder onlineOrder = onlineOrderService.getOrderByOrderId(orderId);
		if (null == orderId || null == onlineOrder) {
			return ResponseObject.newErrorResponseObject("参数异常");
		}
		ResponseObject ro =	onlineOrderService.orderIsExitCourseIsBuy(orderId,onlineOrder.getUserId());
		return ro;
	}
}
