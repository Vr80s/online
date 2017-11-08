package com.xczhihui.bxg.online.common.utils.cc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryBean implements Serializable {
	private static final long serialVersionUID = 8959352472808204750L;
	private String id;
	private String name;
	private List<CategoryBean> subs = new ArrayList<CategoryBean>();
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
	public List<CategoryBean> getSubs() {
		return subs;
	}
	public void setSubs(List<CategoryBean> subs) {
		this.subs = subs;
	}
}
