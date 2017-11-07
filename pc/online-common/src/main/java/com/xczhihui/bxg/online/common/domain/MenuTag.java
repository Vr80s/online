package com.xczhihui.bxg.online.common.domain;

import com.xczhihui.bxg.common.support.domain.BasicEntity;
import com.xczhihui.bxg.common.support.domain.BasicEntity2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 菜单实体类
 * @author Rongcai Kang
 */
@Entity
@Table(name = "menutag")
public class MenuTag extends BasicEntity implements Serializable {

	private static final long serialVersionUID = 6249862612548452415L;

	/**
	 * 节点名称
	 */
	@Column(name = "menu", length = 20)
	private String menu;

	/**
	 * 父节点ID
	 */
	@Column(name = "pid", length = 32)
	private String pid;

	/**
	 * 状态
	 */
	@Column(name = "status")
	private String status;

	/**
	 * 菜单排序
	 */
	@Column(name = "sort")
	private Integer sort;

	/**
	 * 备注
	 */
	@Column(name = "mdesc")
	private String mdesc;

	public String getMdesc() {
		return mdesc;
	}

	public void setMdesc(String mdesc) {
		this.mdesc = mdesc;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
