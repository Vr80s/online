package com.xczh.consumer.market.vo;

import java.util.Map;

public class PartnerVo {

	private String id;
	private String name;
	private String mobile;
	private String create_time;
	private Map<String, Object> countMap;
	 
	 
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Map<String, Object> getCountMap() {
		return countMap;
	}
	public void setCountMap(Map<String, Object> countMap) {
		this.countMap = countMap;
	}
	 
	 
}
