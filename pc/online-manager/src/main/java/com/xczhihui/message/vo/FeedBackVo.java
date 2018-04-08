package com.xczhihui.message.vo;

import java.util.Date;

/**
 * 意见反馈VO
 * @author duanqh
 *
 */
public class FeedBackVo {

	/**
	 * 编号
	 */
	private String id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String context;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 回复时间
	 */
	private Date lastTime;

	/**
	 * 回答人
	 */
	private String anwerName;

	/**
	 * 状态  1:已回答  0：未回答
	 */
	private Integer status;

	/**
	 * 回答内容
	 */
	private String replytext;

	/**
	 * 提问人
	 */
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getAnwerName() {
		return anwerName;
	}

	public void setAnwerName(String anwerName) {
		this.anwerName = anwerName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getReplytext() {
		return replytext;
	}

	public void setReplytext(String replytext) {
		this.replytext = replytext;
	}
	
}
