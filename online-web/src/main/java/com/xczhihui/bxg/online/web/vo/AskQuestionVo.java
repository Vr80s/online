package com.xczhihui.bxg.online.web.vo;

import java.util.Date;

/**
 * 提问
 */
public class AskQuestionVo  extends AskVo  {
	private static final long serialVersionUID = 8264541173924037162L;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 内容的纯文本
	 */
	private String text;
	/**
	 * 学科id
	 */
	private Integer ment_id;

	/**
	 * 学科名称
	 */
	private String name;
	/**
	 * 标签文字，多个用英文逗号分隔
	 */
	private String tags;

	/**
	 *标签数据
	 */
	private String[] tag;
	/**
	 * 回答者数量
	 */
	private Integer answer_sum;
	/**
	 * 点赞数
	 */
	private Integer praise_sum;

	/**
	 * 回答信息
	 */
	private AskAnswerVo askAnswerVo;

	/**
	 * 点赞的人的登录名，多个用英文逗号分隔
	 */
	private String praise_login_names;
	/**
	 * 浏览数
	 */
	private Integer browse_sum;
	/**
	 * 状态，0待回答，1回答中，2已解决
	 */
	private String status;
	/**
	 * 是否被投诉，默认“否”
	 */
	private Boolean accused;
	/**
	 * 昵称
	 */
	private String create_nick_name;
	/**
	 * 头像
	 */
	private String create_head_img;

	/**
	 * 收藏状态 true:已收藏  false:未收藏
	 */
	private Boolean collectStatu = false;

	/**
	 * 投诉状态 true:已投诉  false:未投诉
	 */
	private  Boolean  accuseStatus ;

	/**
	 *服务器系统时间
	 */
	private Date  systemTime;

	/**
	 *是否是本人
	 */
	private boolean  isMyself;

	private  String  strTime;

	/**
	 * 控制学科是否公开 1:全部公开  0：只对付费课程公开
	 */
	private  Integer  ask_limit;

	/**
	 * 是否显示问题下面的回答信息:true 显示  false 不显示
	 */
	private boolean  isShowAnswer;

	/**
	 * 管理员针对投诉问题的处理状态:0未处理1已处理 2不做处理
	 */
    private  Integer handleStatus;

	/**
	 * 登录用户id
	 */
	private String  userId;

	/**
	 * 视频id
	 */
	private String videoId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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

	public Integer getMent_id() {
		return ment_id;
	}

	public void setMent_id(Integer ment_id) {
		this.ment_id = ment_id;
	}

	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Integer getAnswer_sum() {
		return answer_sum;
	}
	public void setAnswer_sum(Integer answer_sum) {
		this.answer_sum = answer_sum;
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
	public Integer getBrowse_sum() {
		return browse_sum;
	}
	public void setBrowse_sum(Integer browse_sum) {
		this.browse_sum = browse_sum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getAccused() {
		return accused;
	}
	public void setAccused(Boolean accused) {
		this.accused = accused;
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


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public AskAnswerVo getAskAnswerVo() {
		return askAnswerVo;
	}

	public void setAskAnswerVo(AskAnswerVo askAnswerVo) {
		this.askAnswerVo = askAnswerVo;
	}

	public Boolean isAccused() {
		return accused;
	}

	public String[] getTag() {
		return tag;
	}

	public void setTag(String[] tag) {
		this.tag = tag;
	}

	public Boolean isCollectStatu() {
		return collectStatu;
	}

	public void setCollectStatu(Boolean collectStatu) {
		this.collectStatu = collectStatu;
	}

	public String getStrTime() {
		return strTime;
	}

	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}

	public Date getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Date systemTime) {
		this.systemTime = systemTime;
	}

	public boolean isMyself() {
		return isMyself;
	}

	public void setIsMyself(boolean isMyself) {
		this.isMyself = isMyself;
	}

	public Boolean isAccuseStatus() {
		return accuseStatus;
	}

	public void setAccuseStatus(Boolean accuseStatus) {
		this.accuseStatus = accuseStatus;
	}

	public Integer getAsk_limit() {
		return ask_limit;
	}

	public void setAsk_limit(Integer ask_limit) {
		this.ask_limit = ask_limit;
	}

	public boolean isShowAnswer() {
		return isShowAnswer;
	}

	public void setIsShowAnswer(boolean isShowAnswer) {
		this.isShowAnswer = isShowAnswer;
	}

	public Integer getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(Integer handleStatus) {
		this.handleStatus = handleStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
}
