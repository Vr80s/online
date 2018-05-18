package com.xczh.consumer.market.wxpay;

import com.xczh.consumer.market.utils.HttpUtil;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;

@Component
public class TokenThread implements Runnable {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TokenThread.class); 
	
	private static final String access_token_url="https://api.weixin.qq.com/cgi-bin/token?"
	        + "grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	private static  String appid;//你自己的appid
    private static  String appsecret;//你自己的appsecret
    
    private static String returnOpenidUri;//

	static{
		InputStream in = null;
		try{
			Properties properties = new Properties();
			in =TokenThread.class.getClassLoader().getResourceAsStream("config.properties");
			properties.load(in);
			
			//微信公众号和h5
			appid = properties.getProperty("wechatpay.h5.appid");
			appsecret = properties.getProperty("wechatpay.gzhSecret");
			returnOpenidUri  = properties.getProperty("returnOpenidUri");
			
			
			LOGGER.info("读取配置信息成功！"+appid+"====="+appsecret);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("读取配置信息失败！");
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
	public static String accessToken = null;

	@Override
	public void run() {
		LOGGER.info("returnOpenidUri:"+returnOpenidUri);
		if(returnOpenidUri.indexOf("dev") == -1) { //说明是开发环境，就不请求access_token了
			while (true) {
				try {
					LOGGER.info("读取配置信息成功！"+appid+"====="+appsecret);
					// 调用工具类获取access_token(每日最多获取100000次，每次获取的有效期为7200秒)
					String requestUrl=access_token_url.replace("APPID",appid).replace("APPSECRET", appsecret);
					String token = HttpUtil.sendGetRequest(requestUrl);
			        JSONObject jsonObject = JSONObject.fromObject(token);
					String  access_token = (String)jsonObject.get("access_token");
					Integer expires_in = (Integer)jsonObject.get("expires_in");
					if (null != access_token) {
						accessToken = access_token;
						LOGGER.info("accessToken获取成功："+expires_in+"===="+access_token);
						// 7000秒之后重新进行获取
						Thread.sleep((expires_in - 200) * 1000);
					} else {
						LOGGER.info("accessToken获取失败："+jsonObject.toString());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}else {
			LOGGER.info("开发环境不请求token");
		}
	}
	
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
