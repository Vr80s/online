package com.xczhihui.course.model;

import java.io.Serializable;

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
@TableName("wxcp_pay_flow")
public class WxcpPayFlow extends Model<WxcpPayFlow> {

    private static final long serialVersionUID = 1L;

    /**
     * 微信充值、购买、打赏记录表
     */
    @TableId("flow_id")
	private String flowId;
    /**
     * appid
     */
	private String appid;
    /**
     * attach
     */
	private String attach;
    /**
     * bank_type
     */
	@TableField("bank_type")
	private String bankType;
    /**
     * fee_type
     */
	@TableField("fee_type")
	private String feeType;
    /**
     * is_subscribe
     */
	@TableField("is_subscribe")
	private String isSubscribe;
    /**
     * mch_id
     */
	@TableField("mch_id")
	private String mchId;
    /**
     * nonce_str
     */
	@TableField("nonce_str")
	private String nonceStr;
    /**
     * openid
     */
	private String openid;
    /**
     * out_trade_no,for pass raw order id return,not modify
     */
	@TableField("out_trade_no")
	private String outTradeNo;
	@TableField("result_code")
	private String resultCode;
    /**
     * return_code
     */
	@TableField("return_code")
	private String returnCode;
    /**
     * sign
     */
	private String sign;
    /**
     * sub_mch_id
     */
	@TableField("sub_mch_id")
	private String subMchId;
    /**
     * time_end
     */
	@TableField("time_end")
	private Date timeEnd;
    /**
     * total_fee
     */
	@TableField("total_fee")
	private Integer totalFee;
    /**
     * trade_type
     */
	@TableField("trade_type")
	private String tradeType;
    /**
     * transaction_id,pay flow id by wx.
     */
	@TableField("transaction_id")
	private String transactionId;
    /**
     * 支付类型（区分大小写）：Alipay（支付宝）、WeChat（微信）
     */
	@TableField("payment_type")
	private String paymentType;
	@TableField("user_id")
	private String userId;
	private String subject;


	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSubMchId() {
		return subMchId;
	}

	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	protected Serializable pkVal() {
		return this.flowId;
	}

	@Override
	public String toString() {
		return "WxcpPayFlow{" +
			", flowId=" + flowId +
			", appid=" + appid +
			", attach=" + attach +
			", bankType=" + bankType +
			", feeType=" + feeType +
			", isSubscribe=" + isSubscribe +
			", mchId=" + mchId +
			", nonceStr=" + nonceStr +
			", openid=" + openid +
			", outTradeNo=" + outTradeNo +
			", resultCode=" + resultCode +
			", returnCode=" + returnCode +
			", sign=" + sign +
			", subMchId=" + subMchId +
			", timeEnd=" + timeEnd +
			", totalFee=" + totalFee +
			", tradeType=" + tradeType +
			", transactionId=" + transactionId +
			", paymentType=" + paymentType +
			", userId=" + userId +
			", subject=" + subject +
			"}";
	}
}
