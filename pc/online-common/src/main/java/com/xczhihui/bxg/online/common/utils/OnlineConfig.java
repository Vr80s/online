package com.xczhihui.bxg.online.common.utils;

import java.io.InputStream;
import java.util.Properties;

public class OnlineConfig {
	
	public static Properties pro = new Properties();
    static {
    	try {
    		InputStream in = OnlineConfig.class.getResource("/config.properties").openStream();
			pro.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
//	//OFFICE365用户ID
//	public static final String OFFICE365_USER_ID = getValue("OFFICE365_USER_ID");
//	//OFFICE365获取文件的地址
//	public static final String OFFICE365_URL = getValue("OFFICE365_URL");
//	//OFFICE365向量
//	public static final String OFFICE365_XL = getValue("OFFICE365_XL");
//	//OFFICE365密钥
//	public static final String OFFICE365_KEY = getValue("OFFICE365_KEY");
//	//CC视频USERID
//	public static final String CC_USER_ID = getValue("CC_USER_ID");
//	//CC直播USERID
//	public static final String CC_LIVE_USER_ID = getValue("CC_LIVE_USER_ID");
//	//CC视频API KEY
//	public static final String CC_API_KEY = getValue("CC_API_KEY");
//	//CC直播API KEY
//	public static final String CC_LIVE_API_KEY = getValue("CC_LIVE_API_KEY");
//	//CC播放器ID
//	public static final String CC_PLAYER_ID = getValue("CC_PLAYER_ID");
	//微信开发平台应用id
    public static final String APP_ID = getValue("WX_APP_ID");
    //商户id
    public static final String MCH_ID = getValue("WX_MCH_ID");
    //API KEY
    public static final String API_KEY = getValue("WX_API_KEY");
    //微信支付成功回调
    public static final String NOTIFY_PAY = getValue("WX_NOTIFY_PAY");
    //微信分销系统KEY
    public static final String WECHAT_API_KEY = getValue("WECHAT_API_KEY");

//    public static final String WEB_URL = getValue("WEB_URL");
    
    public static String getValue (String key) {
		return pro.getProperty(key);
	}
}
