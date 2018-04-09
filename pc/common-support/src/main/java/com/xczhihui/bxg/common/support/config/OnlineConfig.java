package com.xczhihui.bxg.common.support.config;

import org.springframework.beans.factory.annotation.Value;

public class OnlineConfig {

	//CC视频USERID
	@Value("${CC_USER_ID}")
	public String ccuserId;
	//CC直播USERID
//	@Value("${cc.live.user.id}")
//	public String cc.live.user.id;
	//CC视频API KEY
	@Value("${cc.api.key}")
	public String ccApiKey;
	//CC直播API KEY
//	@Value("${cc.live.api.key}")
//	public String cc.live.api.key;
	//CC播放器ID
	@Value("${cc.player.id}")
	public String ccPlayerId;
	//微信开发平台应用id
	@Value("${wechat.app.id}")
    public String appId;
    //商户id
	@Value("${wechat.mch.id}")
    public String mchId;
    //API KEY
	@Value("${wechat.api.key}")
    public String apiKey;
    //微信支付成功回调
	@Value("${wechat.notify.pay}")
    public String notifyPay;
//    微信分销系统KEY
	@Value("${wechat.api.fx.key}")
    public String wechatApiId;

}
