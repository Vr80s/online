package com.xczhihui.bxg.online.web.vo;

/**
 * 回答
 */
public class AskAnswerVo  extends AskVo {
	private static final long serialVersionUID = 5749211401087973489L;
    /**
     *问题id
     */
    private String question_id;
    /**
     *内容
     */
    private String  content;
    /**
     * 纯文本内容，不含标签
     */
    private String text;
    /**
     * 评论数
     */
    private Integer comment_sum;
    /**
     * 点赞数
     */
    private Integer praise_sum;
    /**
     * 点赞的人的登录名，多个用英文逗号分隔
     */
    private String praise_login_names;
    /**
     * 是否被采纳，默认“否”
     */
    private Boolean accepted;
    /**
     * 是否被投诉，默认“否”
     */
    private Boolean accused ;
    /**
     * 是否保留作者权利，默认“否”
     */
    private Boolean copyright;
    /**
     * 昵称
     */
    private String create_nick_name;
    /**
     * 头像
     */
    private String create_head_img;
    /**
	 * 当前用户是否点赞
	 */
	private Boolean praise;
	/**
	 * 回答类型（0：普通回答 1：官方回答）
	 */
	private Boolean answer_type;

	/**
	 * 管理员针对投诉问题的处理状态:0未处理1已处理 2不做处理
	 */
	private  Integer handleStatus;
	/**
	 * 当前用户是不是创建者
	 */
	private Boolean creator;

	/**
	 * 当前登录用户id
	 */
	private String  user_id;

	/**
	 * 0：添加 1:编辑
	 */
	private Integer methodType;

	public Boolean getCreator() {
		return creator;
	}
	public void setCreator(Boolean creator) {
		this.creator = creator;
	}
	public Boolean getPraise() {
		return praise;
	}
	public void setPraise(Boolean praise) {
		this.praise = praise;
	}
	public String getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getComment_sum() {
		return comment_sum;
	}
	public void setComment_sum(Integer comment_sum) {
		this.comment_sum = comment_sum;
	}
	public Integer getPraise_sum() {
		return praise_sum;
	}
	public void setPraise_sum(Integer praise_sum) {
		this.praise_sum = praise_sum;
	}
	public String getPraise_login_names() {
		return praise_login_names;
	}
	public void setPraise_login_names(String praise_login_names) {
		this.praise_login_names = praise_login_names;
	}
	public Boolean getAccepted() {
		return accepted;
	}
	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}
	public Boolean getAccused() {
		return accused;
	}
	public void setAccused(Boolean accused) {
		this.accused=false;
		this.accused = accused;
	}
	public Boolean getCopyright() {
		return copyright;
	}
	public void setCopyright(Boolean copyright) {
		this.copyright = copyright;
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

	public Integer getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(Integer handleStatus) {
		this.handleStatus = handleStatus;
	}

	public Boolean getAnswer_type() {
		return answer_type;
	}

	public void setAnswer_type(Boolean answer_type) {
		this.answer_type = answer_type;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Integer getMethodType() {
		return methodType;
	}

	public void setMethodType(Integer methodType) {
		this.methodType = methodType;
	}
}
