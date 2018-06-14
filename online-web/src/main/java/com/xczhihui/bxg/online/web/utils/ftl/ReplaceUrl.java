package com.xczhihui.bxg.online.web.utils.ftl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * ftl 定义调用java方法
 * 
 * @author yangxuan
 *
 */
public class ReplaceUrl implements TemplateMethodModelEx {  
  
    public ReplaceUrl() {  
    	
    }  

	@Override
	public Object exec(List arg0) throws TemplateModelException {
		
		/**
		 * 列表页，url替换用到的方法
		 */
		if(arg0!=null && arg0.size() == 3 && !arg0.get(1).toString().equals("linkCondition")) {
		   return 	replaceAccessTokenReg(arg0.get(0).toString(),arg0.get(1).toString(),arg0.get(2).toString());
		}
		
		/**
		 * banner连接更改用的方法
		 */
		if(arg0!=null && arg0.size() == 3 && arg0.get(1).toString().equals("linkCondition")) {
			String urlParams = arg0.get(0).toString();
			if(urlParams!=null && (urlParams.indexOf("course_id")!=-1 || urlParams.indexOf("userLecturerId")!=-1)) {
				return urlParams.substring(urlParams.indexOf("=")+1,urlParams.length());
			}
		}
		return null;
	}  
	
	 /**
	  * 
	  * @param url   
	  * @param key
	  * @param value
	  * @return
	  */
	 public  String replaceAccessTokenReg(String url, String key, String value) {
		 
        if(StringUtils.isNotBlank(url) && StringUtils.isNotBlank(value)) { 
        	 if(url.indexOf(key)==-1 && url.indexOf("?")==-1) {
             	url+="?"+key+"="+value;
             }else if(url.indexOf(key)==-1 && url.indexOf("?")!=-1){
            	url+="&"+key+"="+value;
             }else {
                url = url.replaceAll("(" + key + "=[^&]*)", key + "=" + value);   
             }
        	 
        }else if(StringUtils.isNotBlank(url) && !StringUtils.isNotBlank(value)) {
        	url = url.replaceAll("(" + key + "=[^&]*)", ""); 
        	if(url.length() == url.indexOf("?")+1 || url.length() == url.indexOf("&")+1) {
        		url = url.substring(0, url.length()-1);
        	}
        }
        if(!StringUtils.isNotBlank(key) && !StringUtils.isNotBlank(value)) {
        	url = url.substring(0, url.indexOf("?")+1);
        }
	    return url;  
     }
}   
