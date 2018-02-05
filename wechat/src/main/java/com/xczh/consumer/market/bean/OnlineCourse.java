package com.xczh.consumer.market.bean;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 课程信息
 * @author zhangshixiong
 */
public class OnlineCourse {
	private Integer id;
	
	private Integer courseId;
	
    private String userId;
    /**
     *报名状态
     */
    private boolean isFree;
    /**
     *推荐状态 false:不推荐  true:推荐
     */
    private  boolean isRecommend;
    /**
     *课程名称
     */
    private String  courseName;

    /**
     * 课程详情图
     */
    private String detailImgPath;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 课程当前价格
     */
    private String currentPrice;

    /**
     * 课程原价
     */
    private String originalCost;

    /**
     * 课程时长
     */
    private Double courseLength;

    /**
     * 已经学习人数
     */
    private String learndCount;

    /**
     * 主讲老师
     */
    private String teacherName;

    /**
     * 课程类别
     */
    private String scoreName;

    /**
     * 课程大图
     */
    private String bigImgPath;

    /**
     * 云课堂链接
     */
    private String cloudClassroom;

    /**
     * 小图
     */
    private String smallImgPath;

    /**
     * 主讲老师
     */
    private String teacherNames;

    /**
     * 课程大纲
     */
    private String courseOutline;;


    /**
     * 常见问题
     */
    private String commonProblem;

    /**
     * 课程详情内容
     */
    private String courseDetail;

    /**
     * 学科id号
     */
    private Integer  menu_id;

    /**
     * 学科名称
     */
    private String  name;

    /**
     * 课程下视频总数
     */
    private Integer count;

    /**
     * 课程下已学习视频总数
     */
    private Integer learndVideo;

    /**
     * 课程下未开始学习视频总数
     */
    private  Integer unStudy;

    /**
     * 是否报名 true:已报名  false:未报名
     */
    private Boolean isApply=false;

    /**
     * 课程咨询qq号
     * 
     */
    private String  qqno;


    /**
     * 优惠价格
     */
    private  String  preferentyMoney;

    /**
     * 当前下单时间
     */
    private Timestamp create_time;

    /**
     * 课程跳转是否展示课程介绍页 0:不展示  1:展示
     */
    private Integer description_show;

    /**
     * 0:职业课  1:微课
     */
    private Integer course_type;
    
    /**
     * 成为分享大使课的id
     */
    private String shareCourseId;
    
    private String gradeName;
    
    private int defaultStudentCount;
    
    
    public Integer type; //
    
    public Integer lineState; //瑞鑫加的直播状态  直播状态 1.直播中，2预告，3直播结束

    private String actualPay;//实付金额
    
    private Date startTime;
    
    private Date endTime;
    

    private String onlineCourse; //直播 线下课

    private String address; //线下课程地址
    
    private String city; //所在城市 
    
    private Integer cutoff =0;	// 0 已截止  1 未截止
    
    private Boolean collection; 	//是否为专辑 false不是 true 是

    
    public String getActualPay() {
		return actualPay;
	}

	public void setActualPay(String actualPay) {
		this.actualPay = actualPay;
	}

	public int getDefaultStudentCount() {
		return defaultStudentCount;
	}

	public void setDefaultStudentCount(int defaultStudentCount) {
		this.defaultStudentCount = defaultStudentCount;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getLearndVideo() {
        return learndVideo;
    }

    public void setLearndVideo(Integer learndVideo) {
        this.learndVideo = learndVideo;
    }

    public String getCourseOutline() {
        return courseOutline;
    }

    public void setCourseOutline(String courseOutline) {
        this.courseOutline = courseOutline;
    }

    public String getDetailImgPath() {
        return detailImgPath;
    }

    public void setDetailImgPath(String detailImgPath) {
        this.detailImgPath = detailImgPath;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getCourseLength() {
        return courseLength;
    }

    public void setCourseLength(Double courseLength) {
        this.courseLength = courseLength;
    }

    public String getLearndCount() {
        return learndCount;
    }

    public void setLearndCount(String learndCount) {
        this.learndCount = learndCount;
    }

    public String getTeacherName() {

        return teacherName == null ? "暂无讲师" :teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getScoreName() {
        return scoreName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }

    public String getBigImgPath() {
        return bigImgPath;
    }

    public void setBigImgPath(String bigImgPath) {
        this.bigImgPath = bigImgPath;
    }

    public String getCloudClassroom() {
        return cloudClassroom;
    }

    public void setCloudClassroom(String cloudClassroom) {
        this.cloudClassroom = cloudClassroom;
    }

    public String getSmallImgPath() {
        return smallImgPath;
    }

    public void setSmallImgPath(String smallImgPath) {
        this.smallImgPath = smallImgPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
    	setCourseId(id);
        this.id = id;
    }

    public String getTeacherNames() {
        return teacherNames;
    }

    public void setTeacherNames(String teacherNames) {
        this.teacherNames = teacherNames;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
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

    public boolean isRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isApply() {
        return isApply;
    }

    public void setIsApply(Boolean isApply) {
        this.isApply = isApply;
    }

    public String getQqno() {
        return qqno;
    }

    public void setQqno(String qqno) {
        this.qqno = qqno;
    }

    public String getPreferentyMoney() {
        return preferentyMoney;
    }

    public void setPreferentyMoney(String preferentyMoney) {
        this.preferentyMoney = preferentyMoney;
    }

    public Integer getUnStudy() {
        return unStudy;
    }

    public void setUnStudy(Integer unStudy) {
        this.unStudy = unStudy;
    }

	public String getOriginalCost() {
		return originalCost;
	}

	public void setOriginalCost(String originalCost) {
		this.originalCost = originalCost;
	}

    public Integer getDescription_show() {
        return description_show;
    }

    public void setDescription_show(Integer description_show) {
        this.description_show = description_show;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Integer getCourse_type() {
        return course_type;
    }

    public void setCourse_type(Integer course_type) {
        this.course_type = course_type;
    }

	public String getShareCourseId() {
		return shareCourseId;
	}

	public void setShareCourseId(String shareCourseId) {
		this.shareCourseId = shareCourseId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getLineState() {
		return lineState;
	}

	public void setLineState(Integer lineState) {
		this.lineState = lineState;
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

    public String getOnlineCourse() {
        return onlineCourse;
    }

    public void setOnlineCourse(String onlineCourse) {
        this.onlineCourse = onlineCourse;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getCutoff() {
		return cutoff;
	}

	public void setCutoff(Integer cutoff) {
		this.cutoff = cutoff;
	}

	public Boolean getCollection() {
		return collection;
	}

	public void setCollection(Boolean collection) {
		this.collection = collection;
	}
	
    
}
