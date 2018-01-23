package com.xczhihui.bxg.online.common.domain;

import com.xczhihui.bxg.common.support.domain.BasicEntity2;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *  课程实体类
 * @author Rongcai Kang
 */
@Entity
@Table(name = "oe_course")
public class Course extends BasicEntity2 implements Serializable {

	private static final long serialVersionUID = 8080612633895345818L;
	/**
	 *讲师	ID
	 */
	@Column(name = "lecturer_Id")
	private Integer lecturerId;
	
	/**
	 * yangxuan 新增用户讲师id
	 */
	@Column(name = "user_lecturer_id")
	private String userLecturerId;
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
	private String  courseLength;


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
	
	/**
	 * 是否推荐
	 */
	@Column(name = "is_recommend")
	private Integer isRecommend;
	
	/**
	 * 推荐排序
	 */
	@Column(name = "recommend_sort")
	private Integer recommendSort;
	
	/**
	 * qq号
	 */
	@Column(name = "qqno")
	private String qqno;
	
	/**
	 * 不展示(0)，展示（1）
	 */
	@Column(name = "description_show")
	private Integer descriptionShow;

	/**
	 * 推荐展示图
	 */
	@Column(name = "rec_img_path")
	private String recImgPath;
	/**
	 * 直播开始时间
	 */
	@Column(name = "start_time")
	private Date startTime;
	/**
	 * 直播结束时间
	 */
	@Column(name = "end_time")
	private Date endTime;
	/**
	 * 助教ID
	 */
	@Column(name = "assistant_id")
	private Integer assistantId;
	/**
	 * 直播布局:1:视频 2:文档3:视频+文档
	 */
	@Column(name = "direct_seeding")
	private Integer directSeeding;
	/**
	 * 直播间ID
	 */
	@Column(name = "direct_id")
	private String directId;
	/**
	 * 外部链接
	 */
	@Column(name = "external_links")
	private String externalLinks;
	/**
	 * 老师鲜花数
	 */
	@Column(name = "flowers_number")
	private Integer flowersNumber;
	/**
	 * 浏览数
	 */
	@Column(name = "pv")
	private Integer pv;
	/**
	 * 最高在线人数
	 */
	@Column(name = "highest_number_line")
	private Integer highestNumberLine;
	/**
	 * 结束时在线人数
	 */
	@Column(name = "end_line_number")
	private Integer endLineNumber;
	/**
	 * 课程分类 1:公开直播课
	 */
	@Column(name = "type")
	private Integer type;

	/**
	 * 课程的业务分类 （0：职业课，1：微课）
	 */
	@Column(name = "course_type")
	private Integer serviceType;
	@Column(name = "course_number")
	private Integer courseNumber;

	/**
	 * 班级额定人数
	 */
	@Column(name = "grade_student_sum")
	private Integer classRatedNum;

	@Column(name = "teacher_img_path")
	private String teacherImgPath;

	/**
	 * 班级QQ群
	 */
	@Column(name = "grade_qq" )
	private  String gradeQQ;

	/**
	 * 默认报名人数
	 */
	@Column(name = "default_student_count")
	private  Integer  defaultStudentCount;

	@Column(name = "is_sent" )
	private  boolean isSent;
	
	@Column(name = "version" )
	private  String version;

	@Column(name = "address" )
	private  String address;
	
	@Column(name = "live_status" )
	private  Integer liveStatus;
	
	@Column(name = "multimedia_type" )
	private  int multimediaType;
	
	
	@Column(name = "live_source" )
	private  int liveSource;  //直播来源  1、后台新增  2、app申请
	
	
	@Column(name = "apply_id")
	private Integer applyId;

	@Column(name = "examine_id")
	private String examineId;
	
	
	/**
	 * 上架或者下架时间
	 */
	@Column(name = "release_time")
	private Date releaseTime;
	
	
	@Column(name = "city")
	private String city;
	@Transient
	private String playCode;
	@Transient
	private String courseMenu;

