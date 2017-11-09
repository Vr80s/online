package com.xczhihui.bxg.online.common.utils.cc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.online.common.utils.OnlineConfig;
import com.xczhihui.bxg.online.common.utils.cc.bean.CategoryBean;
import com.xczhihui.bxg.online.common.utils.cc.config.Config;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class CCUtils {
	/**
	 * 获得全部分类
	 * @return
	 */
	public static List<CategoryBean> getAllCategories() {
		List<CategoryBean> array = new ArrayList<CategoryBean>();
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userid", OnlineConfig.CC_USER_ID);
		paramsMap.put("format", "json");
		long time = System.currentTimeMillis();
		String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,OnlineConfig.CC_API_KEY);
		String responsestr = APIServiceFunction.HttpRetrieve(Config.api_category+"?" + requestURL);
		Gson g = new GsonBuilder().create();
		
		Map<String, Object> fromJson = g.fromJson(responsestr, Map.class);
		Map<String, Object> obj = (Map<String, Object>) fromJson.get("video");
		List<Map<String, Object>> lst = (List<Map<String, Object>>) obj.get("category");
		List<CategoryBean> sub = null;
		for (Map<String, Object> map : lst) {
			CategoryBean first = new CategoryBean();
			first.setId(map.get("id").toString());
			first.setName(map.get("name").toString());
			sub = new ArrayList<CategoryBean>();
			List<Map<String, Object>> subs = (List<Map<String, Object>>)map.get("sub-category");
			for (Map<String, Object> su : subs) {
				CategoryBean sec = new CategoryBean();
				sec.setId(su.get("id").toString());
				sec.setName(su.get("name").toString());
				sub.add(sec);
			}
			first.setSubs(sub);
			array.add(first);
		}
		return array;
	}
	
	public static CategoryBean createCategory(String name,String super_categoryid){
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userid", OnlineConfig.CC_USER_ID);
		paramsMap.put("name", name);
		if (StringUtils.hasText(super_categoryid)) {
			paramsMap.put("super_categoryid", super_categoryid);
		}
		paramsMap.put("format", "json");
		long time = System.currentTimeMillis();
		String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,OnlineConfig.CC_API_KEY);
		String responsestr = APIServiceFunction.HttpRetrieve(Config.api_create_category+"?" + requestURL);
		Gson g = new GsonBuilder().create();
		Map<String, Object> fromJson = g.fromJson(responsestr, Map.class);
		Map<String, Object> obj = (Map<String, Object>) fromJson.get("category");
		CategoryBean bean = new CategoryBean();
		bean.setId(obj.get("id").toString());
		bean.setName(obj.get("name").toString());
		return bean;
	}
	
	public static CategoryBean updateCategory(String categoryid,String name){
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userid", OnlineConfig.CC_USER_ID);
		paramsMap.put("categoryid", categoryid);
		paramsMap.put("name", name);
		paramsMap.put("format", "json");
		long time = System.currentTimeMillis();
		String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,OnlineConfig.CC_API_KEY);
		String responsestr = APIServiceFunction.HttpRetrieve(Config.api_update_category+"?" + requestURL);
		Gson g = new GsonBuilder().create();
		Map<String, Object> fromJson = g.fromJson(responsestr, Map.class);
		Map<String, Object> obj = (Map<String, Object>) fromJson.get("category");
		CategoryBean bean = new CategoryBean();
		bean.setId(obj.get("id").toString());
		bean.setName(obj.get("name").toString());
		return bean;
	}
	
	public static String deleteCategory(String categoryid){
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userid", OnlineConfig.CC_USER_ID);
		paramsMap.put("categoryid", categoryid);
		paramsMap.put("format", "json");
		long time = System.currentTimeMillis();
		String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,OnlineConfig.CC_API_KEY);
		String responsestr = APIServiceFunction.HttpRetrieve(Config.api_delete_category+"?" + requestURL);
		Gson g = new GsonBuilder().create();
		Map<String, Object> fromJson = g.fromJson(responsestr, Map.class);
		if (fromJson != null) {
			return fromJson.get("result").toString();
		}
		return null;
	}
}
