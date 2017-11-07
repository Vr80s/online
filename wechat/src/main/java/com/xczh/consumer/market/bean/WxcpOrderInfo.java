package com.xczh.consumer.market.bean;
import java.io.Serializable;


/**
 * 
 * 订单表
 * @author yanghui
 **/
@SuppressWarnings("serial")
public class WxcpOrderInfo implements Serializable {

	/**主键**/
	 private String id;

	/**买家相关表 关联字段**/
	 private String buyer_id;

	/**买家名称**/
	 private String buyer_name;

	/**买家联系方式**/
	 private String buyer_mobile;

	/**卖家ID **/
	 private String seller_id;

	/**卖家名称**/
	 private String seller_name;

	/**卖家联系方式**/
	 private String seller_mobile;

	/**关联店铺信息表id **/
	 private String shop_id;

	/**店铺名称**/
	 private String shop_name;

	/**订单状态，支持类型：10（待付款），20（已付款），30(已取消.未支付)，40（已完成，已评价）**/
	 private Integer order_status;

	/**订单原价(以分为单位)**/
	 private Integer origin_fee;

	/**实际支付金额(以分为单位)（暂不用）**/
	 private Integer acture_fee;

	/**优惠的金额(以分为单位)（暂不用）**/
	 private Integer coupon_price;

	/**过期时间**/
	 private java.util.Date expire_time;

	/**支付超时时间**/
	 private java.util.Date payment_timeout;

	/**支付时间**/
	 private java.util.Date payment_time;

	/**是否是删除状态，-1：删除，1：未删除**/
	 private Integer is_deleted;

	/**订单创建时间**/
	 private java.util.Date created_time;

	/**订单最后更新时间**/
	 private java.util.Date updated_time;
	 
	/**订单人性化编号，不用关键字id(UUID)；**/
	 private String order_no;
	 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getBuyer_name() {
		return buyer_name;
	}

	public void setBuyer_name(String buyer_name) {
		this.buyer_name = buyer_name;
	}

	public String getBuyer_mobile() {
		return buyer_mobile;
	}

	public void setBuyer_mobile(String buyer_mobile) {
		this.buyer_mobile = buyer_mobile;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getSeller_name() {
		return seller_name;
	}

	public void setSeller_name(String seller_name) {
		this.seller_name = seller_name;
	}

	public String getSeller_mobile() {
		return seller_mobile;
	}

	public void setSeller_mobile(String seller_mobile) {
		this.seller_mobile = seller_mobile;
	}

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public Integer getOrder_status() {
		return order_status;
	}

	public void setOrder_status(Integer order_status) {
		this.order_status = order_status;
	}

	public Integer getOrigin_fee() {
		return origin_fee;
	}

	public void setOrigin_fee(Integer origin_fee) {
		this.origin_fee = origin_fee;
	}

	public Integer getActure_fee() {
		return acture_fee;
	}

	public void setActure_fee(Integer acture_fee) {
		this.acture_fee = acture_fee;
	}

	public Integer getCoupon_price() {
		return coupon_price;
	}

	public void setCoupon_price(Integer coupon_price) {
		this.coupon_price = coupon_price;
	}

	public java.util.Date getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(java.util.Date expire_time) {
		this.expire_time = expire_time;
	}

	public java.util.Date getPayment_timeout() {
		return payment_timeout;
	}

	public void setPayment_timeout(java.util.Date payment_timeout) {
		this.payment_timeout = payment_timeout;
	}

	public java.util.Date getPayment_time() {
		return payment_time;
	}

	public void setPayment_time(java.util.Date payment_time) {
		this.payment_time = payment_time;
	}

	public Integer getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Integer is_deleted) {
		this.is_deleted = is_deleted;
	}

	public java.util.Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(java.util.Date created_time) {
		this.created_time = created_time;
	}

	public java.util.Date getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(java.util.Date updated_time) {
		this.updated_time = updated_time;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	
}
