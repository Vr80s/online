package com.xczhihui.course.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 
 * ClassName: Course.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年1月14日<br>
 */
@TableName("oe_course")
public class Course extends Model<Course> {

    private static final long serialVersionUID = 1L;

	private Integer id;
    /**
	 *讲师	ID
	 */
	@TableField("lecturer_Id")
	private Integer lecturerId;
	
	/**
	 * yangxuan 新增用户讲师id
	 */
	@TableField("user_lecturer_id")
	private String userLecturerId;
	/**
	 *课程名
	 */
	@TableField("grade_name")
	private String gradeName;
	/**
	 * 直播时间
	 */
	@TableField("live_time")
	private Date liveTime;
	/**
	 * 课程小图
	 */
	@TableField("smallimg_path")
	private String smallImgPath;
	/**
	 * 课程详情图
	 */
	@TableField("detailimg_path")
	private String detailImgPath;
	/**
	 * 课程大图
	 */
	@TableField("bigimg_path")
	private String bigImgPath;
	/**
	 * 课程描述
	 */
	@TableField("description")
	private String description;
	/**
	 * 课程结业时间
	 */
	@TableField("graduate_time")
	private Date graduateTime;
	/**
	 * 云课堂链接
	 */
	@TableField("cloud_classroom")
	private String cloudClassroom;
	/**
	 * 课程排序
	 */
	@TableField("sort")
	private Integer sort;


	/**
	 * 授课类型
	 */
	@TableField("courseType")
	private String courseType;

	
	/**
	 * 是否禁用启用
	 */
	@TableField("status")
	private String  status;
	
	/**
	 * 已学人数
	 */
	@TableField("learnd_count")
	private Integer  learndCount;

	/**
	 * 原价
	 */
	@TableField("original_cost")
	private Double  originalCost;

	/**
	 * 课程时长
	 */
	@TableField("course_length")
	private String  courseLength;


	/**
	 * 当前价格
	 */
	@TableField("current_price")
	private Double  currentPrice;
	
	/**
	 * 学科id
	 */
	@TableField("menu_id")
	private Integer  menuId;
	
	/**
	 * 课程类别id
	 */
	@TableField("course_type_id")
	private String  courseTypeId;

	/**
	 * 课程是否免费:true 免费  false不免费
	 */
	@TableField("is_free")
	private boolean  isFree;
	
	/**
	 * 课程名称模板
	 */
	@TableField("class_template")
	private String  classTemplate;

	/**
	 * 课程详情
	 */
	private String courseDetail;

	/**
	 * 课程大纲
	 */
	private String courseOutline;

	/**
	 * 常见问题
	 */
	@TableField("common_problem")
	private String commonProblem;
	
	/**
	 * 是否推荐
	 */
	@TableField("is_recommend")
	private Integer isRecommend;
	
	/**
	 * 推荐排序
	 */
	@TableField("recommend_sort")
	private Integer recommendSort;
	
	/**
	 * qq号
	 */
	@TableField("qqno")
	private String qqno;
	
	/**
	 * 不展示(0)，展示（1）
	 */
	@TableField("description_show")
	private Integer descriptionShow;

	/**
	 * 推荐展示图
	 */
	@TableField("rec_img_path")
	private String recImgPath;
	/**
	 * 直播开始时间
	 */
	@TableField("start_time")
	private Date startTime;
	/**
	 * 直播结束时间
	 */
	@TableField("end_time")
	private Date endTime;
	/**
	 * 助教ID
	 */
	@TableField("assistant_id")
	private Integer assistantId;
	/**
	 * 直播布局:1:视频 2:文档3:视频+文档
	 */
	@TableField("direct_seeding")
	private Integer directSeeding;
	/**
	 * 直播间ID
	 */
	@TableField("direct_id")
	private String directId;
	/**
	 * 外部链接
	 */
	@TableField("external_links")
	private String externalLinks;
	/**
	 * 老师鲜花数
	 */
	@TableField("flowers_number")
	private Integer flowersNumber;
	/**
	 * 浏览数
	 */
	@TableField("pv")
	private Integer pv;
	/**
	 * 最高在线人数
	 */
	@TableField("highest_number_line")
	private Integer highestNumberLine;
	/**
	 * 结束时在线人数
	 */
	@TableField("end_line_number")
	private Integer endLineNumber;
	/**
	 * 课程分类 1:公开直播课
	 */
	@TableField("type")
	private Integer type;

	/**
	 * 课程的业务分类 （0：职业课，1：微课）
	 */
	@TableField("course_type")
	private Integer serviceType;

	/**
	 * 班级额定人数
	 */
	@TableField("grade_student_sum")
	private Integer classRatedNum;

	@TableField("teacher_img_path")
	private String teacherImgPath;

	/**
	 * 班级QQ群
	 */
	@TableField("grade_qq" )
	private  String gradeQQ;

	/**
	 * 默认报名人数
	 */
	@TableField("default_student_count")
	private  Integer  defaultStudentCount;

	@TableField("is_sent" )
	private  boolean isSent;
	
	@TableField("version" )
	private  String version;

	@TableField("address" )
	private  String address;
	
	@TableField("live_status" )
	private  Integer liveStatus;
	
	@TableField("multimedia_type" )
	private  int multimediaType;
	
	
	@TableField("live_source" )
	private  int liveSource;  //直播来源  1、后台新增  2、app申请
	
	
	@TableField("apply_id")
	private String examineId;
	
	
	@TableField("city")
	private String city;
	
