package com.xczhihui.online.api.vo;

import java.io.Serializable;
import java.util.Date;


/**
 * @Author Fudong.Sun【】
 * @Date 2016/11/2 19:52
 */
public class CriticizeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String createPerson;

    private Date createTime;

    private String content;

    private String userId;

    private String chapterId;

    private String videoId;

    private Float starLevel;

    private Integer praiseSum;

    private String praiseLoginNames;

    private String userName;

    private Integer isPraise;

    private String smallPhoto;

    private Integer courseId;

    private String response;

    private Date response_time;
    /**
     * 视频名称
     */
    private String videoName;

    /**
     * 杨宣新增
     */
    //节目内容
    private Float contentLevel;

    //主播演绎
    private Float deductiveLevel;

    //评价标签  1.很赞 2 干货很多 3超值推荐 4喜欢 5买对了
    private String criticizeLable;

    //总体印象
    private Float overallLevel;

    //是否购买
    private Boolean isBuy;

    //是否是专辑评论
    private Integer collectionId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Float getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Float starLevel) {
        this.starLevel = starLevel;
    }

    public Integer getPraiseSum() {
        return praiseSum;
    }

    public void setPraiseSum(Integer praiseSum) {
        this.praiseSum = praiseSum;
    }

    public String getPraiseLoginNames() {
        return praiseLoginNames;
    }

    public void setPraiseLoginNames(String praiseLoginNames) {
        this.praiseLoginNames = praiseLoginNames;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(Integer isPraise) {
        this.isPraise = isPraise;
    }

    public String getSmallPhoto() {
        return smallPhoto;
    }

    public void setSmallPhoto(String smallPhoto) {
        this.smallPhoto = smallPhoto;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getResponse_time() {
        return response_time;
    }

    public void setResponse_time(Date response_time) {
        this.response_time = response_time;
    }

    public Float getContentLevel() {
        return contentLevel;
    }

    public void setContentLevel(Float contentLevel) {
        this.contentLevel = contentLevel;
    }

    public Float getDeductiveLevel() {
        return deductiveLevel;
    }

    public void setDeductiveLevel(Float deductiveLevel) {
        this.deductiveLevel = deductiveLevel;
    }

    public String getCriticizeLable() {
        return criticizeLable;
    }

    public void setCriticizeLable(String criticizeLable) {
        this.criticizeLable = criticizeLable;
    }

    public Float getOverallLevel() {
        return overallLevel;
    }

    public void setOverallLevel(Float overallLevel) {
        this.overallLevel = overallLevel;
    }

    public Boolean getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(Boolean isBuy) {
        this.isBuy = isBuy;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }


}
