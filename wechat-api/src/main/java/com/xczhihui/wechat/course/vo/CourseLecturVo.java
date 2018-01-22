package com.xczhihui.wechat.course.vo;


import java.io.Serializable;
import java.util.Date;


/**
 * Created by admin on 2016/7/27.
 */
public class CourseLecturVo implements Serializable {
    /**
     *课程ID
     */
    private Integer id;
    
    private Integer courseId;
    /**
     *课程名
     */
    private String gradeName;
    
    /**
     * 讲师简介
     */
    private String lecturerDescription;

    /**
     * 课程小图
     */
    private String smallImgPath;

    /**
     * 讲师id
     */
    private String userId;
    /**
     * 讲师名
     */
    private String  name;
    
    /**
     * 讲师头像
     */
    private String headImg;

    /**
     * 直播间id 
     */
    private String  directId;
    
    /**
     * 章节id 
     */
    private String  chapterId;
    
    /**
     * 视频 的 主键id 
     */
    private String  vId;
    
    /**
     * 直播开始时间
     */
    private Date startTime;
    /**
     * 直播结束时间
     */
    private Date endTime;
    /**
     * 当前直播状态:  0 直播已结束   1 直播还未开始   2 点播 
     */
    private Integer lineState;
    
    //c.original_cost as originalCost,c.current_price as currentPrice,c.is_free as isFree
    /**
     * 原价
     */
    private double originalCost;
    /**
     * 现价
     */
    private double currentPrice;
    /**
     * 是否收费  true 免费  false 收费
     */
    private Integer isFree;

    /**
     * 课程时长
     */
    private double courseLength;
    
    /**
     * 观看人数
     */
    private Integer  learndCount;

    private Integer giftCount;
    
    private Integer fansCount;
    
    

    //打赏数量
    private String rewardCount;

    /**
     * 是否需要密码认证
     */
    private Integer isApprove;  //0 需要认证，1，不需要认证
    
    /**
     * 课程详情
     */
    private String description;    //来自oe_course_mobile的详情   富文本的课程详情
    
    
    private String courseDescription; //课程简介
    
    /**
     * 多媒体类型   1 点播  2 音频
     */
    private Integer multimediaType;
    
    /**
     * 房间号
     */
    private Integer roomNumber;
    /**
     *直播方式 
     */
    private Integer type; //课程分类 1:公开直播课     is null ：点播
    
    /**
     * 是否关注了这个主播
     */
    private Integer isfocus; //课程分类   0 未关注     1 关注
    
    private Integer countFans; //粉丝数
    
    private Integer countGift; //礼物数
    
    private Integer countSubscribe; //预约的人数
    
    private Integer isSubscribe; //0 未预约  1预约
    
    
    private Integer watchState; //观看状态 0免费  1收费  2 需要密码验证
    
    private String imRoomId; //im房间号

	private String address;

	private String udescription;//讲师简介
	
	private String city;//线下课程所在城市

	private String courseTimeConver; //课程时间转换为: 00:00:00
	
	private Integer cutoff;	// 0 已截止  1 未截止
	
	private String userLecturerId;
	
	private String  note; //
	

	private Boolean collection;
	
	private Integer courseNumber;
	
	
	public String getUdescription() {
		return udescription;
	}

	public void setUdescription(String udescription) {
		this.udescription = udescription;
	}

	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getSmallImgPath() {
		return smallImgPath;
	}
	public void setSmallImgPath(String smallImgPath) {
		this.smallImgPath = smallImgPath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getDirectId() {
		return directId;
	}
	public void setDirectId(String directId) {
		this.directId = directId;
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
	public Integer getLineState() {
		return lineState;
	}
	public void setLineState(Integer lineState) {
		this.lineState = lineState;
	}
	public double getOriginalCost() {
		return originalCost;
	}
	public void setOriginalCost(double originalCost) {
		this.originalCost = originalCost;
	}
	public double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public Integer getIsFree() {
		return isFree;
	}
	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}
	public double getCourseLength() {
		return courseLength;
	}
	public void setCourseLength(double courseLength) {
		this.courseLength = courseLength;
	}
	public Integer getLearndCount() {
		return learndCount;
	}
	public void setLearndCount(Integer learndCount) {
		this.learndCount = learndCount;
	}
	public Integer getIsApprove() {
		return isApprove;
	}
	public void setIsApprove(Integer isApprove) {
		this.isApprove = isApprove;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		setCourseDescription(description);
		this.description = description;
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
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public Integer getMultimediaType() {
		return multimediaType;
	}
	public void setMultimediaType(Integer multimediaType) {
		this.multimediaType = multimediaType;
	}
	public Integer getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}
	public Integer getIsfocus() {
		return isfocus;
	}
	public void setIsfocus(Integer isfocus) {
		this.isfocus = isfocus;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	public Integer getCountFans() {
		return countFans;
	}
	public void setCountFans(Integer countFans) {
		this.countFans = countFans;
	}
	public Integer getCountGift() {
		return countGift;
	}
	public void setCountGift(Integer countGift) {
		this.countGift = countGift;
	}
	public Integer getCountSubscribe() {
		return countSubscribe;
	}
	public void setCountSubscribe(Integer countSubscribe) {
		this.countSubscribe = countSubscribe;
	}
	public Integer getIsSubscribe() {
		return isSubscribe;
	}
	public void setIsSubscribe(Integer isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
	public Integer getWatchState() {
		return watchState;
	}
	public void setWatchState(Integer watchState) {
		this.watchState = watchState;
	}

	public Integer getGiftCount() {
		return giftCount;
	}

	public String getImRoomId() {
		return imRoomId;
	}
	public void setImRoomId(String imRoomId) {
		this.imRoomId = imRoomId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRewardCount() {
		return rewardCount;
	}

	public void setRewardCount(String rewardCount) {
		this.rewardCount = rewardCount;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public String getvId() {
		return vId;
	}

	public void setvId(String vId) {
		this.vId = vId;
	}

	public String getCourseTimeConver() {
		return courseTimeConver;
	}

	public void setCourseTimeConver(String courseTimeConver) {
		this.courseTimeConver = courseTimeConver;
	}

	public Integer getCutoff() {
		return cutoff;
	}

	public void setCutoff(Integer cutoff) {
		this.cutoff = cutoff;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUserLecturerId() {
		return userLecturerId;
	}

	public void setUserLecturerId(String userLecturerId) {
		this.userLecturerId = userLecturerId;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFansCount() {
		return fansCount;
	}

	public void setFansCount(Integer fansCount) {
		this.fansCount = fansCount;
	}

	public void setGiftCount(Integer giftCount) {
		this.giftCount = giftCount;
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

	public Integer getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(Integer courseNumber) {
		this.courseNumber = courseNumber;
	}
	
	
}
