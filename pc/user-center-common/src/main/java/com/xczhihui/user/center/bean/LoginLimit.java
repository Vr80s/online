package com.xczhihui.user.center.bean;

import java.io.Serializable;
import java.util.Date;

public class LoginLimit implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String loginName;

	private String pcSign;
	private String appSign;
	private String h5Sign;
	private String pcInfo;
	private String appInfo;
	private String h5Info;

	private Date pcLastTime;
	private Date appLastTime;
	private Date h5LastTime;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPcSign() {
		return pcSign;
	}

	public void setPcSign(String pcSign) {
		this.pcSign = pcSign;
	}

	public String getAppSign() {
		return appSign;
	}

	public void setAppSign(String appSign) {
		this.appSign = appSign;
	}

	public String getH5Sign() {
		return h5Sign;
	}

	public void setH5Sign(String h5Sign) {
		this.h5Sign = h5Sign;
	}

	public String getPcInfo() {
		return pcInfo;
	}

	public void setPcInfo(String pcInfo) {
		this.pcInfo = pcInfo;
	}

	public String getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(String appInfo) {
		this.appInfo = appInfo;
	}

	public String getH5Info() {
		return h5Info;
	}

	public void setH5Info(String h5Info) {
		this.h5Info = h5Info;
	}

	public Date getPcLastTime() {
		return pcLastTime;
	}

	public void setPcLastTime(Date pcLastTime) {
		this.pcLastTime = pcLastTime;
	}

	public Date getAppLastTime() {
		return appLastTime;
	}

	public void setAppLastTime(Date appLastTime) {
		this.appLastTime = appLastTime;
	}

	public Date getH5LastTime() {
		return h5LastTime;
	}

	public void setH5LastTime(Date h5LastTime) {
		this.h5LastTime = h5LastTime;
	}
}
