package com.xczhihui.mobile.vo;

import com.xczhihui.bxg.common.support.domain.BasicEntity2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 移动端搜索实体类
 * @author wangyishuai
 */
@Entity
@Table(name = "oe_mobile_search")
public class MobileSearchVo extends BasicEntity2 implements Serializable {

	private static final long serialVersionUID = -1874726130897637379L;
	/**
	 * 名称
	 */
	@Column(name = "name")
    private String  name;

	/**
	 * 状态，0禁用，1启用
	 */
	@Column(name = "status")
	private Integer status;

	/**
	 * 1 搜索框 2 热门搜索
	 */
	@Column(name = "search_type")
	private Integer searchType;

	/**
	 * 排序
	 */
	@Column(name = "seq")
	private Integer seq;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
}
