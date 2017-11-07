package com.xczhihui.bxg.online.common.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.bxg.common.support.domain.BasicEntity;


@Entity
@Table(name = "oe_notes_comment")
public class NotesComment extends BasicEntity  {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "notes_Id")
	private String notesId;
	
	@Column(name = "content")
    private String content;
	
	@Column(name = "create_nick_name")
    private String createNickName;
	
	@Column(name = "create_head_img")
    private String createHeadImg;
	
	@Column(name = "target_person")
    private String targetPerson;
	
	@Column(name = "target_nike_name")
    private String targetNikeName;
	
	@Column(name = "user_id")
    private String userId;
	
	@Column(name = "target_user_id")
    private String targetUserId;

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
