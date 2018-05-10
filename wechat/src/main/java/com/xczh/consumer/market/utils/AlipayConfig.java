package com.xczh.consumer.market.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AlipayConfig {
	// 商户appid
	@Value("${alipay.app.id}")
	public  String APPID;
	// 私钥 pkcs8格式的
	@Value("${alipay.merchant.private.key}")
	public  String RSA_PRIVATE_KEY;
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	@Value("${alipay.notify_url}")
	public  String notify_url;
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	@Value("${alipay.return_url}")
	public  String return_url;
	// 请求网关地址
	@Value("${alipay.url}")
	public  String URL;

	//支付宝网关（沙盒环境）
//	public static String URL = "https://openapi.alipaydev.com/gateway.do";
	// 编码
	public  String CHARSET = "UTF-8";
	// 返回格式
	public  String FORMAT = "json";
	// 支付宝公钥
	@Value("${alipay.alipay.public.key}")
	public  String ALIPAY_PUBLIC_KEY ;
	// 日志记录目录
	public  String log_path = "/log";
	// RSA2
	public  String SIGNTYPE = "RSA2";

	public String getAPPID() {
		return APPID;
	}

	public void setAPPID(String APPID) {
		this.APPID = APPID;
	}

	public String getRSA_PRIVATE_KEY() {
		return RSA_PRIVATE_KEY;
	}

	public void setRSA_PRIVATE_KEY(String RSA_PRIVATE_KEY) {
		this.RSA_PRIVATE_KEY = RSA_PRIVATE_KEY;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String URL) {
		this.URL = URL;
	}

	public String getCHARSET() {
		return CHARSET;
	}

	public void setCHARSET(String CHARSET) {
		this.CHARSET = CHARSET;
	}

	public String getFORMAT() {
		return FORMAT;
	}

	public void setFORMAT(String FORMAT) {
		this.FORMAT = FORMAT;
	}

	public String getALIPAY_PUBLIC_KEY() {
		return ALIPAY_PUBLIC_KEY;
	}

	public void setALIPAY_PUBLIC_KEY(String ALIPAY_PUBLIC_KEY) {
		this.ALIPAY_PUBLIC_KEY = ALIPAY_PUBLIC_KEY;
	}

	public String getLog_path() {
		return log_path;
	}

	public void setLog_path(String log_path) {
		this.log_path = log_path;
	}

	public String getSIGNTYPE() {
		return SIGNTYPE;
	}

	public void setSIGNTYPE(String SIGNTYPE) {
		this.SIGNTYPE = SIGNTYPE;
	}
}
