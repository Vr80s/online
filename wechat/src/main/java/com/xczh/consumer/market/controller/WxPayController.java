package com.xczh.consumer.market.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xczh.consumer.market.bean.OnlineOrder;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.bean.WxcpPayFlow;
import com.xczh.consumer.market.bean.WxcpWxTrans;
import com.xczh.consumer.market.dao.OnlineOrderMapper;
import com.xczh.consumer.market.dao.OnlineUserMapper;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.service.OnlineOrderService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.RewardService;
import com.xczh.consumer.market.service.WxcpClientUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.service.WxcpOrderGoodsService;
import com.xczh.consumer.market.service.WxcpOrderInfoService;
import com.xczh.consumer.market.service.WxcpOrderPayService;
import com.xczh.consumer.market.service.WxcpPayFlowService;
import com.xczh.consumer.market.service.WxcpWxRedpackService;
import com.xczh.consumer.market.service.WxcpWxTransService;
import com.xczh.consumer.market.service.iphoneIpaService;
import com.xczh.consumer.market.utils.ClientUserUtil;
import com.xczh.consumer.market.utils.ConfigUtil;
import com.xczh.consumer.market.utils.GenerateSequenceUtil;
import com.xczh.consumer.market.utils.HttpUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.Token;
import com.xczh.consumer.market.utils.UCCookieUtil;
import com.xczh.consumer.market.vo.CodeUtil;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczh.consumer.market.vo.OrderParamVo;
import com.xczh.consumer.market.vo.RechargeParamVo;
import com.xczh.consumer.market.vo.RewardParamVo;
import com.xczh.consumer.market.wxpay.PayFactory;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.entity.PayInfo;
import com.xczh.consumer.market.wxpay.entity.SendRedPack;
import com.xczh.consumer.market.wxpay.util.CommonUtil;
import com.xczh.consumer.market.wxpay.util.MD5SignUtil;
import com.xczhihui.bxg.online.api.po.RewardStatement;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
import com.xczhihui.bxg.online.api.service.CityService;
import com.xczhihui.bxg.online.api.service.EnchashmentService;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.TokenExpires;

@Controller
@RequestMapping("/bxg/wxpay")
public class WxPayController {
	@Autowired
	private WxcpOrderGoodsService wxcpOrderGoodsService;
	@Autowired
	private WxcpOrderInfoService wxcpOrderInfoService;
	@Autowired
	private WxcpOrderPayService wxcpOrderPayService;
	@Autowired
	private WxcpClientUserService wxcpClientUserService;
	@Autowired
	private WxcpClientUserWxMappingService wxcpClientUserWxMappingService;
	@Autowired
	private WxcpPayFlowService wxcpPayFlowService;
	@Autowired
	private WxcpWxRedpackService wxcpWxRedpackService;
	@Autowired
	private WxcpWxTransService wxcpWxTransService;
	@Autowired
	private OnlineOrderService onlineOrderService;
	@Autowired
	private OnlineUserService onlineUserService;
	@Autowired
	private OnlineOrderMapper orderMapper;
	@Autowired
	private RewardService rewardService;
	@Autowired
	private WxcpClientUserWxMappingService wxService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private OnlineCourseService onlineCourseService;
	@Autowired
	private CityService cityService;

	@Autowired
	private UserCoinService userCoinService;
	
	@Autowired
	private EnchashmentService enchashmentService;

    @Autowired
    private AppBrowserService appBrowserService;
	
	@Value("${online.weburl}")
	private String pcUrl;

	@Value("${onlinekey}")
	private String onlinekey;

	@Value("${rate}")
	private int rate;
	@Value("${minimum_amount}")
	private Double minimumAmount;
	
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(H5AppPayController.class);
	
	
	private final String tell_ok = "success";
	private final String tell_failer = "signError";
	private final String key_sign = "sign";

	@Autowired
	private UserCenterAPI userCenterAPI;
	@Autowired
	private OnlineUserMapper onlineUserMapper;
	
	@Autowired
	private iphoneIpaService iIpaService;
	
