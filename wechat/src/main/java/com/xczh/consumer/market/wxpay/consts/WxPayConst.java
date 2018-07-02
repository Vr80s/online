package com.xczh.consumer.market.wxpay.consts;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WxPayConst {

    // 获取用户信息 -->这个是可以获取到unionid的
    public final static String USERMANAGER_GETINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    public static String appid4name = "熊猫中医";

    public static String gzh_appid;
    public static String gzh_mchid;
    public static String gzh_ApiKey;
    public static String gzh_Secret;

    public static String app_appid; // 1486066022
    public static String app_mchid;
    public static String app_ApiKey;
    // public static String app_Secret;

    public static String returnOpenidUri;
    public static String server_ip;

    public static String webdomain;
    private static Logger logger = LoggerFactory.getLogger(WxPayConst.class);

    static {
        InputStream in = null;
        try {
            Properties properties = new Properties();
            in = WxPayConst.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(in);

            // 微信公众号和h5
            gzh_appid = properties.getProperty("wechatpay.h5.appid");
            gzh_mchid = properties.getProperty("wechatpay.h5.mchid");
            gzh_ApiKey = properties.getProperty("wechatpay.h5.apiKey");
            gzh_Secret = properties.getProperty("wechatpay.gzhSecret");

            // app使用 微信登录和支付
            app_appid = properties.getProperty("wechatpay.app.appid");
            app_mchid = properties.getProperty("wechatpay.app.mchid");
            app_ApiKey = properties.getProperty("wechatpay.app.apiKey");
            // app_Secret = properties.getProperty("wechatpay.appSecret");

            returnOpenidUri = properties.getProperty("returnOpenidUri");
            server_ip = properties.getProperty("server_ip");

            webdomain = properties.getProperty("webdomain");

            logger.info("读取配置信息成功！");
        } catch (Exception e) {
            logger.error("读取配置信息失败！");
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
