package com.xczh.consumer.market.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * 读取配置文件相关配置参数
 * @author yanghui  <br>
 * 2017年2月13日 上午10:20:04
 */
public class ConfigUtil {
	
	private String CONFIG_FILE = "";
	private Properties properties = null;
	
	public ConfigUtil(HttpSession session){
		ServletContext sc = session.getServletContext();
		String strRootDir = sc.getRealPath("/");
		if(strRootDir.charAt(strRootDir.length()-1) != File.separatorChar) {
            strRootDir += File.separator;
        }
		String path=strRootDir + "WEB-INF/classes" + File.separator + "config.properties";		
		this.CONFIG_FILE = path;
		this.init();
	}
	
	public ConfigUtil(String CONFIG_FILE){
		this.CONFIG_FILE = CONFIG_FILE;
		//??this.initFile();
		this.init();
	}
	
	public String getCONFIG_FILE() {
		return CONFIG_FILE;
	}
	public void setCONFIG_FILE(String cONFIG_FILE) {
		CONFIG_FILE = cONFIG_FILE;
	}

	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	//modify by tf 2016-0516
	private void initFile() {
		try {
			InputStream in = ConfigUtil.class.getResourceAsStream(CONFIG_FILE);
			properties = new Properties();
			properties.load(in);
		} catch (IOException e) {
			//System.err.println("reading `" + CONFIG_FILE + "` error!");
			e.printStackTrace();
		}
	}
	private void init() {
		try {
			//ClassLoader loader = ConfigUtil.class.getClassLoader();//InputStream in = loader.getResourceAsStream(CONFIG_FILE);
			//InputStream in = getClass().getResourceAsStream(CONFIG_FILE);
			InputStream in = new BufferedInputStream(new FileInputStream(CONFIG_FILE));   
			properties = new Properties();
			properties.load(in);
		} catch (IOException e) {
			//System.err.println("reading `" + CONFIG_FILE + "` error!");
			e.printStackTrace();
		}
	}

	public String getConfig(String key) {
		return getConfig(key, "");
	}

	public String getConfig(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public  int getConfig(String key, int defaultValue) {
		String val = getConfig(key);
		int setting = 0;
		try {
			setting = Integer.parseInt(val);
		} catch (NumberFormatException e) {
			setting = defaultValue;
		}
		return setting;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {		
		//ConfigUtil cfg = new ConfigUtil("/WEB-INF/config.properties");
		//ConfigUtil cfg = new ConfigUtil("D:/Management/WebRoot/WEB-INF/config.properties");
		//String ApiFrex= cfg.getConfig("ApiFrex");
		//System.out.println("ApiFrex=" + ApiFrex);
	}

    //public static Integer firstFreeMonthlyLimit() {
    //    ConfigUtil cf = new ConfigUtil("/config/confige.properties");
    //    int limitFee = cf.getConfig("use.first.free.fee.by.monthly.limit", 50000);
    //    return limitFee;
    //}
}


