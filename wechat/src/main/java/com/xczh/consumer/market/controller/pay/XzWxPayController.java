package com.xczh.consumer.market.controller.pay;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xczh.consumer.market.bean.Reward;
import com.xczh.consumer.market.utils.RandomUtil;
import com.xczh.consumer.market.utils.TimeUtil;
import com.xczh.consumer.market.utils.WebUtil;

import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineOrder;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineOrderService;
import com.xczh.consumer.market.service.RewardService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.OrderParamVo;
import com.xczh.consumer.market.vo.RechargeParamVo;
import com.xczh.consumer.market.vo.RewardParamVo;
import com.xczh.consumer.market.wxpay.PayFactory;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.entity.PayInfo;
import com.xczh.consumer.market.wxpay.util.CommonUtil;

//import com.xczh.consumer.market.bean.ExampleBean;
//import com.xczh.consumer.market.service.ExampleLocalService;

/**
 * 
 * 客户端用户访问控制器类
 * 
 * @author yanghui
 **/
@Controller
@RequestMapping("/xczh/pay")
public class XzWxPayController {

	@Autowired
	private OnlineOrderService onlineOrderService;
	@Autowired
	private WxcpClientUserWxMappingService wxService;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private RewardService rewardService;
	
	@Autowired
	private AppBrowserService appBrowserService;

	@Value("${rate}")
	private int rate;
	@Value("${minimum_amount}")
	private Double minimumAmount;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(XzWxPayController.class);
	
	/**
	 * 微信统一购买课程接口   订单来源，0直销（本系统），1分销系统，2线下（刷数据） 3:微信分销    4：来自h5   5 来自app  
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping("wxPay")
	@ResponseBody
	@Transactional
	public ResponseObject appOrderPay(HttpServletRequest req,
			@RequestParam("orderId")String orderId,
			@RequestParam("orderFrom")String orderFrom)
			throws Exception {
		
		Map<String, String> retobj = new HashMap<String, String>();
		retobj.put("ok", "false");
		
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		if (null == ou ) {
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		String userId = ou.getId();
		/**
		 * 根据订单id得到这个订单中已经存在的课程。
		 *  如果这个课程已经存在，提示用户这个订单你已经购买过了。
		 */
		ResponseObject ro =	onlineOrderService.orderIsExitCourseIsBuy(orderId,userId,1);
		if(!ro.isSuccess()){//存在此订单哈，
			return ro;
		}
		LOGGER.info(" 登录的用户id "+userId);
		/**
		 * 通过订单id得到订单信息，因为每次微信支付时都把这个订单好变了下，因为如果订单号不变，取消订单后在重复支付会有问题
		 * 更改订单号，购买失败后，再次购买微信提示订单号一样的信息
		 *
		 */
		OnlineOrder onlineOrder = onlineOrderService.getOrderByOrderId(orderId);
		
		LOGGER.info(" onlineOrder "+onlineOrder);
		LOGGER.info(" getOrderNo "+onlineOrder.getOrderNo());
		String newOrderNo=onlineOrderService.updateOrderNo(onlineOrder.getOrderNo());
		
		if (null == onlineOrder) {
			return ResponseObject.newSuccessResponseObject(retobj);
		}
		if(onlineOrder.getActualPay() < (0.01d)){
			return ResponseObject.newErrorResponseObject("金额必须大于等于0.01");
		}
		
		Double actualPay = onlineOrder.getActualPay() * 100;
		int price = actualPay.intValue();
		
		/**
		 * 判断此请求来时h5呢，还是微信公众号，还是app
		 */
		int orderFromI = Integer.parseInt(orderFrom);
		String spbill_create_ip =WxPayConst.server_ip;
		if(orderFromI == 4 || orderFromI == 5){
			spbill_create_ip =getIpAddress(req);
		}
		String tradeType = null; //公众号
		String openId = null;
		if(orderFromI == 3){
			openId = req.getParameter("openId");
			tradeType= PayInfo.TRADE_TYPE_JSAPI;
		}else if(orderFromI == 4){
			tradeType =PayInfo.TRADE_TYPE_H5;
		}else if(orderFromI == 5){
			tradeType =PayInfo.TRADE_TYPE_APP;
		}
		// TODO
		OrderParamVo orderParamVo=new OrderParamVo();
		orderParamVo.setUserId(userId);
		orderParamVo.setSubject(""+onlineOrderService.getCourseNames(newOrderNo));

		String cacheKey=UUID.randomUUID().toString().replaceAll("-","");
		String extDatas ="order&"+cacheKey;

		cacheService.set(cacheKey,com.alibaba.fastjson.JSONObject.toJSON(orderParamVo).toString(),7200);
		LOGGER.info("附加参数："+com.alibaba.fastjson.JSONObject.toJSON(orderParamVo).toString());
		