	public String getCourseMenu() {
		return courseMenu;
	}

	public void setCourseMenu(String courseMenu) {
		this.courseMenu = courseMenu;
	}

	public String getPlayCode() {
		return playCode;
	}

	public void setPlayCode(String playCode) {
		this.playCode = playCode;
	}

	public int getMultimediaType() {
		return multimediaType;
	}

	public void setMultimediaType(int multimediaType) {
		this.multimediaType = multimediaType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 课程密码
	 */
	//TODO
	@Column(name = "course_pwd" )
	private  String coursePwd;

	@Column(name = "online_course" )
	private int onlineCourse;

	@Column(name = "collection" )
	private Boolean collection;
	@Column(name = "lecturer" )
	private String lecturer;
	@Type(type="text")
	@Column(name = "lecturer_description" )
	private String lecturerDescription;
	private String subtitle;
	@Column(name = "collection_course_sort")
	private Integer collectionCourseSort;
	
	/**
	 * 直播源类型  0:来自pc直播,1:来自app 直播
	 */
	@Column(name = "live_source_type")
	private boolean  liveSourceType;
	/**
	 * 直播源类型  0:来自pc直播,1:来自app 直播
	 */
	@Column(name = "essence_sort")
	private Integer  essenceSort;


	@Transient
	private List<Course> courseInfoList;

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

	public List<Course> getCourseInfoList() {
		return courseInfoList;
	}

	public void setCourseInfoList(List<Course> courseInfoList) {
		this.courseInfoList = courseInfoList;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
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

	public Boolean getCollection() {
		return collection;
	}

	public void setCollection(Boolean collection) {
		this.collection = collection;
	}

	public int getOnlineCourse() {
		return onlineCourse;
	}

	public void setOnlineCourse(int onlineCourse) {
		this.onlineCourse = onlineCourse;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

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

	public String getCourseLength() {
		return courseLength;
	}

	public void setCourseLength(String courseLength) {
		this.courseLength = courseLength;
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

	public Double getOriginalCost() {
		return originalCost;
	}

	public void setOriginalCost(Double originalCost) {
		this.originalCost = originalCost;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
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

	public String getTeacherImgPath() {
		return teacherImgPath;
	}

	public void setTeacherImgPath(String teacherImgPath) {
		this.teacherImgPath = teacherImgPath;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getClassRatedNum() {
		return classRatedNum == null ? 0 : classRatedNum;
	}

	public void setClassRatedNum(Integer classRatedNum) {
		this.classRatedNum = classRatedNum == null ? 0 : classRatedNum;
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

	public String getCoursePwd() {
		return coursePwd;
	}

	public void setCoursePwd(String coursePwd) {
		this.coursePwd = coursePwd;
	}

	public boolean isSent() {
		return isSent;
	}

	public void setSent(boolean isSent) {
		this.isSent = isSent;
	}

	public String getUserLecturerId() {
		return userLecturerId;
	}

	public void setUserLecturerId(String userLecturerId) {
		this.userLecturerId = userLecturerId;
	}

	public Integer getLiveStatus() {
		return liveStatus;
	}

	public void setLiveStatus(Integer liveStatus) {
		this.liveStatus = liveStatus;
	}

	public int getLiveSource() {
		return liveSource;
	}

	public void setLiveSource(int liveSource) {
		this.liveSource = liveSource;
	}

	public Integer getApplyId() {
		return applyId;
	}

	public void setApplyId(Integer examineId) {
		this.applyId = examineId;
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

	public Integer getEssenceSort() {
		return essenceSort;
	}

	public void setEssenceSort(Integer essenceSort) {
		this.essenceSort = essenceSort;
	}

	public String getExamineId() {
		return examineId;
	}

	public void setExamineId(String examineId) {
		this.examineId = examineId;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	
	
}
