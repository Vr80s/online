package com.xczh.consumer.market.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *     课程类别 如 大数据 UI等
 * @author yjy
 *
 */
public class WxcpOeMenu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String create_person;
	private Date create_time;
	private Boolean is_delete;
	private String level;
	private String name;
	private Integer number;
	private Integer sort;
	private Integer type;
	private Integer status;
	private String remark;
	private Integer yun_status;
	private Integer bo_status;
	private Integer yun_sort;
	private Integer bo_sort;
	private Integer ask_limit;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreate_person() {
		return create_person;
	}
	public void setCreate_person(String create_person) {
		this.create_person = create_person;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Boolean getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(Boolean is_delete) {
		this.is_delete = is_delete;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getYun_status() {
		return yun_status;
	}
	public void setYun_status(Integer yun_status) {
		this.yun_status = yun_status;
	}
	public Integer getBo_status() {
		return bo_status;
	}
	public void setBo_status(Integer bo_status) {
		this.bo_status = bo_status;
	}
	public Integer getYun_sort() {
		return yun_sort;
	}
	public void setYun_sort(Integer yun_sort) {
		this.yun_sort = yun_sort;
	}
	public Integer getBo_sort() {
		return bo_sort;
	}
	public void setBo_sort(Integer bo_sort) {
		this.bo_sort = bo_sort;
	}
	public Integer getAsk_limit() {
		return ask_limit;
	}
	public void setAsk_limit(Integer ask_limit) {
		this.ask_limit = ask_limit;
	}
	
}
