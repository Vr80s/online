package com.xczhihui.headline.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

/**
 * 博学社评价管理实体类
 * 
 * @author
 */
public class AppraiseVo extends OnlineBaseVo {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private String id;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 评价内容
	 */
	private String content;
	/**
	 * 相关文章ID
	 */
	private String articleId;
	/**
	 * 作者/用户昵称
	 */
	private String name;
	/**
	 * 评价时间
	 */
	@DateTimeFormat(pattern = "yyyy-M-d HH:mm:ss")
	@JsonFormat(pattern = "yyyy-M-d HH:mm", timezone = "GMT+8")
	private Date createTime;
	/*
	 * 创建开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-M-d HH:mm:ss")
	@JsonFormat(pattern = "yyyy-M-d HH:mm", timezone = "GMT+8")
	private Date startTime;

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/*
	 * 创建结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-M-d HH:mm:ss")
	@JsonFormat(pattern = "yyyy-M-d HH:mm", timezone = "GMT+8")
	private Date stopTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
