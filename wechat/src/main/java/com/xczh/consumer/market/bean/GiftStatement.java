package com.xczh.consumer.market.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author liutao
 * @create 2017-08-21 20:20
 **/
public class GiftStatement implements Serializable{

    // Fields

    private Integer id;
    private Integer channel;
    private String giftId;
    private String giftName;
    private Date createTime;
    private Double price;
    private Integer count;
    private String giver;
    private String receiver;
    private String liveId;
    private Integer payType;
    private Integer clientType;
    private Integer status;
    private Integer continuousCount;
    
    private String giftImg;

    // Constructors

    public Integer getContinuousCount() {
		return continuousCount;
	}

	public void setContinuousCount(Integer continuousCount) {
		this.continuousCount = continuousCount;
	}

	/** default constructor */
    public GiftStatement() {
    }

    /** minimal constructor */
    public GiftStatement(Timestamp createTime) {
        this.createTime = createTime;
    }

    /** full constructor */
    public GiftStatement(Integer channel, String giftId, String giftName, Timestamp createTime, Double price,
                           Integer count, String giver, String receiver, String liveId, Integer payType, Integer clientType,
                           Integer status) {
        this.channel = channel;
        this.giftId = giftId;
        this.giftName = giftName;
        this.createTime = createTime;
        this.price = price;
        this.count = count;
        this.giver = giver;
        this.receiver = receiver;
        this.liveId = liveId;
        this.payType = payType;
        this.clientType = clientType;
        this.status = status;
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


    public String getGiftId() {
        return this.giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }


    public String getGiftName() {
        return this.giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
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


    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

	public String getGiftImg() {
		return giftImg;
	}

	public void setGiftImg(String giftImg) {
		this.giftImg = giftImg;
	}
    
    
    
}
