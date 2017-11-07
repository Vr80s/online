package com.xczhihui.bxg.online.api.po;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * OeRewardStatement entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "oe_reward_statement", catalog = "online", uniqueConstraints = @UniqueConstraint(columnNames = "order_no"))
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


	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "channel")
	public Integer getChannel() {
		return this.channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	@Column(name = "reward_id", nullable = false, length = 32)
	public String getRewardId() {
		return this.rewardId;
	}

	public void setRewardId(String rewardId) {
		this.rewardId = rewardId;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "price", nullable = false, precision = 7)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "giver", nullable = false, length = 32)
	public String getGiver() {
		return this.giver;
	}

	public void setGiver(String giver) {
		this.giver = giver;
	}

	@Column(name = "receiver", nullable = false, length = 32)
	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Column(name = "live_id", nullable = false, length = 32)
	public String getLiveId() {
		return this.liveId;
	}

	public void setLiveId(String liveId) {
		this.liveId = liveId;
	}

	@Column(name = "pay_type", nullable = false)
	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@Column(name = "client_type", nullable = false)
	public Integer getClientType() {
		return this.clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "order_no", unique = true, nullable = false, length = 32)
	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		return "RewardStatement [id=" + id + ", channel=" + channel
				+ ", rewardId=" + rewardId + ", createTime=" + createTime
				+ ", price=" + price + ", giver=" + giver + ", receiver="
				+ receiver + ", liveId=" + liveId + ", payType=" + payType
				+ ", clientType=" + clientType + ", status=" + status
				+ ", orderNo=" + orderNo + "]";
	}

}