package com.xczh.consumer.market.wxpay.consts;

import com.xczh.consumer.market.utils.ConfigUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

//import com.xczh.consumer.market.wxpay.PublicCommon;
@Component
public class WxPayConst {

	//??private static String notifyUrl = "";/*??PublicCommon.getHost()+??*///"http://wx.ixincheng.com/bxg/wxpay/wxNotify";

	public static String appid4name="熊猫中医";
	
	public static String gzh_appid;
	public static String gzh_mchid;
	public static String gzh_ApiKey ;
	public static String gzh_Secret ;
	
	public static String app_appid;  // 1486066022
	public static String app_mchid;
	public static String app_ApiKey;
//	public static String app_Secret;

	public static String returnOpenidUri;
	public static String server_ip;
	public static String webdomain;
	
	
	static{
		InputStream in = null;
		try{
			Properties properties = new Properties();
			in =WxPayConst.class.getClassLoader().getResourceAsStream("config.properties");
			properties.load(in);
			
			//微信公众号和h5
			gzh_appid = properties.getProperty("wechatpay.h5.appid");
			gzh_mchid = properties.getProperty("wechatpay.h5.mchid");
			gzh_ApiKey = properties.getProperty("wechatpay.h5.apiKey");
			gzh_Secret = properties.getProperty("wechatpay.gzhSecret");
			
			//app使用 微信登录和支付
			app_appid = properties.getProperty("wechatpay.app.appid");
			app_mchid = properties.getProperty("wechatpay.app.mchid");
			app_ApiKey = properties.getProperty("wechatpay.app.apiKey");
			//app_Secret = properties.getProperty("wechatpay.appSecret");
			
			webdomain = properties.getProperty("webdomain");
			returnOpenidUri = properties.getProperty("returnOpenidUri");
			server_ip = properties.getProperty("server_ip");
			
			
			System.out.println("读取配置信息成功！");
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


	//code获取URL
	public final static String CODE_URL ="https://open.weixin.qq.com/connect/oauth/authorize?appid=appid&redirect_uri=url&response_type=code&scope=snsapi_base&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect";
	public final static String CODE_URL_2 ="https://open.weixin.qq.com/connect/oauth/authorize?appid=appid&redirect_uri=url&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect";

	public final static String CODE_URL_3 ="https://open.weixin.qq.com/connect/oauth2/authorize";//没有参数的
	
	// openid获取URL  
	public final static String OAUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	//获取用户信息URL；
	public final static String USER_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

	//获取用户信息 -->这个是可以获取到unionid的
	public final static String USERMANAGER_GETINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 统一下单URL
	public final static String UNITORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	//对账单URL
	public static final String DOWNLOADBILL_URL ="https://api.mch.weixin.qq.com/pay/downloadbill";

	//查询订单URL
	public static final String QUERY_URL ="https://api.mch.weixin.qq.com/pay/orderquery";

	//access_token
	public static final String QUERY_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=appid&secret=secret";

	

	public static String return_code="return_code";
	public static String result_code="result_code";
	public static String return_code_url="CODE_URL";

	public static String recode_success="SUCCESS";
	public static String recode_fail="FAIL";

	///**
	// * 微信密钥(用于回调校验签名)
	// */
	//public  static String touch_weixin_key=key;

	public static String getNotifyUrl() {
		//HttpServletRequest request = ServletActionContext.getRequest();
		String strRootDir = System.getProperty("user.dir").replace("bin", "webapps");
		if(strRootDir.charAt(strRootDir.length()-1) != File.separatorChar) {
            strRootDir += File.separator;
        }
		System.out.println("getNotifyUrl->strRootDir->\r\n\t" + strRootDir );
		String path=strRootDir + "ROOT/WEB-INF/classes" + File.separator + "config.properties";
		System.out.println("getNotifyUrl->path->\r\n\t" + path );
		ConfigUtil cfg = new ConfigUtil(path);
		String notifyUrl= cfg.getConfig("notifyUrl");
		System.out.println("getNotifyUrl->notifyUrl->\r\n\t" + notifyUrl );
		return notifyUrl;
	}

}
