package com.xczh.consumer.market.wxpay.util;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.xczh.consumer.market.utils.HttpUtil;

@Component
public class SingleAccessToken {

	
	private static final String access_token_url="https://api.weixin.qq.com/cgi-bin/token?"
        + "grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	
//	@Value("${wechatpay.gzh_appid}")
//	public  String appid;
//	
//	@Value("${wechatpay.gzhSecret}")
//	public  String appsecret;
	
//	main 方法测试使用	
//  测试上的配置	
//	private static final String appid="wx48d230a99f1c20d9";//你自己的appid
//  private static final String appsecret="df2206fd380c36389ceccec9e8ac8f5c";//你自己的appsecret
	
	
//  生成上的配置
	private static final String appid="wx81c7ce773415e00a";//你自己的appid
    private static final String appsecret="b17cdd54ce4c35420a9e7782d7a27fa7";//你自己的appsecret

    private AccessToken accessToken;
    private static SingleAccessToken singleAccessToken;
    /**
     * 私有构造函数
     */
    private SingleAccessToken(){
    	
        String requestUrl=access_token_url.replace("APPID",appid).replace("APPSECRET", appsecret);
        String token = HttpUtil.sendGetRequest(requestUrl);
        JSONObject jsonObject = JSONObject.fromObject(token);
		String access_token = (String)jsonObject.get("access_token");
		Integer expires_in = (Integer)jsonObject.get("expires_in");

		if(access_token == null){
			throw new RuntimeException(jsonObject.toString());
		}
		
    	accessToken = new AccessToken();
    	accessToken.setExpiresIn(expires_in);
    	accessToken.setToken(access_token);
    	
    	
    	System.out.println("accessToken:"+accessToken);
        initThread();
    }
    /**
     * 获取SingleAccessToken对象
     * @return
     */
    public static SingleAccessToken getInstance(){
        if(singleAccessToken==null){
        	 System.out.println("singleAccessToken 等于空");
            singleAccessToken=new SingleAccessToken();
        }
        return singleAccessToken;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
    /**
     * 开启线程，设置SingleAccessToken为空
     */
    private void initThread(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                   //睡眠7000秒
                    Thread.sleep(20000);    
                    singleAccessToken=null;

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();
    }
	
}
