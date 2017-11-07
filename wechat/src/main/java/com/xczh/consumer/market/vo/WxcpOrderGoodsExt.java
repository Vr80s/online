package com.xczh.consumer.market.vo;

import java.util.Date;

public class WxcpOrderGoodsExt {
	
	/**
	 * 商品ID(主键)
	 */
	private String goods_id;
	/**
	 * 店铺ID
	 */
	private String shop_id;
	/**
	 * 商品类目ID
	 */
	private String category_id;
	/**
	 * 商品标题
	 */
	private String title;
	/**
	 * 商品副标题
	 */
	private String notice_title;
	/**
	 * 0:下架 1:上架
	 */
	private int is_onsale;
	/**
	 * 审核状态  0:待审核 1:审核通过 2:审核不通过 
	 */
	private int is_violation;
	/**
	 * 是否包邮   包邮则物流模板无效
	 */
	private int is_delivery_payed;
	/**
	 * 是否可用,0->不可用，1->可用
	 */
	private int is_enable;
	/**
	 * 创建人信息
	 */
	private String create_user_id;
	/**
	 * 创建时间
	 */
	private Date create_datetime;
	/**
	 * 0000-00-00 00:00:00' COMMENT '修改时间
	 */
	private Date update_datetime;
	/**
	 * 商品图片列表，多图用英语的逗号分割
	 */
	private String image_URL;
	/**
	 * 商品单价(原始价)
	 */
	private int origin_unit_money;
	/**
	 * 商品单价(优惠价)（暂不用）
	 */
	private int coupon_unit_money;
	/**
	 * 商品内容
	 */
	private String goods_content;
	
	public String getGoods_content() {
		return goods_content;
	}
	public void setGoods_content(String goods_content) {
		this.goods_content = goods_content;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getShop_id() {
		return shop_id;
	}
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNotice_title() {
		return notice_title;
	}
	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}
	public int getIs_onsale() {
		return is_onsale;
	}
	public void setIs_onsale(int is_onsale) {
		this.is_onsale = is_onsale;
	}
	public int getIs_violation() {
		return is_violation;
	}
	public void setIs_violation(int is_violation) {
		this.is_violation = is_violation;
	}
	public int getIs_delivery_payed() {
		return is_delivery_payed;
	}
	public void setIs_delivery_payed(int is_delivery_payed) {
		this.is_delivery_payed = is_delivery_payed;
	}
	public int getIs_enable() {
		return is_enable;
	}
	public void setIs_enable(int is_enable) {
		this.is_enable = is_enable;
	}
	public String getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(String create_user_id) {
		this.create_user_id = create_user_id;
	}
	public Date getCreate_datetime() {
		return create_datetime;
	}
	public void setCreate_datetime(Date create_datetime) {
		this.create_datetime = create_datetime;
	}
	public Date getUpdate_datetime() {
		return update_datetime;
	}
	public void setUpdate_datetime(Date update_datetime) {
		this.update_datetime = update_datetime;
	}
	public String getImage_URL() {
		return image_URL;
	}
	public void setImage_URL(String image_URL) {
		this.image_URL = image_URL;
	}
	public int getOrigin_unit_money() {
		return origin_unit_money;
	}
	public void setOrigin_unit_money(int origin_unit_money) {
		this.origin_unit_money = origin_unit_money;
	}
	public int getCoupon_unit_money() {
		return coupon_unit_money;
	}
	public void setCoupon_unit_money(int coupon_unit_money) {
		this.coupon_unit_money = coupon_unit_money;
	}

	/**订单ID**/
	 private String order_id;
	 
	/**商品数量**/
	 private Integer goods_num;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public Integer getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(Integer goods_num) {
		this.goods_num = goods_num;
	}
	
	
}
