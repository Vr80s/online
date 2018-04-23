package com.xczhihui.pay.weixin.api;

public class WxPayBean {
    private String appId4H5;
    private String mchId4H5;
	private String partnerKey4H5;
	private String appSecret;
	private String certPath;
    private String domain;

	private String appId4App;
	private String mchId4App;
	private String partnerKey4App;

	public String getAppId4H5() {
		return appId4H5;
	}

	public void setAppId4H5(String appId4H5) {
		this.appId4H5 = appId4H5;
	}

	public String getMchId4H5() {
		return mchId4H5;
	}

	public void setMchId4H5(String mchId4H5) {
		this.mchId4H5 = mchId4H5;
	}

	public String getPartnerKey4H5() {
		return partnerKey4H5;
	}

	public void setPartnerKey4H5(String partnerKey4H5) {
		this.partnerKey4H5 = partnerKey4H5;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAppId4App() {
		return appId4App;
	}

	public void setAppId4App(String appId4App) {
		this.appId4App = appId4App;
	}

	public String getMchId4App() {
		return mchId4App;
	}

	public void setMchId4App(String mchId4App) {
		this.mchId4App = mchId4App;
	}

	public String getPartnerKey4App() {
		return partnerKey4App;
	}

	public void setPartnerKey4App(String partnerKey4App) {
		this.partnerKey4App = partnerKey4App;
	}
}
