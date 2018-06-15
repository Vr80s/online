package com.xczhihui.bxg.online.web.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 动态资讯
 */
public class InfomationVo implements Serializable {
	private static final long serialVersionUID = 5176489895562140454L;
	private Integer id;
	private String name;
	private String informationType;
	private String create_person;
	private Date create_time;
	private boolean is_delete;
	private String href_adress;
	private Integer sort;
	private Integer status;
	private Date start_time;
	private Date end_time;
	private Integer click_count;
	private boolean is_hot;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInformationType() {
		return informationType;
	}
	public void setInformationType(String informationType) {
		this.informationType = informationType;
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
	public boolean isIs_delete() {
		return is_delete;
	}
	public void setIs_delete(boolean is_delete) {
		this.is_delete = is_delete;
	}
	public String getHref_adress() {
		return href_adress;
	}
	public void setHref_adress(String href_adress) {
		this.href_adress = href_adress;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public Integer getClick_count() {
		return click_count;
	}
	public void setClick_count(Integer click_count) {
		this.click_count = click_count;
	}
	public boolean isIs_hot() {
		return is_hot;
	}
	public void setIs_hot(boolean is_hot) {
		this.is_hot = is_hot;
	}

}
