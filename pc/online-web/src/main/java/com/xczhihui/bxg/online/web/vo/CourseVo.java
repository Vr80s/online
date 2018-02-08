package com.xczhihui.bxg.online.web.vo;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by rongcai Kang on 2016/7/27.
 *
 *
 */

public class CourseVo{



    private Integer id;

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
    private String courseLength;

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
     */
    private String  qqno;
    private String  coursePwd;


    /**
     * 优惠价格
     */
    private  String  preferentyMoney;
    private  String  multimediaType;

    private String directId;

    /**
     * 当前下单时间
     */
    private Timestamp create_time;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "GMT+8")
    private Date start_time;
    
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private Date endTime;

    private String week;

    private String teacherDescription;
    private String lecturerDescription;

    /**
     * 课程跳转是否展示课程介绍页 0:不展示  1:展示
     */
    private Integer description_show;

    /**
     * 0:职业课  1:微课
     */
    private Integer course_type;
    
    private Integer type;
    
    /**
     * 成为分享大使课的id
     */
    private String shareCourseId;

    private String direct_id;

    /**
     *是否发送过订阅短信提醒
     */
    private Boolean isSent;

    private String userLecturerId;
    
    private Integer giftCount;
    private Integer onlineCourse;
    
    
    private Integer liveStatus; //直播状态1.直播中，2预告，3直播结束

    private boolean isAvailable;//是否有效
    
    
    private boolean isSelfCourse; //是否自己的课程 

    public String getLecturerDescription() {
        return lecturerDescription;
    }

    public void setLecturerDescription(String lecturerDescription) {
        this.lecturerDescription = lecturerDescription;
    }

    public boolean isSelfCourse() {
        return isSelfCourse;
    }

    public void setSelfCourse(boolean isSelfCourse) {
        this.isSelfCourse = isSelfCourse;
    }
    
    
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getCoursePwd() {
		return coursePwd;
	}

	public void setCoursePwd(String coursePwd) {
		this.coursePwd = coursePwd;
	}

    public String getDirectId() {
        return directId;
    }

    public void setDirectId(String directId) {
        this.directId = directId;
    }

    public Integer getOnlineCourse() {
		return onlineCourse;
	}

	public void setOnlineCourse(Integer onlineCourse) {
		this.onlineCourse = onlineCourse;
	}

	public Integer getGiftCount() {
		return giftCount;
	}

	public void setGiftCount(Integer giftCount) {
		this.giftCount = giftCount;
	}

	public String getMultimediaType() {
		return multimediaType;
	}

	public void setMultimediaType(String multimediaType) {
		this.multimediaType = multimediaType;
	}

	public String getTeacherDescription() {
		return teacherDescription;
	}

	public void setTeacherDescription(String teacherDescription) {
		this.teacherDescription = teacherDescription;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public void setStartTime(Date startTime) {
		setWeek(getWeekOfDate(startTime));
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getUserLecturerId() {
		return userLecturerId;
	}

	public void setUserLecturerId(String userLecturerId) {
		this.userLecturerId = userLecturerId;
	}

	public Boolean getIsSent() {
		return isSent;
	}

	public void setIsSent(Boolean isSent) {
		this.isSent = isSent;
	}

	public String getDirect_id() {
		return direct_id;
	}

	public void setDirect_id(String direct_id) {
		this.direct_id = direct_id;
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

    public String getCourseLength() {
        return courseLength;
    }

    public void setCourseLength(String courseLength) {
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Integer getLiveStatus() {
		return liveStatus;
	}

	public void setLiveStatus(Integer liveStatus) {
		this.liveStatus = liveStatus;
	}

	
	
	
	public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }
}
