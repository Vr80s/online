package com.xczhihui.bxg.online.manager.cloudClass.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.CollectionUtils;

import com.xczhihui.bxg.online.manager.utils.StringUtil;

public class ParseUtil {
	public static String _REGEX = "(?<=【)([\\s\\S]+?)(?=】)";

	/**
	 * 中括号
	 */
	public static String _BRACKET = "【】";

	/**
	 * 下划线
	 */
	public static String _UNDERLINE = "_____";

	/**
	 * 空字符
	 */
	public static String _BLANK = "";

	/**
	 * 使用
	 */
	public static String _ENABLED = "1";

	/**
	 * 正则表达式,匹配图片,可以匹配多个
	 */
	public static String _REGEX_IMG = "<img[^>]*?>[\\s\\S]*?";

	/**
	 * 正则表达式,匹配图片的src,可以匹配多个
	 */
	public static String _REGEX_SRC = "src=\"([^\"]*?)\"";

	/**
	 * 标记,查询参数使用
	 */
	public static Integer _MARK = 2;

	/**
	 * 选项
	 */
	public static String[] optionArray = { "A", "B", "C", "D", "E", "F", "G",
	        "H", "I", "J", "K", "L" };

	public static String _PREFIX = "src=\"data";

	public static String _ATTACHMENTCENTER = "attachmentCenter/download?aid=";

	/**
	 * 提取答案
	 * 
	 * @param content
	 * @return
	 */
	public static List<String> extractAnswers(String content) {
		List<String> ls = new ArrayList<String>();
		Pattern pattern = Pattern.compile(_REGEX);// "(?<=【)(.+?)(?=】)"
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			ls.add(matcher.group());
		}
		return ls;
	}

	/**
	 * @param content
	 *            需要替换的字符串
	 * @param regex
	 *            字符串里面需要被替换的字符
	 * @param replace
	 *            字符串里面需要被替换的字符替换后的字符
	 * @return
	 */
	public static String replaceAnswers(String content, String regex,
	        String replace) {
		return content.replaceAll(regex, replace);
	}

	/**
	 * 数组转字符串
	 * 
	 * @param vlaues
	 * @param delim
	 *            分隔符,不传默认为英文逗号分隔
	 * @return
	 */
	public static String arrayToString(Collection vlaues, String delim) {
		if (delim == null) {
			delim = ",";
		}
		StringBuffer buffer = new StringBuffer(vlaues.size());
		if (vlaues != null) {
			Iterator iterator = vlaues.iterator();
			while (iterator.hasNext()) {
				buffer.append(String.valueOf(iterator.next())).append(delim);
			}
		}
		if (buffer.length() > 0) {
			return buffer.toString().substring(0,
			        buffer.length() - delim.length());
		}
		return "";
	}

	/**
	 * 将字符串按符号分隔,转为list
	 * 
	 * @param string
	 * @param delim
	 * @return List<String>
	 */
	public static List<String> parseStringToList(String string, String delim) {
		if (StringUtil.checkNull(string)) {
			return null;
		}
		if (delim == null) {
            delim = ",";
        }
		String[] array = string.split(delim);
		List<String> list = new ArrayList<String>();
		list = Arrays.asList(array);
		return list;
	}

	/**
	 * 判断多选题的选项是否是答案
	 */
	public static boolean isContained(String option, List<String> answerList) {
		if (CollectionUtils.isEmpty(answerList)) {
            return false;
        }
		for (int i = 0, len = answerList.size(); i < len; i++) {
			if (option.equals(answerList.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 通过编辑器上传的图片通过接口无法访问图片对象
	 * 
	 * @param questionHead
	 * @return
	 */
	public static String parseEditorImg(String knowledgeCenterServer,
	        String questionHead, String attachmentCenterPath) {
		if (StringUtil.checkNull(questionHead)) {
			return "";
		}
		Pattern pattern = Pattern.compile(_REGEX_SRC);
		Matcher matcher = pattern.matcher(questionHead);
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			if (matcher.group().startsWith(_PREFIX) && matcher.group().contains(attachmentCenterPath) && matcher.group().contains(_ATTACHMENTCENTER)) {
				matcher.appendReplacement(buffer,"src=\""+ knowledgeCenterServer
				                + matcher.group().substring(matcher.group().length() - 55,matcher.group().length()));
			}
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 * 
	 * @param String
	 *            s 需要得到长度的字符串
	 * @return int 得到的字符串长度
	 */
	public static int length(String s) {
		if (s == null) {
            return 0;
        }
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i])) {
				len++;
			}
		}
		return len;
	}

	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

}
