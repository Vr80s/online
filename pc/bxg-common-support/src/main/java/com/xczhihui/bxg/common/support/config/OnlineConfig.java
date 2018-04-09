package com.xczhihui.bxg.common.support.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OnlineConfig {

	//CC视频USERID
	@Value("${CC_USER_ID}")
	public static String CC_USER_ID;
	//CC直播USERID
	@Value("${CC_LIVE_USER_ID}")
	public static String CC_LIVE_USER_ID;
	//CC视频API KEY
	@Value("${CC_API_KEY}")
	public static String CC_API_KEY;
	//CC直播API KEY
	@Value("${CC_LIVE_API_KEY}")
	public static String CC_LIVE_API_KEY;
	//CC播放器ID
	@Value("${CC_PLAYER_ID}")
	public static String CC_PLAYER_ID;
	//微信开发平台应用id
	@Value("${WX_APP_ID}")
    public static String APP_ID;
    //商户id
	@Value("${WX_MCH_ID}")
    public static String MCH_ID;
    //API KEY
	@Value("${WX_API_KEY}")
    public static String API_KEY;
    //微信支付成功回调
	@Value("${WX_NOTIFY_PAY}")
    public static String NOTIFY_PAY;
    //微信分销系统KEY
	@Value("${WECHAT_API_KEY}")
    public static String WECHAT_API_KEY;

}
