package com.xczhihui.bxg.online.web.vo;

import com.xczhihui.bxg.online.common.domain.MenuTag;

import java.util.List;

public class MenuTagVo {
	private String id;
	private String menu;
	private List<MenuTag> sencodMenu;
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public List<MenuTag> getSencodMenu() {
		return sencodMenu;
	}
	public void setSencodMenu(List<MenuTag> sencodMenu) {
		this.sencodMenu = sencodMenu;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
