package com.xczhihui.course.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baomidou.mybatisplus.toolkit.StringUtils;

public class XzStringUtils {

	public static String delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

//		if(StringUtils.isNotBlank(htmlStr)){  //过滤掉空格
//			htmlStr = htmlStr.replaceAll("&nbsp;", "");
//			System.out.println(htmlStr);
//		}
		
		if(htmlStr!=null && !"".equals(htmlStr)
				&& !"null".equals(htmlStr)) { //过滤掉空格
			htmlStr = htmlStr.replaceAll("&nbsp;", "");
		}
		return htmlStr.trim(); // 返回文本字符串
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号
	 * 
	 * @param phone
	 *            手机号
	 * @return
	 */
	public static boolean checkPhone(String phone) {
		boolean flag = false;
		try {
			// String check =
			// "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0-9]))\\d{8}$";
			String check = "^(1[345678]\\d{9})$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(phone);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证密码 英文大小写字母数字
	 * 
	 * @param phone
	 *            手机号
	 * @return
	 */
	public static boolean checkPassword(String passWord) {
		boolean flag = false;
		try {
			String check = "^([A-Za-z0-9]{6,18})$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(passWord);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * Description：验证呢城
	 * 
	 * @param passWord
	 * @return
	 * @return boolean
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 *
	 */
	public static boolean checkNickName(String nickName) {
		boolean flag = false;
		try {
			// /^[A-Za-z0-9_\-\u4e00-\u9fa5]+$/;//支持中文、字母、数字、'-'、'_'的组合，4-20个字符
			String check = "^([_A-Za-z0-9-\u4e00-\u9fa5]{1,20})$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(nickName);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	
	/**
	 * 校验微信号
	 * @param wechatNo
	 * @return
	 */
	 public static boolean checkWechatNo(String wechatNo) {
    	boolean flag = false;
		try {
			 // 校验微信号正则
	        String judge = "^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}+$";
	        Pattern pat = Pattern.compile(judge);
	        Matcher mat = pat.matcher(wechatNo);
	        flag = mat.matches();
		} catch (Exception e) {
			flag = false;
		}
	    return flag;
	 }
	
	
	
	//将href外站的链接换为302跳转
    public static String formatA(String value) {
        if (value == null)
            return null;
        String pattern = "<a([\\w\\W]*?) href=['|\"]([\\w\\W]*?)['|\"]([\\w\\W]*?)>";
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(value);
        while (m.find()) {
            String a = m.group();
            //整段匹配不会出现错误
            String _a = "<a" + " href='javascript:void(0)' >";
            value = value.replace(a, _a);
        }
        return value;
    }
	
	
	
	public static void main(String[] args) {
		
		System.out.println(checkNickName("54523"));
		
		
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
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
