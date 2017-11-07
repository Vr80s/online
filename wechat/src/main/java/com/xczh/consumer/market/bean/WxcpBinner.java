package com.xczh.consumer.market.bean;

import java.io.Serializable;

public class WxcpBinner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String url;
	private Integer click_sum;
	private String img_path;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getClick_sum() {
		return click_sum;
	}
	public void setClick_sum(Integer click_sum) {
		this.click_sum = click_sum;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	
	
	
	
}
