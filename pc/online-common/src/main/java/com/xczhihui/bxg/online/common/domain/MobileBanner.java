package com.xczhihui.bxg.online.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * ClassName: Course.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年1月14日<br>
 */
@Entity
@Table(name = "oe_course_mobile_banner")
public class MobileBanner  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
	private String id;

	/**
	 *名称
	 */
	@Column(name = "name")
	private String name;

	/**
	 *图片地址
	 */
	@Column(name = "url")
	private String url;

	/**
	 *点击量
	 */
	@Column(name = "click_sum")
	private Integer clickSum;

	/**
	 *创建人id
	 */
	@Column(name = "create_person")
	private String createPerson;

	/**
	 *创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;

	/**
	 *状态，0禁用，1启用
	 */
	@Column(name = "status")
	private Integer status;

	/**
	 *排序
	 */
	@Column(name = "seq")
	private Integer seq;

	/**
	 *图片链接地址
	 */
	@Column(name = "img_path")
	private String imgPath;

	/**
	 *连接类型：1：活动页、2：专题页、3：课程:4：主播:5：课程列表（带筛选条件）
	 */
	@Column(name = "link_type")
	private Integer linkType;

	/**
	 *连接条件
	 */
	@Column(name = "link_condition")
	private String linkCondition;

	/**
	 *  1 推荐 2 线下课程 3 直播 4 听课
	 */
	@Column(name = "banner_type")
	private Integer bannerType;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getClickSum() {
		return clickSum;
	}

	public void setClickSum(Integer clickSum) {
		this.clickSum = clickSum;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public String getLinkCondition() {
		return linkCondition;
	}

	public void setLinkCondition(String linkCondition) {
		this.linkCondition = linkCondition;
	}

	public Integer getBannerType() {
		return bannerType;
	}

	public void setBannerType(Integer bannerType) {
		this.bannerType = bannerType;
	}
}
