package com.xczhihui.bxg.online.manager.vhall.bean;

/** 
 * ClassName: Webinar.java <br>
 * Description: vhall直播间相关<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月27日<br>
 */
public class Webinar {

	private String id;// 活动id
	private String subject;// 活动主题
	private String start_time;// 活动开始时间
	private String user_id;// 活动开始时间
	private String layout;// 1为单视频,2为单文档,3为文档+视频,观看布局
	private String type = "1";// 0为公开,1为非公开,个人公开/非公开活动
	private String auto_record = "1";// 0为否,1为是(默认为否),是否自动回放
	private String host;// 主持人姓名
	private String introduction;// 活动描述

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuto_record() {
		return auto_record;
	}

	public void setAuto_record(String auto_record) {
		this.auto_record = auto_record;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
