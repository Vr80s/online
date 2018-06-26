package com.xczh.consumer.market.controller.pay;

import static com.xczhihui.pay.alipay.controller.AliPayApiController.BUY_COIN_TEXT;
import static com.xczhihui.pay.alipay.controller.AliPayApiController.BUY_COURSE_TEXT;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.WebUtil;
import com.xczh.consumer.market.wxpay.entity.PayInfo;
import com.xczhihui.common.util.IStringUtil;
import com.xczhihui.common.util.OrderNoUtil;
import com.xczhihui.common.util.enums.OrderFrom;
import com.xczhihui.common.util.enums.PayOrderType;
import com.xczhihui.course.model.Order;
import com.xczhihui.course.service.IOrderService;
import com.xczhihui.course.vo.PayMessage;
import com.xczhihui.online.api.service.PayService;
import com.xczhihui.pay.ext.kit.HttpKit;
import com.xczhihui.pay.ext.kit.IpKit;
import com.xczhihui.pay.ext.kit.PaymentKit;
import com.xczhihui.pay.util.MatrixToImageWriter;
import com.xczhihui.pay.weixin.api.H5ScencInfo;
import com.xczhihui.pay.weixin.api.WxPayApi;
import com.xczhihui.pay.weixin.api.WxPayApiConfig;
import com.xczhihui.pay.weixin.api.WxPayApiConfigKit;
import com.xczhihui.pay.weixin.api.WxPayBean;


