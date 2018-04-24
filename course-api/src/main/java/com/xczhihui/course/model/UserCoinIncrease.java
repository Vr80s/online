package com.xczhihui.course.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
@TableName("user_coin_increase")
public class UserCoinIncrease extends Model<UserCoinIncrease> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户-代币增加明细表id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户id
     */
	@TableField("user_id")
	private String userId;
    /**
     * 用户-代币账户表id
     */
	@TableField("user_coin_id")
	private Integer userCoinId;
    /**
     * 1.充值2.平台赠送3.礼物4打赏5.平台提现驳回退回6.结算7.卖课所得
     */
	@TableField("change_type")
	private Integer changeType;
    /**
     * 变动数量
     */
	private BigDecimal value;
    /**
     * 变动充值余额
     */
	private BigDecimal balance;
    /**
     * 变动赠送余额
     */
	@TableField("balance_give")
	private BigDecimal balanceGive;
    /**
     * 变动主播用户收到的礼物打赏余额
     */
	@TableField("balance_reward_gift")
	private BigDecimal balanceRewardGift;
    /**
     * 变动人民币余额
     */
	private BigDecimal rmb;
    /**
     * 当变动类型为礼物打赏或购课时，平台抽成金额
     */
	@TableField("brokerage_value")
	private BigDecimal brokerageValue;
    /**
     * 当变动类型为支付时：支付类型 0.支付宝1.微信2.苹果
     */
	@TableField("pay_type")
	private Integer payType;
    /**
     * 变动账户类型：1.用户熊猫币余额2.主播熊猫币余额3.主播人民币余额
     */
	@TableField("balance_type")
	private Integer balanceType;
    /**
     * 1.充值时，充值的平台单号
     */
	@TableField("order_no_recharge")
	private String orderNoRecharge;
    /**
     * 2.赠送时，赠送单号
     */
	@TableField("order_no_largess")
	private String orderNoLargess;
    /**
     * 3.接收礼物时，礼物流水id
     */
	@TableField("order_no_gift")
	private String orderNoGift;
    /**
     * 4.接收打赏时，打赏流水id
     */
	@TableField("order_no_reward")
	private String orderNoReward;
    /**
     * 5.平台提现驳回退回，提现申请的id
     */
	@TableField("order_no_reject")
	private String orderNoReject;
    /**
     * 6.结算（人民币余额增加）
     */
	@TableField("order_no_settlement")
	private String orderNoSettlement;
    /**
     * 7.卖课所得
     */
	@TableField("order_no_course")
	private String orderNoCourse;
	private String version;
	@TableField("create_time")
	private Date createTime;
	@TableField("update_time")
	private Date updateTime;
    /**
     * 1有效0无效
     */
	private Boolean status;
    /**
     * 1已删除0未删除
     */
	private Boolean deleted;
	private String remark;
    /**
     * 订单来源:1.pc 2.h5 3.android 4.ios
     */
	@TableField("order_from")
	private Integer orderFrom;
    /**
     * 当变动类型为礼物打赏或购课时，IOS官方抽成金额
     */
	@TableField("ios_brokerage_value")
	private BigDecimal iosBrokerageValue;
    /**
     * 分成比例，单位:%
     */
	private BigDecimal ratio;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getUserCoinId() {
		return userCoinId;
	}

	public void setUserCoinId(Integer userCoinId) {
		this.userCoinId = userCoinId;
	}

	public Integer getChangeType() {
		return changeType;
	}

	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalanceGive() {
		return balanceGive;
	}

	public void setBalanceGive(BigDecimal balanceGive) {
		this.balanceGive = balanceGive;
	}

	public BigDecimal getBalanceRewardGift() {
		return balanceRewardGift;
	}

	public void setBalanceRewardGift(BigDecimal balanceRewardGift) {
		this.balanceRewardGift = balanceRewardGift;
	}

	public BigDecimal getRmb() {
		return rmb;
	}

	public void setRmb(BigDecimal rmb) {
		this.rmb = rmb;
	}

	public BigDecimal getBrokerageValue() {
		return brokerageValue;
	}

	public void setBrokerageValue(BigDecimal brokerageValue) {
		this.brokerageValue = brokerageValue;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(Integer balanceType) {
		this.balanceType = balanceType;
	}

	public String getOrderNoRecharge() {
		return orderNoRecharge;
	}

	public void setOrderNoRecharge(String orderNoRecharge) {
		this.orderNoRecharge = orderNoRecharge;
	}

	public String getOrderNoLargess() {
		return orderNoLargess;
	}

	public void setOrderNoLargess(String orderNoLargess) {
		this.orderNoLargess = orderNoLargess;
	}

	public String getOrderNoGift() {
		return orderNoGift;
	}

	public void setOrderNoGift(String orderNoGift) {
		this.orderNoGift = orderNoGift;
	}

	public String getOrderNoReward() {
		return orderNoReward;
	}

	public void setOrderNoReward(String orderNoReward) {
		this.orderNoReward = orderNoReward;
	}

	public String getOrderNoReject() {
		return orderNoReject;
	}

	public void setOrderNoReject(String orderNoReject) {
		this.orderNoReject = orderNoReject;
	}

	public String getOrderNoSettlement() {
		return orderNoSettlement;
	}

	public void setOrderNoSettlement(String orderNoSettlement) {
		this.orderNoSettlement = orderNoSettlement;
	}

	public String getOrderNoCourse() {
		return orderNoCourse;
	}

	public void setOrderNoCourse(String orderNoCourse) {
		this.orderNoCourse = orderNoCourse;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(Integer orderFrom) {
		this.orderFrom = orderFrom;
	}

	public BigDecimal getIosBrokerageValue() {
		return iosBrokerageValue;
	}

	public void setIosBrokerageValue(BigDecimal iosBrokerageValue) {
		this.iosBrokerageValue = iosBrokerageValue;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "UserCoinIncrease{" +
			", id=" + id +
			", userId=" + userId +
			", userCoinId=" + userCoinId +
			", changeType=" + changeType +
			", value=" + value +
			", balance=" + balance +
			", balanceGive=" + balanceGive +
			", balanceRewardGift=" + balanceRewardGift +
			", rmb=" + rmb +
			", brokerageValue=" + brokerageValue +
			", payType=" + payType +
			", balanceType=" + balanceType +
			", orderNoRecharge=" + orderNoRecharge +
			", orderNoLargess=" + orderNoLargess +
			", orderNoGift=" + orderNoGift +
			", orderNoReward=" + orderNoReward +
			", orderNoReject=" + orderNoReject +
			", orderNoSettlement=" + orderNoSettlement +
			", orderNoCourse=" + orderNoCourse +
			", version=" + version +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			", deleted=" + deleted +
			", remark=" + remark +
			", orderFrom=" + orderFrom +
			", iosBrokerageValue=" + iosBrokerageValue +
			", ratio=" + ratio +
			"}";
	}
}
