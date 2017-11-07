package com.xczhihui.bxg.online.web.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 视频验证
 * @author Haicheng Jiang
 *
 */
public class VedioAuthVo {
	//版本，固定为1
	private String version = "1";
	//是否可以完整播放，1允许，0不允许
	private Integer enable = 0;
	//试看时间，单位：秒，仅支持flash
	private Integer freetime = 0;
	//不允许播放或试看结束时播放器显示的内容，支持<a>标签
	private String message = "";
	//不允许播放或试看结束时回调的js函数，访问函数时会携带vid，为空不回调，仅支持flash
	private String callback = "onlinePlayCallbak";
	//跑马灯节点，
	private Map<String, Object> marquee = new HashMap<String, Object>();
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public Integer getFreetime() {
		return freetime;
	}
	public void setFreetime(Integer freetime) {
		this.freetime = freetime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public Map<String, Object> getMarquee() {
		return marquee;
	}
	public void setMarquee(Map<String, Object> marquee) {
		this.marquee = marquee;
	}
}