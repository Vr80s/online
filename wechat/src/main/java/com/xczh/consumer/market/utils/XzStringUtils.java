package com.xczh.consumer.market.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

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

		if(StringUtils.isNotBlank(htmlStr)){  //过滤掉空格
			htmlStr = htmlStr.replaceAll("&nbsp;", "");
			System.out.println(htmlStr);
		}
		return htmlStr.trim(); // 返回文本字符串
	}

	
    public static void main(String[] args) {
		
    	
    	String htmlStr = "程【关于脉诊】&nbsp; &nbsp; &nbsp; &nbsp;脉诊是通过按触人体不同部位的脉搏，以体察脉象变化的切诊方法。又称切脉、诊脉、按脉、持脉。脉象的形成与脏腑气血密切相关，若脏腑气血发生病变，血脉运行就会受到影响，脉象就有变化。&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; 脉诊在临床上，可推断疾病的进退预后。临床上主要掌握脉诊的时间、病人的体位，医生的指法和指力轻重，每次按脉时间，以每侧脉搏跳动不少于50次为限，同时要了解健康人脉象的变化情况，才能正确地进行脉诊。我国古代医学在诊断疾病方面采用的脉诊，是一项独特诊法脉，是中医“四诊”（望、闻、问、切）之一，也是辨证论治的一种不可少的客观依据。【学习脉诊】&nbsp; &nbsp; &nbsp; &nbsp;脉诊作为中医辨证论治不可缺少的客观依据，是中医爱好者们最想要去了解的一项中医诊断技能。本音频摘自网络。尊重知识与劳动，转载请保留版权信息。本平台所发布内容的版权属于相关权利人所有。如存在不当使用的情况，请随时联系我们。";
    	String regEx_html = "&nbsp;111"; // 定义HTML标签的正则表达式
    	
    	System.out.println(htmlStr.replaceAll("&nbsp;",""));
    	
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
	public static boolean checkNickName(String passWord) {
		boolean flag = false;
		try {
			String check = "^([_A-Za-z0-9-\u4e00-\u9fa5]{4,20})$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(passWord);
			flag = matcher.matches();
		} catch (Exception e) {
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
