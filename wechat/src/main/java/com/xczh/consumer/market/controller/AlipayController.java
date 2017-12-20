package com.xczh.consumer.market.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xczh.consumer.market.utils.*;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
import com.xczhihui.bxg.online.api.service.UserCoinService;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xczh.consumer.market.bean.AlipayPaymentRecordH5;
import com.xczh.consumer.market.bean.OnlineCourse;
import com.xczh.consumer.market.bean.OnlineOrder;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.Reward;
import com.xczh.consumer.market.bean.RewardStatement;
import com.xczh.consumer.market.service.AlipayPaymentRecordH5Service;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineOrderService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.RewardService;
import com.xczh.consumer.market.vo.CodeUtil;
import com.xczh.consumer.market.vo.OrderParamVo;
import com.xczh.consumer.market.vo.RechargeParamVo;
import com.xczh.consumer.market.vo.RewardParamVo;

@Controller
@RequestMapping("/bxg/alipay")
public class AlipayController {

	/*
	 * @Autowired private UserCenterAPI userCenterAPI;
	 */

	@Autowired
	private OnlineUserService onlineUserService;

	@Autowired
	private OnlineOrderService onlineOrderService;

	@Autowired
	private AlipayPaymentRecordH5Service alipayPaymentRecordH5Service;

	@Autowired
	private AppBrowserService appBrowserService;

	@Autowired
	private RewardService rewardService;

	@Autowired
	private UserCoinService userCoinService;

	@Value("${online.weburl}")
	private String pcUrl;

	@Value("${onlinekey}")
	private String onlinekey;
	@Value("${rate}")
	private int rate;
	@Value("${minimum_amount}")
	private Double minimumAmount;

	@Autowired
	private AlipayConfig alipayConfig;

	@Value("${returnOpenidUri}")
	private String returnOpenidUri;

	/**
	 * 订单支付
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pay")
	@ResponseBody
	public void OnlineOrderPay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> retobj = new HashMap<String, String>();

		String orderNo = request.getParameter("orderNo");
		if (null == orderNo) {
			// return ResponseObject.newErrorResponseObject("参数异常");
			throw new RuntimeException("订单号不能为空！");
		}
		// 通过订单号得到订单信息
		OnlineOrder onlineOrder = onlineOrderService.getOrderByOrderId(request
				.getParameter("orderId"));// onlineOrderService.getOnlineOrderByOrderNo(orderNo);
		retobj.put("ok", "false");
		if (null == onlineOrder) {
			throw new RuntimeException("找不到订单 ！");
		}

		// 订单号 支付的钱
		String orderNo2 = onlineOrder.getOrderNo();

		String ap = null;
		ap = onlineOrder.getActualPay().toString();
		if (ap.indexOf(".") >= 0
				&& ap.substring(ap.lastIndexOf(".")).length() < 3) {
			ap = ap + "0";
		} else {
		}

		// if (request.getParameter("WIDout_trade_no") != null) {
		// 商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = new String(orderNo2.getBytes("ISO-8859-1"),
				"UTF-8");
		// 订单名称，必填
		String subject = onlineOrderService.getCourseNames(orderNo2);// new
																		// String("订单支付".getBytes("ISO-8859-1"),
																		// "UTF-8");
		// 付款金额，必填
		String total_amount = String.valueOf(ap);
		// 商品描述，可空
		String body = "";
		// 超时时间 可空
		String timeout_express = "24h";
		// 销售产品码 必填
		String product_code = "QUICK_WAP_PAY";
		/**********************/
		// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
		// 调用RSA签名方式
		AlipayClient client = new DefaultAlipayClient(alipayConfig.URL,
				alipayConfig.APPID, alipayConfig.RSA_PRIVATE_KEY,
				alipayConfig.FORMAT, alipayConfig.CHARSET,
				alipayConfig.ALIPAY_PUBLIC_KEY, alipayConfig.SIGNTYPE);
		AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

		// 封装请求支付信息
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setOutTradeNo(out_trade_no);
		model.setSubject(subject);
		model.setTotalAmount(total_amount);
		model.setBody(body);
		model.setTimeoutExpress(timeout_express);
		model.setProductCode(product_code);
		OrderParamVo orderParamVo = new OrderParamVo();
		orderParamVo.setUserId(onlineOrder.getUserId());

