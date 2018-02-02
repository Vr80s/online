package com.xczhihui.bxg.online.common.utils.cc.config;

/**
 * 功能：设置帐户有关信息及返回路径（基础配置页面）
 * 版本：2.1.2
 * 日期：2013-11-13
 * 作者：chu
 **/
public class Config {
	// notify_url 视频上传过程中服务器通知的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
	public static String notify_url = "http://apidemo.test.bokecc.com/java/utf8/notify.jsp";
	//	api_videos api获取视频信息接口
	public static String api_videos = "http://spark.bokecc.com/api/videos";
	//	按分类获得视频信息
	public static String api_category_videos = "http://spark.bokecc.com/api/videos/category";
	//	api_userinfo api获取用户信息接口
	public static String api_user = "http://spark.bokecc.com/api/user";
	// api_video api获取指定视频接口
	public static String api_video = "http://spark.bokecc.com/api/video";
	// api_video api获取指定视频接口，第二版
	public static String api_video_v2 = "http://spark.bokecc.com/api/video/v2";
	public static String api_video_v3 = "http://spark.bokecc.com/api/video/v3";
	// api_category api获取用户全部分类接口
	public static String api_category = "http://spark.bokecc.com/api/video/category";
	// 创建分类
	public static String api_create_category = "http://spark.bokecc.com/api/category/create";
	// 修改分类
	public static String api_update_category = "http://spark.bokecc.com/api/category/update";
	// 删除分类
	public static String api_delete_category = "http://spark.bokecc.com/api/category/delete";
	// api_updatevideo api编辑视频接口
	public static String api_updateVideo = "http://spark.bokecc.com/api/video/update";
	// api_deletevideo api删除视频接口
	public static String api_deleteVideo = "http://spark.bokecc.com/api/video/delete";
	// api_playCode api获取视频播放代码接口
	public static String api_playCode = "http://spark.bokecc.com/api/video/playcode";
	//api_searchVideos api搜索视频接口
	public static String api_searchVideos = "http://spark.bokecc.com/api/videos/search";
	//api_playlists api获取视频列表
	public static String api_videolist = "http://spark.bokecc.com/api/videos";
	//api_live_users 直播间用户
	public static String api_live_users = "http://api.csslcloud.net/api/room/users";
}