	@TableField("live_source_type")
	private  boolean liveSourceType;

	@TableField("is_delete")
	private Boolean isDelete;
    
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Boolean getDelete() {
		return isDelete;
	}

	public void setDelete(Boolean delete) {
		isDelete = delete;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getLecturerId() {
		return lecturerId;
	}


	public void setLecturerId(Integer lecturerId) {
		this.lecturerId = lecturerId;
	}


	public String getUserLecturerId() {
		return userLecturerId;
	}


	public void setUserLecturerId(String userLecturerId) {
		this.userLecturerId = userLecturerId;
	}


	public String getGradeName() {
		return gradeName;
	}


	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
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


	public String getCloudClassroom() {
		return cloudClassroom;
	}


	public void setCloudClassroom(String cloudClassroom) {
		this.cloudClassroom = cloudClassroom;
	}


	public Integer getSort() {
		return sort;
	}


	public void setSort(Integer sort) {
		this.sort = sort;
	}


	public String getCourseType() {
		return courseType;
	}


	public void setCourseType(String courseType) {
		this.courseType = courseType;
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


	public String getCourseLength() {
		return courseLength;
	}


	public void setCourseLength(String courseLength) {
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


	public boolean isFree() {
		return isFree;
	}


	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}


	public String getClassTemplate() {
		return classTemplate;
	}


	public void setClassTemplate(String classTemplate) {
		this.classTemplate = classTemplate;
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


	public String getCommonProblem() {
		return commonProblem;
	}


	public void setCommonProblem(String commonProblem) {
		this.commonProblem = commonProblem;
	}


	public Integer getIsRecommend() {
		return isRecommend;
	}


	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}


	public Integer getRecommendSort() {
		return recommendSort;
	}


	public void setRecommendSort(Integer recommendSort) {
		this.recommendSort = recommendSort;
	}


	public String getQqno() {
		return qqno;
	}


	public void setQqno(String qqno) {
		this.qqno = qqno;
	}


	public Integer getDescriptionShow() {
		return descriptionShow;
	}


	public void setDescriptionShow(Integer descriptionShow) {
		this.descriptionShow = descriptionShow;
	}


	public String getRecImgPath() {
		return recImgPath;
	}


	public void setRecImgPath(String recImgPath) {
		this.recImgPath = recImgPath;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public Integer getAssistantId() {
		return assistantId;
	}


	public void setAssistantId(Integer assistantId) {
		this.assistantId = assistantId;
	}


	public Integer getDirectSeeding() {
		return directSeeding;
	}


	public void setDirectSeeding(Integer directSeeding) {
		this.directSeeding = directSeeding;
	}


	public String getDirectId() {
		return directId;
	}


	public void setDirectId(String directId) {
		this.directId = directId;
	}


	public String getExternalLinks() {
		return externalLinks;
	}


	public void setExternalLinks(String externalLinks) {
		this.externalLinks = externalLinks;
	}


	public Integer getFlowersNumber() {
		return flowersNumber;
	}


	public void setFlowersNumber(Integer flowersNumber) {
		this.flowersNumber = flowersNumber;
	}


	public Integer getPv() {
		return pv;
	}


	public void setPv(Integer pv) {
		this.pv = pv;
	}


	public Integer getHighestNumberLine() {
		return highestNumberLine;
	}


	public void setHighestNumberLine(Integer highestNumberLine) {
		this.highestNumberLine = highestNumberLine;
	}


	public Integer getEndLineNumber() {
		return endLineNumber;
	}


	public void setEndLineNumber(Integer endLineNumber) {
		this.endLineNumber = endLineNumber;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Integer getServiceType() {
		return serviceType;
	}


	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}


	public Integer getClassRatedNum() {
		return classRatedNum;
	}


	public void setClassRatedNum(Integer classRatedNum) {
		this.classRatedNum = classRatedNum;
	}


	public String getTeacherImgPath() {
		return teacherImgPath;
	}


	public void setTeacherImgPath(String teacherImgPath) {
		this.teacherImgPath = teacherImgPath;
	}


	public String getGradeQQ() {
		return gradeQQ;
	}


	public void setGradeQQ(String gradeQQ) {
		this.gradeQQ = gradeQQ;
	}


	public Integer getDefaultStudentCount() {
		return defaultStudentCount;
	}


	public void setDefaultStudentCount(Integer defaultStudentCount) {
		this.defaultStudentCount = defaultStudentCount;
	}


	public boolean isSent() {
		return isSent;
	}


	public void setSent(boolean isSent) {
		this.isSent = isSent;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Integer getLiveStatus() {
		return liveStatus;
	}


	public void setLiveStatus(Integer liveStatus) {
		this.liveStatus = liveStatus;
	}


	public int getMultimediaType() {
		return multimediaType;
	}


	public void setMultimediaType(int multimediaType) {
		this.multimediaType = multimediaType;
	}


	public int getLiveSource() {
		return liveSource;
	}


	public void setLiveSource(int liveSource) {
		this.liveSource = liveSource;
	}


	public String getExamineId() {
		return examineId;
	}


	public void setExamineId(String examineId) {
		this.examineId = examineId;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public boolean isLiveSourceType() {
		return liveSourceType;
	}


	public void setLiveSourceType(boolean liveSourceType) {
		this.liveSourceType = liveSourceType;
	}

		

}
