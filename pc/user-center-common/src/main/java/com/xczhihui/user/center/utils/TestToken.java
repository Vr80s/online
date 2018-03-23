package com.xczhihui.user.center.utils;

import java.io.IOException;

import net.sf.json.JSONObject;

public class TestToken {

	private static final String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?"
			+ "grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 获取Access_Token
	 * @param appid
	 * @param appsecret
	 * @return
	 * @throws IOException
	 */
	public static AccessToken getAccessToken(String appid, String appsecret)
			throws IOException {
		AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("APPID", "wx48d230a99f1c20d9").replace(
				"APPSECRET", "df2206fd380c36389ceccec9e8ac8f5c");
		StringBuffer sbRet = HttpUtil.httpsRequest(requestUrl, "GET", null);// {"access_token":"ACCESS_TOKEN","expires_in":7200}//{"errcode":40013,"errmsg":"invalid appid"}
		JSONObject jsonObject = JSONObject.fromObject(sbRet.toString());
		System.out.println(jsonObject.toString());
		return accessToken;
	}

	
	
	
	
	
	
	public static void main(String[] args) throws IOException {

		/*
		 * String token
		 * =SingleAccessToken.getInstance().getAccessToken().getToken();
		 * System.out.println(token); String token1
		 * =SingleAccessToken.getInstance().getAccessToken().getToken();
		 * System.out.println(token1);
		 */
		getAccessToken(null,null);
	}
}
