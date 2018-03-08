package com.xczhihui.bxg.online.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 评论
 */
public class NotesCommentVo {
	private static final long serialVersionUID = -4001675827737276071L;

	/**
	 * ID
	 */
	private String id;
	/**
	 * 笔记id
	 */
	private String notes_id;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 评论者登录名
	 */
	private String create_person;
	/**
	 * 评论/回复者昵称
	 */
	private String create_nick_name;
	/**
	 * 评论/回复者头像
	 */
	private String create_head_img;
	/**
	 * 被评论/回复者登录名
	 */
	private String target_person;
	/**
	 * 被评论/回复者昵称
	 */
	private String target_nike_name;
	/**
	 * 被评论/回复者账号id
	 */
	private String target_user_id;
	/**
	 * 当前登录用户的id
	 */
	private String user_id;
	/**
	 * 当前用户是否显示删除按钮
	 */
	private Boolean deleteButton;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date create_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotes_id() {
		return notes_id;
	}

	public void setNotes_id(String notes_id) {
		this.notes_id = notes_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreate_person() {
		return create_person;
	}

	public void setCreate_person(String create_person) {
		this.create_person = create_person;
	}

	public String getCreate_nick_name() {
		return create_nick_name;
	}

	public void setCreate_nick_name(String create_nick_name) {
		this.create_nick_name = create_nick_name;
	}

	public String getCreate_head_img() {
		return create_head_img;
	}

	public void setCreate_head_img(String create_head_img) {
		this.create_head_img = create_head_img;
	}

	public String getTarget_person() {
		return target_person;
	}

	public void setTarget_person(String target_person) {
		this.target_person = target_person;
	}

	public String getTarget_nike_name() {
		return target_nike_name;
	}

	public void setTarget_nike_name(String target_nike_name) {
		this.target_nike_name = target_nike_name;
	}

	public String getTarget_user_id() {
		return target_user_id;
	}

	public void setTarget_user_id(String target_user_id) {
		this.target_user_id = target_user_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Boolean getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(Boolean deleteButton) {
		this.deleteButton = deleteButton;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
}
