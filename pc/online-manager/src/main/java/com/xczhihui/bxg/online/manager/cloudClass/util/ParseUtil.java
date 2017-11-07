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
		if (delim == null)
			delim = ",";
		String[] array = string.split(delim);
		List<String> list = new ArrayList<String>();
		list = Arrays.asList(array);
		return list;
	}

	/**
	 * 判断多选题的选项是否是答案
	 */
	public static boolean isContained(String option, List<String> answerList) {
		if (CollectionUtils.isEmpty(answerList))
			return false;
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
		if (s == null)
			return 0;
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

	public static void main(String[] args) {
		// String string = "【士大的夫】士大夫地方【地的方】";
		// String string2 =
		// "<img src=\"data:image/jpeg;base64,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\">水电费水电费水电费<img src=\"data:image/jpeg;base64,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\">sdfsdfsdf电费";
		// System.out.println(ParseUtil.replaceAnswers(string2,
		// ParseUtil._REGEX_IMG, "ttt"));
		// System.out.println(ParseUtil.extractAnswers(string));
		// System.out.println(ParseUtil.replaceAnswers(string, ParseUtil._REGEX,
		// ""));
		// System.out.println(ParseUtil.replaceAnswers(ParseUtil.replaceAnswers(string,
		// ParseUtil._REGEX, ""), ParseUtil._BRACKET, "_____"));
		// System.out.println(ParseUtil.replaceAnswers(ParseUtil.replaceAnswers(string,
		// ParseUtil._REGEX, ParseUtil._BLANK),
		// ParseUtil._BRACKET,
		// ParseUtil._UNDERLINE));
		String stringImg = "什么是值传递和引用传递？<br />"
		        + "什么是值传递和引用传递？<img src=\"/knowledge-center-server2/image/getFileData?id=402880e55372d5ed015372d7c1140000\" alt=\"\" /><img src=\"/knowledge-center-server2/image/getFileData?id=402880e55372da32015372dac8580000\" alt=\"\" /><br />";
		Pattern pattern = Pattern.compile(_REGEX_SRC);// "(?<=【)(.+?)(?=】)"
		Matcher matcher = pattern.matcher(stringImg);
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			// ls.add(matcher.group());
			matcher.appendReplacement(
			        buffer,
			        "src=\""
			                + "http://dual2-kcenter.ixincheng.com:8091"
			                + matcher.group().substring(
			                        matcher.group().length() - 55,
			                        matcher.group().length()));
		}
		matcher.appendTail(buffer);
		// System.out.println(stringImg.matches(_REGEX_IMG));
		System.out.println(stringImg);
		System.out.println(buffer.toString());
	}

}
