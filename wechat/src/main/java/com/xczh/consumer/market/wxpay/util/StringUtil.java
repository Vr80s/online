package com.xczh.consumer.market.wxpay.util;

public class StringUtil {
	
	private static final String FOLDER_SEPARATOR = "/";
	private static final String WINDOWS_FOLDER_SEPARATOR = "\\\\";
	private static final String TOP_PATH = "..";
	private static final String CURRENT_PATH = ".";
	public static final String HYPHEN = " ";

	public static String capitalize(String str) {
		return changeFirstCharacterCase(true, str);
	}

	private static String changeFirstCharacterCase(boolean capitalize, String str){
    
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0))
			return str;
		StringBuffer buf = new StringBuffer(strLen);
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		}
		else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
	  
		buf.append(str.substring(1));
		return buf.toString();
	}
  
}
