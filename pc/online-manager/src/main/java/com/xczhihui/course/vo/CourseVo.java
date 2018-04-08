package com.xczhihui.course.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by admin on 2016/7/27.
 */
public class CourseVo extends OnlineBaseVo{


    /**
     *课程ID
     */
    private int id;
    
    /**
     *课程ID
     */
    private int showCourseId;
    
    /**
     *移动端课程ID
     */
    private int courseId;

    public void setId(Integer id) {
		this.id = id;
	}
	/**
     *课程名称
     */
    private String  courseName;

    /**
     * 课程展示图（宣传页）
     * 课程详情图
     */
    private String detailImgPath;
    /**
     * 课程宣传图（宣传页）
     * 课程大图
     */
    private String bigImgPath;
    /**
     * 课程展示图（首页）
     */
    private String smallingPath;
    /**
     * 课程展示图（仪晓东--新增-- 2016年11月26日 11:20:43）
     */
    private String smallimgPath;
    /**
     * 课程描述
     */
    private String description;
    /**
     * 云课堂链接
     */
    private String cloudClassroom;
    /**
     * qq 号
     */
    private String qqno;

    /**
     * 直播时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date liveTime;
    /**
     * 直播时间排序
     */
    private String liveTimeSort;
    /**
     * 讲师id
     */
    private Integer lecturerId;
    /**
     * 讲师
     */
    private String lecturerName;
    
    /**
	 * yangxuan 新增用户讲师id
	 */
    private String userLecturerId;
    
    /**
     * 结课时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date graduateTime;
    /**
     * 所属分类
     */
    private String menuName;
    
    /**
     * 二级分类
     */
    private String menuNameSecond;
    
    
    /**
	 * 课程类型 1：直播课 2:公开课 3:基础课4:点播课
	 */
	private String courseType;
    
    /**
     * 是否禁用
     */
    private String status;
    
    /**
	 * 已学人数
	 */
	private Integer  learndCount;
	
	/**
	 * 学科id
	 */
	private Integer  menuId;
	
	/**
	 * 课程类别id
	 */
	private String  courseTypeId;
	
	/**
	 * 课程名称模板
	 */
	private String  classTemplate;
	
	/**
	 * 课程简介
	 */
	private String  courseDescribe;
	
	private Boolean  isFree;
	
	//课程时长
	private String  courseLength;
	
	//原价格
	private Double  originalCost;
	
	//现价格
	private Double  currentPrice;
	
	//班级数
	private Integer countGradeNum;
	
	/**
	 * 以下添加的三个字段为在列表显示和查看的时候使用
	 */
	
	//学科名称
	private String xMenuName;
	
	//课程类别名称
	private String scoreTypeName;
	
	//授课方式
	private String teachMethodName;
	
	/**
	 * 是否推荐
	 */
	private Integer isRecommend;
	
	/**
	 * 推荐排序
	 */
	private Integer recommendSort;
	
	/**
	 * 不展示(0)，展示（1）
	 */
	private Integer descriptionShow;

	/**
	 * 推荐展示图
	 */
	private String recImgPath;
	/**
	 * 直播开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startTime;
	/**
	 * 直播结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;
	/**
	 * 助教ID
	 */
	private Integer assistantId;
	/**
	 * 直播方式:1:本站 2:外部
	 */
	private Integer directSeeding;
	/**
	 * 直播间ID
	 */
	private String directId;
	/**
	 * 外部链接
	 */
	private String externalLinks;
	/**
	 * 老师鲜花数
	 */
	private Integer flowersNumber;
	/**
	 * 浏览数
	 */
	private Integer pv;
	/**
	 * 最高在线人数
	 */
	private Integer highestNumberLine;
	/**
	 * 结束时在线人数
	 */
	private Integer endLineNumber;
	/**
	 * 课程分类 1:公开直播课
	 */
	private Integer type;
	/**
	 * 课程的业务分类 （0：职业课，1：微课）
	 */
	private Integer serviceType;

    /**
	 * actCount实际报名人数
	 */
	private Integer actCount;
	
	private String teacherImgPath;

	//排序类型 在查询评价管理的时候使用
	private Integer sortType;
	
	private Integer criticizeNum;
	
	private Integer goodCriticizeNum;
	
	private Integer notesNum;
	
	private Integer barrierNum;
	
	private Integer gradeStudentSum;
	
	private Integer teachingDays;
	
	@JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
	private Date createTime;
	
	private Integer barrierStatus;

	/**
	 * 班级额定人数
	 */
	private Integer classRatedNum;
	/**
	 * 班级QQ群
	 */
	private  String gradeQQ;

	/**
	 * 默认报名人数
	 */

	private  Integer  defaultStudentCount;

	 /**
     * 讲师
     */
    private String role_type1;

    /**
     * 班主任
     */
    private String role_type2;

