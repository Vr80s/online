package com.xczhihui.bxg.online.manager.gift.vo;

import java.util.Date;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;


/** 
 * ClassName: GiftVo.java <br>
 * Description:礼物流水表 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月15日<br>
 */
public class GiftStatementVo extends OnlineBaseVo{


    /**
	 * Copyright © 2017 xinchengzhihui. All rights reserved.
	 */
	private static final long serialVersionUID = -5842036551442937318L;

	/**
     *礼物ID
     */
    private int id;
    
    private int channel;
    
    private String giftId;

    private String giftName;
    
    private int count;
    
    private Double price ;
    
    private String giver;
    
    private String receiver;
    
    private String liveId;
    
    private int payType;
    
    private int clientType;

    private int searchType;
    
    private String searchCondition;
    
    private Date startTime;
    
    private Date stopTime;

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public String getGiftId() {
		return giftId;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getGiver() {
		return giver;
	}

	public void setGiver(String giver) {
		this.giver = giver;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getLiveId() {
		return liveId;
	}

	public void setLiveId(String liveId) {
		this.liveId = liveId;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}
    
}

