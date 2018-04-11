package com.xczhihui.wechat.utils;

import java.io.InputStream;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.xczhihui.user.center.utils.AccessToken;
import com.xczhihui.user.center.utils.HttpUtil;


@Component
public class TokenThread implements Runnable {

	private static final String access_token_url="https://api.weixin.qq.com/cgi-bin/token?"
	        + "grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	// 微信公众号的凭证和秘钥
//	@Value("${wechatpay.gzh_appid}")
//	public  String appid;
//	@Value("${wechatpay.gzhSecret}")
//	public  String appsecret;
//	main 方法测试使用	
//  测试上的配置	
	private static  String appid;//你自己的appid
    private static  String appsecret;//你自己的appsecret
//  生成上的配置
//	private static final String appid="wx81c7ce773415e00a";//你自己的appid
//  private static final String appsecret="b17cdd54ce4c35420a9e7782d7a27fa7";//你自己的appsecret
    
	static{
		InputStream in = null;
		try{
			Properties properties = new Properties();
			in =TokenThread.class.getClassLoader().getResourceAsStream("config.properties");
			properties.load(in);
			
			//微信公众号和h5
			appid = properties.getProperty("wechatpay.gzh_appid");
			appsecret = properties.getProperty("wechatpay.gzhSecret");
			
			System.out.println("读取配置信息成功！"+appid+"====="+appsecret);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("读取配置信息失败！");
		}finally{
			if(in != null){
				try{
					in.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
    
	public static AccessToken accessToken = null;

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("读取配置信息成功！"+appid+"====="+appsecret);
				// 调用工具类获取access_token(每日最多获取100000次，每次获取的有效期为7200秒)
				String requestUrl=access_token_url.replace("APPID",appid).replace("APPSECRET", appsecret);
		        String token = HttpUtil.doGet(requestUrl);
		        JSONObject jsonObject = JSONObject.fromObject(token);
				String  access_token = (String)jsonObject.get("access_token");
				Integer expires_in = (Integer)jsonObject.get("expires_in");
				if (null != access_token) {
					accessToken = new AccessToken();
					accessToken.setExpiresIn(expires_in);
					accessToken.setToken(access_token);
					
					System.out.println("accessToken获取成功："+expires_in);
					// 7000秒之后重新进行获取
					Thread.sleep((expires_in - 200) * 1000);
				} else {
					System.out.println("accessToken获取失败："+jsonObject.toString());
					// 获取失败时，60秒之后尝试重新获取
					//Thread.sleep(60 * 1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
