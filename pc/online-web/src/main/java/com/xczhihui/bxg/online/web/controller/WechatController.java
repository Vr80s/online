package com.xczhihui.bxg.online.web.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xczhihui.bxg.online.api.service.OrderPayService;
import com.xczhihui.bxg.online.common.enums.IncreaseChangeType;
import com.xczhihui.bxg.online.common.enums.OrderFrom;
import com.xczhihui.bxg.online.common.enums.Payment;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.xczhihui.bxg.common.support.service.CacheService;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.api.po.RewardStatement;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.Reward;
import com.xczhihui.bxg.online.common.domain.WxcpPayFlow;
import com.xczhihui.bxg.online.common.utils.OnlineConfig;
import com.xczhihui.bxg.online.web.base.utils.RandomUtil;
import com.xczhihui.bxg.online.web.base.utils.TimeUtil;
import com.xczhihui.bxg.online.web.base.utils.WebUtil;
import com.xczhihui.bxg.online.web.service.OrderService;
import com.xczhihui.bxg.online.web.service.RewardService;
import com.xczhihui.bxg.online.web.service.WechatService;
import com.xczhihui.bxg.online.web.service.WxcpPayFlowService;
import com.xczhihui.bxg.online.web.utils.PayCommonUtil;
import com.xczhihui.bxg.online.web.utils.XMLUtil;
import com.xczhihui.bxg.online.web.vo.OrderParamVo;
import com.xczhihui.bxg.online.web.vo.RewardParamVo;
import com.xczhihui.bxg.online.web.vo.WechatVo;

/**
 * @Author Fudong.Sun【】
 * @Date 2016/11/8 20:03
 */
