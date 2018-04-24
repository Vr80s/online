package com.xczhihui.bxg.online.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xczhihui.bxg.online.api.service.OrderPayService;
import com.xczhihui.bxg.common.util.enums.OrderFrom;
import com.xczhihui.bxg.common.util.enums.Payment;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.RewardStatement;
import com.xczhihui.bxg.online.common.domain.UserCoinIncrease;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.common.domain.AlipayPaymentRecord;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.Reward;
import com.xczhihui.bxg.online.web.base.utils.RandomUtil;
import com.xczhihui.bxg.online.web.base.utils.TimeUtil;
import com.xczhihui.bxg.online.web.base.utils.WebUtil;
import com.xczhihui.bxg.online.web.exception.XcApiException;
import com.xczhihui.bxg.online.web.service.AliPayPaymentRecordService;
import com.xczhihui.bxg.online.web.service.OrderService;
import com.xczhihui.bxg.online.web.service.RewardService;
import com.xczhihui.bxg.online.web.utils.alipay.AlipayConfig;
import com.xczhihui.bxg.online.web.vo.OrderParamVo;
import com.xczhihui.bxg.online.web.vo.RewardParamVo;

/**
 * 支付宝相关接口
 *
 * @author liutao 【jvmtar@gmail.com】
 * @create 2017-08-19 10:33
 **/
@Controller
@RequestMapping(value = "/web1")
public class AlipayController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderPayService orderPayService;

    @Autowired
    private AliPayPaymentRecordService aliPayPaymentRecordService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private UserCoinService userCoinService;

    @Value("${online.web.url}")
    private String weburl;
    
    @Value("${alipay.app.id}")
    private String app_id;
    @Value("${alipay.merchant.private.key}")
    private String merchant_private_key;
    @Value("${alipay.alipay.public.key}")
    private String alipay_public_key;
    @Value("${alipay.url}")
    private String gatewayUrl;

    @Value("${rate}")
    private int rate;
    @Value("${minimum_amount}")
    private Double minimumAmount;
