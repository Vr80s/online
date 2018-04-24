package com.xczhihui.course.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("alipay_payment_record")
public class AlipayPaymentRecord extends Model<AlipayPaymentRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * pc--支付打赏充值记录
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("trade_no")
	private String tradeNo;
	@TableField("app_id")
	private String appId;
	@TableField("out_trade_no")
	private String outTradeNo;
	@TableField("out_biz_no")
	private String outBizNo;
	@TableField("buyer_id")
	private String buyerId;
	@TableField("seller_id")
	private String sellerId;
	@TableField("trade_status")
	private String tradeStatus;
	@TableField("total_amount")
	private String totalAmount;
	@TableField("receipt_amount")
	private String receiptAmount;
	@TableField("invoice_amount")
	private String invoiceAmount;
	@TableField("buyer_pay_amount")
	private String buyerPayAmount;
	@TableField("point_amount")
	private String pointAmount;
	@TableField("refund_fee")
	private String refundFee;
	private String subject;
	private String body;
	@TableField("gmt_create")
	private String gmtCreate;
	@TableField("gmt_payment")
	private String gmtPayment;
	@TableField("gmt_refund")
	private String gmtRefund;
	@TableField("gmt_close")
	private String gmtClose;
	@TableField("fund_bill_list")
	private String fundBillList;
	@TableField("voucher_detail_list")
	private String voucherDetailList;
	@TableField("passback_params")
	private String passbackParams;
	@TableField("user_id")
	private String userId;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getOutBizNo() {
		return outBizNo;
	}

	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(String receiptAmount) {
		this.receiptAmount = receiptAmount;
	}

	public String getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getBuyerPayAmount() {
		return buyerPayAmount;
	}

	public void setBuyerPayAmount(String buyerPayAmount) {
		this.buyerPayAmount = buyerPayAmount;
	}

	public String getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(String pointAmount) {
		this.pointAmount = pointAmount;
	}

	public String getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(String gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	public String getGmtRefund() {
		return gmtRefund;
	}

	public void setGmtRefund(String gmtRefund) {
		this.gmtRefund = gmtRefund;
	}

	public String getGmtClose() {
		return gmtClose;
	}

	public void setGmtClose(String gmtClose) {
		this.gmtClose = gmtClose;
	}

	public String getFundBillList() {
		return fundBillList;
	}

	public void setFundBillList(String fundBillList) {
		this.fundBillList = fundBillList;
	}

	public String getVoucherDetailList() {
		return voucherDetailList;
	}

	public void setVoucherDetailList(String voucherDetailList) {
		this.voucherDetailList = voucherDetailList;
	}

	public String getPassbackParams() {
		return passbackParams;
	}

	public void setPassbackParams(String passbackParams) {
		this.passbackParams = passbackParams;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AlipayPaymentRecord{" +
			", id=" + id +
			", tradeNo=" + tradeNo +
			", appId=" + appId +
			", outTradeNo=" + outTradeNo +
			", outBizNo=" + outBizNo +
			", buyerId=" + buyerId +
			", sellerId=" + sellerId +
			", tradeStatus=" + tradeStatus +
			", totalAmount=" + totalAmount +
			", receiptAmount=" + receiptAmount +
			", invoiceAmount=" + invoiceAmount +
			", buyerPayAmount=" + buyerPayAmount +
			", pointAmount=" + pointAmount +
			", refundFee=" + refundFee +
			", subject=" + subject +
			", body=" + body +
			", gmtCreate=" + gmtCreate +
			", gmtPayment=" + gmtPayment +
			", gmtRefund=" + gmtRefund +
			", gmtClose=" + gmtClose +
			", fundBillList=" + fundBillList +
			", voucherDetailList=" + voucherDetailList +
			", passbackParams=" + passbackParams +
			", userId=" + userId +
			"}";
	}
}
