package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/** 
 * ClassName: GiftStatementVo.java <br>
 * Description:礼物流水表 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月15日<br>
 */
@Entity
@Table(name = "oe_gift_statement")
public class GiftStatement implements Serializable{

	/**
	 * Copyright © 2017 xinchengzhihui. All rights reserved.
	 */
	private static final long serialVersionUID = 380595710822938948L;

	/**
	 * 唯一标识
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "channel")
    private int channel;
    
	@Column(name = "gift_id")
    private String giftId;
	
	@Column(name = "gift_name")
	private String giftName;
    
	@Column(name = "count")
    private int count;
    
	@Column(name = "price")
    private Double price ;
    
	@Column(name = "giver")
    private String giver;
    
	@Column(name = "receiver")
    private String receiver;
    
	@Column(name = "live_id")
    private String liveId;
    
	@Column(name = "pay_type")
    private int payType;
    
	@Column(name = "client_type")
    private int clientType;
	
	@Transient
	private String receiverName;
	
	@Transient
	private String giverName;
	@Transient
	private Integer continuousCount;
	@Transient
	private String giftImg;

	public String getGiftImg() {
		return giftImg;
	}

	public void setGiftImg(String giftImg) {
		this.giftImg = giftImg;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
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

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getContinuousCount() {
		return continuousCount;
	}

	public void setContinuousCount(Integer continuousCount) {
		this.continuousCount = continuousCount;
	}

}