	/**
	 * 订单支付
	 * 
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "h5Prepay")
	@ResponseBody
	public ResponseObject OnlineOrderPay(HttpServletRequest req,
										 HttpServletResponse res, Map<String, String> params)
			throws Exception {
		Map<String, String> retobj = new HashMap<String, String>();
		String orderNo = req.getParameter("orderNo");
		String userId = req.getParameter("userId");
		if (null == orderNo || null == userId) {
			return ResponseObject.newErrorResponseObject("参数异常");
		}
		// 通过订单号得到订单信息
		OnlineOrder onlineOrder = onlineOrderService
				.getOnlineOrderByOrderNo(orderNo);
		retobj.put("ok", "false");
		if (null == onlineOrder) {
			return ResponseObject.newSuccessResponseObject(retobj);
		}
		// 订单号 支付的钱
		String orderNo2 = onlineOrder.getOrderNo();
		Double actualPay = onlineOrder.getActualPay() * 100;
		int price = actualPay.intValue();
		String extDatas = "";
		WxcpClientUserWxMapping wxUser = wxService
				.getWxcpClientUserWxMappingByUserId(userId);
		Map<String, String> retpay = PayFactory.work().getPrePayInfos(orderNo2,
				price, WxPayConst.appid4name, extDatas, wxUser.getOpenid());
		if (retpay != null) {
			retpay.put("ok", "true");
			JSONObject jsonObject = JSONObject.fromObject(retpay);
			log.info("h5Prepay->jsonObject->\r\n\t"
					+ jsonObject.toString());
			return ResponseObject.newSuccessResponseObject(retpay);
		}
		log.info("h5Prepay->retobj->\r\n\t" + retobj.toString());
		return ResponseObject.newSuccessResponseObject(retobj);
	}
	/**
	 * Description：微信通知 异步
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("wxNotify")
	@ResponseBody
	@Transactional
	public ResponseObject wxNotify(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		try {

			log.info("进入回调函数方法中啦");

			req.setCharacterEncoding("utf-8");
			ServletInputStream is = req.getInputStream();
			String content = IOUtils.toString(is);
			if (content == null)
				content = "";
			content = content.trim();
			System.out
					.println("wxNotify->content->\r\n\t" + content.toString());// log.info("wxNotify=\r\n"
																				// +
																				// content.toString());
			Map<String, String> map = CommonUtil.parseXml(content);
			log.info("wxNotify->map->\r\n\t"
					+ JSONObject.fromObject(map).toString());// log.info("map:"
																// +
																// map.toString());
			String flow_id = UUID.randomUUID().toString().replace("-", "");
			String appid = map.get("appid"); // 应用ID
			String attach = map.get("attach"); // 商户数据包
			String bank_type = map.get("bank_type");// 付款银行
			String fee_type = map.get("fee_type"); // fee_type
			String is_subscribe = map.get("is_subscribe");// 是否关注公众账号
			String mch_id = map.get("mch_id"); // 商户号
			String nonce_str = map.get("nonce_str");// 随机字符串
			String openid = map.get("openid");
			String out_trade_no = map.get("out_trade_no");// 商户订单号
			String result_code = map.get("result_code");// 业务结果
			String return_code = map.get("return_code");
			String sign = map.get("sign");
			String sub_mch_id = map.get("sub_mch_id");
			String time_end = map.get("time_end"); // 支付完成时间
			
			log.info("time_end:"+time_end);
			
			String total_fee = map.get("total_fee"); // 总金额
			String trade_type = map.get("trade_type");// 交易类型
			String transaction_id = map.get("transaction_id");// 微信支付订单号
			String return_msg = map.get("return_msg");
			String payment_type = "WeChat";
			WxcpPayFlow wxcpPayFlow = new WxcpPayFlow();
			wxcpPayFlow.setFlow_id(flow_id);
			wxcpPayFlow.setAppid(appid);
			wxcpPayFlow.setAttach(attach);
			log.info("微信返回数据包："+attach);
			//wxcpPayFlow.setUser_id((com.alibaba.fastjson.JSONObject.parseObject(attach).get("userId").toString()));
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


			WxcpPayFlow condPayFlow = new WxcpPayFlow();
			condPayFlow.setOut_trade_no(out_trade_no);// 商户订单号

			List<WxcpPayFlow> listWxcpPayFlow = wxcpPayFlowService
					.select(condPayFlow);
			int paysed_sum = 0;
			if (listWxcpPayFlow != null && listWxcpPayFlow.size() > 0) {
				for (int i = 0; i < listWxcpPayFlow.size(); i++) {
					if (listWxcpPayFlow.get(i).getResult_code() == null
							|| listWxcpPayFlow.get(i).getResult_code().trim()
									.length() == 0
							|| listWxcpPayFlow.get(i).getReturn_code() == null
							|| listWxcpPayFlow.get(i).getReturn_code().trim()
									.length() == 0)
						continue;
					if (listWxcpPayFlow.get(i).getResult_code()
							.equals("SUCCESS")
							&& listWxcpPayFlow.get(i).getReturn_code()
									.equals("SUCCESS")) {
						try {
							paysed_sum = Integer.valueOf(listWxcpPayFlow.get(i)
									.getTotal_fee());
						} catch (Exception e) {
							System.out
									.println("WxPayController->wxNotify->paysed_sum->\r\n\t"
											+ e.getMessage());
						}
						break;
					}
				}
			}
			/** 放到在线更新 **/
			String key ="";
			if(PayInfo.trade_type_app.equals(trade_type)){
				key = WxPayConst.app_ApiKey;
			}else{
				key = WxPayConst.gzh_ApiKey;
			}
			if (MD5SignUtil.VerifySignature(map, map.get(key_sign),key)) {
				
				log.info("回调后签名对比是否成功");
				//打赏支付
				if(StringUtils.isNotBlank(wxcpPayFlow.getAttach())){
					String[] attachs=attach.split("&");

					//String ppbt= (String) com.alibaba.fastjson.JSONObject.parseObject(attach).get("passbackParamBizType");
					//if(ppbt.equals("1")) {
					//RewardParamVo rpv= com.alibaba.fastjson.JSONObject.parseObject(wxcpPayFlow.getAttach(),RewardParamVo.class);
					//if("1".equals(rpv.getT())){

					if(attachs.length>0)
					if(attachs[0].equals("reward")){

					    String json=cacheService.get(attachs[1]);
						RewardParamVo rpv= com.alibaba.fastjson.JSONObject.parseObject(json,RewardParamVo.class);
						RewardStatement rs=new RewardStatement();
						BeanUtils.copyProperties(rs,rpv);
						rs.setCreateTime(new Date());
						rs.setPayType(1);//
						rs.setOrderNo(out_trade_no);
						rs.setPrice((new Double(wxcpPayFlow.getTotal_fee())/100));
						rs.setChannel(1);
						rs.setStatus(1);
						//rewardService.insert(rs);
						wxcpPayFlow.setUser_id(rpv.getUserId());
						wxcpPayFlow.setSubject(rpv.getSubject());
						wxcpPayFlowService.insert(wxcpPayFlow);
						userCoinService.updateBalanceForReward(rs);
						res.getWriter().write(tell_ok);
						log.info("打赏回调打印:"+json);
				//	}
					}else if(attachs[0].equals("order")){
						String json=cacheService.get(attachs[1]);
						OrderParamVo rpv= com.alibaba.fastjson.JSONObject.parseObject(json,OrderParamVo.class);
						wxcpPayFlow.setUser_id(rpv.getUserId());
						log.info("order回调打印:"+json);
						wxcpPayFlow.setSubject(rpv.getSubject());
						wxcpPayFlowService.insert(wxcpPayFlow);
					}else if(attachs[0].equals("recharge")){
						String json=cacheService.get(attachs[1]);
						RechargeParamVo rpv= com.alibaba.fastjson.JSONObject.parseObject(json,RechargeParamVo.class);
						wxcpPayFlow.setUser_id(rpv.getUserId());
						log.info("充值回调打印:"+json);
						wxcpPayFlow.setSubject(rpv.getSubject());
						wxcpPayFlowService.insert(wxcpPayFlow);

						UserCoinIncrease userCoinIncrease=new UserCoinIncrease();
						userCoinIncrease.setUserId(wxcpPayFlow.getUser_id());
						userCoinIncrease.setChangeType(1);
						userCoinIncrease.setPayType(1);
						userCoinIncrease.setValue(new BigDecimal(new Double(wxcpPayFlow.getTotal_fee())/100*rate));//熊猫币
						userCoinIncrease.setCreateTime(new Date());
//						userCoinIncrease.setChangeType(0);
						userCoinIncrease.setOrderFrom(Integer.valueOf(rpv.getClientType()));
						userCoinIncrease.setOrderNoRecharge(wxcpPayFlow.getOut_trade_no());
						userCoinService.updateBalanceForIncrease(userCoinIncrease);
					}
				}

				log.info("WxPayController->wxNotify->sign success");
				if (map != null
						&& WxPayConst.recode_success.equals(map
								.get(WxPayConst.return_code))) {
					log.info("WxPayController->wxNotify->good");
				} else {
					log.info("WxPayController->wxNotify->bad");
				}
				if(attach.split("&")[0].equals("order")){
					
					boolean onlinePaySuccess = httpOnline(out_trade_no, transaction_id);//普通订单
				
				}

				res.getWriter().write(tell_ok);
			} else {
				log.info("wxNotify->sign failure");
				res.getWriter().write(tell_failer);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 */
	@RequestMapping("appleIapPayOrder")
	@ResponseBody
	@Transactional
	public ResponseObject appleInternalPurchaseOrder(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		
		try {
			log.info("======================================");
			/*
			 * 传递过来一个订单号
			 */
			String order_no = req.getParameter("order_no");
			//String courderName = req.getParameter("courderName");
			log.info("======================================"+order_no);
			
			ResponseObject orderDetails = onlineOrderService.getOrderAndCourseInfoByOrderNo(order_no);
    		if(null == orderDetails.getResultObject()){
    			return ResponseObject.newErrorResponseObject("未找到订单信息");
    		}
			OnlineOrder order  = (OnlineOrder) orderDetails.getResultObject();
    		Double actualPrice = order.getActualPay();//订单金额
    		double  xmb = actualPrice * rate;
    		
    		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
    		if(user == null) {
    	         return ResponseObject.newErrorResponseObject("登录超时！");
    	    }
    		String userYE =  enchashmentService.enableEnchashmentBalance(user.getId());
    		double d = Double.valueOf(userYE);
    		log.info("要消费余额:"+xmb);
    		log.info("当前用户余额:"+d);
    		if(xmb>d){
    			return ResponseObject.newErrorResponseObject("余额不足,请到个人账户充值！");
			}
			/**
			 * 然后你那边加下密
			 */
			//得到更多的参数。然后
			String transaction_id = CodeUtil.getRandomUUID();
			
			String s = "out_trade_no=" + order_no + "&result_code=SUCCESS"
					+ "&transaction_id="+transaction_id+"&key=" + onlinekey;
			
			String mysign = CodeUtil.MD5Encode(s).toLowerCase();
			String resXml = "<xml>" + "<out_trade_no><![CDATA[" + order_no
					+ "]]></out_trade_no>"
					+ "<result_code><![CDATA[SUCCESS]]></result_code>"
					+ "<transaction_id>"+transaction_id+"<![CDATA[]]></transaction_id>"
					+ "<sign><![CDATA[" + mysign
					+ "]]></sign>" + " </xml> ";
			
			log.info("请求web端的  ios   内购成功回调  pay_notify_iosiap");
			String msg = HttpUtil.sendDataRequest(
					pcUrl  + "/web/pay_notify_iosiap", "application/xml", resXml
							.toString().getBytes());
			
			log.info("msg  >>>  " + msg);
			Gson g = new GsonBuilder().create();
			Map<String, Object> mp = g.fromJson(msg, Map.class);
			boolean falg =  Boolean.valueOf(mp.get("success").toString());
	        if(falg){
	        	/**
	    		 * 获取订单详情
	    		 */
	    		String courderName ="";
	    		if(order.getAllCourse().size()>0){
	    			courderName =order.getAllCourse().get(0).getGradeName();
	    		}
	    		/**
	    		 * 记录下ios支付成功后的记录
	    		 */
	    		ResponseObject finalResult = iIpaService.iapOrder(order.getUserId(), xmb, order_no, actualPrice+"",courderName);
	    		return finalResult;
	        }else{
	        	return ResponseObject.newErrorResponseObject("签名有误");
	        }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("服务器有误");
		}
	}
	
	/**
	 * 调用在线接口
	 * 
	 * @param order_no
	 * @param transaction_id
	 */
	private boolean httpOnline(String order_no, String transaction_id)
			throws Exception {
		String s = "out_trade_no=" + order_no + "&result_code=SUCCESS"
				+ "&transaction_id=" + transaction_id + "&key=" + onlinekey;
		String mysign = CodeUtil.MD5Encode(s).toLowerCase();

		String resXml = "<xml>" + "<out_trade_no><![CDATA[" + order_no
				+ "]]></out_trade_no>"
				+ "<result_code><![CDATA[SUCCESS]]></result_code>"
				+ "<transaction_id><![CDATA[" + transaction_id
				+ "]]></transaction_id>" + "<sign><![CDATA[" + mysign
				+ "]]></sign>" + " </xml> ";
		log.info("请求web端的微信支付回调pay_notify_wechat");
		// 请求web端的方法
		String msg = HttpUtil.sendDataRequest(
				pcUrl + "/web/pay_notify_wechat", "application/xml", resXml
						.toString().getBytes());
		log.info("msg  >>>  " + msg);
		Gson g = new GsonBuilder().create();
		Map<String, Object> mp = g.fromJson(msg, Map.class);
		return Boolean.valueOf(mp.get("success").toString());

	}
    
	@RequestMapping("h5GetCodeid")
	public void h5GetCodeid(HttpServletRequest req, HttpServletResponse res,
			Map<String, String> params) throws Exception {

		try {
			log.info("wx return code:" + req.getParameter("code"));
			String code = req.getParameter("code");
			String openid = ClientUserUtil.setWxInfo(code, null, null,
					wxcpClientUserService, wxcpClientUserWxMappingService,
					userCenterAPI, onlineUserMapper, req, res);
			ConfigUtil cfg = new ConfigUtil(req.getSession());
			String returnCodeUri = cfg.getConfig("returnCodeUri");
			log.info("returnCodeUri=" + returnCodeUri);
			if (openid != null && !openid.isEmpty()) {
				JSONObject jsonObject = JSONObject.fromObject(openid);
				res.sendRedirect(returnCodeUri + "/bxg/page/index/"
						+ jsonObject.get("openid") + "/" + code);// res.sendRedirect(returnCodeUri+"?code="+code);
			} else
				// res.getWriter().write(openid);
				res.sendRedirect(returnCodeUri + "/bxg/page/index/" + openid
						+ "/" + code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description：通过code获取用户openid
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("h5BsGetCodeUrlAndBase")
	public void h5BsGetCodeUrlAndBase(HttpServletRequest req, HttpServletResponse res,
			Map<String, String> params) throws Exception {

		String code = req.getParameter("code");//微信code
		/**
		 * 额外的参数
		 */
		String share = req.getParameter("share");
		String courseid = req.getParameter("courseid");
		String shareCourseId = req.getParameter("shareCourseId");
		
		log.info("code:" + code);
		log.info("share:" + share);
		log.info("courseid:" + courseid);
		log.info("shareCourseId:" + shareCourseId);
		
		ConfigUtil cfg = new ConfigUtil(req.getSession());
		String returnOpenidUri = cfg.getConfig("returnOpenidUri");
		try {
			String code_buffer = CommonUtil.getOpenId(code);
			JSONObject jsonObject = JSONObject.fromObject(code_buffer);//Map<String, Object> access_info =GsonUtils.fromJson(code_buffer, Map.class);
			String openid = (String)jsonObject.get("openid");
         
			log.info("openid:" + openid);
			
			if(StringUtils.isNotBlank(share) && share == "liveDetails"){  //liveDetails bunchDetails
				res.sendRedirect("/bxg/xcpage/courseDetails?courseId="+courseid);
			}else if(StringUtils.isNotBlank(share) && share == "bunchDetails"){  //xcviews/html/particulars.html?courseId=299
				res.sendRedirect("/xcviews/html/particulars.html?courseId=" + shareCourseId);
			}else if(StringUtils.isNotBlank(share) && share == "foresshow"){
				res.sendRedirect("/xcviews/html/foreshow.html?course_id=" + shareCourseId);
			}else{
				res.sendRedirect("/bxg/page/index/" + openid + "/null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.sendRedirect(returnOpenidUri + "/bxg/page/login/1?error=error");
		}
	}
	
	/**
	 * 点击在线课堂
	 * 微信授权获取用户信息后的回调
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("h5GetOpenid")
	public void h5GetOpenid(HttpServletRequest req, HttpServletResponse res,
			Map<String, String> params) throws Exception {
		log.info("wx return code:" + req.getParameter("code"));
		try {
			String code = req.getParameter("code");
			WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
			/*
			 * 判断这个uninonid是否在user表中存在
			 */
			OnlineUser ou =  onlineUserMapper.findOnlineUserByUnionid(wxw.getUnionid());
			String openid = wxw.getOpenid();
			/**
			 * 如果这个用户信息已经保存进去了，那么就直接登录就ok
			 */
			ConfigUtil cfg = new ConfigUtil(req.getSession());
			String returnOpenidUri = cfg.getConfig("returnOpenidUri");
			if(ou != null){
			    ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
				Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
				ou.setTicket(t.getTicket());
				onlogin(req,res,t,ou,t.getTicket());
				if (openid != null && !openid.isEmpty()) {
					res.sendRedirect(returnOpenidUri + "/bxg/page/index/"+ openid + "/" + code);
				} else{
					res.getWriter().write(openid);
				}	
			}else{
				//否则跳转到这是页面。绑定下手机号啦   -- 如果从个人中心进入的话，也需要绑定手机号啊，绑定过后，就留在这个页面就行。
				res.sendRedirect(returnOpenidUri + "/xcviews/html/my.html?openId="+openid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//res.getWriter().write(e.getMessage());
		}
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("h5ShareGetWxUserInfo")
	public void h5ShareGetWxUserInfo(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		log.info("wx return code:" + req.getParameter("code"));
		log.info("courseId:" + req.getParameter("courseId"));
		log.info("wxOrbrower:" + req.getParameter("wxOrbrower"));
		try {
			String courseId = req.getParameter("courseId");
			String code = req.getParameter("code");
			String wxOrbrower = req.getParameter("wxOrbrower");
			OnlineUser ou =null;
			String openid ="";
			//判断是微信浏览器还是普通浏览器
			if(!StringUtils.isNotBlank(wxOrbrower)){
				WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
				openid = wxw.getOpenid();
				/*
				 * 判断这个uninonid是否在user表中存在
				 */
				ou =  onlineUserMapper.findOnlineUserByUnionid(wxw.getUnionid());
				if(ou!=null){
					OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
					if(user == null){ //直接跳转到分享页面
						//这里不用判断用户有没有登录了。没哟登录帮他登录
					    ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
						Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
						ou.setTicket(t.getTicket());
						onlogin(req,res,t,ou,t.getTicket());
					}	
				}
			}else{
				ou =  appBrowserService.getOnlineUserByReq(req);
			}
			String url  = "/bxg/page/index/"+ openid + "/" + code;
			/**
			 * 如果这个用户信息已经保存进去了，那么就直接登录就ok
			 */
			ConfigUtil cfg = new ConfigUtil(req.getSession());
			String returnOpenidUri = cfg.getConfig("returnOpenidUri");
			//先判断这个用户是否存在
			if(ou != null){
				log.info("}}}}}}}}}}}}}}}}} ou.getUnionId():"+ou.getUnionId());
				Integer type = onlineCourseService.getIsCouseType(Integer.parseInt(courseId));
				Map<String,Object> mapCourseInfo = onlineCourseService.shareLink(Integer.parseInt(courseId), type);
				//这样直接跳转的话，怎样跳转呢，直接到直播页面啊，还是直接到
				if(type == 1){ //直播或者预约详情页           
					//1.直播中，2预告，3直播结束
					if(null != mapCourseInfo.get("lineState") && mapCourseInfo.get("lineState").toString().equals("2")){  //预告
						url = "/xcviews/html/foreshow.html?course_id="+Integer.parseInt(courseId)+"&openId="+openid;
					}else if(null != mapCourseInfo.get("lineState")){  //直播获取直播结束的
						url = "/bxg/xcpage/courseDetails?courseId="+Integer.parseInt(courseId)+"&openId="+openid;
					}
				}else{ //视频音频详情页
					//haiyao   multimediaType
					Integer multimediaType = 1;
					if(null != mapCourseInfo.get("multimediaType") && mapCourseInfo.get("multimediaType").toString().equals("2")){
						multimediaType = 2;
					}
					url = "/xcviews/html/particulars.html?courseId="+Integer.parseInt(courseId)+"&openId="+openid+"&multimedia_type="+multimediaType;
				}
				log.info("}}}}}}}}}}}}}}}}}} url："+url);
				res.sendRedirect(returnOpenidUri + url);
			}else{
				log.info("}}}}}}}}}}}}}}}}}用户不存在");
				//否则跳转到这是页面。绑定下手机号啦   -- 如果从个人中心进入的话，也需要绑定手机号啊，绑定过后，就留在这个页面就行。
				//这里跳转到分享页面啦
				res.sendRedirect(returnOpenidUri + "/xcviews/html/share.html?openid="+openid+"&course_id="+courseId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//res.getWriter().write(e.getMessage());
		}
	}
	/**
	 * 
	 * 1、需要在写一个来判断这个微信信息是否包含了手机号。
	 * 2、或者这个手机号是否和已经存在的一样
	 */
	@RequestMapping("h5GetCodeAndUserMobile")
	public void h5GetCodeAndUserMobile(HttpServletRequest req, HttpServletResponse res,
			Map<String, String> params) throws Exception {

		ConfigUtil cfg = new ConfigUtil(req.getSession());
		String returnOpenidUri = cfg.getConfig("returnOpenidUri");
		/**
		 * 微信回调后，获取微信信息。
		 */
		String userName = req.getParameter("userName");
		log.info("wx return code:" + req.getParameter("code"));
		String code = req.getParameter("code");
		/*
		 * 获取当前登录用户信息
		 */
		OnlineUser ou = onlineUserMapper.findUserByLoginName(userName);
		/*
		 * 获取当前微信信息
		 */
		WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
		/*
		 * 	如果都登录
		 * 	但是如果微信不等于null，说明绑定了手机号了。
		 *  那么判断绑定的手机号是否和当前用户的一致，如果一致就不用绑定了			   	
		 *  							如果不一致，此微信号已经判断了手机号了。  ---返回到另一个页面。
		 *  
		 *  如果wxw.getClient_id()==null，微信信息不包含用户信息。亦着反之
		 *  如果ou.getUnionId()==null，用户信息不包含微信信息。亦着反之
		 *  
		 */
		if(wxw.getClient_id()==null){  //直接绑定了
			/*
			 * 如果用户信息中的一些基本信息为null的话，可以把微信的中基本信息填充过去
			 */
			OnlineUser ouNew = new OnlineUser();
			//性别
			if(ou.getSex()==2){
				ouNew.setSex(Integer.parseInt(wxw.getSex()));
			}
			//名称
			if(StringUtils.isBlank(ou.getName())){
				ouNew.setName(wxw.getNickname());
			}
			//头像
			if(StringUtils.isBlank(ou.getSmallHeadPhoto())){
				ouNew.setSmallHeadPhoto(wxw.getHeadimgurl());
			}
			ouNew.setUnionId(wxw.getUnionid());
			//省市区
			if(StringUtils.isBlank(ou.getProvince())){
				log.info("country_:"+wxw.getCountry()+",province_:"+wxw.getProvince()+",city_:"+wxw.getCity());
				Map<String,Object> map = cityService.getSingProvinceByCode(wxw.getCountry());
				if(map!=null){
					Object objId = map.get("cid");
					int countryId = Integer.parseInt(objId.toString());
					ouNew.setDistrict(countryId+"");
					map = cityService.getSingCityByCodeAndPid(wxw.getProvince(), countryId);
					if(map!=null){
						objId = map.get("cid");
						Object objName = map.get("name");	
						int provinceId = Integer.parseInt(objId.toString());
						ouNew.setProvince(provinceId+"");
						ouNew.setProvinceName(objName.toString());
						map = cityService.getSingDistrictByCodeAndPid(wxw.getCity(), provinceId);
						if(map!=null){
							objId = map.get("cid");
							objName = map.get("name");
							int cityId = Integer.parseInt(objId.toString());
							ouNew.setCity(cityId+"");
							ouNew.setCityName(objName.toString());
						}
					}
				}
			}
			/*
			 * 更新用户信息
			 */
			onlineUserService.updateOnlineUserByWeixinInfo(ou,ouNew);

			/**
			 * 补充用户信息到微信中
			 */
			String openid = wxw.getOpenid();
			wxw.setClient_id(ou.getId());
			wxcpClientUserWxMappingService.update(wxw);
			/**
			 * 如果这个用户信息已经保存进去了，那么就直接登录就ok
			 */
		    ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
			Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
			ou.setTicket(t.getTicket());
			onlogin(req,res,t,ou,t.getTicket());
			
			/*
			 * 去首页，首页是jsp吗，jsp哈哈就可以得到数据啦
			 */
			res.sendRedirect(returnOpenidUri+"/bxg/page/index/"+wxw.getOpenid()+"/"+code);
			
		}else if(wxw.getClient_id()!=null  &&  wxw.getClient_id().equals(ou.getId())){
			
			/* 微信中包含用户id，但是用户信息中不包含微信信息
			 * 这里可以做下容错处理
			 */
			ou.setUnionId(wxw.getUnionid());
			onlineUserService.updateUserUnionidByid(ou);
			
			ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
			Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
			ou.setTicket(t.getTicket());
			onlogin(req,res,t,ou,t.getTicket());
			res.sendRedirect(returnOpenidUri+"/bxg/page/index/"+wxw.getOpenid()+"/"+code);
			
		}else{	//如果我这个微信绑定了其他的手机号，但是这个用户用其他手机号登录来了，其实也可以的啦。
			res.sendRedirect(returnOpenidUri+"/bxg/page/index/"+wxw.getOpenid()+"/"+code);
		}
	}
	
	/**
	 * 绑定微信号和手机号 -- 方式为微信端登录的方式
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("h5GetCodeAndUserName")
	public void h5GetCodeAndUserPassword(HttpServletRequest req, HttpServletResponse res,
			Map<String, String> params) throws Exception {

		ConfigUtil cfg = new ConfigUtil(req.getSession());
		String returnOpenidUri = cfg.getConfig("returnOpenidUri");
		String userName = req.getParameter("userName");
		/*
		 * 把这些微信信息存放在用户信息中或者判断这信息
		 */
		OnlineUser ou = onlineUserMapper.findUserByLoginName(userName);
		if(null == ou){  //去登录页面
			res.sendRedirect(returnOpenidUri + "/bxg/page/login/1");
		}
		log.info("wx return code:" + req.getParameter("code"));
		
		String code = req.getParameter("code");
		WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
		
		//如果这个微信号中已经包含了一个手机号的话，那么就需要把
		
		if(null == wxw){
			res.sendRedirect(returnOpenidUri + "/bxg/page/login/1");
		}
		
		
		OnlineUser ouNew = new OnlineUser();
		//性别
		if(ou.getSex()==2){
			ouNew.setSex(Integer.parseInt(wxw.getSex()));
		}
		//名称
		if(StringUtils.isBlank(ou.getName())){
			ouNew.setName(wxw.getNickname());
		}
		//头像
		if(StringUtils.isBlank(ou.getSmallHeadPhoto())){
			ouNew.setSmallHeadPhoto(wxw.getHeadimgurl());
		}
		
		ouNew.setUnionId(wxw.getUnionid());
		
		//省市区
		if(StringUtils.isBlank(ou.getProvince())){
			log.info("country_:"+wxw.getCountry()+",province_:"+wxw.getProvince()+",city_:"+wxw.getCity());
			Map<String,Object> map = cityService.getSingProvinceByCode(wxw.getCountry());
			if(map!=null){
				Object objId = map.get("cid");
				int countryId = Integer.parseInt(objId.toString());
				ouNew.setDistrict(countryId+"");
				map = cityService.getSingCityByCodeAndPid(wxw.getProvince(), countryId);
				if(map!=null){
					objId = map.get("cid");
					Object objName = map.get("name");	
					int provinceId = Integer.parseInt(objId.toString());
					ouNew.setProvince(provinceId+"");
					ouNew.setProvinceName(objName.toString());
					map = cityService.getSingDistrictByCodeAndPid(wxw.getCity(), provinceId);
					if(map!=null){
						objId = map.get("cid");
						objName = map.get("name");
						int cityId = Integer.parseInt(objId.toString());
						ouNew.setCity(cityId+"");
						ouNew.setCityName(objName.toString());
					}
				}
			}
		}
		/*
		 * 更新用户信息
		 */
		onlineUserService.updateOnlineUserByWeixinInfo(ou,ouNew);
		String openid = wxw.getOpenid();
		
		wxw.setClient_id(ou.getId());
		wxcpClientUserWxMappingService.update(wxw);
		
		/**
		 * 如果这个用户信息已经保存进去了，那么就直接登录就ok
		 */
	    ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
		Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
		ou.setTicket(t.getTicket());
		onlogin(req,res,t,ou,t.getTicket());
		if (openid != null && !openid.isEmpty()) {
			res.sendRedirect(returnOpenidUri + "/bxg/page/index/"+ openid + "/" + code);
		} else{
			res.getWriter().write(openid);
		}
	}


	/**
	 * 个人中心   --去 my.html 页面
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("h5GetOpenidForPersonal")
	public void h5GetOpenidForPersonal(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		log.info("wx return code:" + req.getParameter("code"));
		try {
			String code = req.getParameter("code");
			WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
			/*
			 * 判断这个uninonid是否在user表中存在
			 */
			OnlineUser ou =  onlineUserMapper.findOnlineUserByUnionid(wxw.getUnionid());
			String openid = wxw.getOpenid();
			/**
			 * 如果这个用户信息已经保存进去了，那么就直接登录就ok
			 */
			ConfigUtil cfg = new ConfigUtil(req.getSession());
			String returnOpenidUri = cfg.getConfig("returnOpenidUri");
			if(ou != null){
			    ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
				Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
				ou.setTicket(t.getTicket());
				onlogin(req,res,t,ou,t.getTicket());
				if (openid != null && !openid.isEmpty()) {
					res.sendRedirect(returnOpenidUri + "/xcviews/html/my.html?center=center");
				} else{
					res.getWriter().write(openid);
				}	
			}else{
				//否则跳转到这是页面。绑定下手机号啦   -- 如果从个人中心进入的话，也需要绑定手机号啊，绑定过后，就留在这个页面就行。
				res.sendRedirect(returnOpenidUri + "/xcviews/html/my.html?"
						+ "openId="+openid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//res.getWriter().write(e.getMessage());
		}
	}


	@RequestMapping("getWechatCode")
	public void getWechatCode(HttpServletRequest req, HttpServletResponse res,
			Map<String, String> params) throws Exception {
		String strLinkHome = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri="+WxPayConst.returnOpenidUri+"/bxg/wxpay/h5GetOpenidRedirect&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect"
				.replace("appid=APPID", "appid=" + WxPayConst.gzh_appid);
		res.sendRedirect(strLinkHome);
	}

	@RequestMapping("h5GetOpenidRedirect")
	public void h5GetOpenidRedirect(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		try {
			log.info("wx return code:" + req.getParameter("code"));
			String code = req.getParameter("code");
			String courseId = req.getParameter("courseId");
			// String openid = PayFactory.work().getOpenId(code);//openid =
			// "{\"openid\":\"openid1234567890\",\"errcode\":40013,\"errmsg\":\"invalid appid, hints: [ req_id: 0701ns83 ]\"}";
			String openid = ClientUserUtil.setWxInfo(code, null, null,
					wxcpClientUserService, wxcpClientUserWxMappingService,
					userCenterAPI, onlineUserMapper, req, res);
			ConfigUtil cfg = new ConfigUtil(req.getSession());
			String returnOpenidUriRedirect = cfg
					.getConfig("returnOpenidUriRedirect");
			log.info("returnOpenidUriRedirect="
					+ returnOpenidUriRedirect);
			// log.info("notifyUrl=" + WxPayConst.getNotifyUrl());
			if (openid != null && !openid.isEmpty()) {
				JSONObject jsonObject = JSONObject.fromObject(openid);
				res.sendRedirect(returnOpenidUriRedirect
						+ jsonObject.get("openid") + "&courseId=" + courseId);
			} else
				res.getWriter().write(openid);

		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().write(e.getMessage());
		}
	}

	@RequestMapping("wxAuthSimulate")
	public void wxAuthSimulate(HttpServletRequest req, HttpServletResponse res,
			Map<String, String> params) throws Exception {

		String redirect_uri = req.getParameter("redirect_uri");
		String code = req.getParameter("code");
		if (code == null)
			code = "bxgcodesimulate";
		try {
			res.sendRedirect(redirect_uri + "?code=" + code);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("h5GetCodeidComplete")
	public void h5GetCodeidComplete(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {

		try {
			log.info("wx get codeid:" + req.getParameter("code"));
			String code = req.getParameter("code");
			if (code != null && !code.isEmpty()) {
				res.getWriter().write("code=" + code);
			} else
				res.getWriter().write("failure");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("h5GetOpenidComplete")
	public void h5GetOpenidComplete(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {

		try {
			log.info("wx get openid:" + req.getParameter("openid"));
			String openid = req.getParameter("openid");
			if (openid != null && !openid.isEmpty()) {
				res.getWriter().write("openid=" + openid);
			} else
				res.getWriter().write("failure");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("sendRedPack")
	@ResponseBody
	public ResponseObject sendRedPack(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {

		String re_openid = req.getParameter("re_openid");
		String total_amount = req.getParameter("total_amount");
		String wishing = req.getParameter("wishing");
		String act_name = req.getParameter("act_name");
		String remark = req.getParameter("remark");
		if (re_openid == null || re_openid.trim().length() == 0
				|| total_amount == null || total_amount.trim().length() == 0
				|| wishing == null || wishing.trim().length() == 0
				|| act_name == null || act_name.trim().length() == 0
				|| remark == null || remark.trim().length() == 0)
			return ResponseObject.newSuccessResponseObject(null);

		String sub_mch_id = req.getParameter("sub_mch_id");
		String nick_name = req.getParameter("nick_name");
		String logo_imgurl = req.getParameter("logo_imgurl");
		String share_content = req.getParameter("share_content");
		String share_url = req.getParameter("share_url");
		String share_imgurl = req.getParameter("share_imgurl");

		SendRedPack redPack = new SendRedPack();

		redPack.setNonce_str(UUID.randomUUID().toString());
		redPack.setSign("");
		redPack.setMch_billno(createBillNo());
		redPack.setWxappid(WxPayConst.gzh_appid);
		redPack.setMch_id(WxPayConst.gzh_mchid);
		redPack.setSend_name(WxPayConst.appid4name);
		redPack.setRe_openid(re_openid);
		redPack.setTotal_amount(Integer.valueOf(total_amount));
		redPack.setTotal_num(1);
		redPack.setWishing(wishing);
		redPack.setClient_ip("127.0.0.1");
		redPack.setAct_name(act_name);
		redPack.setRemark(remark);
		redPack.setSub_mch_id(sub_mch_id);
		redPack.setNick_name(nick_name);
		redPack.setMin_value(Integer.valueOf(total_amount));
		redPack.setMax_value(Integer.valueOf(total_amount));
		redPack.setLogo_imgurl(logo_imgurl);
		redPack.setShare_content(share_content);
		redPack.setShare_url(share_url);
		redPack.setShare_imgurl(share_imgurl);

		if (true) {
			JSONObject jsonObject = JSONObject.fromObject(redPack);
			log.info("WxPayController->sendRedPack->send\r\n\t"
					+ jsonObject.toString());
		}

		Map<String, String> retInfo = CommonUtil.sendRedPack(redPack);
		String return_code_ = retInfo.get("return_code");
		String return_msg_ = retInfo.get("return_msg");
		String result_code_ = retInfo.get("result_code");
		String err_code_ = retInfo.get("err_code");
		String err_code_des_ = retInfo.get("err_code_des");
		String mch_billno_ = retInfo.get("mch_billno");
		String mch_id_ = retInfo.get("mch_id");
		String wxappid_ = retInfo.get("wxappid");
		String total_amount_ = retInfo.get("total_amount");

		// put in storage
		redPack.setReturn_code(return_code_);
		redPack.setResult_code(result_code_);
		redPack.setReturn_msg(return_msg_);
		wxcpWxRedpackService.insert(redPack);
		if (true) {
			JSONObject jsonObject = JSONObject.fromObject(redPack);
			log.info("WxPayController->sendRedPack->recv\r\n\t"
					+ jsonObject.toString());
		}

		// ??if(return_code_ != null && return_code_.equals("SUCCESS") &&
		// result_code_ != null && result_code_.equals("SUCCESS") ) {
		// ??
		// ??}

		return ResponseObject.newSuccessResponseObject(retInfo);
	}

	@RequestMapping("wxTrans")
	@ResponseBody
	public ResponseObject wxTrans(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {

		String openid_ = req.getParameter("openid");
		String amount_ = req.getParameter("amount");
		if (openid_ == null || openid_.trim().length() == 0 || amount_ == null
				|| amount_.trim().length() == 0)
			return ResponseObject.newSuccessResponseObject(null);

		String re_user_name_ = req.getParameter("re_user_name");
		String desc_ = req.getParameter("desc");

		String trans_id = UUID.randomUUID().toString().replace("-", "");
		String mch_appid = WxPayConst.gzh_appid;
		String mchid = WxPayConst.gzh_mchid;
		String device_info = "";
		String nonce_str = UUID.randomUUID().toString();
		String sign = "";
		String partner_trade_no = GenerateSequenceUtil.generateSequenceNo();
		String openid = openid_;
		String check_name = "OPTION_CHECK";
		String re_user_name = re_user_name_;
		int amount = Integer.valueOf(amount_);
		String desc = desc_;
		String spbill_create_ip = "127.0.0.1";
		String return_code = "";
		String result_code = "";
		String return_msg = "";
		String payment_no = "";
		String payment_time = "";

		// for test begin
		if (true) {
			mch_appid = "wxe062425f740c30d8";
			mchid = "10000098";
			nonce_str = "3PG2J4ILTKCH16CQ2502SI8ZNMTM67VS";
			sign = "C97BDBACF37622775366F38B629F45E3";
			partner_trade_no = "100000982014120919616";
			openid = "ohO4Gt7wVPxIT1A9GjFaMYMiZY1s";
			re_user_name = "张三";
			amount = 100;
			desc = "节日快乐!";
			spbill_create_ip = "10.2.3.10";
		}
		// for test end

		WxcpWxTrans wxTrans = new WxcpWxTrans();
		wxTrans.setTrans_id(trans_id);
		wxTrans.setMch_appid(mch_appid);
		wxTrans.setMchid(mchid);
		wxTrans.setDevice_info(device_info);
		wxTrans.setNonce_str(nonce_str);
		wxTrans.setSign(sign);
		wxTrans.setPartner_trade_no(partner_trade_no);
		wxTrans.setOpenid(openid);
		wxTrans.setCheck_name(check_name);
		wxTrans.setRe_user_name(re_user_name);
		wxTrans.setAmount(amount);
		wxTrans.setDesc(desc);
		wxTrans.setSpbill_create_ip(spbill_create_ip);
		wxTrans.setReturn_code(return_code);
		wxTrans.setResult_code(result_code);
		wxTrans.setReturn_msg(return_msg);
		wxTrans.setPayment_no(payment_no);
		wxTrans.setPayment_time(payment_time);
		if (true) {
			JSONObject jsonObject = JSONObject.fromObject(wxTrans);
			log.info("WxPayController->wxTrans->send\r\n\t"
					+ jsonObject.toString());
		}

		Map<String, String> retInfo = CommonUtil.sendTrans(wxTrans);
		String return_code_ = retInfo.get("return_code");
		String result_code_ = retInfo.get("result_code");
		String return_msg_ = retInfo.get("return_msg");
		String payment_no_ = retInfo.get("payment_no");
		String payment_time_ = retInfo.get("payment_time");

		// put in storage
		wxTrans.setReturn_code(return_code_);
		wxTrans.setResult_code(result_code_);
		wxTrans.setReturn_msg(return_msg_);
		wxTrans.setPayment_no(payment_no_);
		wxTrans.setPayment_time(payment_time_);
		wxcpWxTransService.insert(wxTrans);
		if (true) {
			JSONObject jsonObject = JSONObject.fromObject(wxTrans);
			log.info("WxPayController->wxTrans->recv\r\n\t"
					+ jsonObject.toString());
		}

		// ??if(return_code_ != null && return_code_.equals("SUCCESS") &&
		// result_code_ != null && result_code_.equals("SUCCESS") ) {
		// ??
		// ??}

		return ResponseObject.newSuccessResponseObject(retInfo);
	}

	public static String createBillNo() {
		Date dt = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyymmdd");
		String nowTime = df.format(dt);
		int length = 10;
		return WxPayConst.gzh_mchid + nowTime + getRandomNum(length);
	}

	public static String getRandomNum(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			val += String.valueOf(random.nextInt(10));
		}
		return val;
	}
	/**
	 * 登陆成功处理
	 * @param req
	 * @param res
	 * @param token
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	public void onlogin(HttpServletRequest req, HttpServletResponse res,
                        Token token, OnlineUser user, String ticket){
		// 用户登录成功
		// 第一个BUG的解决:第二个用户登录后将之前的session销毁!
		req.getSession().invalidate();
		// 第二个BUG的解决:判断用户是否已经在Map集合中,存在：已经在列表中.销毁其session.
		// 获得到ServletCOntext中存的Map集合.
		Map<OnlineUser, HttpSession> userMap = (Map<OnlineUser, HttpSession>) req.getServletContext()
				.getAttribute("userMap");
		// 判断用户是否已经在map集合中'
		HttpSession session = userMap.get(user);
		if(session!=null && userMap.containsKey(user)){
			/**
			 * 得到客户端信息
			 */
			Map<String,String> mapClientInfo =  com.xczh.consumer.market.utils.HttpUtil.getClientInformation(req);
			//session.invalidate();
			session.setAttribute("topOff", mapClientInfo);
			session.setAttribute("_user_",null);
		}else if(session!=null){
			session.setAttribute("topOff",null);
		}
		// 使用监听器:HttpSessionBandingListener作用在JavaBean上的监听器.
		req.getSession().setMaxInactiveInterval(1000);//设置session失效时间
		req.getSession().setAttribute("_user_", user);
		/**
		 * 这是cookie 
		 */
		UCCookieUtil.writeTokenCookie(res, token);
	}
}
