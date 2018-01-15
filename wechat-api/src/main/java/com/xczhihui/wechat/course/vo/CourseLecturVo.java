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
    private int id;
    
    private int courseId;
    /**
     *课程名
     */
    private String gradeName;

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
    public String headImg;

    /**
     * 直播间id 
     */
    public String  directId;
    
    /**
     * 章节id 
     */
    public String  chapterId;
    
    /**
     * 视频 的 主键id 
     */
    public String  vId;
    
    /**
     * 直播开始时间
     */
    public Date startTime;
    /**
     * 直播结束时间
     */
    public Date endTime;
    /**
     * 当前直播状态:  0 直播已结束   1 直播还未开始   2 点播 
     */
    public int lineState;
    
    //c.original_cost as originalCost,c.current_price as currentPrice,c.is_free as isFree
    /**
     * 原价
     */
    public double originalCost;
    /**
     * 现价
     */
    public double currentPrice;
    /**
     * 是否收费  true 免费  false 收费
     */
    public Integer isFree;
    /**
     * 课程时长
     */
    public double courseLength = 0;
    
    /**
     * 观看人数
     */
    public int  learndCount;

    public int giftCount;

    //打赏数量
    private String rewardCount;

    /**
     * 是否需要密码认证
     */
    public Integer isApprove;  //0 需要认证，1，不需要认证
    
    /**
     * 课程详情
     */
    public String description;    //来自oe_course_mobile的详情   富文本的课程详情
    
    
    public String courseDescription; //课程简介
    
    /**
     * 多媒体类型   1 点播  2 音频
     */
    public Integer multimediaType;
    
    /**
     * 房间号
     */
    public Integer roomNumber;
    /**
     *直播方式 
     */
    public Integer type; //课程分类 1:公开直播课     is null ：点播
    
    /**
     * 是否关注了这个主播
     */
    public Integer isfocus = 0; //课程分类   0 未关注     1 关注
    
    public Integer countFans =0; //粉丝数
    
    public Integer countGift = 0; //礼物数
    
    public Integer countSubscribe =0; //预约的人数
    
    public Integer isSubscribe = 0; //0 未预约  1预约
    
    
    public Integer watchState = 0; //观看状态 0免费  1收费  2 需要密码验证
    
    public String imRoomId; //im房间号

	private String address;

	private String udescription;//讲师简介
	
	private String city;//线下课程所在城市

	private String courseTimeConver; //课程时间转换为: 00:00:00
	
	private Integer cutoff =0;	// 0 已截止  1 未截止
	
	
	private String  note; //
	
	
	public String getUdescription() {
		return udescription;
	}

	public void setUdescription(String udescription) {
		this.udescription = udescription;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		setCourseId(id);
		this.id = id;
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
	public int getLineState() {
		return lineState;
	}
	public void setLineState(int lineState) {
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
	public int getLearndCount() {
		return learndCount;
	}
	public void setLearndCount(int learndCount) {
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
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
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

	public int getGiftCount() {
		return giftCount;
	}

	public void setGiftCount(int giftCount) {
		this.giftCount = giftCount;
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
}
