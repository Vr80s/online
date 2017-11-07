package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.bxg.common.support.domain.BasicEntity;
import com.xczhihui.bxg.common.support.domain.BasicEntity2;

/**
 *  标签实体类
 * @author yxd
 */
@Entity
@Table(name = "oe_ask_tag")
public class AskTag extends BasicEntity implements Serializable {

	private static final long serialVersionUID = -4621626305569948170L;
	
	/**
	 * 学科id
	 */
	@Column(name = "menu_id")
	private Integer  menuId;
	
	/**
	 * 标签名
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 排序
	 */
	@Column(name = "seq")
	private Integer seq;
	
	/**
	 * 排序
	 */
	@Column(name = "status")
	private Integer status;

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
