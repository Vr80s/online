package com.xczhihui.ask.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 问题管理web端调用的结果封装类
 * @author 王高伟
 */
public class AccuseVo extends OnlineBaseVo {
    private String id;
    /**
     * 投诉目标类型，0问题，1回答，2评论
     */
    private String targetType;
    /**
     * 目标id
     */
    private String targetId;
    /**
     * 投诉内容类型，0广告营销等垃圾信息，1抄袭内容，2辱骂等不文明言语的人身攻击，3色情或反动的违法信息，4其他
     */
    private String accuseType;
    /**
     * 投诉类型其他内容
     */
    private String accuseTypeContent;

    /**
     * 投诉内容
     */
    private String content;
    
    /**
     * 投诉状态 0未处理1已处理3不做处理
     */
    private Integer status;

    /**
     * 创建人名称
     */
    private String createPersonName;
    
    /**
     * 问题ID
     */
    private String questionId;
    
    /*
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
    private Date createTime;
    
    /**
     * 被投诉人姓名
     */
    private String accusePersonName;
    
    /**
     * 被投诉人ID
     */
    private String accusePersonId;
    
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId( String targetId) {
		this.targetId = targetId;
	}
	public String getAccuseType() {
		return accuseType;
	}
	public void setAccuseType(String accuseType) {
		this.accuseType = accuseType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatePersonName() {
		return createPersonName;
	}
	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
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
	public String getAccuseTypeContent() {
		return accuseTypeContent;
	}
	public void setAccuseTypeContent(String accuseTypeContent) {
		this.accuseTypeContent = accuseTypeContent;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getAccusePersonName() {
		return accusePersonName;
	}
	public void setAccusePersonName(String accusePersonName) {
		this.accusePersonName = accusePersonName;
	}
	public String getAccusePersonId() {
		return accusePersonId;
	}
	public void setAccusePersonId(String accusePersonId) {
		this.accusePersonId = accusePersonId;
	}
}