//    private final int RATE=10;

    /**
     * pc统一下单
     *
     * @param request
     * @param response
     * @param orderNo
     * @throws IOException
     * @throws AlipayApiException
     */
    @RequestMapping(value = "/alipay/unifiedorder/{orderNo}", method = RequestMethod.GET)
    public void pay(HttpServletRequest request, HttpServletResponse response, @PathVariable String orderNo,String orderId) throws XcApiException {

        orderNo=orderService.findOrderByOrderId(orderId).getOrder_no();
        //会返回一个支付宝的html表单提交代码
        response.setContentType("text/html");

        Map<String, Object> payInfo = orderService.checkPayInfo(orderNo);
        String ap = null;

        if (payInfo.get("error_msg") != null) {
            throw  new XcApiException("订单检查未通过！原因：" + payInfo.get("error_msg"));
        }

        ap = payInfo.get("actual_pay").toString();
        if (ap.indexOf(".") >= 0 && ap.substring(ap.lastIndexOf(".")).length() < 3) {
            ap = ap + "0";
        }

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, "json", AlipayConfig.charset, alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(weburl+AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(weburl+AlipayConfig.notify_url);
        
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = new String(orderNo);
        //付款金额，必填
        String total_amount = new String(ap);
        //订单名称，必填
        String subject =new String("购买课程【"+payInfo.get("course_name").toString()+"】");
        //商品描述，可空
        String body = new String("");
        OrderParamVo orderParamVo=new OrderParamVo();
        orderParamVo.setUserId(payInfo.get("user_id").toString());
        String passbackParams="order&"+orderParamVo.getUserId();
        String timeoutExpress="24h";
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"passback_params\":\"" + passbackParams + "\","
                + "\"timeout_express\":\"" + timeoutExpress + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        try {
            //请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();

            //输出html
            response.getWriter().println(result);

        }catch (Exception ex){
            ex.printStackTrace();
            throw  new XcApiException("未知错误！");
        }
    }

    /**
     * pc打赏统一下单
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws AlipayApiException
     */
    @RequestMapping(value = "/alipay/reward", method = RequestMethod.GET)
    public void reward(HttpServletRequest request, HttpServletResponse response) throws XcApiException {

        //会返回一个支付宝的html表单提交代码
        response.setContentType("text/html");

        String rewardId = request.getParameter("rewardId").toString();
        String ap = null;
        ap = request.getParameter("price").toString();
        if (ap.indexOf(".") >= 0 && ap.substring(ap.lastIndexOf(".")).length() < 3) {
            ap = ap + "0";
        }

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, "json", AlipayConfig.charset, alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(weburl+AlipayConfig.close_url);
        alipayRequest.setNotifyUrl(weburl+AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
        //付款金额，必填
        String total_amount = new String(ap);
        //订单名称，必填
        String subject =new String("打赏");
        //商品描述，可空
        String body = new String("打赏");


        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
        RewardParamVo rewardParamVo=new RewardParamVo();
        rewardParamVo.setUserId(loginUser.getId());
        rewardParamVo.setClientType("1");
        rewardParamVo.setLiveId(request.getParameter("liveId"));
        rewardParamVo.setGiver(loginUser.getId());
        rewardParamVo.setReceiver(request.getParameter("receiver"));
        rewardParamVo.setUserId(loginUser.getId());

        if(Double.valueOf(ap)<0.01){
			throw new RuntimeException("打赏金额必须大于0.01");
		}
        Reward reward=rewardService.findRewardById(rewardId);
        if(reward==null){
        	throw new RuntimeException("无此打赏类型");
        }else if(!reward.getIsFreeDom()&&reward.getPrice().doubleValue()!=Double.valueOf(ap).doubleValue()){
        	throw new RuntimeException("打赏类型与金额不符");
        }
        rewardParamVo.setPrice(Double.valueOf(ap));
        rewardParamVo.setRewardId(rewardId);
        String passbackParams="reward&"+JSONObject.toJSON(rewardParamVo).toString().replaceAll("\"", "|");
        String timeoutExpress="24h";
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"passback_params\":\"" + passbackParams + "\","
                + "\"timeout_express\":\"" + timeoutExpress + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        try {
            //请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();

            //输出html
            response.getWriter().println(result);

        }catch (Exception ex){
            ex.printStackTrace();
            throw  new XcApiException("未知错误！");
        }
    }
    
    /**
     * pc充值统一下单
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws AlipayApiException
     */
    @RequestMapping(value = "/alipay/recharge/{price}/{orderNo}", method = RequestMethod.GET)
    public void recharge(HttpServletRequest request, HttpServletResponse response,@PathVariable String price,@PathVariable String orderNo) throws XcApiException {
    	
    	//会返回一个支付宝的html表单提交代码
    	response.setContentType("text/html");
    	
    	OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
    	Double count = Double.valueOf(price)*rate; 
    	if (price.indexOf(".") >= 0 && price.substring(price.lastIndexOf(".")).length() < 3) {
    		price = price + "0";
    	}
    	if(!WebUtil.isIntegerForDouble(count)){
			throw new RuntimeException("充值金额"+price+"兑换的熊猫币"+count+"不为整数");
		}
		if(minimumAmount > Double.valueOf(price)){
			throw new RuntimeException("充值金额低于最低充值金额："+minimumAmount);
		}
    	//获得初始化的AlipayClient
    	AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, "json", AlipayConfig.charset, alipay_public_key, AlipayConfig.sign_type);
    	
    	//设置请求参数
    	AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
    	alipayRequest.setReturnUrl(weburl+AlipayConfig.recharge_jump_url);
    	alipayRequest.setNotifyUrl(weburl+AlipayConfig.notify_url);
		
    	//商户订单号，商户网站订单系统中唯一订单号，必填
    	String out_trade_no = orderNo;
    	//付款金额，必填
    	String total_amount = new String(price);
    	//订单名称，必填
    	String subject =new String("充值熊猫币:"+count.intValue()+"个");
    	//商品描述，可空
    	String body = new String("");
    	
    	
    	UserCoinIncrease uci = new  UserCoinIncrease();
    	uci.setChangeType(1);
    	uci.setValue(new BigDecimal(count));
		uci.setUserId(loginUser.getId());
    	
       	String passbackParams="recharge&"+JSONObject.toJSON(uci).toString().replaceAll("\"", "|");
    	String timeoutExpress="24h";
    	alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
    			+ "\"total_amount\":\"" + total_amount + "\","
    			+ "\"subject\":\"" + subject + "\","
    			+ "\"body\":\"" + body + "\","
    			+ "\"passback_params\":\"" + passbackParams + "\","
    			+ "\"timeout_express\":\"" + timeoutExpress + "\","
    			+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
    	try {
    		//请求
    		String result = alipayClient.pageExecute(alipayRequest).getBody();
    		//输出html
    		response.getWriter().println(result);
    		
    	}catch (Exception ex){
    		ex.printStackTrace();
    		throw  new XcApiException("未知错误！");
    	}

    }

    /**
     * 同步回调
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/alipay/pay_notify")
    public void pay_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        
        /*线下课程跳转到线下课程详情---yuruixin---20170913*/
        String page="/web/html/myStudyCenter.html";
//        String orderNo = params.get("out_trade_no");
//        OrderVo order = orderService.findOrderByOrderNo(orderNo);
//        if(order!=null){
//        	CourseVo cv = courseService.findCourseOrderById(order.getCourse_id());
//        	if(cv!=null && cv.getOnlineCourse()==1){//线下课程
//        		page="/web/html/payRealCourseDetailPage.html?id="+cv.getId();
//        	}
//        }
        /*线下课程跳转到线下课程详情---yuruixin---20170913*/

        //同步回调返回的页面
        if (signVerified) {
            response.getWriter().println("<script>window.open('" + weburl + page+"','_self')</script>");
        } else {
            response.getWriter().println("验签失败");
        }

    }

    /**
     * 异步回调
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/alipay/notify_url")
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.info("进入支付宝异步回调");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
            logger.info("{}:{}",name,valueStr);
        }
        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
        logger.info("验证签名:{}",signVerified);
        if (signVerified) {
            //验证成功
            AlipayPaymentRecord alipayPaymentRecord = new AlipayPaymentRecord();
            alipayPaymentRecord.setTradeNo(params.get("trade_no"));
            alipayPaymentRecord.setAppId(params.get("app_id"));
            alipayPaymentRecord.setOutTradeNo(params.get("out_trade_no"));
            alipayPaymentRecord.setOutBizNo(params.get("out_biz_no"));
            alipayPaymentRecord.setBuyerId(params.get("buyer_id"));
            alipayPaymentRecord.setSellerId(params.get("seller_id"));
            alipayPaymentRecord.setTradeStatus(params.get("trade_status"));
            alipayPaymentRecord.setTotalAmount(params.get("total_amount"));
            alipayPaymentRecord.setReceiptAmount(params.get("receipt_amount"));
            alipayPaymentRecord.setInvoiceAmount(params.get("invoice_amount"));
            alipayPaymentRecord.setBuyerPayAmount(params.get("buyer_pay_amount"));
            alipayPaymentRecord.setPointAmount(params.get("point_amount"));
            alipayPaymentRecord.setRefundFee(params.get("refund_fee"));
            alipayPaymentRecord.setSubject(params.get("subject"));
            alipayPaymentRecord.setBody(params.get("body"));
            alipayPaymentRecord.setGmtCreate(params.get("gmt_create"));
            alipayPaymentRecord.setGmtPayment(params.get("gmt_payment"));
            alipayPaymentRecord.setGmtClose(params.get("gmt_close"));
            alipayPaymentRecord.setFundBillList(params.get("fund_bill_list"));
            alipayPaymentRecord.setVoucherDetailList(params.get("voucher_detail_list"));
            alipayPaymentRecord.setPassbackParams(params.get("passback_params"));
            try {
                String notifyType=alipayPaymentRecord.getPassbackParams().split("&")[0];
                if("order".equals(notifyType)){
                    alipayPaymentRecord.setUserId(alipayPaymentRecord.getPassbackParams().split("&")[1]);
                }else if("reward".equals(notifyType)){
                    RewardParamVo rewardParamVo = JSONObject.parseObject(alipayPaymentRecord.getPassbackParams().split("&")[1].replace("|", "\""),RewardParamVo.class);
                    alipayPaymentRecord.setUserId(rewardParamVo.getUserId());
                }else if("recharge".equals(notifyType)){
                    UserCoinIncrease userCoinIncrease=JSONObject.parseObject(alipayPaymentRecord.getPassbackParams().split("&")[1].replace("|", "\""),UserCoinIncrease.class);
                    alipayPaymentRecord.setUserId(userCoinIncrease.getUserId());
                }

            }catch (Exception ex){
                logger.info("支付宝返回参数解析失败，参数为【"+alipayPaymentRecord.getPassbackParams()+"】");
            }

            aliPayPaymentRecordService.save(alipayPaymentRecord);

            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            if ("TRADE_SUCCESS".equals(trade_status)) {
                //判断该笔订单是否在商户网站中已经做过处理
                String notifyType=alipayPaymentRecord.getPassbackParams().split("&")[0];
                if("order".equals(notifyType)){
                    Integer orderStatus = orderService.getOrderStatus(out_trade_no);
                    //付款成功，如果order未完成
                    if (orderStatus == 0) {
                        try {
                            //计时
                            long current = System.currentTimeMillis();
                            //处理订单业务
                            orderPayService.addPaySuccess(out_trade_no, Payment.ALIPAY,trade_no);
//                            orderService.addPaySuccess(out_trade_no, 0, trade_no);
                            logger.info("订单支付成功，订单号:{},用时{}",
                                    out_trade_no, (System.currentTimeMillis() - current) + "毫秒");
                            //为购买用户发送购买成功的消息通知
//                            orderService.savePurchaseNotice(weburl, out_trade_no);
                        } catch (Exception e) {
                            logger.error("用户支付成功，构建课程失败！！！" + out_trade_no + "，错误信息：", e);
                        }
                    }

                }else if("reward".equals(notifyType)){
                    RewardParamVo rpv = JSONObject.parseObject(alipayPaymentRecord.getPassbackParams().split("&")[1].replace("|", "\""),RewardParamVo.class);
                    RewardStatement rs=new RewardStatement();
                    BeanUtils.copyProperties(rs,rpv);
                    rs.setCreateTime(new Date());
                    rs.setPayType(Payment.ALIPAY.getCode());
                    rs.setChannel(OrderFrom.PC.getCode());
                    rs.setOrderNo(out_trade_no);
                    rs.setStatus(1);
                    userCoinService.updateBalanceForReward(rs);

                }else if("recharge".equals(notifyType)){
                    UserCoinIncrease uci = JSONObject.parseObject(alipayPaymentRecord.getPassbackParams().split("&")[1].replace("|", "\""),UserCoinIncrease.class);
                    userCoinService.updateBalanceForRecharge(uci.getUserId(),Payment.ALIPAY,uci.getValue(), OrderFrom.PC,out_trade_no);
                }

            }
            response.getWriter().println("success");

        } else {
            //验证失败
            response.getWriter().println("fail");

        }
    }

}
