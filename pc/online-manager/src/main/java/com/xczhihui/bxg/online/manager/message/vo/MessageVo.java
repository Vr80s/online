package com.xczhihui.bxg.online.manager.message.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xczhihui.bxg.common.util.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

/**
 * 消息数据传输模型
 * @author majian
 * @date 2016-3-4 10:52:53
 */
public class MessageVo {

    /**
     * 行记录ID
     */
    private Integer rowId;
    /**
     * ID
     */
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String context;
    /**
     * 类型
     */
    private Integer type;
    /**
     *  状态字典code
     */
    private Short status;
    /**
     * 字典值
     */
    private String statusStr;
    /**
     * 按起始时间时间查询
     */
    private String time_end;
    /**
     * 按结束时间时间查询
     */
    private String time_start;
    /**
     * 回复状态
     */
    private Short answerStatus;
    /**
     * 回答人
     */
    private String answerName;
    /**
     * 管理员回复时间
     */
    private Date lastTime;
    private String lastTimeStr;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 创建时间 Date类型
     */
    private Date createTime;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 创建时间 String类型
     */
    private String createTimeStr;

    /**
	 * 学科
	 */
	@Column(name = "subject")
	private String subject;

	/**
	 * 课程
	 */
	private String course;
	
	/**
	 * 班级
	 */
	private String grade;
    
	
	/**
	 * 学科
	 */
	private String subjectName;

	/**
	 * 课程
	 */
	private String courseName;
	
	/**
	 * 班级
	 */
	private String gradeName;
	
	/**
	 * 发送用户数
	 */
	private Integer userCount;

    /**
     * 推送时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pushTime;

    /**
     * 推送次数
     */
    private Integer pushCount;

    /**
     * 指定用户id
     */
    private String userIdList;

    /**
     * 推送类型
     */
    private Integer pushType;

    /**
     * 后续动作
     */
    private Integer pushAction;

    /**
     * url地址
     */
    private String url;


		
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
        setLastTimeStr();
    }

    public String getLastTimeStr() {
        return lastTimeStr;
    }

    public void setLastTimeStr() {
    	if (lastTime != null) {
    		lastTimeStr = DateUtil.formatDate(lastTime, DateUtil.FORMAT_DAY_TIME);
        }
    }
    
    public void setLastTimeStr(String lastTimeStr){
    	this.lastTimeStr = lastTimeStr;
    }

    private String[] userIds; //用户列表

    public String[] getUserIds() {
        return userIds;
    }

    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public Short getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(Short answerStatus) {
        this.answerStatus = answerStatus;
    }

    public String getAnswerName() {
        return answerName;
    }

    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr() {
        if (createTime != null) {
            createTimeStr = DateUtil.formatDate(createTime, DateUtil.FORMAT_DAY_TIME);
        }
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
        setCreateTimeStr();
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getPushCount() {
        return pushCount;
    }

    public void setPushCount(Integer pushCount) {
        this.pushCount = pushCount;
    }

    public String getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(String userIdList) {
        this.userIdList = userIdList;
    }

    public Integer getPushType() {
        return pushType;
    }

    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

    public Integer getPushAction() {
        return pushAction;
    }

    public void setPushAction(Integer pushAction) {
        this.pushAction = pushAction;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
