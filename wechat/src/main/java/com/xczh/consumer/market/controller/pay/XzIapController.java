package com.xczh.consumer.market.controller.pay;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xczh.consumer.market.bean.OnlineOrder;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineOrderService;
import com.xczh.consumer.market.service.iphoneIpaService;
import com.xczh.consumer.market.utils.HttpUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.VersionCompareUtil;
import com.xczh.consumer.market.vo.CodeUtil;
import com.xczhihui.bxg.online.api.service.EnchashmentService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/xczh/iap")
public class XzIapController {

    //购买凭证验证地址
    @Value("${iphone.iap.url}")
    private  String certificateUrl;
    
    //现在这里做下记录
    @Value("${iphone.version}")
    private  String iphoneVersion;
    
    //购买凭证验证地址
    @Value("${rate}")
    private  Integer rate;
    
	@Value("${onlinekey}")
	private String onlinekey;
	
	@Value("${online.weburl}")
	private String pcUrl;


    @Autowired
    private iphoneIpaService iphoneIpaService;

    @Autowired
	private OnlineOrderService onlineOrderService;
    
    @Autowired
    private AppBrowserService appBrowserService;
    
	@Autowired
	private EnchashmentService enchashmentService;
    
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(XzIapController.class);
    /**
     * 接收iOS端发过来的购买凭证
     * @param receipt
     */
    @ResponseBody
    @RequestMapping("/setIapCertificate")
    public Object setIapCertificate(String receipt,String userId,String actualPrice,String version) throws SQLException {
       // receipt=new String(Base64.decode(receipt));
    	LOGGER.info("111111111111111================================================");
        LOGGER.info("receipt:"+receipt);
        
        String url = certificateUrl;
        String newVersion = iphoneVersion;
        int diff = VersionCompareUtil.compareVersion(newVersion, version);
        /*
         * 非最新版本
         */
        //iphone.iap.url=https://sandbox.itunes.apple.com/verifyReceipt
        //iphone.iap.url=https://buy.itunes.apple.com/verifyReceipt
        if (diff < 0) {  //当前版本小于最新版本，说明是老版本，需要用--生产环境
            //return ResponseObject.newSuccessResponseObject(defaultNoUpdateResult);
        	url = "https://buy.itunes.apple.com/verifyReceipt";
        }else{//当前版本等于最新版本，说明是正在审核的版本，用沙箱环境
        	url = "https://sandbox.itunes.apple.com/verifyReceipt";
        }
        final String certificateCode = receipt;
        if(StringUtils.isNotEmpty(certificateCode)){
        	String resp=   sendHttpsCoon(url, certificateCode);
	        LOGGER.info("苹果返回数据:"+resp);
	         //把苹果返回的数据存到数据库
	        String productId= JSONObject.parseObject(resp).getJSONObject("receipt").getJSONArray("in_app").getJSONObject(0).get("product_id").toString();
	        LOGGER.info("productId:"+productId);
	        if(StringUtils.isBlank(productId)){
	            return ResponseObject.newErrorResponseObject("操作失败！找不到productId");
	        }
	        //记录数据 增加熊猫币
	//            10熊猫币 	消耗型项目	com.bj.healthlive.coin_01
	//            60熊猫币 	消耗型项目	com.bj.healthlive.coin_02
	//            120熊猫币 	消耗型项目	com.bj.healthlive.coin_03
	//            500熊猫币 	消耗型项目	com.bj.healthlive.coin_04
	//            980熊猫币 	消耗型项目	com.bj.healthlive.coin_05
	//            4880熊猫币 	消耗型项目	com.bj.healthlive.coin_06
	//            int xmb=Integer.parseInt(actualPrice)*rate;
	//            switch (productId){
	//
	//                case "com.bj.healthlive.coin_01":
	//                    xmb=10;
	//                    break;
	//                case "com.bj.healthlive.coin_02":
	//                    xmb=60;
	//                    break;
	//                case "com.bj.healthlive.coin_03":
	//                    xmb=120;
	//                    break;
	//                case "com.bj.healthlive.coin_04":
	//                    xmb=500;
	//                    break;
	//                case "com.bj.healthlive.coin_05":
	//                    xmb=980;
	//                    break;
	//                case "com.bj.healthlive.coin_06":
	//                    xmb=4880;
	//                    break;
	//                    default:
	//                        throw new RuntimeException("productId不正确");
	//            }
	        	int xmb=Integer.parseInt(actualPrice)*10;
	            iphoneIpaService.increase(userId,xmb,resp,actualPrice);
	            LOGGER.info("22222222222222222222222222222====================");
	            return ResponseObject.newSuccessResponseObject(null);
        }else{
            return null;
        }
    }
    /**
	 * 苹果手机下单后扣减熊猫币
	 */
	@RequestMapping("appleIapPayOrder")
	@ResponseBody
	@Transactional
	public ResponseObject appleInternalPurchaseOrder(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		try {
			/*
			 * 传递过来一个订单号
			 */
			String order_no = req.getParameter("order_no");
			ResponseObject orderDetails = onlineOrderService.getOrderAndCourseInfoByOrderNo(order_no);
    		if(null == orderDetails.getResultObject()){
    			return ResponseObject.newErrorResponseObject("未找到订单信息");
    		}
			OnlineOrder order  = (OnlineOrder) orderDetails.getResultObject();
			//订单金额
    		Double actualPrice = order.getActualPay();
    		double  xmb = actualPrice * rate;
    		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
    		if(user == null) {
    	         return ResponseObject.newErrorResponseObject("登录超时！");
    	    }
    		String userYE =  enchashmentService.enableEnchashmentBalance(user.getId());
    		double d = Double.valueOf(userYE);
    		LOGGER.info("要消费余额:"+xmb);
    		LOGGER.info("当前用户余额:"+d);
    		if(xmb>d){
    			return ResponseObject.newErrorResponseObject("余额不足,请到个人账户充值！");
			}
			/**
			 * 然后你那边加下密
			 */
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
			
			LOGGER.info("请求web端的  ios   内购成功回调  pay_notify_iosiap");
			
			String msg = HttpUtil.sendDataRequest(
					pcUrl  + "/web/pay_notify_iosiap", "application/xml", resXml
							.toString().getBytes());
			
			LOGGER.info("msg  >>>  " + msg);
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
	    		ResponseObject finalResult = iphoneIpaService.iapOrder(order.getUserId(), xmb, order_no, actualPrice+"",courderName);
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
     * 发送请求
     * @param url
     * @return
     */
    private String sendHttpsCoon(String url, String code){
        if(url.isEmpty()){
            return null;
        }
        try {
            //设置SSLContext
            SSLContext ssl = SSLContext.getInstance("SSL");
            ssl.init(null, new TrustManager[]{myX509TrustManager}, null);
            //打开连接
            HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
            //设置套接工厂
            conn.setSSLSocketFactory(ssl.getSocketFactory());
            //加入数据
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type","application/json");

            JSONObject obj = new JSONObject();
            obj.put("receipt-data", code);

            BufferedOutputStream buffOutStr = new BufferedOutputStream(conn.getOutputStream());
            buffOutStr.write(obj.toString().getBytes());
            buffOutStr.flush();
            buffOutStr.close();

            //获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = null;
            StringBuffer sb = new StringBuffer();
            while((line = reader.readLine())!= null){
                sb.append(line);
            }
            LOGGER.info("要通过这个参数来得到这个用户的信息："+sb.toString());
            return sb.toString();

        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 重写X509TrustManager
     */
    private static TrustManager myX509TrustManager = new X509TrustManager() {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }
    };
}