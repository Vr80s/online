package com.xczh.consumer.market.bean;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 店铺表基本信息表
 * @author yanghui
 **/
@SuppressWarnings("serial")
public class WxcpShopInfo implements Serializable {

	/**店铺id**/
	 private String shop_id;

	/**店铺名称**/
	 private String shop_name;

	/**店铺类型，支持的类型：B2C，O2O，区分大小写**/
	 private String shop_type;

	/**描述**/
	 private String description;

	/**店铺LOGO图片ID**/
	 private String logo_image_id;

	/**是否认证  1:已认证   0:未认证**/
	 private String is_promoted;

	/**店铺信用**/
	 private java.math.BigDecimal shop_credit;

	/**店铺位置，关联common_location 表。**/
	 private String location_id;

	/**位置详细描述**/
	 private String location_detail;

	/**是否启用**/
	 private Integer is_enable;

	/**营业时间_开始时间**/
	 private Date shop_hours1;

	/**营业时间_结束时间**/
	 private Date shop_hours2;

	/**创建人**/
	 private String creater;

	/**创建时间**/
	 private Date create_date;

	/**店铺联系方式**/
	 private String shop_contacts;

	/**邮政编码**/
	 private String post_id;

	/**店铺法人信息**/
	 private String law_info;

	/**客服电话**/
	 private String customer_telephone;

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

	public String getShop_type() {
		return shop_type;
	}

	public void setShop_type(String shop_type) {
		this.shop_type = shop_type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogo_image_id() {
		return logo_image_id;
	}

	public void setLogo_image_id(String logo_image_id) {
		this.logo_image_id = logo_image_id;
	}

	public String getIs_promoted() {
		return is_promoted;
	}

	public void setIs_promoted(String is_promoted) {
		this.is_promoted = is_promoted;
	}

	public java.math.BigDecimal getShop_credit() {
		return shop_credit;
	}

	public void setShop_credit(java.math.BigDecimal shop_credit) {
		this.shop_credit = shop_credit;
	}

	public String getLocation_id() {
		return location_id;
	}

	public void setLocation_id(String location_id) {
		this.location_id = location_id;
	}

	public String getLocation_detail() {
		return location_detail;
	}

	public void setLocation_detail(String location_detail) {
		this.location_detail = location_detail;
	}

	public Integer getIs_enable() {
		return is_enable;
	}

	public void setIs_enable(Integer is_enable) {
		this.is_enable = is_enable;
	}

	public Date getShop_hours1() {
		return shop_hours1;
	}

	public void setShop_hours1(Date shop_hours1) {
		this.shop_hours1 = shop_hours1;
	}

	public Date getShop_hours2() {
		return shop_hours2;
	}

	public void setShop_hours2(Date shop_hours2) {
		this.shop_hours2 = shop_hours2;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getShop_contacts() {
		return shop_contacts;
	}

	public void setShop_contacts(String shop_contacts) {
		this.shop_contacts = shop_contacts;
	}

	public String getPost_id() {
		return post_id;
	}

	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}

	public String getLaw_info() {
		return law_info;
	}

	public void setLaw_info(String law_info) {
		this.law_info = law_info;
	}

	public String getCustomer_telephone() {
		return customer_telephone;
	}

	public void setCustomer_telephone(String customer_telephone) {
		this.customer_telephone = customer_telephone;
	}

}
