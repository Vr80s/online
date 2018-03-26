package com.xczhihui.user.center.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class TestToken1 {

	public static void main(String[] args) {

		//String token =SingleAccessToken.getInstance().getAccessToken().getToken();
		//System.out.println("token"+token);
/*		Map<String,String>  description = new HashMap<String,String>();
		description.put("title", "杨宣");
		description.put("introduction", "测试");
		
		System.out.println(description.toString());
		String  targatUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadimg";
		targatUrl ="https://api.weixin.qq.com/cgi-bin/material/add_material";
		Map<String,String>  map = new HashMap<String,String>();
		map.put("access_token", "8_0W8Wzd1B0JhO0OSDSHTlpLIdjmEejE3kN1SKnfhAhZ4v_0b8DEMX4W_JvlDyUDFiG5fRorm8DWUjL3L75ZVfu5IT6dTXmbcPOxeudPzNgwkIQYk7nrImbkJHNNtVQ8XPoozyNAbOlMyf3AHaBIXiAJANUJ");
		map.put("type", "video");
		map.put("description","{\"title\":\"111\", \"introduction\":\"111\"}");
		*//**
		 * 上传文件
		 *//*
		 byte[] buffer = getBytes("F:\\Desktop\\1.mp4");
		 String str = HttpUtil.uploadFile(targatUrl, map,
				"media",
				"1.mp4",
				buffer);
		 System.out.println("就看你了，兄弟："+str);*/
		 
		 String title ="测试.....";
		 String thumb_media_id ="6y0EBrCsG4Si29EjR7_uAA0zZmaUa37VIAAqNDbrjQE";
		 String content_source_url ="http://www.ixincheng.com";
		 String str1 ="{\"articles\":[{\"title\": \""+title+"\",\"thumb_media_id\": \""+thumb_media_id+"\","+
					"\"author\": \""+title+"\","+
					"\"digest\": \""+title+"\","+
					"\"show_cover_pic\": 1,"+
					"\"content\": \""+title+"\","+
					"\"content_source_url\": \""+content_source_url+"\""+
					"}]"+
		 			"}";
		 Map<String,String>  mapYJ = new HashMap<String,String>();
		 mapYJ.put("access_token", "8_YmHpMe04i2eth1QufkXR6ChwwOVi7TPMzezqkM59y-UF2oWcHlJKvYyP-brjonMCo4U5u_h-c6ptpph8fDkQax7tA8Vu2TM0hAIsMTsZnUOJ3LWGNI035HElnHYGUBbAFAKYF");
		 mapYJ.put("articles", str1);
		 
		 
		 String url_access = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=8_YmHpMe04i2eth1QufkXR6ChwwOVi7TPMzezqkM59y-UF2oWcHlJKvYyP-brjonMCo4U5u_h-c6ptpph8fDkQax7tA8Vu2TM0hAIsMTsZnUOJ3LWGNI035HElnHYGUBbAFAKYF";
		 String hehe = HttpUtil.doHttpsPost(url_access, str1);
		 System.out.println("就看你了，兄弟："+hehe);
	}
	
	
	public String  nihao(){
		
		 String title ="测试.....";
		 String thumb_media_id ="6y0EBrCsG4Si29EjR7_uADBRH05VQ9ORFIWDUAYNMkk";
		 String content_source_url ="http://www.ixincheng.com";
		
		 StringBuilder sb=new StringBuilder();  
	        sb.append("{\"articles\":[");  
	        boolean t=false;  
//	        for(MicroWechatInfo info:list){  
//	            if(t)sb.append(",");  
//	            Pattern p = Pattern.compile("src\\s*=\\s*'(.*?)'",Pattern.CASE_INSENSITIVE);  
//	            String content = info.getMicrowechatcontent().replace("\"", "'");  
//	            Matcher m = p.matcher(content);  
//	            while (m.find()) {  
//	                String[] str = m.group().split("'");  
//	                if(str.length>1){  
//	                    try {  
//	                        if(!str[1].contains("//mmbiz.")){  
//	                            content = content.replace(str[1], uploadImg(UrlToFile(str[1]),getAccessToken(wx.getAppid(), wx.getAppkey())).getString("url"));  
//	                        }  
//	                    } catch (Exception e) {  
//	                    }  
//	                }  
//	            }  
/*        sb.append("{\"thumb_media_id\":\""+uploadMedia(new File(info.getMicrowechatcover()), getAccessToken(wx.getAppid(), wx.getAppkey()), "image").get("media_id")+"\"," +  
                "\"author\":\""+info.getMicrowechatauthor()+"\"," +  
                "\"title\":\""+info.getMicrowechattitle()+"\"," +  
                "\"content_source_url\":\""+info.getOriginallink()+"\"," +  
                "\"digest\":\""+info.getMicrowechatabstract()+"\"," +  
                "\"show_cover_pic\":\""+info.getShowcover()+"\"," +  
                "\"content\":\""+content+"\"}");  
        t=true;  */
//	        }  
	    sb.append("]}");  
		
		return null;
	}
	
	/** 
     * 获得指定文件的byte数组 
     */  
    private static byte[] getBytes(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }
	

}
