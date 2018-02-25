package com.xczhihui.bxg.online.api.po;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;


/** 
 * ClassName: CourseAnchorDivide.java <br>
 * Description:主播分成表 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月11日<br>
 */
@Entity
@Table(name = "course_anchor")
public class CourseAnchor implements java.io.Serializable {

	// Fields

	private Integer id;
	private String userId;

	private String video;

	private String detail;
	private Integer type;

	private String version;
	private Date createTime;
	private Date updateTime;
	private Boolean status;
	private Boolean deleted;
	private String remark;
	private String createPerson;
	private String updatePerson;

	private BigDecimal vodDivide;

	private BigDecimal liveDivide;

	private BigDecimal offlineDivide;

	private BigDecimal giftDivide;

	private String name;

	private String profilePhoto;
	private String loginName;

	private Integer recommendSort;

	private Integer isRecommend;

	@Column(name="is_recommend")
	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	@Column(name="recommend_sort")
	public Integer getRecommendSort() {
		return recommendSort;
	}

	public void setRecommendSort(Integer recommendSort) {
		this.recommendSort = recommendSort;
	}

	@Transient
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name="profile_photo")
	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	@Column(name="vod_divide")
	public BigDecimal getVodDivide() {
		return vodDivide;
	}

	public void setVodDivide(BigDecimal vodDivide) {
		this.vodDivide = vodDivide;
	}

	@Column(name="live_divide")
	public BigDecimal getLiveDivide() {
		return liveDivide;
	}

	public void setLiveDivide(BigDecimal liveDivide) {
		this.liveDivide = liveDivide;
	}

	@Column(name="offline_divide")
	public BigDecimal getOfflineDivide() {
		return offlineDivide;
	}

	public void setOfflineDivide(BigDecimal offlineDivide) {
		this.offlineDivide = offlineDivide;
	}

	@Column(name="gift_divide")
	public BigDecimal getGiftDivide() {
		return giftDivide;
	}

	public void setGiftDivide(BigDecimal giftDivide) {
		this.giftDivide = giftDivide;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false, length = 32)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



	@Column(name = "version", length = 32)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "status", nullable = false)
	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Column(name = "deleted", nullable = false)
	public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Column(name = "remark", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Column(name = "create_person")
	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	@Column(name = "update_person")
	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	@Type(type="text")
	@Column(name = "detail")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}