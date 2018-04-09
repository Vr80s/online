package com.xczhihui.operate.vo;

import java.util.ArrayList;
import java.util.List;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class TypeVo extends OnlineBaseVo {
	private static final long serialVersionUID = 447025951456409795L;
	private String id;
	private String value;
	private String name;
	private String parentId;
	private int displayOrder;
	List<TypeVo> vos = new ArrayList<TypeVo>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public List<TypeVo> getVos() {
		return vos;
	}

	public void setVos(List<TypeVo> vos) {
		this.vos = vos;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
