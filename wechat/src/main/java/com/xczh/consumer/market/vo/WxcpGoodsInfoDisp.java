package com.xczh.consumer.market.vo;

public class WxcpGoodsInfoDisp {
	
	/**
	 * 商品ID(主键)
	 */
	private String goods_id;
	
	/**
	 * 商品标题
	 */
	private String title;
	
	/**
	 * 商品图片列表，多图用英语的逗号分割
	 */
	private String image_URL;
	
	/**
	 * 商品单价(原始价)
	 */
	private int origin_unit_money;

	/**商品数量**/
	private Integer goods_num;
	
	/**是否评论**/
	boolean is_credit = false;//false没有评论；true已评论；
	
	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Integer getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(Integer goods_num) {
		this.goods_num = goods_num;
	}

	public boolean isIs_credit() {
		return is_credit;
	}

	public void setIs_credit(boolean is_credit) {
		this.is_credit = is_credit;
	}
		
}
