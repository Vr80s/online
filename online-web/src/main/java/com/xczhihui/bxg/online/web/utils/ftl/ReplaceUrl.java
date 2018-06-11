package com.xczhihui.bxg.online.web.utils.ftl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 用户  a 标签跳转时表示处理url后缀
 * @author yangxuan
 *
 */
public class ReplaceUrl implements TemplateMethodModelEx {  
  
    public ReplaceUrl() {  
        //System.out.println("ToUpperCaseMethod构造函数被调用啦");  
    }  

	@Override
	public Object exec(List arg0) throws TemplateModelException {
		
		
		if(arg0!=null && arg0.size() == 3 && !arg0.get(1).toString().equals("linkCondition")) {
		   return 	replaceAccessTokenReg(arg0.get(0).toString(),arg0.get(1).toString(),
				  arg0.get(2).toString());
		}
		if(arg0!=null && arg0.size() == 3 && arg0.get(1).toString().equals("linkCondition")) {
			String urlParams = arg0.get(0).toString();
			if(urlParams!=null && (urlParams.indexOf("course_id")!=-1
				 || urlParams.indexOf("userLecturerId")!=-1)) {
				return urlParams.substring(urlParams.indexOf("=")+1,urlParams.length());
			}
		}
		return null;
	}  
	
	
	
	
	 /**
	  *  
	  * @param url
	  * @param name
	  * @param accessToken
	  * @return
	  */
	 public  String replaceAccessTokenReg(String url, String name, String accessToken) {
		 
        if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(accessToken)) { 
        	 if(url.indexOf(name)==-1 && url.indexOf("?")==-1) {
             	url+="?"+name+"="+accessToken;
             }else if(url.indexOf(name)==-1 && url.indexOf("?")!=-1){
            	url+="&"+name+"="+accessToken;
             }else {
                url = url.replaceAll("(" + name + "=[^&]*)", name + "=" + accessToken);   
             }
        }else if(StringUtils.isNotBlank(url) && !StringUtils.isNotBlank(accessToken)) {
        	url = url.replaceAll("(" + name + "=[^&]*)", ""); 
        	if(url.length() == url.indexOf("?")+1 || url.length() == url.indexOf("&")+1) {
        		url = url.substring(0, url.length()-1);
        	}
        }
        if(!StringUtils.isNotBlank(name) && !StringUtils.isNotBlank(accessToken)) {
        	url = url.substring(0, url.indexOf("?")+1);
        }
	    return url;  
     }
		
		public static void main(String[] args) {
//			String url="http://localhost/index.jsp?d=4";
//			String name ="d";
//			String accessToken = "789";
//			
//		  if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(accessToken)) { 
////	        	 if(url.indexOf(name)==-1 && url.indexOf("?")==-1) {
////	             	url+="?"+name+"="+accessToken;
////	             }else if(url.indexOf(name)==-1 && url.indexOf("?")!=-1){
////	            	 url+="&"+name+"="+accessToken;
////	             }else {
////	                 url = url.replaceAll("(" + name + "=[^&]*)", name + "=" + accessToken);   
////	             }
//			  url = url.replaceAll("(/?" + name + "=[^&]*)", name + "=" + accessToken);   
//	        }
//			System.out.println( url);
//			System.out.println( url.length());
//			System.out.println( url.indexOf("?"));
			
		  String str ="course_id=123";	
	      System.out.println(str.substring(str.indexOf("=")+1,str.length()));
	      
			
			
			
		}
	
    
}   
