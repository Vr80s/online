package com.xczh.consumer.market.bean;
// default package

import java.sql.Timestamp;
import java.util.Date;


/**
 * OeRewardStatement entity. @author MyEclipse Persistence Tools
 */

public class RewardStatement implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer channel;
	private String rewardId;
	private Date createTime;
	private Double price;
	private String giver;
	private String receiver;
	private String liveId;
	private Integer payType;
	private Integer clientType;
	private Integer status;
	private String orderNo;

	// Constructors

	/** default constructor */
	public RewardStatement() {
	}

	/** minimal constructor */
	public RewardStatement(Timestamp createTime) {
		this.createTime = createTime;
	}


	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getChannel() {
		return this.channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}


	public String getRewardId() {
		return rewardId;
	}

	public void setRewardId(String rewardId) {
		this.rewardId = rewardId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


	public String getGiver() {
		return this.giver;
	}

	public void setGiver(String giver) {
		this.giver = giver;
	}


	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


	public String getLiveId() {
		return this.liveId;
	}

	public void setLiveId(String liveId) {
		this.liveId = liveId;
	}


	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}


	public Integer getClientType() {
		return this.clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}


	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}