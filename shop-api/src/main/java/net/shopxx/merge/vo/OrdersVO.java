package net.shopxx.merge.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersVO implements Serializable {
    private Long id;

    private Date createdDate;

    private Date lastmodifieddate;

    private Long version;

    private String address;

    private BigDecimal amount;

    private BigDecimal amountpaid;

    private String areaname;

    private Date completedate;

    private String consignee;

    private BigDecimal coupondiscount;

    private Long exchangepoint;

    private Date expire;

    private BigDecimal fee;

    private BigDecimal freight;

    private String invoicecontent;

    private String invoicetaxnumber;

    private String invoicetitle;

    private Boolean isallocatedstock;

    private Boolean isexchangepoint;

    private Boolean isreviewed;

    private Boolean isusecouponcode;

    private String memo;

    private BigDecimal offsetamount;

    private String paymentmethodname;

    private Integer paymentmethodtype;

    private String phone;

    private BigDecimal price;

    private BigDecimal promotiondiscount;

    private String promotionnames;

    private Integer quantity;

    private BigDecimal refundamount;

    private Integer returnedquantity;

    private Long rewardpoint;

    private Integer shippedquantity;

    private String shippingmethodname;

    private String sn;

    private Integer status;

    private BigDecimal tax;

    private Integer type;

    private Integer weight;

    private String zipcode;

    private Long areaId;

    private Long couponcodeId;

    private Long memberId;

    private Long paymentmethodId;

    private Long shippingmethodId;

    private Long storeId;
    
    /**
	 * 订单项
	 */
	private List<OrderItemVO> orderVoItems = new ArrayList<OrderItemVO>();

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getLastmodifieddate() {
        return lastmodifieddate;
    }

    public void setLastmodifieddate(Date lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountpaid() {
        return amountpaid;
    }

    public void setAmountpaid(BigDecimal amountpaid) {
        this.amountpaid = amountpaid;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname == null ? null : areaname.trim();
    }

    public Date getCompletedate() {
        return completedate;
    }

    public void setCompletedate(Date completedate) {
        this.completedate = completedate;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public BigDecimal getCoupondiscount() {
        return coupondiscount;
    }

    public void setCoupondiscount(BigDecimal coupondiscount) {
        this.coupondiscount = coupondiscount;
    }

    public Long getExchangepoint() {
        return exchangepoint;
    }

    public void setExchangepoint(Long exchangepoint) {
        this.exchangepoint = exchangepoint;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public String getInvoicecontent() {
        return invoicecontent;
    }

    public void setInvoicecontent(String invoicecontent) {
        this.invoicecontent = invoicecontent == null ? null : invoicecontent.trim();
    }

    public String getInvoicetaxnumber() {
        return invoicetaxnumber;
    }

    public void setInvoicetaxnumber(String invoicetaxnumber) {
        this.invoicetaxnumber = invoicetaxnumber == null ? null : invoicetaxnumber.trim();
    }

    public String getInvoicetitle() {
        return invoicetitle;
    }

    public void setInvoicetitle(String invoicetitle) {
        this.invoicetitle = invoicetitle == null ? null : invoicetitle.trim();
    }

    public Boolean getIsallocatedstock() {
        return isallocatedstock;
    }

    public void setIsallocatedstock(Boolean isallocatedstock) {
        this.isallocatedstock = isallocatedstock;
    }

    public Boolean getIsexchangepoint() {
        return isexchangepoint;
    }

    public void setIsexchangepoint(Boolean isexchangepoint) {
        this.isexchangepoint = isexchangepoint;
    }

    public Boolean getIsreviewed() {
        return isreviewed;
    }

    public void setIsreviewed(Boolean isreviewed) {
        this.isreviewed = isreviewed;
    }

    public Boolean getIsusecouponcode() {
        return isusecouponcode;
    }

    public void setIsusecouponcode(Boolean isusecouponcode) {
        this.isusecouponcode = isusecouponcode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public BigDecimal getOffsetamount() {
        return offsetamount;
    }

    public void setOffsetamount(BigDecimal offsetamount) {
        this.offsetamount = offsetamount;
    }

    public String getPaymentmethodname() {
        return paymentmethodname;
    }

    public void setPaymentmethodname(String paymentmethodname) {
        this.paymentmethodname = paymentmethodname == null ? null : paymentmethodname.trim();
    }

    public Integer getPaymentmethodtype() {
        return paymentmethodtype;
    }

    public void setPaymentmethodtype(Integer paymentmethodtype) {
        this.paymentmethodtype = paymentmethodtype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPromotiondiscount() {
        return promotiondiscount;
    }

    public void setPromotiondiscount(BigDecimal promotiondiscount) {
        this.promotiondiscount = promotiondiscount;
    }

    public String getPromotionnames() {
        return promotionnames;
    }

    public void setPromotionnames(String promotionnames) {
        this.promotionnames = promotionnames == null ? null : promotionnames.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRefundamount() {
        return refundamount;
    }

    public void setRefundamount(BigDecimal refundamount) {
        this.refundamount = refundamount;
    }

    public Integer getReturnedquantity() {
        return returnedquantity;
    }

    public void setReturnedquantity(Integer returnedquantity) {
        this.returnedquantity = returnedquantity;
    }

    public Long getRewardpoint() {
        return rewardpoint;
    }

    public void setRewardpoint(Long rewardpoint) {
        this.rewardpoint = rewardpoint;
    }

    public Integer getShippedquantity() {
        return shippedquantity;
    }

    public void setShippedquantity(Integer shippedquantity) {
        this.shippedquantity = shippedquantity;
    }

    public String getShippingmethodname() {
        return shippingmethodname;
    }

    public void setShippingmethodname(String shippingmethodname) {
        this.shippingmethodname = shippingmethodname == null ? null : shippingmethodname.trim();
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getCouponcodeId() {
        return couponcodeId;
    }

    public void setCouponcodeId(Long couponcodeId) {
        this.couponcodeId = couponcodeId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getPaymentmethodId() {
        return paymentmethodId;
    }

    public void setPaymentmethodId(Long paymentmethodId) {
        this.paymentmethodId = paymentmethodId;
    }

    public Long getShippingmethodId() {
        return shippingmethodId;
    }

    public void setShippingmethodId(Long shippingmethodId) {
        this.shippingmethodId = shippingmethodId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

	public List<OrderItemVO> getOrderVoItems() {
		return orderVoItems;
	}

	public void setOrderVoItems(List<OrderItemVO> orderVoItems) {
		this.orderVoItems = orderVoItems;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}