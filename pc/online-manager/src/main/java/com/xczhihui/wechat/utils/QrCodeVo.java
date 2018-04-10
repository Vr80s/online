package com.xczhihui.wechat.utils;

import java.util.Date;

public class QrCodeVo {

	
	private Date createTime;  //二维码创建时间
	
	private String customQrCodeUrl;    //二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
	
	private String wechatUrl;     //微信二维码下载地址
	
	private Long validTime;   //二维码有效期  单位秒
	
	private String ticket;    //二维码票据
	
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getCustomQrCodeUrl() {
		return customQrCodeUrl;
	}

	public void setCustomQrCodeUrl(String customQrCodeUrl) {
		this.customQrCodeUrl = customQrCodeUrl;
	}

	public String getWechatUrl() {
		return wechatUrl;
	}

	public void setWechatUrl(String wechatUrl) {
		this.wechatUrl = wechatUrl;
	}

	public Long getValidTime() {
		return validTime;
	}

	public void setValidTime(Long validTime) {
		this.validTime = validTime;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Override
	public String toString() {
		return "QrCodeVo [createTime=" + createTime + ", customQrCodeUrl="
				+ customQrCodeUrl + ", wechatUrl=" + wechatUrl + ", validTime="
				+ validTime + ", ticket=" + ticket + "]";
	}

}
