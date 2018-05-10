package com.xczhihui.bxg.online.web.controller.pay;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.xczhihui.common.util.IStringUtil;
import com.xczhihui.common.util.OrderNoUtil;
import com.xczhihui.common.util.enums.PayOrderType;
import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.utils.WebUtil;
import com.xczhihui.online.api.service.PayService;
import com.xczhihui.bxg.online.web.utils.MatrixToImageWriter;
import com.xczhihui.pay.ext.kit.HttpKit;
import com.xczhihui.pay.ext.kit.IpKit;
import com.xczhihui.pay.ext.kit.PaymentKit;
import com.xczhihui.pay.ext.kit.StrKit;
import com.xczhihui.pay.weixin.api.*;
import com.xczhihui.pay.weixin.api.WxPayApi.TradeType;
import com.xczhihui.pay.weixin.api.WxPayApiConfig.PayModel;
import com.xczhihui.course.model.Order;
import com.xczhihui.course.service.IOrderService;
import com.xczhihui.course.vo.PayMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Controller
@RequestMapping("/web/wxPay")
public class WxPayController extends WxPayApiController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	public static final String BUY_COURSE_TEXT = "购买课程{0}";
	public static final String BUY_COIN_TEXT = "充值熊猫币:{0}个";

	@Autowired
	private WxPay4PcBean wxPayBean;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private PayService payService;

	@Value("${rate}")
	private int rate;
	String notify_url;

	@Override
	@ModelAttribute
	public void initWxPayApiConfig() {
		log.info("init wxpay config");
		notify_url = wxPayBean.getDomain().concat("/web/wxPay/pay_notify");
		WxPayApiConfig wxPayApiConfig = WxPayApiConfig.New()
				.setAppId(wxPayBean.getAppId())
				.setMchId(wxPayBean.getMchId())
				.setPaternerKey(wxPayBean.getPartnerKey())
				.setPayModel(PayModel.BUSINESSMODEL);
		WxPayApiConfigKit.setThreadLocalWxPayApiConfig(wxPayApiConfig);
	}

	/**
	 * 生成支付二维码并在页面上显示
	 */
	@RequestMapping("/{orderNo}")
	@ResponseBody
	public ModelAndView scanCode(HttpServletRequest request,  @PathVariable String orderNo) throws WriterException {
		ModelAndView m = new ModelAndView("/ScanCodePay");
		if (request.getParameter("preurl") != null) {
			m.addObject("preurl", request.getParameter("preurl"));
		}

		Order order = orderService.getOrderNo4PayByOrderNo(orderNo);

		String ip = IpKit.getRealIp(request);
		if (StrKit.isBlank(ip)) {
			ip = "127.0.0.1";
		}

		String totalAmount = order.getActualPay().toString();

		PayMessage payMessage = new PayMessage();
		payMessage.setType(PayOrderType.COURSE_ORDER.getCode());
		payMessage.setUserId(order.getUserId());

		String attach = PayMessage.getPayMessage(payMessage);
		Map<String, String> params = WxPayApiConfigKit.getWxPayApiConfig()
				.setAttach(attach)
				.setBody(MessageFormat.format(BUY_COURSE_TEXT,order.getCourseNames()))
				.setSpbillCreateIp(ip)
				.setTotalFee((int) (order.getActualPay()*100)+"")
				.setTradeType(TradeType.NATIVE)
				.setNotifyUrl(notify_url)
				.setOutTradeNo(getorderNo4Pay(orderNo))
				.build();

		String xmlResult = WxPayApi.pushOrder(false,params);

		log.info(xmlResult);
		Map<String, String> resultMap = PaymentKit.xmlToMap(xmlResult);

		String return_code = resultMap.get("return_code");
		String return_msg = resultMap.get("return_msg");
		if (!PaymentKit.codeIsOK(return_code)) {
			System.out.println(xmlResult);
			m.addObject("errorMsg"+return_msg);
			return m;
		}
		String result_code = resultMap.get("result_code");
		if (!PaymentKit.codeIsOK(result_code)) {
			System.out.println(xmlResult);
			m.addObject("errorMsg"+return_msg);
			return m;
		}
		//生成预付订单success

		String qrCodeUrl = resultMap.get("code_url");

		m.addObject("codeimg", encodeQrcode(qrCodeUrl));
		m.addObject("orderNo", order.getOrderNo());
		m.addObject("couseName", order.getCourseNames());
		m.addObject("price", totalAmount);
		System.out.println(qrCodeUrl);
		return m;
	}

	/**
	 * 生成支付二维码并在页面上显示--充值
	 */
	@RequestMapping("/recharge/{price}")
	@ResponseBody
	public ModelAndView scanCode4Recharge(HttpServletRequest request,  @PathVariable String price) throws WriterException {
		ModelAndView m = new ModelAndView("/ScanCodePay");
		m.addObject("recharge", "recharge");
		if (request.getParameter("preurl") != null) {
			m.addObject("preurl", request.getParameter("preurl"));
		}

		OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
		if(loginUser==null){
			throw new RuntimeException("充值需登录后进行");
		}
		Double count = Double.valueOf(price)*rate;
		if(!WebUtil.isIntegerForDouble(count)){
			throw new RuntimeException("充值金额"+price+"兑换的熊猫币"+count+"不为整数");
		}

		String ip = IpKit.getRealIp(request);
		if (StrKit.isBlank(ip)) {
			ip = "127.0.0.1";
		}

		PayMessage payMessage = new PayMessage();
		payMessage.setType(PayOrderType.COIN_ORDER.getCode());
		payMessage.setUserId(loginUser.getId());
		payMessage.setValue(new BigDecimal(count));

		String orderNo = OrderNoUtil.getCoinOrderNo();
		String attach = PayMessage.getPayMessage(payMessage);
		Map<String, String> params = WxPayApiConfigKit.getWxPayApiConfig()
				.setAttach(attach)
				.setBody(MessageFormat.format(BUY_COIN_TEXT,count))
				.setSpbillCreateIp(ip)
				.setTotalFee((int) (Double.valueOf(price)*100)+"")
				.setTradeType(TradeType.NATIVE)
				.setNotifyUrl(notify_url)
				.setOutTradeNo(orderNo)
				.build();

		String xmlResult = WxPayApi.pushOrder(false,params);

		log.info(xmlResult);
		Map<String, String> resultMap = PaymentKit.xmlToMap(xmlResult);

		String return_code = resultMap.get("return_code");
		String return_msg = resultMap.get("return_msg");
		if (!PaymentKit.codeIsOK(return_code)) {
			log.info(xmlResult);
			m.addObject("errorMsg"+return_msg);
			return m;
		}
		String result_code = resultMap.get("result_code");
		if (!PaymentKit.codeIsOK(result_code)) {
			log.info(xmlResult);
			m.addObject("errorMsg"+return_msg);
			return m;
		}
		//生成预付订单success
		String qrCodeUrl = resultMap.get("code_url");

		m.addObject("codeimg", encodeQrcode(qrCodeUrl));
		m.addObject("orderNo", orderNo);
		m.addObject("couseName", MessageFormat.format(BUY_COIN_TEXT,count));
		m.addObject("price", price);
		return m;
	}

    private String getorderNo4Pay(String orderNo) {
	    return orderNo+IStringUtil.getRandomString();
    }

    /**
     * Description：支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/20 0020 下午 2:51
     **/
	@RequestMapping("/pay_notify")
	@ResponseBody
	public String pay_notify(HttpServletRequest request) throws Exception {
		String xmlMsg = HttpKit.readData(request);
		log.info("支付回调通知="+xmlMsg);
		Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);

		if(PaymentKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey())){
			String result_code  = params.get("result_code");
			if (("SUCCESS").equals(result_code)) {
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
            return "data:image/png;base64,"+out.toString(StandardCharsets.UTF_8.name());
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
