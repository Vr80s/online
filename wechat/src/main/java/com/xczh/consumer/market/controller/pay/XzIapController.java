package com.xczh.consumer.market.controller.pay;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.SQLException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.iphoneIpaService;
import com.xczh.consumer.market.utils.RandomUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.TimeUtil;
import com.xczh.consumer.market.utils.VersionCompareUtil;
import com.xczhihui.bxg.online.api.service.XmbBuyCouserService;

@Controller
@RequestMapping("/xczh/iap")
public class XzIapController {

	// 购买凭证验证地址
	@Value("${iphone.iap.url}")
	private String certificateUrl;

	// 现在这里做下记录
	@Value("${iphone.version}")
	private String iphoneVersion;

	@Value("${rate}")
	private Integer rate;

	@Value("${onlinekey}")
	private String onlinekey;

	@Value("${online.weburl}")
	private String pcUrl;

	@Autowired
	private iphoneIpaService iphoneIpaService;


	@Autowired
	private AppBrowserService appBrowserService;


	@Autowired
	private XmbBuyCouserService xmbBuyCouserService;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(XzIapController.class);

	
	
	/**
	 * ios请求后台获取订单号
	 * @param receipt
	 */
	@ResponseBody
	@RequestMapping("/getIapOrderNo")
	public ResponseObject  getIapOrderNo() throws SQLException {
		String orderNo  =  TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
		return ResponseObject.newSuccessResponseObject(orderNo);
	}
	
	/**
	 * 接收iOS端发过来的购买凭证
	 * 
	 * @param receipt
	 */
	@ResponseBody
	@RequestMapping("/setIapCertificate")
	@Transactional
	public Object setIapCertificate(@RequestParam("receipt") String receipt,
			@RequestParam("userId") String userId,
			@RequestParam("transactionId") String transactionId,
			@RequestParam("orderNo") String orderNo,
			@RequestParam("version") String version) throws SQLException {

		LOGGER.info("苹果充值   封装的数据  receipt:" + receipt);
		String url = certificateUrl;
		LOGGER.info("app 购买地址   certificateUrl:" + url);
		/*
		 * 如果
		 */
		if(certificateUrl.equals("https://sandbox.itunes.apple.com/verifyReceipt")){ //测试环境
			
			url = certificateUrl; 
		
		}else{
			/*
			 * 正式环境需要区分下：是否在审核中，审核中需要是沙箱环境，审核后我们上线需要是正式环境
			 * 
			 *  需要注意的一点，如果ios进行上架后，后台需要立即更新这个状态app版本状态
			 */
			
//			VersionInfoVo newVer = versionService.getNewVersion(1);
			
//			String newVersion = newVer.getVersion()+".1";
			LOGGER.info("newVersion:" + iphoneVersion);
			LOGGER.info("currentVersion:" + version);
			
			int diff = VersionCompareUtil.compareVersion(iphoneVersion, version);
			if (diff > 0) {   
				LOGGER.info("{}{}{}{}{}-----》当前版本小于最新版本，说明是老版本  ");
				url = "https://buy.itunes.apple.com/verifyReceipt";
			} else {        // 当前版本大于等于最新版本，说明是正在审核的版本或者调试的版本，用沙箱环境
				LOGGER.info("{}{}{}{}{}-----》当前版本等于最新版本，说明是正在审核的版本或者调试的版本，用沙箱环境");
				url = "https://sandbox.itunes.apple.com/verifyReceipt";
			}
		}

		LOGGER.info("苹果地址:" + url);
		final String certificateCode = receipt;
		if (StringUtils.isNotEmpty(certificateCode)) {
			
			String resp = sendHttpsCoon(url, certificateCode);
			
			LOGGER.info("二次验证获取的数据---》苹果返回数据:" + resp);
			
			JSONObject newObj = JSONObject.parseObject(resp);
			
			// 把苹果返回的数据存到数据库
			//String productId = newObj.getJSONObject("receipt").getJSONArray("in_app").getJSONObject(0).get("product_id").toString();
			
		    String status= newObj.getString("status");  //状态码,0为成功
			if (!status.equals("0")) {
				return ResponseObject.newErrorResponseObject("操作失败！");
			}

			JSONArray inApp = newObj.getJSONObject("receipt").getJSONArray("in_app");
			JSONObject iap = null;
			for (int i = 0; i < inApp.size(); i++) {
				if(inApp.getJSONObject(i).get("transaction_id").equals(transactionId)){
					iap = inApp.getJSONObject(i);
				}
			}
			if(iap!=null){
				LOGGER.info("iap:"+iap.toJSONString());//开发商交易ID
				LOGGER.info("status:" + status);

				int actualPrice = IapType.getPrice((String) iap.get("product_id"));
			/*
			 * 保存消费信息，并且做对应的熊猫币增加
			 */
				iphoneIpaService.increaseNew(orderNo,userId,
						new BigDecimal(actualPrice*10),
						resp,
						new BigDecimal(actualPrice),transactionId);

				LOGGER.info("{}{}{}{}{}{}{}{}------------苹果充值成功");
				return ResponseObject.newSuccessResponseObject(null);
			}else{
				return ResponseObject.newErrorResponseObject("交易信息有误");
			}

		} else {
			return null;
		}
	}


	/**
	 * 安卓、ios、h5 扣减熊猫币,购买课程
	 */
	@RequestMapping("appleIapPayOrder")
	@ResponseBody
	@Transactional
	public ResponseObject appleInternalPurchaseOrder(HttpServletRequest req,
			HttpServletResponse res, @RequestParam("order_no") String order_no)
			throws Exception {
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if (user == null) {
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		xmbBuyCouserService.xmbBuyCourseLock(order_no);
		return ResponseObject.newSuccessResponseObject("购买成功");
	}

	/**
	 * 发送请求
	 * 
	 * @param url
	 * @return
	 */
	private String sendHttpsCoon(String url, String code) {
		if (url.isEmpty()) {
			return null;
		}
		try {
			// 设置SSLContext
			SSLContext ssl = SSLContext.getInstance("SSL");
			ssl.init(null, new TrustManager[] { myX509TrustManager }, null);
			// 打开连接
			HttpsURLConnection conn = (HttpsURLConnection) new URL(url)
					.openConnection();
			// 设置套接工厂
			conn.setSSLSocketFactory(ssl.getSocketFactory());
			// 加入数据
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-type", "application/json");

			JSONObject obj = new JSONObject();
			obj.put("receipt-data", code);

			BufferedOutputStream buffOutStr = new BufferedOutputStream(
					conn.getOutputStream());
			buffOutStr.write(obj.toString().getBytes());
			buffOutStr.flush();
			buffOutStr.close();

			// 获取输入流
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			LOGGER.info("要通过这个参数来得到这个用户的信息：" + sb.toString());
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
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {

		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {

		}
	};

	enum IapType {

		C1("com.bj.healthlive.coin_01", 1),
		C2("com.bj.healthlive.coin_02", 6),
		C3("com.bj.healthlive.coin_03", 12),
		C4("com.bj.healthlive.coin_04", 50),
		C5("com.bj.healthlive.coin_05", 98),
		C6("com.bj.healthlive.coin_06", 488)
		;

		// 成员变量
		private int price;
		private String code;

		// 构造方法
		private IapType(String code, int count) {
			this.price = count;
			this.code = code;
		}

		public static int getPrice(String code){
			for (IapType e : IapType.values()) {
				if(e.getCode().equals(code)){
					return e.getPrice();
				}
			}
			return 0;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
	}
}