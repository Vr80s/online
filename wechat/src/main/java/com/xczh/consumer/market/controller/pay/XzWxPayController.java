package com.xczh.consumer.market.controller.pay;

import com.alibaba.fastjson.JSON;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpPayFlow;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.WxcpPayFlowService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.WebUtil;
import com.xczh.consumer.market.wxpay.entity.PayInfo;
import com.xczhihui.common.util.IStringUtil;
import com.xczhihui.common.util.OrderNoUtil;
import com.xczhihui.common.util.enums.PayOrderType;
import com.xczhihui.bxg.online.api.service.PayService;
import com.xczhihui.pay.ext.kit.HttpKit;
import com.xczhihui.pay.ext.kit.IpKit;
import com.xczhihui.pay.ext.kit.PaymentKit;
import com.xczhihui.pay.weixin.api.*;
import com.xczhihui.wechat.course.model.Order;
import com.xczhihui.wechat.course.service.IOrderService;
import com.xczhihui.wechat.course.vo.PayMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xczhihui.pay.alipay.controller.AliPayApiController.BUY_COIN_TEXT;
import static com.xczhihui.pay.alipay.controller.AliPayApiController.BUY_COURSE_TEXT;


/**
 * 
 * 客户端用户访问控制器类
 * 
 * @author yanghui
 **/
