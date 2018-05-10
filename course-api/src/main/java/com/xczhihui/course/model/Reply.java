package com.xczhihui.course.model;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
public class Reply extends Model<Reply> {

    private static final long serialVersionUID = 1L;

    /**
     * 评论回复表
     */
	private String id;
	private Boolean isDelete;
	private String createPerson;
	private Date createTime;
	private Integer sort;
	private Integer status;
    /**
     * 回复内容
     */
	private String replyContent;
    /**
     * 回复人id
     */
	private String replyUser;
    /**
     * 评论id
     */
	private String criticizeId;

	private OnlineUser onlineUser;
	private String name;
	private String loginName;
	

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		if(this.onlineUser==null){
			this.onlineUser = new OnlineUser();
		}
		this.onlineUser.setId(this.getReplyUser());
		this.onlineUser.setLoginName(loginName);
		this.loginName = loginName;
	}
	
	
	public OnlineUser getOnlineUser() {
		return onlineUser;
	}

	public void setOnlineUser(OnlineUser onlineUser) {
		this.onlineUser = onlineUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.onlineUser = new OnlineUser();
		onlineUser.setName(name);
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getDelete() {
		return isDelete;
	}

	public void setDelete(Boolean isDelete) {
		this.isDelete = isDelete;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getReplyUser() {
		return replyUser;
	}

	public void setReplyUser(String replyUser) {
		if(this.onlineUser==null){
			this.onlineUser = new OnlineUser();
		}
		this.onlineUser.setId(replyUser);
		this.replyUser = replyUser;
	}

	public String getCriticizeId() {
		return criticizeId;
	}

	public void setCriticizeId(String criticizeId) {
		this.criticizeId = criticizeId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "OeReply{" +
			", id=" + id +
			", isDelete=" + isDelete +
			", createPerson=" + createPerson +
			", createTime=" + createTime +
			", sort=" + sort +
			", status=" + status +
			", replyContent=" + replyContent +
			", replyUser=" + replyUser +
			", criticizeId=" + criticizeId +
			"}";
	}
}
