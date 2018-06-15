package com.xczhihui.bxg.online.web.vo;

public class GiftInfo {

	private String userId;
	private String giftId;
	private int giftCount;
	private String giveTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGiftId() {
		return giftId;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}

	public int getGiftCount() {
		return giftCount;
	}

	public void setGiftCount(int giftCount) {
		this.giftCount = giftCount;
	}

	public String getGiveTime() {
		return giveTime;
	}

	public void setGiveTime(String giveTime) {
		this.giveTime = giveTime;
	}

	@Override
	public String toString() {
		
		return userId+"-"+giftId+"-"+giftCount+"-"+giveTime;
	}

	
}
