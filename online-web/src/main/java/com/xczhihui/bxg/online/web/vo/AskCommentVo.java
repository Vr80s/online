package com.xczhihui.bxg.online.web.vo;

/**
 * 评论
 */
public class AskCommentVo  extends AskVo  {
	private static final long serialVersionUID = -4001675827737276071L;
	/**
	 * 问题id
	 */
	private String answer_id;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 点赞的人的登录名，多个用英文逗号分隔
	 */
	private String praise_login_names;
	/**
	 * 点赞数
	 */
	private Integer praise_sum;
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
	 * 当前用户是否点赞
	 */
	private boolean praise;
	/**
	 * 是否显示删除按钮
	 */
	private boolean delete_button;
	/**
	 * 当前登录用户的id
	 */
	private String user_id;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public boolean isDelete_button() {
		return delete_button;
	}
	public void setDelete_button(boolean delete_button) {
		this.delete_button = delete_button;
	}
	public boolean isPraise() {
		return praise;
	}
	public void setPraise(boolean praise) {
		this.praise = praise;
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
	public String getAnswer_id() {
		return answer_id;
	}
	public void setAnswer_id(String answer_id) {
		this.answer_id = answer_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPraise_login_names() {
		return praise_login_names;
	}
	public void setPraise_login_names(String praise_login_names) {
		this.praise_login_names = praise_login_names;
	}
	public Integer getPraise_sum() {
		return praise_sum;
	}
	public void setPraise_sum(Integer praise_sum) {
		this.praise_sum = praise_sum;
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

	public String getTarget_user_id() {
		return target_user_id;
	}

	public void setTarget_user_id(String target_user_id) {
		this.target_user_id = target_user_id;
	}
}
