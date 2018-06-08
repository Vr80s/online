package com.xczhihui.bxg.online.web.controller.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.utils.WebUtil;
import com.xczhihui.bxg.online.web.utils.alipay.AlipayConfig;
import com.xczhihui.common.util.OrderNoUtil;
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
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }

            boolean verify_result = AlipaySignature.rsaCheckV1(map, aliPayBean.getPublicKey(), "UTF-8","RSA2");
            if (verify_result) {
                logger.info("return_url 验证成功");
                response.getWriter().println("<script>window.open('" + weburl + "/web/html/personal-center/personal-index.html"+"','_self')</script>");
            } else {
                logger.info("return_url 验证失败");
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
