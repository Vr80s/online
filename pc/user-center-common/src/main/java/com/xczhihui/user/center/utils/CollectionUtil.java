package com.xczhihui.user.center.utils;

import java.util.HashSet;
import java.util.Set;

public class CollectionUtil {

	/**
	 * 将逗号分割的字符串转成Set<Integer>
	 * 
	 * @param str
	 * @return
	 */
	public static Set<Integer> splitToIntSet(String str) {
		Set<Integer> set = new HashSet<Integer>();
		if (str != null && str.trim().length() > 0) {
			String[] strs = str.split(",");
			for (String s : strs) {
				set.add(Integer.parseInt(s.trim()));
			}
		}
		return set;
	}

	/**
	 * 将逗号分割的字符串转成Set<String>
	 * 
	 * @param str
	 * @return
	 */
	public static Set<String> splitToStringSet(String str) {
		Set<String> set = new HashSet<String>();
		if (str != null && str.trim().length() > 0) {
			String[] strs = str.split(",");
			for (String s : strs) {
				set.add(s.trim());
			}
		}
		return set;
	}

	/**
	 * 将数组转成Set。
	 * 
	 * @param array
	 * @return
	 */
	public static <T> Set<T> arrayToList(T array[]) {
		Set<T> set = new HashSet<T>();
		if (array != null && array.length > 0) {
			for (T i : array) {
				set.add(i);
			}
		}
		return set;
	}
}