@Controller
@RequestMapping("/xczh/pay")
public class XzWxPayController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IOrderService orderService;
	@Autowired
	private AppBrowserService appBrowserService;
	@Autowired
	private  WxcpPayFlowService wxcpPayFlowService;
	@Autowired
	private PayService payService;
	@Autowired
	WxPayBean wxPayBean;

	@Value("${rate}")
	private int rate;
	@Value("${minimum_amount}")
	private Double minimumAmount;

	private String notify_url;

	public WxPayApiConfig getApiConfig(boolean appPay) {
		notify_url = wxPayBean.getDomain().concat("/xczh/pay/pay_notify");
		if(appPay){
			log.info("========app=======");
			log.info("appi"+wxPayBean.getAppId4App());
			log.info("mchid"+wxPayBean.getMchId4App());
			log.info("paternerekey"+wxPayBean.getPartnerKey4App());
			return WxPayApiConfig.New()
					.setAppId(wxPayBean.getAppId4App())
					.setMchId(wxPayBean.getMchId4App())
					.setPaternerKey(wxPayBean.getPartnerKey4App())
					.setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL);
		}
		log.info("========h5=======");
		return WxPayApiConfig.New()
				.setAppId(wxPayBean.getAppId4H5())
				.setMchId(wxPayBean.getMchId4H5())
				.setPaternerKey(wxPayBean.getPartnerKey4H5())
				.setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL);
	}


	/**
	 * Description：微信支付-购课
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 2018/4/23 0023 下午 5:52
	 **/
	@RequestMapping("wxPay")
	@ResponseBody
	public ResponseObject appOrderPay(HttpServletRequest req,
									  @RequestParam("orderId")String orderId,
									  @RequestParam("orderFrom")String orderFrom)throws Exception {
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		if (null == ou) {
			return ResponseObject.newErrorResponseObject("登录失效");
		}

		Order order = orderService.getOrderNo4PayByOrderId(orderId);

		int actualPay = (int) (order.getActualPay() * 100);

		int orderFromI = Integer.parseInt(orderFrom);
		boolean appPay = false;
		if(orderFromI==5){
			appPay = true;
		}
		WxPayApiConfigKit.setThreadLocalWxPayApiConfig(getApiConfig(appPay));

//		String spbill_create_ip =WxPayConst.server_ip;
		String ip = IpKit.getRealIp(req);
//		String tradeType = null;
		String openId = req.getParameter("openId");

		PayMessage payMessage = new PayMessage();
		payMessage.setType(PayOrderType.COURSE_ORDER.getCode());
		payMessage.setUserId(order.getUserId());
		String attach = PayMessage.getPayMessage(payMessage);

		Map<String, String> payParams = getPayParams(orderFromI, order.getOrderNo()+ IStringUtil.getRandomString(), ip, actualPay + "", openId, attach,MessageFormat.format(BUY_COURSE_TEXT,order.getCourseNames()));
		for (Map.Entry<String, String> entry : payParams.entrySet()) {
			log.info(entry.getKey() + " = " + entry.getValue());
		}

		String xmlResult = WxPayApi.pushOrder(false,payParams);
		log.info(xmlResult);
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);

		String return_code = result.get("return_code");
		String return_msg = result.get("return_msg");
		if (!PaymentKit.codeIsOK(return_code)) {
			log.error("return_code>"+return_code+" return_msg>"+return_msg);
			throw new RuntimeException(return_msg);
		}
		String result_code = result.get("result_code");
		if (!PaymentKit.codeIsOK(result_code)) {
			log.error("result_code>"+result_code+" return_msg>"+return_msg);
			throw new RuntimeException(return_msg);
		}
		// 以下字段在return_code 和result_code都为SUCCESS的时候有返回

		String prepay_id = result.get("prepay_id");
		String mweb_url = result.get("mweb_url");

		log.info("prepay_id:"+prepay_id+" mweb_url:"+mweb_url);

		if (result != null) {
			result.put("ok", "true");
			/**
			 * app支付需要进行二次签名
			 */
			if(orderFromI == 5){
				//封装调起微信支付的参数 https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12
				Map<String, String> packageParams = new HashMap<String, String>();
				packageParams.put("appid", WxPayApiConfigKit.getWxPayApiConfig().getAppId());
				packageParams.put("partnerid", WxPayApiConfigKit.getWxPayApiConfig().getMchId());
				packageParams.put("prepayid", prepay_id);
				packageParams.put("package", "Sign=WXPay");
				packageParams.put("noncestr", System.currentTimeMillis() + "");
				packageParams.put("timestamp", System.currentTimeMillis() / 1000 + "");
				String packageSign = PaymentKit.createSign(packageParams, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey());
				packageParams.put("sign", packageSign);

				String jsonStr = JSON.toJSONString(packageParams);
				System.out.println(jsonStr);
				return ResponseObject.newSuccessResponseObject(packageParams);
			}else if(orderFromI == 3){
				Map<String, String> param = new HashMap<>();
				param.put("appId", result.get("appid"));
				param.put("nonceStr", result.get("nonce_str"));
				String preid = result.get("prepay_id");
				param.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
				param.put("package", "prepay_id=" + preid);
				param.put("signType", "MD5");
				param.put("paySign", PaymentKit.createSign(param, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey()));
				return ResponseObject.newSuccessResponseObject(param);
			}
			return ResponseObject.newSuccessResponseObject(result);
		}
		return ResponseObject.newErrorResponseObject("请求错误");
	}

	/**
	 * Description：微信支付-充值
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 2018/4/23 0023 下午 5:52
	 **/
	@RequestMapping("rechargePay")
	@ResponseBody
	public ResponseObject rechargePay(HttpServletRequest request, HttpServletResponse response,@RequestParam("clientType")Integer clientType,
									  @RequestParam("actualPay")String actualPay)throws Exception {

		OnlineUser user = appBrowserService.getOnlineUserByReq(request);
		if (null == user) {
			throw new RuntimeException("登录失效");
		}

		Double count = Double.valueOf(actualPay) * rate;
		if (!WebUtil.isIntegerForDouble(count)) {
			throw new RuntimeException("充值金额" + actualPay + "兑换的熊猫币" + count + "不为整数");
		}

		int total = (int) (Double.valueOf(actualPay) * 100);

		int orderFromI = Integer.valueOf(clientType);
		boolean appPay = false;
		if(orderFromI==5){
			appPay = true;
		}
		WxPayApiConfig apiConfig = getApiConfig(appPay);
		log.info("appi"+apiConfig.getAppId());
		log.info("mchid"+apiConfig.getMchId());
		log.info("paternerekey"+apiConfig.getPaternerKey());
		WxPayApiConfigKit.setThreadLocalWxPayApiConfig(apiConfig);

//		String spbill_create_ip =WxPayConst.server_ip;
		String ip = IpKit.getRealIp(request);
		String openId = request.getParameter("openId");

		PayMessage payMessage = new PayMessage();
		payMessage.setType(PayOrderType.COIN_ORDER.getCode());
		payMessage.setUserId(user.getId());
		payMessage.setValue(new BigDecimal(count));

		String attach = PayMessage.getPayMessage(payMessage);

		String orderNo = OrderNoUtil.getCoinOrderNo();

		Map<String, String> payParams = getPayParams(orderFromI, orderNo, ip, total + "", openId, attach, MessageFormat.format(BUY_COIN_TEXT,count));
		for (Map.Entry<String, String> entry : payParams.entrySet()) {
			log.error(entry.getKey() + " = " + entry.getValue());
		}

		String xmlResult = WxPayApi.pushOrder(false,payParams);
		log.info(xmlResult);
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);

		String return_code = result.get("return_code");
		String return_msg = result.get("return_msg");
		if (!PaymentKit.codeIsOK(return_code)) {
			log.error("return_code>"+return_code+" return_msg>"+return_msg);
			throw new RuntimeException(return_msg);
		}
		String result_code = result.get("result_code");
		if (!PaymentKit.codeIsOK(result_code)) {
			log.error("result_code>"+result_code+" return_msg>"+return_msg);
			throw new RuntimeException(return_msg);
		}
		// 以下字段在return_code 和result_code都为SUCCESS的时候有返回

		String prepay_id = result.get("prepay_id");
		String mweb_url = result.get("mweb_url");

		log.info("prepay_id:"+prepay_id+" mweb_url:"+mweb_url);

		if (result != null) {
			result.put("ok", "true");
			/**
			 * app支付需要进行二次签名
			 */
			if(orderFromI == 5){
				System.out.println("result"+JSON.toJSONString(result));
				//封装调起微信支付的参数 https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12
				Map<String, String> packageParams = new HashMap<String, String>();
				packageParams.put("appid", WxPayApiConfigKit.getWxPayApiConfig().getAppId());
				packageParams.put("partnerid", WxPayApiConfigKit.getWxPayApiConfig().getMchId());
				packageParams.put("prepayid", prepay_id);
				packageParams.put("package", "Sign=WXPay");
				packageParams.put("noncestr", result.get("nonce_str"));
				String timestamp = System.currentTimeMillis() / 1000 + "";
				packageParams.put("timestamp", timestamp);
				String packageSign = PaymentKit.createSign(packageParams, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey());
//				packageParams.put("sign", packageSign);

				result.put("device_info","wxpay");
				result.put("sign", packageSign);
				result.put("timestamp", timestamp);
				String jsonStr = JSON.toJSONString(result);
				System.out.println("json"+jsonStr);
				return ResponseObject.newSuccessResponseObject(result);
			}else if(orderFromI == 3){



				Map<String, String> param = new HashMap<>();
				param.put("appId", result.get("appid"));
				param.put("nonceStr", result.get("nonce_str"));
				String preid = result.get("prepay_id");
				param.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
				param.put("package", "prepay_id=" + preid);
				param.put("signType", "MD5");
				param.put("paySign", PaymentKit.createSign(param, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey()));
				return ResponseObject.newSuccessResponseObject(param);
			}
			return ResponseObject.newSuccessResponseObject(result);
		}
		return ResponseObject.newErrorResponseObject("请求错误");
	}

	/**
	 * 通过 自定义的订单号来查找微信充值信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryWechatPayInfoByOutTradeNo")
	@ResponseBody
	public ResponseObject queryAlipayPaymentRecordH5ByOutTradeNo(@RequestParam("outTradeNo")String outTradeNo) throws Exception {

		log.info("查询充值订单信息："+outTradeNo);
		
		WxcpPayFlow condition = new WxcpPayFlow();
		condition.setOut_trade_no(outTradeNo);
		List<WxcpPayFlow> list =  wxcpPayFlowService.select(condition);
		if(list!=null && list.size()>0){
			log.info("list："+list.size());
			return ResponseObject.newSuccessResponseObject(list.get(0));
		}
		return ResponseObject.newErrorResponseObject("未获得充值信息");
	}

	@RequestMapping(value = "pay_notify")
	@ResponseBody
	public String wxNotify(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		// 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7

		String xmlMsg = HttpKit.readData(req);
		System.out.println("支付通知="+xmlMsg);
		Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
		String result_code  = params.get("result_code");

		// 交易类型
		String trade_type = params.get("trade_type");
		//根据支付类型判断得到--》回调验证签名的key
		//h5和公众号用的是一样了。
		//app用的是另一个
		if(PayInfo.TRADE_TYPE_APP.equals(trade_type)){
			WxPayApiConfigKit.setThreadLocalWxPayApiConfig(getApiConfig(true));
		}else{
			WxPayApiConfigKit.setThreadLocalWxPayApiConfig(getApiConfig(false));
		}

		if(PaymentKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey())){
			if (("SUCCESS").equals(result_code)) {
				log.info("============kaishi===========");
				for (Map.Entry<String, String> entry : params.entrySet()) {
					log.error(entry.getKey() + " = " + entry.getValue());
				}
				payService.wxPayBusiness(params);
				//发送通知等
				Map<String, String> xml = new HashMap<String, String>();
				xml.put("return_code", "SUCCESS");
				xml.put("return_msg", "OK");
				return PaymentKit.toXml(xml);
			}
		}

		return null;

	}

	public Map<String, String> getPayParams(Integer orderFromI,String orderNo,String ip,String actualPay,String openId,String attach,String body) {
		System.out.println(notify_url);
		WxPayApiConfig wxPayApiConfig = WxPayApiConfigKit.getWxPayApiConfig()
				.setAttach(attach)
				.setBody(body)
				.setSpbillCreateIp(ip)
				.setTotalFee(actualPay)
				.setNotifyUrl(notify_url)
				.setOutTradeNo(orderNo);
		if(orderFromI == 3){
			if(!StringUtils.isNotBlank("openId")) {
				throw new RuntimeException("尝试下重新登录,或者关注公众号!");
			}
			Map<String, String> params = wxPayApiConfig.setOpenId(openId).setTradeType(WxPayApi.TradeType.JSAPI).build();
			return params;
		}else if(orderFromI == 4){
			H5ScencInfo sceneInfo = new H5ScencInfo();
			H5ScencInfo.H5 h5Info = new H5ScencInfo.H5();
			h5Info.setType("Wap");
			//此域名必须在商户平台--"产品中心"--"开发配置"中添加
			h5Info.setWap_url(wxPayBean.getDomain());
			h5Info.setWap_name("熊猫中医");
			sceneInfo.setH5_info(h5Info);

			Map<String, String> params = wxPayApiConfig.setTradeType(WxPayApi.TradeType.MWEB).setSceneInfo(h5Info.toString()).build();
			return params;
		}else if(orderFromI == 5){
			Map<String, String> params = wxPayApiConfig.setTradeType(WxPayApi.TradeType.APP).build();
			return params;
		}
		return null;
	}

}
