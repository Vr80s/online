package com.xczh.consumer.market.bean;
import java.io.Serializable;


/**
 * 
 * 订单关联的商品表
 * @author yanghui
 **/
@SuppressWarnings("serial")
public class WxcpOrderGoods implements Serializable {

	/**主键**/
	 private String o2g_id;

	/**订单ID**/
	 private String order_id;

	/**买家ID，关联client_user**/
	 private String buyer_id;

	/**卖家ID,关联shop_user**/
	 private String seller_id;

	/**未优惠的总价格（分）**/
	 private Integer origin_fee;

	/**优惠后的价格（分）**/
	 private Integer acture_fee;

	/**商品ID**/
	 private String goods_id;

	/****/
	 private java.util.Date created_time;

	/****/
	 private java.util.Date updated_time;

	/**商品数量**/
	 private Integer goods_num;

	public String getO2g_id() {
		return o2g_id;
	}

	public void setO2g_id(String o2g_id) {
		this.o2g_id = o2g_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
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

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
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

	public Integer getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(Integer goods_num) {
		this.goods_num = goods_num;
	}
	 
}
