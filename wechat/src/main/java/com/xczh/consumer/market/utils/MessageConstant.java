package com.xczh.consumer.market.utils;

public class MessageConstant {

	//获取商户信息
	public static final String OBTAIN_MERCHANT_INFORMATION = "https://api.weixin.qq.com/scan/merchantinfo/get?access_token=APPSECRET";
	
	//创建商品
	public static final String CREATE_GOODS = "https://api.weixin.qq.com/scan/merchantinfo/get?access_token=APPSECRET";
	
	//获取二维码的ticket
	public static final String QR_CODE_TICKET = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=APPSECRET";
	
	//通过ticket 获取二维码图片
	public static final String QR_CODE_IMG = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKETS";
	
	//获取永久素材   
	public static  final String GET_PERMANENT_MATERISL ="https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=APPSECRET"; 
	
	//获取用户基本信息(UnionID机制)   -->这个并不是通过用户授权得到了啊
	public static final String UNIONID_USERINFO ="https://api.weixin.qq.com/cgi-bin/user/info?access_token=APPSECRET&openid=OPENID&lang=zh_CN"; 

	// 请求消息类型：文本
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";
    // 请求消息类型：图片
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
    // 请求消息类型：语音
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
    // 请求消息类型：视频
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
    // 请求消息类型：小视频
    public static final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
    // 请求消息类型：地理位置
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
    // 请求消息类型：链接
    public static final String REQ_MESSAGE_TYPE_LINK = "link";
 
    // 请求消息类型：事件推送
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";
 
    // 事件类型：subscribe(订阅)
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    // 事件类型：unsubscribe(取消订阅)
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    
    // 事件类型：scan(用户已关注时的扫描带参数二维码)
    public static final String EVENT_TYPE_SCAN = "SCAN";
    // 事件类型：LOCATION(上报地理位置)
    public static final String EVENT_TYPE_LOCATION = "LOCATION";
    // 事件类型：CLICK(自定义菜单)
    public static final String EVENT_TYPE_CLICK = "CLICK";
 
    
    ////////////   这个具体在微信开发者平台  --》  在客户消息中      
    
    // 响应消息类型：文本
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    // 响应消息类型：图片
    public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
    // 响应消息类型：语音
    public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
    // 响应消息类型：视频
    public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
    // 响应消息类型：音乐
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
    // 响应消息类型：图文
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";
    
}
