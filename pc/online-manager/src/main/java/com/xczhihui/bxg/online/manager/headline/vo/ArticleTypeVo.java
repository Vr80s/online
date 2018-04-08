package com.xczhihui.bxg.online.manager.headline.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ArticleTypeVo extends OnlineBaseVo{

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private String id;
	/**
	 * 文章类型名
	 */
	private String name;
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date create_time;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 排序字段
	 */
	private Integer sort;
	/**
	 * 排序类型
	 */
	private Integer sortType;
	/**
	 * 文章数
	 */
	private Integer articleNum;

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

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

	public Integer getArticleNum() {
		return articleNum;
	}

	public void setArticleNum(Integer articleNum) {
		this.articleNum = articleNum;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
