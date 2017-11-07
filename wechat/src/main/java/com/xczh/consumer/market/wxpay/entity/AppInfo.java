package com.xczh.consumer.market.wxpay.entity;

import com.xczh.consumer.market.wxpay.SignAbledBean;

import java.io.Serializable;

public class AppInfo extends SignAbledBean implements Serializable{

	private static final long serialVersionUID = -646852015222912170L;
	
//	{"nonce_str":"1WI7s6lQktoBjDjW",
//		"device_info":"wxpay","appid":"wxf1f3c13da2fae87b",
//		"sign":"AD001B4E41B602EDCE0073C898501817",
//		"trade_type":"APP",
//		"return_msg":"OK","result_code":"SUCCESS","mch_id":"1487723692",
//		"ok":"true","return_code":"SUCCESS",
//		"prepay_id":"wx2017082315531344c9aaa51a0832773449"}
	
//	appid，partnerid，prepayid，noncestr，timestamp，package
	
	private String appid;
	private String partnerid;
	private String prepayid;
	private String noncestr;
	private String timestamp;
	private String package_app;
	private String sign;
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getPrepayid() {
		return prepayid;
	}
	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getPackage_app() {
		return package_app;
	}
	public void setPackage_app(String package_app) {
		this.package_app = package_app;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
	
	
	
	
}