    /**
     *助教
     */
    private String role_type3;
    
    private Integer paperNum;
    
    private  String coursePwd;
    
    private Integer multimediaType;
    
    private int onlineCourse;

    private  String address;
    
    private int liveSource;
    
    private String realProvince;
    
    private String realCitys;
    
    private String realCounty;
    
    private Integer liveStatus;

    private Boolean collection;
    private Boolean singleSale;
    private String lecturer;
    private String lecturerDescription;
	private String subtitle;
	
	// 精品推荐
	private Integer isEssence;

	// 精品排序
	private Integer essenceSort; //不推荐(0)，推荐（1）
	
	// 分类推荐
	private Integer isTypeRecommend;
	
	// 分类排序
	private Integer typeSort; 
	//发布时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date releaseTime;

    //排序更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sortUpdateTime;
	

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}



	public Integer getEssenceSort() {
		return essenceSort;
	}

	public void setEssenceSort(Integer essenceSort) {
		this.essenceSort = essenceSort;
	}

	public String getLecturerDescription() {
		return lecturerDescription;
	}

	public void setLecturerDescription(String lecturerDescription) {
		this.lecturerDescription = lecturerDescription;
	}

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

	public Boolean getCollection() {
		return collection;
	}

	public void setCollection(Boolean collection) {
		this.collection = collection;
	}

	public Boolean getSingleSale() {
		return singleSale;
	}

	public void setSingleSale(Boolean singleSale) {
		this.singleSale = singleSale;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getOnlineCourse() {
		return onlineCourse;
	}

	public void setOnlineCourse(int onlineCourse) {
		this.onlineCourse = onlineCourse;
	}
	/*
     * 用于判断，是否存在公开详情了。如果存在公开详情呢，说明在app端、微信端可以展示了
     */
    private Integer isCourseDetails; //TODO 0 表示不展示 1 表示展示

	
    public Integer getMultimediaType() {
		return multimediaType;
	}

	public void setMultimediaType(Integer multimediaType) {
		this.multimediaType = multimediaType;
	}

	public Integer getBarrierStatus() {
		return barrierStatus;
	}

	public void setBarrierStatus(Integer barrierStatus) {
		this.barrierStatus = barrierStatus;
	}

	public String getxMenuName() {
		return xMenuName;
	}

	public void setxMenuName(String xMenuName) {
		this.xMenuName = xMenuName;
	}

	public String getScoreTypeName() {
		return scoreTypeName;
	}

	public void setScoreTypeName(String scoreTypeName) {
		this.scoreTypeName = scoreTypeName;
	}

	public String getTeachMethodName() {
		return teachMethodName;
	}

	public void setTeachMethodName(String teachMethodName) {
		this.teachMethodName = teachMethodName;
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

	public String getCourseLength() {
		return courseLength;
	}

	public void setCourseLength(String courseLength) {
		this.courseLength = courseLength;
	}


	public Boolean getIsFree() {
		return isFree;
	}

	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCloudClassroom() {
        return cloudClassroom;
    }

    public void setCloudClassroom(String cloudClassroom) {
        this.cloudClassroom = cloudClassroom;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getQqno() {
        return qqno;
    }

    public void setQqno(String qqno) {
        this.qqno = qqno;
    }


	public String getLecturerName() {
		return lecturerName;
	}

	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}


	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(Date liveTime) {
		this.liveTime = liveTime;
	}

	public Date getGraduateTime() {
		return graduateTime;
	}

	public void setGraduateTime(Date graduateTime) {
		this.graduateTime = graduateTime;
	}

	public String getMenuNameSecond() {
		return menuNameSecond;
	}

	public void setMenuNameSecond(String menuNameSecond) {
		this.menuNameSecond = menuNameSecond;
	}

	public String getSmallingPath() {
		return smallingPath;
	}

	public void setSmallingPath(String smallingPath) {
		this.smallingPath = smallingPath;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public Integer getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(Integer lecturerId) {
		this.lecturerId = lecturerId;
	}

	public String getLiveTimeSort() {
		return liveTimeSort;
	}

	public void setLiveTimeSort(String liveTimeSort) {
		this.liveTimeSort = liveTimeSort;
	}

	public Integer getLearndCount() {
		return learndCount;
	}

	public void setLearndCount(Integer learndCount) {
		this.learndCount = learndCount;
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

	public String getClassTemplate() {
		return classTemplate;
	}

	public void setClassTemplate(String classTemplate) {
		this.classTemplate = classTemplate;
	}

	public String getCourseDescribe() {
		return courseDescribe;
	}

	public void setCourseDescribe(String courseDescribe) {
		this.courseDescribe = courseDescribe;
	}

	public Integer getCountGradeNum() {
		return countGradeNum;
	}

	public void setCountGradeNum(Integer countGradeNum) {
		this.countGradeNum = countGradeNum;
	}

	@Override
    public Date getCreateTime() {
		return createTime;
	}

	@Override
    public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public int getShowCourseId() {
		return showCourseId;
	}

	public void setShowCourseId(int showCourseId) {
		this.showCourseId = showCourseId;
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

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public String getSmallimgPath() {
		return smallimgPath;
	}

	public void setSmallimgPath(String smallimgPath) {
		this.smallimgPath = smallimgPath;
	}

	public Integer getActCount() {
		return actCount;
	}

	public void setActCount(Integer actCount) {
		this.actCount = actCount;
	}

	public String getTeacherImgPath() {
		return teacherImgPath;
	}

	public void setTeacherImgPath(String teacherImgPath) {
		this.teacherImgPath = teacherImgPath;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

	public Integer getCriticizeNum() {
		return criticizeNum;
	}

	public void setCriticizeNum(Integer criticizeNum) {
		this.criticizeNum = criticizeNum;
	}

	public Integer getGoodCriticizeNum() {
		return goodCriticizeNum;
	}

	public void setGoodCriticizeNum(Integer goodCriticizeNum) {
		this.goodCriticizeNum = goodCriticizeNum;
	}

	public Integer getNotesNum() {
		return notesNum;
	}

	public void setNotesNum(Integer notesNum) {
		this.notesNum = notesNum;
	}

	public Integer getBarrierNum() {
		return barrierNum;
	}

	public void setBarrierNum(Integer barrierNum) {
		this.barrierNum = barrierNum;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getClassRatedNum() {
		return classRatedNum = classRatedNum == null ? 0 : classRatedNum;
	}

	public void setClassRatedNum(Integer classRatedNum) {
		this.classRatedNum = classRatedNum == null ? 0 : classRatedNum;
	}

	public Integer getGradeStudentSum() {
		return gradeStudentSum;
	}

	public void setGradeStudentSum(Integer gradeStudentSum) {
		this.gradeStudentSum = gradeStudentSum;
	}

	public Integer getTeachingDays() {
		return teachingDays;
	}

	public void setTeachingDays(Integer teachingDays) {
		this.teachingDays = teachingDays;
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

	public String getRole_type1() {
		return role_type1;
	}

	public void setRole_type1(String role_type1) {
		this.role_type1 = role_type1;
	}

	public String getRole_type2() {
		return role_type2;
	}

	public void setRole_type2(String role_type2) {
		this.role_type2 = role_type2;
	}

	public String getRole_type3() {
		return role_type3;
	}

	public void setRole_type3(String role_type3) {
		this.role_type3 = role_type3;
	}

	public Integer getPaperNum() {
		return paperNum;
	}

	public void setPaperNum(Integer paperNum) {
		this.paperNum = paperNum;
	}

	public String getCoursePwd() {
		return coursePwd;
	}

	public void setCoursePwd(String coursePwd) {
		this.coursePwd = coursePwd;
	}

	public String getUserLecturerId() {
		return userLecturerId;
	}

	public void setUserLecturerId(String userLecturerId) {
		this.userLecturerId = userLecturerId;
	}

	public Integer getIsCourseDetails() {
		return isCourseDetails;
	}

	public void setIsCourseDetails(Integer isCourseDetails) {
		this.isCourseDetails = isCourseDetails;
	}

	public int getLiveSource() {
		return liveSource;
	}

	public void setLiveSource(int liveSource) {
		this.liveSource = liveSource;
	}


	public String getRealProvince() {
		return realProvince;
	}

	public void setRealProvince(String realProvince) {
		this.realProvince = realProvince;
	}

	public String getRealCitys() {
		return realCitys;
	}

	public void setRealCitys(String realCitys) {
		this.realCitys = realCitys;
	}

	public String getRealCounty() {
		return realCounty;
	}

	public void setRealCounty(String realCounty) {
		this.realCounty = realCounty;
	}

	public Integer getLiveStatus() {
		return liveStatus;
	}

	public void setLiveStatus(Integer liveStatus) {
		this.liveStatus = liveStatus;
	}

	public Integer getIsEssence() {
		return isEssence;
	}

	public void setIsEssence(Integer isEssence) {
		this.isEssence = isEssence;
	}

	public Integer getIsTypeRecommend() {
		return isTypeRecommend;
	}

	public void setIsTypeRecommend(Integer isTypeRecommend) {
		this.isTypeRecommend = isTypeRecommend;
	}

	public Integer getTypeSort() {
		return typeSort;
	}

	public void setTypeSort(Integer typeSort) {
		this.typeSort = typeSort;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

    public Date getSortUpdateTime() {
        return sortUpdateTime;
    }

    public void setSortUpdateTime(Date sortUpdateTime) {
        this.sortUpdateTime = sortUpdateTime;
    }
}