		//
		// PayBiz payBizRun= PayBizRunFactory.init("dashang");
		//
		//
		// AlipayTradeWapPayRequest alipa= payBizRun.(request,response);

		String passbackParams = JSONObject.toJSON(orderParamVo).toString();
		System.out.println("附加参数：" + passbackParams);
		model.setPassbackParams(passbackParams);
		alipay_request.setBizModel(model);
		// 设置异步通知地址
		alipay_request.setNotifyUrl(alipayConfig.notify_url);
		// 设置同步地址

		// 如果订单包含多个课程就跳到订单列表,否则直接跳掉视频详情页,如果是从微信出来的,就跳到返回微信的页面

		List<OnlineCourse> cList = null;
		if (StringUtils.isNotBlank(request.getParameter("formIsWechat"))) {
			alipay_request.setReturnUrl(returnOpenidUri
					+ "/xcviews/html/goWechat.html");
		} else {
			cList = ((OnlineOrder) onlineOrderService
					.getOrderAndCourseInfoByOrderNo(orderNo).getResultObject())
					.getAllCourse();
			int cCount = cList.size();
			if (cCount > 1) { // 多个课程 跳到订单列表
				alipay_request.setReturnUrl(alipayConfig.return_url);
			} else { // 单个课程 直接跳到播放页
				OnlineCourse payCourse = cList.get(0);
				if (payCourse.getType() == 1) { // 直播
					alipay_request.setReturnUrl(returnOpenidUri
							+ "/bxg/xcpage/courseDetails?courseId="
							+ payCourse.getId());
				} else { // 视频 音频
					alipay_request.setReturnUrl(returnOpenidUri
							+ "/xcviews/html/particulars.html?courseId="
							+ payCourse.getId());
				}
			}
		}

		// form表单生产
		String form = "";
		try {
			// 调用SDK生成表单
			form = client.pageExecute(alipay_request).getBody();
			response.setContentType("text/html;charset=" + alipayConfig.CHARSET);
			response.getWriter().write(form);// 直接将完整的表单html输出到页面
			response.getWriter().flush();
			response.getWriter().close();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 线下订单支付
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "payXianXia")
	@ResponseBody
	public void xianxiaPay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> retobj = new HashMap<String, String>();

		String orderNo = request.getParameter("orderNo");
		if (null == orderNo) {
			// return ResponseObject.newErrorResponseObject("参数异常");
			throw new RuntimeException("订单号不能为空！");
		}
		// 通过订单号得到订单信息
		OnlineOrder onlineOrder = onlineOrderService.getOrderByOrderId(request
				.getParameter("orderId"));// onlineOrderService.getOnlineOrderByOrderNo(orderNo);
		retobj.put("ok", "false");
		if (null == onlineOrder) {
			throw new RuntimeException("找不到订单 ！");
		}

		// 订单号 支付的钱
		orderNo = onlineOrder.getOrderNo();
		System.out.println("orderNo:" + orderNo);
		System.out.println("orderNo2:" + orderNo);

		String ap = null;
		ap = onlineOrder.getActualPay().toString();
		if (ap.indexOf(".") >= 0
				&& ap.substring(ap.lastIndexOf(".")).length() < 3) {
			ap = ap + "0";
		} else {
		}

