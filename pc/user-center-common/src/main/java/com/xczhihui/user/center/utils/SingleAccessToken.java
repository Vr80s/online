package com.xczhihui.user.center.utils;

import net.sf.json.JSONObject;

public class SingleAccessToken {

	
	private static final String access_token_url="https://api.weixin.qq.com/cgi-bin/token?"
        + "grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	private static final String appid="wx48d230a99f1c20d9";//你自己的appid
    private static final String appsecret="df2206fd380c36389ceccec9e8ac8f5c";//你自己的appsecret

    private AccessToken accessToken;
    private static SingleAccessToken singleAccessToken;
    /**
     * 私有构造函数
     */
    private SingleAccessToken(){
    	
        String requestUrl=access_token_url.replace("APPID",appid).replace("APPSECRET", appsecret);
        String token = HttpUtil.doGet(requestUrl);
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
