package com.xczhihui.medical.anchor.vo;



import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2018-01-19
 */
public class CourseApplyInfoVO implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 课程申请信息表
     */
	private Integer id;
    /**
     * 标题
     */
	private String title;
    /**
     * 副标题
     */
	private String subtitle;
    /**
     * 上传人
     */
	private String userId;
    /**
     * 封面
     */
	private String imgPath;
    /**
     * 主播
     */
	private String lecturer;
    /**
     * 主播介绍
     */
	private String lecturerDescription;
    /**
     * 课程类型：1.直播 2.点播 3.线下课
     */
	private Integer courseForm;
    /**
     * 课程分类
     */
	private String courseMenu;
    /**
     * 课程开始时间
     */
	private Date startTime;
    /**
     * 课程时长
     */
	private String courseLength;
    /**
     * 课程单价
     */
	private Double price;
    /**
     * 课程密码
     */
	private String password;
    /**
     * 课程简介
     */
	private String courseDescription;
    /**
     * 课程简介
     */
	private String courseDetail;
    /**
     * 课程大纲
     */
	private String courseOutline;
    /**
     * 课程资源
     */
	private String courseResource;
    /**
     * 合辑包含课程数
     */
	private Integer courseNumber;
    /**
     * 合辑中课程的排序字段
     */
	private Integer collectionCourseSort;
    /**
     * 多媒体类型:1视频2音频
     */
	private Integer multimediaType;
    /**
     * 是否为专辑
     */
	private Boolean collection;
    /**
     * 是否上架
     */
	private Boolean sale;
    /**
     * 审核状态 0未审核 1 审核通过 2 审核未通过
     */
	private Integer status;
    /**
     * 驳回原因
     */
	private Integer dismissal;
    /**
     * 驳回备注
     */
	private String dismissalRemark;
    /**
     * 审核时间
     */
	private Date reviewTime;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 更新时间
     */
	private Date updateTime;
	private Date lastUpdateTime;
    /**
     * 授课地址
     */
	private String address;
    /**
     * 结课时间
     */
	private Date endTime;

	private Integer recommend;

	private Integer applyStatus;

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
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

	public Integer getMultimediaType() {
		return multimediaType;
	}

	public void setMultimediaType(Integer multimediaType) {
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "CourseApplyInfo{" +
			", id=" + id +
			", title=" + title +
			", subtitle=" + subtitle +
			", userId=" + userId +
			", imgPath=" + imgPath +
			", lecturer=" + lecturer +
			", lecturerDescription=" + lecturerDescription +
			", courseForm=" + courseForm +
			", courseMenu=" + courseMenu +
			", startTime=" + startTime +
			", courseLength=" + courseLength +
			", price=" + price +
			", password=" + password +
			", courseDescription=" + courseDescription +
			", courseDetail=" + courseDetail +
			", courseOutline=" + courseOutline +
			", courseResource=" + courseResource +
			", courseNumber=" + courseNumber +
			", collectionCourseSort=" + collectionCourseSort +
			", multimediaType=" + multimediaType +
			", collection=" + collection +
			", sale=" + sale +
			", status=" + status +
			", dismissal=" + dismissal +
			", dismissalRemark=" + dismissalRemark +
			", reviewTime=" + reviewTime +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", address=" + address +
			", endTime=" + endTime +
			"}";
	}
}
