package com.xczhihui.bxg.online.web.controller.pay;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.bxg.online.web.base.utils.WebUtil;
import com.xczhihui.bxg.online.web.utils.alipay.AlipayConfig;
import com.xczhihui.common.util.OrderNoUtil;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.OrderFrom;
import com.xczhihui.common.util.enums.PayOrderType;
import com.xczhihui.course.model.Order;
import com.xczhihui.course.service.IOrderService;
import com.xczhihui.course.vo.PayMessage;
import com.xczhihui.online.api.service.PayService;
import com.xczhihui.pay.alipay.AliPayApi;
import com.xczhihui.pay.alipay.AliPayApiConfig;
import com.xczhihui.pay.alipay.AliPayApiConfigKit;
import com.xczhihui.pay.alipay.AliPayBean;
import com.xczhihui.pay.alipay.controller.AliPayApiController;

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
        model.setSubject(MessageFormat.format(BUY_COURSE_TEXT, order.getCourseNames()));
        model.setBody(MessageFormat.format(BUY_COURSE_TEXT, order.getCourseNames()));
        model.setTimeoutExpress(TIMEOUT_EXPRESS);

        PayMessage payMessage = new PayMessage();
        payMessage.setType(PayOrderType.COURSE_ORDER.getCode());
        payMessage.setUserId(order.getUserId());
        payMessage.setFrom(OrderFrom.PC.getCode());

        String passbackParams = PayMessage.getPayMessage(payMessage);
        model.setPassbackParams(passbackParams);

        AliPayApi.tradePage(response, model, weburl + AlipayConfig.notify_url, weburl + AlipayConfig.return_url);
    }

    @RequestMapping(value = "/alipay/recharge/{price}", method = RequestMethod.GET)
    public void recharge(HttpServletRequest request, HttpServletResponse response, @PathVariable String price) throws IOException, AlipayApiException {
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser();
        if (loginUser == null) {
            throw new RuntimeException("充值需登录后进行");
        }
        Double count = Double.valueOf(price) * rate;
        if (!WebUtil.isIntegerForDouble(count)) {
            throw new RuntimeException("充值金额" + price + "兑换的熊猫币" + count + "不为整数");
        }

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();

        String orderNo = OrderNoUtil.getCoinOrderNo();
        model.setOutTradeNo(orderNo);
        model.setProductCode(WEB_PRODUCT_CODE);
        model.setTotalAmount(price);
        model.setSubject(MessageFormat.format(BUY_COIN_TEXT, count));
        model.setBody(MessageFormat.format(BUY_COIN_TEXT, count));
        model.setTimeoutExpress(TIMEOUT_EXPRESS);

        PayMessage payMessage = new PayMessage();
        payMessage.setType(PayOrderType.COIN_ORDER.getCode());
        payMessage.setUserId(loginUser.getId());
        payMessage.setValue(new BigDecimal(count));
        payMessage.setFrom(OrderFrom.PC.getCode());

        String passbackParams = PayMessage.getPayMessage(payMessage);
        model.setPassbackParams(passbackParams);

        AliPayApi.tradePage(response, model, weburl + AlipayConfig.notify_url, weburl + AlipayConfig.recharge_jump_url);

    }

    /**
     * 
    * @Title: rechargeQrCode
    * @Description:扫码支付
    * @param @param request
    * @param @param response
    * @param @param price
    * @param @return
    * @param @throws IOException
    * @param @throws AlipayApiException    参数
    * @return ResponseObject    返回类型
    * @author yangxuan
    * @throws
     */
    @RequestMapping(value = "/alipay/rechargeQrCode/{price}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject rechargeQrCode(HttpServletRequest request, HttpServletResponse response, 
    		@PathVariable String price) throws IOException, AlipayApiException {
    	
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser();
        if (loginUser == null) {
            throw new RuntimeException("充值需登录后进行");
        }
        Double count = Double.valueOf(price) * rate;
        if (!WebUtil.isIntegerForDouble(count)) {
            throw new RuntimeException("充值金额" + price + "兑换的熊猫币" + count + "不为整数");
        }

        PayMessage payMessage = new PayMessage();
        payMessage.setType(PayOrderType.COIN_ORDER.getCode());
        
        payMessage.setUserId(loginUser.getId());
        payMessage.setValue(new BigDecimal(count));
        payMessage.setFrom(OrderFrom.PC.getCode());
        
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        String orderNo = OrderNoUtil.getCoinOrderNo();
        model.setOutTradeNo(orderNo);
        model.setTotalAmount(price);
        model.setSubject(MessageFormat.format(BUY_QRCODE_COIN_TEXT, count));
        model.setBody(PayMessage.getPayMessage(payMessage));
        model.setTimeoutExpress(TIMEOUT_EXPRESS);
        model.setQrCodeTimeoutExpress(TIMEOUT_EXPRESS);
        
        String body = AliPayApi.tradePrecreatePay(model, weburl + AlipayConfig.notify_url);
        
        return ResponseObject.newSuccessResponseObject(JSONObject.parse(body));
    }
    
    
    /**
     * 关闭订单
     */
    @RequestMapping(value = "/alipay/tradeClose")
    @ResponseBody
    public String tradeClose(@RequestParam("out_trade_no") String outTradeNo) {
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
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/19 0019 下午 3:44
     **/
    @RequestMapping(value = "/alipay/return_url")
    @ResponseBody
    public void return_url(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // 获取支付宝GET过来反馈信息
            Map<String, String> map = AliPayApi.toMap(request);
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey() + " = " + entry.getValue() + ";");
            }
            logger.warn("warn   return_url 回调信息:{}", sb.toString());

            boolean verify_result = AlipaySignature.rsaCheckV1(map, aliPayBean.getPublicKey(), "UTF-8", "RSA2");
            if (verify_result) {
                logger.info("return_url 验证成功");
                Order order = orderService.getOrderByOrderNo(map.get("out_trade_no"));
                String page = "/my#menu4";
                if (order != null) {
                    page = "/order/pay/success?orderId=" + order.getId();
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
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/19 0019 下午 3:44
     **/
    @RequestMapping(value = "/alipay/notify_url")
    @ResponseBody
    public String notify_url(HttpServletRequest request) {
        try {
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = AliPayApi.toMap(request);
            boolean verify_result = AlipaySignature.rsaCheckV1(params, aliPayBean.getPublicKey(), "UTF-8", "RSA2");
            if (verify_result) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    sb.append(entry.getKey() + " = " + entry.getValue() + ";");
                }
                logger.warn("notify_url 验证成功:{}", sb.toString());
                logger.error("notify_url 验证成功:{}", sb.toString());
                payService.aliPayBusiness(params);
                return "success";
            } else {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    sb.append(entry.getKey() + " = " + entry.getValue() + ";");
                }
                logger.error("notify_url 验证失败:{}", sb.toString());
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failure";
        } catch (Exception e) {
            return "failure";
        }
    }

    public static void main(String[] args) throws AlipayApiException {
		
    	
//    	AliPayApiConfig aliPayApiConfigKit = AliPayApiConfig.New()
//                .setAppId(aliPayBean.getAppId())
//                .setAlipayPublicKey(aliPayBean.getPublicKey())
//                .setCharset("UTF-8")
//                .setPrivateKey(aliPayBean.getPrivateKey())
//                .setServiceUrl(aliPayBean.getServerUrl())
//                .setSignType("RSA2")
//                .build();
    	
    	AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
    			"2017072807932656","MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQChsJLO96VFCaRCLozFDbk+w1X0te3s3I7bJ8c3igZsstFAbm8WDb5Y/h41BOGeVC2LOSsjZQC2awcs/fso2QpFPGTBlk5kcMgT4ezMijCSKKM2pK9Cvh97/02YO0g/VkjX6AZFVd77t+XwrkPOiygb9PyEDq/jIvO8WcIucdP771r/ZvE89NRypa9kAcr41twDYPWY62mNs2YyjYmTLQd5mFsi/4BBatdPz0XvtXA1kmSRUhvUcvzmCm6jk0RwXVy6Ea7Z0Ceu+4pfnc/aJFfcKJv6UJQQ3rmipmwinhYi2nJ4bVtIgx+ASNlXINWt3bTJmGMZqFVtCZ3l49yCOzfBAgMBAAECggEAMuyrAFaNDfZgbpu8qF+PJY5eJymZmw1ITQv1Oa/WICwdrZ5ajGadueenWemEqdo3Ue8agBZSqCGDbA8+KHpbOr0vuqz9WbMPwPtaGn23mIEGDrLFpE6/Gc2qAbVCJvilDqM8PmAyT7N2z1wDbSz04AFD+s+pY+9hNsRKXVhqfKFA71nJ4rT8EUBYGbhJLh7Gme8nS583umoWVwjHrvPLqfZe9QB576kbWwCJOBqXKStpTYea9bNZUWbtw20hGrDfCMyyzjUL3K0ecezL4vxTaSGR6unpcG9TdJfBq6KmIKJG/cyejI2R9NjhXfLHOCCqLc6BHe0qkNbC+fitLTCtVQKBgQDYfD9W7AX/jpCy3RvgF+vpB7c3uUS771lTtQ07nMn90Yavx/1mk2UodYymL2LRubCH1M9SHltdk9lBxe70YiPlhIJJZeCOs1CUErtfDoYnKERhwpGx/1s44qjs9ooPcSdNAKoRDjFXNIQPnw6+k1UzQ0dvrGXp/7/r0pnblJWviwKBgQC/M+Gf/YcZkrQ+GSGWUDS5AUWjZadbIlRCLSTW8GDMMVbmrzc+7DuMAA1QuhyHS1HDnz38NeJBEK3Uqs4qIYXn2lF5u2z/l6eAFEOd1OFFPE6NrjqUy+nslAuoO2QvuwJQ8AjjcjdPJKc66zRRrovNHLAkGextT+Ci5eNIxxOfYwKBgQC/XFz07d+DfjcEFJVOanbbXzmipT9PzQwuBS20UyzuE2c2PNcO9B2IPRhd0idM8hJMj13P3guvVUDHdjp6hcHrYU11qftsyK7ipQhBx2nodRy1ObNmHy44w4rFJEz3x3MRCxRJzTzqM/7EfDohVcULcl5UJZVU2gCBaYEda2NBbwKBgBuVsZRycD5JQwW+fHECK0kRnOlg7g8g2cUeXDVCQsTSzXXEi5ThYgnlrAYcg6clP6uYWsn7QCQg8uM+rTW41mfHwH9ugeAyEfFRexvXLZTeiXq5SyxSavI9vZzMzLxyH3hr2OxvevlJEXNXoZmzM+oonGTo9IokvwThY7QJPJR/AoGBAMpoH2mmmIpNZnAkJw82YS0PfUy3bF7nBUU5mEnZiEVnrMY10uqgohSDt+wslbpx2dL7NoRrSx3K5l5sEV4QNv/u1FZMF0cUSlXY78LTiLiPLZtmvXLmbZhmNag/irMXJ0pZ8Q5xOrO0Na4nuLPOfDrtXK1q2FIKeYDIUDgzvVli",
    			"json","UTF-8","MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuzLNkQQ41cDv/azbuctIziPqUVDbpaT3r5NT5d6mhaDQ8m1v3UtFa0rM7oV4XiLhM8O0uBH6Dx0U0eb9izOjE2yIDWT8FJaraOKf6ncscpfawZX79TDg+L+531uBUFExsrhNaCZDNpmmREyXkPkpXHIlYDTpuUzwdJpXtaQPE9B8yeVsrixdEAT5XnSfyhqP/KJeo8P8Axj7w3aTY5vjduLXVfBOyOTVw/5bx2LfqFPkUl12xIr3L0KE1tSVAzdGrTEReWQDPkOU5Q7FdLsck+FCquadcZtg/Kj5d07dD/i++VeThK8yB6DaQ/dUloMTmwvYxclFnkGfGjR8qFXjYQIDAQAB",
    			"RSA2");
    	
    	AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
    	request.setBizContent("{" +
    	"\"out_trade_no\":\"2015032001011111001\"," +
    	"\"total_amount\":88.88," +
    	"\"subject\":\"Iphone6 16G\"," +
    	"\"body\":\"Iphone6 16G\"," +
    	"\"qr_code_timeout_express\":\"90m\"" +
    	"  }");
    	AlipayTradePrecreateResponse response = alipayClient.execute(request);
    	System.out.println(response.getBody());
    	if(response.isSuccess()){
    	System.out.println("调用成功");
    	} else {
    	System.out.println("调用失败");
    	}
    	
    	
    	
	}
    
}
