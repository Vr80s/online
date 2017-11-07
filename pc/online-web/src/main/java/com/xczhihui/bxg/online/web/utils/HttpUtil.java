package com.xczhihui.bxg.online.web.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Fudong.Sun【】
 * @Date 2016/11/8 19:59
 */
public class HttpUtil {
  
    private final static int CONNECT_TIMEOUT = 5000; // in milliseconds
    private final static String DEFAULT_ENCODING = "UTF-8";  
      
    public static String postData(String urlStr, String data){  
        return postData(urlStr, data, null);  
    }  
      
    public static String postData(String urlStr, String data, String contentType){  
        BufferedReader reader = null;
        try {  
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);  
            conn.setConnectTimeout(CONNECT_TIMEOUT);  
            conn.setReadTimeout(CONNECT_TIMEOUT);  
            if(contentType != null)  
                conn.setRequestProperty("content-type", contentType);  
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
            if(data == null)  
                data = "";  
            writer.write(data);   
            writer.flush();  
            writer.close();    
  
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
            StringBuilder sb = new StringBuilder();  
            String line = null;  
            while ((line = reader.readLine()) != null) {  
                sb.append(line);  
                sb.append("\r\n");  
            }  
            return sb.toString();  
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {  
                if (reader != null)  
                    reader.close();  
            } catch (IOException e) {  
            }  
        }  
        return null;  
    }
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param paramMap
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendGet(String url, Map<String, String> paramMap) {
        String param = createQueryString(paramMap);//将map转化为查询字符串
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        try {
            String urlName = url + "?" + param;
            URL realUrl = new URL(urlName);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param paramMap
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url, Map<String, String> paramMap) {
        String param = createQueryString(paramMap);//将map转化为查询字符串
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
    /**
     * 功能：用一个Map生成一个QueryString，参数的顺序不可预知。
     *
     * @param queryMap
     * @return
     */
    public static String createQueryString(Map<String, String> queryMap) {
        if (queryMap == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : queryMap.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                String key = entry.getKey().trim();
                String value = URLEncoder.encode(entry.getValue().trim(),
                        DEFAULT_ENCODING);
                sb.append(String.format("%s=%s&", key, value));
            }
            return sb.substring(0, sb.length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 将 String 转为 map
     *
     * @param param
     * @return
     */
    public static Map<String, Object> getUrlParamMap(String param) {
        Map<String, Object> map = new HashMap<>();
        if ("".equals(param) || null == param) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }
    public static void main(String args[]) {
        Map<String,String> param = new HashMap<>();
        param.put("grant_type","authorization_code");
        param.put("code","CODE");
        param.put("appid", ThirdConnectionConfig.WX_APP_ID);
        param.put("secret",ThirdConnectionConfig.WX_APP_KEY);
        param.put("code","ED12F9682E734EEE91540227B3D0EE1E");
        //发送GET请求
        String returnString = sendGet("https://api.weixin.qq.com/sns/oauth2/access_token",param);
        Gson g = new GsonBuilder().create();
        Map<String, Object> fromJson = g.fromJson(returnString, Map.class);
        String error = String.valueOf(fromJson.get("error"));
        String error_description = (String) fromJson.get("error_description");
        System.out.println(returnString+"---");
        //发送POST请求
//        String s1 = sendPost("http://localhost:8888/abc/a.jsp", param);
//        System.out.println(s1);
    }
}  