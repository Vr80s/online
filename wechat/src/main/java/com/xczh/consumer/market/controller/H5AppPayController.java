package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.utils.ResponseObject;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

//import com.xczh.consumer.market.bean.ExampleBean;
//import com.xczh.consumer.market.service.ExampleLocalService;

/**
 * 
 * 客户端用户访问控制器类
 * 
 * @author yanghui
 **/
@Controller
@RequestMapping("/bxg/pay")
public class H5AppPayController {

	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(H5AppPayController.class);
	
	/**
	 * 拉取微信访问用户信息；
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("wxPay")
	@ResponseBody
	@Transactional
	public ResponseObject appOrderPay(HttpServletRequest req,HttpServletResponse res)
			throws Exception {
		
		LOGGER.info("老版本方法----》》》》wxPay");
		
		return ResponseObject.newErrorResponseObject("请使用最新版本");
		
//		Map<String, String> params=new HashMap<>();
//		params.put("token",req.getParameter("token"));
//		Map<String, String> retobj = new HashMap<String, String>();
//		String orderId = req.getParameter("orderId");
//		String order_From = req.getParameter("orderFrom");
//		OnlineUser u = appBrowserService.getOnlineUserByReq(req);
//		/**
//		 * 根据订单id得到这个订单中已经存在的课程。
//		 *  如果这个课程已经存在，提示用户这个订单你已经购买过了。
//		 */
//		ResponseObject ro =	onlineOrderService.orderIsExitCourseIsBuy(orderId,u.getId(),1);
//		if(!ro.isSuccess()){//存在此订单哈，
//			return ro;
//		}
//		LOGGER.info("wxPay   user.getId()"+u.getId());
//		
//		String userId = u.getId();
//		if (null == orderId || null == userId || null == order_From) {
//			return ResponseObject.newErrorResponseObject("参数异常");
//		}
//		OnlineOrder onlineOrder = onlineOrderService.getOrderByOrderId(orderId);
//		String newOrderNo=onlineOrderService.updateOrderNo(onlineOrder.getOrderNo());
//		retobj.put("ok", "false");
//		if (null == onlineOrder) {
//			return ResponseObject.newSuccessResponseObject(retobj);
//		}
//		if(onlineOrder.getActualPay() < (0.01d)){
//			return ResponseObject.newErrorResponseObject("金额必须大于等于0.01");
//		}
//		
//		Double actualPay = onlineOrder.getActualPay() * 100;
//		int price = actualPay.intValue();
//
//		//订单来源，0直销（本系统），1分销系统，2线下（刷数据） 3:微信分销    4：来自h5   5 来自app  
//		/**
//		 * 判断此请求来时h5呢，还是微信公众号，还是app
//		 */
//		//int orderFrom = onlineOrder.getOrderFrom();
//		int orderFrom = Integer.parseInt(order_From);
//		String spbill_create_ip =WxPayConst.server_ip;
//		if(orderFrom == 4 || orderFrom == 5){
//			spbill_create_ip =getIpAddress(req);
//		}
//		String tradeType = null; //公众号
//		String openId = null;
//		if(orderFrom == 3){
//			String openId1 = req.getParameter("openId");
//			if(null == openId1){
//				WxcpClientUserWxMapping wxUser = wxService.getWxcpClientUserWxMappingByUserId(userId);
//				if(wxUser == null){
//					wxUser = wxService.getWxMappingByUserIdOrUnionId(userId);
//				}
//				if(wxUser!=null){
//					openId = wxUser.getOpenid();
//				}else{
//					/*
//					 * 如果以上两种情况都获取不到微信的信息，重定向的登录页面。
//					 */
//					return ResponseObject.newErrorResponseObject("登录失效");
//				}
//			}else{
//				openId = openId1;
//			}
//			tradeType= PayInfo.TRADE_TYPE_JSAPI;
//		}else if(orderFrom == 4){
//			tradeType =PayInfo.TRADE_TYPE_H5;
//		}else if(orderFrom == 5){
//			tradeType =PayInfo.TRADE_TYPE_APP;
//		}
//		// TODO
//		OrderParamVo orderParamVo=new OrderParamVo();
//		orderParamVo.setUserId(userId);
//		orderParamVo.setSubject(onlineOrderService.getCourseNames(newOrderNo));
//
//		String cacheKey=UUID.randomUUID().toString().replaceAll("-","");
//		String extDatas ="order&"+cacheKey;
//
//		cacheService.set(cacheKey,com.alibaba.fastjson.JSONObject.toJSON(orderParamVo).toString(),7200);
//		LOGGER.info("附加参数："+com.alibaba.fastjson.JSONObject.toJSON(orderParamVo).toString());
//		Map<String, String> retpay = PayFactory.work().getPrePayInfosCommon
//				(newOrderNo, price,  "订单支付",
//						extDatas, openId, spbill_create_ip, tradeType);
//		if (retpay != null) {
//			retpay.put("ok", "true");
//			if(orderFrom == 5){
//				retpay = CommonUtil.getSignER(retpay);
//			}
//			JSONObject jsonObject = JSONObject.fromObject(retpay);
//			LOGGER.info("h5Prepay->jsonObject->\r\n\t"
//					+ jsonObject.toString());// LOGGER.info(jsonObject);
//			return ResponseObject.newSuccessResponseObject(retpay);
//		}
//		LOGGER.info("h5Prepay->retobj->\r\n\t" + retobj.toString());
//		return ResponseObject.newSuccessResponseObject(retobj);
	}

	/**
	 * 微信统一打赏入口
	 * @param req
	 * @param res
	 * @param params
	 * 1pc,2app,3h5 4微信
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("rewardPay")
	@ResponseBody
	public ResponseObject rewardPay(HttpServletRequest req,
                                    HttpServletResponse res, Map<String, String> params)
			throws Exception {

		LOGGER.info("老版本方法----》》》》rewardPay");
		
		return ResponseObject.newErrorResponseObject("请使用最新版本");
		
		
/*		Map<String, String> params2=new HashMap<>();
		params2.put("token",req.getParameter("token"));
		OnlineUser user = appBrowserService.getOnlineUserByReq(req, params2); // onlineUserMapper.findUserById("2c9aec345d59c9f6015d59caa6440000");
		if (user == null) {
			throw new RuntimeException("登录失效");
		}*/
