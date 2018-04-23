package com.xczh.consumer.market.controller.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.WebUtil;
import com.xczhihui.bxg.common.util.OrderNoUtil;
import com.xczhihui.bxg.common.util.enums.PayOrderType;
import com.xczhihui.bxg.online.api.service.PayService;
import com.xczhihui.pay.alipay.AliPayApi;
import com.xczhihui.pay.alipay.AliPayApiConfig;
import com.xczhihui.pay.alipay.AliPayBean;
import com.xczhihui.pay.alipay.controller.AliPayApiController;
import com.xczhihui.wechat.course.model.Order;
import com.xczhihui.wechat.course.service.IOrderService;
import com.xczhihui.wechat.course.vo.PayMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Map;


@Controller
@RequestMapping("/xczh/alipay")
public class XzAlipayController extends AliPayApiController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	private AppBrowserService appBrowserService;

	@Autowired
	private AliPayBean aliPayBean;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private PayService payService;

	@Value("${online.weburl}")
	private String pcUrl;
	@Value("${onlinekey}")
	private String onlinekey;
	@Value("${rate}")
	private int rate;
	@Value("${minimum_amount}")
	private Double minimumAmount;

    private static String ALIPAY_NOTIFY_URL = "/xczh/alipay/alipayNotifyUrl";
