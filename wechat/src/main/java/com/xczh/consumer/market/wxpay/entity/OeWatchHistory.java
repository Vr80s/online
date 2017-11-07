package com.xczh.consumer.market.wxpay.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class OeWatchHistory {
    private Long id;
    
    private String userId;       //用户id

    private String lecturerId;    //讲师id

    private Integer courseId;     //课程id 

    private String smallimgPath;  //直播缩率图

    private String gradeName;     //课程名字

    private String lecturerName;  //老师名字

    private String teacherHeadImg;  //老师头像

    private Date watchTime;        //观看时间 
    
    private String  timeDifference; //时间差  -- 多少分钟前，多少小时前  ......
    
    private Date startTime;  //开始时间
    
    private Date endTime;  //结束时间

    private String directId; //视频id
    
    private String type; //观看类型 1 直播 2 点播 3 音频
    
    /**
     * 是否收费     0 付费   1 免费
     */
    public Integer isFree;
    /**
     * 是否需要密码认证
     */
    public Integer isApprove;  //0 需要认证，1，不需要认证
    /**
     * 
     */
    public Integer  lineState; //直播状态
    
    /**
     * 请求 --》直播使用了， 微吼接口得到的当前在线人数返回的结果集
     */
    public JSONObject jobj;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId == null ? null : lecturerId.trim();
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }


    public String getSmallimgPath() {
        return smallimgPath;
    }

    public void setSmallimgPath(String smallimgPath) {
        this.smallimgPath = smallimgPath == null ? null : smallimgPath.trim();
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName == null ? null : gradeName.trim();
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName == null ? null : lecturerName.trim();
    }

    public String getTeacherHeadImg() {
        return teacherHeadImg;
    }

    public void setTeacherHeadImg(String teacherHeadImg) {
        this.teacherHeadImg = teacherHeadImg == null ? null : teacherHeadImg.trim();
    }

    public Date getWatchTime() {
        return watchTime;
    }

    public void setWatchTime(Date watchTime) {
        this.watchTime = watchTime;
    }

	public String getTimeDifference() {
		return timeDifference;
	}

	public void setTimeDifference(String timeDifference) {
		this.timeDifference = timeDifference;
	}

	public Integer getIsFree() {
		return isFree;
	}

	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}

	public Integer getIsApprove() {
		return isApprove;
	}

	public void setIsApprove(Integer isApprove) {
		this.isApprove = isApprove;
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

	public String getDirectId() {
		return directId;
	}

	public void setDirectId(String directId) {
		this.directId = directId;
	}

	public JSONObject getJobj() {
		return jobj;
	}

	public void setJobj(JSONObject jobj) {
		this.jobj = jobj;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
    
}