/**
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
    private WxcpClientUserWxMappingService wxcpClientUserWxMappingService;
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
        if (appPay) {
            log.info("========app=======");
            log.info("appi" + wxPayBean.getAppId4App());
            log.info("mchid" + wxPayBean.getMchId4App());
            log.info("paternerekey" + wxPayBean.getPartnerKey4App());
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
     *  orderFrom 3:微信 4：h5 5 app
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/23 0023 下午 5:52
     **/
    @RequestMapping("wxPay")
    @ResponseBody
    public ResponseObject appOrderPay(HttpServletRequest req,
                                      @RequestParam("orderId") String orderId,
                                      @RequestParam("orderFrom") String orderFrom, @Account String accountId) throws Exception {
        Order order = orderService.getOrderNo4PayByOrderId(orderId);

        int actualPay = (int) (order.getActualPay() * 100);

        int orderFromI = Integer.parseInt(orderFrom);
        boolean appPay = false;

        PayMessage payMessage = new PayMessage();
        payMessage.setType(PayOrderType.COURSE_ORDER.getCode());
        payMessage.setUserId(order.getUserId());
        if (orderFromI == 5) {
            appPay = true;
            //app端目前仅安卓端调用
            payMessage.setFrom(OrderFrom.ANDROID.getCode());
        }else{
            payMessage.setFrom(OrderFrom.H5.getCode());
        }
        WxPayApiConfigKit.setThreadLocalWxPayApiConfig(getApiConfig(appPay));

        String ip = IpKit.getRealIp(req);
        String openId = req.getParameter("openId");
        if (StringUtils.isBlank(openId)) {
            openId = getWxOpenId(accountId);
        }

        String attach = PayMessage.getPayMessage(payMessage);

        Map<String, String> payParams = getPayParams(orderFromI, order.getOrderNo() + IStringUtil.getRandomString(), ip, actualPay + "", openId, attach, MessageFormat.format(BUY_COURSE_TEXT, order.getCourseNames()));
        for (Map.Entry<String, String> entry : payParams.entrySet()) {
            log.info(entry.getKey() + " = " + entry.getValue());
        }

        String xmlResult = WxPayApi.pushOrder(false, payParams);
        log.warn("xmlResult:{}",xmlResult);
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);

        String return_code = result.get("return_code");
        String return_msg = result.get("return_msg");
        if (!PaymentKit.codeIsOK(return_code)) {
            log.error("return_code>" + return_code + " return_msg>" + return_msg);
            throw new RuntimeException(return_msg);
        }
        String result_code = result.get("result_code");
        if (!PaymentKit.codeIsOK(result_code)) {
            log.error("result_code>" + result_code + " return_msg>" + return_msg);
            throw new RuntimeException(return_msg);
        }
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回

        String prepay_id = result.get("prepay_id");
        String mweb_url = result.get("mweb_url");

        log.warn("prepay_id:{}", prepay_id);
        log.warn("mweb_url:{}", mweb_url);

        if (result != null) {
            result.put("ok", "true");
            /**
             * app支付需要进行二次签名
             */
            if (orderFromI == 5) {
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
                return ResponseObject.newSuccessResponseObject(packageParams);
            } else if (orderFromI == 3) {
                Map<String, String> param = new HashMap<>();
                param.put("appId", result.get("appid"));
                param.put("nonceStr", result.get("nonce_str"));
                String preid = result.get("prepay_id");
                param.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
                param.put("package", "prepay_id=" + preid);
                param.put("signType", "MD5");
                param.put("paySign", PaymentKit.createSign(param, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey()));
                return ResponseObject.newSuccessResponseObject(param);
            }else if(orderFromI == 6) {
            	Map<String, String> param = new HashMap<>();
            	
            	String qrCodeUrl = result.get("code_url");
            	
            	param.put("codeimg", encodeQrcode(qrCodeUrl));
            	param.put("orderNo", order.getOrderNo());
            	param.put("courseName", order.getCourseNames());
            	param.put("price", actualPay+"");
            	param.put("orderId", order.getId());
            	
            	return ResponseObject.newSuccessResponseObject(param);
            }
            return ResponseObject.newSuccessResponseObject(result);
        }
        return ResponseObject.newErrorResponseObject("请求错误");
    }

    /**
     * Description：微信支付-充值
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/23 0023 下午 5:52
     **/
    @RequestMapping("rechargePay")
    @ResponseBody
    public ResponseObject rechargePay(HttpServletRequest request, HttpServletResponse response, @RequestParam("clientType") Integer clientType,
                                      @RequestParam("actualPay") String actualPay, @Account String accountId) throws Exception {
        Double count = Double.valueOf(actualPay) * rate;
        if (!WebUtil.isIntegerForDouble(count)) {
            throw new RuntimeException("充值金额" + actualPay + "兑换的熊猫币" + count + "不为整数");
        }

        int total = (int) (Double.valueOf(actualPay) * 100);

        int orderFromI = Integer.valueOf(clientType);
        boolean appPay = false;

        PayMessage payMessage = new PayMessage();
        payMessage.setType(PayOrderType.COIN_ORDER.getCode());
        payMessage.setUserId(accountId);
        payMessage.setValue(new BigDecimal(count));
        if (orderFromI == 5) {
            appPay = true;
            //app端目前仅安卓端调用
            payMessage.setFrom(OrderFrom.ANDROID.getCode());
        }else{
            payMessage.setFrom(OrderFrom.H5.getCode());
        }
        WxPayApiConfig apiConfig = getApiConfig(appPay);
        log.info("appi" + apiConfig.getAppId());
        log.info("mchid" + apiConfig.getMchId());
        log.info("paternerekey" + apiConfig.getPaternerKey());
        WxPayApiConfigKit.setThreadLocalWxPayApiConfig(apiConfig);

        String ip = IpKit.getRealIp(request);
        String openId = request.getParameter("openId");
        if (StringUtils.isBlank(openId)) {
            openId = getWxOpenId(accountId);
        }

        String attach = PayMessage.getPayMessage(payMessage);

        String orderNo = OrderNoUtil.getCoinOrderNo();

        Map<String, String> payParams = getPayParams(orderFromI, orderNo, ip, total + "", openId, attach, MessageFormat.format(BUY_COIN_TEXT, count));
        for (Map.Entry<String, String> entry : payParams.entrySet()) {
            log.error(entry.getKey() + " = " + entry.getValue());
        }

        String xmlResult = WxPayApi.pushOrder(false, payParams);
        log.warn("xmlResult:{}",xmlResult);
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);

        String return_code = result.get("return_code");
        String return_msg = result.get("return_msg");
        if (!PaymentKit.codeIsOK(return_code)) {
            log.error("return_code>" + return_code + " return_msg>" + return_msg);
            throw new RuntimeException(return_msg);
        }
        String result_code = result.get("result_code");
        if (!PaymentKit.codeIsOK(result_code)) {
            log.error("result_code>" + result_code + " return_msg>" + return_msg);
            throw new RuntimeException(return_msg);
        }
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回

        String prepay_id = result.get("prepay_id");
        String mweb_url = result.get("mweb_url");

        log.warn("mweb_url:{}", mweb_url);
        log.warn("prepay_id:{}", prepay_id);

        if (result != null) {
            result.put("ok", "true");
            /**
             * app支付需要进行二次签名
             */
            if (orderFromI == 5) {
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
                packageParams.put("sign", packageSign);

                return ResponseObject.newSuccessResponseObject(packageParams);
            } else if (orderFromI == 3) {
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

    
    
    
    
    @RequestMapping(value = "pay_notify")
    @ResponseBody
    public String wxNotify(HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        // 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7

        String xmlMsg = HttpKit.readData(req);
        log.warn("wechat服务微信支付回调通知:{}", xmlMsg);
        Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
        String resultCode = params.get("result_code");

        // 交易类型
        String tradeType = params.get("trade_type");
        //根据支付类型判断得到--》回调验证签名的key
        //h5和公众号用的是一样了。
        //app用的是另一个
        if (PayInfo.TRADE_TYPE_APP.equals(tradeType)) {
            WxPayApiConfigKit.setThreadLocalWxPayApiConfig(getApiConfig(true));
        } else {
            WxPayApiConfigKit.setThreadLocalWxPayApiConfig(getApiConfig(false));
        }

        if (PaymentKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey())) {
            if (("SUCCESS").equals(resultCode)) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    sb.append(entry.getKey() + " = " + entry.getValue()+";");
                }
                log.warn("wechat服务微信支付回调:{}",sb.toString());
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

    public Map<String, String> getPayParams(Integer orderFromI, String orderNo, String ip, String actualPay, String openId, String attach, String body) {
        WxPayApiConfig wxPayApiConfig = WxPayApiConfigKit.getWxPayApiConfig()
                .setAttach(attach)
                .setBody(body)
                .setSpbillCreateIp(ip)
                .setTotalFee(actualPay)
                .setNotifyUrl(notify_url)
                .setOutTradeNo(orderNo);
        if (orderFromI == 3) {
            if (!StringUtils.isNotBlank(openId)) {
                throw new RuntimeException("尝试下重新登录,或者关注公众号!");
            }
            Map<String, String> params = wxPayApiConfig.setOpenId(openId).setTradeType(WxPayApi.TradeType.JSAPI).build();
            return params;
        } else if (orderFromI == 4) {
            H5ScencInfo sceneInfo = new H5ScencInfo();
            H5ScencInfo.H5 h5Info = new H5ScencInfo.H5();
            h5Info.setType("Wap");
            //此域名必须在商户平台--"产品中心"--"开发配置"中添加
            h5Info.setWap_url(wxPayBean.getDomain());
            h5Info.setWap_name("熊猫中医");
            sceneInfo.setH5_info(h5Info);

            Map<String, String> params = wxPayApiConfig.setTradeType(WxPayApi.TradeType.MWEB).setSceneInfo(h5Info.toString()).build();
            return params;
        } else if (orderFromI == 5) {
            Map<String, String> params = wxPayApiConfig.setTradeType(WxPayApi.TradeType.APP).build();
            return params;
        } else if (orderFromI == 6) {
        	Map<String, String> params = wxPayApiConfig.setTradeType(WxPayApi.TradeType.NATIVE).build();
            return params;
        }
        return null;
    }

    private String getWxOpenId(String userId) {
        try {
            WxcpClientUserWxMapping wxcpClientUserWxMapping = wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByUserId(userId);
            return wxcpClientUserWxMapping == null ? null : wxcpClientUserWxMapping.getOpenid();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private String encodeQrcode(String urlCode) throws WriterException {
        int width = 300; // 二维码图片宽度
        int height = 300; // 二维码图片高度

        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());// 内容所使用字符集编码

        BitMatrix bitMatrix = new MultiFormatWriter().encode(urlCode, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream out = null;
        // 输出二维码图片流
        try {
            out = new ByteArrayOutputStream();
            ImageIO.write(image, "png", Base64.getEncoder().wrap(out));
            return "data:image/png;base64," + out.toString(StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
    
}
