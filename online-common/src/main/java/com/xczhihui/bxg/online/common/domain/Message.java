package com.xczhihui.bxg.online.common.domain;

import com.xczhihui.common.support.domain.BasicEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 学员消息
 * @author duanqh
 *
 */
@Entity
@Table(name = "oe_message")
public class Message extends BasicEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@Column(name = "user_id", length = 32)
	private String userId;

	/**
	 * 消息标题
	 */
	@Column(name = "title", length = 50)
	private String title;

	/**
	 * 消息内容
	 */
	@Type(type="text")
	@Column(name = "context")
	private String context;
	
	/**
	 * 消息类型：0.系统消息(站内推送消息;)  1.课程消息 2.用户意见反馈  3.问答消息  4.评论消息
	 *
	 */
	@Column(name = "type")
	private Integer type;

	/**
	 * 消息状态：1.已发送;
	 */
	@Column(name = "status")
	private Short status;



	/**
	 * 管理员回复时间
	 */
	@Column(name = "lasttime")
	private Date lastTime;

	/**
	 * 读取状态：0.未读取  1.已读取;
	 */
	@Column(name = "readstatus")
	private Short readstatus;

	/**
	 * 意见反馈编号
	 */
	@Column(name = "pid")
	private String pid;

	/**
	 * 反馈状态 0:未反馈 1:已反馈
	 */
	@Column(name = "answerStatus")
	private Short answerStatus;

	public Short getAnswerStatus() {
		return answerStatus;
	}

	public void setAnswerStatus(Short answerStatus) {
		this.answerStatus = answerStatus;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getReadstatus() {
		return readstatus;
	}
	public void setReadstatus(Short readstatus) {
		this.readstatus = readstatus;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
