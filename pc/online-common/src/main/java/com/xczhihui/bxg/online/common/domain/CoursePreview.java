package com.xczhihui.bxg.online.common.domain;

import com.xczhihui.bxg.common.support.domain.BasicEntity2;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 课程预览
 * @author Haicheng Jiang
 *
 */
@Entity
@Table(name = "oe_course_preview")
public class CoursePreview extends BasicEntity2 {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7418633445879646581L;
	/**
	 *讲师	ID
	 */
	@Column(name = "lecturer_Id")
	private Integer lecturerId;
	/**
	 *课程名
	 */
	@Column(name = "grade_name")
	private String gradeName;
	/**
	 * 直播时间
	 */
	@Column(name = "live_time")
	private Date liveTime;
	/**
	 * 课程小图
	 */
	@Column(name = "smallimg_path")
	private String smallImgPath;
	/**
	 * 课程详情图
	 */
	@Column(name = "detailimg_path")
	private String detailImgPath;
	/**
	 * 课程大图
	 */
	@Column(name = "bigimg_path")
	private String bigImgPath;
	/**
	 * 课程描述
	 */
	@Column(name = "description")
	private String description;
	/**
	 * 课程结业时间
	 */
	@Column(name = "graduate_time")
	private Date graduateTime;
	/**
	 * 云课堂链接
	 */
	@Column(name = "cloud_classroom")
	private String cloudClassroom;
	/**
	 * 课程排序
	 */
	@Column(name = "sort")
	private Integer sort;


	/**
	 * 授课类型
	 */
	@Column(name = "courseType")
	private String courseType;

	
	/**
	 * 是否禁用启用
	 */
	@Column(name = "status")
	private String  status;
	
	/**
	 * 已学人数
	 */
	@Column(name = "learnd_count")
	private Integer  learndCount;

	/**
	 * 原价
	 */
	@Column(name = "original_cost")
	private Double  originalCost;

	/**
	 * 课程时长
	 */
	@Column(name = "course_length")
	private Double  courseLength;


	/**
	 * 当前价格
	 */
	@Column(name = "current_price")
	private Double  currentPrice;
	
	/**
	 * 学科id
	 */
	@Column(name = "menu_id")
	private Integer  menuId;
	
	/**
	 * 课程类别id
	 */
	@Column(name = "course_type_id")
	private String  courseTypeId;

	/**
	 * 课程是否免费:true 免费  false不免费
	 */
	@Column(name = "is_free")
	private boolean  isFree;
	
	/**
	 * 课程名称模板
	 */
	@Column(name = "class_template")
	private String  classTemplate;


	/**
	 * 课程详情
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type="text")
	@Column(name = "course_detail")
	private String courseDetail;

	/**
	 * 课程大纲
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type="text")
	@Column(name = "course_outline")
	private String courseOutline;

	/**
	 * 常见问题
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type="text")
	@Column(name = "common_problem")
	private String commonProblem;


	public String getCommonProblem() {
		return commonProblem;
	}

	public void setCommonProblem(String commonProblem) {
		this.commonProblem = commonProblem;
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

	public Integer getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(Integer lecturerId) {
		this.lecturerId = lecturerId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Date getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(Date liveTime) {
		this.liveTime = liveTime;
	}

	public String getSmallImgPath() {
		return smallImgPath;
	}

	public void setSmallImgPath(String smallImgPath) {
		this.smallImgPath = smallImgPath;
	}

	public String getDetailImgPath() {
		return detailImgPath;
	}

	public void setDetailImgPath(String detailImgPath) {
		this.detailImgPath = detailImgPath;
	}

	public String getBigImgPath() {
		return bigImgPath;
	}

	public void setBigImgPath(String bigImgPath) {
		this.bigImgPath = bigImgPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getGraduateTime() {
		return graduateTime;
	}

	public void setGraduateTime(Date graduateTime) {
		this.graduateTime = graduateTime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCloudClassroom() {
		return cloudClassroom;
	}

	public void setCloudClassroom(String cloudClassroom) {
		this.cloudClassroom = cloudClassroom;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getLearndCount() {
		return learndCount;
	}

	public void setLearndCount(Integer learndCount) {
		this.learndCount = learndCount;
	}

	public Double getOriginalCost() {
		return originalCost;
	}

	public void setOriginalCost(Double originalCost) {
		this.originalCost = originalCost;
	}

	public Double getCourseLength() {
		return courseLength;
	}

	public void setCourseLength(Double courseLength) {
		this.courseLength = courseLength;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getCourseTypeId() {
		return courseTypeId;
	}

	public void setCourseTypeId(String courseTypeId) {
		this.courseTypeId = courseTypeId;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setIsFree(boolean isFree) {
		this.isFree = isFree;
	}

	public String getClassTemplate() {
		return classTemplate;
	}

	public void setClassTemplate(String classTemplate) {
		this.classTemplate = classTemplate;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}
	
}
