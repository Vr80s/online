package com.xczh.consumer.market.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	
	 public static String delHTMLTag(String htmlStr){ 
	        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
	        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
	        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
	         
	        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
	        Matcher m_script=p_script.matcher(htmlStr); 
	        htmlStr=m_script.replaceAll(""); //过滤script标签 
	         
	        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
	        Matcher m_style=p_style.matcher(htmlStr); 
	        htmlStr=m_style.replaceAll(""); //过滤style标签 
	         
	        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
	        Matcher m_html=p_html.matcher(htmlStr); 
	        htmlStr=m_html.replaceAll(""); //过滤html标签 

	        return htmlStr.trim(); //返回文本字符串 
	    }

	 	/**
	     * 验证邮箱
	     * @param email
	     * @return
	     */
	    public static boolean checkEmail(String email){
	        boolean flag = false;
	        try{
	                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	                Pattern regex = Pattern.compile(check);
	                Matcher matcher = regex.matcher(email);
	                flag = matcher.matches();
	            }catch(Exception e){
	                flag = false;
	            }
	        return flag;
	    }
	    
		/**
	     * 验证手机号
	     * @param phone 手机号
	     * @return
	     */
	    public static boolean checkPhone(String phone){
	        boolean flag = false;
	        try{
                String check = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(phone);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
	        return flag;
	    }
	    
	    
	 
	    
	    public static String formatDuring(long mss) {  
	        long days = mss / (1000 * 60 * 60 * 24);  
	        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);  
	        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);  
	        long seconds = (mss % (1000 * 60)) / 1000;  
	        return days + " days " + hours + " hours " + minutes + " minutes "  
	                + seconds + " seconds ";  
	    } 
	    
	    /**  
	    * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指  
	    * 定精度，以后的数字四舍五入。  
	    * @param v1 被除数  
	    * @param v2 除数  
	    * @param scale 表示表示需要精确到小数点以后几位。  
	    * @return 两个参数的商  
	    */  
	    public static double div(double v1,double v2,int scale){   
	    	if(scale<0){   
		    	throw new IllegalArgumentException(   
		    	"The scale must be a positive integer or zero");   
	    	}   
	    	BigDecimal b1 = new BigDecimal(Double.toString(v1));   
	    	BigDecimal b2 = new BigDecimal(Double.toString(v2));   
	    	return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();   
	    }  
	    
		public static void main(String[] args) {
	//	    	String str = "<div style='text-align:center;'> 整治“四风”   清弊除垢<br/><span style='font-size:14px;'> </span><span style='font-size:18px;'>公司召开党的群众路线教育实践活动动员大会</span><br/></div>";  
	//	    	String str1 = delHTMLTag(str);
	//	    	System.out.println(str1);
	//		 System.out.println(checkEmail("453999075@qq.com"));
	//		 System.out.println(checkEmail("45。3@qq.com"));
	//		 System.out.println(checkEmail("45。3@qq.com"));
			
//			double   f   =   111231.5585;  
//			BigDecimal   b   =   new   BigDecimal(f);  
//			double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
			 //System.out.println(div(500000d,3600000d,6));
		 
		   System.out.println(checkPhone("13723160793"));
		
		}
	
}
