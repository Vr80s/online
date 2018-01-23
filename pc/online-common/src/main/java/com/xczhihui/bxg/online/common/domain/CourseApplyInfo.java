package com.xczhihui.bxg.online.common.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the course_apply_info database table.
 * 
 */
@Entity
@Table(name="course_apply_info")
public class CourseApplyInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String title;
	private String subtitle;
	@Column(name="user_id")
	private String userId;
	@Column(name="img_path")
	private String imgPath;
	private String lecturer;
	@Type(type="text")
	@Column(name="lecturer_description")
	private String lecturerDescription;
	@Column(name="course_form")
	private Integer courseForm;
	@Column(name="course_menu")
	private String courseMenu;
	@Column(name="course_length")
	private String courseLength;
	private Double price;
	private String password;
	@Type(type="text")
	@Column(name="course_description")
	private String courseDescription;
	@Type(type="text")
	@Column(name="course_detail")
	private String courseDetail;
	@Type(type="text")
	@Column(name="course_outline")
	private String courseOutline;
	@Column(name="course_resource")
	private String courseResource;
	@Column(name="course_number")
	private Integer courseNumber;
	@Column(name="collection_course_sort")
	private Integer collectionCourseSort;
	@Column(name="multimedia_type")
	private int multimediaType;
	private Boolean collection;
	private Boolean sale;
	private Integer status;
	private Integer dismissal;
	@Column(name="dismissal_remark")
	private String dismissalRemark;
	@Column(name="review_person")
	private String reviewPerson;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_time")
	private Date startTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="review_time")
	private Date reviewTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_time")
	private Date endTime;

	@Column(name="is_delete")
	private Boolean isDelete;

	@Transient
	private List<CourseApplyInfo> courseApplyInfoList;
	@Transient
	private String playCode;
	@Transient
	private String userName;
	@Transient
	private String dismissalText;
	private String address;
	private String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDismissalText() {
		return dismissalText;
	}

	public void setDismissalText(String dismissalText) {
		this.dismissalText = dismissalText;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPlayCode() {
		return playCode;
	}

	public void setPlayCode(String playCode) {
		this.playCode = playCode;
	}

	public List<CourseApplyInfo> getCourseApplyInfoList() {
		return courseApplyInfoList;
	}

	public void setCourseApplyInfoList(List<CourseApplyInfo> courseApplyInfoList) {
		this.courseApplyInfoList = courseApplyInfoList;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

	public String getLecturerDescription() {
		return lecturerDescription;
	}

	public void setLecturerDescription(String lecturerDescription) {
		this.lecturerDescription = lecturerDescription;
	}

	public Integer getCourseForm() {
		return courseForm;
	}

	public void setCourseForm(Integer courseForm) {
		this.courseForm = courseForm;
	}

	public String getCourseMenu() {
		return courseMenu;
	}

	public void setCourseMenu(String courseMenu) {
		this.courseMenu = courseMenu;
	}

	public String getCourseLength() {
		return courseLength;
	}

	public void setCourseLength(String courseLength) {
		this.courseLength = courseLength;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public String getCourseDetail() {
		return courseDetail;
	}

	public void setCourseDetail(String courseDetail) {
		this.courseDetail = courseDetail;
	}

	public String getCourseOutline() {
		return courseOutline;
	}

	public void setCourseOutline(String courseOutline) {
		this.courseOutline = courseOutline;
	}

	public String getCourseResource() {
		return courseResource;
	}

	public void setCourseResource(String courseResource) {
		this.courseResource = courseResource;
	}

	public Integer getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(Integer courseNumber) {
		this.courseNumber = courseNumber;
	}

	public Integer getCollectionCourseSort() {
		return collectionCourseSort;
	}

	public void setCollectionCourseSort(Integer collectionCourseSort) {
		this.collectionCourseSort = collectionCourseSort;
	}

	public int getMultimediaType() {
		return multimediaType;
	}

	public void setMultimediaType(int multimediaType) {
		this.multimediaType = multimediaType;
	}

	public Boolean getCollection() {
		return collection;
	}

	public void setCollection(Boolean collection) {
		this.collection = collection;
	}

	public Boolean getSale() {
		return sale;
	}

	public void setSale(Boolean sale) {
		this.sale = sale;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDismissal() {
		return dismissal;
	}

	public void setDismissal(Integer dismissal) {
		this.dismissal = dismissal;
	}

	public String getDismissalRemark() {
		return dismissalRemark;
	}

	public void setDismissalRemark(String dismissalRemark) {
		this.dismissalRemark = dismissalRemark;
	}

	public String getReviewPerson() {
		return reviewPerson;
	}

	public void setReviewPerson(String reviewPerson) {
		this.reviewPerson = reviewPerson;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getDelete() {
		return isDelete;
	}

	public void setDelete(Boolean delete) {
		isDelete = delete;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean delete) {
		isDelete = delete;
	}
}