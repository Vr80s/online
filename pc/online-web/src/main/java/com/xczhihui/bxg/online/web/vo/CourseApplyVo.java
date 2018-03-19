package com.xczhihui.bxg.online.web.vo;/**
 * Created by admin on 2016/8/30.
 */

/**
 * 课程表名详细信息返回封装类
 *
 * @author 康荣彩
 * @create 2016-08-30 20:51
 */
public class CourseApplyVo {

    private Integer id;
    /**
     *课程名称
     */
    private String  courseName;

    /**
     *课程是否免费状态:true:免费  false:付费
     */
    private boolean  isFree;

    /**
     *课程图片
     */
    private String  bigImgPath;

    /**
     *云课堂跳转地址
     */
    private  String cloudClassroom;

    /**
     *课程描述信息
     */
    private  String  description;


    /**
     *课程原价
     */
    private String originalCost;


    /**
     *课程当前价格
     */
    private  String  currentPrice;

    /**
     * 授课方式：直播  点播  。。。。
     */
    private  String  courseType;

    /**
     * 用户登录账号
     */
    private  String  userId;

    private  String  coursePwd;

    private Integer type;
    private Boolean collection;

    private int onlineCourse;

    private String directId;
    //主播id
    private  String userLecturerId;

    public Boolean getCollection() {
        return collection;
    }

    public void setCollection(Boolean collection) {
        this.collection = collection;
    }

    public String getDirectId() {
        return directId;
    }

    public void setDirectId(String directId) {
        this.directId = directId;
    }

    public String getCoursePwd() {
		return coursePwd;
	}

	public void setCoursePwd(String coursePwd) {
		this.coursePwd = coursePwd;
	}

	public int getOnlineCourse() {
		return onlineCourse;
	}

	public void setOnlineCourse(int onlineCourse) {
		this.onlineCourse = onlineCourse;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(String originalCost) {
        this.originalCost = originalCost;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserLecturerId() {
        return userLecturerId;
    }

    public void setUserLecturerId(String userLecturerId) {
        this.userLecturerId = userLecturerId;
    }
}
