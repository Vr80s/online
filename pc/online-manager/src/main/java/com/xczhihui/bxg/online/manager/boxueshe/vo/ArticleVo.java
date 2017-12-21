package com.xczhihui.bxg.online.manager.boxueshe.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class ArticleVo  extends OnlineBaseVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 文章内容
	 */
	private String content;
	/**
	 * 文章类型,外键引用
	 */
	private String typeId;
	/**
	 * 分列名称
	 */
	private String typeName;
	/**
	 * 图片
	 */
	private String imgPath;
	/** 
	 * banner图片
	 */
	private String bannerPath;
	/** 
	 * 标签ID
	 */
	private String tagId;
	
	/** 
	 * 标签名称
	 */
	private String tagName;
	/**
	 * 阅读量
	 */
	private Integer browseSum;
	/**
	 * 点赞数
	 */
	private Integer praiseSum;
	/**
	 * 评论数
	 */
	private Integer commentSum;
	/**
	 * 是否推荐，1推荐，0不推荐
	 */
	private Boolean isRecommend;
	/**
	 * banner图禁用状态，1启用，0禁用
	 */
	private Integer bannerStatus;
	/**
	 * 文章禁用状态，1启用，0禁用
	 */
	private Integer status;
	/**
	 * 用户id 关联user
	 */
	private String userId;
	/**
	 * 作者
	 */
	private String author;
	private String doctorAuthor;
	private String reportDoctor;
	/**
	 * sort
	 */
	private Integer sort;
	private Date startTime;
	private Date stopTime;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date createTime;

	public String getReportDoctor() {
		return reportDoctor;
	}

	public void setReportDoctor(String reportDoctor) {
		this.reportDoctor = reportDoctor;
	}

	public Boolean getRecommend() {
		return isRecommend;
	}

	public void setRecommend(Boolean recommend) {
		isRecommend = recommend;
	}

	public String getDoctorAuthor() {
		return doctorAuthor;
	}

	public void setDoctorAuthor(String doctorAuthor) {
		this.doctorAuthor = doctorAuthor;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getBannerPath() {
		return bannerPath;
	}
	public void setBannerPath(String bannerPath) {
		this.bannerPath = bannerPath;
	}
	public Integer getBrowseSum() {
		return browseSum;
	}
	public void setBrowseSum(Integer browseSum) {
		this.browseSum = browseSum;
	}
	public Integer getPraiseSum() {
		return praiseSum;
	}
	public void setPraiseSum(Integer praiseSum) {
		this.praiseSum = praiseSum;
	}
	public Integer getCommentSum() {
		return commentSum;
	}
	public void setCommentSum(Integer commentSum) {
		this.commentSum = commentSum;
	}
	public Boolean getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}
	public Integer getBannerStatus() {
		return bannerStatus;
	}
	public void setBannerStatus(Integer bannerStatus) {
		this.bannerStatus = bannerStatus;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
	
	
}
