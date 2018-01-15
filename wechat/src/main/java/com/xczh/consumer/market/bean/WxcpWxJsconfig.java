package com.xczh.consumer.market.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/*
 * WX js config 配置信息表；
 */
@SuppressWarnings("serial")
public class WxcpWxJsconfig implements Serializable {
	
	private String id	= "";
	private String appid = "";
	private String timestamp = "";
	private String nonce_str = "";
	private String signature = "";
	private String openid = "";
	private String access_token = "";
	private String jsapi_ticket = "";
	private String jsconfig_url = "";
	private Date create_time = new Date();
	private String expires_time = "";
	private ArrayList<String> jsApiList = new ArrayList<String>();
	private String expires_timestamp = "";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	public String getJsapi_ticket() {
		return jsapi_ticket;
	}
	public void setJsapi_ticket(String jsapi_ticket) {
		this.jsapi_ticket = jsapi_ticket;
	}
	
	public String getJsconfig_url() {
		return jsconfig_url;
	}
	public void setJsconfig_url(String jsconfig_url) {
		this.jsconfig_url = jsconfig_url;
	}
	
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	public String getExpires_time() {
		return expires_time;
	}
	public void setExpires_time(String expires_time) {
		this.expires_time = expires_time;
	}
	
	public ArrayList<String> getJsApiList() {
		return jsApiList;
	}
	public void setJsApiList(ArrayList<String> jsApiList) {
		this.jsApiList = jsApiList;
	}
	
	public String getExpires_timestamp() {
		return expires_timestamp;
	}
	public void setExpires_timestamp(String expires_timestamp) {
		this.expires_timestamp = expires_timestamp;
	}
		
}
