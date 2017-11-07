package com.xczhihui.bxg.online.web.base.common;

public class Constant {
	/**
	 * 用户登录后的session存放
	 */
	//public static Map<String, UserSessionBean> LOGIN_USER_SESSIONS = new HashMap<String, UserSessionBean>();
	public static final String LOGINUSER = "_user_";
	public static final String LOGINTOKEN = "_token_";
	private static final String SESSION_TICKET = "_ticket_";
	/**
	 * 有效的状态
	 */
	public static final short _STATUS_EN = 1;
	/**
	 * 无效的状态
	 */
	public static final short _STATUS_IN = 0;
	/**
	 * 未删除
	 */
	public static final boolean _NOT_DELETED = false; 
	/**
	 * 已删除
	 */
	public static final boolean _DELETED = true; 
	
	/**
	 * 问题未解决,未采纳答案情况下关闭问题
	 */
	public static final Integer _UNSOLVED = 5;
	
	/**
	 * 问题解决中,有用户回答,但是没有采纳答案
	 */
	public static final Integer _SOLVING = 4;
	
	/**
	 * 问题解决中,有用户回答,但是没有采纳答案
	 */
	public static final String _LATESTANSWER = "最新回答";
	
	/**
	 * 问题解决中,有用户回答,但是没有采纳答案
	 */
	public static final String _ADOPTEDANSWER = "已采纳答案";
	
	/**
	 * 网站用户
	 */
	public static final short _WEBSITE_USER = 1;
	
	/**
	 * 正在学
	 */
	public static final short _ON_LEARNING = 1;
	/**
	 * 正在学
	 */
	public static final String _ON_LEARNING_STRING = "正在学";
	/**
	 * 已学完
	 */
	public static final short _HAVE_LEARNED = 2;
	/**
	 * 已学完
	 */
	public static final String _HAVE_LEARNED_STRING = "已学完";
	/**
	 * 职业
	 * group表的type
	 */
	public static final Integer _TYPE_CAREER = 2;
	/**
	 * 知识体系
	 * group表的type
	 */
	public static final Integer _TYPE_KNOWLEDGE = 1;
	/**
	 * 赞同
	 */
	public static final Integer _APPLAUD = 1;
	/**
	 * 反对
	 */
	public static final Integer _OPPOSE = 2;
	/**
	 * 用户没有对问题投票
	 */
	public static final Integer _NOT_VOTE = 0;
	/**
	 * 新注册的用户默认昵称
	 */
	public static final String _NICKNAME = "博小白";
	/**
	 * 新注册的用户默认个性签名
	 */
	public static final String _AUTOGRAPH = "说点什么来彰显你的个性吧……";
	/**
	 * 用户默认头像
	 */
	public static final String _DEFAULT_HEAD_IMG = "/webview/images/usershow/defaultHeadImg.jpg";
	/**
	 * 当前登录用户非提问者或回答者
	 */
	public static final String _IS_NOT_AUTHOR_MESSAGE = "非创建用户";
	
}
