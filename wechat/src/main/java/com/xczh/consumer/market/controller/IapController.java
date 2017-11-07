package com.xczh.consumer.market.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.JsonObject;
import com.xczh.consumer.market.dao.IphoneIpaMapper;
import com.xczh.consumer.market.service.iphoneIpaService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.wxpay.util.Base64;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bxg/iap")
public class IapController {

    //购买凭证验证地址
    @Value("${iphone.iap.url}")
    private  String certificateUrl;



    @Autowired
    private iphoneIpaService iphoneIpaService;

    /**
     * 重写X509TrustManager
     */
    private static TrustManager myX509TrustManager = new X509TrustManager() {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }
    };



    /**
     * 接收iOS端发过来的购买凭证
     * @param receipt
     */
    @ResponseBody
    @RequestMapping("/setIapCertificate")
    public Object setIapCertificate( String receipt,String userId,String actualPrice) throws SQLException {
       // receipt=new String(Base64.decode(receipt));
        System.out.println("receipt:"+receipt);
        String url = certificateUrl;
        final String certificateCode = receipt;

        if(StringUtils.isNotEmpty(certificateCode)){

         String resp=   sendHttpsCoon(url, certificateCode);
         System.out.println("苹果返回数据:"+resp);

         //把苹果返回的数据存到数据库

        String productId= JSONObject.parseObject(resp).getJSONObject("receipt").getJSONArray("in_app").getJSONObject(0).get("product_id").toString();
        System.out.println("productId:"+productId);
        if(StringUtils.isBlank(productId)){
            return ResponseObject.newErrorResponseObject("操作失败！找不到productId");
        }

        //记录数据 增加熊猫币

//            10熊猫币 	消耗型项目	com.bj.healthlive.coin_01
//            60熊猫币 	消耗型项目	com.bj.healthlive.coin_02
//            120熊猫币 	消耗型项目	com.bj.healthlive.coin_03
//            500熊猫币 	消耗型项目	com.bj.healthlive.coin_04
//            980熊猫币 	消耗型项目	com.bj.healthlive.coin_05
//            4880熊猫币 	消耗型项目	com.bj.healthlive.coin_06

            int xmb=0;
            switch (productId){

                case "com.bj.healthlive.coin_01":
                    xmb=10;
                    break;
                case "com.bj.healthlive.coin_02":
                    xmb=60;
                    break;
                case "com.bj.healthlive.coin_03":
                    xmb=120;
                    break;
                case "com.bj.healthlive.coin_04":
                    xmb=500;
                    break;
                case "com.bj.healthlive.coin_05":
                    xmb=980;
                    break;
                case "com.bj.healthlive.coin_06":
                    xmb=4880;
                    break;
                    default:
                        throw new RuntimeException("productId不正确");
            }

            iphoneIpaService.increase(userId,xmb,resp,actualPrice);

            return ResponseObject.newSuccessResponseObject(null);
        }else{
            return null;
        }
    }

    /**
     * 发送请求
     * @param url
     * @return
     */
    private String sendHttpsCoon(String url, String code){
        if(url.isEmpty()){
            return null;
        }
        try {
            //设置SSLContext
            SSLContext ssl = SSLContext.getInstance("SSL");
            ssl.init(null, new TrustManager[]{myX509TrustManager}, null);

            //打开连接
            HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
            //设置套接工厂
            conn.setSSLSocketFactory(ssl.getSocketFactory());
            //加入数据
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type","application/json");

            JSONObject obj = new JSONObject();
            obj.put("receipt-data", code);

            BufferedOutputStream buffOutStr = new BufferedOutputStream(conn.getOutputStream());
            buffOutStr.write(obj.toString().getBytes());
            buffOutStr.flush();
            buffOutStr.close();

            //获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = null;
            StringBuffer sb = new StringBuffer();
            while((line = reader.readLine())!= null){
                sb.append(line);
            }
            return sb.toString();

        } catch (Exception e) {
            return null;
        }
    }
}