package com.xczh.consumer.market.wxpay.util;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xczh.consumer.market.utils.HttpUtil;

@Component
public class SingleAccessToken {

	
	private static final String access_token_url="https://api.weixin.qq.com/cgi-bin/token?"
        + "grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	
//	@Value("${wechatpay.h5.appid}")
//	public  String appid;
//	
//	@Value("${wechatpay.gzhSecret}")
//	public  String appsecret;
	
//	main 方法测试使用	
//  测试上的配置	
	private static final String appid="wx48d230a99f1c20d9";//你自己的appid
    private static final String appsecret="df2206fd380c36389ceccec9e8ac8f5c";//你自己的appsecret
	
	
//  生成上的配置
//	private static final String appid="wx81c7ce773415e00a";//你自己的appid
//  private static final String appsecret="b17cdd54ce4c35420a9e7782d7a27fa7";//你自己的appsecret


    /**
     * 获取SingleAccessToken对象
     * @return
     */
    public static String  getInstance(){
    	String requestUrl=access_token_url.replace("APPID",appid).replace("APPSECRET", appsecret);
        String token = HttpUtil.sendGetRequest(requestUrl);
        JSONObject jsonObject = JSONObject.fromObject(token);
 		String access_token = (String)jsonObject.get("access_token");
 		return access_token;
    }

    
}
