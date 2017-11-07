package com.xczh.consumer.market.wxpay;

import com.xczh.consumer.market.wxpay.entity.BizOrder;
import com.xczh.consumer.market.wxpay.util.CommonUtil;
import com.xczh.consumer.market.wxpay.util.PayUtils;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

//import java.util.HashMap;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//import com.smarthaier.server.alipay.constants.AlipayConfig;
//import com.smarthaier.server.alipay.sign.RSA;
//import com.smarthaier.server.alipay.util.AlipayCore;
//import com.smarthaier.server.alipay.util.AlipaySubmit;
//import com.xczh.consumer.market.wxpay.typeutil.StringUtil;
//import com.xczh.consumer.market.wxpay.consts.WxPayConst;
//import com.xczh.consumer.market.wxpay.entity.QueryInfo;
//import com.xczh.consumer.market.wxpay.util.HttpsRequest;

/**
 * 支付宝微信通用二维码生成工厂类
 */
public class PayFactory {

	public static PayFactory work() {
		return new PayFactory();
	}

	/**
	 * 微信支付
	 */
	public static final String wx = "wx";
	
	/**
	 * 支付宝支付
	 */
	public static final String zfb = "zfb";

	/**
	 * 支付宝、微信扫码支付
	 * 
	 * @author 周铭株 at 2016年5月7日 上午10:54:02
	 * @param payType
	 * @param orderId
	 * @param shopId
	 * @param money
	 * @param payTitle
	 * @return
	 * @throws Exception
	 */
	public String getPayQrimage(OutputStream os, 
			long orderId, 
			int money, 
			String payTitle, 
			String extDatas)
			throws Exception {
		
		if (os == null) { //??支付宝请求支付;
//			// 把请求参数打包成数组
//			Map<String, String> sParaTemp = getAliPreOrderParams(AlipayConfig.service4Jshdz, orderId, money, payTitle,
//					extDatas);
//			sParaTemp.put("qr_pay_mode",""+1);
//			// 建立请求
//			String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
//			return sHtmlText; //??返回支付请求的页面"html字符串";
			return null;
		} else { //??微信支付请求;
			// 金额，转化为分
			BizOrder bo = getWxPayBo(orderId, money, payTitle, extDatas);
			PayUtils.fromBizBean2QRImgStream(bo, os); //??返回直接扫描支付的页面二维码图片;
			return null;
		}
	}

	/**
	 * 微信支付宝移动支付，签名处理后预下单处理
	 * @param orderId
	 * @param money
	 * @param payTitle
	 * @param extDatas
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getPrePayInfos(String pt, 
			long orderId, 
			int money, 
			String payTitle, 
			String extDatas)
			throws Exception {
		
		if (wx.equals(pt)) {
			return PayUtils.fromBizBean2PrePay4App(getWxPayBo(orderId, money, payTitle, extDatas));
		} else if (zfb.equals(pt)) {
//			// Map<String, String> map = AlipaySubmit.buildRequestPara(
//			// getAliPreOrderParams(AlipayConfig.service4Mobile, orderId, money,
//			// payTitle, extDatas));
//			Map<String, String> map = getAliPreOrderParams(AlipayConfig.service4Mobile, orderId, money, payTitle,
//					extDatas);
//			map = AlipayCore.paraFilter(map);
//			String preSing = AlipayCore.createLinkString(map);
//			System.out.println("支付宝移动支付拼接串:" + preSing);
//			String sign = "";
//			if (AlipayConfig.sign_type.equals("RSA")) {
//				sign = RSA.sign(preSing, AlipayConfig.getPrivateKey(), AlipayConfig.input_charset);
//			}
//			// 把签名转为url格式
//			// map.put("sign2url", URLEncoder.encode(map.get("sign"), "utf-8"));
//			sign = URLEncoder.encode(sign, "utf-8");
//			map.put("payLink", preSing + "&sign_type=\"RSA\"&sign=\"" + sign + "\"");
//			return map;
		} else
			;
		return null;
	}
	
	/**
	 * jsapi支付方式
	 * @param pt
	 * @param orderId
	 * @param money
	 * @param payTitle
	 * @param extDatas
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getPrePayInfos(String orderId, 
			int money, 
			String payTitle, 
			String extDatas,
			String openid) throws Exception{
		
		return PayUtils.fromBizBean2PrePay4Jsapi(getWxPayBo(orderId, money, payTitle, extDatas,openid));
	}

	public Map<String, String> getPrePayInfosH5(String orderId, 
			int money, String payTitle, String extDatas,String openId,String khdip) throws Exception{
		
		return PayUtils.fromBizBean2PrePay4H5(getWxPayBo(orderId, money, payTitle, extDatas,openId),khdip);
	}
	
	public Map<String, String> getPrePayInfosApp(String orderId, 
			int money, String payTitle, String extDatas,String openId,String khdip) throws Exception{
		
		return PayUtils.fromBizBean2PrePay4App(getWxPayBo(orderId, money, payTitle, extDatas,openId),khdip);
	}
	
	/**
	 * Description：
	 * @param orderId  订单号
	 * @param money    支付的金额
	 * @param payTitle 支付标题（测试或者生成）
	 * @param extDatas 
	 * @param openId   微信公众号的话，需要传递openid
	 * @param khdip    app和h5需要获取客户端ip 
	 * @param tradeType   支付类型
	 * @return
	 * @throws Exception
	 * @return Map<String,String>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public Map<String, String> getPrePayInfosCommon(String orderId, 
			int money, String payTitle, String extDatas,String openId,String khdip,String tradeType) throws Exception{

		return PayUtils.getPrePayInfosCommon(getWxPayBo(orderId, money, payTitle, extDatas,openId),khdip,tradeType);
	}

	/**
	 * 构建微信支付业务对象
	 * @param orderId
	 * @param money
	 * @param payTitle
	 * @param detail
	 * @return 
	 */
	private BizOrder getWxPayBo(long orderId,
                                int money,
                                String payTitle,
                                String detail) {
		
		BizOrder bo;
		bo = new BizOrder();
		
		// 微信金额单位是分
		bo.setFeeAmount(money);
		bo.setId(detail);// 其实是附加数据attch
		bo.setOrderCode(String.valueOf(orderId));
		bo.setPaytitle(payTitle);
		// bo.setClientIp();
		// bo.setDeviceInfo();
		return bo;
	}
	
	
	public String getWxCode(String uri) throws Exception {
		
		uri=URLEncoder.encode(uri,"utf-8");
		String code = CommonUtil.getWxCode(uri);
		return code;
	}

