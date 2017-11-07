package com.xczhihui.attachment.center.server.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigUtil {
	public static Properties properties = new Properties();
	
	static {
		InputStream is = null;
		try {
			is = ConfigUtil.class.getResourceAsStream("/config.properties");
			properties.load(is);
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static String getValueByName(String propertyName){
		return properties.getProperty(propertyName);
		
	}
		
	public static String replace(String content){
			Pattern p = Pattern.compile("\\s*|\\t|\\r|\\n");
			Matcher m = p.matcher(content);
			String after = m.replaceAll(""); 
			return after;
	}
}
