package com.xczhihui.bxg.online.manager.common.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * 读取config.properties文件的工具类
 * @author xingzj
 * 
 * @date 2015年11月25日
 */
public class ConfigUtil {

	private static String config_path = ConfigUtil.class.getResource("/").getPath() + "config.properties";
	private static Properties pt = new Properties();
	
	static{
		try {
		
	        pt.load(new FileReader(config_path));
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
	
	/**
	 * 根据指定的key获取相应的值
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		return pt.getProperty(key);
	}
}
