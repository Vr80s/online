package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.bxg.common.support.domain.BasicEntity2;

/**
 * 教师实体类
 * @author Rongcai Kang
 */
@Entity
@Table(name = "oe_lecturer")
public class Lecturer extends BasicEntity2 implements Serializable {

	private static final long serialVersionUID = -8649999925497079512L;
	/**
	 * 讲师名
	 */
	@Column(name = "name")
	private String name;
	/**
	 * 老师昵称
	 */
	@Column(name = "nickname")
	private String nickname;
	/**
	 *讲师介绍信息
	 */
	@Column(name = "description")
	private String description;

	/**
	 * 讲师排序
	 */
	@Column(name = "sort")
	private Integer sort;

	/**
	 * 头像
	 */
	@Column(name = "head_img")
	private String headImg;
	
	/**
	 * 角色
	 */
	@Column(name = "role_type")
	private String roleType;
	
	/**
	 * 所属学科
	 */
	@Column(name = "menu_id")
	private Integer menuId;

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	
	}