//		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
//		String userId = user.getId();
//		
//		String clientType=req.getParameter("clientType");
//		if ( null == userId) {
//			return ResponseObject.newErrorResponseObject("参数异常");
//		}
//
//		//订单号  支付的钱
//		Double actualPay = new Double(req.getParameter("actualPay")) * 100;
//
//		int price = actualPay.intValue();
//		retobj.put("ok", "false");
//		//订单来源，0直销（本系统），1分销系统，2线下（刷数据） 3:微信分销    4：来自h5   5 来自app
//		/**
//		 * 判断此请求来时h5呢，还是微信公众号，还是app
//		 */
//		int orderFrom = new Integer(clientType);
//		String spbill_create_ip =WxPayConst.server_ip;
//		if(orderFrom == 2 || orderFrom == 3){
//			spbill_create_ip =getIpAddress(req);
//		}
//		String tradeType = null; //公众号
//		String openId = null;
//		
//		if(orderFrom == 4){
//			String openId1 = req.getParameter("openId");
//			if(null == openId1){
//				WxcpClientUserWxMapping wxUser = wxService.getWxcpClientUserWxMappingByUserId(userId);
//				if(wxUser == null){
//					wxUser = wxService.getWxMappingByUserIdOrUnionId(userId);
//				}
//				if(wxUser!=null){
//					openId = wxUser.getOpenid();
//				}else{
//					return ResponseObject.newErrorResponseObject("登录失效");
//				}
//			}else{
//				openId = openId1;
//			}
//			tradeType= PayInfo.TRADE_TYPE_JSAPI;
//		}else if(orderFrom == 3){
//			tradeType =PayInfo.TRADE_TYPE_H5;
//		}else if(orderFrom == 2){
//			tradeType =PayInfo.TRADE_TYPE_APP;
//		}
//		// TODO
//		RewardParamVo rewardParamVo=new RewardParamVo();
//		rewardParamVo.setRewardId(req.getParameter("rewardId"));
//		rewardParamVo.setT("1");
//		rewardParamVo.setClientType(orderFrom+"");
//		rewardParamVo.setLiveId(req.getParameter("liveId"));
//		rewardParamVo.setGiver(userId);
//		rewardParamVo.setReceiver(req.getParameter("receiver"));
//		rewardParamVo.setSubject("打赏");
//		rewardParamVo.setUserId(userId);
//
//
//
//		//验证打赏id和价格是否匹配
//		if(Double.valueOf(req.getParameter("actualPay"))<0.01){
//			throw new IllegalArgumentException("打赏金额必须大于0.01");
//		}
//		Reward reward=rewardService.findById(rewardParamVo.getRewardId());
//		if(reward==null){
//			throw new IllegalArgumentException("无此打赏类型");
//		}else if(!reward.getIsFreedom()&&
//				reward.getPrice().doubleValue()!=Double.valueOf(req.getParameter("actualPay")).doubleValue()){
//			throw new IllegalArgumentException("打赏类型与金额不符");
//		}
////		//根据价格获取giftId
////		List<Reward> rlist=rewardService.listAll();
////		Integer nullId=null;
////		for (Reward r:rlist){
////			if(r.getPrice()==0.0){
////				nullId=r.getId();
////				continue;
////			}
////			if(req.getParameter("actualPay").equals(r.getPrice()+"")){
////				rewardParamVo.setGiftId(r.getId()+"");
////				break;
////			}
////		}
////		if(rewardParamVo.getGiftId()==null){
////			rewardParamVo.setGiftId(nullId+"");
////		}
//
//		
//		String cacheKey=UUID.randomUUID().toString().replaceAll("-","");
//		
//		String extDatas ="reward&"+cacheKey;
//
//		cacheService.set(cacheKey,com.alibaba.fastjson.JSONObject.toJSON(rewardParamVo).toString(),7200);
//		LOGGER.info("打赏参数："+extDatas.length());
//		Map<String, String> retpay = PayFactory.work().getPrePayInfosCommon
//				(TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12), price,  "打赏支付",
//						extDatas, openId, spbill_create_ip, tradeType);
//
//		if (retpay != null) {
//			retpay.put("ok", "true");
//
//			if(orderFrom == 2){
//				retpay = CommonUtil.getSignER(retpay);
//			}
//			JSONObject jsonObject = JSONObject.fromObject(retpay);
//			
//			LOGGER.info("h5Prepay->jsonObject->\r\n\t"
//					+ jsonObject.toString());// LOGGER.info(jsonObject);
//			return ResponseObject.newSuccessResponseObject(retpay);
//		}
//		LOGGER.info("h5Prepay->retobj->\r\n\t" + retobj.toString());
//		return ResponseObject.newSuccessResponseObject(retobj);
	}


	/**
	 * 微信统一充值入口
	 * @param req
	 * @param res
	 * @param params
	 * 1pc,2app,3h5 4微信
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("rechargePay")
	@ResponseBody
	public ResponseObject rechargePay(
			HttpServletRequest req,HttpServletResponse res)
			throws Exception {
		
		LOGGER.info("老版本方法----》》》》rechargePay");

		return ResponseObject.newErrorResponseObject("请使用最新版本");
		
//		Map<String, String> params2=new HashMap<>();
//		params2.put("token",req.getParameter("token"));
//		OnlineUser user = appBrowserService.getOnlineUserByReq(req); // onlineUserMapper.findUserById("2c9aec345d59c9f6015d59caa6440000");
//		if ( user== null) {
//			throw new RuntimeException("登录失效");
//		}
//		String userId = user.getId();
//		String clientType=req.getParameter("clientType");
//		if ( null == userId) {
//			return ResponseObject.newErrorResponseObject("参数异常");
//		}
//
//		//订单号  支付的钱
//		Double actualPay = new Double(req.getParameter("actualPay")) * 100;
//
//		Double count = Double.valueOf(req.getParameter("actualPay"))*rate;
//		if(!WebUtil.isIntegerForDouble(count)){
//			throw new RuntimeException("充值金额"+req.getParameter("actualPay")+"兑换的熊猫币"+count+"不为整数");
//		}
//		if(minimumAmount > Double.valueOf(req.getParameter("actualPay"))){
//			throw new RuntimeException("充值金额低于最低充值金额："+minimumAmount);
//		}
//
//		int price = actualPay.intValue();
//		retobj.put("ok", "false");
//		//订单来源，0直销（本系统），1分销系统，2线下（刷数据） 3:微信分销    4：来自h5   5 来自app
//		/**
//		 * 判断此请求来时h5呢，还是微信公众号，还是app
//		 */
//		int orderFrom = new Integer(clientType);
//		String spbill_create_ip =WxPayConst.server_ip;;
//		if(orderFrom == 2 || orderFrom == 3){
//			spbill_create_ip =getIpAddress(req);
//		}
//		String tradeType = null; //公众号
//		String openId = null;
//		if(orderFrom == 4){
//			openId = req.getParameter("openId");
//			tradeType= PayInfo.TRADE_TYPE_JSAPI;
//		}else if(orderFrom == 3){
//			tradeType =PayInfo.TRADE_TYPE_H5;
//		}else if(orderFrom == 2){
//			tradeType =PayInfo.TRADE_TYPE_APP;
//		}
//		// TODO
//
//		RechargeParamVo rechargeParamVo=new RechargeParamVo();
//		rechargeParamVo.setT("2");
//		rechargeParamVo.setClientType("2");
//		rechargeParamVo.setUserId(user.getId());
//		rechargeParamVo.setSubject("充值熊猫币:"+count+"个");
//
//		String cacheKey=UUID.randomUUID().toString().replaceAll("-","");
//		String extDatas ="recharge&"+cacheKey;
//		String passbackParams = com.alibaba.fastjson.JSONObject.toJSON(rechargeParamVo).toString();
//		cacheService.set(cacheKey,passbackParams,7200);
//		LOGGER.info("充值参数："+extDatas.length());
//		Map<String, String> retpay = PayFactory.work().getPrePayInfosCommon
//				(TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12), price,  "充值",
//						extDatas, openId, spbill_create_ip, tradeType);
//
//		if (retpay != null) {
//			retpay.put("ok", "true");
//			
//			if(orderFrom == 2){
//				retpay = CommonUtil.getSignER(retpay);
//			}
//			JSONObject jsonObject = JSONObject.fromObject(retpay);
//			LOGGER.info("h5Prepay->jsonObject->\r\n\t"
//					+ jsonObject.toString());// LOGGER.info(jsonObject);
//			return ResponseObject.newSuccessResponseObject(retpay);
//		}
//		LOGGER.info("h5Prepay->retobj->\r\n\t" + retobj.toString());
//		return ResponseObject.newSuccessResponseObject(retobj);
	}
}
