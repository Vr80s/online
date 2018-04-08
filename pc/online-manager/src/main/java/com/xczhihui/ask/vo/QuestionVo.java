package com.xczhihui.ask.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 问题管理web端调用的结果封装类
 * @author 王高伟
 */
public class QuestionVo extends OnlineBaseVo {
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 学科ID
     */
    private Integer mentId;
    /**
     * 学科名称
     */
    private String mentName;

    /**
     * 回答数
     */
    private Integer answerSum;

    /**
     * 浏览数
     */
    private Integer browseSum;

    /**
     * 创建人名称
     */
    private String createPersonName;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 标签
     */
    private String tags;
    
    /*
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
    private Date createTime;
    
    /*
     * 创建开始时间
     */
    @JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
    private Date startTime;
    /*
     * 创建结束时间
     */
    @JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
    private Date stopTime;
    
    private String origin;
    
    private Integer type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getMentId() {
		return mentId;
	}

	public void setMentId(Integer mentId) {
		this.mentId = mentId;
	}

	public String getMentName() {
		return mentName;
	}

	public void setMentName(String mentName) {
		this.mentName = mentName;
	}

	public Integer getAnswerSum() {
		return answerSum;
	}

	public void setAnswerSum(Integer answerSum) {
		this.answerSum = answerSum;
	}

	public Integer getBrowseSum() {
		return browseSum;
	}

	public void setBrowseSum(Integer browseSum) {
		this.browseSum = browseSum;
	}

	public String getCreatePersonName() {
		return createPersonName;
	}

	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
    public Date getCreateTime() {
		return createTime;
	}

	@Override
    public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
