package com.xczh.consumer.market.bean;

import java.io.Serializable;

/**
 * 
 * 分类推荐
 * @author zhangshixiong
 **/
@SuppressWarnings("serial")
public class WxcpCategoryRecommend implements Serializable {
	/****/
	private String id;
	/**商品分类ID**/
	private String category_id;
	/**图片地址**/
	private String img_url;
	/**推荐时间**/
	private String tj_dt;
	/**推荐类型：0->首页，其他以后再说；**/
	private int tj_type;
	/**排序，数越小越靠前**/
	private int sort;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getTj_dt() {
		return tj_dt;
	}
	public void setTj_dt(String tj_dt) {
		this.tj_dt = tj_dt;
	}
	public int getTj_type() {
		return tj_type;
	}
	public void setTj_type(int tj_type) {
		this.tj_type = tj_type;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
}