		// if (request.getParameter("WIDout_trade_no") != null) {
		// 商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = new String(orderNo.getBytes("ISO-8859-1"),
				"UTF-8");
		// 订单名称，必填
		String subject = onlineOrderService.getCourseNames(orderNo);// new
																	// String("订单支付".getBytes("ISO-8859-1"),
																	// "UTF-8");
		// 付款金额，必填
		String total_amount = String.valueOf(ap);
		// 商品描述，可空
		String body = "";
		// 超时时间 可空
		String timeout_express = "24h";
		// 销售产品码 必填
		String product_code = "QUICK_WAP_PAY";
		/**********************/
		// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
		// 调用RSA签名方式
		AlipayClient client = new DefaultAlipayClient(alipayConfig.URL,
				alipayConfig.APPID, alipayConfig.RSA_PRIVATE_KEY,
				alipayConfig.FORMAT, alipayConfig.CHARSET,
				alipayConfig.ALIPAY_PUBLIC_KEY, alipayConfig.SIGNTYPE);
		AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

		// 封装请求支付信息
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setOutTradeNo(out_trade_no);
		model.setSubject(subject);
		model.setTotalAmount(total_amount);
		model.setBody(body);
		model.setTimeoutExpress(timeout_express);
		model.setProductCode(product_code);
		OrderParamVo orderParamVo = new OrderParamVo();
		orderParamVo.setUserId(onlineOrder.getUserId());

		// PayBiz payBizRun= PayBizRunFactory.init("dashang");
		// AlipayTradeWapPayRequest alipa= payBizRun.(request,response);

		String passbackParams = JSONObject.toJSON(orderParamVo).toString();
		System.out.println("附加参数：" + passbackParams);
		model.setPassbackParams(passbackParams);
		alipay_request.setBizModel(model);
		// 设置异步通知地址
		alipay_request.setNotifyUrl(alipayConfig.notify_url);
		// 设置同步地址
		// 如果订单包含多个课程就跳到订单列表,否则直接跳掉视频详情页,如果是从微信出来的,就跳到返回微信的页面
		List<OnlineCourse> cList = null;
		if (StringUtils.isNotBlank(request.getParameter("formIsWechat"))) {

			System.out.println("orderNo:" + orderNo);

			System.out.println("OnlineOrder:"
					+ onlineOrderService
							.getOrderAndCourseInfoByOrderNo(orderNo)
							.getResultObject());

			// System.out.println("OnlineOrder:"+onlineOrderService.getOrderAndCourseInfoByOrderNo(orderNo).getResultObject().getAllCourse().s);

			cList = ((OnlineOrder) onlineOrderService
					.getOrderAndCourseInfoByOrderNo(orderNo).getResultObject())
					.getAllCourse();
			int cCount = cList.size();
			if (cCount > 1) { // 多个课程 跳到订单列表
				alipay_request.setReturnUrl(alipayConfig.return_url);
			} else { // 单个课程 直接跳到播放页
				OnlineCourse payCourse = cList.get(0);
				alipay_request.setReturnUrl(returnOpenidUri
						+ "/xcviews/html/personalfor.html?userId="
						+ onlineOrder.getUserId() + "&id=" + payCourse.getId());
			}
		} else {
			cList = ((OnlineOrder) onlineOrderService
					.getOrderAndCourseInfoByOrderNo(orderNo).getResultObject())
					.getAllCourse();
			int cCount = cList.size();
			if (cCount > 1) { // 多个课程 跳到订单列表
				alipay_request.setReturnUrl(alipayConfig.return_url);
			} else { // 单个课程 直接跳到播放页
				OnlineCourse payCourse = cList.get(0);
				alipay_request.setReturnUrl(returnOpenidUri
						+ "/xcviews/html/personalfor.html?userId="
						+ onlineOrder.getUserId() + "&id=" + payCourse.getId());// 调到填写报名信息页
			}
		}

		// form表单生产
		String form = "";
		try {
			// 调用SDK生成表单
			form = client.pageExecute(alipay_request).getBody();
			response.setContentType("text/html;charset=" + alipayConfig.CHARSET);
			response.getWriter().write(form);// 直接将完整的表单html输出到页面
			response.getWriter().flush();
			response.getWriter().close();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 打赏支付
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "rewardPay")
	@ResponseBody
	public void onlineRewardPay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*
		 * Map<String, String> params2=new HashMap<>();
		 * params2.put("token",request.getParameter("token")); OnlineUser user =
		 * appBrowserService.getOnlineUserByReq(request, params2); //
		 * onlineUserMapper.findUserById("2c9aec345d59c9f6015d59caa6440000"); if
		 * (user == null) { throw new RuntimeException("登录超时！"); }
		 */

		String ap = null;
		ap = request.getParameter("actualPay");
		if (ap.indexOf(".") >= 0
				&& ap.substring(ap.lastIndexOf(".")).length() < 3) {
			ap = ap + "0";
		}

		// 订单号 支付的钱
		// 商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = TimeUtil.getSystemTime()
				+ RandomUtil.getCharAndNumr(12);
		// 订单名称，必填
		String subject = new String("打赏");
		// 付款金额，必填
		String total_amount = ap;
		// 商品描述，可空
		String body = "";
		// 超时时间 可空
		String timeout_express = "15d";
		// 销售产品码 必填
		String product_code = "QUICK_WAP_PAY";
		/**********************/
		// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
		// 调用RSA签名方式
		AlipayClient client = new DefaultAlipayClient(alipayConfig.URL,
				alipayConfig.APPID, alipayConfig.RSA_PRIVATE_KEY,
				alipayConfig.FORMAT, alipayConfig.CHARSET,
				alipayConfig.ALIPAY_PUBLIC_KEY, alipayConfig.SIGNTYPE);
		AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

		RewardParamVo rewardParamVo = new RewardParamVo();
		rewardParamVo.setT("1");
		rewardParamVo.setClientType("3");
		rewardParamVo.setLiveId(request.getParameter("liveId"));
		rewardParamVo.setGiver(request.getParameter("giver"));
		rewardParamVo.setReceiver(request.getParameter("receiver"));
		rewardParamVo.setUserId(request.getParameter("giver"));
		rewardParamVo.setRewardId(request.getParameter("rewardId"));

		// 验证打赏id和价格是否匹配
		if (Double.valueOf(ap) < 0.01) {
			throw new IllegalArgumentException("打赏金额必须大于0.01");
		}
		Reward reward = rewardService.findById(rewardParamVo.getRewardId());
		if (reward == null) {
			throw new IllegalArgumentException("无此打赏类型");
		} else if (!reward.getIsFreedom()
				&& reward.getPrice().doubleValue() != Double.valueOf(ap)
						.doubleValue()) {
			throw new IllegalArgumentException("打赏类型与金额不符");
		}

		// 根据价格获取giftId
		/*
		 * List<Reward> rlist=rewardService.listAll(); Integer nullId=null; for
		 * (Reward r:rlist){ if(r.getPrice()==0.0){ nullId=r.getId(); continue;
		 * } if(request.getParameter("actualPay").equals(r.getPrice()+"")){
		 * rewardParamVo.setGiftId(r.getId()+""); break; } }
		 * if(rewardParamVo.getGiftId()==null){
		 * rewardParamVo.setGiftId(nullId+""); }
		 */

		String passbackParams = JSONObject.toJSON(rewardParamVo).toString();
		System.out.println("打赏参数：" + passbackParams);
		// 封装请求支付信息
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setOutTradeNo(out_trade_no);
		model.setSubject(subject);
		model.setTotalAmount(total_amount);
		model.setBody(body);
		model.setTimeoutExpress(timeout_express);
		model.setProductCode(product_code);
		model.setPassbackParams(passbackParams);
		alipay_request.setBizModel(model);
		// 设置异步通知地址
		alipay_request.setNotifyUrl(alipayConfig.notify_url);
		// 设置同步地址
		alipay_request.setReturnUrl(request.getParameter("returnUrl"));

		if (StringUtils.isNotBlank(request.getParameter("formIsWechat"))) {
			alipay_request.setReturnUrl(returnOpenidUri
					+ "/xcviews/html/goWechat.html");
		}
		// form表单生产
		String form = "";
		try {
			// 调用SDK生成表单
			form = client.pageExecute(alipay_request).getBody();
			response.setContentType("text/html;charset=" + alipayConfig.CHARSET);
			response.getWriter().write(form);// 直接将完整的表单html输出到页面
			response.getWriter().flush();
			response.getWriter().close();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 充值代币(暂时禁用)
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "rechargePay")
	@ResponseBody
	public void rechargePay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> params2 = new HashMap<>();
		params2.put("token", request.getParameter("token"));
		OnlineUser user = appBrowserService
				.getOnlineUserByReq(request, params2); // onlineUserMapper.findUserById("2c9aec345d59c9f6015d59caa6440000");
		if (user == null) {
			throw new RuntimeException("登录超时！");
		}

		String ap = null;
		ap = request.getParameter("actualPay");

		
		if (ap.indexOf(".") >= 0
				&& ap.substring(ap.lastIndexOf(".")).length() < 3) {
			ap = ap + "0";
		}

		Double count = Double.valueOf(ap) * rate;

		if (!WebUtil.isIntegerForDouble(count)) {
			throw new RuntimeException("充值金额" + ap + "兑换的熊猫币" + count + "不为整数");
		}
		if (minimumAmount > Double.valueOf(ap)) {
			throw new RuntimeException("充值金额低于最低充值金额：" + minimumAmount);
		}

		// 订单号 支付的钱
		// 商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = TimeUtil.getSystemTime()
				+ RandomUtil.getCharAndNumr(12);
		// 订单名称，必填
		String subject = new String("充值熊猫币:" + count + "个");
		// 付款金额，必填
		String total_amount = ap;
		// 商品描述，可空
		String body = "";
		// 超时时间 可空
		String timeout_express = "15d";
		// 销售产品码 必填
		String product_code = "QUICK_WAP_PAY";
		/**********************/
		// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
		// 调用RSA签名方式
		AlipayClient client = new DefaultAlipayClient(alipayConfig.URL,
				alipayConfig.APPID, alipayConfig.RSA_PRIVATE_KEY,
				alipayConfig.FORMAT, alipayConfig.CHARSET,
				alipayConfig.ALIPAY_PUBLIC_KEY, alipayConfig.SIGNTYPE);
		AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

		RechargeParamVo rechargeParamVo = new RechargeParamVo();
		rechargeParamVo.setT("3");
		rechargeParamVo.setClientType("3");
		rechargeParamVo.setUserId(user.getId());

		String passbackParams = JSONObject.toJSON(rechargeParamVo).toString();
		System.out.println("充值代币参数：" + passbackParams);
		// 封装请求支付信息
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setOutTradeNo(out_trade_no);
		model.setSubject(subject);
		model.setTotalAmount(total_amount);
		model.setBody(body);
		model.setTimeoutExpress(timeout_express);
		model.setProductCode(product_code);
		model.setPassbackParams(passbackParams);
		alipay_request.setBizModel(model);
		// 设置异步通知地址
		alipay_request.setNotifyUrl(alipayConfig.notify_url);
		// 设置同步地址
		alipay_request.setReturnUrl(returnOpenidUri
				+ "/xcviews/html/topup.html?xmbCount=" + count);

		// if(StringUtils.isNotBlank(request.getParameter("formIsWechat"))){
		// alipay_request.setReturnUrl(returnOpenidUri+"/xcviews/html/topup.html?xmbCount="+count);
		// }

		// form表单生产
		String form = "";
		try {
			// 调用SDK生成表单
			form = client.pageExecute(alipay_request).getBody();
			response.setContentType("text/html;charset=" + alipayConfig.CHARSET);
			response.getWriter().write(form);// 直接将完整的表单html输出到页面
			response.getWriter().flush();
			response.getWriter().close();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * app支付获取订单str
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getOrderStr")
	@ResponseBody
	public ResponseObject getOrderStr(HttpServletRequest req,
			HttpServletResponse res) throws SQLException,
			UnsupportedEncodingException {

		Map<String, String> retobj = new HashMap<String, String>();
		String orderNo = req.getParameter("orderNo");

		if (null == orderNo) {
			throw new RuntimeException("订单号不能为空！");
		}
		// 通过订单号得到订单信息
		OnlineOrder onlineOrder = onlineOrderService.getOrderByOrderId(req
				.getParameter("orderId"));
		retobj.put("ok", "false");
		if (null == onlineOrder) {
			throw new RuntimeException("找不到订单！");
		}

		/**
		 * 根据订单id得到这个订单中已经存在的课程。 如果这个课程已经存在，提示用户这个订单你已经购买过了。
		 */
		ResponseObject ro = onlineOrderService.orderIsExitCourseIsBuy(
				onlineOrder.getId(), onlineOrder.getUserId());
		if (!ro.isSuccess()) {// 存在此订单哈
			return ro;
		}

		// 订单号 支付的钱
		Double actualPay = onlineOrder.getActualPay();
		int price = actualPay.intValue();

		// 实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.URL,
				alipayConfig.APPID, alipayConfig.RSA_PRIVATE_KEY,
				alipayConfig.FORMAT, alipayConfig.CHARSET,
				alipayConfig.ALIPAY_PUBLIC_KEY, alipayConfig.SIGNTYPE);
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		// model.setBody("");
		String sub = new String("order pay".getBytes("ISO-8859-1"), "UTF-8");
		model.setSubject("购买课程-【"
				+ onlineOrderService.getCourseNames(onlineOrder.getOrderNo())
				+ "】");
		model.setOutTradeNo(onlineOrder.getOrderNo());
		model.setTimeoutExpress("24h");
		model.setTotalAmount(String.valueOf(actualPay));
		model.setProductCode("QUICK_MSECURITY_PAY");
		OrderParamVo orderParamVo = new OrderParamVo();
		orderParamVo.setUserId(onlineOrder.getUserId());
		String passbackParams = JSONObject.toJSON(orderParamVo).toString();
		System.out.println("附加参数：" + passbackParams);
		model.setPassbackParams(passbackParams);
		request.setBizModel(model);
		request.setNotifyUrl(alipayConfig.notify_url);
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient
					.sdkExecute(request);
			System.out.println(response.getBody());// 就是orderString
													// 可以直接给客户端请求，无需再做处理。
			return ResponseObject.newSuccessResponseObject(response.getBody());
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return ResponseObject.newErrorResponseObject("操作失败！");
	}

	/**
	 * app打赏支付获取订单str
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getRewardOrderStr")
	@ResponseBody
	public ResponseObject getRewardOrderStr(HttpServletRequest req,
			HttpServletResponse res) throws SQLException {

		// 订单号 支付的钱
		String ap = null;
		ap = req.getParameter("actualPay");

		try {
			double apd = Double.valueOf(ap);
			if (apd < (0.01d)) {
				return ResponseObject.newErrorResponseObject("金额必须大于等于0.01");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("请输入正确的金额");
		}

		if (ap.indexOf(".") >= 0
				&& ap.substring(ap.lastIndexOf(".")).length() < 3) {
			ap = ap + "0";
		}
		// 实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.URL,
				alipayConfig.APPID, alipayConfig.RSA_PRIVATE_KEY,
				alipayConfig.FORMAT, alipayConfig.CHARSET,
				alipayConfig.ALIPAY_PUBLIC_KEY, alipayConfig.SIGNTYPE);
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。

		RewardParamVo rewardParamVo = new RewardParamVo();
		rewardParamVo.setRewardId(req.getParameter("rewardId"));
		rewardParamVo.setT("1");
		rewardParamVo.setClientType(req.getParameter("orderFrom"));
		rewardParamVo.setLiveId(req.getParameter("liveId"));
		rewardParamVo.setGiver(req.getParameter("userId"));
		rewardParamVo.setReceiver(req.getParameter("receiver"));
		rewardParamVo.setUserId(req.getParameter("userId"));
		String passbackParams = JSONObject.toJSON(rewardParamVo).toString();
		System.out.println("打赏参数：" + passbackParams);

		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		// model.setBody("");
		model.setSubject("打赏");
		model.setOutTradeNo(TimeUtil.getSystemTime()
				+ RandomUtil.getCharAndNumr(12));
		model.setTimeoutExpress("24h");
		model.setTotalAmount(String.valueOf(ap));
		model.setProductCode("QUICK_MSECURITY_PAY");
		model.setPassbackParams(passbackParams);
		request.setBizModel(model);
		request.setNotifyUrl(alipayConfig.notify_url);
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient
					.sdkExecute(request);
			System.out.println(response.getBody());// 就是orderString
													// 可以直接给客户端请求，无需再做处理。
			return ResponseObject.newSuccessResponseObject(response.getBody());
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return ResponseObject.newErrorResponseObject("操作失败！");
	}

	@RequestMapping(value = "alipayReturn")
	public void alipayReturn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 获取支付宝GET过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号

		String out_trade_no = new String(request.getParameter("out_trade_no")
				.getBytes("ISO-8859-1"), "UTF-8");

		// 支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes(
				"ISO-8859-1"), "UTF-8");

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		// 计算得出通知验证结果
		// boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String
		// publicKey, String charset, String sign_type)
		boolean verify_result = AlipaySignature.rsaCheckV1(params,
				alipayConfig.ALIPAY_PUBLIC_KEY, alipayConfig.CHARSET, "RSA2");

		if (verify_result) {// 验证成功
			// ////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码
			// 该页面可做页面美工编辑
			// out.clear();
			// response.getWriter().println("验证成功<br />");

			String weburl = "www";
			response.getWriter()
					.println(
							""
									+ ""
									+ "<script>window.open('"
									+ weburl
									+ "/web/html/myStudyCenter.html','_self')</script>");
			// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

			// ////////////////////////////////////////////////////////////////////////////////////////
		} else {
			// 该页面可做页面美工编辑
			// out.clear();
			response.getWriter().println("验证失败");
		}

	}

	/**
	 * 異步通知
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "alipayNotifyUrl")
	@ResponseBody
	public void alipayNotify(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 获取支付宝POST过来反馈信息
		Map<String, String> para = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			para.put(name, valueStr);
		}
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号

		String out_trade_no = new String(request.getParameter("out_trade_no")
				.getBytes("ISO-8859-1"), "UTF-8");
		// 支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes(
				"ISO-8859-1"), "UTF-8");

		// 交易状态
		String trade_status = new String(request.getParameter("trade_status")
				.getBytes("ISO-8859-1"), "UTF-8");

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		// 计算得出通知验证结果
		// boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String
		// publicKey, String charset, String sign_type)
		boolean verify_result = AlipaySignature.rsaCheckV1(para,
				alipayConfig.ALIPAY_PUBLIC_KEY, alipayConfig.CHARSET, "RSA2");

		if (verify_result) {// 验证成功
			if ("TRADE_CLOSED".equals(para.get("trade_status"))) {
				return;
			}
			AlipayPaymentRecordH5 alipayPaymentRecordH5 = new AlipayPaymentRecordH5();
			alipayPaymentRecordH5.setNotifyTime(para.get("notify_time"));
			alipayPaymentRecordH5.setNotifyType(para.get("notify_type"));
			alipayPaymentRecordH5.setNotifyId(para.get("notify_id"));
			alipayPaymentRecordH5.setAppId(para.get("app_id"));
			alipayPaymentRecordH5.setCharset(para.get("charset"));
			alipayPaymentRecordH5.setVersion(para.get("version"));
			alipayPaymentRecordH5.setSignType(para.get("sign_type"));
			alipayPaymentRecordH5.setSign(para.get("sign"));
			alipayPaymentRecordH5.setTradeNo(para.get("trade_no"));
			alipayPaymentRecordH5.setOutTradeNo(para.get("out_trade_no"));
			alipayPaymentRecordH5.setOutBizNo(para.get("out_biz_no"));
			alipayPaymentRecordH5.setBuyerId(para.get("buyer_id"));
			alipayPaymentRecordH5.setBuyerLogonId(para.get("buyer_logon_id"));
			alipayPaymentRecordH5.setSellerId(para.get("seller_id"));
			alipayPaymentRecordH5.setSellerEmail(para.get("seller_email"));
			alipayPaymentRecordH5.setTradeStatus(para.get("trade_status"));
			alipayPaymentRecordH5.setTotalAmount(para.get("total_amount"));
			alipayPaymentRecordH5.setReceiptAmount(para.get("receipt_amount"));
			alipayPaymentRecordH5.setInvoiceAmount(para.get("invoice_amount"));
			alipayPaymentRecordH5.setBuyerPayAmount(para
					.get("buyer_pay_amount"));
			alipayPaymentRecordH5.setPointAmount(para.get("point_amount"));
			alipayPaymentRecordH5.setRefundFee(para.get("refund_fee"));
			alipayPaymentRecordH5.setSubject(para.get("subject"));
			alipayPaymentRecordH5.setBody(para.get("body"));
			alipayPaymentRecordH5.setGmtCreate(para.get("gmt_create"));
			alipayPaymentRecordH5.setGmtPayment(para.get("gmt_payment"));
			alipayPaymentRecordH5.setGmtRefund(para.get("gmt_refund"));
			alipayPaymentRecordH5.setGmtClose(para.get("gmt_close"));
			alipayPaymentRecordH5.setFundBillList(para.get("fund_bill_list"));
			alipayPaymentRecordH5
					.setPassbackParams(para.get("passback_params"));
			alipayPaymentRecordH5.setVoucherDetailList(para
					.get("voucher_detail_list"));

			if (trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
				// 如果有做过处理，不执行商户的业务程序

				// 注意：
				// 如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				// 如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
				if (StringUtils.isNotBlank(alipayPaymentRecordH5
						.getPassbackParams())) {
					String ppbt = JSONObject
							.parseObject(
									alipayPaymentRecordH5.getPassbackParams())
							.get("t").toString();
					if (ppbt.equals("1")) { // 打赏
						RewardParamVo rpv = JSONObject.parseObject(
								alipayPaymentRecordH5.getPassbackParams(),
								RewardParamVo.class);
						// RewardStatement rs=new RewardStatement();
						com.xczhihui.bxg.online.api.po.RewardStatement rs = new com.xczhihui.bxg.online.api.po.RewardStatement();
						BeanUtils.copyProperties(rs, rpv);
						rs.setCreateTime(new Date());
						rs.setPayType(0);//
						rs.setStatus(1);
						rs.setOrderNo(out_trade_no);
						rs.setPrice(new Double(alipayPaymentRecordH5
								.getTotalAmount()));
						rs.setChannel(1);
						// rewardService.insert(rs);1
						alipayPaymentRecordH5.setUserId(rpv.getUserId());
						alipayPaymentRecordH5Service
								.insert(alipayPaymentRecordH5);
						userCoinService.updateBalanceForReward(rs);
						response.getWriter().println("success"); // 请不要修改或删除
						return;
					} else if (ppbt.equals("2")) { // 普通订单

						System.out.println("回调数据包："
								+ alipayPaymentRecordH5.getPassbackParams());
						alipayPaymentRecordH5.setUserId((JSONObject
								.parseObject(
										alipayPaymentRecordH5
												.getPassbackParams()).get(
										"userId").toString()));

						alipayPaymentRecordH5Service
								.insert(alipayPaymentRecordH5);
						boolean onlinePaySuccess = httpOnline(out_trade_no,
								trade_no); // 普通订单

						if (onlinePaySuccess) {
							response.getWriter().println("success"); // 请不要修改或删除
						}
					} else if (ppbt.equals("3")) {
						System.out.println("充值回调数据包："
								+ alipayPaymentRecordH5.getPassbackParams());
						alipayPaymentRecordH5.setUserId((JSONObject
								.parseObject(
										alipayPaymentRecordH5
												.getPassbackParams()).get(
										"userId").toString()));
						alipayPaymentRecordH5Service
								.insert(alipayPaymentRecordH5);

						// 执行代币充值工作
						UserCoinIncrease userCoinIncrease = new UserCoinIncrease();
						userCoinIncrease.setUserId(alipayPaymentRecordH5
								.getUserId());
						userCoinIncrease.setChangeType(1);
						userCoinIncrease
								.setValue(new BigDecimal(new Double(
										alipayPaymentRecordH5.getTotalAmount())
										* rate));
						userCoinIncrease.setCreateTime(new Date());
						userCoinIncrease.setPayType(0);
						// userCoinIncrease.setChangeType(0);
						userCoinIncrease
								.setOrderNoRecharge(alipayPaymentRecordH5
										.getTradeNo());
						userCoinService
								.updateBalanceForIncrease(userCoinIncrease);
						response.getWriter().println("success"); // 请不要修改或删除
					}
				}
			}
		} else {// 验证失败
			response.getWriter().println("fail");
		}

	}

	/**
	 * 调用pc端处理订单
	 * 
	 * @param order_no
	 * @param transaction_id
	 */
	private boolean httpOnline(String order_no, String transaction_id)
			throws Exception {
		// TODO
		String s = "out_trade_no=" + order_no + "&result_code=SUCCESS"
				+ "&transaction_id=" + transaction_id + "&key=" + onlinekey;
		String mysign = CodeUtil.MD5Encode(s).toLowerCase();

		String resXml = "<xml>" + "<out_trade_no><![CDATA[" + order_no
				+ "]]></out_trade_no>"
				+ "<result_code><![CDATA[SUCCESS]]></result_code>"
				+ "<transaction_id><![CDATA[" + transaction_id
				+ "]]></transaction_id>" + "<sign><![CDATA[" + mysign
				+ "]]></sign>" + " </xml> ";
		// 请求web端的方法
		String msg = HttpUtil.sendDataRequest(pcUrl + "/web/pay_notify_alipay",
				"application/xml", resXml.toString().getBytes());
		System.out.println("msg  >>>  " + msg);

		//

		Gson g = new GsonBuilder().create();
		Map<String, Object> mp = g.fromJson(msg, Map.class);
		return Boolean.valueOf(mp.get("success").toString());

	}

	
	public static void main(String[] args) {
		
		String ap = "中国你好";
        try {
        	double apd = Double.valueOf(ap);
            if(apd < (0.01d)){
    			System.out.println("金额必须大于等于0.01");
    		}
		} catch (Exception e) {
			 e.printStackTrace();
			System.out.println("请输入正确的金额");
		}
		
	}
	
}