	public String getOpenId(String code) throws Exception {
		
		return CommonUtil.getOpenId(code);
	}
	/**
	 * 构建微信支付业务对象
	 * 主要h5支付用
	 * @param orderId
	 * @param money
	 * @param payTitle
	 * @param detail
	 * @return
	 */
	private BizOrder getWxPayBo(String orderId,
                                int money,
                                String payTitle,
                                String detail,
                                String openId) {
		
		BizOrder bo;
		bo = new BizOrder();
				
		bo.setFeeAmount(money);// 微信金额单位是分
		bo.setId(detail);// 其实是附加数据attch
		bo.setOrderCode(String.valueOf(orderId));
		bo.setPaytitle(payTitle);
		bo.setOpenId(openId);
		return bo;
	}

	/**
	 * 构建支付宝预下单公共请求参数
	 * @param orderId
	 * @param money
	 * @param payTitle
	 * @param detail
	 * @return
	 */
	private Map<String, String> getAliPreOrderParams(String service, 
			long orderId, 
			int money, 
			String payTitle,
			String detail) {
		
//		Map<String, String> sParaTemp = new HashMap<String, String>();
//		boolean im = AlipayConfig.service4Mobile.equals(service);
//		sParaTemp.put("service", im ? $(service) : service);
//		sParaTemp.put("partner", im ? $(AlipayConfig.getPid()) : AlipayConfig.getPid());
//		sParaTemp.put("seller_id", im ? $(AlipayConfig.seller_id) : AlipayConfig.seller_id);
//		sParaTemp.put("_input_charset", im ? $(AlipayConfig.input_charset) : AlipayConfig.input_charset);
//		sParaTemp.put("payment_type", im ? $(AlipayConfig.payment_type) : AlipayConfig.payment_type);
//		sParaTemp.put("notify_url", im ? $(AlipayConfig.getNotifyUrl()) : AlipayConfig.getNotifyUrl());
//		if (!im) {
//			sParaTemp.put("return_url", AlipayConfig.return_url);
//			sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
//			sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
//		}
//		sParaTemp.put("out_trade_no", im ? $(String.valueOf(orderId)) : String.valueOf(orderId));
//		sParaTemp.put("subject", im ? $(payTitle) : payTitle);
//		
//		// 支付宝金额单位是元
//		sParaTemp.put("total_fee", im ? $(String.valueOf((float) money / 100)) : String.valueOf((float) money / 100));
//		sParaTemp.put("body", im ? $(detail) : detail);
//		return sParaTemp;
		
		return null;
	}
	
	private String getAliSingleQuery(String orderId) throws Exception{
		
//		Map<String, String> sParaTemp = new HashMap<String, String>();
//		sParaTemp.put("service", "single_trade_query");
//        sParaTemp.put("partner", AlipayConfig.getPid());
//        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
//		sParaTemp.put("out_trade_no", orderId);
//		
//		//建立请求
//		String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
//		return sHtmlText;
		
		return null;
	}
	
	/**
	 * 从第三方支付系统取支付信息
	 * @param orderid
	 * @return
	 * @throws Exception 
	 */
	public Map<String, String> queryBill(String orderid) throws Exception{
		
		/*??
		HttpsRequest request = new HttpsRequest();
		ExecutorService executor=Executors.newFixedThreadPool(3);
		//请求第一个微信账户
		Future<String> firstask=executor.submit(()->request.sendPost(WxPayConst.query_url, new QueryInfo(orderid, true)));
		//请求第二个微信账户
		Future<String> sectask=executor.submit(()->request.sendPost(WxPayConst.query_url, new QueryInfo(orderid, false)));
		//请求支付宝账户
		Future<String> thdtask=executor.submit(()->getAliSingleQuery(orderid));
		Map<String,String> mres=null;
		if(!StringUtil.isEmpty(firstask.get())||!StringUtil.isEmpty(sectask.get())){
			mres=CommonUtil.parseXml(firstask.get());
			if(WxPayConst.recode_success.equals(mres.get(WxPayConst.result_code)))
				return mres;
			mres=CommonUtil.parseXml(sectask.get());
			if(WxPayConst.recode_success.equals(mres.get(WxPayConst.result_code)))
				return mres;
		}if(!StringUtil.isEmpty(thdtask.get())){
			String[] tmps=thdtask.get().replace("<trade>", "	").replace("</trade>","	").split("	");
			if(tmps.length>2)
				mres=CommonUtil.parseXml("<xml>"+tmps[1]+"</xml>");
			return mres;
		}
		return null;
		??*/
		
		return null;
	}

	// 加双引号
	private String $(String str) {
		return "\"" + str + "\"";
	}

	public static void main(String[] args) throws Exception {
		String str = new PayFactory().getPayQrimage(null, 2222213L, 1, "31111114324", "11123424");
		System.out.println(str);
	}

}

