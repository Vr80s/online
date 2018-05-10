package com.xczhihui.bxg.online.common.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.xczhihui.common.support.domain.BasicEntity;

/**
 * 资源和权限
 * 
 * @author Haicheng Jiang
 *
 */
@Entity
@Table(name = "resource", uniqueConstraints = { @UniqueConstraint(columnNames = { "permission", "type" }) })
public class Resource extends BasicEntity {

	private String name;

	/**
	 * 父资源ID
	 */
	@Column(name = "parent_id")
	private String parentId;

	/**
	 * 资源对应的url，一般菜单资源有url属性。
	 */
	private String url;

	/**
	 * 显示顺序
	 */
	@Column(name = "display_order")
	private int displayOrder;

	/**
	 * 图标，一般是菜单资源使用
	 */
	private String icon;

	/**
	 * 权限字符串resourceCode:action，如：question:list,exam:delete等
	 */
	private String permission;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 资源类型，如：menu,button等
	 */
	private String type;

	/**
	 * 资源类型，如：菜单，按钮等
	 */
	@Transient
	private String typeDesc;

	@Transient
	private List<Resource> children;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public List<Resource> getChildren() {
		return children;
	}

	public void setChildren(List<Resource> children) {
		this.children = children;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