@RestController
@RequestMapping(value = "/web")
public class WechatController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderPayService orderPayService;
	@Autowired
	private WechatService wechatService;
	@Autowired
	private RewardService rewardService;

	@Autowired
	private WxcpPayFlowService wxcpPayFlowService;

	@Autowired
	private CacheService cacheService;
	@Autowired
    private UserCoinService userCoinService;

	@Value("${online.web.url}")
	private String weburl;
	
    @Value("${rate}")
    private int rate;
    @Value("${minimum_amount}")
    private Double minimumAmount;
    
	/**
	 * 微信支付统一下单，获取二维码
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/weixin_pay_unifiedorder/{orderId}")
	public ModelAndView weixin_pay_unifiedorder(HttpServletRequest req,@PathVariable String orderId) {
		ModelAndView m = new ModelAndView("ScanCodePay");
		if (req.getParameter("preurl") != null) {
			m.addObject("preurl", req.getParameter("preurl"));
		}
		try {

			String newOrderNo=TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
			//根据orderId 修改订单号
			orderService.updateOrderNo(newOrderNo,orderId);

			Map<String, Object> payInfo = orderService.checkPayInfo(newOrderNo);
			
			//错误信息
			if (payInfo.get("error_msg") != null) {
				m.addObject("errorMsg", (payInfo.get("error_msg").toString()));
				return m;
			}
			
			double d = Double.valueOf(payInfo.get("actual_pay").toString()) * 100;
			String body = payInfo.get("course_name").toString();
			if (body != null && body.contains(",")) {
				body = body.split(",")[0] + "等";
			}
			OrderParamVo orderParamVo=new OrderParamVo();
			orderParamVo.setUserId(payInfo.get("user_id").toString());
			orderParamVo.setSubject("购买课程【"+payInfo.get("course_name").toString()+"】");
			String attach= JSONObject.toJSON(orderParamVo).toString();

			String cacheKey=UUID.randomUUID().toString().replaceAll("-","");
			String extDatas ="order&"+cacheKey;

			cacheService.set(cacheKey,attach,7200);


			Map<String, Object> msg = orderService.addWeixinPayUnifiedorder(body,
					newOrderNo, payInfo.get("course_id").toString(), (int) d,extDatas);
			if (!msg.containsKey("errorMsg")) {
				m.addObject("codeimg", msg.get("codeimg"));
				m.addObject("orderNo", newOrderNo);
				m.addObject("couseName", payInfo.get("course_name"));
				String ap = payInfo.get("actual_pay").toString();
				if (ap.indexOf(".") >= 0 && ap.substring(ap.lastIndexOf(".")).length() < 3) {
					ap = ap + "0";
				}
				m.addObject("price", ap);
				return m;
			} else {
				m.addObject("errorMsg", (msg.get("errorMsg")));
			}

		} catch (Exception e) {
			m.addObject("errorMsg", "出现错误，" + e.getMessage());
			logger.error("微信统一下单出现错误", e);
		}
		return m;
	}

	/**
	 * 微信支付 打赏，获取二维码
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/weixin_pay_unifiedorder/reward")
	public ResponseObject reward(HttpServletRequest req) {
		try {
			OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(req);

			String rewardId = req.getParameter("rewardId").toString();
			double d = Double.valueOf(req.getParameter("actualPay").toString()) * 100;

			RewardParamVo rewardParamVo=new RewardParamVo();
			rewardParamVo.setUserId(loginUser.getId());
			rewardParamVo.setClientType(OrderFrom.PC.getCode()+"");
			rewardParamVo.setLiveId(req.getParameter("liveId"));
			rewardParamVo.setGiver(loginUser.getId());
			rewardParamVo.setReceiver(req.getParameter("receiver"));
			rewardParamVo.setUserId(loginUser.getId());
			rewardParamVo.setPrice(d/100);
			rewardParamVo.setSubject("打赏");
			
			if(d/100<0.01){
				throw new RuntimeException("打赏金额必须大于0.01");
			}
			Reward reward=rewardService.findRewardById(rewardId);
	        if(reward==null){
	        	throw new RuntimeException("无此打赏类型");
	        }else if(!reward.getIsFreeDom()&&reward.getPrice().doubleValue()!=Double.valueOf(d/100).doubleValue()){
	        	throw new RuntimeException("打赏类型与金额不符");
	        }

			rewardParamVo.setRewardId(rewardId+"");

			String attach= JSONObject.toJSON(rewardParamVo).toString();
			String body="打赏";
			String cacheKey=UUID.randomUUID().toString().replaceAll("-","");
			String extDatas ="reward&"+cacheKey;

			cacheService.set(cacheKey,attach,7200);

			String orderNo=TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
			Map<String, Object> msg = orderService.addWeixinPayUnifiedorder(body,
					orderNo, UUID.randomUUID().toString().replaceAll("-",""), (int) d,extDatas);
			if (!msg.containsKey("errorMsg")) {
				return ResponseObject.newSuccessResponseObject(msg.get("codeimg"));
			} else {
				return ResponseObject.newErrorResponseObject("获取微信支付二维码失败");
			}

		} catch (Exception e) {
			logger.error("微信统一下单出现错误", e);
		}
		return ResponseObject.newErrorResponseObject("获取微信支付二维码失败");
	}
	
	/**
	 * 微信支付 充值，获取二维码
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/weixin_pay_unifiedorder/recharge/{price}/{orderNo}")
	public ModelAndView weixin_pay_unifiedorder(HttpServletRequest req,@PathVariable String price,@PathVariable String orderNo) {
		ModelAndView m = new ModelAndView("ScanCodePay");
		m.addObject("recharge", "recharge");
		try {
			double d = Double.valueOf(price) * 100;
			Double count = Double.valueOf(price)*rate; 
			OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(req);
			
			if(!WebUtil.isIntegerForDouble(count)){
				throw new RuntimeException("充值金额"+price+"兑换的熊猫币"+count+"不为整数");
			}
			if(minimumAmount > Double.valueOf(price)){
				throw new RuntimeException("充值金额低于最低充值金额："+minimumAmount);
			}
			
			UserCoinIncrease uci = new  UserCoinIncrease();
	    	uci.setPayType(Payment.WECHATPAY.getCode());
	    	uci.setChangeType(IncreaseChangeType.RECHARGE.getCode());
	    	uci.setValue(new BigDecimal(count));
	    	uci.setUserId(loginUser.getId());
	    	
			String body="充值熊猫币:"+count.intValue()+"个";
			uci.setSubject(body);
			String cacheKey=UUID.randomUUID().toString().replaceAll("-","");
			String extDatas ="recharge&"+cacheKey;
			String attach= JSONObject.toJSON(uci).toString();
			
			cacheService.set(cacheKey,attach,7200);

			Map<String, Object> msg = orderService.addWeixinPayUnifiedorder(body,
					orderNo, UUID.randomUUID().toString().replaceAll("-",""), (int) d,extDatas);
			if (!msg.containsKey("errorMsg")) {
				m.addObject("codeimg", msg.get("codeimg"));
				m.addObject("orderNo", orderNo);
				m.addObject("couseName", body);
				if (price.indexOf(".") >= 0 && price.substring(price.lastIndexOf(".")).length() < 3) {
					price = price + "0";
				}
				m.addObject("price", price);
				return m;
			} else {
				m.addObject("errorMsg", (msg.get("errorMsg")));
			}
		} catch (Exception e) {
			logger.error("微信统一下单出现错误", e);
		}
		return m;
	}

	/**
	 * 微信支付回调接口
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/weixin_pay_notify")
	public void weixin_pay_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getContextPath();
		String basePath =weburl;
		long current = System.currentTimeMillis();
		String orderNo = null;
		// 读取参数
		String resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
		InputStream inputStream = request.getInputStream();
		StringBuffer sb = new StringBuffer();
		String s = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		inputStream.close();
		
		// 解析xml成map
		Map<String, String> m = XMLUtil.doXMLParse(sb.toString());
		if (m != null && !m.isEmpty()) {
			// 过滤空 设置 TreeMap
			SortedMap<Object, Object> packageParams = new TreeMap<>();
			for (Map.Entry<String, String> e : m.entrySet()) {
				if (StringUtils.hasText(e.getValue()) && StringUtils.hasText(e.getKey())) {
					packageParams.put(e.getKey(), e.getValue());
				}
			}
			orderNo = String.valueOf(packageParams.get("out_trade_no"));
			if (packageParams.isEmpty()) {
				logger.info("参数为空，恶意调用！！！");
			} else if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, OnlineConfig.API_KEY)) {
				// 处理业务开始
				if ("SUCCESS".equals(packageParams.get("result_code"))) {
					String out_trade_no = String.valueOf(packageParams.get("out_trade_no"));
					String transaction_id = String.valueOf(packageParams.get("transaction_id"));
					String flow_id = UUID.randomUUID().toString().replace("-", "");
					String appid = String.valueOf(packageParams.get("appid")); // 应用ID
					String attach = String.valueOf(packageParams.get("attach")); // 商户数据包
					String bank_type = String.valueOf(packageParams.get("bank_type"));// 付款银行
					String fee_type = String.valueOf(packageParams.get("fee_type")); // fee_type
					String is_subscribe = String.valueOf(packageParams.get("is_subscribe"));// 是否关注公众账号
					String mch_id = String.valueOf(packageParams.get("mch_id")); // 商户号
					String nonce_str = String.valueOf(packageParams.get("nonce_str"));// 随机字符串
					String openid = String.valueOf(packageParams.get("openid"));
					String result_code = String.valueOf(packageParams.get("result_code"));// 业务结果
					String return_code = String.valueOf(packageParams.get("return_code"));
					String sign = String.valueOf(packageParams.get("sign"));
					String sub_mch_id = String.valueOf(packageParams.get("sub_mch_id"));
					String time_end = String.valueOf(packageParams.get("time_end")); // 支付完成时间
					String total_fee = String.valueOf(packageParams.get("total_fee")); // 总金额
					String trade_type = String.valueOf(packageParams.get("trade_type"));// 交易类型
					String return_msg = String.valueOf(packageParams.get("return_msg"));
					String payment_type = "WeChat";
					WxcpPayFlow wxcpPayFlow = new WxcpPayFlow();
					wxcpPayFlow.setFlow_id(flow_id);
					wxcpPayFlow.setAppid(appid);
					wxcpPayFlow.setAttach(attach);
					//wxcpPayFlow.setUser_id(((String) com.alibaba.fastjson.JSONObject.parseObject(attach).get("userId")));
					wxcpPayFlow.setBank_type(bank_type);
					wxcpPayFlow.setFee_type(fee_type);
					wxcpPayFlow.setIs_subscribe(is_subscribe);
					wxcpPayFlow.setMch_id(mch_id);
					wxcpPayFlow.setNonce_str(nonce_str);
					wxcpPayFlow.setOpenid(openid);
					wxcpPayFlow.setOut_trade_no(out_trade_no);
					wxcpPayFlow.setResult_code(result_code);
					wxcpPayFlow.setReturn_code(return_code);
					wxcpPayFlow.setSign(sign);
					wxcpPayFlow.setSub_mch_id(sub_mch_id);
					wxcpPayFlow.setTime_end(time_end);
					wxcpPayFlow.setTotal_fee(total_fee);
					wxcpPayFlow.setTrade_type(trade_type);
					wxcpPayFlow.setTransaction_id(transaction_id);
					wxcpPayFlow.setReturn_msg(return_msg);
					wxcpPayFlow.setPayment_type(payment_type);
					//wxcpPayFlow.setSubject(((String) com.alibaba.fastjson.JSONObject.parseObject(attach).get("subject")));


					String[] attachs=attach.split("&");

					if(attachs.length>0) {
                        if ("order".equals(attachs[0])) {
                            String json = cacheService.get(attachs[1]);
                            OrderParamVo orderParamVo = JSONObject.parseObject(json, OrderParamVo.class);
                            wxcpPayFlow.setSubject(orderParamVo.getSubject());
                            wxcpPayFlow.setUser_id(orderParamVo.getUserId());
                        } else if ("reward".equals(attachs[0])) {
                            String json = cacheService.get(attachs[1]);
                            RewardParamVo rpv = JSONObject.parseObject(json, RewardParamVo.class);
                            RewardStatement rs = new RewardStatement();
                            BeanUtils.copyProperties(rs, rpv);
                            rs.setCreateTime(new Date());
                            rs.setPayType(Payment.WECHATPAY.getCode());
                            rs.setOrderNo(out_trade_no);
                            rs.setChannel(OrderFrom.PC.getCode());
                            rs.setStatus(1);
                            userCoinService.updateBalanceForReward(rs);
                            wxcpPayFlow.setUser_id(rpv.getUserId());
                            wxcpPayFlow.setSubject(rpv.getSubject());
                        } else if ("recharge".equals(attachs[0])) {
                            String json = cacheService.get(attachs[1]);
                            UserCoinIncrease uci = JSONObject.parseObject(json, UserCoinIncrease.class);
//                            uci.setCreateTime(new Date());
//                            uci.setPayType(Payment.WECHATPAY.getCode());
//                            uci.setOrderNoRecharge(out_trade_no);
//                            uci.setOrderFrom(OrderFrom.PC.getCode());
//                            uci.setBalanceType(BalanceType.BALANCE.getCode());
                            userCoinService.updateBalanceForRecharge(uci.getUserId(),Payment.WECHATPAY,uci.getValue(), OrderFrom.PC,out_trade_no);
                            wxcpPayFlow.setUser_id(uci.getUserId());
                            wxcpPayFlow.setSubject(uci.getSubject());
                        }
                    }
                    try{
						wxcpPayFlowService.insert(wxcpPayFlow);
					}catch (Exception exception){
						logger.error("以下订单重复插入:"+wxcpPayFlow.toString());
					}


					if("order".equals(attachs[0])) {
					//新启线程，处理支付成功
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								//计时
								long current = System.currentTimeMillis();
								//处理订单业务
								orderPayService.addPaySuccess(out_trade_no, Payment.WECHATPAY,transaction_id);
//								orderService.addPaySuccess(out_trade_no,1,transaction_id);
								logger.info("订单支付成功，订单号:{},用时{}",
										out_trade_no, (System.currentTimeMillis() - current) + "毫秒");
								//为购买用户发送购买成功的消息通知
//								orderService.savePurchaseNotice(basePath, out_trade_no);
							} catch (Exception e) {
								logger.error("用户支付成功，构建课程失败！！！"+out_trade_no+"，错误信息：",e);
							}
						}
					}).start();
					}
				} else {
					logger.error("用户支付失败！！！错误信息：" + packageParams.get("err_code"));
				}
			} else {
				logger.error("签名验证失败，有可能是恶意调用！！！");
			}
		}
		
		// 处理业务完毕
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();
		
		long end = System.currentTimeMillis();
		logger.info("微信回调"+orderNo+"，用时"+(end - current)+"毫秒");
	}
	
	/**
	 * 微信分销系统支付成功回调
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/pay_notify_wechat")
	public ResponseObject pay_notify_wechat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 读取参数
			InputStream inputStream = request.getInputStream();
			StringBuffer sb = new StringBuffer();
			String str = null;
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
			in.close();
			inputStream.close();
			
			// 解析xml成map
			Map<String, String> m = XMLUtil.doXMLParse(sb.toString());
			if (m != null && !m.isEmpty()) {
				// 过滤空 设置 TreeMap
				SortedMap<Object, Object> packageParams = new TreeMap<>();
				for (Map.Entry<String, String> e : m.entrySet()) {
					if (StringUtils.hasText(e.getValue()) && StringUtils.hasText(e.getKey())) {
						packageParams.put(e.getKey(), e.getValue());
					}
				}
				if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, OnlineConfig.WECHAT_API_KEY)) {
					String out_trade_no = String.valueOf(packageParams.get("out_trade_no"));
					String transaction_id = String.valueOf(packageParams.get("transaction_id"));
					//新启线程，处理支付成功
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								//计时
								long current = System.currentTimeMillis();
								//处理订单业务
								orderPayService.addPaySuccess(out_trade_no,Payment.WECHATPAY,transaction_id);
//								orderService.addPaySuccess(out_trade_no,1,transaction_id);
								logger.info("订单支付成功，订单号:{},用时{}",
										out_trade_no, (System.currentTimeMillis() - current) + "毫秒");
								//为购买用户发送购买成功的消息通知
//								orderService.savePurchaseNotice(weburl, out_trade_no);
							} catch (Exception e) {
								logger.error("用户支付成功，构建课程失败！！！"+out_trade_no+"，错误信息：",e);
							}
						}
					}).start();
				} else {
					logger.error("微信分销系统回调接口，签名验证失败，有可能是恶意调用！！！");
				}
			}
			return ResponseObject.newSuccessResponseObject("支付成功！");
		} catch (Exception e) {
			return ResponseObject.newSuccessResponseObject(e.getMessage());
		}
	}

	/**
	 * IOS内购成功后回调
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/pay_notify_iosiap")
	public ResponseObject pay_notify_iosiap(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 读取参数
			InputStream inputStream = request.getInputStream();
			StringBuffer sb = new StringBuffer();
			String str = null;
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
			in.close();
			inputStream.close();
			
			// 解析xml成map
			Map<String, String> m = XMLUtil.doXMLParse(sb.toString());
			
			if (m != null && !m.isEmpty()) {
				// 过滤空 设置 TreeMap
				SortedMap<Object, Object> packageParams = new TreeMap<>();
				for (Map.Entry<String, String> e : m.entrySet()) {
					if (StringUtils.hasText(e.getValue()) && StringUtils.hasText(e.getKey())) {
						packageParams.put(e.getKey(), e.getValue());
					}
				}
				if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, OnlineConfig.WECHAT_API_KEY)) {

					String out_trade_no = String.valueOf(packageParams.get("out_trade_no"));
					String transaction_id = String.valueOf(packageParams.get("transaction_id"));
					//新启线程，处理支付成功
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								
								//计时
								long current = System.currentTimeMillis();
								//处理订单业务
								orderPayService.addPaySuccess(out_trade_no,Payment.WECHATPAY,transaction_id);
//								orderService.addPaySuccess(out_trade_no,1,transaction_id);
								logger.info("订单支付成功，订单号:{},用时{}",
										out_trade_no, (System.currentTimeMillis() - current) + "毫秒");

							} catch (Exception e) {
								logger.error("用户支付成功，构建课程失败！！！"+out_trade_no+"，错误信息：",e);
							}
						}
					}).start();
				} else {
					logger.error("微信分销系统回调接口，签名验证失败，有可能是恶意调用！！！");
				}
			}
			return ResponseObject.newSuccessResponseObject("支付成功！");
		} catch (Exception e) {
			return ResponseObject.newSuccessResponseObject(e.getMessage());
		}
	}
	
	
	
	@RequestMapping(value = "/wechatDistribution", method = RequestMethod.GET)
	public ResponseObject wechatDistribution(WechatVo wechatVo) {
		return ResponseObject.newSuccessResponseObject(wechatService.saveUserAndBuyCourse(wechatVo));
	}
}
