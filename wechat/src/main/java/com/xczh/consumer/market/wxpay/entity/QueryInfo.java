package com.xczh.consumer.market.wxpay.entity;

import com.xczh.consumer.market.wxpay.SignAbledBean;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.util.CommonUtil;

/**
 * 查询参数实体
 */
public class QueryInfo extends SignAbledBean {
	
	private String appid;
	private String mch_id;
	private String out_trade_no;
	private String nonce_str;
	private String sign;

	public QueryInfo(String orderid,boolean firstry) throws Exception{
		
		this.appid=firstry? WxPayConst.app_appid: WxPayConst.gzh_appid;
		this.mch_id=firstry? WxPayConst.app_mchid: WxPayConst.gzh_mchid;
		this.out_trade_no=orderid;
		this.nonce_str= CommonUtil.CreateNoncestr();
		this.sign= CommonUtil.getSign(this);
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

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
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
}
