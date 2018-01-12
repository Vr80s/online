package com.xczh.consumer.market.wxpay.entity;

import com.xczh.consumer.market.wxpay.SignAbledBean;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.typeutil.StringUtil;
import com.xczh.consumer.market.wxpay.util.CommonUtil;
import net.sf.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PayInfo extends SignAbledBean implements Serializable {

	private static final long serialVersionUID = -646852015222912170L;
	
	private String appid;
	private String mch_id;
	private String device_info;
	private String nonce_str;
	private String sign;
	private String body;
	private String attach;
	private String out_trade_no;
	private int total_fee;
	private String spbill_create_ip;
	private String notify_url;
	private String trade_type;
	private String openid;
    private String scene_info; //新加了h5字符必填项
	private String time_expire;

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getScene_info() {
		return scene_info;
	}

	public void setScene_info(String scene_info) {
		this.scene_info = scene_info;
	}



	public static final String trade_type_native = "NATIVE";
	public static final String trade_type_app = "APP";
	public static final String trade_type_jsapi = "JSAPI";
	public static final String trade_type_h5 = "MWEB";

	/**
	 * 创建本类特定实例
	 * 
	 * @param bizOrder
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public PayInfo createPayInfo(BizOrder bizOrder, String tradeType) throws Exception {
		
		PayInfo payInfo = new PayInfo();
		if(trade_type_app.equals(tradeType)){
			payInfo.setAppid(WxPayConst.app_appid);
			payInfo.setMch_id(WxPayConst.app_mchid);
		}else if(trade_type_native.equals(tradeType) || tradeType.equals(trade_type_jsapi)){
			payInfo.setAppid(WxPayConst.gzh_ApiKey);
			payInfo.setMch_id(WxPayConst.gzh_Secret);
		}
		payInfo.setSpbill_create_ip(WxPayConst.server_ip);
		payInfo.setDevice_info("wxpay");
		payInfo.setNonce_str(CommonUtil.CreateNoncestr());
		payInfo.setBody(bizOrder.getPaytitle());
		payInfo.setAttach(bizOrder.getId());
		payInfo.setOut_trade_no(bizOrder.getOrderCode());
		payInfo.setTotal_fee(bizOrder.getFeeAmount());
		payInfo.setTrade_type(tradeType);//
		
		payInfo.setNotify_url(WxPayConst.returnOpenidUri+"/bxg/wxpay/wxNotify");
		if(!StringUtil.isEmpty(bizOrder.getOpenId())) {
            payInfo.setOpenid(bizOrder.getOpenId());
        }
		System.out.println("createPayInfo->\r\n\t" + JSONObject.fromObject(payInfo).toString());		
		// 签名
		payInfo.setSign(CommonUtil.getSign(payInfo));
		System.out.println("createPayInfo->\r\n\t" + JSONObject.fromObject(payInfo).toString());
		return payInfo;
	}
	
	/**
	 * 创建h5订单信息
	 * @param bizOrder
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public PayInfo createPayInfoH5(BizOrder bizOrder, String tradeType, String khdip) throws Exception {
		
		PayInfo payInfo = new PayInfo();
		payInfo.setAppid(WxPayConst.gzh_appid);
		payInfo.setMch_id(WxPayConst.gzh_mchid);
		Map<String,Object> m = new HashMap<String,Object>();
		Map<String,String> m1 = new HashMap<String,String>();
		//{"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}
		m1.put("type", "Wap");
		m1.put("wap_url", WxPayConst.returnOpenidUri);
		m1.put("wap_name", "中医传承");
		m.put("h5_info", m1);
		payInfo.setScene_info(m.toString());
		
		payInfo.setDevice_info("wxpay");
		payInfo.setNonce_str(CommonUtil.CreateNoncestr());
		payInfo.setBody(bizOrder.getPaytitle());
		payInfo.setAttach(bizOrder.getId());
		payInfo.setOut_trade_no(bizOrder.getOrderCode());
		payInfo.setTotal_fee(bizOrder.getFeeAmount());
		payInfo.setSpbill_create_ip(khdip);// payInfo.setSpbill_create_ip(bizOrder);		
		payInfo.setTrade_type(tradeType);//
		payInfo.setNotify_url(WxPayConst.returnOpenidUri+"/bxg/wxpay/wxNotify");
		if(!StringUtil.isEmpty(bizOrder.getOpenId())) {
            payInfo.setOpenid(bizOrder.getOpenId());
        }
		
		System.out.println("createPayInfo->\r\n\t" + JSONObject.fromObject(payInfo).toString());		
		// 签名
		payInfo.setSign(CommonUtil.getSign(payInfo));
		System.out.println("createPayInfo->\r\n\t" + JSONObject.fromObject(payInfo).toString());
		return payInfo;
	}
	
	/**
	 * 创建h5订单信息
	 * @param bizOrder
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public PayInfo createPayInfoApp(BizOrder bizOrder, String tradeType, String khdip) throws Exception {
		PayInfo payInfo = new PayInfo();
		payInfo.setAppid(WxPayConst.app_appid);
		payInfo.setMch_id(WxPayConst.app_mchid);
		Map<String,Object> m = new HashMap<String,Object>();
		Map<String,String> m1 = new HashMap<String,String>();
		//{"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}
		m1.put("type", "Wap");
		m1.put("wap_url", WxPayConst.returnOpenidUri);
		m1.put("wap_name", "中医传承");
		m.put("h5_info", m1);
		payInfo.setScene_info(m.toString());
		
		payInfo.setDevice_info("wxpay");
		payInfo.setNonce_str(CommonUtil.CreateNoncestr());
		payInfo.setBody(bizOrder.getPaytitle());
		payInfo.setAttach(bizOrder.getId());
		payInfo.setOut_trade_no(bizOrder.getOrderCode());
		payInfo.setTotal_fee(bizOrder.getFeeAmount());
		payInfo.setSpbill_create_ip(khdip);// payInfo.setSpbill_create_ip(bizOrder);		
		payInfo.setTrade_type(tradeType);//
		payInfo.setNotify_url(WxPayConst.returnOpenidUri+"/bxg/wxpay/wxNotify");
		if(!StringUtil.isEmpty(bizOrder.getOpenId())) {
            payInfo.setOpenid(bizOrder.getOpenId());
        }
		System.out.println("createPayInfo->\r\n\t" + JSONObject.fromObject(payInfo).toString());		
		// 签名
		payInfo.setSign(CommonUtil.getSign(payInfo));
		System.out.println("createPayInfo->\r\n\t" + JSONObject.fromObject(payInfo).toString());
		return payInfo;
	}
	
	/**
	 * 微信统一下单接口
	 * @param bizOrder
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public PayInfo createPayInfoCommon(BizOrder bizOrder, String tradeType, String khdip) throws Exception {
		PayInfo payInfo = new PayInfo();
		if(PayInfo.trade_type_app.equals(tradeType)){
			payInfo.setAppid(WxPayConst.app_appid); 
			payInfo.setMch_id(WxPayConst.app_mchid);
		}else{
			payInfo.setAppid(WxPayConst.gzh_appid);
			payInfo.setMch_id(WxPayConst.gzh_mchid);
		}
		payInfo.setBody(bizOrder.getPaytitle());
		if(PayInfo.trade_type_h5.equals(tradeType)){
			Map<String,Object> m = new HashMap<String,Object>();
			Map<String,String> m1 = new HashMap<String,String>();
			m1.put("type", "Wap");
			m1.put("wap_url", WxPayConst.returnOpenidUri);
			m1.put("wap_name", "中医传承");
			m.put("h5_info", m1);
			payInfo.setScene_info(m.toString());
		}
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 1);
		payInfo.setTime_expire(new SimpleDateFormat("yyyyMMddHHmmss").format(c.getTime()));
		
		payInfo.setNonce_str(CommonUtil.CreateNoncestr());
		payInfo.setOut_trade_no(bizOrder.getOrderCode());
		payInfo.setTotal_fee(bizOrder.getFeeAmount());
		payInfo.setSpbill_create_ip(khdip);// payInfo.setSpbill_create_ip(bizOrder);	
		payInfo.setNotify_url(WxPayConst.returnOpenidUri+"/bxg/wxpay/wxNotify");
		payInfo.setTrade_type(tradeType);//
		
	    payInfo.setDevice_info("wxpay");
		payInfo.setAttach(bizOrder.getId());
		
	    //9ED42A76220A3258231C8B51C615571C
		if(!StringUtil.isEmpty(bizOrder.getOpenId())) {
            payInfo.setOpenid(bizOrder.getOpenId());
        }
		System.out.println("createPayInfo->\r\n\t" + JSONObject.fromObject(payInfo).toString());		
		payInfo.setSign(CommonUtil.getSign(payInfo,tradeType));
		System.out.println("createPayInfo->\r\n\t" + JSONObject.fromObject(payInfo).toString());
		return payInfo;
	}
	
}
