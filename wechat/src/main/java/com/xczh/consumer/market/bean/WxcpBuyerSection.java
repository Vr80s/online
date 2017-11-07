package com.xczh.consumer.market.bean;
import java.io.Serializable;


/**
 * 
 * 买家信息表
 * @author yanghui
 **/
@SuppressWarnings("serial")
public class WxcpBuyerSection implements Serializable {

	/**关键字**/
	 private String buyer_section_id;

	/**关联订单order_info的ID**/
	 private String order_info_id;

	/**登录账户ID**/
	 private String clientauth_id;

	/**登录用户名称**/
	 private String clientauth_name;

	/**收货人姓名**/
	 private String receiver_name;

	/**关联地址表id**/
	 private String localtion_id;

	/**买家电话**/
	 private String contact_phone;

	/**买家电话类型**/
	 private String contact_type;

	/**买家具体地址信息（北京、朝阳、望京、酒仙桥路甲12号）**/
	 private String location_info;

	/****/
	 private java.util.Date created_time;

	/****/
	 private java.util.Date updated_time;

	public String getBuyer_section_id() {
		return buyer_section_id;
	}

	public void setBuyer_section_id(String buyer_section_id) {
		this.buyer_section_id = buyer_section_id;
	}

	public String getOrder_info_id() {
		return order_info_id;
	}

	public void setOrder_info_id(String order_info_id) {
		this.order_info_id = order_info_id;
	}

	public String getClientauth_id() {
		return clientauth_id;
	}

	public void setClientauth_id(String clientauth_id) {
		this.clientauth_id = clientauth_id;
	}

	public String getClientauth_name() {
		return clientauth_name;
	}

	public void setClientauth_name(String clientauth_name) {
		this.clientauth_name = clientauth_name;
	}

	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public String getLocaltion_id() {
		return localtion_id;
	}

	public void setLocaltion_id(String localtion_id) {
		this.localtion_id = localtion_id;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getContact_type() {
		return contact_type;
	}

	public void setContact_type(String contact_type) {
		this.contact_type = contact_type;
	}

	public String getLocation_info() {
		return location_info;
	}

	public void setLocation_info(String location_info) {
		this.location_info = location_info;
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

}
