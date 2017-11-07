package com.xczh.consumer.market.bean;
// default package


public class AlipayPaymentRecordH5 implements java.io.Serializable {

	// Fields

	private Integer id;
	private String notifyTime;
	private String notifyType;
	private String notifyId;
	private String appId;
	private String charset;
	private String version;
	private String signType;
	private String sign;
	private String tradeNo;
	private String outTradeNo;
	private String outBizNo;
	private String buyerId;
	private String buyerLogonId;
	private String sellerId;
	private String sellerEmail;
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
	private String passbackParams;
	private String voucherDetailList;
	private String userId;

	// Constructors

	/** default constructor */
	public AlipayPaymentRecordH5() {
	}

	/** full constructor */
	public AlipayPaymentRecordH5(String notifyTime, String notifyType, String notifyId, String appId, String charset,
			String version, String signType, String sign, String tradeNo, String outTradeNo, String outBizNo,
			String buyerId, String buyerLogonId, String sellerId, String sellerEmail, String tradeStatus,
			String totalAmount, String receiptAmount, String invoiceAmount, String buyerPayAmount, String pointAmount,
			String refundFee, String subject, String body, String gmtCreate, String gmtPayment, String gmtRefund,
			String gmtClose, String fundBillList, String passbackParams, String voucherDetailList) {
		this.notifyTime = notifyTime;
		this.notifyType = notifyType;
		this.notifyId = notifyId;
		this.appId = appId;
		this.charset = charset;
		this.version = version;
		this.signType = signType;
		this.sign = sign;
		this.tradeNo = tradeNo;
		this.outTradeNo = outTradeNo;
		this.outBizNo = outBizNo;
		this.buyerId = buyerId;
		this.buyerLogonId = buyerLogonId;
		this.sellerId = sellerId;
		this.sellerEmail = sellerEmail;
		this.tradeStatus = tradeStatus;
		this.totalAmount = totalAmount;
		this.receiptAmount = receiptAmount;
		this.invoiceAmount = invoiceAmount;
		this.buyerPayAmount = buyerPayAmount;
		this.pointAmount = pointAmount;
		this.refundFee = refundFee;
		this.subject = subject;
		this.body = body;
		this.gmtCreate = gmtCreate;
		this.gmtPayment = gmtPayment;
		this.gmtRefund = gmtRefund;
		this.gmtClose = gmtClose;
		this.fundBillList = fundBillList;
		this.passbackParams = passbackParams;
		this.voucherDetailList = voucherDetailList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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

	public String getBuyerLogonId() {
		return buyerLogonId;
	}

	public void setBuyerLogonId(String buyerLogonId) {
		this.buyerLogonId = buyerLogonId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
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

	public String getPassbackParams() {
		return passbackParams;
	}

	public void setPassbackParams(String passbackParams) {
		this.passbackParams = passbackParams;
	}

	public String getVoucherDetailList() {
		return voucherDetailList;
	}

	public void setVoucherDetailList(String voucherDetailList) {
		this.voucherDetailList = voucherDetailList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}