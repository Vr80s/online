package com.xczhihui.bxg.online.web.controller.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.utils.WebUtil;
import com.xczhihui.bxg.online.web.utils.alipay.AlipayConfig;
import com.xczhihui.common.util.OrderNoUtil;
import com.xczhihui.common.util.enums.OrderFrom;
import com.xczhihui.common.util.enums.PayOrderType;
import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.course.model.Order;
import com.xczhihui.course.service.IOrderService;
import com.xczhihui.course.vo.PayMessage;
import com.xczhihui.online.api.service.PayService;
import com.xczhihui.pay.alipay.AliPayApi;
import com.xczhihui.pay.alipay.AliPayApiConfig;
import com.xczhihui.pay.alipay.AliPayApiConfigKit;
import com.xczhihui.pay.alipay.AliPayBean;
import com.xczhihui.pay.alipay.controller.AliPayApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;

/**
 * 支付宝相关接口
 *
 * @author liutao 【jvmtar@gmail.com】
 * @create 2017-08-19 10:33
 **/
@Controller
@RequestMapping(value = "/web")
public class AliPayController extends AliPayApiController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IOrderService orderService;
    @Autowired
    private PayService payService;

    @Autowired
    private AliPayBean aliPayBean;

    @Value("${web.url}")
    private String weburl;
    @Value("${rate}")
    private int rate;


    @Override
    @ModelAttribute
    public void initAliPayApiConfig() {
        logger.info("init alipay config");
        AliPayApiConfig aliPayApiConfigKit = AliPayApiConfig.New()
                .setAppId(aliPayBean.getAppId())
                .setAlipayPublicKey(aliPayBean.getPublicKey())
                .setCharset("UTF-8")
                .setPrivateKey(aliPayBean.getPrivateKey())
                .setServiceUrl(aliPayBean.getServerUrl())
                .setSignType("RSA2")
                .build();
        AliPayApiConfigKit.setThreadLocalAliPayApiConfig(aliPayApiConfigKit);
    }

    /**
     * pc统一下单
     *
     * @param response
     * @param orderNo
     * @throws IOException
     * @throws AlipayApiException
     */
    @RequestMapping(value = "/alipay/unifiedorder/{orderNo}", method = RequestMethod.GET)
    public void pay(HttpServletResponse response, @PathVariable String orderNo) throws IOException, AlipayApiException {
        Order order = orderService.getOrderNo4PayByOrderNo(orderNo);
        String totalAmount = order.getActualPay().toString();

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();

        model.setOutTradeNo(order.getOrderNo());
        model.setProductCode(WEB_PRODUCT_CODE);
        model.setTotalAmount(totalAmount);
        model.setSubject(MessageFormat.format(BUY_COURSE_TEXT,order.getCourseNames()));
        model.setBody(MessageFormat.format(BUY_COURSE_TEXT,order.getCourseNames()));
        model.setTimeoutExpress(TIMEOUT_EXPRESS);

        PayMessage payMessage = new PayMessage();
        payMessage.setType(PayOrderType.COURSE_ORDER.getCode());
        payMessage.setUserId(order.getUserId());
        payMessage.setFrom(OrderFrom.PC.getCode());

        String passbackParams = PayMessage.getPayMessage(payMessage);
        model.setPassbackParams(passbackParams);

        AliPayApi.tradePage(response,model , weburl+AlipayConfig.notify_url, weburl+AlipayConfig.return_url);
    }

    @RequestMapping(value = "/alipay/recharge/{price}", method = RequestMethod.GET)
    public void recharge(HttpServletRequest request, HttpServletResponse response,@PathVariable String price) throws IOException, AlipayApiException {
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser();
        if(loginUser==null){
            throw new RuntimeException("充值需登录后进行");
        }
        Double count = Double.valueOf(price)*rate;
        if(!WebUtil.isIntegerForDouble(count)){
            throw new RuntimeException("充值金额"+price+"兑换的熊猫币"+count+"不为整数");
        }

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();

        String orderNo = OrderNoUtil.getCoinOrderNo();
        model.setOutTradeNo(orderNo);
        model.setProductCode(WEB_PRODUCT_CODE);
        model.setTotalAmount(price);
        model.setSubject(MessageFormat.format(BUY_COIN_TEXT,count));
        model.setBody(MessageFormat.format(BUY_COIN_TEXT,count));
        model.setTimeoutExpress(TIMEOUT_EXPRESS);

        PayMessage payMessage = new PayMessage();
        payMessage.setType(PayOrderType.COIN_ORDER.getCode());
        payMessage.setUserId(loginUser.getId());
        payMessage.setValue(new BigDecimal(count));
        payMessage.setFrom(OrderFrom.PC.getCode());

        String passbackParams = PayMessage.getPayMessage(payMessage);
        model.setPassbackParams(passbackParams);

        AliPayApi.tradePage(response,model , weburl+AlipayConfig.notify_url, weburl+AlipayConfig.recharge_jump_url);

    }


    /**
     * 关闭订单
     */
    @RequestMapping(value = "/alipay/tradeClose")
    @ResponseBody
    public String tradeClose(@RequestParam("out_trade_no") String outTradeNo){
        try {
            AlipayTradeCloseModel model = new AlipayTradeCloseModel();
            model.setOutTradeNo(outTradeNo);
            return AliPayApi.tradeCloseToResponse(model).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Description：pc端支付宝同步回调
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/19 0019 下午 3:44
     **/
    @RequestMapping(value = "/alipay/return_url")
    @ResponseBody
    public void return_url(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try {
            // 获取支付宝GET过来反馈信息
            Map<String, String> map = AliPayApi.toMap(request);
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey() + " = " + entry.getValue()+";");
            }
            logger.warn("return_url 回调信息:{}",sb.toString());

            boolean verify_result = AlipaySignature.rsaCheckV1(map, aliPayBean.getPublicKey(), "UTF-8","RSA2");
            if (verify_result) {
                logger.info("return_url 验证成功");
                Order order = orderService.getOrderByOrderNo(map.get("out_trade_no"));
                String page = "/my#menu4";
                if(order != null){
                    page = "/order/pay/success?orderId="+order.getId();
                }
                String url = "<script>window.open('" + weburl + page + "','_self')</script>";
                response.getWriter().println(url);
            } else {
                logger.error("return_url 验证失败");
                response.getWriter().println("验签失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            response.getWriter().println("验签失败");
        }
    }

    /**
     * Description：pc端支付宝异步回调
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/19 0019 下午 3:44
     **/
    @RequestMapping(value = "/alipay/notify_url")
    @ResponseBody
    public String  notify_url(HttpServletRequest request) {
        try {
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = AliPayApi.toMap(request);
            boolean verify_result = AlipaySignature.rsaCheckV1(params, aliPayBean.getPublicKey(), "UTF-8","RSA2");
            if (verify_result) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    sb.append(entry.getKey() + " = " + entry.getValue()+";");
                }
                logger.warn("notify_url 验证成功:{}",sb.toString());
                payService.aliPayBusiness(params);
                return "success";
            } else {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    sb.append(entry.getKey() + " = " + entry.getValue()+";");
                }
                logger.error("notify_url 验证失败:{}",sb.toString());
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