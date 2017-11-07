package com.xczhihui.bxg.online.common.domain;
// default package

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * AlipayPaymentRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "alipay_payment_record")
public class AlipayPaymentRecord implements java.io.Serializable {

	// Fields


	private Integer id;
	private String tradeNo;
	private String appId;
	private String outTradeNo;
	private String outBizNo;
	private String buyerId;
	private String sellerId;
	private String tradeStatus;
	private String totalAmount;
	private String receiptAmount;
	private String invoiceAmount;
	private String buyerPayAmount;
	private String pointAmount;
	private String refundFee;
	private String subject;
	private String body;
	private String gmtCreate;
	private String gmtPayment;
	private String gmtRefund;
	private String gmtClose;
	private String fundBillList;
	private String voucherDetailList;
	private String passbackParams;
	private String userId;

	// Constructors

	/** default constructor */
	public AlipayPaymentRecord() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "trade_no", unique = true, nullable = false, length = 64)

	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	@Column(name = "app_id", length = 32)

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(name = "out_trade_no", length = 64)

	public String getOutTradeNo() {
		return this.outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	@Column(name = "out_biz_no", length = 64)

	public String getOutBizNo() {
		return this.outBizNo;
	}

	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}

	@Column(name = "buyer_id", length = 16)

	public String getBuyerId() {
		return this.buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	@Column(name = "seller_id", length = 30)

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	@Column(name = "trade_status", length = 32)

	public String getTradeStatus() {
		return this.tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	@Column(name = "total_amount", length = 100)

	public String getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(name = "receipt_amount", length = 100)

	public String getReceiptAmount() {
		return this.receiptAmount;
	}

	public void setReceiptAmount(String receiptAmount) {
		this.receiptAmount = receiptAmount;
	}

	@Column(name = "invoice_amount", length = 100)

	public String getInvoiceAmount() {
		return this.invoiceAmount;
	}

	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	@Column(name = "buyer_pay_amount", length = 100)

	public String getBuyerPayAmount() {
		return this.buyerPayAmount;
	}

	public void setBuyerPayAmount(String buyerPayAmount) {
		this.buyerPayAmount = buyerPayAmount;
	}

	@Column(name = "point_amount", length = 100)

	public String getPointAmount() {
		return this.pointAmount;
	}

	public void setPointAmount(String pointAmount) {
		this.pointAmount = pointAmount;
	}

	@Column(name = "refund_fee", length = 100)

	public String getRefundFee() {
		return this.refundFee;
	}

	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}

	@Column(name = "subject", length = 256)

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String suject) {
		this.subject = suject;
	}

	@Column(name = "body", length = 400)

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Column(name = "gmt_create", length = 100)

	public String getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Column(name = "gmt_payment", length = 100)

	public String getGmtPayment() {
		return this.gmtPayment;
	}

	public void setGmtPayment(String gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	@Column(name = "gmt_refund", length = 100)

	public String getGmtRefund() {
		return this.gmtRefund;
	}

	public void setGmtRefund(String gmtRefund) {
		this.gmtRefund = gmtRefund;
	}

	@Column(name = "gmt_close", length = 100)

	public String getGmtClose() {
		return this.gmtClose;
	}

	public void setGmtClose(String gmtClose) {
		this.gmtClose = gmtClose;
	}

	@Column(name = "fund_bill_list", length = 512)

	public String getFundBillList() {
		return this.fundBillList;
	}

	public void setFundBillList(String fundBillList) {
		this.fundBillList = fundBillList;
	}

	@Column(name = "voucher_detail_list", length = 512)

	public String getVoucherDetailList() {
		return this.voucherDetailList;
	}

	public void setVoucherDetailList(String voucherDetailList) {
		this.voucherDetailList = voucherDetailList;
	}

	@Column(name = "passback_params", length = 512)

	public String getPassbackParams() {
		return this.passbackParams;
	}

	public void setPassbackParams(String passbackParams) {
		this.passbackParams = passbackParams;
	}

	@Column(name = "user_id", length = 32)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}