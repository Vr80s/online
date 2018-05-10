package com.xczhihui.common.support.cc.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xczhihui.common.support.cc.bean.CategoryBean;
import com.xczhihui.common.support.cc.config.Config;
import com.xczhihui.common.support.config.OnlineConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.*;

public class CCUtils {

	@Autowired
	private OnlineConfig onlineConfig;

	/**
	 * 获得全部分类
	 * @return
	 */
	public List<CategoryBean> getAllCategories() {
		List<CategoryBean> array = new ArrayList<CategoryBean>();
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userid", onlineConfig.ccuserId);
		paramsMap.put("format", "json");
		long time = System.currentTimeMillis();
		String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,onlineConfig.ccApiKey);
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
	
	public CategoryBean createCategory(String name,String super_categoryid){
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userid", onlineConfig.ccuserId);
		paramsMap.put("name", name);
		if (StringUtils.hasText(super_categoryid)) {
			paramsMap.put("super_categoryid", super_categoryid);
		}
		paramsMap.put("format", "json");
		long time = System.currentTimeMillis();
		String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,onlineConfig.ccApiKey);
		String responsestr = APIServiceFunction.HttpRetrieve(Config.api_create_category+"?" + requestURL);
		Gson g = new GsonBuilder().create();
		Map<String, Object> fromJson = g.fromJson(responsestr, Map.class);
		Map<String, Object> obj = (Map<String, Object>) fromJson.get("category");
		CategoryBean bean = new CategoryBean();
		bean.setId(obj.get("id").toString());
		bean.setName(obj.get("name").toString());
		return bean;
	}
	
	public CategoryBean updateCategory(String categoryid,String name){
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userid", onlineConfig.ccuserId);
		paramsMap.put("categoryid", categoryid);
		paramsMap.put("name", name);
		paramsMap.put("format", "json");
		long time = System.currentTimeMillis();
		String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,onlineConfig.ccApiKey);
		String responsestr = APIServiceFunction.HttpRetrieve(Config.api_update_category+"?" + requestURL);
		Gson g = new GsonBuilder().create();
		Map<String, Object> fromJson = g.fromJson(responsestr, Map.class);
		Map<String, Object> obj = (Map<String, Object>) fromJson.get("category");
		CategoryBean bean = new CategoryBean();
		bean.setId(obj.get("id").toString());
		bean.setName(obj.get("name").toString());
		return bean;
	}
	
	public String deleteCategory(String categoryid){
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userid", onlineConfig.ccuserId);
		paramsMap.put("categoryid", categoryid);
		paramsMap.put("format", "json");
		long time = System.currentTimeMillis();
		String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,onlineConfig.ccApiKey);
		String responsestr = APIServiceFunction.HttpRetrieve(Config.api_delete_category+"?" + requestURL);
		Gson g = new GsonBuilder().create();
		Map<String, Object> fromJson = g.fromJson(responsestr, Map.class);
		if (fromJson != null) {
			return fromJson.get("result").toString();
		}
		return null;
	}


	public String getVideoLength(String videoid){
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userid", onlineConfig.ccuserId);
		paramsMap.put("videoid", videoid);
		paramsMap.put("format", "json");
		long time = System.currentTimeMillis();
		String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,onlineConfig.ccApiKey);
		String responsestr = APIServiceFunction.HttpRetrieve(Config.api_video_v3+"?" + requestURL);
		Gson g = new GsonBuilder().create();
		System.out.println(responsestr);
		Map<String, Object> fromJson = g.fromJson(responsestr, Map.class);
		Map<String, Object> obj = (Map<String, Object>) fromJson.get("video");
		String duration = obj.get("duration").toString();
		return String.valueOf(Double.valueOf(duration).intValue()/60);
	}

	public String getPlayCode(String videoId,String audioStr){
		return getPlayCode(videoId,audioStr,"100%","100%");
	}

	public String getPlayCode(String videoId,String audioStr,String width,String height){
		String src = "https://p.bokecc.com/flash/single/"+onlineConfig.ccuserId+"_"+videoId+"_false_"+onlineConfig.ccPlayerId+"_1"+audioStr+"/player.swf";
		String id = UUID.randomUUID().toString().replace("-", "");
		String playCode = "";
		playCode+="<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" ";
		playCode+="		codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0\" ";
		playCode+="		width=\""+width+"\" ";
		playCode+="		height=\""+height+"\" ";
		playCode+="		id=\""+id+"\">";
		playCode+="		<param name=\"movie\" value=\""+src+"\" />";
		playCode+="		<param name=\"allowFullScreen\" value=\"true\" />";
		playCode+="		<param name=\"allowScriptAccess\" value=\"always\" />";
		playCode+="		<param value=\"transparent\" name=\"wmode\" />";
		playCode+="		<embed src=\""+src+"\" ";
		playCode+="			width=\""+width+"\" height=\""+height+"\" name=\""+id+"\" allowFullScreen=\"true\" ";
		playCode+="			wmode=\"transparent\" allowScriptAccess=\"always\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" ";
		playCode+="			type=\"application/x-shockwave-flash\"/> ";
		playCode+="	</object>";
		return playCode;
	}
}
