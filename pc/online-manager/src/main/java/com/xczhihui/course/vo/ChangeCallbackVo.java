package com.xczhihui.course.vo;

public class ChangeCallbackVo {

	private String channel;
	private String method;
	private String webinarId;
	private String type;
	private String event;
	private String unionId;
	private String timestamp;
	private String signature;
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getWebinarId() {
		return webinarId;
	}
	public void setWebinarId(String webinarId) {
		this.webinarId = webinarId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	@Override
	public String toString() {
		return "ChangeCallbackVo [channel=" + channel + ", method=" + method
				+ ", webinarId=" + webinarId + ", type=" + type + ", event="
				+ event + ", unionId=" + unionId + ", timestamp=" + timestamp
				+ ", signature=" + signature + "]";
	}
	
	
}
