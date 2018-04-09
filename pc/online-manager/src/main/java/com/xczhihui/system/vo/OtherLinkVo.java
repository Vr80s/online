package com.xczhihui.system.vo;

import java.util.Date;

/**
 * 友情链接Vo
 * 
 * @author liuchenG
 * @date 2016-06-03
 *
 */
public class OtherLinkVo {

	/**
	 * id
	 */
	private String id;

	/**
	 * 名称
	 */
	private String orgname;

	/**
	 * 链接
	 */
	private String url;

	/**
	 * 是否删除
	 */
	private boolean idDelete;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人
	 */
	private String createPerson;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 是否禁用
	 */
	private boolean status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isIdDelete() {
		return idDelete;
	}

	public void setIdDelete(boolean idDelete) {
		this.idDelete = idDelete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