		Map<String, String> retpay = PayFactory.work().getPrePayInfosCommon
				(newOrderNo, price,  "订单支付",extDatas, openId, spbill_create_ip, tradeType);
		
		if (retpay != null) {
			retpay.put("ok", "true");
			/**
			 * app支付需要进行二次签名
			 */
			if(orderFromI == 5){
				retpay = CommonUtil.getSignER(retpay);
			}
			JSONObject jsonObject = JSONObject.fromObject(retpay);
			LOGGER.info("h5Prepay->jsonObject->\r\n\t"+ jsonObject.toString());// LOGGER.info(jsonObject);
			return ResponseObject.newSuccessResponseObject(retpay);
		}
		LOGGER.info("h5Prepay->retobj->\r\n\t" + retobj.toString());
		return ResponseObject.newSuccessResponseObject(retobj);
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
	public ResponseObject rechargePay(HttpServletRequest req,HttpServletResponse res,
			@RequestParam("clientType")Integer clientType,
			@RequestParam("actualPay")String actualPay)
			throws Exception {
		
		Map<String, String> retobj = new HashMap<String, String>();
		retobj.put("ok", "false");
		
		OnlineUser user = appBrowserService.getOnlineUserByReq(req); // onlineUserMapper.findUserById("2c9aec345d59c9f6015d59caa6440000");
		if ( user== null) {
			throw new RuntimeException("登录失效");
		}
		
		//订单号  支付的钱
		Double pay = new Double(actualPay) * 100;
		//需要充值的熊猫币
		Double count = Double.valueOf(pay)*rate;
		
		//传递给微信的价格，int类型
		int price = pay.intValue();
		
		if(!WebUtil.isIntegerForDouble(count)){
			throw new RuntimeException("充值金额"+req.getParameter("actualPay")+"兑换的熊猫币"+count+"不为整数");
		}
		if(minimumAmount > Double.valueOf(req.getParameter("actualPay"))){
			throw new RuntimeException("充值金额低于最低充值金额："+minimumAmount);
		}
		/**
		 * 判断此请求来时h5呢，还是微信公众号，还是app
		 */
		String spbill_create_ip =WxPayConst.server_ip;;
		if(clientType == 2 || clientType == 3){
			spbill_create_ip =getIpAddress(req);
		}
		String tradeType = null; //公众号
		String openId = null;
		
		int orderFrom = 0;
		if(clientType == 3){
			openId = req.getParameter("openId");
			tradeType= PayInfo.TRADE_TYPE_JSAPI;
			orderFrom = 3;
		}else if(clientType == 4){
			tradeType =PayInfo.TRADE_TYPE_H5;
			orderFrom = 2;
		}else if(clientType == 5){
			tradeType =PayInfo.TRADE_TYPE_APP;
			orderFrom = 2;
		}
		// TODO
		/*
		 *封装充值对象 
		 */
		RechargeParamVo rechargeParamVo=new RechargeParamVo();
		rechargeParamVo.setT("2");
		rechargeParamVo.setClientType(orderFrom+"");  //订单来源
		rechargeParamVo.setUserId(user.getId());  //充值的用户id
		rechargeParamVo.setSubject("充值");         
		
		String cacheKey=UUID.randomUUID().toString().replaceAll("-","");
		String extDatas ="recharge&"+cacheKey;
		String passbackParams = com.alibaba.fastjson.JSONObject.toJSON(rechargeParamVo).toString();
		cacheService.set(cacheKey,passbackParams,7200);
		
		LOGGER.info("充值参数："+extDatas.length());
		
		Map<String, String> retpay = PayFactory.work().getPrePayInfosCommon
				(TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12), price,  "充值",
						extDatas, openId, spbill_create_ip, tradeType);

		if (retpay != null) {
			retpay.put("ok", "true");
			if(orderFrom == 2){
				retpay = CommonUtil.getSignER(retpay);
			}
			JSONObject jsonObject = JSONObject.fromObject(retpay);
			LOGGER.info("h5Prepay->jsonObject->\r\n\t"
					+ jsonObject.toString());// LOGGER.info(jsonObject);
			return ResponseObject.newSuccessResponseObject(retpay);
		}
		LOGGER.info("h5Prepay->retobj->\r\n\t" + retobj.toString());
		return ResponseObject.newSuccessResponseObject(retobj);
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
		Map<String, String> retobj = new HashMap<String, String>();

		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		String userId = user.getId();
		
		String clientType=req.getParameter("clientType");
		if ( null == userId) {
			return ResponseObject.newErrorResponseObject("参数异常");
		}

		//订单号  支付的钱
		Double actualPay = new Double(req.getParameter("actualPay")) * 100;

