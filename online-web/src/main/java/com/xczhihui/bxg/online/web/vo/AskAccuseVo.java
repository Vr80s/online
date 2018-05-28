package com.xczhihui.bxg.online.web.vo;

/**
 * 投诉
 */
public class AskAccuseVo extends AskVo {
	private static final long serialVersionUID = 591222807014874091L;


	/**
	 *投诉目标类型，0问题，1回答，2评论
	 */
	private String target_type;
	/**
	 *目标id
	 */
	private String  target_id;

	/**
	 * 投诉内容类型，0广告营销等垃圾信息，1抄袭内容，2辱骂等不文明言语的人身攻击，3色情或反动的违法信息，4其他
	 */
	private String accuse_type;

	/**
	 * 其他内容，当“accuse_type=其他”的时候填写
	 */
	private String content;

	/**
	 * 投诉状态： 0未处理1已处理2不做处理
	 */
	private Integer status;

	/**
	 * 当前登录用户的id
	 */
	private  String user_id;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTarget_type() {
		return target_type;
	}

	public void setTarget_type(String target_type) {
		this.target_type = target_type;
	}

	public String getTarget_id() {
		return target_id;
	}

	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}

	public String getAccuse_type() {
		return accuse_type;
	}

	public void setAccuse_type(String accuse_type) {
		this.accuse_type = accuse_type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
