package com.xczhihui.bxg.online.api.po;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liutao
 * @create 2017-09-18 11:45
 **/
public class LiveExamineInfoVo implements Serializable{

    private String id;
    private String logo;
    private String title;
    private String seeMode;
    private String price;
    private Date startTime;
    private String directId;
    private String learndCount;
    private String againstReason;
    private String examineStatus;
    private String type;
    private Date createTime;
    private String liveStatus;
    private String content;
    private String courseId;
    
    private Date endTime;
    
    private String imRoomId; 
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeeMode() {
        return seeMode;
    }

    public void setSeeMode(String seeMode) {
        this.seeMode = seeMode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getDirectId() {
        return directId;
    }

    public void setDirectId(String directId) {
        this.directId = directId;
    }

    public String getLearndCount() {
        return learndCount;
    }

    public void setLearndCount(String learndCount) {
        this.learndCount = learndCount;
    }

    public String getAgainstReason() {
        return againstReason;
    }

    public void setAgainstReason(String againstReason) {
        this.againstReason = againstReason;
    }

    public String getExamineStatus() {
        return examineStatus;
    }

    public void setExamineStatus(String examineStatus) {
        this.examineStatus = examineStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getImRoomId() {
		return imRoomId;
	}

	public void setImRoomId(String imRoomId) {
		this.imRoomId = imRoomId;
	}
    
    
}
