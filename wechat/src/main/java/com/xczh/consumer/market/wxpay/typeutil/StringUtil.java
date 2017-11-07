package com.xczh.consumer.market.wxpay.typeutil;

import com.xczh.consumer.market.wxpay.json.GsonUtils;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static String getString(Object obj) {
		if (obj == null)
			return null;
		return obj.toString();
	}

	public static <T> T getIDFromStr(String jsonStr, Class<T> classType) {
		T n = null;
		try {
			if (StringUtils.isBlank(jsonStr)) {
				return n;
			}
			n = GsonUtils.getInstance().fromJson(jsonStr, classType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}

	public static Long getLongFromStr(String jsonStr) {
		return getIDFromStr(jsonStr, Long.class);
	}

	public static Integer getIntegerFromStr(String jsonStr) {
		return getIDFromStr(jsonStr, Integer.class);
	}

	public static Double getDoubleFromStr(String jsonStr) {
		return getIDFromStr(jsonStr, Double.class);
	}

	public static Long getIDFromObj(Object object) {
		return getLongFromStr(object == null ? null : object.toString());
	}

	/**
	 * 不经过Gson转换的
	 * 
	 * @param str
	 * @return
	 */
	public static long getIdFromStr(String str) {
		if (isEmpty(str))
			return 0;
		return Long.parseLong(str);
	}

	/**
	 * @param str
	 * @return 字符串是否为空
	 */
	public static boolean isEmpty(String str) {
		return StringUtils.isBlank(str);
	}

	/**
	 * 判断是否中文
	 * @param str
	 * @return
	 */
	public static boolean isChineseChar(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}

}