		int price = actualPay.intValue();
		retobj.put("ok", "false");
		//订单来源，0直销（本系统），1分销系统，2线下（刷数据） 3:微信分销    4：来自h5   5 来自app
		/**
		 * 判断此请求来时h5呢，还是微信公众号，还是app
		 */
		int orderFrom = new Integer(clientType);
		String spbill_create_ip =WxPayConst.server_ip;
		if(orderFrom == 2 || orderFrom == 3){
			spbill_create_ip =getIpAddress(req);
		}
		String tradeType = null; //公众号
		String openId = null;
		
		if(orderFrom == 4){
			String openId1 = req.getParameter("openId");
			if(null == openId1){
				WxcpClientUserWxMapping wxUser = wxService.getWxcpClientUserWxMappingByUserId(userId);
				if(wxUser == null){
					wxUser = wxService.getWxMappingByUserIdOrUnionId(userId);
				}
				if(wxUser!=null){
					openId = wxUser.getOpenid();
				}else{
					return ResponseObject.newErrorResponseObject("登录失效");
				}
			}else{
				openId = openId1;
			}
			tradeType= PayInfo.TRADE_TYPE_JSAPI;
		}else if(orderFrom == 3){
			tradeType =PayInfo.TRADE_TYPE_H5;
		}else if(orderFrom == 2){
			tradeType =PayInfo.TRADE_TYPE_APP;
		}
		// TODO
		RewardParamVo rewardParamVo=new RewardParamVo();
		rewardParamVo.setRewardId(req.getParameter("rewardId"));
		rewardParamVo.setT("1");
		rewardParamVo.setClientType(orderFrom+"");
		rewardParamVo.setLiveId(req.getParameter("liveId"));
		rewardParamVo.setGiver(userId);
		rewardParamVo.setReceiver(req.getParameter("receiver"));
		rewardParamVo.setSubject("打赏");
		rewardParamVo.setUserId(userId);



		//验证打赏id和价格是否匹配
		if(Double.valueOf(req.getParameter("actualPay"))<0.01){
			throw new IllegalArgumentException("打赏金额必须大于0.01");
		}
		Reward reward=rewardService.findById(rewardParamVo.getRewardId());
		if(reward==null){
			throw new IllegalArgumentException("无此打赏类型");
		}else if(!reward.getIsFreedom()&&
				reward.getPrice().doubleValue()!=Double.valueOf(req.getParameter("actualPay")).doubleValue()){
			throw new IllegalArgumentException("打赏类型与金额不符");
		}
//		//根据价格获取giftId
//		List<Reward> rlist=rewardService.listAll();
//		Integer nullId=null;
//		for (Reward r:rlist){
//			if(r.getPrice()==0.0){
//				nullId=r.getId();
//				continue;
//			}
//			if(req.getParameter("actualPay").equals(r.getPrice()+"")){
//				rewardParamVo.setGiftId(r.getId()+"");
//				break;
//			}
//		}
//		if(rewardParamVo.getGiftId()==null){
//			rewardParamVo.setGiftId(nullId+"");
//		}
		String cacheKey=UUID.randomUUID().toString().replaceAll("-","");
		String extDatas ="reward&"+cacheKey;
		cacheService.set(cacheKey,com.alibaba.fastjson.JSONObject.toJSON(rewardParamVo).toString(),7200);
		LOGGER.info("打赏参数："+extDatas.length());
		Map<String, String> retpay = PayFactory.work().getPrePayInfosCommon
				(TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12), price,  "打赏支付",
						extDatas, openId, spbill_create_ip, tradeType);

		if (retpay != null) {
			retpay.put("ok", "true");

			if(orderFrom == 2){
				retpay = CommonUtil.getSignER(retpay);
			}
			JSONObject jsonObject = JSONObject.fromObject(retpay);
			
			LOGGER.info("h5Prepay->jsonObject->\r\n\t"
					+ jsonObject.toString());// LOGGER.info(jsonObject);
			return ResponseObject.newSuccessResponseObject(retpay);
		}
		LOGGER.info("h5Prepay->retobj->\r\n\t" + retobj.toString());
		return ResponseObject.newSuccessResponseObject(retobj);
	}

	public static String getIpAddress(HttpServletRequest request) {

		String ipAddress = request.getHeader("x-forwarded-for");

		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknow".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();

			if ("127.0.0.1".equals(ipAddress)
					|| "0:0:0:0:0:0:0:1".equals(ipAddress)) {
				// 根据网卡获取本机配置的IP地址
				InetAddress inetAddress = null;
				try {
					inetAddress = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inetAddress.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实的IP地址，多个IP按照','分割
		if (null != ipAddress && ipAddress.length() > 15) {
			// "***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
}
