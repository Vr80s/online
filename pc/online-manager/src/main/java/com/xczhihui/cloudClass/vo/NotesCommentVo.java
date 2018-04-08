package com.xczhihui.cloudClass.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class NotesCommentVo extends OnlineBaseVo  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String notesId;
    private String content;
    private String createNickName;
    private String createHeadImg;
    private String targetPerson;
    private String targetNikeName;
    private String userId;
    private String targetUserId;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNotesId() {
		return notesId;
	}
	public void setNotesId(String notesId) {
		this.notesId = notesId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateNickName() {
		return createNickName;
	}
	public void setCreateNickName(String createNickName) {
		this.createNickName = createNickName;
	}
	public String getCreateHeadImg() {
		return createHeadImg;
	}
	public void setCreateHeadImg(String createHeadImg) {
		this.createHeadImg = createHeadImg;
	}
	public String getTargetPerson() {
		return targetPerson;
	}
	public void setTargetPerson(String targetPerson) {
		this.targetPerson = targetPerson;
	}
	public String getTargetNikeName() {
		return targetNikeName;
	}
	public void setTargetNikeName(String targetNikeName) {
		this.targetNikeName = targetNikeName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}
}