//    private static String alipayNotifyUrl = "/xczh/alipay/alipayNotifyUrl";

	@Override
	public AliPayApiConfig getApiConfig() {
		return AliPayApiConfig.New()
				.setAppId(aliPayBean.getAppId())
				.setAlipayPublicKey(aliPayBean.getPublicKey())
				.setCharset("UTF-8")
				.setPrivateKey(aliPayBean.getPrivateKey())
				.setServiceUrl(aliPayBean.getServerUrl())
				.setSignType("RSA2")
				.build();
	}

	/**
	 * Description：支付宝wap支付，课程下单
	 * @param orderId		订单id
	 * @param formIsWechat  是否来自微信浏览器
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 2018/4/23 0023 上午 10:49
	 **/
	@RequestMapping(value = "pay")
	@ResponseBody
	public void pay(HttpServletResponse response,@RequestParam("orderId")String orderId,
							   @RequestParam(required=false)String formIsWechat) throws Exception {

		Order order = orderService.getOrderNo4PayByOrderId(orderId);

		String returnUrl;
		String notifyUrl = aliPayBean.getDomain() + ALIPAY_NOTIFY_URL;
		if (StringUtils.isNotBlank(formIsWechat)) {
			returnUrl = aliPayBean.getDomain()+"/xcview/html/goWechat.html";
		} else {
			//跳到购买成功页面
			returnUrl = aliPayBean.getDomain()+ "/xcview/html/buy_prosperity.html?courseId="+ order.getCourseIds().get(0);
		}

		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setBody(MessageFormat.format(BUY_COURSE_TEXT,order.getCourseNames()));
		model.setSubject(MessageFormat.format(BUY_COURSE_TEXT,order.getCourseNames()));
		model.setTotalAmount(order.getActualPay()+"");
		model.setOutTradeNo(order.getOrderNo());
		model.setTimeoutExpress(TIMEOUT_EXPRESS);
		model.setProductCode(WAP_PRODUCT_CODE);

		PayMessage payMessage = new PayMessage();
		payMessage.setType(PayOrderType.COURSE_ORDER.getCode());
		payMessage.setUserId(order.getUserId());
		String passbackParams = PayMessage.getPayMessage(payMessage);
		model.setPassbackParams(passbackParams);

		try {
			AliPayApi.wapPay(response, model, returnUrl, notifyUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description：支付宝wap支付，充值
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 2018/4/23 0023 上午 10:50
	 **/
	@RequestMapping(value = "rechargePay")
	@ResponseBody
	public void rechargePay(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("actualPay")String actualPay) throws Exception {

		OnlineUser user = appBrowserService.getOnlineUserByReq(request);
		if (null == user) {
			throw new RuntimeException("登录失效");
		}

		Double count = Double.valueOf(actualPay) * rate;
		if (!WebUtil.isIntegerForDouble(count)) {
			throw new RuntimeException("充值金额" + actualPay + "兑换的熊猫币" + count + "不为整数");
		}

        String returnUrl = aliPayBean.getDomain() + "/xcview/html/goWechat.html";
        String notifyUrl = aliPayBean.getDomain() + ALIPAY_NOTIFY_URL;
        String orderNo = OrderNoUtil.getCoinOrderNo();

        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(orderNo);
        model.setProductCode(WAP_PRODUCT_CODE);
        model.setTotalAmount(actualPay);
        model.setSubject(MessageFormat.format(BUY_COIN_TEXT,count));
        model.setBody(MessageFormat.format(BUY_COIN_TEXT,count));
        model.setTimeoutExpress(TIMEOUT_EXPRESS);

        PayMessage payMessage = new PayMessage();
        payMessage.setType(PayOrderType.COIN_ORDER.getCode());
        payMessage.setUserId(user.getUserId());
        payMessage.setValue(new BigDecimal(count));

        String passbackParams = PayMessage.getPayMessage(payMessage);
        model.setPassbackParams(passbackParams);

        try {
            AliPayApi.wapPay(response, model, returnUrl, notifyUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
	 * app支付获取订单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getOrderStr")
	@ResponseBody
	public ResponseObject getOrderStr(@RequestParam("orderId")String orderId) throws SQLException,
            UnsupportedEncodingException, AlipayApiException {

        Order order = orderService.getOrderNo4PayByOrderId(orderId);

        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(MessageFormat.format(BUY_COURSE_TEXT,order.getCourseNames()));
        model.setSubject(MessageFormat.format(BUY_COURSE_TEXT,order.getCourseNames()));
        model.setTotalAmount(order.getActualPay()+"");
        model.setOutTradeNo(order.getOrderNo());
        model.setTimeoutExpress(TIMEOUT_EXPRESS);
        model.setProductCode(APP_PRODUCT_CODE);

        PayMessage payMessage = new PayMessage();
        payMessage.setType(PayOrderType.COURSE_ORDER.getCode());
        payMessage.setUserId(order.getUserId());
        String passbackParams = PayMessage.getPayMessage(payMessage);
        model.setPassbackParams(passbackParams);

        String orderInfo = AliPayApi.startAppPay(model, aliPayBean.getDomain() + ALIPAY_NOTIFY_URL);
        return ResponseObject.newSuccessResponseObject(orderInfo);

	}

	/**
	 * app 支付宝充值接口
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "rechargeAppPay")
	@ResponseBody
	public ResponseObject rechargeAppPay(HttpServletRequest request,
			@RequestParam("actualPay")String actualPay) throws SQLException, AlipayApiException {

        OnlineUser user = appBrowserService.getOnlineUserByReq(request);
        if (null == user) {
            throw new RuntimeException("登录失效");
        }

        Double count = Double.valueOf(actualPay) * rate;
        if (!WebUtil.isIntegerForDouble(count)) {
            throw new RuntimeException("充值金额" + actualPay + "兑换的熊猫币" + count + "不为整数");
        }

        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        String orderNo = OrderNoUtil.getCoinOrderNo();
        model.setOutTradeNo(orderNo);
        model.setProductCode(WAP_PRODUCT_CODE);
        model.setTotalAmount(actualPay);
        model.setSubject(MessageFormat.format(BUY_COIN_TEXT,count));
        model.setBody(MessageFormat.format(BUY_COIN_TEXT,count));
        model.setTimeoutExpress(TIMEOUT_EXPRESS);

        PayMessage payMessage = new PayMessage();
        payMessage.setType(PayOrderType.COIN_ORDER.getCode());
        payMessage.setUserId(user.getUserId());
        payMessage.setValue(new BigDecimal(count));

        String passbackParams = PayMessage.getPayMessage(payMessage);
        model.setPassbackParams(passbackParams);


        String orderInfo = AliPayApi.startAppPay(model, aliPayBean.getDomain() + ALIPAY_NOTIFY_URL);
        return ResponseObject.newSuccessResponseObject(orderInfo);
	}

	/**
	 * Description：异步回调通知
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 2018/4/23 0023 上午 11:36
	 **/
	@RequestMapping(value = "alipayNotifyUrl")
	@ResponseBody
	@Transactional
	public String alipayNotify(HttpServletRequest request) throws Exception {
            try {
                // 获取支付宝POST过来反馈信息
                Map<String, String> params = AliPayApi.toMap(request);
                boolean verify_result = AlipaySignature.rsaCheckV1(params, aliPayBean.getPublicKey(), "UTF-8","RSA2");
                if (verify_result) {
                    payService.aliPayBusiness(params);
                    return "success";
                } else {
                    logger.error("notify_url 验证失败"+params);
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        logger.error(entry.getKey() + " = " + entry.getValue());
                    }
                    return "failure";
                }
            } catch (AlipayApiException e) {
                e.printStackTrace();
                return "failure";
            } catch (Exception e) {
                return "failure";
            }
	}

}